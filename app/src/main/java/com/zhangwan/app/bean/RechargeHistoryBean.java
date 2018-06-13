package com.zhangwan.app.bean;

public class RechargeHistoryBean {

    /**
     * id : 2280
     * userId : 300
     * balance : 1450
     * stream : 30
     * createTime : 1521426066000
     * type : 4
     * orderId : null
     */

    private int id;
    private int userId;
    private int balance;
    private int stream;
    private long createTime;
    private int type;
    private Object orderId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getStream() {
        return stream;
    }

    public void setStream(int stream) {
        this.stream = stream;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Object getOrderId() {
        return orderId;
    }

    public void setOrderId(Object orderId) {
        this.orderId = orderId;
    }
}
