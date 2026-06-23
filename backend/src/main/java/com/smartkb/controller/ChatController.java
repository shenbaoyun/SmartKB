package com.smartkb.controller;

import com.smartkb.model.dto.request.ChatRequest;
import com.smartkb.model.dto.response.ApiResponse;
import com.smartkb.model.dto.response.ChatResponse;
import com.smartkb.service.ChatService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * 对话控制器
 *
 * @author codex
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    /** 非流式发送消息 */
    @PostMapping("/{conversationId}")
    public ApiResponse<ChatResponse> sendMessage(@PathVariable Long conversationId,
                                                  @Valid @RequestBody ChatRequest request) {
        ChatResponse response = chatService.sendMessage(conversationId, request);
        return ApiResponse.success(response);
    }

    /** SSE 流式发送消息 */
    @GetMapping(value = "/{conversationId}/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter streamMessage(@PathVariable Long conversationId,
                                     @RequestParam("content") String content,
                                     @RequestParam(value = "promptMode", defaultValue = "auto") String promptMode,
                                     @RequestParam(value = "promptId", required = false) Long promptId,
                                     @RequestParam(value = "modelCode", defaultValue = "deepseek-v3") String modelCode) {
        ChatRequest request = new ChatRequest();
        request.setContent(content);
        request.setPromptMode(promptMode);
        request.setPromptId(promptId);
        request.setModelCode(modelCode);
        return chatService.streamMessage(conversationId, request);
    }
}
