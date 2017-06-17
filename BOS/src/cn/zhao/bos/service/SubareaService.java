package cn.zhao.bos.service;

import java.util.List;

import cn.zhao.bos.utils.PageBean;
import cn.zhao.bos.vo.Subarea;

public interface SubareaService {
    /**
     * 分页查询
     * @param pageBean
     */
    public void pageQuery(PageBean pageBean);
    /**
     * 查询所有
     * @return
     */
    public List<Subarea> findAll();
    /**
     * 查询未与定区关联的分区
     * @return
     */
    public List<Subarea> findNotAssociation();
    /**
     * 保存
     * @param model
     */
    public void save(Subarea model);
    /**
     * 编辑
     * @param model
     */
    public void edit(Subarea model);
    /**
     * 删除
     * @param ids 
     */
    public void delete(String ids);
    /**
     * 批量保存
     * @param subareaList
     */
    public void saveBatch(List<Subarea> subareaList);

}
