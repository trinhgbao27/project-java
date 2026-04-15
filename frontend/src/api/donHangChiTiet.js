import api from './axios'

export const createDonHangChiTiet = (data) => api.post('/don-hang-chi-tiet', data)
export const getChiTietByDonHang = (donHangId) => api.get(`/don-hang-chi-tiet/don-hang/${donHangId}`)