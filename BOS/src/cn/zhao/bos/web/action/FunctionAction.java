package cn.zhao.bos.web.action;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.zhao.bos.service.FunctionService;
import cn.zhao.bos.vo.Function;
import cn.zhao.bos.web.action.base.BaseAction;
@Controller
@Scope("prototype")
public class FunctionAction extends BaseAction<Function> {
    @Autowired
    private FunctionService functionService;
    
    public String pageQuery() throws IOException {
        String page = model.getPage();
        pageBean.setPageNum(Integer.parseInt(page));
        this.functionService.pageQuery(pageBean);
        this.writePageBean2Json(pageBean, new String[] {"function", "roles", "pageBean", "pageNum", "pageSize"});
        return NONE;
    }
    //为添加权限页面下拉框提供数据
    public String ajaxList() throws IOException {
        List<Function> list = this.functionService.findAll();
        this.writeList2Json(list, new String[] {"function", "roles"});
        return NONE;
    }
    
    public String save() {
        this.functionService.save(model);
        return "list";
    }
    
    public String findMenu() throws IOException {
        List<Function> list = this.functionService.findMenu();
        this.writeList2Json(list, new String[] {"function"});
        return NONE;
    }
}
