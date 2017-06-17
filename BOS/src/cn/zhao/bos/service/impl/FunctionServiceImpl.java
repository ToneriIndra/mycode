package cn.zhao.bos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.zhao.bos.dao.FunctionDao;
import cn.zhao.bos.service.FunctionService;
import cn.zhao.bos.utils.PageBean;
import cn.zhao.bos.vo.Function;
import cn.zhao.bos.vo.User;

import com.opensymphony.xwork2.ActionContext;
@Service
@Transactional
public class FunctionServiceImpl implements FunctionService {
    @Autowired
    private FunctionDao functionDao;

    public void pageQuery(PageBean pageBean) {
        this.functionDao.pageQuery(pageBean);
    }

    public List<Function> findAll() {
        return this.functionDao.findAll();
    }

    public void save(Function model) {
        Function function = this.functionDao.findById(model.getId());
        if(function == null || function.getId().trim().length()==0) {
            model.setFunction(null);
        }
        this.functionDao.save(model);
    }

    public List<Function> findMenu() {
        User user = (User) ActionContext.getContext().getSession().get("loginUser");
        List<Function> list = null;
        if(user.getUsername().equals("admin")) {
            list = this.functionDao.findAllMenu();
        } else {
            list = this.functionDao.findMenuByUserId(user.getId()); 
        }
        return list;
    }
}       
