package com.smartkb.model.dto.response;

/**
 * 对话响应
 *
 * @author codex
 * @since 1.0.0
 */
public class ChatResponse {
    private Long messageId;
    private String content;
    private String role;
    private String promptName;
    private String modelCode;

    public Long getMessageId() { return messageId; }
    public void setMessageId(Long messageId) { this.messageId = messageId; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public String getPromptName() { return promptName; }
    public void setPromptName(String promptName) { this.promptName = promptName; }
    public String getModelCode() { return modelCode; }
    public void setModelCode(String modelCode) { this.modelCode = modelCode; }
}
