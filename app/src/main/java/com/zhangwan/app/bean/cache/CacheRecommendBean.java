package com.zhangwan.app.bean.cache;

import com.google.gson.annotations.SerializedName;
import com.zhangwan.app.bean.BookTypeBean;
import com.zhangwan.app.bean.RecommendBannerBean;
import com.zhangwan.app.bean.RecommendBean;

import java.io.Serializable;
import java.util.List;

public class CacheRecommendBean implements Serializable {

    private static final long serialVersionUID = -8449515894732928907L;

    @SerializedName("RecommendBean")
    private List<RecommendBean> recommendBeanList;

    @SerializedName("BookTypeBean")
    private List<BookTypeBean> bookTypeBeanList;

    @SerializedName("RecommendBannerBean")
    private List<RecommendBannerBean> bannerBeanList;

    public List<RecommendBean> getRecommendBeanList() {
        return recommendBeanList;
    }

    public void setRecommendBeanList(List<RecommendBean> recommendBeanList) {
        this.recommendBeanList = recommendBeanList;
    }

    public List<BookTypeBean> getBookTypeBeanList() {
        return bookTypeBeanList;
    }

    public void setBookTypeBeanList(List<BookTypeBean> bookTypeBeanList) {
        this.bookTypeBeanList = bookTypeBeanList;
    }

    public List<RecommendBannerBean> getBannerBeanList() {
        return bannerBeanList;
    }

    public void setBannerBeanList(List<RecommendBannerBean> bannerBeanList) {
        this.bannerBeanList = bannerBeanList;
    }
}
