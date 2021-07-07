package com.yinchaxian.bookshop.mapper;

import com.yinchaxian.bookshop.entity.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface CommentMapper {
    @Insert("insert into comment " +
            "values(null, #{bookId}, #{userId}, #{username}, #{date}, #{content})")
    @Options(useGeneratedKeys = true, keyProperty = "commentId", keyColumn = "comment_id")
    int insert(Comment comment);

    @Delete("delete from comment " +
            "where comment_id = #{commentId} " +
            "and user_id = #{userId}")
    int delete(int commentId, int userId);

    @Update("update comment " +
            "set content = #{content}, " +
            "date = #{date} " +
            "where comment_id = #{commentId} " +
            "and user_id = #{userId}")
    int update(Comment comment);

    @Select("select * " +
            "from comment " +
            "where comment_id = #{commentId}")
    @ResultType(Comment.class)
    Comment select(int commentId);

    @Select("select * " +
            "from comment " +
            "where user_id = #{userId} " +
            "order by date desc " +
            "limit #{offset}, #{amount}")
    @ResultType(Comment.class)
    List<Comment> selectByUser(int userId, int offset, int amount);

    @Select("select * " +
            "from comment " +
            "where book_id = #{bookId} " +
            "order by date desc " +
            "limit #{offset}, #{amount}")
    @ResultType(Comment.class)
    List<Comment> selectByBook(int book_id, int offset, int amount);
}
