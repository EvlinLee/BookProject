package com.zhangwan.app.bean.cache;

import com.google.gson.annotations.SerializedName;
import com.zhangwan.app.bean.BookRackBean;
import com.zhangwan.app.bean.BookShelfBannerBean;

import java.io.Serializable;
import java.util.List;

public class CacheBookShelfListBean implements Serializable {
    private static final long serialVersionUID = -6221352322677338584L;

    @SerializedName("BookShelfHistoryBean")
    private List<BookShelfBannerBean> history;

    @SerializedName("BsBean")
    private List<BookRackBean.BsBean> bookRack;


    public List<BookShelfBannerBean> getHistory() {
        return history;
    }

    public void setHistory(List<BookShelfBannerBean> history) {
        this.history = history;
    }

    public List<BookRackBean.BsBean> getBookRack() {
        return bookRack;
    }

    public void setBookRack(List<BookRackBean.BsBean> bookRack) {
        this.bookRack = bookRack;
    }
}
