package com.zhangwan.app.ui.recommend.mouth;

import com.gxtc.commlibrary.BasePresenter;
import com.gxtc.commlibrary.BaseUiView;
import com.zhangwan.app.bean.BookBean;

import java.util.List;

/**
 * Created by zzg on 2018/3/23.
 */

public class MouthContract {

    public  interface View extends BaseUiView<Presenter> {
       public void  showMouthData(List<BookBean> datas);
       public void  showCommomData(List<BookBean> datas);
    }

    public interface Presenter extends BasePresenter {
        public void getMouthData(int number, int size);
        public void getCommomData(int number, int size);
    }
}
