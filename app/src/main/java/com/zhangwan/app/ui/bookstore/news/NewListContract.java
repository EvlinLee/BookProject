package com.zhangwan.app.ui.bookstore.news;

import com.gxtc.commlibrary.BasePresenter;
import com.gxtc.commlibrary.BaseUiView;
import com.zhangwan.app.bean.BookBean;

import java.util.List;
import java.util.Map;

/**
 * Created by zzg on 2018/3/23.
 */

public class NewListContract {

    public  interface View extends BaseUiView<Presenter> {
       public void  showData(List<BookBean> datas);
    }

    public interface Presenter extends BasePresenter{
        public void getNesData(Map<String,String> map);
    }
}
