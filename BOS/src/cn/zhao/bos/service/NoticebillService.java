package cn.zhao.bos.service;

import java.util.List;

import cn.zhao.bos.vo.Noticebill;

public interface NoticebillService {
    /**
     * 保存业务通知单
     * @param model
     */
    public void save(Noticebill model);
    /**
     * 查询未关联取派员订单
     * @return
     */
    public List<Noticebill> findNoAssociations();
    /**
     * 关联取派员
     * @param model
     */
    public void associationStaff(Noticebill model);
    /**
     * 根据客户手机号查询
     * @param telephone
     * @return
     */
    public List<Noticebill> findBytelephone(String telephone);

}
