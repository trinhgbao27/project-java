# Cấu trúc thư mục BE cơ bản
```
├── optical-shop
│   ├── src
│   │   └── main
│   │       ├── java
│   │       │   └── com
│   │       │       └── opticalshop
│   │       │           ├── adapter
│   │       │           │   ├── external
│   │       │           │   │   ├── notification
│   │       │           │   │   │   └── EmailNotificationAdapter.java
│   │       │           │   │   ├── payment
│   │       │           │   │   │   ├── MomoPaymentAdapter.java
│   │       │           │   │   │   ├── PaymentGatewayRouter.java
│   │       │           │   │   │   └── VnpayPaymentAdapter.java
│   │       │           │   │   └── shipping
│   │       │           │   │       └── GhnShippingAdapter.java
│   │       │           │   ├── persistence
│   │       │           │   │   ├── entity
│   │       │           │   │   │   ├── OrderItemJpaEntity.java
│   │       │           │   │   │   ├── OrderJpaEntity.java
│   │       │           │   │   │   ├── PaymentJpaEntity.java
│   │       │           │   │   │   ├── PrescriptionJpaEntity.java
│   │       │           │   │   │   ├── ProductJpaEntity.java
│   │       │           │   │   │   ├── ProductVariantJpaEntity.java
│   │       │           │   │   │   └── UserJpaEntity.java
│   │       │           │   │   ├── impl
│   │       │           │   │   │   ├── OrderRepositoryImpl.java
│   │       │           │   │   │   ├── PaymentRepositoryImpl.java
│   │       │           │   │   │   ├── PrescriptionRepositoryImpl.java
│   │       │           │   │   │   ├── ProductRepositoryImpl.java
│   │       │           │   │   │   └── UserRepositoryImpl.java
│   │       │           │   │   ├── mapper
│   │       │           │   │   │   ├── CommonMapper.java
│   │       │           │   │   │   ├── OrderMapper.java
│   │       │           │   │   │   └── ProductMapper.java
│   │       │           │   │   └── repository
│   │       │           │   │       ├── OrderJpaRepository.java
│   │       │           │   │       ├── PaymentJpaRepository.java
│   │       │           │   │       ├── PrescriptionJpaRepository.java
│   │       │           │   │       ├── ProductJpaRepository.java
│   │       │           │   │       ├── ProductVariantJpaRepository.java
│   │       │           │   │       └── UserJpaRepository.java
│   │       │           │   └── web
│   │       │           │       ├── customer
│   │       │           │       │   ├── AccountController.java
│   │       │           │       │   ├── OrderController.java
│   │       │           │       │   └── ProductController.java
│   │       │           │       ├── dto
│   │       │           │       │   ├── request
│   │       │           │       │   └── response
│   │       │           │       ├── manager
│   │       │           │       │   └── ProductManagementController.java
│   │       │           │       ├── staff
│   │       │           │       │   └── StaffOrderController.java
│   │       │           │       └── GlobalExceptionHandler.java
│   │       │           ├── application
│   │       │           │   ├── port
│   │       │           │   │   ├── NotificationPort.java
│   │       │           │   │   ├── PaymentGatewayPort.java
│   │       │           │   │   └── ShippingCarrierPort.java
│   │       │           │   └── usecase
│   │       │           │       ├── cart
│   │       │           │       ├── order
│   │       │           │       │   ├── CancelOrderCommand.java
│   │       │           │       │   ├── CancelOrderUseCase.java
│   │       │           │       │   ├── PlaceOrderCommand.java
│   │       │           │       │   ├── PlaceOrderResult.java
│   │       │           │       │   ├── PlaceOrderUseCase.java
│   │       │           │       │   ├── TrackOrderQuery.java
│   │       │           │       │   ├── TrackOrderResult.java
│   │       │           │       │   └── TrackOrderUseCase.java
│   │       │           │       ├── payment
│   │       │           │       ├── prescription
│   │       │           │       │   ├── CreatePrescriptionOrderCommand.java
│   │       │           │       │   ├── CreatePrescriptionOrderResult.java
│   │       │           │       │   └── CreatePrescriptionOrderUseCase.java
│   │       │           │       ├── product
│   │       │           │       │   ├── GetProductDetailQuery.java
│   │       │           │       │   ├── GetProductDetailUseCase.java
│   │       │           │       │   └── ProductDetailResult.java
│   │       │           │       └── staff
│   │       │           │           ├── ConfirmOrderUseCase.java
│   │       │           │           └── UpdateShipmentUseCase.java
│   │       │           ├── domain
│   │       │           │   ├── event
│   │       │           │   │   ├── DomainEvent.java
│   │       │           │   │   ├── OrderPlacedEvent.java
│   │       │           │   │   └── OrderStatusChangedEvent.java
│   │       │           │   ├── exception
│   │       │           │   │   ├── DomainException.java
│   │       │           │   │   ├── InsufficientStockException.java
│   │       │           │   │   └── OrderNotFoundException.java
│   │       │           │   ├── model
│   │       │           │   │   ├── cart
│   │       │           │   │   ├── order
│   │       │           │   │   │   ├── ItemType.java
│   │       │           │   │   │   ├── Order.java
│   │       │           │   │   │   ├── OrderItem.java
│   │       │           │   │   │   ├── OrderStatus.java
│   │       │           │   │   │   └── OrderType.java
│   │       │           │   │   ├── payment
│   │       │           │   │   │   ├── Payment.java
│   │       │           │   │   │   ├── PaymentMethod.java
│   │       │           │   │   │   └── PaymentStatus.java
│   │       │           │   │   ├── prescription
│   │       │           │   │   │   └── Prescription.java
│   │       │           │   │   ├── product
│   │       │           │   │   │   ├── CoatingType.java
│   │       │           │   │   │   ├── LensCatalog.java
│   │       │           │   │   │   ├── LensType.java
│   │       │           │   │   │   ├── Product.java
│   │       │           │   │   │   ├── ProductType.java
│   │       │           │   │   │   └── ProductVariant.java
│   │       │           │   │   └── user
│   │       │           │   │       └── User.java
│   │       │           │   ├── repository
│   │       │           │   │   ├── OrderRepository.java
│   │       │           │   │   ├── PaymentRepository.java
│   │       │           │   │   ├── PrescriptionRepository.java
│   │       │           │   │   ├── ProductRepository.java
│   │       │           │   │   └── UserRepository.java
│   │       │           │   └── service
│   │       │           │       └── PricingService.java
│   │       │           └── infrastructure
│   │       │               ├── config
│   │       │               │   ├── JpaConfig.java
│   │       │               │   ├── SecurityConfig.java
│   │       │               │   ├── SwaggerConfig.java
│   │       │               │   └── UseCaseConfig.java
│   │       │               ├── security
│   │       │               │   ├── CustomUserPrincipal.java
│   │       │               │   ├── JwtFilter.java
│   │       │               │   └── JwtUtil.java
│   │       │               └── OpticalShopApplication.java
│   │       └── resources
│   │           └── application.properties
│   ├── docker-compose.yml
│   └── pom.xml
├── .gitignore
└── readme.md
```