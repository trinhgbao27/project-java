import api from './axios'

export const createDonHang = (data) => api.post('/don-hang', data)
export const getDonHangByNguoiDung = (nguoiDungId) => api.get(`/don-hang/nguoi-dung/${nguoiDungId}`)
export const getDonHangById = (id) => api.get(`/don-hang/${id}`)
export const huyDonHang = (id, nguoiDungId) => api.put(`/don-hang/${id}/huy?nguoiDungId=${nguoiDungId}`)
export const yeuCauHoanTra = (id, nguoiDungId, data) =>
  api.put(`/don-hang/${id}/hoan-tra?nguoiDungId=${nguoiDungId}`, data)
export const tuChoiHoanTra = (id, data) => api.put(`/don-hang/${id}/tu-choi-hoan-tra`, data)