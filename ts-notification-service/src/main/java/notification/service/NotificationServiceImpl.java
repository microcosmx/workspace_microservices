package notification.service;

import notification.domain.NotifyInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

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

//    @Autowired
//    VelocityEngine velocityEngine;

    public boolean notify(NotifyInfo info){
//        MimeMessage mimeMessage = mailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
//        helper.setFrom("dyc87112@qq.com");
//        helper.setTo("dyc87112@qq.com");
//        helper.setSubject("主题：模板邮件");
//        Map<String, Object> model = new HashMap();
//        model.put("username", "didi");
//        String text = VelocityEngineUtils.mergeTemplateIntoString(
//                velocityEngine, "template.vm", "UTF-8", model);
//        helper.setText(text, true);
//        mailSender.send(mimeMessage);
        return true;
    }
}
