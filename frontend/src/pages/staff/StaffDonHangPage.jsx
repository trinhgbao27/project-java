import { useEffect, useState } from 'react'
import StaffLayout from '../../components/staff/StaffLayout'
import api from '../../api/axios'
import { getChiTietByDonHang } from '../../api/donHangChiTiet'
import { getSanPhamById } from '../../api/sanPham'
import { tuChoiHoanTra } from '../../api/donHang'

const trangThaiOptions = [
  { value: 'cho_thanh_toan',        label: 'Chờ thanh toán',      color: 'bg-amber-100 text-amber-700' },
  { value: 'cho_xac_nhan',          label: 'Chờ xác nhận',       color: 'bg-blue-100 text-blue-700' },
  { value: 'dang_xu_ly',            label: 'Đang xử lý',          color: 'bg-indigo-100 text-indigo-700' },
  { value: 'dang_giao',             label: 'Đang giao',            color: 'bg-purple-100 text-purple-700' },
  { value: 'hoan_thanh',            label: 'Hoàn thành',           color: 'bg-green-100 text-green-700' },
  { value: 'da_huy',                label: 'Đã hủy',               color: 'bg-red-100 text-red-700' },
  { value: 'yeu_cau_hoan_tra',      label: 'Yêu cầu hoàn trả',    color: 'bg-orange-100 text-orange-700' },
  { value: 'cho_duyet_tra_hang',    label: 'Chờ duyệt trả hàng',  color: 'bg-yellow-100 text-yellow-700' },
  { value: 'dang_hoan_hang',        label: 'Đang hoàn hàng',       color: 'bg-pink-100 text-pink-700' },
  { value: 'da_tra_hang_hoan_tien', label: 'Đã hoàn tiền',         color: 'bg-teal-100 text-teal-700' },
  { value: 'tu_choi_hoan_tra',      label: 'Từ chối hoàn trả',     color: 'bg-gray-100 text-gray-600' },
]

const buocTiepTheo = {
  cho_thanh_toan:        ['cho_xac_nhan', 'da_huy'],
  cho_xac_nhan:          ['dang_xu_ly', 'da_huy'],
  dang_xu_ly:            ['dang_giao', 'da_huy'],
  dang_giao:             ['hoan_thanh'],
  hoan_thanh:            [],
  da_huy:                [],
  // yeu_cau_hoan_tra xử lý riêng bằng 2 nút
  yeu_cau_hoan_tra:      [],
  cho_duyet_tra_hang:    ['dang_hoan_hang'],
  dang_hoan_hang:        ['da_tra_hang_hoan_tien'],
  da_tra_hang_hoan_tien: [],
  tu_choi_hoan_tra:      [],
}

export default function AdminDonHangPage() {
  const [orders, setOrders] = useState([])
  const [loading, setLoading] = useState(true)
  const [filterTrangThai, setFilterTrangThai] = useState('')
  const [searchOrderCode, setSearchOrderCode] = useState('')
  const [selectedOrder, setSelectedOrder] = useState(null)
  const [orderItems, setOrderItems] = useState([])
  const [loadingItems, setLoadingItems] = useState(false)
  const [updatingId, setUpdatingId] = useState(null)
  const [showTuChoiModal, setShowTuChoiModal] = useState(false)
  const [lyDoTuChoi, setLyDoTuChoi] = useState('')
  const [tuChoiError, setTuChoiError] = useState('')

  const fetchOrders = () => {
    setLoading(true)
    api.get('/don-hang')
      .then(res => setOrders(res.data))
      .finally(() => setLoading(false))
  }

  useEffect(() => { fetchOrders() }, [])

  const openDetail = async (order) => {
    setLoadingItems(true)
    try {
      const orderRes = await api.get(`/don-hang/${order.id}`)
      setSelectedOrder(orderRes.data)
      const res = await getChiTietByDonHang(order.id)
      const items = await Promise.all(
        res.data.map(async item => {
          const spRes = await getSanPhamById(item.sanPhamId)
          return { ...item, sanPham: spRes.data }
        })
      )
      setOrderItems(items)
    } finally {
      setLoadingItems(false)
    }
  }

  const handleCapNhatTrangThai = async (orderId, trangThai) => {
    setUpdatingId(orderId)
    try {
      await api.put(`/don-hang/${orderId}/trang-thai`, { trangThai })
      setOrders(prev => prev.map(o => o.id === orderId ? { ...o, trangThai } : o))
      setSelectedOrder(prev => ({ ...prev, trangThai }))
    } finally {
      setUpdatingId(null)
    }
  }

  const handleTuChoi = async () => {
    if (!lyDoTuChoi.trim()) { setTuChoiError('Vui lòng nhập lý do từ chối'); return }
    setUpdatingId(selectedOrder.id)
    setTuChoiError('')
    try {
      const res = await tuChoiHoanTra(selectedOrder.id, { lyDoTuChoi })
      setOrders(prev => prev.map(o => o.id === selectedOrder.id ? { ...o, trangThai: 'tu_choi_hoan_tra' } : o))
      setSelectedOrder(res.data)
      setShowTuChoiModal(false)
      setLyDoTuChoi('')
    } catch (err) {
      setTuChoiError(err?.response?.data?.error || 'Từ chối thất bại, thử lại')
    } finally {
      setUpdatingId(null)
    }
  }

  const getTrangThai = (value) =>
    trangThaiOptions.find(t => t.value === value) || { label: value, color: 'bg-gray-100 text-gray-700' }

  const filtered = orders.filter(o => {
    const matchTrangThai = filterTrangThai ? o.trangThai === filterTrangThai : true
    const shortCode = (o.id || '').slice(0, 8).toLowerCase()
    const fullCode = (o.id || '').toLowerCase()
    const keyword = searchOrderCode.trim().toLowerCase()
    const matchCode = !keyword || shortCode.includes(keyword) || fullCode.includes(keyword)
    return matchTrangThai && matchCode
  })
  const nextSteps = selectedOrder ? (buocTiepTheo[selectedOrder?.trangThai] ?? []) : []

  return (
    <StaffLayout>
      <div className="flex items-center justify-between mb-6">
        <h1 className="text-xl font-bold text-gray-800">Xử lý đơn hàng</h1>
        <span className="text-sm text-gray-500">{filtered.length} đơn hàng</span>
      </div>

      <div className="mb-4">
        <input
          type="text"
          value={searchOrderCode}
          onChange={e => setSearchOrderCode(e.target.value)}
          placeholder="Tìm theo mã đơn (8 ký tự đầu hoặc full UUID)..."
          className="w-full md:w-96 border border-gray-200 rounded-xl px-4 py-2.5 text-sm focus:outline-none focus:ring-2 focus:ring-blue-300"
        />
      </div>

      <div className="flex gap-2 mb-4 flex-wrap">
        <button onClick={() => setFilterTrangThai('')}
          className={`px-3 py-1.5 rounded-xl text-xs font-medium transition ${filterTrangThai === '' ? 'bg-gray-800 text-white' : 'bg-white border border-gray-200 text-gray-600 hover:bg-gray-50'}`}>
          Tất cả ({orders.length})
        </button>
        {trangThaiOptions.map(tt => (
          <button key={tt.value} onClick={() => setFilterTrangThai(tt.value)}
            className={`px-3 py-1.5 rounded-xl text-xs font-medium transition ${filterTrangThai === tt.value ? 'bg-gray-800 text-white' : 'bg-white border border-gray-200 text-gray-600 hover:bg-gray-50'}`}>
            {tt.label} ({orders.filter(o => o.trangThai === tt.value).length})
          </button>
        ))}
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-4">
        {/* Danh sách */}
        <div className="bg-white rounded-2xl border border-gray-100 shadow-sm overflow-hidden">
          {loading ? <div className="text-center py-16 text-gray-400">Đang tải...</div>
            : filtered.length === 0 ? <div className="text-center py-16 text-gray-400">Không có đơn hàng</div>
            : (
              <div className="divide-y divide-gray-50">
                {filtered.map(order => {
                  const tt = getTrangThai(order.trangThai)
                  const isSelected = selectedOrder?.id === order.id
                  return (
                    <div key={order.id} onClick={() => openDetail(order)}
                      className={`p-4 cursor-pointer hover:bg-gray-50 transition ${isSelected ? 'bg-blue-50 border-l-4 border-blue-500' : ''}`}>
                      <div className="flex items-center justify-between mb-1">
                        <div>
                          <p className="font-mono text-sm font-semibold text-gray-700">#{order.id.slice(0, 8).toUpperCase()}</p>
                          {order.hoTenKhachHang && <p className="text-xs text-gray-500 mt-0.5">{order.hoTenKhachHang}</p>}
                        </div>
                        <span className={`text-xs font-medium px-2 py-0.5 rounded-full ${tt.color}`}>{tt.label}</span>
                      </div>
                      <div className="flex justify-between text-xs text-gray-500 mt-1">
                        <span>{new Date(order.taoLuc).toLocaleDateString('vi-VN')}</span>
                        <span className="font-bold text-gray-700">{order.tongTien.toLocaleString('vi-VN')}₫</span>
                      </div>
                    </div>
                  )
                })}
              </div>
            )}
        </div>

        {/* Panel chi tiết */}
        <div className="bg-white rounded-2xl border border-gray-100 shadow-sm p-5 overflow-y-auto max-h-[80vh]">
          {!selectedOrder ? (
            <div className="text-center py-16 text-gray-400"><div className="text-4xl mb-2">📋</div><p className="text-sm">Chọn đơn hàng để xem chi tiết</p></div>
          ) : (
            <>
              <div className="flex items-start justify-between mb-4">
                <div>
                  <p className="text-xs text-gray-400">Mã đơn</p>
                  <p className="font-mono font-bold text-gray-800">#{selectedOrder.id.slice(0, 8).toUpperCase()}</p>
                  <p className="text-xs text-gray-400 mt-1">{new Date(selectedOrder.taoLuc).toLocaleString('vi-VN')}</p>
                </div>
                <span className={`text-xs font-medium px-3 py-1 rounded-full ${getTrangThai(selectedOrder.trangThai).color}`}>
                  {getTrangThai(selectedOrder.trangThai).label}
                </span>
              </div>

              {/* Khách hàng */}
              {(selectedOrder.hoTenKhachHang || selectedOrder.emailKhachHang) && (
                <div className="bg-gray-50 rounded-xl p-3 mb-3">
                  <p className="text-xs text-gray-500 font-medium mb-1">Khách hàng</p>
                  {selectedOrder.hoTenKhachHang && <p className="text-sm font-semibold text-gray-800">{selectedOrder.hoTenKhachHang}</p>}
                  {selectedOrder.emailKhachHang && <p className="text-xs text-gray-500">{selectedOrder.emailKhachHang}</p>}
                </div>
              )}

              {/* Giao hàng */}
              {(selectedOrder.hoTenNguoiNhan || selectedOrder.soDienThoai || selectedOrder.diaChi) && (
                <div className="bg-gray-50 rounded-xl p-3 mb-3">
                  <p className="text-xs text-gray-500 font-medium mb-2">Thông tin giao hàng</p>
                  <div className="flex flex-col gap-1 text-xs">
                    {selectedOrder.hoTenNguoiNhan && <div className="flex gap-2"><span className="text-gray-400 w-20">Người nhận</span><span className="font-medium text-gray-700">{selectedOrder.hoTenNguoiNhan}</span></div>}
                    {selectedOrder.soDienThoai && <div className="flex gap-2"><span className="text-gray-400 w-20">Điện thoại</span><span className="font-medium text-gray-700">{selectedOrder.soDienThoai}</span></div>}
                    {selectedOrder.diaChi && <div className="flex gap-2"><span className="text-gray-400 w-20">Địa chỉ</span><span className="font-medium text-gray-700">{selectedOrder.diaChi}</span></div>}
                  </div>
                </div>
              )}

              {/* Thông tin hoàn trả của khách */}
              {selectedOrder.lyDoHoanTra && (
                <div className="bg-orange-50 rounded-xl p-3 mb-3 border border-orange-100">
                  <p className="text-xs text-orange-600 font-medium mb-2">Yêu cầu hoàn trả của khách</p>
                  <div className="flex flex-col gap-1 text-xs">
                    <div className="flex gap-2"><span className="text-gray-400 w-24">Lý do</span><span className="font-medium text-gray-700">{selectedOrder.lyDoHoanTra}</span></div>
                    {selectedOrder.tenNganHang && <div className="flex gap-2"><span className="text-gray-400 w-24">Ngân hàng</span><span className="font-medium text-gray-700">{selectedOrder.tenNganHang}</span></div>}
                    {selectedOrder.soTaiKhoan && <div className="flex gap-2"><span className="text-gray-400 w-24">Số TK</span><span className="font-medium text-gray-700">{selectedOrder.soTaiKhoan}</span></div>}
                    {selectedOrder.tenChuTaiKhoan && <div className="flex gap-2"><span className="text-gray-400 w-24">Chủ TK</span><span className="font-medium text-gray-700">{selectedOrder.tenChuTaiKhoan}</span></div>}
                  </div>
                </div>
              )}

              {/* Lý do từ chối nếu có */}
              {selectedOrder.lyDoTuChoi && (
                <div className="bg-gray-100 rounded-xl p-3 mb-3 border border-gray-200">
                  <p className="text-xs text-gray-500 font-medium mb-1">Lý do từ chối</p>
                  <p className="text-xs text-gray-700">{selectedOrder.lyDoTuChoi}</p>
                </div>
              )}

              {/* Cập nhật trạng thái */}
              <div className="mb-4">
                <p className="text-xs text-gray-500 mb-2 font-medium">Cập nhật trạng thái</p>

                {/* Luồng đặc biệt: yeu_cau_hoan_tra — 2 nút */}
                {selectedOrder.trangThai === 'yeu_cau_hoan_tra' ? (
                  <div className="flex gap-2">
                    <button
                      onClick={() => handleCapNhatTrangThai(selectedOrder.id, 'cho_duyet_tra_hang')}
                      disabled={updatingId === selectedOrder.id}
                      className="flex-1 text-xs py-2 px-3 rounded-xl border border-green-300 text-green-700 bg-green-50 hover:bg-green-100 transition disabled:opacity-50"
                    >
                      ✓ Xác nhận hoàn trả
                    </button>
                    <button
                      onClick={() => { setLyDoTuChoi(''); setTuChoiError(''); setShowTuChoiModal(true) }}
                      disabled={updatingId === selectedOrder.id}
                      className="flex-1 text-xs py-2 px-3 rounded-xl border border-red-300 text-red-600 bg-red-50 hover:bg-red-100 transition disabled:opacity-50"
                    >
                      ✗ Từ chối
                    </button>
                  </div>
                ) : nextSteps.length > 0 ? (
                  <div className="flex gap-2 flex-wrap">
                    {nextSteps.map(step => (
                      <button key={step}
                        onClick={() => handleCapNhatTrangThai(selectedOrder.id, step)}
                        disabled={updatingId === selectedOrder.id}
                        className="text-xs py-2 px-4 rounded-xl border border-gray-200 text-gray-700 hover:bg-gray-50 transition disabled:opacity-50"
                      >
                        {updatingId === selectedOrder.id ? '...' : `→ ${getTrangThai(step).label}`}
                      </button>
                    ))}
                  </div>
                ) : (
                  <p className="text-xs text-gray-400 italic">Đơn này đã kết thúc.</p>
                )}
              </div>

              {/* Sản phẩm */}
              <div className="border-t border-gray-100 pt-4">
                <p className="text-xs text-gray-500 mb-3 font-medium">Sản phẩm trong đơn</p>
                {loadingItems ? <p className="text-center text-gray-400 text-sm py-4">Đang tải...</p> : (
                  <div className="flex flex-col gap-2">
                    {orderItems.map(item => (
                      <div key={item.id} className="flex justify-between items-center text-sm">
                        <div className="flex items-center gap-2">
                          <span className="text-lg">👓</span>
                          <div>
                            <p className="font-medium text-gray-800">{item.sanPham?.ten}</p>
                            <p className="text-xs text-gray-400">x{item.soLuong}</p>
                          </div>
                        </div>
                        <p className="font-bold text-gray-700">{(item.giaBan * item.soLuong).toLocaleString('vi-VN')}₫</p>
                      </div>
                    ))}
                  </div>
                )}
                <div className="border-t border-gray-100 pt-3 mt-3 flex justify-between font-bold text-gray-800">
                  <span>Tổng cộng</span>
                  <span className="text-blue-600">{selectedOrder.tongTien.toLocaleString('vi-VN')}₫</span>
                </div>
              </div>
            </>
          )}
        </div>
      </div>

      {/* Modal từ chối */}
      {showTuChoiModal && (
        <div className="fixed inset-0 bg-black/40 flex items-center justify-center z-50 px-4">
          <div className="bg-white rounded-2xl shadow-xl w-full max-w-md p-6">
            <h2 className="text-lg font-bold text-gray-800 mb-1">Từ chối yêu cầu hoàn trả</h2>
            <p className="text-xs text-gray-400 mb-4">Khách hàng sẽ thấy lý do này.</p>
            <textarea
              value={lyDoTuChoi}
              onChange={e => setLyDoTuChoi(e.target.value)}
              placeholder="Nhập lý do từ chối..."
              rows={4}
              className="w-full border border-gray-200 rounded-xl px-4 py-2.5 text-sm focus:outline-none focus:ring-2 focus:ring-red-300 resize-none"
            />
            {tuChoiError && <p className="mt-2 text-sm text-red-500 bg-red-50 px-3 py-2 rounded-xl">{tuChoiError}</p>}
            <div className="flex gap-2 mt-4">
              <button onClick={() => setShowTuChoiModal(false)} className="flex-1 border border-gray-200 text-gray-600 py-2.5 rounded-xl text-sm hover:bg-gray-50 transition">Hủy</button>
              <button onClick={handleTuChoi} disabled={updatingId === selectedOrder?.id}
                className="flex-1 bg-red-500 text-white py-2.5 rounded-xl text-sm font-medium hover:bg-red-600 transition disabled:opacity-60">
                {updatingId === selectedOrder?.id ? '...' : 'Xác nhận từ chối'}
              </button>
            </div>
          </div>
        </div>
      )}
    </StaffLayout>
  )
}