package com.yinchaxian.bookshop.entity;

/**
 * @author: zhang
 * @date: 2021/7/15 22:24
 * @description:
 */
public class ConversionIndex {
    private int totalUser;
    private int purchasedUser;
    private float conversionRate;
    private String duration;

    public int getTotalUser() {
        return totalUser;
    }

    public void setTotalUser(int totalUser) {
        this.totalUser = totalUser;
    }

    public int getPurchasedUser() {
        return purchasedUser;
    }

    public void setPurchasedUser(int purchasedUser) {
        this.purchasedUser = purchasedUser;
    }

    public float getConversionRate() {
        return conversionRate;
    }

    public void setConversionRate(float conversionRate) {
        this.conversionRate = conversionRate;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
