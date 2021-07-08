package com.yinchaxian.bookshop.mapper;

import com.yinchaxian.bookshop.entity.ClickLog;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.sql.Timestamp;
import java.util.List;

public interface ClickLogMapper {
    @Insert("insert into click_log " +
            "values(#{receiveTime}, #{userId}, #{ipAddress}, #{url}, #{referUrl}, #{areaAddress}, " +
            "#{localAddress}, #{browserType}, #{operationSys}, #{sessionId}, #{sessionTimes}, #{csvp})")
    int insert(ClickLog clickLog);

    @Delete("delete from click_log " +
            "where receive_time between #{start} and #{end}")
    int delete(Timestamp start, Timestamp end);

    @Select("select * " +
            "from click_log " +
            "where receive_time between #{start} and #{end}")
    @ResultType(ClickLog.class)
    List<ClickLog> select(Timestamp start, Timestamp end);
}
