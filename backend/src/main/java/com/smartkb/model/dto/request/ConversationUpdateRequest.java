package com.smartkb.model.dto.request;

/**
 * 更新会话请求
 *
 * @author codex
 * @since 1.0.0
 */
public class ConversationUpdateRequest {
    private String title;
    private String modelCode;
    private Long promptId;
    private String promptMode;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getModelCode() { return modelCode; }
    public void setModelCode(String modelCode) { this.modelCode = modelCode; }
    public Long getPromptId() { return promptId; }
    public void setPromptId(Long promptId) { this.promptId = promptId; }
    public String getPromptMode() { return promptMode; }
    public void setPromptMode(String promptMode) { this.promptMode = promptMode; }
}
