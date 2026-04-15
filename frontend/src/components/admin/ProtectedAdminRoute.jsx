import { Navigate } from 'react-router-dom'
import { useAuth } from '../../context/AuthContext'

export default function ProtectedAdminRoute({ children }) {
  const { user } = useAuth()

  if (!user) {
    return <Navigate to="/dang-nhap" replace />
  }

  if (user.vaiTro !== 'quanly') {
    return <Navigate to="/" replace />
  }

  return children
}