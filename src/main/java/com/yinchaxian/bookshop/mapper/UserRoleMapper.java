package com.yinchaxian.bookshop.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.sql.Timestamp;
import java.util.List;

public interface UserRoleMapper {
    @Insert("insert into user_role " +
            "values(#{userId}, #{roleId})")
    int insert(int userId, int roleId, Timestamp created);

    @Delete("delete from user_role " +
            "where user_id = #{userId} " +
            "and role_id = #{roleId}")
    int delete(int userId, int roleId);

    @Select("select role_id " +
            "from user_role " +
            "where user_id = #{userId}")
    List<Integer> select(int userId);
}
