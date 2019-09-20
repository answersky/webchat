package cn.liu.main.realm;

import cn.liu.webChat.domain.UserInfo;
import cn.liu.webChat.service.IUserInfoService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * @author answer
 * 2018/4/12
 */
public class ShiroDbRealm extends AuthorizingRealm {
    private IUserInfoService userService;

    public IUserInfoService getUserValidateService() {
        return userService;
    }

    public void setUserValidateService(IUserInfoService userService) {
        this.userService = userService;
    }

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        UserInfo user = (UserInfo) principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//        User user1 = userService.findUserByName(user.getUsername());
//        String role=user1.getRole();
//        info.addRole(role);
        return info;
    }

    //验证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = (String) authenticationToken.getPrincipal();

        UserInfo user = userService.findUserByUsername(username);
        if (user == null) {
            return null;
        }
        String password = user.getPassword();
        return new SimpleAuthenticationInfo(user, password, this.getName());

    }
}
