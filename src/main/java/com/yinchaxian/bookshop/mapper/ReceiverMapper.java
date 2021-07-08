package com.yinchaxian.bookshop.mapper;

import com.yinchaxian.bookshop.entity.Receiver;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ReceiverMapper {
    @Insert("insert into receiver " +
            "values(#{receiverId}, #{userId}, #{name}, #{phone}, #{state}, #{city}, " +
            "#{district}, #{address}, #{zip}, #{created), #{updated}")
    int insert(Receiver receiver);

    @Delete("delete from receiver " +
            "where receiver_id = #{receiverId}")
    int delete(String receiverId);

    @Update("update receiver " +
            "set name = #{name}, " +
            "phone = #{phone}, " +
            "state = #{state}, " +
            "city = #{city}, " +
            "district = #{district}, " +
            "address = #{address}, " +
            "zip = #{zip}, " +
            "updated = #{updated} " +
            "where receiver_id = #{receiverId}")
    int update(Receiver receiver);

    @Select("select * " +
            "from receiver " +
            "where receiver_id = #{receiverId}")
    @ResultType(Receiver.class)
    Receiver select(String receiverId);

    @Select("select * " +
            "from receiver " +
            "where user_id = #{userId}")
    @ResultType(Receiver.class)
    List<Receiver> selectByUser(int userId);
}
