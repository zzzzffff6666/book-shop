package com.yinchaxian.bookshop.mapper;

import com.yinchaxian.bookshop.entity.Order;
import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;
import java.util.List;

public interface OrderMapper {
    @Insert("insert into order " +
            "values(#{orderId}, #{userId}, #{receiverId}, #{storeId}, #{bookId}, " +
            "#{bookName}, #{orderMount}, #{price}, #{postFee}, #{paymentType}, " +
            "0, #{created}, null, null, #{updated})")
    int insert(Order order);

    @Delete("delete from order " +
            "where order_id = #{orderId} " +
            "and status in (0, 4)")
    int delete(String orderId);

    @Delete("delete from order " +
            "where order_id = #{orderId}")
    int deleteByAdmin(String orderId);

    @Update("update order " +
            "set receiver_id = #{receiverId}, " +
            "price = #{price}, " +
            "post_fee = #{postFee}, " +
            "order_mount = #{orderMount}, " +
            "updated = #{updated} " +
            "where order_id = #{order_id}")
    int updateInfo(Order order);

    @Update("update order " +
            "set payment_type = #{paymentType}, " +
            "status = 1, " +
            "paid = #{paid}, " +
            "updated = #{paid} " +
            "where order_id = #{orderId} " +
            "and user_id = #{userId}")
    int updatePay(String orderId, int userId, int paymentType, Timestamp paid);

    @Update("update order " +
            "set status = 2, " +
            "updated = #{updated} " +
            "where order_id = #{orderId} " +
            "and store_id = #{storeId}")
    int updateDelivery(String orderId, int storeId, Timestamp updated);

    @Update("update order " +
            "set status = 3, " +
            "finished = #{finished}, " +
            "updated = #{finished} " +
            "where order_id = #{orderId}")
    int updateFinish(String orderId, Timestamp finished);

    @Update("update order " +
            "set status = 4 " +
            "updated = #{updated} " +
            "where order_id = #{orderId}")
    int updateError(String orderId, Timestamp updated);

    @Select("select * " +
            "from order " +
            "where order_id = #{orderId}")
    @ResultType(Order.class)
    Order select(String orderId);

    @Select("select * " +
            "from order " +
            "where user_id = #{userId} " +
            "order by created desc " +
            "limit #{offset}, #{amount}")
    @ResultType(Order.class)
    List<Order> selectByUser(int userId, int offset, int amount);

    @Select("select * " +
            "from order " +
            "where user_id = #{userId} " +
            "and status = #{status} " +
            "order by created desc " +
            "limit #{offset}, #{amount}")
    @ResultType(Order.class)
    List<Order> selectByUserAndStatus(int userId, int status, int offset, int amount);

    @Select("select * " +
            "from order " +
            "where store_id = #{storeId} " +
            "order by created desc " +
            "limit #{offset}, #{amount}")
    @ResultType(Order.class)
    List<Order> selectByStore(int storeId, int offset, int amount);

    @Select("select * " +
            "from order " +
            "where store_id = #{storeId} " +
            "and status = #{status} " +
            "order by created desc " +
            "limit #{offset}, #{amount}")
    @ResultType(Order.class)
    List<Order> selectByStoreAndStatus(int storeId, int status, int offset, int amount);

    @Select("select user_id " +
            "from order " +
            "where order_id = #{orderId}")
    int selectUserId(String orderId);

    @Select("select store_id " +
            "from order " +
            "where order_id = #{orderId}")
    int selectStoreId(String orderId);

    @Select("select book_id " +
            "from order " +
            "where order_id = #{orderId}")
    long selectBookId(String orderId);

    @Select("select count(*) " +
            "from order " +
            "where user_id = #{userId} " +
            "and book_id = #{bookId} " +
            "and status = 3 " +
            "group by (orderId)")
    int selectTimesByUserAndBook(int userId, long bookId);
}
