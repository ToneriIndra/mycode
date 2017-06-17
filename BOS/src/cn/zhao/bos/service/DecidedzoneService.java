package cn.zhao.bos.service;

import cn.zhao.bos.utils.PageBean;
import cn.zhao.bos.vo.Decidedzone;

public interface DecidedzoneService {
    /**
     * 保存
     * @param model
     * @param subareaId 
     */
    public void save(Decidedzone model, String[] subareaId);
    /**
     * 分页查询
     * @param page
     * @param rows
     */
    public void pageQuery(PageBean pageBean);
    /**
     * 修改
     * @param model
     * @param subareaId 
     */
    public void edit(Decidedzone model, String[] subareaId);
    /**
     * 删除
     * @param ids
     */
    public void delete(String ids);
    
}
