package cn.zhao.bos.service;

import java.util.List;

import cn.zhao.bos.utils.PageBean;
import cn.zhao.bos.vo.Role;
import cn.zhao.bos.vo.User;

public interface UserService {
    /**
     * 获取用户名密码封装成User返回
     * @param model
     * @return
     */
    public User login(User model);
    /**
     * 通过用户ID修改密码
     * @param newPwd 新密码
     * @param id   用户ID
     */
    public void editPwdById(String newPwd, String id);
    /**
     * 分页查询
     * @param pageBean
     */
    public void pageQuery(PageBean pageBean);
    /**
     * 关联角色保存用户
     * @param model
     * @param roleIds
     */
    public void save(User model, String[] roleIds);
    /**
     * 删除用户
     * @param ids
     */
    public void delete(String ids);
    /**
     * 修改用户
     * @param model
     * @param roleIds 
     */
    public void edit(User model, String[] roleIds);
        
}
