package com.zhangwan.app.http.service;

import com.zhangwan.app.bean.BookShelfBannerBean;
import com.zhangwan.app.http.ApiBuild;
import com.zhangwan.app.http.ApiResponseBean;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public class BookShelfApiService {
    private static BookShelfApiService.Service instance;

    public static BookShelfApiService.Service getInstance() {
        if (instance == null) {
            instance = ApiBuild.getRetrofit().create(BookShelfApiService.Service.class);
        }
        return instance;
    }

    public interface Service {

        //轮播
        @POST("publish/book/history")
        @FormUrlEncoded
        Observable<ApiResponseBean<BookShelfBannerBean>> getBanner(@Field("token") String type);


        //我的书架删除
        @POST("publish/book/deleteBookRack")
        @FormUrlEncoded
        Observable<ApiResponseBean<Object>> deleteBookRack(@Field("id") String id);

    }
}
