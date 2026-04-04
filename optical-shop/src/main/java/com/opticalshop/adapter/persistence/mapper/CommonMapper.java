package com.opticalshop.adapter.persistence.mapper;

import com.opticalshop.adapter.persistence.entity.PaymentJpaEntity;
import com.opticalshop.adapter.persistence.entity.PrescriptionJpaEntity;
import com.opticalshop.adapter.persistence.entity.UserJpaEntity;
import com.opticalshop.domain.model.payment.Payment;
import com.opticalshop.domain.model.prescription.Prescription;
import com.opticalshop.domain.model.user.User;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
public class CommonMapper {

    // ──────────── Payment ────────────

    public PaymentJpaEntity toJpa(Payment payment) {
        PaymentJpaEntity e = new PaymentJpaEntity();
        e.setId(payment.getId());
        e.setOrderId(payment.getOrderId());
        e.setMethod(payment.getMethod());
        e.setStatus(payment.getStatus());
        e.setAmount(payment.getAmount());
        e.setTransactionRef(payment.getTransactionRef());
        e.setPaidAt(payment.getPaidAt());
        return e;
    }

    public Payment toDomain(PaymentJpaEntity e) {
        try {
            Payment payment = allocate(Payment.class);
            setField(payment, "id",             e.getId());
            setField(payment, "orderId",        e.getOrderId());
            setField(payment, "method",         e.getMethod());
            setField(payment, "status",         e.getStatus());
            setField(payment, "amount",         e.getAmount());
            setField(payment, "transactionRef", e.getTransactionRef());
            setField(payment, "paidAt",         e.getPaidAt());
            return payment;
        } catch (Exception ex) {
            throw new RuntimeException("Failed to map PaymentJpaEntity", ex);
        }
    }

    // ──────────── Prescription ────────────

    public PrescriptionJpaEntity toJpa(Prescription p) {
        PrescriptionJpaEntity e = new PrescriptionJpaEntity();
        e.setId(p.getId());
        e.setCustomerId(p.getCustomerId());
        e.setOdSphere(p.getOdSphere());
        e.setOdCylinder(p.getOdCylinder());
        e.setOdAxis(p.getOdAxis());
        e.setOsSphere(p.getOsSphere());
        e.setOsCylinder(p.getOsCylinder());
        e.setOsAxis(p.getOsAxis());
        e.setPd(p.getPd());
        e.setIssuedDate(p.getIssuedDate());
        e.setIssuedBy(p.getIssuedBy());
        e.setNotes(p.getNotes());
        return e;
    }

    public Prescription toDomain(PrescriptionJpaEntity e) {
        return new Prescription(
                e.getId(), e.getCustomerId(),
                e.getOdSphere(), e.getOdCylinder(), e.getOdAxis(),
                e.getOsSphere(), e.getOsCylinder(), e.getOsAxis(),
                e.getPd(), e.getIssuedDate(), e.getIssuedBy(), e.getNotes()
        );
    }

    // ──────────── User ────────────

    public UserJpaEntity toJpa(User user) {
        UserJpaEntity e = new UserJpaEntity();
        e.setId(user.getId());
        e.setUsername(user.getUsername());
        e.setEmail(user.getEmail());
        e.setPasswordHash(user.getPasswordHash());
        e.setFullName(user.getFullName());
        e.setPhone(user.getPhone());
        e.setAddress(user.getAddress());
        e.setActive(user.isActive());
        e.setCreatedAt(user.getCreatedAt());
        return e;
    }

    public User toDomain(UserJpaEntity e) {
        return new User(
                e.getId(), e.getUsername(), e.getEmail(), e.getPasswordHash(),
                e.getFullName(), e.getPhone(), e.getAddress()
        );
    }

    // ──────────── Reflection helpers ────────────

    @SuppressWarnings("unchecked")
    private <T> T allocate(Class<T> clazz) throws Exception {
        Field f = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        sun.misc.Unsafe unsafe = (sun.misc.Unsafe) f.get(null);
        return (T) unsafe.allocateInstance(clazz);
    }

    private void setField(Object target, String name, Object value) throws Exception {
        Class<?> clazz = target.getClass();
        while (clazz != null) {
            try {
                Field f = clazz.getDeclaredField(name);
                f.setAccessible(true);
                f.set(target, value);
                return;
            } catch (NoSuchFieldException ex) {
                clazz = clazz.getSuperclass();
            }
        }
    }
}
