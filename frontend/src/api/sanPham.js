import api from './axios'

export const getAllSanPham = () => api.get('/san-pham')
export const getSanPhamById = (id) => api.get(`/san-pham/${id}`)