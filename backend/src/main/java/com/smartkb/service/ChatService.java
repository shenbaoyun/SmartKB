package com.smartkb.service;

import com.smartkb.model.dto.request.ChatRequest;
import com.smartkb.model.dto.response.ChatResponse;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * 对话服务接口
 *
 * @author codex
 * @since 1.0.0
 */
public interface ChatService {

    /** 非流式对话 */
    ChatResponse sendMessage(Long conversationId, ChatRequest request);

    /** SSE 流式对话 */
    SseEmitter streamMessage(Long conversationId, ChatRequest request);
}
