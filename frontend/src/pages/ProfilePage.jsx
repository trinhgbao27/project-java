import { useState, useEffect } from 'react'
import { useNavigate } from 'react-router-dom'
import { useAuth } from '../context/AuthContext'
import { getNguoiDungById, updateNguoiDung } from '../api/nguoiDung'

export default function ProfilePage() {
  const { user, login } = useAuth()
  const navigate = useNavigate()
  const [loading, setLoading] = useState(true)
  const [saving, setSaving] = useState(false)
  const [success, setSuccess] = useState(false)
  const [error, setError] = useState('')
  const [form, setForm] = useState({ hoTen: '', email: '' })

  // Khi vào trang, lấy thông tin mới nhất từ server
  useEffect(() => {
    if (!user) { setLoading(false); return }
    getNguoiDungById(user.id)
      .then(res => {
        setForm({ hoTen: res.data.hoTen || '', email: res.data.email || '' })
      })
      .catch(() => setError('Không tải được thông tin tài khoản'))
      .finally(() => setLoading(false))
  }, [user])

  const handleChange = (e) => {
    setForm(prev => ({ ...prev, [e.target.name]: e.target.value }))
    setSuccess(false)
    setError('')
  }

  const handleSubmit = async (e) => {
    e.preventDefault()
    if (!form.hoTen.trim() || !form.email.trim()) {
      setError('Vui lòng điền đầy đủ họ tên và email')
      return
    }
    setSaving(true)
    setError('')
    try {
      const res = await updateNguoiDung(user.id, {
        ...user,
        hoTen: form.hoTen.trim(),
        email: form.email.trim(),
      })
      // Cập nhật lại thông tin user đang đăng nhập
      login(res.data)
      setSuccess(true)
    } catch (err) {
      setError(err.response?.data?.error || 'Lưu thất bại, vui lòng thử lại')
    } finally {
      setSaving(false)
    }
  }

  // Chưa đăng nhập
  if (!user) {
    return (
      <div className="max-w-2xl mx-auto px-4 py-20 text-center">
        <div className="text-5xl mb-4">🔒</div>
        <h2 className="text-xl font-semibold text-gray-700">Chưa đăng nhập</h2>
        <p className="text-gray-400 mt-2 mb-6">Vui lòng đăng nhập để xem thông tin tài khoản</p>
        <button
          onClick={() => navigate('/dang-nhap')}
          className="bg-blue-600 text-white px-6 py-2.5 rounded-xl hover:bg-blue-700 transition"
        >
          Đăng nhập
        </button>
      </div>
    )
  }

  return (
    <div className="max-w-xl mx-auto px-4 py-10">
      <h1 className="text-2xl font-bold text-gray-800 mb-6">👤 Thông tin tài khoản</h1>

      {loading ? (
        <div className="text-center py-20 text-gray-400">Đang tải...</div>
      ) : (
        <div className="bg-white rounded-2xl border border-gray-100 shadow-sm p-6">

          {/* Hiển thị vai trò */}
          <div className="mb-5 flex items-center gap-2">
            <span className="text-sm text-gray-500">Vai trò:</span>
            <span className="text-xs font-medium bg-blue-100 text-blue-700 px-3 py-1 rounded-full">
              {user.vaiTro === 'khachhang' ? '🛍️ Khách hàng'
                : user.vaiTro === 'quanly' ? '🔧 Quản lý'
                : '👷 Nhân viên'}
            </span>
          </div>

          <form onSubmit={handleSubmit} className="flex flex-col gap-4">
            {/* Họ tên */}
            <div>
              <label className="text-sm text-gray-600 mb-1 block font-medium">Họ tên</label>
              <input
                name="hoTen"
                value={form.hoTen}
                onChange={handleChange}
                placeholder="Nguyễn Văn A"
                className="w-full border border-gray-200 rounded-xl px-4 py-2.5 text-sm focus:outline-none focus:ring-2 focus:ring-blue-300"
              />
            </div>

            {/* Email */}
            <div>
              <label className="text-sm text-gray-600 mb-1 block font-medium">Email</label>
              <input
                name="email"
                type="email"
                value={form.email}
                onChange={handleChange}
                placeholder="example@gmail.com"
                className="w-full border border-gray-200 rounded-xl px-4 py-2.5 text-sm focus:outline-none focus:ring-2 focus:ring-blue-300"
              />
            </div>

            {/* Thông báo lỗi */}
            {error && (
              <p className="text-sm text-red-500 bg-red-50 px-3 py-2 rounded-xl">{error}</p>
            )}

            {/* Thông báo thành công */}
            {success && (
              <p className="text-sm text-green-600 bg-green-50 px-3 py-2 rounded-xl">
                ✓ Cập nhật thông tin thành công!
              </p>
            )}

            {/* Nút lưu */}
            <button
              type="submit"
              disabled={saving}
              className="w-full bg-blue-600 text-white py-2.5 rounded-xl font-medium hover:bg-blue-700 transition disabled:opacity-60"
            >
              {saving ? 'Đang lưu...' : 'Lưu thay đổi'}
            </button>
          </form>
        </div>
      )}
    </div>
  )
}
