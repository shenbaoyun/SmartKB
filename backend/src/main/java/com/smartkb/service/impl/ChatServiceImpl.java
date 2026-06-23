package com.smartkb.service.impl;

import com.smartkb.common.enums.MessageRoleEnum;
import com.smartkb.common.enums.PromptMatchModeEnum;
import com.smartkb.common.exception.BusinessException;
import com.smartkb.common.exception.ErrorCodeEnum;
import com.smartkb.entity.model.ConversationDO;
import com.smartkb.entity.model.MessageDO;
import com.smartkb.entity.model.ModelConfigDO;
import com.smartkb.entity.model.PromptTemplateDO;
import com.smartkb.mapper.ConversationMapper;
import com.smartkb.mapper.MessageMapper;
import com.smartkb.mapper.PromptTemplateMapper;
import com.smartkb.model.dto.request.ChatRequest;
import com.smartkb.service.ChatService;
import com.smartkb.service.ModelConfigService;
import com.smartkb.service.PromptMatcherService;
import com.smartkb.service.RagService;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {
    private static final Logger log = LoggerFactory.getLogger(ChatServiceImpl.class);
    private final ConversationMapper conversationMapper;
    private final MessageMapper messageMapper;
    private final PromptTemplateMapper promptTemplateMapper;
    private final ModelConfigService modelConfigService;
    private final PromptMatcherService promptMatcherService;
    private final RagService ragService;

    @Value("${smartkb.chat.max-history-messages:20}")
    private int maxHistoryMessages;

    @Value("${smartkb.model.deepseek.api-key}")
    private String llmApiKey;

    @Value("${smartkb.model.deepseek.base-url}")
    private String llmBaseUrl;

    public ChatServiceImpl(ConversationMapper cm, MessageMapper mm, PromptTemplateMapper pm,
                           ModelConfigService mcs, PromptMatcherService pms, RagService rs) {
        this.conversationMapper = cm; this.messageMapper = mm;
        this.promptTemplateMapper = pm; this.modelConfigService = mcs;
        this.promptMatcherService = pms; this.ragService = rs;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public com.smartkb.model.dto.response.ChatResponse sendMessage(Long cid, ChatRequest req) {
        ConversationDO conv = lookup(cid);
        PromptTemplateDO prompt = resolvePrompt(req);
        String mc = modelCode(req, conv);
        ModelConfigDO mcfg = modelConfigService.getByCode(mc);
        String ai = callLlm(mcfg, buildMessages(cid, req.getContent(), prompt));
        saveUserMsg(cid, req.getContent(), prompt, mc);
        Long aid = saveAssistantMsg(cid, ai, prompt, mc);
        conv.setUpdatedTime(LocalDateTime.now()); conversationMapper.updateById(conv);
        com.smartkb.model.dto.response.ChatResponse resp = new com.smartkb.model.dto.response.ChatResponse();
        resp.setMessageId(aid); resp.setContent(ai); resp.setRole(MessageRoleEnum.ASSISTANT.getCode());
        resp.setPromptName(prompt.getName()); resp.setModelCode(mc);
        return resp;
    }

    @Override
    public SseEmitter streamMessage(Long cid, ChatRequest req) {
        SseEmitter emitter = new SseEmitter(300000L);
        new Thread(() -> {
            try {
                com.smartkb.model.dto.response.ChatResponse resp = sendMessage(cid, req);
                String ct = resp.getContent();
                if (ct != null && !ct.isEmpty()) {
                    for (int i = 0; i < ct.length(); i += 3) {
                        int end = Math.min(i + 3, ct.length());
                        emitter.send(SseEmitter.event().name("message").data(ct.substring(i, end)));
                        Thread.sleep(10);
                    }
                }
                emitter.send(SseEmitter.event().name("done").data("{\"id\":" + resp.getMessageId() + "}"));
                emitter.complete();
            } catch (Exception e) {
                log.error("SSE fail", e);
                try { emitter.send(SseEmitter.event().name("error").data(e.getMessage())); emitter.complete(); }
                catch (Exception ignored) {}
            }
        }).start();
        return emitter;
    }

    private ConversationDO lookup(Long cid) {
        ConversationDO c = conversationMapper.selectById(cid);
        if (c == null) throw new BusinessException(ErrorCodeEnum.CONVERSATION_NOT_FOUND);
        return c;
    }

    private String modelCode(ChatRequest req, ConversationDO conv) {
        return req.getModelCode() != null ? req.getModelCode() : conv.getModelCode();
    }

    private String callLlm(ModelConfigDO cfg, List<ChatMessage> msgs) {
        String key = llmApiKey;
        String url = llmBaseUrl;
        String model = cfg != null && cfg.getModelName() != null ? cfg.getModelName() : "deepseek-chat";
        if (cfg != null && cfg.getBaseUrl() != null && !cfg.getBaseUrl().isBlank()) url = cfg.getBaseUrl();
        ChatLanguageModel clm = OpenAiChatModel.builder().apiKey(key).baseUrl(url).modelName(model)
                .timeout(Duration.ofSeconds(120)).build();
        try { return clm.chat(dev.langchain4j.model.chat.request.ChatRequest.builder().messages(msgs).build()).aiMessage().text(); }
        catch (Exception e) { throw new BusinessException(ErrorCodeEnum.AI_CALL_ERROR, e.getMessage()); }
    }

    private PromptTemplateDO resolvePrompt(ChatRequest req) {
        if (PromptMatchModeEnum.MANUAL.getCode().equals(req.getPromptMode()) && req.getPromptId() != null)
            return promptTemplateMapper.selectById(req.getPromptId());
        return promptMatcherService.matchPrompt(req.getContent());
    }

    private List<ChatMessage> buildMessages(Long cid, String uc, PromptTemplateDO prompt) {
        List<ChatMessage> msgs = new ArrayList<>();
        String st = prompt.getSystemPrompt();
        if (ragService.hasDocuments()) {
            List<String> ctxs = ragService.search(uc, 3);
            if (!ctxs.isEmpty()) {
                StringBuilder sb = new StringBuilder("\n\n[Knowledge base]:\n");
                for (int i = 0; i < ctxs.size(); i++) {
                    String c = ctxs.get(i);
                    if (c.length() > 1000) c = c.substring(0, 1000) + "...";
                    sb.append("\n--- Doc ").append(i + 1).append(" ---\n").append(c);
                }
                st += sb.toString();
            }
        }
        msgs.add(new SystemMessage(st));
        for (MessageDO m : messageMapper.selectByConversationId(cid, maxHistoryMessages)) {
            if (MessageRoleEnum.USER.getCode().equals(m.getRole())) msgs.add(new UserMessage(m.getContent()));
            else if (MessageRoleEnum.ASSISTANT.getCode().equals(m.getRole())) msgs.add(new AiMessage(m.getContent()));
        }
        msgs.add(new UserMessage(uc));
        return msgs;
    }

    private void saveUserMsg(Long cid, String content, PromptTemplateDO p, String mc) {
        MessageDO m = new MessageDO(); m.setConversationId(cid);
        m.setRole(MessageRoleEnum.USER.getCode()); m.setContent(content);
        m.setPromptId(p.getId()); m.setPromptName(p.getName());
        m.setModelCode(mc); m.setCreatedTime(LocalDateTime.now());
        messageMapper.insert(m);
    }

    private Long saveAssistantMsg(Long cid, String content, PromptTemplateDO p, String mc) {
        MessageDO m = new MessageDO(); m.setConversationId(cid);
        m.setRole(MessageRoleEnum.ASSISTANT.getCode()); m.setContent(content);
        m.setPromptId(p.getId()); m.setPromptName(p.getName());
        m.setModelCode(mc); m.setCreatedTime(LocalDateTime.now());
        messageMapper.insert(m);
        return m.getId();
    }
}