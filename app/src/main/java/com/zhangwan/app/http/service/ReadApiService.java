package com.zhangwan.app.http.service;

import com.zhangwan.app.bean.BookBean;
import com.zhangwan.app.bean.ChatperBean;
import com.zhangwan.app.bean.ChatperDetailBean;
import com.zhangwan.app.http.ApiBuild;
import com.zhangwan.app.http.ApiResponseBean;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by sjr on 2018/3/23.
 * 阅读Api
 */

public class ReadApiService {

    private static ReadApiService.Service instance;

    public static ReadApiService.Service getInstance() {
        if (instance == null) {
            instance = ApiBuild.getRetrofit().create(ReadApiService.Service.class);
        }
        return instance;
    }

    public interface Service {

        /**
         * 6.3、获取小说章节目录
         *
         * @param bookId 书本id
         * @param start  从0开始
         * @param size
         * @param token
         * @return
         */
        @POST("publish/book/getChapterList")
        @FormUrlEncoded
        Observable<ApiResponseBean<ChatperBean>> getChatper(@Field("bookId") String bookId,
                                                            @Field("start") int start,
                                                            @Field("size") String size,
                                                            @Field("token") String token);

        /**
         * 6.4、章节内容
         * @param cId
         * @param token
         * @return
         */
        @POST("publish/book/getChapter")
        @FormUrlEncoded
        Observable<ApiResponseBean<ChatperDetailBean>> getChatperDetail(@Field("cId") String cId,
                                                                        @Field("token") String token);

    }
}
