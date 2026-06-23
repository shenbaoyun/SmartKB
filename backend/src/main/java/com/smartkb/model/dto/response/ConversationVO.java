package com.smartkb.model.dto.response;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 会话视图对象
 *
 * <p>返回给前端的会话信息，包含最近一条消息预览</p>
 *
 * @author codex
 * @since 1.0.0
 */
public class ConversationVO {

    private Long id;
    private String title;
    private String modelCode;
    private Long promptId;
    private String promptMode;
    private String lastMessage;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;

    // Getter / Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getModelCode() { return modelCode; }
    public void setModelCode(String modelCode) { this.modelCode = modelCode; }
    public Long getPromptId() { return promptId; }
    public void setPromptId(Long promptId) { this.promptId = promptId; }
    public String getPromptMode() { return promptMode; }
    public void setPromptMode(String promptMode) { this.promptMode = promptMode; }
    public String getLastMessage() { return lastMessage; }
    public void setLastMessage(String lastMessage) { this.lastMessage = lastMessage; }
    public LocalDateTime getCreatedTime() { return createdTime; }
    public void setCreatedTime(LocalDateTime createdTime) { this.createdTime = createdTime; }
    public LocalDateTime getUpdatedTime() { return updatedTime; }
    public void setUpdatedTime(LocalDateTime updatedTime) { this.updatedTime = updatedTime; }
}
