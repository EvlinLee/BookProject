package com.zhangwan.app.ui.bookstore.like;

import com.gxtc.commlibrary.BasePresenter;
import com.gxtc.commlibrary.BaseUiView;
import com.zhangwan.app.bean.BookBean;
import com.zhangwan.app.bean.LikeBean;

import java.util.List;

/**
 * Created by zzg on 2018/3/23.
 */

public class GessLikeListContract {

    public  interface View extends BaseUiView<Presenter> {
        void showLike(List<LikeBean> data);
    }

    public interface Presenter extends BasePresenter {
        void getLikeBook(int size, int number);
    }
}
