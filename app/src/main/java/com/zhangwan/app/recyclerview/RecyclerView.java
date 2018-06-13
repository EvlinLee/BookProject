package com.zhangwan.app.recyclerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.SimpleItemAnimator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;



import java.util.List;

import com.gxtc.commlibrary.utils.LogUtil;
import com.gxtc.commlibrary.utils.WindowUtil;
import com.zhangwan.app.R;
import com.zhangwan.app.base.BaseMoreTypeRecyclerAdapter;
import com.zhangwan.app.base.BaseRecyclerAdapter;
import com.zhangwan.app.recyclerview.base.IRecyclerViewAdapter;
import com.zhangwan.app.recyclerview.wrapper.HeaderAndFooterWrapper;
import com.zhangwan.app.recyclerview.wrapper.LoadMoreWrapper;

/**
 * 改鸿洋的  https://github.com/hongyangAndroid/baseAdapter
 */

public class RecyclerView extends android.support.v7.widget.RecyclerView {
    private static final String TAG = "RecyclerView";

    public static final int DEFAULT_LOADMORE = -1;
    public static final int DEFAULT_FOOTVIEW = 0;

    protected HeaderAndFooterWrapper headerWrapper;
    protected LoadMoreWrapper loadMoreWrapper;

    private LoadMoreWrapper.OnLoadMoreListener loadMoreListener;

    public RecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        //RecyclerView调用notifyItemChanged闪烁问题相信很多人都遇到过。
        ((SimpleItemAnimator) getItemAnimator()).setSupportsChangeAnimations(false);
    }

    public RecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        ((SimpleItemAnimator) getItemAnimator()).setSupportsChangeAnimations(false);
    }

    public RecyclerView(Context context) {
        super(context);
        ((SimpleItemAnimator) getItemAnimator()).setSupportsChangeAnimations(false);


    }


    @Override
    public void setAdapter(Adapter adapter) {
        if (headerWrapper != null && loadMoreWrapper != null) {
            headerWrapper.setAdapter(adapter);
            loadMoreWrapper.setAdapter(headerWrapper);
            super.setAdapter(loadMoreWrapper);
            return;
        }

        if (headerWrapper != null) {
            headerWrapper.setAdapter(adapter);
            super.setAdapter(headerWrapper);
            return;
        }

        if (loadMoreWrapper != null) {
            loadMoreWrapper.setAdapter(adapter);
            super.setAdapter(loadMoreWrapper);
            return;
        }

        super.setAdapter(adapter);

    }
    int mStartX;
    int mEndX;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int x = (int) ev.getX();
        switch (ev.getAction()) {

            case MotionEvent.ACTION_DOWN:
                mStartX = x;

                LogUtil.printD("dowx"+x);
                break;

            case MotionEvent.ACTION_UP:
                mEndX = x;

                LogUtil.printD("upx"+x);
                break;
        }
        int disX = mEndX - mStartX;
        int screenW = WindowUtil.getScreenW(getContext());
        if ((disX * 100) / screenW > 10) {
            return true;
        }

        return super.onTouchEvent(ev);
    }
    /**
     * 添加头部布局 高度自适配
     *
     * @param resId
     */
    public View addHeadView(int resId) {
        View view = LayoutInflater.from(getContext()).inflate(resId, this, false);
        addHeadView(view);
        return view;
    }
    /**
     * 添加头部布局 高度自己设定
     *
     * @param resId
     */
    public View addHeadView(int resId,int height) {
        View view = LayoutInflater.from(getContext()).inflate(resId, this, false);
        addHeadView(view,height);
        return view;
    }

    public void addHeadView(View view,int height) {
        Adapter adapter = getAdapter();
        if (adapter != null) {
            throw new IllegalArgumentException("addHeadView 必须在 setAdapter() 之前调用");
        }

        if (headerWrapper == null) {
            headerWrapper = new HeaderAndFooterWrapper();
        }

        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
        view.setLayoutParams(params);
        headerWrapper.addHeaderView(view);
    }


    public View addFootView(int resId) {
        View view = LayoutInflater.from(getContext()).inflate(resId, this, false);
        addFootView(view);
        return view;
    }

    public void addHeadView(View view) {
        Adapter adapter = getAdapter();
        if (adapter != null) {
            throw new IllegalArgumentException("addHeadView 必须在 setAdapter() 之前调用");
        }

        if (headerWrapper == null) {
            headerWrapper = new HeaderAndFooterWrapper();
        }

        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup
                .LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(params);
        headerWrapper.addHeaderView(view);
    }

    public void addFootView(View view) {
        addFootView(view, DEFAULT_LOADMORE);
    }

    public void addFootView(View view, int defaultPage) {
        Adapter adapter = getAdapter();
        if (adapter != null) {
            throw new IllegalArgumentException("addFootView 必须在 setAdapter() 之前调用");
        }

        if (loadMoreWrapper == null) {
            loadMoreWrapper = new LoadMoreWrapper();
        }

        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup
                .LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(params);
        loadMoreWrapper.setLoadMoreView(view, defaultPage);
    }

    /**
     * 设置加载更多布局
     *
     * @param resId
     */
    public void setLoadMoreView(int resId) {
        Adapter adapter = getAdapter();
        if (adapter != null) {
            throw new IllegalArgumentException("setLoadMoreView 必须在 setAdapter() 之前调用");
        }

        if (loadMoreWrapper == null) {
            loadMoreWrapper = new LoadMoreWrapper();
        }

        if (loadMoreListener != null) {
            loadMoreWrapper.setOnLoadMoreListener(loadMoreListener);
        }

        View view = LayoutInflater.from(getContext()).inflate(resId, null, false);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup
                .LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(params);
        loadMoreWrapper.setLoadMoreView(view, DEFAULT_LOADMORE);
    }


    /**
     * 只有当服务器返回了没有更多数据字段 在调用这个方法，否则后续加载更多将不能显示loading布局
     */
    public void loadFinish() {
        if (loadMoreWrapper != null) {
            loadMoreWrapper.loadFinish();
        }
    }

    /**
     * 不要显示没有更多的view;
     */
    public void loadFinishNotView() {
        if (loadMoreWrapper != null) {
            loadMoreWrapper.loadFinish();
            LayoutManager manager = getLayoutManager();
            View view = manager.findViewByPosition(loadMoreWrapper.getItemCount() - 1);
            if (view != null) {
                View progress = view.findViewById(R.id.base_progressBar);
                if (progress != null) {
                    progress.setVisibility(GONE);
                }
            }
        }
    }


    public LoadMoreWrapper getLoadMoreWrapper() {
        if (loadMoreWrapper != null)
            return loadMoreWrapper;
        else return new LoadMoreWrapper();
    }

    /**
     * 如果调用了loadFinish()  后又想恢复可以加载更多 就执行这个方法
     */
    public void reLoadFinish() {
        if (loadMoreWrapper != null) {
            loadMoreWrapper.reLoadFinish();
        }
    }


    /**
     * 加载更多数据使用这个方法
     */

    public void changeData(List list, BaseRecyclerAdapter adapter) {
        if (loadMoreWrapper != null && adapter != null) {
            adapter.changeData(list);
            loadMoreWrapper.notifyDataSetChanged();
        }
    }

    public void changeData(List list, BaseMoreTypeRecyclerAdapter adapter) {
        if (loadMoreWrapper != null && adapter != null) {
            adapter.changeData(list);
            loadMoreWrapper.notifyDataSetChanged();
        }
    }


    /**
     * 刷新视图
     */
    public void notifyChangeData() {
        getAdapter().notifyDataSetChanged();

        if (loadMoreWrapper != null) {
            loadMoreWrapper.notifyDataSetChanged();
            return;
        }

        if (headerWrapper != null) {
            headerWrapper.notifyDataSetChanged();
            return;
        }
    }

    public void manualLoadMore() {
        if (loadMoreWrapper != null && loadMoreListener != null) {
            if (loadMoreWrapper.isLoadmore()) {
                loadMoreListener.onLoadMoreRequested();

            }
        }
    }

    ;


    public void setOnLoadMoreListener(LoadMoreWrapper.OnLoadMoreListener listener) {
        loadMoreListener = listener;
        if (loadMoreWrapper != null && loadMoreListener != null) {
            loadMoreWrapper.setOnLoadMoreListener(loadMoreListener);
        }

        setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(android.support.v7.widget.RecyclerView recyclerView,
                                             int newState) {

                switch (newState) {
                    case SCROLL_STATE_DRAGGING:
                        Log.d(TAG, "onScrollStateChanged: SCROLL_STATE_DRAGGING");
                        break;
                    case SCROLL_STATE_IDLE:
                        if (loadMoreWrapper != null && loadMoreListener != null) {
                            if (loadMoreWrapper.isLoadmore()) {
                                loadMoreListener.onLoadMoreRequested();
                            }
                        }
                        break;
                    case SCROLL_STATE_SETTLING:
                        Log.d(TAG, "onScrollStateChanged: SCROLL_STATE_SETTLING");
                        break;
                }

            }

            @Override
            public void onScrolled(android.support.v7.widget.RecyclerView recyclerView, int dx,
                                   int dy) {
                Log.d(TAG, "dx:" + dx);
                Log.d(TAG, "dy:" + dy);
                super.onScrolled(recyclerView, dx, dy);
            }
        });

    }


//    /**
//     * 下拉刷新使用这个方法
//     */
//    public void notifyChangeData(List list, BaseRecyclerAdapter adapter) {
//        if (loadMoreWrapper != null && adapter != null) {
//            adapter.notifyChangeData(list);
//            loadMoreWrapper.notifyDataSetChanged();
//        }
//    }
//
//    public void notifyChangeData(List list, BaseMoreTypeRecyclerAdapter adapter) {
//        if (loadMoreWrapper != null && adapter != null) {
//            adapter.notifyChangeData(list);
//            loadMoreWrapper.notifyDataSetChanged();
//        }
//    }


    public <A extends IRecyclerViewAdapter> void notifyChangeData(List list, A adapter) {
        if (loadMoreWrapper != null && adapter != null) {
            adapter.notifyChangeData(list);
            loadMoreWrapper.notifyDataSetChanged();
        }
    }


    /**
     * 下拉刷新  把新的内容加载到指定位置
     *
     * @param list
     * @param position
     * @param adapter
     */
    public <A extends IRecyclerViewAdapter> void notifyChangeData(List list, int position, A adapter) {
        if (loadMoreWrapper != null && adapter != null) {
            adapter.changeData(list, position);
            loadMoreWrapper.notifyDataSetChanged();
        }
    }


    /**
     * 刷新单个item
     *
     * @param position 要刷新的位置
     */
    public void notifyItemChanged(int position) {
        if (loadMoreWrapper != null) {
            int headCount = 0;
            if (headerWrapper != null) {
                headCount = headerWrapper.getHeadersCount();
            }
            loadMoreWrapper.notifyItemChanged(position + headCount);
            return;
        }

        if (headerWrapper != null) {
            int headCount = headerWrapper.getHeadersCount();
            headerWrapper.notifyItemChanged(position + headCount);
            return;
        }

        getAdapter().notifyItemChanged(position);
    }

    public <A extends IRecyclerViewAdapter> void removeData(A adapter, int position) {
        adapter.removeData(position);
        if (loadMoreWrapper != null) {
            loadMoreWrapper.notifyDataSetChanged();
            return;
        }

        if (headerWrapper != null) {
            headerWrapper.notifyDataSetChanged();
            return;
        }
    }

//    public void removeData(BaseMoreTypeRecyclerAdapter adapter, int position) {
//        adapter.removeData(position);
//        if (loadMoreWrapper != null) {
//            loadMoreWrapper.notifyDataSetChanged();
//            return;
//        }
//
//        if (headerWrapper != null) {
//            headerWrapper.notifyDataSetChanged();
//            return;
//        }
//    }

//    /**
//     * 下拉刷新  把新的内容加载到指定位置
//     *
//     * @param list
//     * @param adapter
//     */
//    public <A extends IRecyclerViewAdapter> void notifyChangeData(List list, A adapter) {
//        if (loadMoreWrapper != null && adapter != null) {
//            adapter.notifyChangeData(list);
//            loadMoreWrapper.notifyDataSetChanged();
//        }
//    }

    /**
     * 加载更多数据使用这个方法
     */
    public <A extends IRecyclerViewAdapter> void changeData(List list, A adapter) {
        if (loadMoreWrapper != null && adapter != null) {
            adapter.changeData(list);
            loadMoreWrapper.notifyDataSetChanged();
        }
    }

//    public <A extends IRecyclerViewAdapter> void removeData(A adapter, int position) {
//        adapter.removeData(position);
//        if (loadMoreWrapper != null) {
//            loadMoreWrapper.notifyDataSetChanged();
//            return;
//        }
//
//        if (headerWrapper != null) {
//            headerWrapper.notifyDataSetChanged();
//            return;
//        }
//    }

//    public void loadFinishNotView() {
//        if (loadMoreWrapper != null) {
//            loadMoreWrapper.loadFinish();
//            LayoutManager manager = getLayoutManager();
//            View view = manager.findViewByPosition(loadMoreWrapper.getItemCount() - 1);
//            if (view != null) {
//                View progress = view.findViewById(R.id.base_progressBar);
//                if (progress != null) {
//                    progress.setVisibility(GONE);
//                }
////                View data = view.findViewById(R.id.tv_no_data);
////                if (data != null) {
////                    data.setVisibility(VISIBLE);
//                }
}
//    }
//}

