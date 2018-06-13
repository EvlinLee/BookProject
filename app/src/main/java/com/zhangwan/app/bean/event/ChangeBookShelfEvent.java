package com.zhangwan.app.bean.event;

/**
 * 删除书架
 * Created by Administrator on 2018/3/26 0026.
 */

public class ChangeBookShelfEvent {
    private int type;

    public ChangeBookShelfEvent(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
