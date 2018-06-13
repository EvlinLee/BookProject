package com.zhangwan.app.http.service;


import com.zhangwan.app.bean.BannerBean;
import com.zhangwan.app.bean.BookBean;
import com.zhangwan.app.bean.BookInfoBean;
import com.zhangwan.app.bean.BookRackBean;
import com.zhangwan.app.bean.BookTypeBean;
import com.zhangwan.app.bean.CodeBean;
import com.zhangwan.app.bean.DeleteHistoryBean;
import com.zhangwan.app.bean.FiltrResultBean;
import com.zhangwan.app.bean.HistoryBean;
import com.zhangwan.app.bean.HotBean;
import com.zhangwan.app.bean.LikeBean;
import com.zhangwan.app.bean.LoginBean;
import com.zhangwan.app.bean.RecommendBean;
import com.zhangwan.app.bean.SearchBean;
import com.zhangwan.app.bean.UserInfoBean;
import com.zhangwan.app.http.ApiBuild;
import com.zhangwan.app.http.ApiResponseBean;

import java.util.List;
import java.util.Map;

import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 接口
 * Created by sjr on 2017/9/15.
 */

public class ApiService {
    private static Service instance;

    public static Service getInstance() {
        if (instance == null) {
            instance = ApiBuild.getRetrofit().create(Service.class);
        }
        return instance;
    }


    public interface Service {

        //获取手机验证码
        @FormUrlEncoded
        @POST("publish/app/code")
        Observable<CodeBean> apiPhoneCode(@Field("phone") String phone);

        //手机登录
        @FormUrlEncoded
        @POST("publish/app/login")
        Observable<LoginBean> apiLogin(@Field("type") String type, @FieldMap Map<String, Object> map);

        //获取用户信息
        @FormUrlEncoded
        @POST("publish/user/getInfo")
        Observable<ApiResponseBean<UserInfoBean>> apiUserInfo(@Field("token") String token);

        //推荐
        @POST("publish/book/recommend")
        @FormUrlEncoded
        Observable<ApiResponseBean<List<RecommendBean>>> apiRecommend(@Field("type") String type);

        //轮播
        @POST("publish/book/recommend")
        @FormUrlEncoded
        Observable<ApiResponseBean<List<BannerBean>>> apiBanner(@Field("type") String type);

        //热门小说
        @FormUrlEncoded
        @POST("publish/book/hot")
        Observable<ApiResponseBean<List<HotBean>>> apiHotBook(@Field("number") int number,
                                                              @Field("size") int size);

        //小说分类
        @POST("publish/book/bookType")
        Observable<ApiResponseBean<List<BookTypeBean>>> apiBookType();

        //小说筛选结果
        @FormUrlEncoded
        @POST("publish/book/filtrBook")
        Observable<ApiResponseBean<List<BookBean>>> apiFiltrBook(@Field("type") String type,
                                                                 @Field("attr") String attr,
                                                                 @Field("is_finish") String is_finish,
                                                                 @Field("number") int number,
                                                                 @Field("size") int size);

        //搜索
        @FormUrlEncoded
        @POST("publish/book/searchBook")
        Observable<ApiResponseBean<SearchBean>> apiSearchBook(@Field("start") int start,
                                                              @Field("searchKey") String searchKey);

        //小说详情
        @FormUrlEncoded
        @POST("publish/book/bookInfo")
        Observable<ApiResponseBean<BookInfoBean>> apiBookInfo(@Field("token") String token,@Field("bookId") int bookId);

        //猜你喜欢
        @FormUrlEncoded
        @POST("publish/book/randBook")
        Observable<ApiResponseBean<List<LikeBean>>> apiLike(@Field("size") int size, @Field("number") int number);

        //我的书架
        @FormUrlEncoded
        @POST("publish/book/bookRack")
        Observable<ApiResponseBean<BookRackBean>> apiBookRack(@Field("start") int start, @Field("uniqueId") String uniqueId);

        //阅读记录
        @FormUrlEncoded
        @POST("publish/book/listHistory")
        Observable<ApiResponseBean<HistoryBean>> apiHistory(@Field("start") int start, @Field("token") String token);

        //删除阅读记录
        @FormUrlEncoded
        @POST("publish/book/deleteHistory")
        Observable<DeleteHistoryBean> apiDeleteHistory(@Field("id") int id);

        //限时免费
        @FormUrlEncoded
        @POST("publish/book/listFreeread")
        Observable<DeleteHistoryBean> apiFree(@Field("id") int id);

    }

}
