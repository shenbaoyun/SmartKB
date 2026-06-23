import request from './request'

export function createConversation(data) {
  return request.post('/conversations', data)
}

export function listConversations() {
  return request.get('/conversations')
}

export function getConversation(id) {
  return request.get(`/conversations/${id}`)
}

export function updateConversation(id, data) {
  return request.put(`/conversations/${id}`, data)
}

export function deleteConversation(id) {
  return request.delete(`/conversations/${id}`)
}

export function getMessages(conversationId) {
  return request.get(`/conversations/${conversationId}/messages`)
}
