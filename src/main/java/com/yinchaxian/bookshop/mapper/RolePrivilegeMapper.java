package com.yinchaxian.bookshop.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RolePrivilegeMapper {
    @Insert("insert into role_privilege " +
            "values(#{roleId}, #{privilegeId})")
    int insert(int roleId, int privilegeId);

    @Delete("delete from role_privilege " +
            "where role_id = #{roleId} " +
            "and privilege_id = #{privilegeId}")
    int delete(int roleId, int privilegeId);

    @Select("select privilege_id " +
            "from role_privilege " +
            "where role_id = #{roleId}")
    List<Integer> select(int roleId);

    @Select("<script>" +
            "select distinct privilege_id " +
            "from role_privilege " +
            "where role_id in " +
            "<foreach item='item' index='index' collection='list' open='(' separator=',' close=')'>" +
            "#{item}" +
            "</foreach> " +
            "</script>")
    List<Integer> selectByRoleList(@Param("list") List<Integer> list);
}
