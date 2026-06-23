package com.smartkb.service.impl;

import com.smartkb.common.exception.BusinessException;
import com.smartkb.common.exception.ErrorCodeEnum;
import com.smartkb.entity.model.PromptTemplateDO;
import com.smartkb.mapper.PromptTemplateMapper;
import com.smartkb.service.PromptMatcherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;

@Service
public class PromptMatcherServiceImpl implements PromptMatcherService {
    private static final Logger log = LoggerFactory.getLogger(PromptMatcherServiceImpl.class);
    private final PromptTemplateMapper promptTemplateMapper;
    public PromptMatcherServiceImpl(PromptTemplateMapper promptTemplateMapper) {
        this.promptTemplateMapper = promptTemplateMapper;
    }
    @Override
    public PromptTemplateDO matchPrompt(String userMessage) {
        if (userMessage == null || userMessage.isBlank()) return getDefault();
        PromptTemplateDO matched = matchByKeywords(userMessage);
        if (matched != null) { log.info("Prompt matched: {}", matched.getName()); return matched; }
        log.info("No keyword match, using default");
        return getDefault();
    }
    private PromptTemplateDO matchByKeywords(String userMessage) {
        List<PromptTemplateDO> templates = promptTemplateMapper.selectMatchableTemplates();
        if (templates.isEmpty()) return null;
        String lower = userMessage.toLowerCase();
        PromptTemplateDO best = null; int bestScore = 0;
        for (PromptTemplateDO t : templates) {
            int score = calcScore(lower, t.getMatchKeywords());
            if (score > bestScore) { bestScore = score; best = t; }
        }
        return bestScore > 0 ? best : null;
    }
    private int calcScore(String msg, String keywords) {
        if (keywords == null || keywords.isBlank()) return 0;
        return (int) Arrays.stream(keywords.split(",")).map(String::trim)
                .filter(k -> !k.isEmpty() && msg.contains(k.toLowerCase())).count();
    }
    private PromptTemplateDO getDefault() {
        PromptTemplateDO d = promptTemplateMapper.selectDefaultTemplate();
        if (d == null) throw new BusinessException(ErrorCodeEnum.PROMPT_TEMPLATE_NO_MATCH);
        return d;
    }
}