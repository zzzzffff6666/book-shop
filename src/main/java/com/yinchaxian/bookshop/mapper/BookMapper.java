package com.yinchaxian.bookshop.mapper;

import com.yinchaxian.bookshop.entity.Book;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface BookMapper {
    @Insert("insert into book " +
            "values(null, #{cateId}, #{cname}, #{storeId}, #{name}, #{imageUrl}, " +
            "#{outline}, #{author}, #{press}, #{version}, #{publishDate}, #{isbn}, " +
            "#{pages}, #{catalog}, #{packStyle}, #{storeMount}, #{price}, " +
            "#{marketPrice}, #{memberPrice}, #{discount}, #{dealMount}, #{lookMount})")
    @Options(useGeneratedKeys = true, keyProperty = "bookId", keyColumn = "book_id")
    int insert(Book book);

    @Delete("delete from book " +
            "where book_id = #{bookId}")
    int delete(long bookId);

    @Delete("delete from book " +
            "where store_id = #{storeId}")
    int deleteStore(int storeId);

    @Update("update book " +
            "set cate_id = #{cateId}, " +
            "cname = #{cname}, " +
            "name = #{name}, " +
            "image_url = #{imageUrl}, " +
            "outline = #{outline}, " +
            "author = #{author}, " +
            "press = #{press}, " +
            "version = #{version}, " +
            "publish_date = #{publishDate}, " +
            "isbn = #{isbn}, " +
            "pages = #{pages}, " +
            "catalog = #{catalog}, " +
            "pack_style = #{packStyle}, " +
            "store_mount = #{storeMount} " +
            "where book_id = #{bookId} " +
            "and store_id = #{storeId}")
    int updateInfo(Book book);

    @Update("update book " +
            "set price = #{price}, " +
            "market_price = #{marketPrice}, " +
            "member_price = #{memberPrice}, " +
            "discount = #{discount} " +
            "where book_id = #{bookId}, " +
            "and store_id = #{storeId}")
    int updatePrice(Book book);

    @Update("update book " +
            "set market_price = price * #{discount}, " +
            "member_price = price * #{discount} * 0.9, " +
            "discount = #{discount} " +
            "where store_id = #{storeId}")
    int updatePriceOnce(int storeId, float discount);

    @Update("update book " +
            "set deal_mount = deal_amount + #{addition} " +
            "where book_id = #{bookId}")
    int updateDeal(long bookId, int addition);

    @Update("update book " +
            "set look_mount = look_mount + #{addition} " +
            "where book_id = #{bookId}")
    int updateLook(long bookId, int addition);

    @Select("select * " +
            "from book " +
            "where book_id =#{bookId}")
    @ResultType(Book.class)
    Book select(long bookId);

    @Select("select * " +
            "from book")
    @ResultType(Book.class)
    List<Book> selectAll();

    @Select("select * " +
            "from book " +
            "order by deal_mount desc")
    @ResultType(Book.class)
    List<Book> selectAllByDealMount();

    @Select("select * " +
            "from book " +
            "where cate_id = #{cateId}")
    @ResultType(Book.class)
    List<Book> selectByCate(int cateId);

    @Select("select * " +
            "from book " +
            "where store_id = #{storeId}")
    @ResultType(Book.class)
    List<Book> selectByStore(int storeId);

    @Select("select * " +
            "from book " +
            "where name like '%${name}%'")
    @ResultType(Book.class)
    List<Book> searchByName(String name);

    @Select("select store_id " +
            "from book " +
            "where book_id = #{bookId}")
    int selectStoreId(long bookId);
}
