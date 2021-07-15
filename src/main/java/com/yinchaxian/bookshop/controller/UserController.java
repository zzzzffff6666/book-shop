package com.yinchaxian.bookshop.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinchaxian.bookshop.entity.Receiver;
import com.yinchaxian.bookshop.entity.Role;
import com.yinchaxian.bookshop.entity.User;
import com.yinchaxian.bookshop.http.ErrorMessage;
import com.yinchaxian.bookshop.http.Result;
import com.yinchaxian.bookshop.service.ReceiverService;
import com.yinchaxian.bookshop.service.RoleService;
import com.yinchaxian.bookshop.service.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @author: zhang
 * @date: 2021/7/10 13:05
 * @description: 收货人，用户，角色和权限相关的访问控制器
 */
@RestController
public class UserController {
    private static final int userPageAmount = 20;
    private static final int receiverPageAmount = 10;

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private ReceiverService receiverService;

    //
    // 登陆注册部分：
    //

    /**
     * 登陆
     * @param params 登陆的表单参数
     * @param session session信息
     * @return 是否成功
     */
    @PostMapping("/login")
    public Result login(@RequestBody Map<String, String> params, HttpSession session) {
        //获取subject
        Subject subject = SecurityUtils.getSubject();
        //将前端账号和密码包装成令牌
        UsernamePasswordToken token = new UsernamePasswordToken(params.get("username"), params.get("password"));
        try {
            // 传入令牌，令牌中有前端输入的账号密码，调用shiro的配置函数与数据库的账号密码比对
            // 如果比对错误将抛出异常， 正确则继续执行
            subject.login(token);
            User user = userService.selectUserForLogin(params.get("username"));
            session.setAttribute("userId", user.getUserId());
            session.setAttribute("username", user.getUsername());
            user.setPassword((subject.hasRole("super") || subject.hasRole("root")) + "");
            user.setSalt(null);
            return Result.success(user);
        } catch (AuthenticationException e) {
            return Result.error(ErrorMessage.loginError);
        }
    }

    /**
     * 注册
     * @param user 注册提供的用户信息
     * @return 是否成功
     */
    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        if (userService.isExist(user.getUsername())) return Result.error(ErrorMessage.nameError);
        String salt = RandomStringUtils.random(30, true, true);
        String encodedPassword = new SimpleHash("md5", user.getPassword(), salt, 2).toString();
        user.setSalt(salt);
        user.setPassword(encodedPassword);
        boolean suc = userService.insertUser(user);
        if (suc) {
            // 注册成功给该用户分配普通用户角色
            userService.insertUserRole(user.getUserId(), 7);
            return Result.success();
        }
        else return Result.error(ErrorMessage.registerError);
    }

    //
    // User部分：
    //

    /**
     * 删除用户
     * @param userId 用户Id
     * @param session session信息
     * @return 是否成功
     */
    @DeleteMapping("/user/{userId}")
    @RequiresPermissions(value = {"user:delete", "user:*"}, logical = Logical.OR)
    public Result deleteUser(@PathVariable("userId") int userId, HttpSession session) {
        if (userId == 1) return Result.error(ErrorMessage.authError);
        int id = (int) session.getAttribute("userId");
        if (id != userId) {
            Subject subject = SecurityUtils.getSubject();
            if (!subject.isPermitted("user:*")) {
                return Result.error(ErrorMessage.authError);
            }
        }
        boolean suc = userService.deleteUser(userId);
        return suc ? Result.success() : Result.error(ErrorMessage.deleteError);
    }

    /**
     * 更新用户信息
     * @param user 用户的新信息
     * @param session session信息
     * @return 是否成功
     */
    @PutMapping("/user/info")
    @RequiresPermissions("user:update")
    public Result updateInfo(@RequestBody User user, HttpSession session) {
        int id = (int) session.getAttribute("userId");
        if (id != user.getUserId()) {
            return Result.error(ErrorMessage.authError);
        }

        if (userService.isExist(user.getUsername())) {
            return Result.error(ErrorMessage.nameError);
        }
        boolean suc = userService.updateUserInfo(user);
        return suc ? Result.success() : Result.error(ErrorMessage.updateError);
    }

    /**
     * 更新用户密码
     * @param params 新旧密码信息
     * @param session session信息
     * @return 是否成功
     */
    @PutMapping("/user/password")
    @RequiresPermissions("user:update")
    public Result updatePassword(@RequestBody Map<String, String> params, HttpSession session) {
        int id = (int) session.getAttribute("userId");
        int userId = Integer.parseInt(params.get("userId"));
        if (id != userId) {
            return Result.error(ErrorMessage.authError);
        }

        User user = userService.selectUserForPassword(userId);
        String oldPassword = params.get("old");
        String encodePassword = new SimpleHash("md5", oldPassword, user.getSalt(), 2).toString();
        if (!encodePassword.equals(user.getPassword())) {
            return Result.error(ErrorMessage.passwordError);
        }
        String newPassword = params.get("new");
        String salt = RandomStringUtils.random(30, true, true);
        String newEncodePassword = new SimpleHash("md5", newPassword, salt, 2).toString();
        boolean suc = userService.updateUserPassword(userId, newEncodePassword, salt);
        return suc ? Result.success() : Result.error(ErrorMessage.updateError);
    }

    /**
     * 更改用户的活跃状态， 活跃状态为 0 则表示该用户被冻结
     * @param params 用户ID和活跃状态
     * @return 是否成功
     */
    @PutMapping("/user/active")
    @RequiresPermissions("user:*")
    public Result updateUserActive(@RequestBody Map<String, String> params) {
        int userId = Integer.parseInt(params.get("userId"));
        int active = Integer.parseInt(params.get("active"));
        boolean suc = userService.updateUserActive(userId, active);
        return suc ? Result.success() : Result.error(ErrorMessage.updateError);
    }

    /**
     * 查询用户信息
     * @param userId 用户ID
     * @return 查询结果
     */
    @GetMapping("/user/{userId}")
    @RequiresPermissions(value = {"user:select", "user:*"}, logical = Logical.OR)
    public Result selectUser(@PathVariable("userId") int userId) {
        User user = userService.selectUser(userId);
        return Result.success(user);
    }

    /**
     * 查询所有用户
     * @param page 页数，默认为 1
     * @return 查询结果
     */
    @GetMapping(value = {"/user/list", "/user/list/{page}"})
    @RequiresPermissions("user:*")
    public Result selectAllUser(@PathVariable(value = "page", required = false) Integer page) {
        if (page == null) page = 1;
        PageHelper.startPage(page, userPageAmount);
        PageInfo<User> list = new PageInfo<>(userService.selectAllUser());
        return Result.success(list);
    }

    //
    // UserRole部分：
    //

    /**
     * 给用户分配角色
     * @param list 角色ID列表
     * @param userId 用户ID
     * @return 是否成功
     */
    @PostMapping("/user_role/{userId}")
    @RequiresPermissions(value = {"user_role:*", "user_role:insert"}, logical = Logical.OR)
    public Result insertUserRole(@RequestBody List<Integer> list, @PathVariable("userId") int userId) {
        StringBuilder msg = new StringBuilder();
        boolean suc = true;
        for (int roleId : list) {
            if (!userService.insertUserRole(userId, roleId)) {
                msg.append(roleId).append(ErrorMessage.insertError).append("\n");
                suc = false;
            }
        }
        return suc ? Result.success() : Result.error(msg.toString());
    }

    /**
     * 删除用户的角色
     * @param list 角色ID列表
     * @param userId 用户ID
     * @return 是否成功
     */
    @DeleteMapping("/user_role/{userId}")
    @RequiresPermissions(value = {"user_role:*", "user_role:delete"}, logical = Logical.OR)
    public Result deleteUserRole(@RequestBody List<Integer> list, @PathVariable("userId") int userId) {
        if (userId == 1) return Result.error(ErrorMessage.authError);
        StringBuilder msg = new StringBuilder();
        boolean suc = true;
        for (int roleId : list) {
            if (!userService.deleteUserRole(userId, roleId)) {
                msg.append(roleId).append(ErrorMessage.deleteError).append("\n");
                suc = false;
            }
        }
        return suc ? Result.success() : Result.error(msg.toString());
    }

    /**
     * 查询用户的角色信息
     * @param userId 用户ID
     * @return 查询结果
     */
    @GetMapping("/user_role/{userId}")
    @RequiresPermissions(value = {"user_role:*", "user_role:select"}, logical = Logical.OR)
    public Result selectUserRole(@PathVariable("userId") int userId) {
        PageHelper.startPage(1, 8);
        PageInfo<Role> list = new PageInfo<>(roleService.selectRoleList(userService.selectUserRole(userId)));
        return Result.success(list);
    }

    //
    // Role部分：
    //

    /**
     * 查询角色信息
     * @param roleId 角色ID
     * @return 查询结果
     */
    @GetMapping("/role/{roleId}")
    @RequiresPermissions(value = {"role:*", "role:select"}, logical = Logical.OR)
    public Result selectRole(@PathVariable("roleId") int roleId) {
        Role role = roleService.selectRole(roleId);
        return Result.success(role);
    }

    /**
     * 查询所有角色
     * @return 查询结果
     */
    @GetMapping("/role/list")
    @RequiresPermissions(value = {"role:*", "role:select"}, logical = Logical.OR)
    public Result selectAllRole() {
        PageHelper.startPage(1, 8);
        PageInfo<Role> list = new PageInfo<>(roleService.selectAllRole());
        return Result.success(list);
    }

    //
    // Receiver部分：
    //

    /**
     * 新建收件人
     * @param receiver 收件人信息
     * @param session session信息
     * @return 是否成功
     */
    @PostMapping("/receiver")
    @RequiresPermissions(value = {"receiver:insert", "receiver:*"}, logical = Logical.OR)
    public Result insertReceiver(@RequestBody Receiver receiver, HttpSession session) {
        int id = (int) session.getAttribute("userId");
        receiver.setUserId(id);
        boolean suc = receiverService.insertReceiver(receiver);
        return suc ? Result.success() : Result.error(ErrorMessage.insertError);
    }

    /**
     * 删除收件人
     * @param receiverId 收件人ID
     * @param session session信息
     * @return 是否成功
     */
    @DeleteMapping("/receiver/{receiverId}")
    @RequiresPermissions(value = {"receiver:delete", "receiver:*"}, logical = Logical.OR)
    public Result deleteReceiver(@PathVariable("receiverId") int receiverId, HttpSession session) {
        int id = (int) session.getAttribute("userId");
        int userId = receiverService.selectReceiverUserId(receiverId);
        if (id != userId) {
            return Result.error(ErrorMessage.authError);
        }
        boolean suc = receiverService.deleteReceiver(receiverId);
        return suc ? Result.success() : Result.error(ErrorMessage.deleteError);
    }

    /**
     * 更新收件人信息
     * @param receiver 收件人信息
     * @param session session信息
     * @return 是否成功
     */
    @PutMapping("/receiver")
    @RequiresPermissions(value = {"receiver:update", "receiver:*"}, logical = Logical.OR)
    public Result updateReceiver(@RequestBody Receiver receiver, HttpSession session) {
        int id = (int) session.getAttribute("userId");
        if (id != receiver.getUserId()) {
            return Result.error(ErrorMessage.authError);
        }
        boolean suc = receiverService.updateReceiver(receiver);
        return suc ? Result.success() : Result.error(ErrorMessage.updateError);
    }

    /**
     * 查询收件人信息
     * @param receiverId 收件人ID
     * @return 查询结果
     */
    @GetMapping("/receiver/{receiverId}")
    @RequiresPermissions(value = {"receiver:select", "receiver:*"}, logical = Logical.OR)
    public Result selectReceiver(@PathVariable("receiverId") int receiverId) {
        Receiver receiver = receiverService.selectReceiver(receiverId);
        return Result.success(receiver);
    }

    /**
     * 查询当前用户所有收件人信息
     * @param page 页数，默认为 1
     * @param session session信息
     * @return 查询结果
     */
    @GetMapping(value = {"/receiver/list", "/receiver/list/{page}"})
    @RequiresPermissions(value = {"receiver:select", "receiver:*"}, logical = Logical.OR)
    public Result selectReceiverByUser(@PathVariable(value = "page", required = false) Integer page, HttpSession session) {
        if (page == null) page = 1;
        int id = (int) session.getAttribute("userId");
        PageHelper.startPage(page, receiverPageAmount);
        PageInfo<Receiver> list = new PageInfo<>(receiverService.selectReceiverByUser(id));
        return Result.success(list);
    }

}
