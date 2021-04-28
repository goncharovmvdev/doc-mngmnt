package doc.mngmnt.service.impl.notification;

import doc.mngmnt.service.api.notification.NotificationService;
import doc.mngmnt.service.impl.notification.properties.MailProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
@Transactional
@EnableConfigurationProperties({MailProperties.class})
public class NotificationServiceImpl implements NotificationService {
    private final JavaMailSender javaMailSender;
    private final MailProperties mailProperties;

    @Autowired
    public NotificationServiceImpl(JavaMailSender javaMailSender, MailProperties mailProperties) {
        this.javaMailSender = javaMailSender;
        this.mailProperties = mailProperties;
    }

    @Override
    public void sendNotificationToUser(String notification, String emailTo) {
        Assert.notNull(emailTo, "Email can't be null");
        SimpleMailMessage mailMessage = this.createSimpleMailMessage(notification);
        mailMessage.setTo(emailTo);
        javaMailSender.send(mailMessage);
    }

    private SimpleMailMessage createSimpleMailMessage(String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(mailProperties.getFrom());
        mailMessage.setText(message);
        return mailMessage;
    }
}
