package doc.mngmnt.service.impl.notification;

import doc.mngmnt.service.api.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
@Transactional
@PropertySource("classpath:mail.properties")
@PreAuthorize("permitAll()")
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final JavaMailSender javaMailSender;
    @Value("${from}")
    private String from;

    @Override
    public void sendNotificationToUser(String notification, String emailTo) throws Exception {
        if (emailTo == null) {
            throw new Exception();
        }
        SimpleMailMessage mailMessage = this.createSimpleMailMessage(notification);
        mailMessage.setTo(emailTo);
        javaMailSender.send(mailMessage);
    }

    private SimpleMailMessage createSimpleMailMessage(String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(from);
        mailMessage.setText(message);
        return mailMessage;
    }
}
