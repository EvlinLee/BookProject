package com.zhangwan.app.http.service;

import com.zhangwan.app.bean.BookBean;
import com.zhangwan.app.bean.CountWelfareBean;
import com.zhangwan.app.bean.FriendBean;
import com.zhangwan.app.bean.InviteUrlBean;
import com.zhangwan.app.bean.MessageBean;
import com.zhangwan.app.bean.PayListBean;
import com.zhangwan.app.bean.ModifyInfoBean;
import com.zhangwan.app.bean.RechargeHistoryBean;
import com.zhangwan.app.bean.ShareWelfareBean;
import com.zhangwan.app.bean.SignBean;
import com.zhangwan.app.http.ApiBuild;
import com.zhangwan.app.http.ApiResponseBean;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by zzg on 2018/3/23.
 */

public class MineApiService {
    private static MineApiService.Service instance;

    public static MineApiService.Service getInstance() {
        if (instance == null) {
            instance = ApiBuild.getRetrofit().create(Service.class);
        }
        return instance;
    }

    public interface Service {
        //热门
        @POST("publish/book/hot")
        @FormUrlEncoded
        Observable<ApiResponseBean<List<BookBean>>> getHotList(@Field("number") int number,
                                                               @Field("size") int size);


        //消息
        @POST("publish/user/getMsg")
        @FormUrlEncoded
        Observable<ApiResponseBean<List<MessageBean>>> getMessageList(@Field("pageIndex") int pageIndex,
                                                                      @Field("pageSize") int pageSize);


        //头像上传
        @POST("publish/bookWelfare/upload")
        Observable<Object> uploadFile(@Body RequestBody body);

        //反馈建议
        @POST("publish/bookWelfare/feedback")
        @FormUrlEncoded
        Observable<ApiResponseBean<Object>> setFeedback(@Field("token") String token, @Field("content") String content, @Field("type") String type, @Field("phone") String phone);

        /**
         * 充值列表
         *
         * @param type
         * @return
         */
        @POST("publish/pay/listRecharage")
        @FormUrlEncoded
        Observable<ApiResponseBean<List<PayListBean>>> getPayList(@Field("type") String type);

        //修改个人信息
        @POST("publish/bookWelfare/addUserInfo")
        @FormUrlEncoded
        Observable<ApiResponseBean<Object>> apiModifyInfo(@FieldMap Map<String, String> map);

        //修改个人信息
        @POST("publish/bookWelfare/getUserInfo")
        @FormUrlEncoded
        Observable<ApiResponseBean<ModifyInfoBean>> getUserInfo(@Field("token") String token);

        //充值记录
        @POST("publish/user/listStream")
        @FormUrlEncoded
        Observable<ApiResponseBean<List<RechargeHistoryBean>>> apiRechargeHistory(@Field("token") String token,
                                                                                  @Field("start") int start,
                                                                                  @Field("type") int type);

        //福利数量
        @POST("publish/bookWelfare/countWelfare")
        @FormUrlEncoded
        Observable<ApiResponseBean<CountWelfareBean>> getCountWelfare(@Field("token") String token);

        //签到状态
        @POST("publish/user/isSign")
        @FormUrlEncoded
        Observable<SignBean> getSign(@Field("token") String token);

        //邀请链接
        @POST("publish/bookWelfare/inviteUrl")
        @FormUrlEncoded
        Observable<ApiResponseBean<InviteUrlBean>> apiInviteUrl(@Field("token") String token);

        //好友列表
        @POST("publish/bookWelfare/inviteFriends")
        @FormUrlEncoded
        Observable<ApiResponseBean<FriendBean>> apiFriend(@Field("token") String token);

        //每日分享领取奖励
        @POST("publish/bookWelfare/shareWelfare")
        @FormUrlEncoded
        Observable<ApiResponseBean<ShareWelfareBean>> apiShareWelfare(@Field("token") String token);

    }
}
