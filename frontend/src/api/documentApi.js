import request from './request'

export function uploadDocument(file) {
  const formData = new FormData()
  formData.append('file', file)
  return request.post('/documents', formData, {
    headers: { 'Content-Type': 'multipart/form-data' }
  })
}

export function listDocuments() {
  return request.get('/documents')
}

export function deleteDocument(id) {
  return request.delete(`/documents/${id}`)
}