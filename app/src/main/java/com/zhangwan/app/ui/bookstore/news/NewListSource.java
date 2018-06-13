package com.zhangwan.app.ui.bookstore.news;

import com.gxtc.commlibrary.data.BaseSource;
import com.zhangwan.app.http.ApiCallBack;

import java.util.List;
import java.util.Map;

/**
 * Created by zzg on 2018/3/23.
 */

public interface NewListSource extends BaseSource {
    public void getData(Map<String,String> map, ApiCallBack<List<String>> callBack);
}
