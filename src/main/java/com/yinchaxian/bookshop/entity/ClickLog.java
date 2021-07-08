package com.yinchaxian.bookshop.entity;

import java.sql.Timestamp;

public class ClickLog {
    private Timestamp receiveTime;
    private String userId;
    private String ipAddress;
    private String url;
    private String referUrl;
    private String areaAddress;
    private String localAddress;
    private String browserType;
    private String operationSys;
    private String sessionId;
    private String sessionTimes;
    private String csvp;

    public Timestamp getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Timestamp receiveTime) {
        this.receiveTime = receiveTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getReferUrl() {
        return referUrl;
    }

    public void setReferUrl(String referUrl) {
        this.referUrl = referUrl;
    }

    public String getAreaAddress() {
        return areaAddress;
    }

    public void setAreaAddress(String areaAddress) {
        this.areaAddress = areaAddress;
    }

    public String getLocalAddress() {
        return localAddress;
    }

    public void setLocalAddress(String localAddress) {
        this.localAddress = localAddress;
    }

    public String getBrowserType() {
        return browserType;
    }

    public void setBrowserType(String browserType) {
        this.browserType = browserType;
    }

    public String getOperationSys() {
        return operationSys;
    }

    public void setOperationSys(String operationSys) {
        this.operationSys = operationSys;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionTimes() {
        return sessionTimes;
    }

    public void setSessionTimes(String sessionTimes) {
        this.sessionTimes = sessionTimes;
    }

    public String getCsvp() {
        return csvp;
    }

    public void setCsvp(String csvp) {
        this.csvp = csvp;
    }
}
