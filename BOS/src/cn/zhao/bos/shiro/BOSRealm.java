package cn.zhao.bos.shiro;

import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.zhao.bos.dao.FunctionDao;
import cn.zhao.bos.dao.UserDao;
import cn.zhao.bos.vo.Function;
import cn.zhao.bos.vo.User;
public class BOSRealm extends AuthorizingRealm {
    @Autowired
    private UserDao userDao;
    @Autowired
    private FunctionDao functionDao;
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        UsernamePasswordToken uptoken = (UsernamePasswordToken) token;
        String userName = uptoken.getUsername();
        User user = this.userDao.findByUserName(userName);
        if(user == null) {
            return null;
        } else {
            String password = user.getPassword();
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, password,this.getClass().getSimpleName());
            return info;
        }
    }
    
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        User user = (User) principals.getPrimaryPrincipal();
        List<Function> list = null;
        if(user.getUsername().equals("admin")) {
            list = this.functionDao.findAll();
        } else {
            list = this.functionDao.findListByUserId(user.getId());
        }
        for(Function function:list) {
            info.addStringPermission(function.getCode());
        }
        return info;
    }


}
