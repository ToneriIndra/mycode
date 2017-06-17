package cn.zhao.bos.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.zhao.bos.dao.RegionDao;
import cn.zhao.bos.dao.base.impl.BaseDaoImpl;
import cn.zhao.bos.vo.Region;
@Repository
public class RegionDaoImpl extends BaseDaoImpl<Region> implements RegionDao {
    public List<Region> findByQ(String q) {
        String hql = "FROM Region WHERE province LIKE ? OR city LIKE ? OR district LIKE ?";
        return this.getHibernateTemplate().find(hql, "%"+q+"%","%"+q+"%","%"+q+"%");
}
}
