import request from './request'

export function listPrompts() {
  return request.get('/prompts')
}

export function getPrompt(id) {
  return request.get(`/prompts/${id}`)
}

export function matchPrompt(message) {
  return request.get('/prompts/match', { params: { message } })
}

export function createPrompt(data) {
  return request.post('/prompts', data)
}

export function updatePrompt(id, data) {
  return request.put(`/prompts/${id}`, data)
}

export function deletePrompt(id) {
  return request.delete(`/prompts/${id}`)
}
