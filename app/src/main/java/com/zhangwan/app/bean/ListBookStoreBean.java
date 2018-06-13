package com.zhangwan.app.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by laoshiren on 2018/4/3.
 * 缓存书城首页
 */
public class ListBookStoreBean implements Serializable {

    private static final long serialVersionUID = -763618847875650322L;

    //轮播图
    @SerializedName("BannerBean")
    private List<BannerBean> bannerBeanList;

    //热门
    @SerializedName("HotBean")
    private List<HotBean> hotBeanList;

    //推荐
    @SerializedName("RecommendBean")
    private List<RecommendBean> recommendBeanList;

    //新书
    @SerializedName("newRecomendBeanList")
    private List<RecommendBean> newRecomendBeanList;

    //限时免费
    @SerializedName("BookBean")
    private List<BookBean> bookBeanList;

    //猜你喜欢
    @SerializedName("LikeBean")
    private List<LikeBean> likeBeanList;


    public List<BannerBean> getBannerBeanList() {
        return bannerBeanList;
    }

    public void setBannerBeanList(List<BannerBean> bannerBeanList) {
        this.bannerBeanList = bannerBeanList;
    }

    public List<HotBean> getHotBeanList() {
        return hotBeanList;
    }

    public void setHotBeanList(List<HotBean> hotBeanList) {
        this.hotBeanList = hotBeanList;
    }

    public List<RecommendBean> getRecommendBeanList() {
        return recommendBeanList;
    }

    public void setRecommendBeanList(List<RecommendBean> recommendBeanList) {
        this.recommendBeanList = recommendBeanList;
    }

    public List<RecommendBean> getNewRecomendBeanList() {
        return newRecomendBeanList;
    }

    public void setNewRecomendBeanList(List<RecommendBean> newRecomendBeanList) {
        this.newRecomendBeanList = newRecomendBeanList;
    }

    public List<BookBean> getBookBeanList() {
        return bookBeanList;
    }

    public void setBookBeanList(List<BookBean> bookBeanList) {
        this.bookBeanList = bookBeanList;
    }

    public List<LikeBean> getLikeBeanList() {
        return likeBeanList;
    }

    public void setLikeBeanList(List<LikeBean> likeBeanList) {
        this.likeBeanList = likeBeanList;
    }
}
