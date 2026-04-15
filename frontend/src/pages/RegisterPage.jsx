import { useState } from 'react'
import { useNavigate, Link } from 'react-router-dom'
import { createNguoiDung } from '../api/nguoiDung'
import { useAuth } from '../context/AuthContext'

export default function RegisterPage() {
  const navigate = useNavigate()
  const { login } = useAuth()
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState('')
  const [form, setForm] = useState({
    hoTen: '',
    email: '',
  })

  const handleChange = (e) => {
    setForm(prev => ({ ...prev, [e.target.name]: e.target.value }))
  }

  const handleSubmit = async (e) => {
    e.preventDefault()
    if (!form.hoTen || !form.email) {
      setError('Vui lòng điền đầy đủ thông tin')
      return
    }
    setLoading(true)
    setError('')
    try {
      const res = await createNguoiDung({
        hoTen: form.hoTen,
        email: form.email,
        vaiTro: 'khachhang',
      })
      login(res.data)
      navigate('/')
    } catch (err) {
      const msg = err.response?.data?.error || 'Đăng ký thất bại'
      setError(msg.includes('Email đã tồn tại') ? 'Email này đã được đăng ký' : msg)
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="min-h-screen bg-gray-50 flex items-center justify-center px-4">
      <div className="bg-white rounded-2xl shadow-sm border border-gray-100 p-8 w-full max-w-md">
        <div className="text-center mb-6">
          <div className="text-4xl mb-2">👓</div>
          <h1 className="text-2xl font-bold text-gray-800">Tạo tài khoản</h1>
          <p className="text-gray-500 text-sm mt-1">Đăng ký để mua kính online</p>
        </div>

        <form onSubmit={handleSubmit} className="flex flex-col gap-4">
          <div>
            <label className="text-sm text-gray-600 mb-1 block">Họ tên</label>
            <input
              name="hoTen"
              value={form.hoTen}
              onChange={handleChange}
              placeholder="Nguyễn Văn A"
              className="w-full border border-gray-200 rounded-xl px-4 py-2.5 text-sm focus:outline-none focus:ring-2 focus:ring-blue-300"
            />
          </div>
          <div>
            <label className="text-sm text-gray-600 mb-1 block">Email</label>
            <input
              name="email"
              type="email"
              value={form.email}
              onChange={handleChange}
              placeholder="example@gmail.com"
              className="w-full border border-gray-200 rounded-xl px-4 py-2.5 text-sm focus:outline-none focus:ring-2 focus:ring-blue-300"
            />
          </div>

          {error && (
            <p className="text-sm text-red-500 bg-red-50 px-3 py-2 rounded-xl">{error}</p>
          )}

          <button
            type="submit"
            disabled={loading}
            className="w-full bg-blue-600 text-white py-2.5 rounded-xl font-medium hover:bg-blue-700 transition disabled:opacity-60"
          >
            {loading ? 'Đang đăng ký...' : 'Đăng ký'}
          </button>
        </form>

        <p className="text-center text-sm text-gray-500 mt-4">
          Đã có tài khoản?{' '}
          <Link to="/dang-nhap" className="text-blue-600 hover:underline font-medium">
            Đăng nhập
          </Link>
        </p>
      </div>
    </div>
  )
}