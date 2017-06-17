package cn.zhao.bos.service;

import java.util.List;

import cn.zhao.bos.utils.PageBean;
import cn.zhao.bos.vo.Region;

public interface RegionService {
    /**
     * 批量保存
     * @param regionList 区域集合
     */
    public void saveBatch(List<Region> regionList);
    /**
     * 分页查询
     * @param pageBean
     */
    public void pageQuery(PageBean pageBean);
    /**
     * 查询所有
     * @return
     */
    public List<Region> findAll();
    /**
     * 根据条件查询区域
     * @param q
     * @return
     */
    public List<Region> findByQ(String q);
    /**
     * 保存
     * @param model
     */
    public void save(Region model);
    /**
     * 修改
     * @param model
     */
    public void edit(Region model);
    /**
     * 删除
     * @param ids
     */
    public void delete(String ids);

}
