package com.zhangwan.app.ui.bookstore;

import com.gxtc.commlibrary.data.BaseSource;
import com.zhangwan.app.bean.BookBean;
import com.zhangwan.app.http.ApiCallBack;

import java.util.List;
import java.util.Map;

/**
 * Created by zzg on 2018/3/23.
 */

public interface BookStoreSource extends BaseSource {
    public void getNewData(Map<String, String> map, ApiCallBack<List<BookBean>> callBack);
    public void getHotData(int number, int size, ApiCallBack<List<BookBean>> callBack);
    public void getLimitData(int number, int size, ApiCallBack<List<BookBean>> callBack);
    public void getBoyData(int number, int size, ApiCallBack<List<BookBean>> callBack);
    public void getGirlData(int number, int size, ApiCallBack<List<BookBean>> callBack);
}
