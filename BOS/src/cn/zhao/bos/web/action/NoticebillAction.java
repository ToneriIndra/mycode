package cn.zhao.bos.web.action;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.zhao.bos.service.NoticebillService;
import cn.zhao.bos.vo.Noticebill;
import cn.zhao.bos.vo.User;
import cn.zhao.bos.web.action.base.BaseAction;
import cn.zhao.crm.service.CustomerService;
import cn.zhao.crm.vo.Customer;

import com.opensymphony.xwork2.ActionContext;
@Controller
@Scope("prototype")
public class NoticebillAction extends BaseAction<Noticebill> {
    @Autowired
    private NoticebillService noticebillServie;
    @Autowired
    private CustomerService customerService;
    public String findCustomerByTelephone() throws IOException {
        Customer customer = this.customerService.findCustomerByTelephone(model.getTelephone());
        this.writeObject2Json(customer, new String[] {});
        return NONE;
    }
    
    public String save() {
        User user = (User) ActionContext.getContext().getSession().get("loginUser");
        model.setUser(user);
        this.noticebillServie.save(model);
        return "addUI";
    }
    //查询未关联工单
    public String findNoAssociations() throws IOException {
        List<Noticebill> list = this.noticebillServie.findNoAssociations();
        writeList2Json(list, new String[] {"user"});
        return NONE;
    }
    
    public String associationStaff() {
        this.noticebillServie.associationStaff(model);
        return "toList";
    }
    
    public String list() {
        return "list";
    }
}
