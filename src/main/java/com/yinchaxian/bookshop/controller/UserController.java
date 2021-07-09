package com.yinchaxian.bookshop.controller;

import com.yinchaxian.bookshop.entity.User;
import com.yinchaxian.bookshop.http.Result;
import com.yinchaxian.bookshop.service.UserService;
import com.yinchaxian.bookshop.utils.TimeUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {
    @Value("@{default.page.amount.user}")
    private int userPageAmount;

    @Value("@{default.hint.error.login}")
    private String loginError;
    @Value("@{default.hint.error.register}")
    private String registerError;
    @Value("@{default.hint.error.name}")
    private String nameError;
    @Value("@{default.hint.error.password}")
    private String passwordError;
    @Value("@{default.hint.error.insert}")
    private String insertError;
    @Value("@{default.hint.error.delete}")
    private String deleteError;
    @Value("@{default.hint.error.update}")
    private String updateError;
    @Value("@{default.hint.error.select}")
    private String selectError;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        //获取subject
        Subject subject = SecurityUtils.getSubject();
        //将前端账号和密码包装成令牌
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        try {
            // 传入令牌，令牌中有前端输入的账号密码，调用shiro的配置函数与数据库的账号密码比对
            // 如果比对错误将抛出异常， 正确则继续执行
            subject.login(token);
            return Result.success();
        } catch (AuthenticationException e) {
            return Result.error(loginError);
        }
    }

    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        if (userService.isExist(user.getUsername())) return Result.error(nameError);
        String salt = RandomStringUtils.random(30, true, true);
        String encodedPassword = new SimpleHash("md5", user.getPassword(), salt, 2).toString();
        user.setSalt(salt);
        user.setPassword(encodedPassword);
        Timestamp t = TimeUtil.getCurrentTime();
        user.setCreated(t);
        user.setUpdated(t);
        boolean suc = userService.insertUser(user);
        return suc ? Result.success() : Result.error(registerError);
    }

    @DeleteMapping("/user/{userId}")
    public Result deleteUser(@PathVariable("userId") int userId) {
        boolean suc = userService.deleteUser(userId);
        return suc ? Result.success() : Result.error(deleteError);
    }

    @PutMapping("/user/{userId}/info")
    public Result updateInfo(@RequestBody User user, @PathVariable("userId") int userId) {
        user.setUserId(userId);
        boolean suc = userService.updateUserInfo(user);
        return suc ? Result.success() : Result.error(updateError);
    }

    @PutMapping("/user/{userId}/password")
    public Result updatePassword(@RequestBody Map<String, String> params, @PathVariable("userId") int userId) {
        User user = userService.selectUserForPassword(userId);
        String oldPassword = params.get("old");
        String encodePassword = new SimpleHash("md5", oldPassword, user.getSalt(), 2).toString();
        if (!encodePassword.equals(user.getPassword())) {
            return Result.error(passwordError);
        }
        String newPassword = params.get("new");
        String salt = RandomStringUtils.random(30, true, true);
        String newEncodePassword = new SimpleHash("md5", newPassword, salt, 2).toString();
        boolean suc = userService.updateUserPassword(userId, newEncodePassword, salt, TimeUtil.getCurrentTime());
        return suc ? Result.success() : Result.error(updateError);
    }

    @GetMapping("/user/{userId}")
    public Result selectUser(@PathVariable("userId") int userId) {
        User user = userService.selectUser(userId);
        return user == null ? Result.error(selectError) : Result.success(user);
    }

    @GetMapping(value = {"/user/list", "/user/list/{page}"})
    public Result selectAllUser(@PathVariable(value = "page", required = false) Integer page) {
        if (page == null) page = 1;
        List<User> list = userService.selectAllUser((page - 1) * userPageAmount, userPageAmount);
        return list.isEmpty() ? Result.error(selectError) : Result.success(list);
    }
}
