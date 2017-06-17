package cn.zhao.bos.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import cn.zhao.bos.dao.UserDao;
import cn.zhao.bos.dao.base.impl.BaseDaoImpl;
import cn.zhao.bos.vo.Role;
import cn.zhao.bos.vo.User;
@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {

    public User findByUserNameAndPassword(String userName, String password) {
        String hql = "from User where userName = ? and password = ?";
        List<User> temp = this.getHibernateTemplate().find(hql, userName, password);
        if(temp != null && temp.size() > 0) {
            return temp.get(0);
        }
        return null;
    }

    public User findByUserName(String userName) {
        String hql = "from User where userName = ?";
        List<User> temp = this.getHibernateTemplate().find(hql, userName);
        if(temp != null && temp.size()>0) {
            return temp.get(0);
        }
        return null;
    }


    public Role findRoleById(String id) {
        String hql = "select distinct r from User u join u.roles r where u.id = ?";
        return (Role) this.getHibernateTemplate().find(hql,id).get(0);
    }

}
