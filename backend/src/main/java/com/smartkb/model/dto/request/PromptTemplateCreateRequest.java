package com.smartkb.model.dto.request;

import jakarta.validation.constraints.NotBlank;

/**
 * 创建 Prompt 模板请求
 *
 * @author codex
 * @since 1.0.0
 */
public class PromptTemplateCreateRequest {

    @NotBlank(message = "模板名称不能为空")
    private String name;

    private String description;

    @NotBlank(message = "系统提示词不能为空")
    private String systemPrompt;

    private String category = "自定义";
    private String matchKeywords;
    private Integer matchMode = 1;
    private Integer sortOrder = 100;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getSystemPrompt() { return systemPrompt; }
    public void setSystemPrompt(String systemPrompt) { this.systemPrompt = systemPrompt; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getMatchKeywords() { return matchKeywords; }
    public void setMatchKeywords(String matchKeywords) { this.matchKeywords = matchKeywords; }
    public Integer getMatchMode() { return matchMode; }
    public void setMatchMode(Integer matchMode) { this.matchMode = matchMode; }
    public Integer getSortOrder() { return sortOrder; }
    public void setSortOrder(Integer sortOrder) { this.sortOrder = sortOrder; }
}
