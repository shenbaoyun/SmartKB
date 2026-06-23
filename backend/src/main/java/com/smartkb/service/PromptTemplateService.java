package com.smartkb.service;

import com.smartkb.model.dto.request.PromptTemplateCreateRequest;
import com.smartkb.model.dto.request.PromptTemplateUpdateRequest;
import com.smartkb.model.dto.response.PromptTemplateVO;

import java.util.List;

/**
 * Prompt 模板管理服务接口
 *
 * @author codex
 * @since 1.0.0
 */
public interface PromptTemplateService {

    List<PromptTemplateVO> listActive();
    PromptTemplateVO getById(Long id);
    PromptTemplateVO create(PromptTemplateCreateRequest request);
    PromptTemplateVO update(Long id, PromptTemplateUpdateRequest request);
    void delete(Long id);
}
