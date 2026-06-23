package com.smartkb.controller;

import com.smartkb.model.dto.request.PromptTemplateCreateRequest;
import com.smartkb.model.dto.request.PromptTemplateUpdateRequest;
import com.smartkb.model.dto.response.ApiResponse;
import com.smartkb.model.dto.response.PromptTemplateVO;
import com.smartkb.service.PromptMatcherService;
import com.smartkb.service.PromptTemplateService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Prompt 模板管理控制器
 *
 * @author codex
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/prompts")
public class PromptTemplateController {

    private final PromptTemplateService promptService;
    private final PromptMatcherService promptMatcherService;

    public PromptTemplateController(PromptTemplateService promptService,
                                     PromptMatcherService promptMatcherService) {
        this.promptService = promptService;
        this.promptMatcherService = promptMatcherService;
    }

    /** 获取所有启用模板 */
    @GetMapping
    public ApiResponse<List<PromptTemplateVO>> list() {
        return ApiResponse.success(promptService.listActive());
    }

    /** 获取单个模板 */
    @GetMapping("/{id}")
    public ApiResponse<PromptTemplateVO> getById(@PathVariable Long id) {
        return ApiResponse.success(promptService.getById(id));
    }

    /** 智能匹配：传入用户消息，返回匹配的 Prompt */
    @GetMapping("/match")
    public ApiResponse<PromptTemplateVO> match(@RequestParam("message") String message) {
        var matched = promptMatcherService.matchPrompt(message);
        PromptTemplateVO vo = promptService.getById(matched.getId());
        return ApiResponse.success("匹配成功：" + matched.getName(), vo);
    }

    /** 新增模板 */
    @PostMapping
    public ApiResponse<PromptTemplateVO> create(@Valid @RequestBody PromptTemplateCreateRequest request) {
        return ApiResponse.success(promptService.create(request));
    }

    /** 更新模板（即时生效） */
    @PutMapping("/{id}")
    public ApiResponse<PromptTemplateVO> update(@PathVariable Long id,
                                                 @RequestBody PromptTemplateUpdateRequest request) {
        return ApiResponse.success(promptService.update(id, request));
    }

    /** 删除模板（预设不可删） */
    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        promptService.delete(id);
        return ApiResponse.success("删除成功", null);
    }
}
