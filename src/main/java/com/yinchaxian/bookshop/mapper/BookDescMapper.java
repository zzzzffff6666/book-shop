package com.yinchaxian.bookshop.mapper;

import com.yinchaxian.bookshop.entity.BookDesc;
import org.apache.ibatis.annotations.*;

public interface BookDescMapper {
    @Insert("insert into book_desc " +
            "values(#{bookId}, #{desc}, #{created}, #{updated})")
    int insert(BookDesc bookDesc);

    @Delete("delete from book_desc " +
            "where book_id = #{bookId}")
    int delete(long bookId);

    @Update("update book_desc " +
            "set desc = #{desc}, " +
            "updated = #{updated} " +
            "where book_id = #{bookId}")
    int update(BookDesc bookDesc);

    @Select("select * " +
            "from book_desc " +
            "where book_id = #{bookId}")
    @ResultType(BookDesc.class)
    BookDesc select(long bookId);
}
