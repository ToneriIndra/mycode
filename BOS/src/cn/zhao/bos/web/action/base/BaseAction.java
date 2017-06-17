package cn.zhao.bos.web.action.base;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;









import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;

import cn.zhao.bos.utils.PageBean;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class BaseAction<T> extends ActionSupport implements ModelDriven<T> {
    
    protected T model;
    public T getModel() {
            return model;
        }
    protected PageBean pageBean = new PageBean();
    DetachedCriteria dc = null;
    
    protected int rows;
    protected int page;
    public void setRows(int rows) {
        this.rows = rows;
        pageBean.setPageSize(rows);
    }

    public void setPage(int page) {
        this.page = page;
        pageBean.setPageNum(page);
    }

    //初始化类
    public BaseAction() {
        ParameterizedType genericSuperclass = null;
        if(this.getClass().getGenericSuperclass() instanceof ParameterizedType) {
            genericSuperclass = (ParameterizedType) this.getClass().getGenericSuperclass();
        } else {
            genericSuperclass = (ParameterizedType) this.getClass().getSuperclass().getGenericSuperclass();
        }
        Type[] actualTypeArguments = genericSuperclass.getActualTypeArguments();
        Class<T> entityClass = (Class<T>) actualTypeArguments[0];
        try {
            dc = DetachedCriteria.forClass(entityClass);
            pageBean.setDc(dc);
            model = entityClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    //pageBean转json
    public void writePageBean2Json(PageBean pageBean, String[] exludes) throws IOException {
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(exludes);
        JSONObject jsonObj = JSONObject.fromObject(pageBean, jsonConfig);
        String json = jsonObj.toString();
        ServletActionContext.getResponse().setContentType("text/html;charset=UTF-8");
        ServletActionContext.getResponse().getWriter().print(json);
    }
    //list转json
    public void writeList2Json(List list, String[] exludes) throws IOException {
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(exludes);
        JSONArray jsonArr = JSONArray.fromObject(list, jsonConfig);
        String json = jsonArr.toString();
        ServletActionContext.getResponse().setContentType("text/html;charset=UTF-8");
        ServletActionContext.getResponse().getWriter().print(json);
    }
    
    //object转json
    public void writeObject2Json(Object object, String[] exludes) throws IOException {
        JsonConfig  jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(exludes);
        JSONObject jsonObject = JSONObject.fromObject(object, jsonConfig);
        String json = jsonObject.toString();
        ServletActionContext.getResponse().setContentType("text/html;charset=UTF-8");
        ServletActionContext.getResponse().getWriter().print(json);
    }
}
