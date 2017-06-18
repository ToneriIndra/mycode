package com.zjxjd.manage.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.abel533.entity.Example;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zjxjd.manage.pojo.BasePojo;

public abstract class BaseService<T extends BasePojo> {
    /**
     * 注入mapper,具体实现交给子类
     */
    @Autowired
    private Mapper<T> mapper;
    public Mapper<T> getMapper() {
        return mapper;
    }
    /**
     * 根据主键查询数据
     * @param id
     * @return
     */
    public T queryById(Long id) {
        return this.mapper.selectByPrimaryKey(id);
    }
    /**
     * 查询所有
     * @return
     */
    public List<T> queryAll() {
        return this.mapper.select(null);
    }
    /**
     * 根据条件查询
     * @param t
     * @return
     */
    public List<T> queryByWhere(T t) {
        return this.mapper.select(t);
    }
    /**
     * 查询一个
     * @param t
     * @return
     */
    public T queryOne(T t) {
        return this.mapper.selectOne(t);
    }
    /**
     * 分页查询
     * @param t
     * @param pageNum 页数
     * @param pageSize 每页显示条数
     * @return
     */
    public PageInfo<T> queryPageListByWhere(T t, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<T> list = this.queryByWhere(t);
        return new PageInfo<T>(list);
    }
    /**
     * 自定义条件的分页查询
     * @param t 实体
     * @param pageNum 当前页数
     * @param pageSize 每页显示条数
     * @return
     */
    public PageInfo<T> queryPageListByExample(Example example, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<T> list = this.mapper.selectByExample(example);
        return new PageInfo<T>(list);
    }
    /**
     * 保存
     * @param t
     * @return
     */
    public Integer save(T t) {
        t.setCreated(new Date());
        t.setUpdated(t.getCreated());
        return this.mapper.insert(t);
    }
    /**
     * 使用不为空字段保存
     * @param t
     * @return
     */
    public Integer saveSelective(T t) {
        t.setCreated(new Date());
        t.setUpdated(t.getCreated());
        return this.mapper.insertSelective(t);
    }
    /**
     * 更新
     * @param t
     * @return
     */
    public Integer update(T t) {
        t.setUpdated(new Date());
        return this.mapper.updateByPrimaryKey(t);
    }
    /**
     * 使用不为空字段更新
     * @param t
     * @return
     */
    public Integer updateSelective(T t) {
        t.setUpdated(new Date());
        return this.mapper.updateByPrimaryKeySelective(t);
    }
    /**
     * 根据主键删除
     * @param id
     * @return
     */
    public Integer deleteById(Long id) {
        return this.mapper.deleteByPrimaryKey(id);
    }
    /**
     * 批量删除
     * @param ids id 
     * @param property
     * @param clazz
     * @return
     */
    public Integer deleteByIds(List<Object> ids ,String property ,Class<T> clazz) {
        Example example = new Example(clazz);
        example.createCriteria().andIn(property, ids);
        return this.mapper.deleteByExample(example);
    }
}
