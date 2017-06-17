package cn.zhao.bos.service.impl;

import java.util.List;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.zhao.bos.dao.RoleDao;
import cn.zhao.bos.service.RoleService;
import cn.zhao.bos.utils.PageBean;
import cn.zhao.bos.vo.Function;
import cn.zhao.bos.vo.Role;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private IdentityService identityService;

    public void pageQuery(PageBean pageBean) {
        this.roleDao.pageQuery(pageBean);
    }

    public void save(Role model, String ids) {
        this.roleDao.save(model);
        Group group = new GroupEntity(model.getName());
        identityService.saveGroup(group);
        String[] functionIds = ids.split(",");
        for(String functionId : functionIds) {
            Function function = new Function(functionId);
            model.getFunctions().add(function);
        }
    }

    public List<Role> findAll() {
        return this.roleDao.findAll();
    }
}
