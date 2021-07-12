package com.yinchaxian.bookshop.mapper;

import com.yinchaxian.bookshop.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserMapper {
    @Insert("insert into user " +
            "values(null, #{username}, #{nickname}, #{password}, " +
            "#{email}, #{phone}, #{age}, #{country}, #{address}, " +
            "#{identity}, #{salt}, 1)")
    @Options(useGeneratedKeys = true, keyProperty = "userId", keyColumn = "user_id")
    int insert(User user);

    @Delete("delete from user " +
            "where user_id = #{userId}")
    int delete(int userId);

    @Update("update user " +
            "set username = #{username}, " +
            "nickname = #{nickname}, " +
            "email = #{email}, " +
            "phone = #{phone}, " +
            "age = #{age}, " +
            "country = #{country}, " +
            "address = #{address}, " +
            "identity = #{identity} " +
            "where user_id = #{userId}")
    int updateInfo(User user);

    @Update("update user " +
            "set password = #{password}, " +
            "salt = #{salt} " +
            "where user_id = #{userId}")
    int updatePassword(int userId, String password, String salt);

    @Update("update user " +
            "set active = #{active} " +
            "where user_id = #{userId}")
    int updateActive(int userId, int active);

    @Select("select user_id, username, nickname, email, phone, age, country, address, identity " +
            "from user " +
            "where user_id = #{userId}")
    @ResultType(User.class)
    User select(int userId);

    @Select("select user_id, username, password, salt " +
            "from user " +
            "where username = #{username}")
    @ResultType(User.class)
    User selectInfoByUsername(String username);

    @Select("select user_id, username, password, salt " +
            "from user " +
            "where user_id = #{userId}")
    @ResultType(User.class)
    User selectInfoByUserId(int userId);

    @Select("select user_id, username, nickname, email, phone, age, country, address, identity, active " +
            "from user " +
            "limit #{offset}, #{amount}")
    @ResultType(User.class)
    List<User> selectAll(int offset, int amount);

    @Select("select count(*) " +
            "from user " +
            "where username = #{username}")
    int isExist(String username);
}
