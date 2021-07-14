package com.yinchaxian.bookshop.mapper;

import com.yinchaxian.bookshop.entity.ClickLog;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.sql.Timestamp;
import java.util.List;

public interface ClickLogMapper {
    @Select("select * " +
            "from click_log " +
            "where receive_time between #{start} and #{end}")
    @ResultType(ClickLog.class)
    List<ClickLog> selectByTime(Timestamp start, Timestamp end);

    @Select("select * " +
            "from click_log " +
            "where user_id = #{userId} " +
            "order by receive_time desc")
    @ResultType(ClickLog.class)
    List<ClickLog> selectByUserId(String userId);
}
