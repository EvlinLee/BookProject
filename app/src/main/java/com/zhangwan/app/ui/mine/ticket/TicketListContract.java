package com.zhangwan.app.ui.mine.ticket;

import com.gxtc.commlibrary.BasePresenter;
import com.gxtc.commlibrary.BaseUiView;

import java.util.List;

/**
 * Created by zzg on 2018/3/23.
 */

public class TicketListContract {

    public  interface View extends BaseUiView<Presenter> {
       public void  showData(List<String> datas);
    }

    public interface Presenter extends BasePresenter {
        public void getTicketListData(String parm, int number, int size);
    }
}
