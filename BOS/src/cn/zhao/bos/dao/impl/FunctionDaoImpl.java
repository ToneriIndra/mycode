package cn.zhao.bos.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.zhao.bos.dao.FunctionDao;
import cn.zhao.bos.dao.base.impl.BaseDaoImpl;
import cn.zhao.bos.vo.Function;
@Repository
public class FunctionDaoImpl extends BaseDaoImpl<Function> implements FunctionDao {

    public List<Function> findListByUserId(String id) {
        String hql = "select  distinct f from User u join u.roles r join r.functions f where u.id = ? ";
        return this.getHibernateTemplate().find(hql, id);
                
    }

    public List<Function> findAllMenu() {
        String hql = "from Function f where f.generatemenu = '1' order by f.zindex desc";
        return this.getHibernateTemplate().find(hql);
    }

    public List<Function> findMenuByUserId(String id) {
        String hql = "select distinct f from User u join u.roles r join r.functions f where u.id = ? and f.generatemenu='1' order by f.zindex desc";
        return this.getHibernateTemplate().find(hql, id);
    }

}
