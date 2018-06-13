package com.zhangwan.app.presenter;

import com.gxtc.commlibrary.data.BaseRepository;
import com.gxtc.commlibrary.utils.SpUtil;
import com.zhangwan.app.bean.ChatperBean;
import com.zhangwan.app.bean.UserInfoBean;
import com.zhangwan.app.http.ApiCallBack;
import com.zhangwan.app.http.ApiObserver;
import com.zhangwan.app.http.ApiResponseBean;
import com.zhangwan.app.http.service.ApiService;
import com.zhangwan.app.http.service.ReadApiService;

import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by sjr on 2018/3/30 .
 */

public class CatalogueRepository extends BaseRepository implements CatalogueContract.Source {


    @Override
    public void geCalogue(String token,String bookId, String total, ApiCallBack<ChatperBean> callBack) {
        Subscription sub = ReadApiService
                .getInstance()
                .getChatper(bookId,1,total,token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiObserver<ApiResponseBean<ChatperBean>>(callBack));
        addSub(sub);
    }
}
