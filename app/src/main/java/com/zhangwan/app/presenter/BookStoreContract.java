package com.zhangwan.app.presenter;

import com.gxtc.commlibrary.BasePresenter;
import com.gxtc.commlibrary.BaseUiView;
import com.gxtc.commlibrary.data.BaseSource;
import com.zhangwan.app.bean.BannerBean;
import com.zhangwan.app.bean.BookBean;
import com.zhangwan.app.bean.HotBean;
import com.zhangwan.app.bean.LikeBean;
import com.zhangwan.app.bean.RecommendBean;
import com.zhangwan.app.http.ApiCallBack;

import java.util.List;

/**
 * Created by Administrator on 2018/3/23 0023.
 */

public class BookStoreContract {

    public interface View extends BaseUiView<BookStoreContract.Presenter> {

        //  主编推荐
        void showRecommend(List<RecommendBean> data,int tp);

        //  热门小说
        void showHotData(List<HotBean> data);

        //  轮播
        void showBanner(List<BannerBean> data);

        //  猜你喜欢
        void showLike(List<LikeBean> data);

        //  限时免费
        void showFree(List<BookBean> data);
    }

    public interface Presenter extends BasePresenter {
        void getRecommend(String type,int tp);

        void getHotData(int number, int size);

        void getBannerData(String type);

        void getLikeBook(int size, int number);

        void getFreeBook(int number, int size);
    }

    public interface Source extends BaseSource {
        void getRecommend(String type,ApiCallBack<List<RecommendBean>> callBack);

        void getHotData(int number, int size, ApiCallBack<List<HotBean>> callBack);

        void getBannerData(String type, ApiCallBack<List<BannerBean>> callBack);

        void getLikeBook(int size, int number, ApiCallBack<List<LikeBean>> callBack);

        void getFreeBook(int number, int size, ApiCallBack<List<BookBean>> callBack);
    }
}
