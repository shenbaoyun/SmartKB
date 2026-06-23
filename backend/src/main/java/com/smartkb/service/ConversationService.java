package com.smartkb.service;

import com.smartkb.model.dto.request.ConversationCreateRequest;
import com.smartkb.model.dto.request.ConversationUpdateRequest;
import com.smartkb.model.dto.response.ConversationVO;
import com.smartkb.model.dto.response.MessageVO;

import java.util.List;

/**
 * 会话管理服务接口
 *
 * @author codex
 * @since 1.0.0
 */
public interface ConversationService {

    ConversationVO create(ConversationCreateRequest request);
    List<ConversationVO> list();
    ConversationVO getDetail(Long id);
    ConversationVO update(Long id, ConversationUpdateRequest request);
    void delete(Long id);
    List<MessageVO> getMessages(Long conversationId);
}
