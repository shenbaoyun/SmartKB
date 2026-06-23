package com.smartkb.model.dto.request;

/**
 * 创建会话请求
 *
 * @author codex
 * @since 1.0.0
 */
public class ConversationCreateRequest {
    private String title = "新对话";
    private String modelCode = "deepseek-v3";
    private Long promptId;
    private String promptMode = "auto";

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getModelCode() { return modelCode; }
    public void setModelCode(String modelCode) { this.modelCode = modelCode; }
    public Long getPromptId() { return promptId; }
    public void setPromptId(Long promptId) { this.promptId = promptId; }
    public String getPromptMode() { return promptMode; }
    public void setPromptMode(String promptMode) { this.promptMode = promptMode; }
}
