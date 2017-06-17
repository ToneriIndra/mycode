package cn.zhao.bos.web.action;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.zhao.bos.service.RoleService;
import cn.zhao.bos.vo.Role;
import cn.zhao.bos.web.action.base.BaseAction;

@Controller
@Scope("prototype")
public class RoleAction extends BaseAction<Role> {
    @Autowired
    private RoleService roleService;
    private String ids;
    public void setIds(String ids) {
        this.ids = ids;
    }

    public String pageQuery() throws IOException {
        this.roleService.pageQuery(pageBean);
        this.writePageBean2Json(pageBean, new String[] {"pageBean", "pageNum", "pageSize"});
        return NONE;
    }
    
    public String save() {
        this.roleService.save(model,ids);
        return "list";
    }
    
    
    public String ajaxList() throws IOException {
        List<Role> list = this.roleService.findAll();
        this.writeList2Json(list, new String[] {});
        return NONE;
    }
}
