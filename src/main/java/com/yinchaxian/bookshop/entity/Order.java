package com.yinchaxian.bookshop.entity;

import java.sql.Timestamp;

public class Order {
    private String orderId;
    private int userId;
    private int receiverId;
    private float price;
    private float postFee;
    private int paymentType;
    private int orderMount;
    private String shippingName;
    private String shippingCode;
    private String buyerFeedback;
    private int buyerRate;
    private int status;
    private Timestamp created;
    private Timestamp paid;
    private Timestamp finished;
    private Timestamp closed;
    private Timestamp updated;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getPostFee() {
        return postFee;
    }

    public void setPostFee(float postFee) {
        this.postFee = postFee;
    }

    public int getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(int paymentType) {
        this.paymentType = paymentType;
    }

    public int getOrderMount() {
        return orderMount;
    }

    public void setOrderMount(int orderMount) {
        this.orderMount = orderMount;
    }

    public String getShippingName() {
        return shippingName;
    }

    public void setShippingName(String shippingName) {
        this.shippingName = shippingName;
    }

    public String getShippingCode() {
        return shippingCode;
    }

    public void setShippingCode(String shippingCode) {
        this.shippingCode = shippingCode;
    }

    public String getBuyerFeedback() {
        return buyerFeedback;
    }

    public void setBuyerFeedback(String buyerFeedback) {
        this.buyerFeedback = buyerFeedback;
    }

    public int getBuyerRate() {
        return buyerRate;
    }

    public void setBuyerRate(int buyerRate) {
        this.buyerRate = buyerRate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public Timestamp getPaid() {
        return paid;
    }

    public void setPaid(Timestamp paid) {
        this.paid = paid;
    }

    public Timestamp getFinished() {
        return finished;
    }

    public void setFinished(Timestamp finished) {
        this.finished = finished;
    }

    public Timestamp getClosed() {
        return closed;
    }

    public void setClosed(Timestamp closed) {
        this.closed = closed;
    }

    public Timestamp getUpdated() {
        return updated;
    }

    public void setUpdated(Timestamp updated) {
        this.updated = updated;
    }
}
