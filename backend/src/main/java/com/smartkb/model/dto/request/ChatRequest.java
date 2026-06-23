package com.smartkb.model.dto.request;

import jakarta.validation.constraints.NotBlank;

/**
 * 对话请求
 *
 * @author codex
 * @since 1.0.0
 */
public class ChatRequest {

    /** 用户消息内容 */
    @NotBlank(message = "消息内容不能为空")
    private String content;

    /** Prompt 模板ID：NULL=智能匹配，非NULL=手动锁定 */
    private Long promptId;

    /** Prompt 匹配模式：auto=智能匹配，manual=手动锁定 */
    private String promptMode = "auto";

    /** 模型代码，如：deepseek-v3 */
    private String modelCode;

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public Long getPromptId() { return promptId; }
    public void setPromptId(Long promptId) { this.promptId = promptId; }
    public String getPromptMode() { return promptMode; }
    public void setPromptMode(String promptMode) { this.promptMode = promptMode; }
    public String getModelCode() { return modelCode; }
    public void setModelCode(String modelCode) { this.modelCode = modelCode; }
}
