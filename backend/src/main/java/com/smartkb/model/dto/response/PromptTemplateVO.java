package com.smartkb.model.dto.response;

import java.time.LocalDateTime;

/**
 * Prompt 模板视图对象
 *
 * @author codex
 * @since 1.0.0
 */
public class PromptTemplateVO {
    private Long id;
    private String name;
    private String description;
    private String systemPrompt;
    private String category;
    private String matchKeywords;
    private Integer matchMode;
    private Integer isPreset;
    private Integer isActive;
    private Integer sortOrder;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
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
    public Integer getIsPreset() { return isPreset; }
    public void setIsPreset(Integer isPreset) { this.isPreset = isPreset; }
    public Integer getIsActive() { return isActive; }
    public void setIsActive(Integer isActive) { this.isActive = isActive; }
    public Integer getSortOrder() { return sortOrder; }
    public void setSortOrder(Integer sortOrder) { this.sortOrder = sortOrder; }
    public LocalDateTime getCreatedTime() { return createdTime; }
    public void setCreatedTime(LocalDateTime createdTime) { this.createdTime = createdTime; }
    public LocalDateTime getUpdatedTime() { return updatedTime; }
    public void setUpdatedTime(LocalDateTime updatedTime) { this.updatedTime = updatedTime; }
}
