package com.opticalshop.adapter.persistence.mapper;

import com.opticalshop.adapter.persistence.entity.OrderItemJpaEntity;
import com.opticalshop.adapter.persistence.entity.OrderJpaEntity;
import com.opticalshop.domain.model.order.Order;
import com.opticalshop.domain.model.order.OrderItem;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Chuyển đổi giữa Order domain model và OrderJpaEntity.
 *
 * Lưu ý: Order dùng private constructor + factory method → tao dùng reflection
 * để reconstruct domain object từ JPA entity (tránh lộ setter vào domain).
 * Cách thay thế sạch hơn là thêm package-private constructor vào Order,
 * nhưng cách này giữ nguyên domain code không cần sửa.
 */
@Component
public class OrderMapper {

    // ──────────────────────────────────────────────
    // Domain → JPA
    // ──────────────────────────────────────────────

    public OrderJpaEntity toJpa(Order order) {
        OrderJpaEntity entity = new OrderJpaEntity();
        entity.setId(order.getId());
        entity.setOrderCode(order.getOrderCode());
        entity.setCustomerId(order.getCustomerId());
        entity.setOrderType(order.getOrderType());
        entity.setStatus(order.getStatus());
        entity.setDiscountAmount(order.getDiscountAmount());
        entity.setShippingFee(order.getShippingFee());
        entity.setShippingAddress(order.getShippingAddress());
        entity.setNotes(order.getNotes());
        entity.setCreatedAt(order.getCreatedAt());
        entity.setUpdatedAt(order.getUpdatedAt());

        List<OrderItemJpaEntity> itemEntities = order.getItems().stream()
                .map(item -> toItemJpa(item, entity))
                .toList();
        entity.setItems(itemEntities);

        return entity;
    }

    private OrderItemJpaEntity toItemJpa(OrderItem item, OrderJpaEntity orderEntity) {
        OrderItemJpaEntity e = new OrderItemJpaEntity();
        e.setId(item.getId());
        e.setOrder(orderEntity);
        e.setVariantId(item.getVariantId());
        e.setLensId(item.getLensId());
        e.setPrescriptionId(item.getPrescriptionId());
        e.setItemType(item.getItemType());
        e.setQuantity(item.getQuantity());
        e.setUnitPrice(item.getUnitPrice());
        e.setSubtotal(item.getSubtotal());
        return e;
    }

    // ──────────────────────────────────────────────
    // JPA → Domain  (dùng reflection để bypass private constructor)
    // ──────────────────────────────────────────────

    public Order toDomain(OrderJpaEntity entity) {
        try {
            // Tạo Order rỗng bằng reflection (không gọi constructor thực)
            Order order = (Order) unsafe().allocateInstance(Order.class);

            setField(order, "id",              entity.getId());
            setField(order, "orderCode",       entity.getOrderCode());
            setField(order, "customerId",      entity.getCustomerId());
            setField(order, "orderType",       entity.getOrderType());
            setField(order, "status",          entity.getStatus());
            setField(order, "discountAmount",  entity.getDiscountAmount());
            setField(order, "shippingFee",     entity.getShippingFee());
            setField(order, "shippingAddress", entity.getShippingAddress());
            setField(order, "notes",           entity.getNotes());
            setField(order, "createdAt",       entity.getCreatedAt());
            setField(order, "updatedAt",       entity.getUpdatedAt());

            // Khởi tạo list items
            java.util.List<OrderItem> items = entity.getItems().stream()
                    .map(this::toItemDomain)
                    .collect(java.util.stream.Collectors.toCollection(java.util.ArrayList::new));
            setField(order, "items", items);

            return order;
        } catch (Exception e) {
            throw new RuntimeException("Failed to map OrderJpaEntity to Order domain", e);
        }
    }

    private OrderItem toItemDomain(OrderItemJpaEntity e) {
        // OrderItem có public constructor → dùng trực tiếp
        return new OrderItem(
                e.getVariantId(),
                e.getLensId(),
                e.getPrescriptionId(),
                e.getItemType(),
                e.getQuantity(),
                e.getUnitPrice()
        );
    }

    // ──────────────────────────────────────────────
    // Reflection helpers
    // ──────────────────────────────────────────────

    private void setField(Object target, String fieldName, Object value) throws Exception {
        Class<?> clazz = target.getClass();
        while (clazz != null) {
            try {
                Field f = clazz.getDeclaredField(fieldName);
                f.setAccessible(true);
                f.set(target, value);
                return;
            } catch (NoSuchFieldException ex) {
                clazz = clazz.getSuperclass();
            }
        }
        throw new NoSuchFieldException(fieldName + " not found in " + target.getClass());
    }

    @SuppressWarnings("all")
    private sun.misc.Unsafe unsafe() throws Exception {
        Field f = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        return (sun.misc.Unsafe) f.get(null);
    }
}
