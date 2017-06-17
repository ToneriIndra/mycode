package cn.zhao.bos.web.action;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.MailSender;
import org.springframework.stereotype.Controller;

import cn.zhao.bos.service.MailService;

import com.opensymphony.xwork2.ActionSupport;
@Controller
@Scope("prototype")
public class MailAction extends ActionSupport {
    private String suggestions_c;
    
    public void setSuggestions_c(String suggestions_c) {
        this.suggestions_c = suggestions_c;
    }
    @Autowired
    private MailSender mailSender;
    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }
    @Autowired
    private MailService mailService;
    //用户建议
    public void suggestions() throws Exception {
        this.mailService.sendSuggestions(mailSender,suggestions_c);
    }
    
}
