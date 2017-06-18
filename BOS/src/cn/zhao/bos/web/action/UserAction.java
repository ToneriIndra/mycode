package cn.zhao.bos.web.action;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.zhao.bos.service.UserService;
import cn.zhao.bos.utils.MD5Utils;
import cn.zhao.bos.vo.Role;
import cn.zhao.bos.vo.User;
import cn.zhao.bos.web.action.base.BaseAction;

import com.opensymphony.xwork2.ActionContext;
@Controller
@Scope("prototype")
public class UserAction extends BaseAction<User> {
    @Autowired
    private UserService userService;
    
    private String checkcode;
    public void setCheckcode(String checkcode) {
        this.checkcode = checkcode;
    }
    
    private String[] roleIds;
    public void setRoleIds(String[] roleIds) {
        this.roleIds = roleIds;
    }

    public String[] getRoleIds() {
        return roleIds;
    }

    public String login() {
        String key = (String) ActionContext.getContext().getSession().get("key");
        if(StringUtils.isNotBlank(checkcode) && checkcode.equals(key)) {
        	Subject subject = SecurityUtils.getSubject();
        	String password = model.getPassword();
        	password = MD5Utils.md5(password);
        	AuthenticationToken token = new UsernamePasswordToken(model.getUsername(),password);
			try {
				subject.login(token);
			} catch (Exception e) {
				e.printStackTrace();
				this.addActionError(this.getText("LoginFail"));
				return "loginFail";
			}
        	User user = (User) subject.getPrincipal();
        	ActionContext.getContext().getSession().put("loginUser", user);
        	return "loginSuccess";
            } else {
            this.addActionError(this.getText("CheckCodeError"));
            return "loginFail";
        }
    }
    
    public String editPwd() throws IOException {
        String flag = "1";
        try {
            User user = (User) ActionContext.getContext().getSession().get("loginUser");
            String newPwd = model.getPassword();
            userService.editPwdById(newPwd, user.getId());
        } catch (Exception e) {
            e.printStackTrace();
            flag = "0";
        }
        ServletActionContext.getResponse().setContentType("text/html;charset=UTF-8");
        ServletActionContext.getResponse().getWriter().print(flag);
        return NONE;
    }
    
    public String logout() {
        ActionContext.getContext().getSession().put("loginUser", null);
        return "loginFail";
    }
    
    //查询用户,姓名性别用户名过滤
    public String pageQuery() throws IOException {
        DetachedCriteria dc2 = pageBean.getDc();
        String username = model.getUsername();
        String gender = model.getGender();
        String name = model.getName();
        if(StringUtils.isNotBlank(username)) {
            dc2.add(Restrictions.eq("username", username));
        }
        if(StringUtils.isNotBlank(name)) {
            dc2.add(Restrictions.eq("name", name));
        }
        if(StringUtils.isNotBlank(gender)) {
            dc2.add(Restrictions.eq("gender", gender));
        }
        this.userService.pageQuery(pageBean);
        this.writePageBean2Json(pageBean, new String[]{"pageBean", "pageNum", "pageSize","dc2","dc"});
        return NONE;
    }
    
    public String save() {
        this.userService.save(model, roleIds);
        return "toList";
    }
    
    private String ids;
    public void setIds(String ids) {
        this.ids = ids;
    }

    public String delete() {
        this.userService.delete(ids);
        return "toList";
    }
    
    public String list() {
        return "list";
    }
    
    public String edit() {
        this.userService.edit(model,roleIds);
        return "toList";
    }
    
}
