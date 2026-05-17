# Cấu trúc thư mục BE 
```
├── .mvn
│   └── wrapper
│       └── maven-wrapper.properties
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── bankinh
│   │   │           └── backend
│   │   │               ├── application
│   │   │               │   ├── dto
│   │   │               │   │   ├── request
│   │   │               │   │   │   ├── CapNhatTrangThaiRequest.java
│   │   │               │   │   │   ├── DonHangChiTietRequest.java
│   │   │               │   │   │   ├── DonHangRequest.java
│   │   │               │   │   │   ├── DonKinhRequest.java
│   │   │               │   │   │   ├── NguoiDungRequest.java
│   │   │               │   │   │   ├── SanPhamRequest.java
│   │   │               │   │   │   ├── TuChoiHoanTraRequest.java
│   │   │               │   │   │   └── YeuCauHoanTraRequest.java
│   │   │               │   │   └── response
│   │   │               │   │       ├── DonHangChiTietResponse.java
│   │   │               │   │       ├── DonHangResponse.java
│   │   │               │   │       ├── DonKinhResponse.java
│   │   │               │   │       ├── NguoiDungResponse.java
│   │   │               │   │       └── SanPhamResponse.java
│   │   │               │   ├── mapper
│   │   │               │   │   ├── DonHangChiTietMapper.java
│   │   │               │   │   ├── DonHangMapper.java
│   │   │               │   │   ├── DonKinhMapper.java
│   │   │               │   │   ├── NguoiDungMapper.java
│   │   │               │   │   └── SanPhamMapper.java
│   │   │               │   └── service
│   │   │               │       ├── DonHangChiTietService.java
│   │   │               │       ├── DonHangService.java
│   │   │               │       ├── DonKinhService.java
│   │   │               │       ├── NguoiDungService.java
│   │   │               │       └── SanPhamService.java
│   │   │               ├── common
│   │   │               │   └── exception
│   │   │               │       ├── GlobalExceptionHandler.java
│   │   │               │       └── ResourceNotFoundException.java
│   │   │               ├── domain
│   │   │               │   ├── model
│   │   │               │   │   ├── DonHang.java
│   │   │               │   │   ├── DonHangChiTiet.java
│   │   │               │   │   ├── DonKinh.java
│   │   │               │   │   ├── LoaiSanPham.java
│   │   │               │   │   ├── NguoiDung.java
│   │   │               │   │   ├── SanPham.java
│   │   │               │   │   ├── TrangThaiDonHang.java
│   │   │               │   │   └── VaiTro.java
│   │   │               │   └── repository
│   │   │               │       ├── DonHangChiTietRepository.java
│   │   │               │       ├── DonHangRepository.java
│   │   │               │       ├── DonKinhRepository.java
│   │   │               │       ├── NguoiDungRepository.java
│   │   │               │       └── SanPhamRepository.java
│   │   │               ├── infrastructure
│   │   │               │   └── persistence
│   │   │               │       ├── adapter
│   │   │               │       │   ├── DonHangChiTietRepositoryAdapter.java
│   │   │               │       │   ├── DonHangRepositoryAdapter.java
│   │   │               │       │   ├── DonKinhRepositoryAdapter.java
│   │   │               │       │   ├── NguoiDungRepositoryAdapter.java
│   │   │               │       │   └── SanPhamRepositoryAdapter.java
│   │   │               │       ├── config
│   │   │               │       │   └── DatabaseSchemaUpdater.java
│   │   │               │       ├── converter
│   │   │               │       │   ├── LoaiSanPhamConverter.java
│   │   │               │       │   ├── TrangThaiDonHangConverter.java
│   │   │               │       │   └── VaiTroConverter.java
│   │   │               │       ├── entity
│   │   │               │       │   ├── DonHangChiTietEntity.java
│   │   │               │       │   ├── DonHangEntity.java
│   │   │               │       │   ├── DonKinhEntity.java
│   │   │               │       │   ├── NguoiDungEntity.java
│   │   │               │       │   └── SanPhamEntity.java
│   │   │               │       ├── mapper
│   │   │               │       │   ├── DonHangChiTietEntityMapper.java
│   │   │               │       │   ├── DonHangEntityMapper.java
│   │   │               │       │   ├── DonKinhEntityMapper.java
│   │   │               │       │   ├── NguoiDungEntityMapper.java
│   │   │               │       │   └── SanPhamEntityMapper.java
│   │   │               │       └── repository
│   │   │               │           ├── DonHangChiTietJpaRepository.java
│   │   │               │           ├── DonHangJpaRepository.java
│   │   │               │           ├── DonKinhJpaRepository.java
│   │   │               │           ├── NguoiDungJpaRepository.java
│   │   │               │           └── SanPhamJpaRepository.java
│   │   │               ├── interfaces
│   │   │               │   └── rest
│   │   │               │       ├── DonHangChiTietController.java
│   │   │               │       ├── DonHangController.java
│   │   │               │       ├── DonKinhController.java
│   │   │               │       ├── NguoiDungController.java
│   │   │               │       └── SanPhamController.java
│   │   │               └── Main.java
│   │   └── resources
│   │       ├── static
│   │       ├── templates
│   │       └── application.properties
│   └── test
│       └── java
│           └── com
│               └── bankinh
│                   └── backend
│                       └── BackendApplicationTests.java
├── .gitattributes
├── .gitignore
├── codesql.txt
├── mvnw
├── mvnw.cmd
└── pom.xml
```
# Cấu trúc thư mục FE
```
├── public
│   ├── favicon.svg
│   └── icons.svg
├── src
│   ├── api
│   │   ├── axios.js
│   │   ├── donHang.js
│   │   ├── donHangChiTiet.js
│   │   ├── donKinh.js
│   │   ├── nguoiDung.js
│   │   └── sanPham.js
│   ├── assets
│   │   ├── hero.png
│   │   ├── react.svg
│   │   └── vite.svg
│   ├── components
│   │   ├── admin
│   │   │   ├── AdminLayout.jsx
│   │   │   └── ProtectedAdminRoute.jsx
│   │   ├── staff
│   │   │   ├── ProtectedStaffRoute.jsx
│   │   │   └── StaffLayout.jsx
│   │   ├── Header.jsx
│   │   ├── Pagination.jsx
│   │   └── ProductCard.jsx
│   ├── context
│   │   ├── AuthContext.jsx
│   │   └── CartContext.jsx
│   ├── pages
│   │   ├── admin
│   │   │   ├── AdminDashboard.jsx
│   │   │   ├── AdminDoanhThuPage.jsx
│   │   │   ├── AdminDonHangPage.jsx
│   │   │   ├── AdminKhachHangPage.jsx
│   │   │   ├── AdminNhanVienPage.jsx
│   │   │   └── AdminSanPhamPage.jsx
│   │   ├── staff
│   │   │   ├── StaffDonHangPage.jsx
│   │   │   ├── StaffKhachHangPage.jsx
│   │   │   └── StaffSanPhamPage.jsx
│   │   ├── CartPage.jsx
│   │   ├── CheckoutPage.jsx
│   │   ├── HomePage.jsx
│   │   ├── LoginPage.jsx
│   │   ├── OrderDetailPage.jsx
│   │   ├── OrdersPage.jsx
│   │   ├── ProductDetailPage.jsx
│   │   └── RegisterPage.jsx
│   ├── App.jsx
│   ├── index.css
│   └── main.jsx
├── .gitignore
├── README.md
├── eslint.config.js
├── index.html
├── package-lock.json
├── package.json
└── vite.config.js
```
