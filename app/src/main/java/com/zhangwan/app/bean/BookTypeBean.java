package com.zhangwan.app.bean;

import java.io.Serializable;

/**
 * 书本类型
 * Created by Administrator on 2018/3/23 0023.
 */

public class BookTypeBean implements Serializable{

    private static final long serialVersionUID = -763618847875650324L;
    /**
     * id : 12
     * name : 玄幻
     * createTime : 1510018536000
     */

    private int id;
    private String name;
    private long createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
