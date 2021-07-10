package com.yinchaxian.bookshop.service;

import com.yinchaxian.bookshop.entity.Order;
import com.yinchaxian.bookshop.entity.OrderDetail;
import com.yinchaxian.bookshop.mapper.OrderDetailMapper;
import com.yinchaxian.bookshop.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;

    public boolean insertOrder(Order order) {
        return orderMapper.insert(order) == 1;
    }

    public boolean deleteOrder(String orderId) {
        return orderMapper.delete(orderId) == 1;
    }

    public boolean updateOrderInfo(Order order) {
        return orderMapper.updateInfo(order) == 1;
    }

    public boolean updateOrderPay(Order order) {
        return orderMapper.updatePay(order) == 1;
    }

    public boolean updateOrderShipping(Order order) {
        return orderMapper.updateShipping(order) == 1;
    }

    public boolean updateOrderFinish(String orderId, Timestamp finished) {
        return orderMapper.updateFinish(orderId, finished) == 1;
    }

    public boolean updateOrderClose(String orderId, Timestamp closed) {
        return orderMapper.updateClose(orderId, closed) == 1;
    }

    public Order selectOrder(String orderId) {
        return orderMapper.select(orderId);
    }

    public List<Order> selectOrderByUser(int userId) {
        return orderMapper.selectByUser(userId);
    }

    public boolean insertOrderDetail(OrderDetail orderDetail) {
        return orderDetailMapper.insert(orderDetail) == 1;
    }

    public boolean deleteOrderDetail(String orderId) {
        return orderDetailMapper.delete(orderId) == 1;
    }

    public boolean updateOrderDetailInfo(OrderDetail orderDetail) {
        return orderDetailMapper.updateInfo(orderDetail) == 1;
    }

    public boolean updateOrderDetailDelivery(OrderDetail orderDetail) {
        return orderDetailMapper.updateDelivery(orderDetail) == 1;
    }

    public boolean updateOrderDetailScore(String orderId, int score) {
        return orderDetailMapper.updateScore(orderId, score) == 1;
    }

    public OrderDetail selectOrderDetail(String orderId) {
        return orderDetailMapper.select(orderId);
    }

    public List<OrderDetail> selectOrderDetailByBook(long bookId) {
        return orderDetailMapper.selectByBook(bookId);
    }
}
