package com.smartkb.service.impl;

import com.smartkb.common.exception.BusinessException;
import com.smartkb.common.exception.ErrorCodeEnum;
import com.smartkb.entity.model.PromptTemplateDO;
import com.smartkb.mapper.PromptTemplateMapper;
import com.smartkb.model.dto.request.PromptTemplateCreateRequest;
import com.smartkb.model.dto.request.PromptTemplateUpdateRequest;
import com.smartkb.model.dto.response.PromptTemplateVO;
import com.smartkb.service.PromptTemplateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PromptTemplateServiceImpl implements PromptTemplateService {
    private static final Logger log = LoggerFactory.getLogger(PromptTemplateServiceImpl.class);
    private final PromptTemplateMapper promptTemplateMapper;
    public PromptTemplateServiceImpl(PromptTemplateMapper promptTemplateMapper) {
        this.promptTemplateMapper = promptTemplateMapper;
    }
    @Override
    public List<PromptTemplateVO> listActive() {
        return promptTemplateMapper.selectActiveTemplates().stream().map(this::toVO).collect(Collectors.toList());
    }
    @Override
    public PromptTemplateVO getById(Long id) {
        PromptTemplateDO t = promptTemplateMapper.selectById(id);
        if (t == null) throw new BusinessException(ErrorCodeEnum.PROMPT_TEMPLATE_NOT_FOUND);
        return toVO(t);
    }
    @Override
    public PromptTemplateVO create(PromptTemplateCreateRequest req) {
        PromptTemplateDO t = new PromptTemplateDO();
        t.setName(req.getName()); t.setDescription(req.getDescription());
        t.setSystemPrompt(req.getSystemPrompt());
        t.setCategory(req.getCategory() != null ? req.getCategory() : "custom");
        t.setMatchKeywords(req.getMatchKeywords());
        t.setMatchMode(req.getMatchMode() != null ? req.getMatchMode() : 1);
        t.setIsPreset(0); t.setIsActive(1);
        t.setSortOrder(req.getSortOrder() != null ? req.getSortOrder() : 100);
        t.setCreatedTime(LocalDateTime.now()); t.setUpdatedTime(LocalDateTime.now());
        promptTemplateMapper.insert(t);
        log.info("Prompt created: id={}", t.getId());
        return toVO(t);
    }
    @Override
    public PromptTemplateVO update(Long id, PromptTemplateUpdateRequest req) {
        PromptTemplateDO t = promptTemplateMapper.selectById(id);
        if (t == null) throw new BusinessException(ErrorCodeEnum.PROMPT_TEMPLATE_NOT_FOUND);
        if (req.getName() != null) t.setName(req.getName());
        if (req.getDescription() != null) t.setDescription(req.getDescription());
        if (req.getSystemPrompt() != null) t.setSystemPrompt(req.getSystemPrompt());
        if (req.getCategory() != null) t.setCategory(req.getCategory());
        if (req.getMatchKeywords() != null) t.setMatchKeywords(req.getMatchKeywords());
        if (req.getMatchMode() != null) t.setMatchMode(req.getMatchMode());
        if (req.getIsActive() != null) t.setIsActive(req.getIsActive());
        if (req.getSortOrder() != null) t.setSortOrder(req.getSortOrder());
        t.setUpdatedTime(LocalDateTime.now());
        promptTemplateMapper.updateById(t);
        log.info("Prompt updated: id={}", id);
        return toVO(t);
    }
    @Override
    public void delete(Long id) {
        PromptTemplateDO t = promptTemplateMapper.selectById(id);
        if (t == null) throw new BusinessException(ErrorCodeEnum.PROMPT_TEMPLATE_NOT_FOUND);
        if (t.getIsPreset() != null && t.getIsPreset() == 1) {
            throw new BusinessException(ErrorCodeEnum.PROMPT_TEMPLATE_PRESET_DELETE);
        }
        promptTemplateMapper.deleteById(id);
    }
    private PromptTemplateVO toVO(PromptTemplateDO t) {
        PromptTemplateVO vo = new PromptTemplateVO();
        vo.setId(t.getId()); vo.setName(t.getName()); vo.setDescription(t.getDescription());
        vo.setSystemPrompt(t.getSystemPrompt()); vo.setCategory(t.getCategory());
        vo.setMatchKeywords(t.getMatchKeywords()); vo.setMatchMode(t.getMatchMode());
        vo.setIsPreset(t.getIsPreset()); vo.setIsActive(t.getIsActive());
        vo.setSortOrder(t.getSortOrder()); vo.setCreatedTime(t.getCreatedTime());
        vo.setUpdatedTime(t.getUpdatedTime());
        return vo;
    }
}