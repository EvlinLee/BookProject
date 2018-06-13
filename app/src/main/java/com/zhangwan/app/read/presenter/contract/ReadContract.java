package com.zhangwan.app.read.presenter.contract;

import com.zhangwan.app.read.model.bean.BookChapterBean;
import com.zhangwan.app.read.model.bean.ZhuiYueChapterInfoBean;
import com.zhangwan.app.read.widget.page.TxtChapter;

import java.util.List;



/**
 * Created by newbiechen on 17-5-16.
 */

public interface ReadContract extends BaseContract{
    interface View extends BaseContract.BaseView {
        void showCategory(List<BookChapterBean> bookChapterList);
        void finishChapter(ZhuiYueChapterInfoBean chapterInfoBean);
        void errorChapter();
    }

    interface Presenter extends BaseContract.BasePresenter<View>{
        void loadCategory(String bookId,String total,String token);
        void loadChapter(String bookId,String token, List<TxtChapter> bookChapterList);
    }
}
