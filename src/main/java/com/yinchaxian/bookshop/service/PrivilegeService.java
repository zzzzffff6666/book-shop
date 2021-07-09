package com.yinchaxian.bookshop.service;

import com.yinchaxian.bookshop.entity.Privilege;
import com.yinchaxian.bookshop.mapper.PrivilegeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrivilegeService {
    @Autowired
    private PrivilegeMapper privilegeMapper;

    public boolean insertPrivilege(Privilege privilege) {
        return privilegeMapper.insert(privilege) == 1;
    }

    public boolean deletePrivilege(int privilegeId) {
        return privilegeMapper.delete(privilegeId) == 1;
    }

    public boolean updatePrivilege(Privilege privilege) {
        return privilegeMapper.update(privilege) == 1;
    }

    public Privilege selectPrivilege(int privilegeId) {
        return privilegeMapper.select(privilegeId);
    }

    public List<Privilege> selectPrivilegeList(List<Integer> list) {
        return privilegeMapper.selectList(list);
    }

    public List<String> selectPrivilegeListUrl(List<Integer> list) {
        return privilegeMapper.selectUrlList(list);
    }
}
