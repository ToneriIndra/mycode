package cn.zhao.bos.web.action;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
@Controller
@Scope("prototype")
public class ProcessDefinitionAction extends ActionSupport {
    @Autowired
    private RepositoryService repositoryService;
    //查询流程定义
    public String list() {
        ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
        query.latestVersion();
        query.orderByProcessDefinitionName().desc();
        List<ProcessDefinition> list = query.list();
        ActionContext.getContext().getValueStack().set("list", list);
        return "list";
    }
    
    //部署流程定义
    private String zipFile;
    public void setZipFile(String zipFile) {
        this.zipFile = zipFile;
    }
    
    public String deploy() throws FileNotFoundException {
        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();
        deploymentBuilder.addZipInputStream(new ZipInputStream(new FileInputStream(zipFile)));
        deploymentBuilder.deploy();
        return "toList";
    }
    
    //显示流程图片
    private String id;
    public void setId(String id) {
        this.id = id;
    }
    
    public String showImg() {
        InputStream imgStream = repositoryService.getProcessDiagram(id);
        ActionContext.getContext().getValueStack().set("imgStream", imgStream);
        return "showImg";
    }
    
    //删除流程定义
    public String delete() {
        String deltag = "0";
        ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
        query.processDefinitionId(id);
        ProcessDefinition processDefinition = query.singleResult();
        String deploymentId = processDefinition.getDeploymentId();
        try{
            repositoryService.deleteDeployment(deploymentId);
        } catch (Exception e) {
            deltag = "1";
            ActionContext.getContext().getValueStack().set("deltag", deltag);
            ProcessDefinitionQuery query2 = repositoryService.createProcessDefinitionQuery();
            query2.latestVersion();
            query2.orderByProcessDefinitionName().desc();
            List<ProcessDefinition> list = query2.list();
            ActionContext.getContext().getValueStack().set("list", list);
            return "list";
        }
        return "toList";
    }
}
