import { Routes, Route, Navigate } from 'react-router-dom'
import { CartProvider } from './context/CartContext'
import { AuthProvider } from './context/AuthContext'
import ProtectedAdminRoute from './components/admin/ProtectedAdminRoute'
import ProtectedStaffRoute from './components/staff/ProtectedStaffRoute'
import Header from './components/Header'
import HomePage from './pages/HomePage'
import ProductDetailPage from './pages/ProductDetailPage'
import CartPage from './pages/CartPage'
import CheckoutPage from './pages/CheckoutPage'
import OrdersPage from './pages/OrdersPage'
import OrderDetailPage from './pages/OrderDetailPage'
import LoginPage from './pages/LoginPage'
import RegisterPage from './pages/RegisterPage'
import AdminDashboard from './pages/admin/AdminDashboard'
import AdminSanPhamPage from './pages/admin/AdminSanPhamPage'
import AdminDonHangPage from './pages/admin/AdminDonHangPage'
import AdminKhachHangPage from './pages/admin/AdminKhachHangPage'
import AdminNhanVienPage from './pages/admin/AdminNhanVienPage'
import AdminDoanhThuPage from './pages/admin/AdminDoanhThuPage'
import StaffDonHangPage from './pages/staff/StaffDonHangPage'
import StaffSanPhamPage from './pages/staff/StaffSanPhamPage'
import StaffKhachHangPage from './pages/staff/StaffKhachHangPage'

function App() {
  return (
    <AuthProvider>
      <CartProvider>
        <div className="min-h-screen bg-gray-50">
          <Routes>
            <Route path="/dang-nhap" element={<LoginPage />} />
            <Route path="/dang-ky" element={<RegisterPage />} />

            {/* Admin routes — chỉ quanly */}
            <Route path="/admin" element={<ProtectedAdminRoute><AdminDashboard /></ProtectedAdminRoute>} />
            <Route path="/admin/san-pham" element={<ProtectedAdminRoute><AdminSanPhamPage /></ProtectedAdminRoute>} />
            <Route path="/admin/don-hang" element={<ProtectedAdminRoute><AdminDonHangPage /></ProtectedAdminRoute>} />
            <Route path="/admin/khach-hang" element={<ProtectedAdminRoute><AdminKhachHangPage /></ProtectedAdminRoute>} />
            <Route path="/admin/nhan-vien" element={<ProtectedAdminRoute><AdminNhanVienPage /></ProtectedAdminRoute>} />
            <Route path="/admin/doanh-thu" element={<ProtectedAdminRoute><AdminDoanhThuPage /></ProtectedAdminRoute>} />

            {/* Staff routes — chỉ nhanvien */}
            <Route path="/nhan-vien" element={<ProtectedStaffRoute><Navigate to="/nhan-vien/don-hang" replace /></ProtectedStaffRoute>} />
            <Route path="/nhan-vien/don-hang" element={<ProtectedStaffRoute><StaffDonHangPage /></ProtectedStaffRoute>} />
            <Route path="/nhan-vien/san-pham" element={<ProtectedStaffRoute><StaffSanPhamPage /></ProtectedStaffRoute>} />
            <Route path="/nhan-vien/khach-hang" element={<ProtectedStaffRoute><StaffKhachHangPage /></ProtectedStaffRoute>} />

            {/* Customer routes */}
            <Route path="*" element={
              <>
                <Header />
                <main>
                  <Routes>
                    <Route path="/" element={<HomePage />} />
                    <Route path="/san-pham/:id" element={<ProductDetailPage />} />
                    <Route path="/gio-hang" element={<CartPage />} />
                    <Route path="/checkout" element={<CheckoutPage />} />
                    <Route path="/don-hang" element={<OrdersPage />} />
                    <Route path="/don-hang/:id" element={<OrderDetailPage />} />
                  </Routes>
                </main>
              </>
            } />
          </Routes>
        </div>
      </CartProvider>
    </AuthProvider>
  )
}

export default App