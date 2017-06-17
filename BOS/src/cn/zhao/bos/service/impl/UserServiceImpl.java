package cn.zhao.bos.service.impl;

import java.util.List;

import org.activiti.engine.IdentityService;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.zhao.bos.dao.RoleDao;
import cn.zhao.bos.dao.UserDao;
import cn.zhao.bos.service.UserService;
import cn.zhao.bos.utils.MD5Utils;
import cn.zhao.bos.utils.PageBean;
import cn.zhao.bos.vo.Role;
import cn.zhao.bos.vo.User;
@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private IdentityService identityService;
    @Autowired
    private RoleDao roleDao;

    public User login(User user) {
        String userName = user.getUsername();
        String password = user.getPassword();
        password = MD5Utils.md5(password);
        return userDao.findByUserNameAndPassword(userName, password);
    }

    public void editPwdById(String newPwd, String id) {
        newPwd = MD5Utils.md5(newPwd);
        userDao.executeUpdate("editPwd", newPwd, id);
    }

    public void pageQuery(PageBean pageBean) {
        this.userDao.pageQuery(pageBean);
    }

    public void save(User model, String[] roleIds) {
        String password = model.getPassword();
        password = MD5Utils.md5(password);
        model.setPassword(password);
        this.userDao.save(model);
        org.activiti.engine.identity.User actUser = new UserEntity(model.getId());
        identityService.saveUser(actUser);
        if(roleIds != null && roleIds.length > 0) {
            for(String id : roleIds) {
                Role role = this.roleDao.findById(id);
                model.getRoles().add(role);
                identityService.createMembership(actUser.getId(), role.getName());
            }
        }
    }

    public void delete(String ids) {
        String[] temp = ids.split(",");
        for (String id : temp) {
            org.activiti.engine.identity.User actUser = identityService.createUserQuery().userId(id).singleResult();
            Role role = this.userDao.findRoleById(id);
            identityService.deleteMembership(actUser.getId(), role.getName());
            this.userDao.delete(this.userDao.findById(id));
        }
    }

    public void edit(User model,String[] roleIds) {
        User user = this.userDao.findById(model.getId());
        user.setName(model.getName());
        user.setSalary(model.getSalary());
        user.setStation(model.getStation());
        user.setTelephone(model.getTelephone());
        user.setRemark(model.getRemark());
        org.activiti.engine.identity.User actUser = identityService.createUserQuery().userId(model.getId()).singleResult();
        if(roleIds != null && roleIds.length > 0) {
            for (String id : roleIds) {
                Role role = this.roleDao.findById(id);
                model.getRoles().add(role);
                identityService.deleteMembership(actUser.getId(), role.getName());
                identityService.createMembership(actUser.getId(), role.getName());
            }
        }
        this.userDao.update(user);
    }

}
