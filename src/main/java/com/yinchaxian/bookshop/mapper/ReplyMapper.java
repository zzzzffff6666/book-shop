package com.yinchaxian.bookshop.mapper;

import com.yinchaxian.bookshop.entity.Reply;
import org.apache.ibatis.annotations.*;

public interface ReplyMapper {
    @Insert("insert into reply " +
            "values(#{replyId}, #{userId}, #{username}, #{date}, #{content})")
    int insert(Reply reply);

    @Delete("delete from reply " +
            "where reply_id = #{replyId} " +
            "and user_id = #{userId}")
    int delete(int replyId, int userId);

    @Update("update reply " +
            "set date = #{date}, " +
            "content = #{content} " +
            "where reply_id = #{replyId} " +
            "and user_id = #{userId}")
    int update(Reply reply);

    @Select("select * " +
            "from reply " +
            "where reply_id = #{replyId}")
    @ResultType(Reply.class)
    Reply select(int replyId);
}
