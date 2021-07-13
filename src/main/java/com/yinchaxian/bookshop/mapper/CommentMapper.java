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

    @Delete("delete from comment " +
            "where comment_id = #{commentId}")
    int deleteByAdmin(int commentId);

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
            "order by date desc")
    @ResultType(Comment.class)
    List<Comment> selectByUser(int userId);

    @Select("select * " +
            "from comment " +
            "where book_id = #{bookId} " +
            "order by date desc")
    @ResultType(Comment.class)
    List<Comment> selectByBook(long bookId);

    @Select("select user_id " +
            "from comment " +
            "where comment_id = #{commentId}")
    int selectUserId(int commentId);
}
