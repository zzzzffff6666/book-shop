package com.yinchaxian.bookshop.mapper;

import com.yinchaxian.bookshop.entity.Reply;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ReplyMapper {
    @Insert("insert into reply " +
            "values(#{replyId}, #{commentId}, #{userId}, #{username}, #{date}, #{content})")
    int insert(Reply reply);

    @Delete("delete from reply " +
            "where reply_id = #{replyId} " +
            "and user_id = #{userId}")
    int delete(int replyId, int userId);

    @Delete("delete from reply " +
            "where reply_id = #{replyId}")
    int deleteByAdmin(int replyId);

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

    @Select("select * " +
            "from reply " +
            "where user_id = #{userId} " +
            "order by date desc")
    @ResultType(Reply.class)
    List<Reply> selectByUser(int userId);

    @Select("select * " +
            "from reply " +
            "where comment_id = #{commentId} " +
            "order by date desc")
    @ResultType(Reply.class)
    List<Reply> selectByComment(int commentId);

    @Select("select user_id " +
            "from reply " +
            "where reply_id = #{replyId}")
    @ResultType(Integer.class)
    int selectUserId(int replyId);
}
