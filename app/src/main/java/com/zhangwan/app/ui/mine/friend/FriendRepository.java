package com.zhangwan.app.ui.mine.friend;

import com.gxtc.commlibrary.data.BaseRepository;
import com.zhangwan.app.bean.FriendBean;
import com.zhangwan.app.http.ApiCallBack;
import com.zhangwan.app.http.ApiObserver;
import com.zhangwan.app.http.ApiResponseBean;
import com.zhangwan.app.http.service.MineApiService;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class FriendRepository extends BaseRepository implements FriendContract.Source {
    @Override
    public void getFriend(String token, ApiCallBack<FriendBean> callBack) {
        Subscription sub = MineApiService
                .getInstance()
                .apiFriend(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiObserver<ApiResponseBean<FriendBean>>(callBack));
        addSub(sub);
    }
}
