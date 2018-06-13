package com.zhangwan.app.read.model.remote;

import com.zhangwan.app.read.model.bean.BookChapterBean;
import com.zhangwan.app.read.model.bean.ZhuiYueChapterInfoBean;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import retrofit2.Retrofit;

public class ZhuiYueRepository {
    private static final String TAG = "RemoteRepository";

    private static ZhuiYueRepository sInstance;
    private Retrofit mRetrofit;
    private BookApi mBookApi;

    private ZhuiYueRepository() {
        mRetrofit = ZhuiyueRemoteHelper.getInstance()
                .getRetrofit();

        mBookApi = mRetrofit.create(BookApi.class);
    }

    public static ZhuiYueRepository getInstance() {
        if (sInstance == null) {
            synchronized (ZhuiyueRemoteHelper.class) {
                if (sInstance == null) {
                    sInstance = new ZhuiYueRepository();
                }
            }
        }
        return sInstance;
    }

    public Single<List<BookChapterBean>> getBookChapters(String bookId, String start, String size, String token) {
        return mBookApi.getChapterList(bookId, start, size, token)
                .map(bean -> {
                    if (bean.getResult() == null) {
                        return new ArrayList<BookChapterBean>(1);
                    } else {
                        return bean.getResult().getcList();
                    }
                });
    }

    /**
     * 注意这里用的是同步请求
     *
     * @return
     */
    public Single<ZhuiYueChapterInfoBean> getChapterInfo(String cId, String token) {
        return mBookApi.getChapterInfo(cId, token)
                .map(bean -> bean);
    }
}
