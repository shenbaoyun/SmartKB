package com.smartkb.model.dto.request;

/**
 * 更新 Prompt 模板请求
 *
 * @author codex
 * @since 1.0.0
 */
public class PromptTemplateUpdateRequest {
    private String name;
    private String description;
    private String systemPrompt;
    private String category;
    private String matchKeywords;
    private Integer matchMode;
    private Integer isActive;
    private Integer sortOrder;

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
    public Integer getIsActive() { return isActive; }
    public void setIsActive(Integer isActive) { this.isActive = isActive; }
    public Integer getSortOrder() { return sortOrder; }
    public void setSortOrder(Integer sortOrder) { this.sortOrder = sortOrder; }
}
