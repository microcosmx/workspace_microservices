package notification.service;

import notification.domain.NotifyInfo;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Wenyi on 2017/6/15.
 */
@Service
public class NotificationServiceImpl implements NotificationService{

    @Autowired
    private JavaMailSender mailSender;

    private VelocityEngine velocityEngine = new VelocityEngine();

    public boolean notify(NotifyInfo info){
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom("fdse_microservices@163.com");
            helper.setTo("fdse_microservices@163.com");
            helper.setSubject("主题：模板邮件");
            Map<String, Object> model = new HashMap();
            model.put("username", "fdse");
            String text = VelocityEngineUtils.mergeTemplateIntoString(
                    velocityEngine, "template.vm", "UTF-8", model);
            helper.setText(text, true);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return true;
    }
}
