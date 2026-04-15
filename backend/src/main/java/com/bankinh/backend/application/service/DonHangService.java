package com.bankinh.backend.application.service;

import com.bankinh.backend.application.dto.request.CapNhatTrangThaiRequest;
import com.bankinh.backend.application.dto.request.DonHangRequest;
import com.bankinh.backend.application.dto.request.TuChoiHoanTraRequest;
import com.bankinh.backend.application.dto.request.YeuCauHoanTraRequest;
import com.bankinh.backend.application.dto.response.DonHangResponse;
import com.bankinh.backend.application.mapper.DonHangMapper;
import com.bankinh.backend.common.exception.ResourceNotFoundException;
import com.bankinh.backend.domain.model.DonHang;
import com.bankinh.backend.domain.model.DonHangChiTiet;
import com.bankinh.backend.domain.model.NguoiDung;
import com.bankinh.backend.domain.model.SanPham;
import com.bankinh.backend.domain.model.TrangThaiDonHang;
import com.bankinh.backend.domain.repository.DonHangChiTietRepository;
import com.bankinh.backend.domain.repository.DonHangRepository;
import com.bankinh.backend.domain.repository.NguoiDungRepository;
import com.bankinh.backend.domain.repository.SanPhamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DonHangService {

    private final DonHangRepository donHangRepository;
    private final NguoiDungRepository nguoiDungRepository;
    private final DonHangChiTietRepository donHangChiTietRepository;
    private final SanPhamRepository sanPhamRepository;
    private final DonHangMapper donHangMapper;

    private static final Set<TrangThaiDonHang> TRANG_THAI_CONG_KHO = Set.of(
            TrangThaiDonHang.da_huy,
            TrangThaiDonHang.da_tra_hang_hoan_tien
    );
    private static final Set<TrangThaiDonHang> TRANG_THAI_CHUA_TRU_KHO = Set.of(
            TrangThaiDonHang.cho_thanh_toan,
            TrangThaiDonHang.cho_xac_nhan
    );

    public DonHangResponse create(DonHangRequest request) {
        NguoiDung nguoiDung = nguoiDungRepository.findById(request.getNguoiDungId())
                .orElseThrow(() -> new ResourceNotFoundException("NguoiDung not found: " + request.getNguoiDungId()));
        DonHang donHang = donHangMapper.toDomain(request);
        DonHangResponse response = donHangMapper.toResponse(donHangRepository.save(donHang));
        enrichWithKhachHang(response, nguoiDung);
        return response;
    }

    public List<DonHangResponse> getAll() {
        return donHangRepository.findAll().stream()
                .map(this::toResponseWithKhachHang)
                .collect(Collectors.toList());
    }

    public List<DonHangResponse> getAllByNguoiDungId(UUID nguoiDungId) {
        if (!nguoiDungRepository.existsById(nguoiDungId)) {
            throw new ResourceNotFoundException("NguoiDung not found: " + nguoiDungId);
        }
        return donHangRepository.findAllByNguoiDungId(nguoiDungId).stream()
                .map(this::toResponseWithKhachHang)
                .collect(Collectors.toList());
    }

    public DonHangResponse getById(UUID id) {
        return toResponseWithKhachHang(
                donHangRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("DonHang not found: " + id))
        );
    }

    public DonHangResponse capNhatTrangThai(UUID id, CapNhatTrangThaiRequest request) {
        DonHang donHang = donHangRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("DonHang not found: " + id));

        TrangThaiDonHang cuTrangThai = donHang.getTrangThai();
        TrangThaiDonHang moiTrangThai = request.getTrangThai();

        if (moiTrangThai == TrangThaiDonHang.dang_xu_ly && cuTrangThai != TrangThaiDonHang.dang_xu_ly) {
            xuLyKho(id, -1);
        }
        if (TRANG_THAI_CONG_KHO.contains(moiTrangThai)
                && !TRANG_THAI_CONG_KHO.contains(cuTrangThai)
                && !TRANG_THAI_CHUA_TRU_KHO.contains(cuTrangThai)) {
            xuLyKho(id, +1);
        }

        donHang.setTrangThai(moiTrangThai);
        return toResponseWithKhachHang(donHangRepository.save(donHang));
    }

    public DonHangResponse huyDon(UUID id, UUID nguoiDungId) {
        DonHang donHang = donHangRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("DonHang not found: " + id));
        if (!donHang.getNguoiDungId().equals(nguoiDungId)) {
            throw new IllegalArgumentException("Không có quyền hủy đơn này");
        }
        if (donHang.getTrangThai() != TrangThaiDonHang.cho_xac_nhan) {
            throw new IllegalArgumentException("Chỉ có thể hủy đơn khi đang chờ xác nhận");
        }
        donHang.setTrangThai(TrangThaiDonHang.da_huy);
        return toResponseWithKhachHang(donHangRepository.save(donHang));
    }

    public DonHangResponse yeuCauHoanTra(UUID id, UUID nguoiDungId, YeuCauHoanTraRequest request) {
        DonHang donHang = donHangRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("DonHang not found: " + id));
        if (!donHang.getNguoiDungId().equals(nguoiDungId)) {
            throw new IllegalArgumentException("Không có quyền yêu cầu hoàn trả đơn này");
        }
        if (donHang.getTrangThai() != TrangThaiDonHang.hoan_thanh) {
            throw new IllegalArgumentException("Chỉ có thể yêu cầu hoàn trả khi đơn đã hoàn thành");
        }
        donHang.setTrangThai(TrangThaiDonHang.yeu_cau_hoan_tra);
        donHang.setLyDoHoanTra(request.getLyDo());
        donHang.setTenNganHang(request.getTenNganHang());
        donHang.setSoTaiKhoan(request.getSoTaiKhoan());
        donHang.setTenChuTaiKhoan(request.getTenChuTaiKhoan());
        return toResponseWithKhachHang(donHangRepository.save(donHang));
    }

    // Admin/nhân viên từ chối hoàn trả — kèm lý do
    public DonHangResponse tuChoiHoanTra(UUID id, TuChoiHoanTraRequest request) {
        DonHang donHang = donHangRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("DonHang not found: " + id));
        if (donHang.getTrangThai() != TrangThaiDonHang.yeu_cau_hoan_tra) {
            throw new IllegalArgumentException("Chỉ có thể từ chối khi đơn đang ở trạng thái yêu cầu hoàn trả");
        }
        donHang.setTrangThai(TrangThaiDonHang.tu_choi_hoan_tra);
        donHang.setLyDoTuChoi(request.getLyDoTuChoi());
        return toResponseWithKhachHang(donHangRepository.save(donHang));
    }

    private void xuLyKho(UUID donHangId, int delta) {
        List<DonHangChiTiet> chiTiets = donHangChiTietRepository.findAllByDonHangId(donHangId);
        for (DonHangChiTiet ct : chiTiets) {
            SanPham sanPham = sanPhamRepository.findById(ct.getSanPhamId())
                    .orElseThrow(() -> new ResourceNotFoundException("SanPham not found: " + ct.getSanPhamId()));
            int soLuongMoi = sanPham.getSoLuongTon() + (delta * ct.getSoLuong());
            if (soLuongMoi < 0) {
                throw new IllegalArgumentException("Sản phẩm '" + sanPham.getTen() + "' không đủ số lượng trong kho");
            }
            sanPham.setSoLuongTon(soLuongMoi);
            sanPhamRepository.save(sanPham);
        }
    }

    private DonHangResponse toResponseWithKhachHang(DonHang donHang) {
        DonHangResponse response = donHangMapper.toResponse(donHang);
        nguoiDungRepository.findById(donHang.getNguoiDungId())
                .ifPresent(nd -> enrichWithKhachHang(response, nd));
        return response;
    }

    private void enrichWithKhachHang(DonHangResponse response, NguoiDung nguoiDung) {
        response.setHoTenKhachHang(nguoiDung.getHoTen());
        response.setEmailKhachHang(nguoiDung.getEmail());
    }
}