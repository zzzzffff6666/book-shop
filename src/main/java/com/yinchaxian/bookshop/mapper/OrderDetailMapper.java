package com.yinchaxian.bookshop.mapper;

import com.yinchaxian.bookshop.entity.OrderDetail;
import org.apache.ibatis.annotations.*;

public interface OrderDetailMapper {
    @Insert("insert into order_detail " +
            "values(#{orderId}, #{bookId}, #{bookName}, #{imageUrl}, #{storeId}, #{mount}, " +
            "#{unitPrice}, #{totalPrice}, #{postStatus}, #{deliveryTime}, #{receiveStatus}, #{score})")
    int insert(OrderDetail orderDetail);

    @Delete("delete from order_detail " +
            "where order_id = #{orderId}")
    int delete(String orderId);

    @Update("update order_detail " +
            "set mount = #{mount}, " +
            "unit_price = #{unitPrice}, " +
            "total_price = #{totalPrice} " +
            "where order_id = #{orderId}")
    int updateInfo(OrderDetail orderDetail);

    @Update("update order_detail " +
            "set post_status = #{postStatus}, " +
            "delivery_time = #{deliveryTime}, " +
            "receive_status = #{receiveStatus} " +
            "where order_id = #{orderId}")
    int updateDelivery(OrderDetail orderDetail);

    @Update("update order_detail " +
            "set score = #{score} " +
            "where order_id = #{orderId}")
    int updateScore(String orderId, int score);

    @Select("select * " +
            "from order_detail " +
            "where order_id = #{orderId}")
    @ResultType(OrderDetail.class)
    OrderDetail select(String orderId);
}
