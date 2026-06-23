package com.smartkb.controller;

import com.smartkb.model.dto.request.ConversationCreateRequest;
import com.smartkb.model.dto.request.ConversationUpdateRequest;
import com.smartkb.model.dto.response.ApiResponse;
import com.smartkb.model.dto.response.ConversationVO;
import com.smartkb.model.dto.response.MessageVO;
import com.smartkb.service.ConversationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 会话管理控制器
 *
 * @author codex
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/conversations")
public class ConversationController {

    private final ConversationService conversationService;

    public ConversationController(ConversationService conversationService) {
        this.conversationService = conversationService;
    }

    @PostMapping
    public ApiResponse<ConversationVO> create(@RequestBody ConversationCreateRequest request) {
        return ApiResponse.success(conversationService.create(request));
    }

    @GetMapping
    public ApiResponse<List<ConversationVO>> list() {
        return ApiResponse.success(conversationService.list());
    }

    @GetMapping("/{id}")
    public ApiResponse<ConversationVO> getDetail(@PathVariable Long id) {
        return ApiResponse.success(conversationService.getDetail(id));
    }

    @PutMapping("/{id}")
    public ApiResponse<ConversationVO> update(@PathVariable Long id,
                                               @RequestBody ConversationUpdateRequest request) {
        return ApiResponse.success(conversationService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        conversationService.delete(id);
        return ApiResponse.success("删除成功", null);
    }

    @GetMapping("/{id}/messages")
    public ApiResponse<List<MessageVO>> getMessages(@PathVariable Long id) {
        return ApiResponse.success(conversationService.getMessages(id));
    }
}
