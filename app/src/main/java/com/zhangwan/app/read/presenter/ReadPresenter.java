package com.zhangwan.app.read.presenter;

import android.util.Log;

import com.gxtc.commlibrary.utils.SpUtil;
import com.zhangwan.app.read.model.bean.BookChapterBean;
import com.zhangwan.app.read.model.bean.ZhuiYueChapterInfoBean;
import com.zhangwan.app.read.model.local.BookRepository;
import com.zhangwan.app.read.model.remote.ZhuiYueRepository;
import com.zhangwan.app.read.presenter.contract.ReadContract;
import com.zhangwan.app.read.utils.LogUtils;
import com.zhangwan.app.read.utils.RxUtils;
import com.zhangwan.app.read.widget.page.TxtChapter;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by newbiechen on 17-5-16.
 */

public class ReadPresenter extends RxPresenter<ReadContract.View>
        implements ReadContract.Presenter {
    private static final String TAG = "ReadPresenter";

    private Subscription mChapterSub;

    // 获取全部章节
    @Override
    public void loadCategory(String bookId, String total, String token) {
        Log.d("ReadPresenter", "loadCategory: " + bookId);
        Disposable disposable = ZhuiYueRepository.getInstance()
                .getBookChapters(bookId, "1", total, token)
                .doOnSuccess(new Consumer<List<BookChapterBean>>() {
                    @Override
                    public void accept(List<BookChapterBean> bookChapterBeen) throws Exception {
                        //进行设定BookChapter所属的书的id。
                        for (BookChapterBean bookChapter : bookChapterBeen) {
                            bookChapter.setId(bookChapter.getId());
                            bookChapter.setBookId(bookId);
                        }
                    }
                })
                .compose(RxUtils::toSimpleSingle)
                .subscribe(
                        beans -> {
                            mView.showCategory(beans);
                        }
                        ,
                        e -> {
                            //TODO: Haven't grate conversation method.
                            LogUtils.e(e);
                        }
                );
        addDisposable(disposable);
    }

    // 获取章节内容
    @Override
    public void loadChapter(String bookId,String token, List<TxtChapter> bookChapters) {
        int size = bookChapters.size();

        //取消上次的任务，防止多次加载
        if (mChapterSub != null) {
            mChapterSub.cancel();
        }

        List<Single<ZhuiYueChapterInfoBean>> chapterInfos = new ArrayList<>(bookChapters.size());
        ArrayDeque<String> titles = new ArrayDeque<>(bookChapters.size());

        // 将要下载章节，转换成网络请求。
        for (int i = 0; i < size; ++i) {
            TxtChapter bookChapter = bookChapters.get(i);
            // 网络中获取数据
            Single<ZhuiYueChapterInfoBean> chapterInfoSingle = ZhuiYueRepository.getInstance()
                    .getChapterInfo(bookChapter.getLink(), token);
            Log.d("ReadPresenter", "loadChapter: " + bookChapter.getLink());
            chapterInfos.add(chapterInfoSingle);

            titles.add(bookChapter.getTitle());
        }

        Single.concat(chapterInfos)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Subscriber<ZhuiYueChapterInfoBean>() {
                            String title = titles.poll();

                            @Override
                            public void onSubscribe(Subscription s) {
                                s.request(Integer.MAX_VALUE);
                                mChapterSub = s;
                            }

                            @Override
                            public void onNext(ZhuiYueChapterInfoBean chapterInfoBean) {
                                if (chapterInfoBean.getResult() != null) {
                                    //存储数据
                                    BookRepository.getInstance().saveChapterInfo(
                                            bookId, title, listToString(chapterInfoBean.getResult().getContent())
                                    );
                                    mView.finishChapter(chapterInfoBean);
                                    //将获取到的数据进行存储
                                    title = titles.poll();
                                }
                            }

                            @Override
                            public void onError(Throwable t) {
                                //只有第一个加载失败才会调用errorChapter
                                if (bookChapters.get(0).getTitle().equals(title)) {
                                    mView.errorChapter();
                                }
                                LogUtils.e(t);
                            }

                            @Override
                            public void onComplete() {
                            }
                        }
                );
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mChapterSub != null) {
            mChapterSub.cancel();
        }
    }

    private static String listToString(List<String> list) {
        if (list == null) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        boolean first = true;
        //第一个前面不拼接","
        for (String string : list) {
            if (first) {
                first = false;
            } else {
                result.append("\n");
            }
            result.append(string);
        }
        return result.toString();
    }
}
