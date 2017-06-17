package cn.zhao.bos.service.impl;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import cn.zhao.bos.service.MailService;
@Service
public class MailServiceImpl implements MailService {

    public void sendSuggestions(MailSender mailSender,String suggestions_c) {
        SimpleMailMessage ssm = new SimpleMailMessage();
        ssm.setTo("317432944@qq.com");
        ssm.setFrom("xswz19900520@163.com");
        ssm.setSubject("BOS用户建议");
        ssm.setText(suggestions_c);
        mailSender.send(ssm);
    }

    public void mailTask(MailSender mailSender, String address) {
        SimpleMailMessage ssm = new SimpleMailMessage();
        ssm.setTo(address);
        ssm.setFrom("xswz19900520@163.com");
        ssm.setSubject("任务办理");
        ssm.setText("您有新的任务,请按时办理");
        mailSender.send(ssm);
    }
    
}
