package cn.zhao.bos.service;

import org.springframework.mail.MailSender;

public interface MailService {
    /**
     * 反馈意见
     * @param mailSender 
     * @param suggestions_c
     */
    public void sendSuggestions(MailSender mailSender, String suggestions_c);
    /**
     * 任务办理邮件
     * @param mailSender
     * @param address
     */
    public void mailTask(MailSender mailSender, String address);

}
