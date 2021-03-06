package com.yinchaxian.bookshop.mapper;

import com.yinchaxian.bookshop.entity.Role;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface RoleMapper {
    @Insert("insert into role " +
            "values(null, #{name}, #{description})")
    @Options(useGeneratedKeys = true, keyProperty = "roleId", keyColumn = "role_id")
    int insert(Role role);

    @Delete("delete from role " +
            "where role_id = #{roleId}")
    int delete(int roleId);

    @Update("update role " +
            "set name = #{name}, " +
            "description = #{description} " +
            "where role_id = #{roleId}")
    int update(Role role);

    @Select("select * " +
            "from role " +
            "where role_id = #{roleId}")
    @ResultType(Role.class)
    Role select(int role_id);

    @Select("select * " +
            "from role")
    @ResultType(Role.class)
    List<Role> selectAll();

    @Select("<script> " +
            "select * " +
            "from role " +
            "where role_id in " +
            "<foreach item='item' index='index' collection='list' open='(' separator=',' close=')'>" +
            "#{item}" +
            "</foreach> " +
            "</script>")
    @ResultType(Role.class)
    List<Role> selectList(@Param("list") List<Integer> list);

    @Select("<script> " +
            "select name " +
            "from role " +
            "where role_id in " +
            "<foreach item='item' index='index' collection='list' open='(' separator=',' close=')'>" +
            "#{item}" +
            "</foreach> " +
            "</script>")
    List<String> selectNameList(@Param("list") List<Integer> list);
}
