package cn.zhao.bos.service;

import java.util.List;

import cn.zhao.bos.utils.PageBean;
import cn.zhao.bos.vo.Staff;

public interface StaffService {
    /**
     * 保存取派员
     * @param model 取派员
     */
    public void save(Staff model);
    /**
     * 分页查询
     * @param pageBean 分页查询bean
     */
    public void pageQuery(PageBean pageBean);
    /**
     * 根据选中行ID逻辑删除
     * @param ids
     */
    public void delete(String ids);
    /**
     * 修改取派员信息
     * @param model
     */
    public void edit(Staff model);
    /**
     * 查询未作废取派员
     * @return
     */
    public List<Staff> findListNotDelete();
    /**
     * 还原取派员
     * @param ids
     */
    public void restore(String ids);

}
