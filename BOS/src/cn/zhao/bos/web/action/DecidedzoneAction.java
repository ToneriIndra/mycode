package cn.zhao.bos.web.action;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.zhao.bos.service.DecidedzoneService;
import cn.zhao.bos.vo.Decidedzone;
import cn.zhao.bos.web.action.base.BaseAction;
import cn.zhao.crm.service.CustomerService;
import cn.zhao.crm.vo.Customer;
@Controller
@Scope("prototype")
public class DecidedzoneAction extends BaseAction<Decidedzone> {
    @Autowired
    private DecidedzoneService decidedzoneService;
    @Autowired
    private CustomerService customerService;
    private String[] subareaId;
    public void setSubareaId(String[] subareaId) {
        this.subareaId = subareaId;
    }
    //保存定区
    public String save() {
        this.decidedzoneService.save(model,subareaId);
        return "toList";
    }
    //编辑定区
    public String edit() {
        this.decidedzoneService.edit(model,subareaId);
        return "toList";
    }
    
    private String ids;
    public void setIds(String ids) {
        this.ids = ids;
    }
    //删除定区
    public String delete() {
        this.decidedzoneService.delete(ids);
        return "toList";
    }
    
    public String list() {
        return "list";
    }
    //分页查询,定区ID过滤
    public String pageQuery() throws IOException {
        DetachedCriteria dc2 = pageBean.getDc();
        if(StringUtils.isNotBlank(model.getId())) {
            dc2.add(Restrictions.eq("decidedzone.id", model.getId()));
        }
        this.decidedzoneService.pageQuery(pageBean);
        this.writePageBean2Json(pageBean, new String[] {"dc", "pageNum", "pageSize"});
        return NONE;
    }
    //查询未关联客户
    public String findnoassociationCustomers() throws IOException {
        List<Customer> list = this.customerService.findnoassociationCustomers();
        this.writeList2Json(list, new String[] {"station","address"});
        return NONE;
    }
    //查询已关联客户
    public String findhasassociationCustomers() throws IOException{
        List<Customer> list = customerService.findhasassociationCustomers(model.getId());
        String[] excludes = new String[]{"station","address"};
        this.writeList2Json(list, excludes);
        return NONE;
    }
    
    private Integer[] customerIds;
    public Integer[] getCustomerIds() {
            return customerIds;
    }

    public void setCustomerIds(Integer[] customerIds) {
            this.customerIds = customerIds;
    }
    
    //关联客户
    public String assignCustomersToDecidedzone(){
            customerService.assignCustomersToDecidedZone(customerIds, model.getId());
            return "list";
    }
}
