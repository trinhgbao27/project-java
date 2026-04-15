import api from './axios'

export const createDonKinh = (data) => api.post('/don-kinh', data)
export const getDonKinhByNguoiDung = (nguoiDungId) => api.get(`/don-kinh/nguoi-dung/${nguoiDungId}`)
export const getDonKinhById = (id) => api.get(`/don-kinh/${id}`)