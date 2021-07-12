package com.yinchaxian.bookshop.mapper;

import com.yinchaxian.bookshop.entity.OrderDetail;
import org.apache.ibatis.annotations.*;

public interface OrderDetailMapper {
    @Insert("insert into order_detail " +
            "values(#{orderId}, #{bookId}, #{bookName}, #{imageUrl}, #{mount}, " +
            "#{unitPrice}, #{totalPrice}, #{shippingName}, #{shippingCode}, " +
            "#{postStatus}, #{receiveStatus}, #{feedback}, #{score})")
    int insert(OrderDetail orderDetail);

    @Delete("delete from order_detail " +
            "where order_id = #{orderId}")
    int delete(String orderId);

    @Update("update order_detail " +
            "set mount = #{mount}, " +
            "total_price = unit_price * #{mount} " +
            "where order_id = #{orderId}")
    int updateInfo(String orderId, int mount);

    @Update("update order_detail " +
            "set shipping_name = #{shippingName}, " +
            "shipping_code = #{shippingCode} " +
            "where order_id = #{orderId}")
    int updateShipping(String orderId, String shippingName, String shippingCode);

    @Update("update order_detail " +
            "set post_status = #{postStatus}, " +
            "receive_status = #{receiveStatus} " +
            "where order_id = #{orderId}")
    int updatePost(String orderId, String postStatus, String receiveStatus);

    @Update("update order_detail " +
            "set feedback = #{feedback}, " +
            "score = #{score} " +
            "where order_id = #{orderId}")
    int updateFeedBack(String orderId, String feedback, int score);

    @Select("select * " +
            "from order_detail " +
            "where order_id = #{orderId}")
    @ResultType(OrderDetail.class)
    OrderDetail select(String orderId);
}
