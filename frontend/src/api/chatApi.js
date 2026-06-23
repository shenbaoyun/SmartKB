import request from './request'

export function sendMessage(conversationId, data) {
  return request.post(`/chat/${conversationId}`, data)
}

/** 获取 SSE 流式 URL */
export function buildStreamUrl(conversationId, params) {
  const query = new URLSearchParams(params)
  return `/api/chat/${conversationId}/stream?${query}`
}
