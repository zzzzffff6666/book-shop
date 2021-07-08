package com.yinchaxian.bookshop.mapper;

import com.yinchaxian.bookshop.entity.BookRate;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface BookRateMapper {
    @Insert("insert into book_rate " +
            "values(#{userId}, #{bookId}, #{score})")
    int insert(BookRate bookRate);

    @Delete("delete from book_rate " +
            "where user_id = #{userId} " +
            "and book_id = #{bookId}")
    int delete(int userId, long bookId);

    @Update("update book_rate " +
            "set score = #{score} " +
            "where user_id = #{userId} " +
            "and book_id = #{bookId}")
    int update(BookRate bookRate);

    @Select("select * " +
            "from book_rate " +
            "where user_id = #{userId} " +
            "and book_id = #{bookId}")
    BookRate select(int userId, long bookId);

    @Select("select score " +
            "from book_rate " +
            "where user_id = #{userId} " +
            "and book_id = #{bookId}")
    int selectScore(int userId, long bookId);
}
