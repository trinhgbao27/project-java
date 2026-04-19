import api from './axios'

export const getAllNguoiDung = () => api.get('/nguoi-dung')
export const getNguoiDungById = (id) => api.get(`/nguoi-dung/${id}`)
export const createNguoiDung = (data) => api.post('/nguoi-dung', data)
export const updateNguoiDung = (id, data) => api.put(`/nguoi-dung/${id}`, data)
export const deleteNguoiDung = (id) => api.delete(`/nguoi-dung/${id}`)