package com.zhangwan.app.presenter;

import com.gxtc.commlibrary.BasePresenter;
import com.gxtc.commlibrary.BaseUiView;
import com.gxtc.commlibrary.BaseUserView;
import com.gxtc.commlibrary.data.BaseSource;
import com.zhangwan.app.bean.BookBean;
import com.zhangwan.app.bean.BookTypeBean;
import com.zhangwan.app.bean.RecommendBannerBean;
import com.zhangwan.app.bean.RecommendBean;
import com.zhangwan.app.http.ApiCallBack;

import java.util.List;

/**
 * Created by laoshiren on 2018/3/21.
 */

public class RecommendContract {

    /**
     * view层接口
     * 如果涉及到用户相关的 那么这个接口应该继承
     *
     * @see BaseUserView  区别在于多了个token过期的回调方法
     * <p>
     * 如果没涉及到用户相关的操作 那么这个接口直接继承
     * @see BaseUiView
     */
    public interface View extends BaseUiView<RecommendContract.Presenter> {
        /**
         * 展示数据
         *
         * @param datas
         */

        void showData(List<RecommendBean> datas);

        void showBookType(List<BookTypeBean> datas);

        void showFiltrResult(List<BookBean> datas);

        //轮播
        void showBanner(List<RecommendBannerBean> datas);
    }

    /**
     * presenter层接口
     */
    public interface Presenter extends BasePresenter {

        void getData();

        void getBookType();

        void getFiltrResult(String type, String is_finish, String attr, int number, int size);

        void getBanner(String type);
    }

    /**
     * model层接口 实现类还需要继承
     */
    public interface Source extends BaseSource {
        //        void getData(int start, int groupid, String userCode, ApiCallBack<List<RecommendBean>> callBack);
        void getData(String type, ApiCallBack<List<RecommendBean>> callBack);

        void getBanner(String type, ApiCallBack<List<RecommendBannerBean>> callBack);
    }
}
