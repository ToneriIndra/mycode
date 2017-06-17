package cn.zhao.bos.dao;

import java.util.List;

import cn.zhao.bos.dao.base.BaseDao;
import cn.zhao.bos.vo.Workordermanage;

public interface WorkordermanageDao extends BaseDao<Workordermanage> {
    /**
     * 查询未启动的工单
     * @return
     */
    public List<Workordermanage> findListNotStart();
    
}
