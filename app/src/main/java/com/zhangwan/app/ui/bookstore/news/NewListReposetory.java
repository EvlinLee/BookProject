package com.zhangwan.app.ui.bookstore.news;

import com.gxtc.commlibrary.data.BaseRepository;
import com.zhangwan.app.http.ApiCallBack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zzg on 2018/3/23.
 */

public class NewListReposetory extends BaseRepository implements NewListSource {

    @Override
    public void getData(Map<String, String> map, ApiCallBack<List<String>> callBack) {
//        Subscription sub = BookStoreApiService
//                .getInstance().getHotList(map)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new ApiObserver<ApiResponseBean<List<String>>>(callBack));
//        addSub(sub);
        List<String> datas = new ArrayList<>();
        for(int i = 0;i < 15;i++){
            datas.add("风行千里");
        }
        callBack.onSuccess(datas);
    }
}
