package com.yinchaxian.bookshop.service;

import com.yinchaxian.bookshop.entity.User;
import com.yinchaxian.bookshop.mapper.UserMapper;
import com.yinchaxian.bookshop.mapper.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;

    public boolean insertUser(User user) {
        return userMapper.insert(user) == 1;
    }

    public boolean deleteUser(int userId) {
        return userMapper.delete(userId) == 1;
    }

    public boolean updateUserInfo(User user) {
        return userMapper.updateInfo(user) == 1;
    }

    public boolean updateUserPassword(int userId, String password, String salt) {
        return userMapper.updatePassword(userId, password, salt) == 1;
    }

    public boolean updateUserActive(int userId, int active) {
        return userMapper.updateActive(userId, active) == 1;
    }

    public boolean isExist(String username) {
        return userMapper.isExist(username) >= 1;
    }

    public User selectUser(int userId) {
        return userMapper.select(userId);
    }

    public User selectUserForLogin(String username) {
        return userMapper.selectInfoByUsername(username);
    }

    public User selectUserForPassword(int userId) {
        return userMapper.selectInfoByUserId(userId);
    }

    public List<User> selectAllUser(int offset, int amount) {
        return userMapper.selectAll(offset, amount);
    }

    public boolean insertUserRole(int userId, int roleId) {
        return userRoleMapper.insert(userId, roleId) == 1;
    }

    public boolean deleteUserRole(int userId, int roleId) {
        return userRoleMapper.delete(userId, roleId) == 1;
    }

    public List<Integer> selectUserRole(int userId) {
        return userRoleMapper.select(userId);
    }
}
