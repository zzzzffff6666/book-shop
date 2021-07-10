package com.yinchaxian.bookshop.mapper;

import com.yinchaxian.bookshop.entity.Order;
import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;
import java.util.List;

public interface OrderMapper {
    @Insert("insert into order " +
            "values(#{orderId}, #{userId}, #{receiverId}, #{price}, #{postFee}, #{paymentType}, {orderMount}, #{shippingName}, #{shippingCode}, #{buyerFeedback}, #{buyerRate}, 0, #{created}, null, null, null, #{updated})")
    int insert(Order order);

    @Delete("delete from order " +
            "where order_id = #{orderId} " +
            "and user_id = #{userId}")
    int delete(String orderId);

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
            "updated = #{updated} " +
            "where order_id = #{orderId}")
    int updatePay(Order order);

    @Update("update order " +
            "set shipping_name = #{shippingName}, " +
            "shipping_code = #{shippingCode}, " +
            "status = 2, " +
            "updated = #{updated} " +
            "where order_id = #{orderId}")
    int updateShipping(Order order);

    @Update("update order " +
            "set status = 3, " +
            "finished = #{finished}, " +
            "updated = #{finished} " +
            "where order_id = #{orderId}")
    int updateFinish(String orderId, Timestamp finished);

    @Update("update order " +
            "set status = 4, " +
            "closed = #{closed}, " +
            "updated = #{closed} " +
            "where order_id = #{orderId}")
    int updateClose(String orderId, Timestamp closed);

    @Select("select * " +
            "from order " +
            "where order_id = #{orderId}")
    @ResultType(Order.class)
    Order select(String orderId);

    @Select("select * " +
            "from order " +
            "where user_id = #{userId}")
    @ResultType(Order.class)
    List<Order> selectByUser(int userId);
}
