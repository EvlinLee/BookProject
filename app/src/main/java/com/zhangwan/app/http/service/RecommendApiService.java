package com.zhangwan.app.http.service;

import com.zhangwan.app.bean.RecommendBannerBean;
import com.zhangwan.app.http.ApiBuild;
import com.zhangwan.app.http.ApiResponseBean;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public class RecommendApiService {
    private static RecommendApiService.Service instance;

    public static RecommendApiService.Service getInstance() {
        if (instance == null) {
            instance = ApiBuild.getRetrofit().create(RecommendApiService.Service.class);
        }
        return instance;
    }

    public interface Service {

        //推荐轮播
        @POST("publish/book/recommend")
        @FormUrlEncoded
        Observable<ApiResponseBean<List<RecommendBannerBean>>> getBanner(@Field("type") String type);

    }
}
