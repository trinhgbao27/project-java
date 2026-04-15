import { useEffect, useState } from 'react'
import AdminLayout from '../../components/admin/AdminLayout'
import { getAllSanPham } from '../../api/sanPham'
import { getAllNguoiDung } from '../../api/nguoiDung'
import api from '../../api/axios'

export default function AdminDashboard() {
  const [stats, setStats] = useState({
    tongSanPham: 0,
    tongDonHang: 0,
    tongNguoiDung: 0,
    doanhThu: 0,
  })
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    Promise.all([
      getAllSanPham(),
      getAllNguoiDung(),
      api.get('/don-hang'),
    ]).then(([spRes, ndRes, dhRes]) => {
      const donHangs = dhRes.data
      const doanhThu = donHangs
        .filter(d => d.trangThai === 'hoan_thanh')
        .reduce((sum, d) => sum + d.tongTien, 0)
      setStats({
        tongSanPham: spRes.data.length,
        tongNguoiDung: ndRes.data.length,
        tongDonHang: donHangs.length,
        doanhThu,
      })
    }).finally(() => setLoading(false))
  }, [])

  const cards = [
    { label: 'Sản phẩm', value: stats.tongSanPham, icon: '👓', color: 'bg-blue-50 text-blue-600' },
    { label: 'Đơn hàng', value: stats.tongDonHang, icon: '📦', color: 'bg-purple-50 text-purple-600' },
    { label: 'Khách hàng', value: stats.tongNguoiDung, icon: '👤', color: 'bg-green-50 text-green-600' },
    {
      label: 'Doanh thu',
      value: stats.doanhThu.toLocaleString('vi-VN') + '₫',
      icon: '💰',
      color: 'bg-yellow-50 text-yellow-600',
    },
  ]

  return (
    <AdminLayout>
      <h1 className="text-xl font-bold text-gray-800 mb-6">Dashboard</h1>

      {loading ? (
        <div className="text-center py-20 text-gray-400">Đang tải...</div>
      ) : (
        <div className="grid grid-cols-4 gap-4">
          {cards.map(card => (
            <div key={card.label} className="bg-white rounded-2xl border border-gray-100 shadow-sm p-5">
              <div className={`w-10 h-10 rounded-xl flex items-center justify-center text-xl mb-3 ${card.color}`}>
                {card.icon}
              </div>
              <p className="text-2xl font-bold text-gray-800">{card.value}</p>
              <p className="text-sm text-gray-500 mt-0.5">{card.label}</p>
            </div>
          ))}
        </div>
      )}
    </AdminLayout>
  )
}