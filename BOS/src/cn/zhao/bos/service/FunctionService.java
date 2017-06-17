package cn.zhao.bos.service;

import java.util.List;

import cn.zhao.bos.utils.PageBean;
import cn.zhao.bos.vo.Function;

public interface FunctionService {
    /**
     * 分页查询
     * @param pageBean
     */
    public void pageQuery(PageBean pageBean);
    /**
     * 查询所有数据
     * @return
     */
    public List<Function> findAll();
    /**
     * 保存
     * @param model
     */
    public void save(Function model);
    /**
     * 查询菜单
     * @return
     */
    public List<Function> findMenu();

}
