package com.zhangwan.app.ui;

import com.gxtc.commlibrary.data.BaseSource;
import com.zhangwan.app.http.ApiCallBack;

import java.io.File;

import okhttp3.RequestBody;
import retrofit2.http.Body;

/**
 * Created by zzg on 2018/4/3.
 */

public interface UpLoadFileSource extends BaseSource {
    public void UpLoadFile(RequestBody body, ApiCallBack<Object> callBack);
}
