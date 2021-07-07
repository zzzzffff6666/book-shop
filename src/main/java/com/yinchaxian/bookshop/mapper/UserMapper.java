package com.yinchaxian.bookshop.mapper;

import com.yinchaxian.bookshop.entity.User;
import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;

public interface UserMapper {
    @Insert("insert into user " +
            "values(null, #{username}, #{nickname}, #{password}, " +
            "#{email}, #{phone}, #{age}, #{country}, #{detailAddress}, " +
            "#{identity}, #{created}, #{updated})")
    @Options(useGeneratedKeys = true, keyProperty = "userId", keyColumn = "user_id")
    int insert(User user);

    @Delete("delete from user " +
            "where user_id = #{userId}")
    int delete(int userId);

    @Update("update user " +
            "set username = #{username}, " +
            "nickname = #{nickname}, " +
            "email = #{email}, " +
            "age = #{age}, " +
            "identity = #{identity}, " +
            "updated = #{updated} " +
            "where user_id = #{userId}")
    int updateInfo(User user);

    @Update("update user " +
            "set phone = #{phone}, " +
            "country = #{country}, " +
            "detail_address = #{detailAddress}, " +
            "updated = #{updated} " +
            "where user_id = #{userId}")
    int updateAddress(User user);

    @Update("update user " +
            "set password = #{password}, " +
            "updated = #{updated} " +
            "where user_id = #{userId}")
    int updatePassword(int userId, String password, Timestamp updated);

    @Select("select * " +
            "from user " +
            "where user_id = #{userId}")
    @ResultType(User.class)
    User select(int userId);

    @Select("select user_id, username, password " +
            "from user " +
            "where username = #{username}")
    @ResultType(User.class)
    User selectInfo(String username);
}
