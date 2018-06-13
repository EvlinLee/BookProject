package com.zhangwan.app.ui.mine;

import com.gxtc.commlibrary.data.BaseRepository;
import com.zhangwan.app.bean.BookBean;
import com.zhangwan.app.bean.MessageBean;
import com.zhangwan.app.http.ApiCallBack;
import com.zhangwan.app.http.ApiObserver;
import com.zhangwan.app.http.ApiResponseBean;
import com.zhangwan.app.http.service.BookStoreApiService;
import com.zhangwan.app.http.service.MineApiService;
import com.zhangwan.app.ui.bookstore.BookStoreSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.http.Field;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zzg on 2018/3/23.
 */

public class MineReposetory extends BaseRepository implements MineSource {

    @Override
    public void getTicketListData(String parm, int number, int size,
                                  ApiCallBack<List<String>> callBack) {
        List<String> dates = new ArrayList<>();
        callBack.onSuccess(dates);
    }

    @Override
    public void setFeedback(String token, String content, String type, String phone, ApiCallBack<Object> callBack) {
        Subscription sub = MineApiService
                .getInstance().setFeedback(token, content, type, phone)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiObserver<ApiResponseBean<Object>>(callBack));
        addSub(sub);
    }
}
