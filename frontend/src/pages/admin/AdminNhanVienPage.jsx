import { useEffect, useState } from 'react'
import AdminLayout from '../../components/admin/AdminLayout'
import { getAllNguoiDung, createNguoiDung, deleteNguoiDung } from '../../api/nguoiDung'

const emptyForm = { hoTen: '', email: '' }

export default function AdminNhanVienPage() {
  const [list, setList] = useState([])
  const [loading, setLoading] = useState(true)
  const [showModal, setShowModal] = useState(false)
  const [form, setForm] = useState(emptyForm)
  const [saving, setSaving] = useState(false)
  const [error, setError] = useState('')
  const [confirmDeleteId, setConfirmDeleteId] = useState(null)
  const [search, setSearch] = useState('')

  const fetchList = () => {
    getAllNguoiDung()
      .then(res => setList(res.data.filter(u => u.vaiTro === 'nhanvien')))
      .finally(() => setLoading(false))
  }

  useEffect(() => { fetchList() }, [])

  const openCreate = () => {
    setForm(emptyForm)
    setError('')
    setShowModal(true)
  }

  const handleSave = async () => {
    if (!form.hoTen || !form.email) {
      setError('Vui lòng điền đầy đủ thông tin')
      return
    }
    setSaving(true)
    setError('')
    try {
      await createNguoiDung({ hoTen: form.hoTen, email: form.email, vaiTro: 'nhanvien' })
      setShowModal(false)
      fetchList()
    } catch (err) {
      setError(err.response?.data?.message || 'Tạo tài khoản thất bại')
    } finally {
      setSaving(false)
    }
  }

  const handleDelete = async (id) => {
    try {
      await deleteNguoiDung(id)
      setConfirmDeleteId(null)
      fetchList()
    } catch {
      alert('Xóa thất bại')
    }
  }

  const filtered = list.filter(u =>
    u.hoTen.toLowerCase().includes(search.toLowerCase()) ||
    u.email.toLowerCase().includes(search.toLowerCase())
  )

  return (
    <AdminLayout>
      <div className="flex items-center justify-between mb-6">
        <h1 className="text-xl font-bold text-gray-800">Nhân viên</h1>
        <button
          onClick={openCreate}
          className="bg-blue-600 text-white px-4 py-2 rounded-xl text-sm font-medium hover:bg-blue-700 transition"
        >
          + Tạo tài khoản
        </button>
      </div>

      <div className="bg-white rounded-2xl border border-gray-100 shadow-sm overflow-hidden">
        <div className="p-4 border-b border-gray-100">
          <input
            type="text"
            placeholder="Tìm theo tên hoặc email..."
            value={search}
            onChange={e => setSearch(e.target.value)}
            className="w-full max-w-xs border border-gray-200 rounded-xl px-4 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-blue-300"
          />
        </div>

        {loading ? (
          <div className="text-center py-16 text-gray-400">Đang tải...</div>
        ) : (
          <table className="w-full text-sm">
            <thead className="bg-gray-50 text-gray-500 text-xs uppercase">
              <tr>
                <th className="px-5 py-3 text-left">Họ tên</th>
                <th className="px-5 py-3 text-left">Email</th>
                <th className="px-5 py-3 text-center">Thao tác</th>
              </tr>
            </thead>
            <tbody className="divide-y divide-gray-50">
              {filtered.length === 0 ? (
                <tr>
                  <td colSpan={3} className="text-center py-12 text-gray-400">Không có nhân viên</td>
                </tr>
              ) : filtered.map(u => (
                <tr key={u.id} className="hover:bg-gray-50 transition">
                  <td className="px-5 py-3.5 font-medium text-gray-800">{u.hoTen}</td>
                  <td className="px-5 py-3.5 text-gray-500">{u.email}</td>
                  <td className="px-5 py-3.5 text-center">
                    <button
                      onClick={() => setConfirmDeleteId(u.id)}
                      className="text-xs bg-gray-100 hover:bg-red-100 hover:text-red-600 text-gray-600 px-3 py-1.5 rounded-lg transition"
                    >
                      Xóa
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>

      {/* Modal tạo tài khoản */}
      {showModal && (
        <div className="fixed inset-0 bg-black/40 flex items-center justify-center z-50 px-4">
          <div className="bg-white rounded-2xl shadow-xl w-full max-w-md p-6">
            <h2 className="text-lg font-bold text-gray-800 mb-4">Tạo tài khoản nhân viên</h2>
            <div className="flex flex-col gap-3">
              <div>
                <label className="text-sm text-gray-600 mb-1 block">Họ tên</label>
                <input
                  value={form.hoTen}
                  onChange={e => setForm(p => ({ ...p, hoTen: e.target.value }))}
                  placeholder="Nguyễn Văn A"
                  className="w-full border border-gray-200 rounded-xl px-4 py-2.5 text-sm focus:outline-none focus:ring-2 focus:ring-blue-300"
                />
              </div>
              <div>
                <label className="text-sm text-gray-600 mb-1 block">Email</label>
                <input
                  type="email"
                  value={form.email}
                  onChange={e => setForm(p => ({ ...p, email: e.target.value }))}
                  placeholder="nhanvien@bankinh.vn"
                  className="w-full border border-gray-200 rounded-xl px-4 py-2.5 text-sm focus:outline-none focus:ring-2 focus:ring-blue-300"
                />
              </div>
            </div>

            {error && <p className="mt-3 text-sm text-red-500 bg-red-50 px-3 py-2 rounded-xl">{error}</p>}

            <div className="flex gap-2 mt-5">
              <button
                onClick={() => setShowModal(false)}
                className="flex-1 border border-gray-200 text-gray-600 py-2.5 rounded-xl text-sm hover:bg-gray-50 transition"
              >
                Hủy
              </button>
              <button
                onClick={handleSave}
                disabled={saving}
                className="flex-1 bg-blue-600 text-white py-2.5 rounded-xl text-sm font-medium hover:bg-blue-700 transition disabled:opacity-60"
              >
                {saving ? 'Đang tạo...' : 'Tạo tài khoản'}
              </button>
            </div>
          </div>
        </div>
      )}

      {/* Confirm xóa */}
      {confirmDeleteId && (
        <div className="fixed inset-0 bg-black/40 flex items-center justify-center z-50 px-4">
          <div className="bg-white rounded-2xl shadow-xl w-full max-w-sm p-6 text-center">
            <div className="text-4xl mb-3">🗑️</div>
            <h2 className="text-lg font-bold text-gray-800 mb-2">Xác nhận xóa</h2>
            <p className="text-sm text-gray-500 mb-5">Tài khoản nhân viên sẽ bị xóa vĩnh viễn.</p>
            <div className="flex gap-2">
              <button
                onClick={() => setConfirmDeleteId(null)}
                className="flex-1 border border-gray-200 text-gray-600 py-2.5 rounded-xl text-sm hover:bg-gray-50 transition"
              >
                Hủy
              </button>
              <button
                onClick={() => handleDelete(confirmDeleteId)}
                className="flex-1 bg-red-500 text-white py-2.5 rounded-xl text-sm font-medium hover:bg-red-600 transition"
              >
                Xóa
              </button>
            </div>
          </div>
        </div>
      )}
    </AdminLayout>
  )
}