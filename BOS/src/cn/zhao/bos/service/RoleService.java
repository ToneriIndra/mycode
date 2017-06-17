package cn.zhao.bos.service;

import java.util.List;

import cn.zhao.bos.utils.PageBean;
import cn.zhao.bos.vo.Role;

public interface RoleService {
    /**
     * 分页查询
     * @param pageBean
     */
    public void pageQuery(PageBean pageBean);
    /**
     * 保存
     * @param model
     * @param ids 权限树ID
     */
    public void save(Role model, String ids);
    /**
     * 查询所有
     * @return
     */
    public List<Role> findAll();

}
