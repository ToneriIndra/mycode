package cn.zhao.bos.dao.base.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.zhao.bos.dao.base.BaseDao;
import cn.zhao.bos.utils.PageBean;
import cn.zhao.bos.vo.Staff;


public class BaseDaoImpl<T> extends HibernateDaoSupport implements BaseDao<T> {
    @Resource
    public void setSessionfactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }
    private Class<T> entityClass;
    public BaseDaoImpl() {
        ParameterizedType genericSuperclass = (ParameterizedType) this.getClass().getGenericSuperclass();
        Type[] actualTypeArguments = genericSuperclass.getActualTypeArguments();
        entityClass = (Class<T>) actualTypeArguments[0];
    }
    
    public void save(T entity) {
        this.getHibernateTemplate().save(entity);
    }

    public void delete(T entity) {
        this.getHibernateTemplate().delete(entity);
    }

    public T findById(Serializable id) {
        return this.getHibernateTemplate().get(entityClass, id);
    }

    public List<T> findAll() {
        String hql = "from " +entityClass.getSimpleName();
        return this.getHibernateTemplate().find(hql);
    }

    public void update(T entity) {
        this.getHibernateTemplate().update(entity);
    }

    public void executeUpdate(String queryName, Object... objects) {
        Session session = this.getSession();
        Query query = session.getNamedQuery(queryName);
        int i = 0;
        for(Object args:objects) {
            query.setParameter(i++, args);
        }
        query.executeUpdate();
    }

    public void pageQuery(PageBean pageBean) {
        int pageNum = pageBean.getPageNum();
        int pageSize = pageBean.getPageSize();
        DetachedCriteria dc = pageBean.getDc();
        dc.setProjection(Projections.rowCount());
        List<Long> temp = this.getHibernateTemplate().findByCriteria(dc);
        pageBean.setTotal(temp.get(0).intValue());
        dc.setProjection(null);
        dc.setResultTransformer(DetachedCriteria.ROOT_ENTITY);
        int pageFirst = (pageNum - 1) * pageSize;
        List rows = this.getHibernateTemplate().findByCriteria(dc,pageFirst,pageSize);
        pageBean.setRows(rows);
    }

    public void saveOrUpdate(T entity) {
        this.getHibernateTemplate().saveOrUpdate(entity);
    }

    public List<T> findByDetached(DetachedCriteria dc) {
        return this.getHibernateTemplate().findByCriteria(dc);
    }
}
