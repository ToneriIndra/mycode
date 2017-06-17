package cn.zhao.bos.dao.base;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import cn.zhao.bos.utils.PageBean;
import cn.zhao.bos.vo.User;

public interface BaseDao<T> {
    public void save(T entity);
    public void delete(T entity);
    /**
     * 通过ID查询用户
     * @param id
     * @return
     */
    public T findById(Serializable id);
    /**
     * 查询所有用户
     * @return
     */
    public List<T> findAll();
    /**
     * 通用更新方法
     * @param queryName 更新名称
     * @param objects 更新传入的参数
     */
    public void executeUpdate(String queryName, Object...objects);
    /**
     * 通用分页查询
     * @param pageBean
     */
    public void pageQuery(PageBean pageBean);
    /**
     * 修改信息
     * @param entity
     */
    public void update(T entity);
    /**
     * 保存或更新方法
     * @param entity
     */
    public void saveOrUpdate(T entity);
    /**
     * 根据条件查询
     * @param dc
     * @return
     */
    public List<T> findByDetached(DetachedCriteria dc);
}
