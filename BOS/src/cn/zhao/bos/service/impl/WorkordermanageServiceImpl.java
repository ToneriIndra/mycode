package cn.zhao.bos.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.zhao.bos.dao.WorkordermanageDao;
import cn.zhao.bos.service.WorkordermanageService;
import cn.zhao.bos.utils.PageBean;
import cn.zhao.bos.vo.Workordermanage;
@Service
@Transactional
public class WorkordermanageServiceImpl implements WorkordermanageService {
    @Autowired
    private WorkordermanageDao workordermanageDao;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private HistoryService historyService;
    
    public List<Workordermanage> findListNotStart() {
        return this.workordermanageDao.findListNotStart();
    }

    public void start(String id) {
        Workordermanage workordermanage = this.workordermanageDao.findById(id);
        workordermanage.setStart("1");
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("业务数据", workordermanage);
        String processDefinitionKey = "transfer";
        String businessKey = id;
        this.runtimeService.startProcessInstanceByKey(processDefinitionKey, businessKey, variables);
    }

    public Workordermanage findById(String workorderManageId) {
        return this.workordermanageDao.findById(workorderManageId);
    }

    public void checkWorkordermanage(String taskId, String check, String workorderManageId) {
        Workordermanage workorderManage = this.workordermanageDao.findById(workorderManageId);
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("check", check);
        String processInstanceId = task.getProcessInstanceId();
        taskService.complete(taskId, variables);
        if(check == "0") {
            workorderManage.setStart("0");
            historyService.deleteHistoricProcessInstance(processInstanceId);
        }
    }

    public void quickSave(Workordermanage model) {
        model.setUpdatetime(new Date());
        this.workordermanageDao.save(model);
    }

    public void pageQuery(PageBean pageBean) {
        this.workordermanageDao.pageQuery(pageBean);
    }

    public void saveBatch(List<Workordermanage> list) {
        for (Workordermanage workordermanage : list) {
            this.workordermanageDao.saveOrUpdate(workordermanage);
        }
    }
    
    
}
