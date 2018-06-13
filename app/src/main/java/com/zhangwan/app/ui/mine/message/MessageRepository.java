package com.zhangwan.app.ui.mine.message;

import com.gxtc.commlibrary.data.BaseRepository;
import com.zhangwan.app.bean.MessageBean;
import com.zhangwan.app.http.ApiCallBack;
import com.zhangwan.app.http.ApiObserver;
import com.zhangwan.app.http.ApiResponseBean;
import com.zhangwan.app.http.service.MineApiService;

import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MessageRepository extends BaseRepository implements MessageContract.Source {
    @Override
    public void getResult(int number, int size, ApiCallBack<List<MessageBean>> callBack) {
        Subscription sub = MineApiService
                .getInstance()
                .getMessageList(number, size)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiObserver<ApiResponseBean<List<MessageBean>>>(callBack));
        addSub(sub);
    }
}
