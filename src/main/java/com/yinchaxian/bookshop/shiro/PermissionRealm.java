package com.yinchaxian.bookshop.shiro;

import com.yinchaxian.bookshop.entity.User;
import com.yinchaxian.bookshop.service.PrivilegeService;
import com.yinchaxian.bookshop.service.RoleService;
import com.yinchaxian.bookshop.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PermissionRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PrivilegeService privilegeService;

    @Override
    public String getName() {
        return "Permission Realm";
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        User user = (User) principalCollection.getPrimaryPrincipal();

        List<Integer> roleIdList = userService.selectUserRole(user.getUserId());
        List<String> roles = roleService.selectRoleNameList(roleIdList);
        List<Integer> privilegeIdList = roleService.selectRoleListPrivilege(roleIdList);
        List<String> privileges = privilegeService.selectPrivilegePermissionList(privilegeIdList);

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRoles(roles);
        info.addStringPermissions(privileges);
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        User user = userService.selectUserForLogin(token.getUsername());
        if (user != null) {
            return new SimpleAuthenticationInfo(user, user.getPassword(), ByteSource.Util.bytes(user.getSalt()), getName());
        }
        return null;
    }
}
