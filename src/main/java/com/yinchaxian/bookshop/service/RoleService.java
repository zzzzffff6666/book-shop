package com.yinchaxian.bookshop.service;

import com.yinchaxian.bookshop.entity.Role;
import com.yinchaxian.bookshop.mapper.RoleMapper;
import com.yinchaxian.bookshop.mapper.RolePrivilegeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RolePrivilegeMapper rolePrivilegeMapper;

    public boolean insertRole(Role role) {
        return roleMapper.insert(role) == 1;
    }

    public boolean deleteRole(int roleId) {
        return roleMapper.delete(roleId) == 1;
    }

    public boolean updateRole(Role role) {
        return roleMapper.update(role) == 1;
    }

    public Role selectRole(int roleId) {
        return roleMapper.select(roleId);
    }

    public List<Role> selectRoleList(List<Integer> list) {
        return roleMapper.selectList(list);
    }

    public List<String> selectRoleNameList(List<Integer> list) {
        return roleMapper.selectNameList(list);
    }

    public boolean insertRolePrivilege(int roleId, int privilegeId) {
        return rolePrivilegeMapper.insert(roleId, privilegeId) == 1;
    }

    public boolean deleteRolePrivilege(int roleId, int privilegeId) {
        return rolePrivilegeMapper.delete(roleId, privilegeId) == 1;
    }

    public List<Integer> selectRolePrivilege(int roleId) {
        return rolePrivilegeMapper.select(roleId);
    }

    public List<Integer> selectRoleListPrivilege(List<Integer> list) {
        return rolePrivilegeMapper.selectByRoleList(list);
    }
}
