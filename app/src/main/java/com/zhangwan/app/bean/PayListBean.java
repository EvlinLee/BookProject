package com.zhangwan.app.bean;

/**
 * Created by laoshiren on 2018/3/31.
 * 充值里列表
 */
public class PayListBean {

    /**
     * id : 3
     * fee : 0.3
     * num : 3000
     * present : 0
     * first : 0
     * type : 0
     */

    private int id;
    private double fee;
    private int num;
    private int present;
    private int first;
    private int type;
    private  String listType;//0充币，1充会员

    public String getListType() {
        return listType;
    }

    public void setListType(String listType) {
        this.listType = listType;
    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getPresent() {
        return present;
    }

    public void setPresent(int present) {
        this.present = present;
    }

    public int getFirst() {
        return first;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
