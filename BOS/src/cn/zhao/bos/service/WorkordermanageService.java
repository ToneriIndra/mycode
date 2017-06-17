package cn.zhao.bos.service;

import java.util.List;

import cn.zhao.bos.utils.PageBean;
import cn.zhao.bos.vo.Workordermanage;

public interface WorkordermanageService {
    /**
     * 查询未启动的工单流程
     * @return
     */
    public List<Workordermanage> findListNotStart();
    /**
     * 根据工作单ID启动流程实例
     * @param id
     */
    public void start(String id);
    /**
     * 根据ID查询工作单
     * @param workorderManageId
     * @return
     */
    public Workordermanage findById(String workorderManageId);
    /**
     * 审核工作单
     * @param taskId 任务ID
     * @param check 审核状态
     * @param workorderManageId 工作单ID
     */
    public void checkWorkordermanage(String taskId, String check, String workorderManageId);
    /**
     * 工作单快速录入
     * @param model
     */
    public void quickSave(Workordermanage model);
    /**
     * 分页查询
     * @param pageBean
     */
    public void pageQuery(PageBean pageBean);
    /**
     * 批量保存
     * @param list
     */
    public void saveBatch(List<Workordermanage> list);

}
