package com.yinchaxian.bookshop.entity;

import java.sql.Timestamp;

public class Order {
    private String orderId;
    private int userId;
    private int receiverId;
    private int storeId;
    private long bookId;
    private String bookName;
    private String imageUrl;
    private int orderMount;
    private float price;
    private float postFee;
    private int paymentType;
    private int status;
    private Timestamp created;
    private Timestamp paid;
    private Timestamp finished;
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

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getOrderMount() {
        return orderMount;
    }

    public void setOrderMount(int orderMount) {
        this.orderMount = orderMount;
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

    public Timestamp getUpdated() {
        return updated;
    }

    public void setUpdated(Timestamp updated) {
        this.updated = updated;
    }
}
