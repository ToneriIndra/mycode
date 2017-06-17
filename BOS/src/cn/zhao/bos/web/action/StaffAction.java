package cn.zhao.bos.web.action;

import java.io.IOException;
import java.util.List;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.zhao.bos.service.StaffService;
import cn.zhao.bos.utils.PageBean;
import cn.zhao.bos.vo.Staff;
import cn.zhao.bos.web.action.base.BaseAction;
@Controller
@Scope("prototype")
public class StaffAction extends BaseAction<Staff> {
    @Autowired
    private StaffService staffService;
    
    private String ids;
    public void setIds(String ids) {
        this.ids = ids;
    }
    
    public String save() {
        staffService.save(model);
        return "toList";
    }
    
    public String list() {
        return "list";
    }
    
    //分页查询
    public String pageQuery() throws IOException {
        staffService.pageQuery(pageBean);
        this.writePageBean2Json(pageBean, new String[]{"pageNum", "pageSize"});
        return NONE;
    }
    
    public String delete() {
        this.staffService.delete(ids);
        return "toList";
    }
    
    public String restore() {
        this.staffService.restore(ids);
        return "toList";
    }
    
    public String edit() {
        this.staffService.edit(model);
        return "toList";
    }
    //为定区窗口下拉框提供列表数据
    public String ajaxList() throws IOException {
        List<Staff> staffs = this.staffService.findListNotDelete();
        this.writeList2Json(staffs, new String[] {"decidedzones"});
        return NONE;
    }
}
