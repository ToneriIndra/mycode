/*package cn.zhao.bos.web.interceptor;

import cn.zhao.bos.vo.User;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

public class LoginInterceptor extends MethodFilterInterceptor {
    protected String doIntercept(ActionInvocation invocation) throws Exception {
        User user = (User) ActionContext.getContext().getSession().get("loginUser");
        if(user == null) {
            return "loginFail";
        }
        return invocation.invoke();
    }
}
*/