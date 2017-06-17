package cn.zhao.bos.web.action;


import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.MailSender;
import org.springframework.stereotype.Controller;

import cn.zhao.bos.service.MailService;
import cn.zhao.bos.service.WorkordermanageService;
import cn.zhao.bos.vo.User;
import cn.zhao.bos.vo.Workordermanage;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
@Controller
@Scope("prototype")
public class TaskAction extends ActionSupport {
    @Autowired
    private TaskService taskService;
    @Autowired
    private WorkordermanageService workordermanagerService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private MailService mailService;
    @Autowired
    private MailSender mailSender;
    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }
    
    //查询组任务
    public String findGroupTask() {
        TaskQuery taskQuery = taskService.createTaskQuery();
        User user = (User) ActionContext.getContext().getSession().get("loginUser");
        String candiateUser = user.getId();
        taskQuery.taskCandidateUser(candiateUser);
        List<Task> list = taskQuery.list();
        ActionContext.getContext().getValueStack().set("list", list);
        return "groupTaskList";
    }
    
    private String taskId;
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
    //拾取组任务
    public String takeTask() {
        User user = (User) ActionContext.getContext().getSession().get("loginUser");
        String userId = user.getId();
        taskService.claim(taskId, userId);
        return "toGroupTaskList";
    }
    //查询个人任务
    public String findPersonalTask() {
        TaskQuery taskQuery = taskService.createTaskQuery();
        User user = (User) ActionContext.getContext().getSession().get("loginUser");
        String assignee = user.getId();
        taskQuery.taskAssignee(assignee);
        List<Task> list = taskQuery.list();
        ActionContext.getContext().getValueStack().set("list", list);
        return "personalTaskList";
    }
    //显示流程变量
    public String showData() throws IOException {
        Map<String,Object> variables = this.taskService.getVariables(taskId);
        ServletActionContext.getResponse().setContentType("text/html;charset=UTF-8");
        ServletActionContext.getResponse().getWriter().print(variables);
        return NONE;
    }
    
    private String check;
    public void setCheck(String check) {
        this.check = check;
    }
    
    public String getTaskId() {
        return taskId;
    }
    //审核任务
    public String checkWorkOrderManage() {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        String workorderManageId = processInstance.getBusinessKey();
        Workordermanage workordermanage = workordermanagerService.findById(workorderManageId);
        if(check == null) {
            ActionContext.getContext().getValueStack().set("map", workordermanage);
            return "check";
        } else {
            workordermanagerService.checkWorkordermanage(taskId,check,workorderManageId);
            this.mailService.mailTask(mailSender,"317432944@qq.com");//给仓库管理员发邮件
            return "toPersonalTaskList";
        }
    }
    

    //出库任务
    public String outStore() {
        taskService.complete(taskId);
        this.mailService.mailTask(mailSender,"317432944@qq.com");//发邮件给取派员
        return "toPersonalTaskList";
    }
    
    //配送
    public String transferGoods() {
        taskService.complete(taskId);
        return "toPersonalTaskList";
    }
    //签收
    public String receive() {
        taskService.complete(taskId);
        return "toPersonalTaskList";
    }
}
