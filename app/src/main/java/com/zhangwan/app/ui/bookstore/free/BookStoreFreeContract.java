package com.zhangwan.app.ui.bookstore.free;

import com.gxtc.commlibrary.BasePresenter;
import com.gxtc.commlibrary.BaseUiView;
import com.zhangwan.app.bean.BookBean;

import java.util.List;

/**
 * Created by zzg on 2018/3/23.
 */

public class BookStoreFreeContract {

    public  interface View extends BaseUiView<Presenter> {
       public void  showLimitData(List<BookBean> datas);
       public void  showBoyData(List<BookBean> datas);
       public void  showGirlData(List<BookBean> datas);
    }

    public interface Presenter extends BasePresenter {
        public void getLimitData(int number, int size);
        public void getBoyData(int number, int size);
        public void getGirlData(int number, int size);
    }
}
