package com.zhangwan.app.bean.event;

/**
 * 书架跳转书城
 * Created by Administrator on 2018/3/26 0026.
 */

public class ToFragmentEvent {
    private int id;

    public ToFragmentEvent(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
