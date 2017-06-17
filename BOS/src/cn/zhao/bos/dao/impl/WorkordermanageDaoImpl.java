package cn.zhao.bos.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.zhao.bos.dao.WorkordermanageDao;
import cn.zhao.bos.dao.base.impl.BaseDaoImpl;
import cn.zhao.bos.vo.Workordermanage;

@Repository
public class WorkordermanageDaoImpl extends BaseDaoImpl<Workordermanage> implements WorkordermanageDao {

    public List<Workordermanage> findListNotStart() {
        String hql = "from Workordermanage where start = ?";
        return this.getHibernateTemplate().find(hql, "0");
    }
    
}
