package com.opticalshop.adapter.external.notification;

import com.opticalshop.application.port.NotificationPort;
import com.opticalshop.domain.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Gửi email thông báo cho khách hàng.
 * Stub: chỉ log — tích hợp JavaMailSender hoặc SendGrid sau.
 */
@Component
public class EmailNotificationAdapter implements NotificationPort {

    private static final Logger log = LoggerFactory.getLogger(EmailNotificationAdapter.class);

    private final UserRepository userRepository;

    public EmailNotificationAdapter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void notifyOrderPlaced(UUID customerId, String orderCode) {
        String email = resolveEmail(customerId);
        log.info("[EMAIL] To: {} | Subject: Order Placed | OrderCode: {}", email, orderCode);

        // TODO: inject JavaMailSender và gửi email thật
        // SimpleMailMessage msg = new SimpleMailMessage();
        // msg.setTo(email);
        // msg.setSubject("Đơn hàng " + orderCode + " đã được đặt thành công");
        // msg.setText("Cảm ơn bạn đã đặt hàng...");
        // mailSender.send(msg);
    }

    @Override
    public void notifyOrderStatusChanged(UUID customerId, String orderCode, String newStatus) {
        String email = resolveEmail(customerId);
        log.info("[EMAIL] To: {} | Subject: Order Status Changed | OrderCode: {} | Status: {}",
                email, orderCode, newStatus);
    }

    @Override
    public void notifyOrderShipped(UUID customerId, String orderCode, String trackingNumber) {
        String email = resolveEmail(customerId);
        log.info("[EMAIL] To: {} | Subject: Order Shipped | OrderCode: {} | Tracking: {}",
                email, orderCode, trackingNumber);
    }

    private String resolveEmail(UUID customerId) {
        return userRepository.findById(customerId)
                .map(u -> u.getEmail())
                .orElse("unknown@opticalshop.com");
    }
}
