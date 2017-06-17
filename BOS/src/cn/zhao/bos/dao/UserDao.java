package cn.zhao.bos.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.zhao.bos.dao.base.BaseDao;
import cn.zhao.bos.vo.Role;
import cn.zhao.bos.vo.User;

public interface UserDao extends BaseDao<User> {
    /**
     * 根据用户名密码查询用户
     * 
     * @param userName
     * @param password
     * @return
     */
    public User findByUserNameAndPassword(String userName, String password);

    /**
     * 根据用户名查询用户
     * 
     * @param userName
     * @return
     */
    public User findByUserName(String userName);

    /**
     * 根据id查询角色
     * 
     * @param id
     * @return
     */
    public Role findRoleById(String id);

}
