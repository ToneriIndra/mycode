package cn.zhao.bos.web.action;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.zhao.bos.service.NoticebillService;
import cn.zhao.bos.service.WorkbillService;
import cn.zhao.bos.vo.Noticebill;
import cn.zhao.bos.vo.Staff;
import cn.zhao.bos.vo.Workbill;
import cn.zhao.bos.web.action.base.BaseAction;
@Controller
@Scope("prototype")
public class WorkbillAction extends BaseAction<Workbill> {
    @Autowired
    private WorkbillService workbillService;
    @Autowired
    private NoticebillService noticebillService;
    
    private String ids;
    public void setIds(String ids) {
        this.ids = ids;
    }
    
    private String telephone;
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    //分页查询,取派员姓名电话过滤
    public String pageQuery() throws IOException {
        DetachedCriteria dc2 = pageBean.getDc();
        Staff staff = model.getStaff();
        if(staff != null) {
            dc2.add(Restrictions.like("staff.name", "%"+staff.getName()+"%"));
        }
        if(StringUtils.isNotBlank(telephone)) {
            List<Noticebill> list = this.noticebillService.findBytelephone(telephone);
            for (Noticebill noticebill : list) {
                String id = noticebill.getId();
                dc2.add(Restrictions.eq("noticebill.id", id));
            }
        }
        this.workbillService.pageQuery(pageBean);
        writePageBean2Json(pageBean, new String[] {"dc","pageBean","pageNum","pageSize"});
        return NONE;
    }
    
    public String delete() {
        this.workbillService.delete(ids);
        return "toList";
    }
    
    public String list() {
        return "list";
    }
}
