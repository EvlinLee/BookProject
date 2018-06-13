package com.zhangwan.app.read.model.remote;
import com.zhangwan.app.read.model.bean.ZhuiYueChapterInfoBean;
import com.zhangwan.app.read.model.bean.ZhuiyueChapterListBean;

import io.reactivex.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by newbiechen on 17-4-20.
 */

public interface BookApi {

    /***********************追阅**************************/

    //追阅--小说章节内容
    @POST("publish/book/getChapterList")
    @FormUrlEncoded
    Single<ZhuiyueChapterListBean> getChapterList(@Field("bookId") String bookId,
                                                  @Field("start") String start,
                                                  @Field("size") String size,
                                                  @Field("token") String token);


    //追阅--小说章节内容
    @POST("publish/book/getChapter")
    @FormUrlEncoded
    Single<ZhuiYueChapterInfoBean> getChapterInfo(@Field("cId") String cId, @Field("token") String token);

}
