package com.yinchaxian.bookshop.entity;

public class OrderDetail {
    private String orderId;
    private long bookId;
    private String bookName;
    private String imageUrl;
    private int mount;
    private float unitPrice;
    private float totalPrice;
    private String shippingName;
    private String shippingCode;
    private String postStatus;
    private String receiveStatus;
    private String feedback;
    private int score;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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

    public int getMount() {
        return mount;
    }

    public void setMount(int mount) {
        this.mount = mount;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
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

    public String getPostStatus() {
        return postStatus;
    }

    public void setPostStatus(String postStatus) {
        this.postStatus = postStatus;
    }

    public String getReceiveStatus() {
        return receiveStatus;
    }

    public void setReceiveStatus(String receiveStatus) {
        this.receiveStatus = receiveStatus;
    }

    public String getBuyerFeedback() {
        return feedback;
    }

    public void setBuyerFeedback(String buyerFeedback) {
        this.feedback = buyerFeedback;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
