package com.zhangwan.app.http.service;

import com.zhangwan.app.bean.AllCommentBean;
import com.zhangwan.app.bean.BookBean;
import com.zhangwan.app.bean.CollectBookBean;
import com.zhangwan.app.bean.DayShareBean;
import com.zhangwan.app.bean.ThumbsupBean;
import com.zhangwan.app.bean.WriteCommentBean;
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
 * Created by zzg on 2018/3/23.
 */

public class BookStoreApiService {
    public static volatile BookStoreApiService.BookStoreService instance;

    public static BookStoreApiService.BookStoreService getInstance() {
        if (instance == null) {
            synchronized (BookStoreApiService.class) {
                if (instance == null) {
                    instance = ApiBuild.getRetrofit().create(BookStoreApiService.BookStoreService.class);
                }
            }
        }
        return instance;
    }

    public interface BookStoreService {
        //热门
        @POST("publish/book/hot")
        @FormUrlEncoded
        Observable<ApiResponseBean<List<BookBean>>> getHotList(@Field("number") int number,
                                                               @Field("size") int size);


        //新书推荐
        @POST("publish/book/recommend")
        @FormUrlEncoded
        Observable<ApiResponseBean<List<BookBean>>> getNewList(@FieldMap Map<String, String> map);


        //限时免费
        @POST("publish/book/listFreeread")
        @FormUrlEncoded
        Observable<ApiResponseBean<List<BookBean>>> getLimitData(@Field("start") int number,
                                                                 @Field("size") int size);


        //加入书架
        @POST("publish/book/collectBook")
        @FormUrlEncoded
        Observable<CollectBookBean> apiCollectBook(@Field("token") String token,
                                                   @Field("bookId") int size);

        //全部评论
        @POST("publish/book/getComment")
        @FormUrlEncoded
        Observable<ApiResponseBean<List<AllCommentBean>>> apiAllComment(@Field("token") String token,
                                                                        @Field("bookId") int bookId,
                                                                        @Field("start") int start,
                                                                        @Field("size") int size);

        //写评论
        @POST("publish/book/saveComment")
        @FormUrlEncoded
        Observable<WriteCommentBean> apiWriteComment(@Field("token") String token,
                                                     @Field("bookId") int bookId,
                                                     @Field("comment") String comment);

        //点赞
        @POST("publish/book/thumbsupComment")
        @FormUrlEncoded
        Observable<ApiResponseBean<ThumbsupBean>> apiThumbsup(@Field("token") String token,
                                                              @Field("commentId") String commentId);

        // 创建每日分享链接
        @POST("publish/bookWelfare/shareUrl")
        @FormUrlEncoded
        Observable<DayShareBean> apiDayShare(@Field("token") String token,
                                             @Field("bookId") int bookId);
    }
}
