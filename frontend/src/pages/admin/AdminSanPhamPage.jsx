import { useEffect, useState } from 'react'
import AdminLayout from '../../components/admin/AdminLayout'
import { getAllSanPham, getSanPhamById } from '../../api/sanPham'
import api from '../../api/axios'

const loaiOptions = [
  { value: 'gong', label: 'Gọng kính' },
  { value: 'trong', label: 'Tròng kính' },
  { value: 'phukien', label: 'Phụ kiện' },
]

const loaiColor = {
  gong: 'bg-blue-100 text-blue-700',
  trong: 'bg-green-100 text-green-700',
  phukien: 'bg-purple-100 text-purple-700',
}

const emptyForm = { ten: '', loai: 'gong', gia: '', soLuongTon: '' }

export default function AdminSanPhamPage() {
  const [products, setProducts] = useState([])
  const [loading, setLoading] = useState(true)
  const [showModal, setShowModal] = useState(false)
  const [editingId, setEditingId] = useState(null)
  const [form, setForm] = useState(emptyForm)
  const [saving, setSaving] = useState(false)
  const [error, setError] = useState('')
  const [search, setSearch] = useState('')
  const [confirmDeleteId, setConfirmDeleteId] = useState(null)
  const [showImageModal, setShowImageModal] = useState(false)
  const [imageProduct, setImageProduct] = useState(null)
  const [imageForm, setImageForm] = useState({ hinhAnhA: '', hinhAnhB: '' })
  const [imageSaving, setImageSaving] = useState(false)
  const [imageError, setImageError] = useState('')

  const fetchProducts = () => {
    getAllSanPham()
      .then(res => setProducts(res.data))
      .finally(() => setLoading(false))
  }

  useEffect(() => { fetchProducts() }, [])

  const openCreate = () => {
    setEditingId(null)
    setForm(emptyForm)
    setError('')
    setShowModal(true)
  }

  const openEdit = (product) => {
    setEditingId(product.id)
    setForm({
      ten: product.ten,
      loai: product.loai,
      gia: String(product.gia),
      soLuongTon: String(product.soLuongTon),
    })
    setError('')
    setShowModal(true)
  }

  const handleSave = async () => {
    if (!form.ten || !form.gia || form.soLuongTon === '') {
      setError('Vui lòng điền đầy đủ thông tin')
      return
    }
    setSaving(true)
    setError('')
    try {
      const payload = {
        ten: form.ten,
        loai: form.loai,
        gia: parseFloat(form.gia),
        soLuongTon: parseInt(form.soLuongTon),
      }
      if (editingId) {
        await api.put(`/san-pham/${editingId}`, payload)
      } else {
        await api.post('/san-pham', payload)
      }
      setShowModal(false)
      fetchProducts()
    } catch (err) {
      setError(err.response?.data?.error || 'Lưu thất bại')
    } finally {
      setSaving(false)
    }
  }

  const handleDelete = async (id) => {
    try {
      await api.delete(`/san-pham/${id}`)
      setConfirmDeleteId(null)
      fetchProducts()
    } catch {
      alert('Xóa thất bại')
    }
  }

  const openImageModal = async (product) => {
    setImageError('')
    try {
      const res = await getSanPhamById(product.id)
      const p = res.data
      setImageProduct(p)
      setImageForm({
        hinhAnhA: p.hinhAnhA || '',
        hinhAnhB: p.hinhAnhB || '',
      })
      setShowImageModal(true)
    } catch {
      setImageError('Không tải được thông tin ảnh sản phẩm')
    }
  }

  const handleSaveImages = async () => {
    if (!imageProduct) return
    const hinhAnhA = imageForm.hinhAnhA.trim()
    const hinhAnhB = imageForm.hinhAnhB.trim()
    if (hinhAnhB && !hinhAnhA) {
      setImageError('Vui lòng nhập Ảnh 1 trước khi thêm Ảnh 2')
      return
    }
    setImageSaving(true)
    setImageError('')
    try {
      await api.put(`/san-pham/${imageProduct.id}`, {
        ten: imageProduct.ten,
        loai: imageProduct.loai,
        gia: imageProduct.gia,
        soLuongTon: imageProduct.soLuongTon,
        hinhAnhA,
        hinhAnhB,
      })
      setShowImageModal(false)
      setImageProduct(null)
      setImageForm({ hinhAnhA: '', hinhAnhB: '' })
      fetchProducts()
    } catch (err) {
      setImageError(err.response?.data?.error || 'Lưu ảnh thất bại')
    } finally {
      setImageSaving(false)
    }
  }

  const filtered = products.filter(p =>
    p.ten.toLowerCase().includes(search.toLowerCase())
  )

  return (
    <AdminLayout>
      <div className="flex items-center justify-between mb-6">
        <h1 className="text-xl font-bold text-gray-800">Quản lý sản phẩm</h1>
        <button
          onClick={openCreate}
          className="bg-blue-600 text-white px-4 py-2 rounded-xl text-sm font-medium hover:bg-blue-700 transition flex items-center gap-2"
        >
          + Thêm sản phẩm
        </button>
      </div>

      <div className="bg-white rounded-2xl border border-gray-100 shadow-sm overflow-hidden">
        <div className="p-4 border-b border-gray-100">
          <input
            type="text"
            placeholder="Tìm kiếm sản phẩm..."
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
                <th className="px-5 py-3 text-left">Tên sản phẩm</th>
                <th className="px-5 py-3 text-left">Loại</th>
                <th className="px-5 py-3 text-right">Giá</th>
                <th className="px-5 py-3 text-right">Tồn kho</th>
                <th className="px-5 py-3 text-center">Thao tác</th>
              </tr>
            </thead>
            <tbody className="divide-y divide-gray-50">
              {filtered.length === 0 ? (
                <tr>
                  <td colSpan={5} className="text-center py-12 text-gray-400">Không có sản phẩm</td>
                </tr>
              ) : filtered.map(p => (
                <tr key={p.id} className="hover:bg-gray-50 transition">
                  <td className="px-5 py-3.5 font-medium text-gray-800">{p.ten}</td>
                  <td className="px-5 py-3.5">
                    <span className={`text-xs font-medium px-2 py-0.5 rounded-full ${loaiColor[p.loai]}`}>
                      {loaiOptions.find(o => o.value === p.loai)?.label}
                    </span>
                  </td>
                  <td className="px-5 py-3.5 text-right font-medium">{p.gia.toLocaleString('vi-VN')}₫</td>
                  <td className="px-5 py-3.5 text-right">
                    <span className={p.soLuongTon === 0 ? 'text-red-500 font-medium' : 'text-gray-700'}>
                      {p.soLuongTon}
                    </span>
                  </td>
                  <td className="px-5 py-3.5 text-center">
                    <div className="flex items-center justify-center gap-2">
                      <button
                        onClick={() => openEdit(p)}
                        className="text-xs bg-gray-100 hover:bg-blue-100 hover:text-blue-600 text-gray-600 px-3 py-1.5 rounded-lg transition"
                      >
                        Sửa
                      </button>
                      <button
                        onClick={() => setConfirmDeleteId(p.id)}
                        className="text-xs bg-gray-100 hover:bg-red-100 hover:text-red-600 text-gray-600 px-3 py-1.5 rounded-lg transition"
                      >
                        Xóa
                      </button>
                      <button
                        onClick={() => openImageModal(p)}
                        className="text-xs bg-gray-100 hover:bg-emerald-100 hover:text-emerald-700 text-gray-600 px-3 py-1.5 rounded-lg transition"
                      >
                        Thêm ảnh
                      </button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>

      {showModal && (
        <div className="fixed inset-0 bg-black/40 flex items-center justify-center z-50 px-4">
          <div className="bg-white rounded-2xl shadow-xl w-full max-w-md p-6">
            <h2 className="text-lg font-bold text-gray-800 mb-4">
              {editingId ? 'Chỉnh sửa sản phẩm' : 'Thêm sản phẩm mới'}
            </h2>
            <div className="flex flex-col gap-3">
              <div>
                <label className="text-sm text-gray-600 mb-1 block">Tên sản phẩm</label>
                <input
                  value={form.ten}
                  onChange={e => setForm(p => ({ ...p, ten: e.target.value }))}
                  placeholder="Gọng kính titan..."
                  className="w-full border border-gray-200 rounded-xl px-4 py-2.5 text-sm focus:outline-none focus:ring-2 focus:ring-blue-300"
                />
              </div>
              <div>
                <label className="text-sm text-gray-600 mb-1 block">Loại</label>
                <select
                  value={form.loai}
                  onChange={e => setForm(p => ({ ...p, loai: e.target.value }))}
                  className="w-full border border-gray-200 rounded-xl px-4 py-2.5 text-sm focus:outline-none focus:ring-2 focus:ring-blue-300"
                >
                  {loaiOptions.map(o => (
                    <option key={o.value} value={o.value}>{o.label}</option>
                  ))}
                </select>
              </div>
              <div className="grid grid-cols-2 gap-3">
                <div>
                  <label className="text-sm text-gray-600 mb-1 block">Giá (₫)</label>
                  <input
                    type="number"
                    value={form.gia}
                    onChange={e => setForm(p => ({ ...p, gia: e.target.value }))}
                    placeholder="350000"
                    className="w-full border border-gray-200 rounded-xl px-4 py-2.5 text-sm focus:outline-none focus:ring-2 focus:ring-blue-300"
                  />
                </div>
                <div>
                  <label className="text-sm text-gray-600 mb-1 block">Tồn kho</label>
                  <input
                    type="number"
                    value={form.soLuongTon}
                    onChange={e => setForm(p => ({ ...p, soLuongTon: e.target.value }))}
                    placeholder="20"
                    className="w-full border border-gray-200 rounded-xl px-4 py-2.5 text-sm focus:outline-none focus:ring-2 focus:ring-blue-300"
                  />
                </div>
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
                {saving ? 'Đang lưu...' : editingId ? 'Cập nhật' : 'Thêm mới'}
              </button>
            </div>
          </div>
        </div>
      )}

      {confirmDeleteId && (
        <div className="fixed inset-0 bg-black/40 flex items-center justify-center z-50 px-4">
          <div className="bg-white rounded-2xl shadow-xl w-full max-w-sm p-6 text-center">
            <div className="text-4xl mb-3">🗑️</div>
            <h2 className="text-lg font-bold text-gray-800 mb-2">Xác nhận xóa</h2>
            <p className="text-sm text-gray-500 mb-5">Sản phẩm sẽ bị xóa vĩnh viễn, không thể khôi phục.</p>
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

      {showImageModal && (
        <div className="fixed inset-0 bg-black/40 flex items-center justify-center z-50 px-4">
          <div className="bg-white rounded-2xl shadow-xl w-full max-w-lg p-6">
            <h2 className="text-lg font-bold text-gray-800 mb-1">Cập nhật ảnh sản phẩm</h2>
            <p className="text-xs text-gray-500 mb-4">Tối đa 2 ảnh. Ảnh 1 hiển thị mặc định, hover sẽ đổi sang Ảnh 2.</p>

            <div className="space-y-3">
              <div>
                <label className="text-sm text-gray-600 mb-1 block">Ảnh 1 (bắt buộc nếu dùng ảnh)</label>
                <input
                  value={imageForm.hinhAnhA}
                  onChange={e => setImageForm(prev => ({ ...prev, hinhAnhA: e.target.value }))}
                  placeholder="https://..."
                  className="w-full border border-gray-200 rounded-xl px-4 py-2.5 text-sm focus:outline-none focus:ring-2 focus:ring-blue-300"
                />
              </div>

              <div>
                <label className="text-sm text-gray-600 mb-1 block">Ảnh 2 (hover)</label>
                <input
                  value={imageForm.hinhAnhB}
                  onChange={e => setImageForm(prev => ({ ...prev, hinhAnhB: e.target.value }))}
                  placeholder="https://..."
                  className="w-full border border-gray-200 rounded-xl px-4 py-2.5 text-sm focus:outline-none focus:ring-2 focus:ring-blue-300"
                />
              </div>
            </div>

            {imageError && <p className="mt-3 text-sm text-red-500 bg-red-50 px-3 py-2 rounded-xl">{imageError}</p>}

            <div className="flex gap-2 mt-5">
              <button
                onClick={() => setShowImageModal(false)}
                className="flex-1 border border-gray-200 text-gray-600 py-2.5 rounded-xl text-sm hover:bg-gray-50 transition"
              >
                Hủy
              </button>
              <button
                onClick={handleSaveImages}
                disabled={imageSaving}
                className="flex-1 bg-emerald-600 text-white py-2.5 rounded-xl text-sm font-medium hover:bg-emerald-700 transition disabled:opacity-60"
              >
                {imageSaving ? 'Đang lưu...' : 'Lưu ảnh'}
              </button>
            </div>
          </div>
        </div>
      )}
    </AdminLayout>
  )
}