package com.zhangwan.app.read.model.remote;

import android.util.Log;

import com.zhangwan.app.read.utils.Constant;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ZhuiyueRemoteHelper {
    private static final String TAG = "ZhuiyueRemoteHelper";
    private static ZhuiyueRemoteHelper sInstance;
    private Retrofit mRetrofit;
    private OkHttpClient mOkHttpClient;

    private ZhuiyueRemoteHelper() {
        mOkHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(
                        new Interceptor() {
                            @Override
                            public Response intercept(Chain chain) throws IOException {
                                Request request = chain.request();

                                //在这里获取到request后就可以做任何事情了
                                Response response = chain.proceed(request);
                                Log.d(TAG, "intercept: " + request.url().toString());
                                return response;
                            }
                        }
                ).build();

        mRetrofit = new Retrofit.Builder()
                .client(mOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(Constant.ZHUIYUE)
                .build();
    }

    public static ZhuiyueRemoteHelper getInstance() {
        if (sInstance == null) {
            synchronized (ZhuiyueRemoteHelper.class) {
                if (sInstance == null) {
                    sInstance = new ZhuiyueRemoteHelper();
                }
            }
        }
        return sInstance;
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }
}
