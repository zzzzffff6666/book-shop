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

    public boolean deleteOrderByAdmin(String orderId) {
        return orderMapper.deleteByAdmin(orderId) == 1;
    }

    public boolean updateOrderInfo(Order order) {
        return orderMapper.updateInfo(order) == 1;
    }

    public boolean updateOrderPay(String orderId, int userId, int paymentType, Timestamp paid) {
        return orderMapper.updatePay(orderId, userId, paymentType, paid) == 1;
    }

    public boolean updateOrderDelivery(String orderId, int storeId, Timestamp updated) {
        return orderMapper.updateDelivery(orderId, storeId, updated) == 1;
    }

    public boolean updateOrderFinish(String orderId, Timestamp finished) {
        return orderMapper.updateFinish(orderId, finished) == 1;
    }

    public boolean updateOrderError(String orderId, Timestamp updated) {
        return orderMapper.updateError(orderId, updated) == 1;
    }

    public Order selectOrder(String orderId) {
        return orderMapper.select(orderId);
    }

    public List<Order> selectOrderByUser(int userId, int offset, int amount) {
        return orderMapper.selectByUser(userId, offset, amount);
    }

    public List<Order> selectOrderByUserAndStatus(int userId, int status, int offset, int amount) {
        return orderMapper.selectByUserAndStatus(userId, status, offset, amount);
    }

    public List<Order> selectOrderByStore(int storeId, int offset, int amount) {
        return orderMapper.selectByStore(storeId, offset, amount);
    }

    public List<Order> selectOrderByStoreAndStatus(int storeId, int status, int offset, int amount) {
        return orderMapper.selectByStoreAndStatus(storeId, status, offset, amount);
    }

    public int selectOrderUserId(String orderId) {
        return orderMapper.selectUserId(orderId);
    }

    public int selectOrderStoreId(String orderId) {
        return orderMapper.selectStoreId(orderId);
    }

    public long selectOrderBookId(String orderId) {
        return orderMapper.selectBookId(orderId);
    }

    public int selectOrderTimesByUserAndBook(int userId, long bookId) {
        return orderMapper.selectTimesByUserAndBook(userId, bookId);
    }

    public boolean insertOrderDetail(OrderDetail orderDetail) {
        return orderDetailMapper.insert(orderDetail) == 1;
    }

    public boolean deleteOrderDetail(String orderId) {
        return orderDetailMapper.delete(orderId) == 1;
    }

    public boolean updateOrderDetailInfo(String orderId, int mount) {
        return orderDetailMapper.updateInfo(orderId, mount) == 1;
    }

    public boolean updateOrderDetailShipping(String orderId, String shippingName, String shippingCode) {
        return orderDetailMapper.updateShipping(orderId, shippingName, shippingCode) == 1;
    }

    public boolean updateOrderDetailPost(String orderId, String postStatus, String receiveStatus) {
        return orderDetailMapper.updatePost(orderId, postStatus, receiveStatus) == 1;
    }

    public boolean updateOrderDetailFeedback(String orderId, String feedback, int score) {
        return orderDetailMapper.updateFeedBack(orderId, feedback, score) == 1;
    }

    public OrderDetail selectOrderDetail(String orderId) {
        return orderDetailMapper.select(orderId);
    }
}
