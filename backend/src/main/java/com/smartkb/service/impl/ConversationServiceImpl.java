package com.smartkb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.smartkb.common.exception.BusinessException;
import com.smartkb.common.exception.ErrorCodeEnum;
import com.smartkb.entity.model.ConversationDO;
import com.smartkb.entity.model.MessageDO;
import com.smartkb.mapper.ConversationMapper;
import com.smartkb.mapper.MessageMapper;
import com.smartkb.model.dto.request.ConversationCreateRequest;
import com.smartkb.model.dto.request.ConversationUpdateRequest;
import com.smartkb.model.dto.response.ConversationVO;
import com.smartkb.model.dto.response.MessageVO;
import com.smartkb.service.ConversationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ConversationServiceImpl implements ConversationService {
    private final ConversationMapper conversationMapper;
    private final MessageMapper messageMapper;
    public ConversationServiceImpl(ConversationMapper cm, MessageMapper mm) {
        this.conversationMapper = cm; this.messageMapper = mm;
    }
    @Override
    public ConversationVO create(ConversationCreateRequest req) {
        ConversationDO conv = new ConversationDO();
        conv.setTitle(req.getTitle() != null ? req.getTitle() : "New Chat");
        conv.setModelCode(req.getModelCode() != null ? req.getModelCode() : "deepseek-v3");
        conv.setPromptId(req.getPromptId());
        conv.setPromptMode(req.getPromptMode() != null ? req.getPromptMode() : "auto");
        conv.setCreatedTime(LocalDateTime.now()); conv.setUpdatedTime(LocalDateTime.now());
        conversationMapper.insert(conv);
        return toVO(conv, null);
    }
    @Override
    public List<ConversationVO> list() {
        List<ConversationDO> convs = conversationMapper.selectList(
            new LambdaQueryWrapper<ConversationDO>().orderByDesc(ConversationDO::getUpdatedTime));
        Map<Long, String> lastMsgMap = new HashMap<>();
        for (ConversationDO c : convs) {
            List<MessageDO> msgs = messageMapper.selectByConversationId(c.getId(), 1);
            if (!msgs.isEmpty()) {
                String ct = msgs.get(0).getContent();
                lastMsgMap.put(c.getId(), ct.length() > 50 ? ct.substring(0, 50) + "..." : ct);
            }
        }
        return convs.stream().map(c -> toVO(c, lastMsgMap.get(c.getId()))).collect(Collectors.toList());
    }
    @Override
    public ConversationVO getDetail(Long id) {
        ConversationDO conv = conversationMapper.selectById(id);
        if (conv == null) throw new BusinessException(ErrorCodeEnum.CONVERSATION_NOT_FOUND);
        List<MessageDO> msgs = messageMapper.selectByConversationId(id, 1);
        String last = msgs.isEmpty() ? null : msgs.get(0).getContent();
        if (last != null && last.length() > 50) last = last.substring(0, 50) + "...";
        return toVO(conv, last);
    }
    @Override
    public ConversationVO update(Long id, ConversationUpdateRequest req) {
        ConversationDO conv = conversationMapper.selectById(id);
        if (conv == null) throw new BusinessException(ErrorCodeEnum.CONVERSATION_NOT_FOUND);
        if (req.getTitle() != null) conv.setTitle(req.getTitle());
        if (req.getModelCode() != null) conv.setModelCode(req.getModelCode());
        if (req.getPromptId() != null) conv.setPromptId(req.getPromptId());
        if (req.getPromptMode() != null) conv.setPromptMode(req.getPromptMode());
        conv.setUpdatedTime(LocalDateTime.now());
        conversationMapper.updateById(conv);
        return getDetail(id);
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        if (conversationMapper.selectById(id) == null)
            throw new BusinessException(ErrorCodeEnum.CONVERSATION_NOT_FOUND);
        messageMapper.deleteByConversationId(id);
        conversationMapper.deleteById(id);
    }
    @Override
    public List<MessageVO> getMessages(Long conversationId) {
        return messageMapper.selectByConversationId(conversationId, 200).stream().map(m -> {
            MessageVO vo = new MessageVO();
            vo.setId(m.getId()); vo.setConversationId(m.getConversationId());
            vo.setRole(m.getRole()); vo.setContent(m.getContent());
            vo.setPromptId(m.getPromptId()); vo.setPromptName(m.getPromptName());
            vo.setModelCode(m.getModelCode()); vo.setCreatedTime(m.getCreatedTime());
            return vo;
        }).collect(Collectors.toList());
    }
    private ConversationVO toVO(ConversationDO c, String lastMsg) {
        ConversationVO vo = new ConversationVO();
        vo.setId(c.getId()); vo.setTitle(c.getTitle()); vo.setModelCode(c.getModelCode());
        vo.setPromptId(c.getPromptId()); vo.setPromptMode(c.getPromptMode());
        vo.setLastMessage(lastMsg); vo.setCreatedTime(c.getCreatedTime());
        vo.setUpdatedTime(c.getUpdatedTime());
        return vo;
    }
}