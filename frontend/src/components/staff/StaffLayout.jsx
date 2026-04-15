import { NavLink, useNavigate } from 'react-router-dom'
import { useAuth } from '../../context/AuthContext'

const navItems = [
  { to: '/nhan-vien/don-hang', label: 'Đơn hàng', icon: '📦' },
  { to: '/nhan-vien/san-pham', label: 'Sản phẩm', icon: '👓' },
  { to: '/nhan-vien/khach-hang', label: 'Khách hàng', icon: '👤' },
]

export default function StaffLayout({ children }) {
  const { user, logout } = useAuth()
  const navigate = useNavigate()

  return (
    <div className="min-h-screen flex bg-gray-100">
      <aside className="w-56 bg-gray-900 text-white flex flex-col fixed h-full">
        <div className="px-5 py-5 border-b border-gray-700">
          <p className="text-lg font-bold text-white">👓 BánKính</p>
          <p className="text-xs text-gray-400 mt-0.5">Nhân viên</p>
        </div>

        <nav className="flex-1 px-3 py-4 flex flex-col gap-1">
          {navItems.map(item => (
            <NavLink
              key={item.to}
              to={item.to}
              className={({ isActive }) =>
                `flex items-center gap-3 px-3 py-2.5 rounded-xl text-sm font-medium transition ${
                  isActive
                    ? 'bg-blue-600 text-white'
                    : 'text-gray-400 hover:bg-gray-800 hover:text-white'
                }`
              }
            >
              <span>{item.icon}</span>
              {item.label}
            </NavLink>
          ))}
        </nav>

        <div className="px-4 py-4 border-t border-gray-700">
          <p className="text-xs text-gray-400 mb-1">Đăng nhập với</p>
          <p className="text-sm font-medium text-white truncate">{user?.hoTen}</p>
          <button
            onClick={() => { logout(); navigate('/') }}
            className="mt-2 text-xs text-gray-500 hover:text-red-400 transition"
          >
            Đăng xuất
          </button>
        </div>
      </aside>

      <div className="ml-56 flex-1 flex flex-col min-h-screen">
        <header className="bg-white border-b border-gray-200 px-6 py-3 flex items-center justify-between">
          <p className="text-sm text-gray-500">
            Xin chào, <span className="font-semibold text-gray-800">{user?.hoTen}</span>
          </p>
          <button
            onClick={() => navigate('/')}
            className="text-sm text-blue-600 hover:underline"
          >
            ← Về trang khách hàng
          </button>
        </header>
        <main className="flex-1 p-6">
          {children}
        </main>
      </div>
    </div>
  )
}