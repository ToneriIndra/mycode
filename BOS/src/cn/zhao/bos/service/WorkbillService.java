package cn.zhao.bos.service;

import cn.zhao.bos.utils.PageBean;

public interface WorkbillService {
    /**
     * 分页查询
     * @param pageBean
     */
    public void pageQuery(PageBean pageBean);
    /**
     * 消单
     * @param ids
     */
    public void delete(String ids);

}
