import { Navigate } from 'react-router-dom'
import { useAuth } from '../../context/AuthContext'

export default function ProtectedStaffRoute({ children }) {
  const { user } = useAuth()

  if (!user) {
    return <Navigate to="/dang-nhap" replace />
  }

  if (user.vaiTro !== 'nhanvien') {
    return <Navigate to="/" replace />
  }

  return children
}