package com.zhangwan.app.base;

import android.content.Context;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



import java.util.List;

import com.zhangwan.app.recyclerview.base.IRecyclerViewAdapter;

/**
 * Created by sjr on 2017/3/28.
 * 抽象多布局适配器基类
 * 只支持两种布局(代码写得有些冗余，后期再重新优化)
 * bindData1 type1 对应第一个布局(构造方法第一个)
 * type2 bindData2对应第二个布局(构造方法第二个)
 * <p>
 * 2017/4/19
 * 需求更改，不止两个布局，优化代码：已经支持任意多布局，这里定义onCreateViewHolder 里的viewType是resid的角标
 * 所以子适配器继承这个类时重写getItemViewType返回的是resid的角标
 */

public abstract class BaseMoreTypeRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements
        IRecyclerViewAdapter<T> {
    protected Context mContext;
    private List<T> mDatas;
    private int[] resid;
    private OnReItemOnClickListener itemListener;
    private OnReItemOnLongClickListener longListener;
//    private int type1;
    //    private int type2;


//    public BaseMoreTypeRecyclerAdapter(Context mContext, List<T> datas,
//                                       int type1, int type2, int... resid) {
//        this.mContext = mContext;
//        this.mDatas = datas;
//        this.type1 = type1;
////        this.type2 = type2;
//        this.resid = resid;
//    }

    public BaseMoreTypeRecyclerAdapter(Context mContext, List<T> datas,
                                       int... resid) {
        this.mContext = mContext;
        this.mDatas = datas;
        this.resid = resid;
    }

    /**
     * 将数据与view视图绑定
     */
//    public abstract void bindData1(BaseMoreTypeRecyclerAdapter.ViewHolder1 holder, int position, T t);
//
//    public abstract void bindData2(BaseMoreTypeRecyclerAdapter.ViewHolder2 holder, int position, T t);
    public abstract void bindData(ViewHolder holder, int position, T t);

    //    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        if (viewType == type1) {
//            View view = LayoutInflater.from(mContext).inflate(resid[0], parent, false);
//            view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (itemListener != null) {
//                        itemListener.onItemClick(v, (Integer) v.getTag());
//                    }
//                }
//            });
//            return new ViewHolder1(view);
//        } else {
//            View view = LayoutInflater.from(mContext).inflate(resid[1], parent, false);
//            view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (itemListener != null) {
//                        itemListener.onItemClick(v, (Integer) v.getTag());
//                    }
//                }
//            });
//            return new ViewHolder2(view);
//        }
//    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(resid[viewType], parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemListener != null) {
                    itemListener.onItemClick(v, (Integer) v.getTag());
                }
            }
        });
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (longListener != null) {
                    longListener.onItemLongClick(v, (Integer) v.getTag());
                }
                return true;
            }
        });
        return new ViewHolder(view);

    }

    //
//    @Override
//    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        if (holder instanceof BaseMoreTypeRecyclerAdapter.ViewHolder1) {
//            BaseMoreTypeRecyclerAdapter.ViewHolder1 viewholder = (ViewHolder1) holder;
//            viewholder.getItemView().setTag(position);
//            bindData1(viewholder, position, mDatas.get(position));
//        } else {
//            BaseMoreTypeRecyclerAdapter.ViewHolder2 viewholder = (ViewHolder2) holder;
//            viewholder.getItemView().setTag(position);
//            bindData2(viewholder, position, mDatas.get(position));
//        }
//
//    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewholder = (ViewHolder) holder;
        viewholder.getItemView().setTag(position);
        bindData(viewholder, position, mDatas.get(position));


    }

    public void removeChangesData(int position) {
        notifyItemRangeChanged(position, mDatas.size() - position);
        notifyDataSetChanged();
    }

    public void removeData(int position) {
        mDatas.remove(position);
        notifyDataSetChanged();
    }

    public List<T> getDatas() {
        return mDatas;
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ArrayMap<Integer, View> viewMap;
        private View itemView;


        private Object obj;

        public void setTag(Object obj) {
            this.obj = obj;
        }

        public Object getTag() {
            return obj;
        }

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            viewMap = new ArrayMap<>();
        }

        public View getView(int viewId) {
            View view = viewMap.get(viewId);
            if (view == null) {
                view = itemView.findViewById(viewId);
                viewMap.put(viewId, view);
            }

            return view;
        }

        public View getItemView() {
            return itemView;
        }
    }

//    public class ViewHolder1 extends RecyclerView.ViewHolder {
//
//        private ArrayMap<Integer, View> viewMap;
//        private View itemView;
//
//        public ViewHolder1(View itemView) {
//            super(itemView);
//            this.itemView = itemView;
//            viewMap = new ArrayMap<>();
//        }
//
//        public View getView(int viewId) {
//            View view = viewMap.get(viewId);
//            if (view == null) {
//                view = itemView.findViewById(viewId);
//                viewMap.put(viewId, view);
//            }
//
//            return view;
//        }
//
//        public View getItemView() {
//            return itemView;
//        }
//    }

//    public class ViewHolder2 extends RecyclerView.ViewHolder {
//
//        private ArrayMap<Integer, View> viewMap;
//        private View itemView;
//
//        public ViewHolder2(View itemView) {
//            super(itemView);
//            this.itemView = itemView;
//            viewMap = new ArrayMap<>();
//        }
//
//        public View getView(int viewId) {
//            View view = viewMap.get(viewId);
//            if (view == null) {
//                view = itemView.findViewById(viewId);
//                viewMap.put(viewId, view);
//            }
//
//            return view;
//        }
//
//        public View getItemView() {
//            return itemView;
//        }
//    }

    /**
     * listener
     */
    public interface OnReItemOnClickListener {
        void onItemClick(View v, int position);
    }

    public interface OnReItemOnLongClickListener {
        void onItemLongClick(View v, int position);
    }

    public void setOnReItemOnClickListener(OnReItemOnClickListener listener) {
        this.itemListener = listener;
    }

    public void setOnReItemOnLongClickListener(OnReItemOnLongClickListener longListener) {
        this.longListener = longListener;
    }

    public void addData(int position, T t) {
        mDatas.add(position, t);
        notifyItemInserted(position);
    }

    public void addData(T t) {
        mDatas.add(t);
        notifyItemInserted(mDatas.size() - 1);
    }


    /**
     * 加载更多数据使用这个方法
     */
    public void changeData(List<T> t) {
        if (t != null) {
            mDatas.addAll(t);
            notifyDataSetChanged();
        }
    }

    /**
     * 下拉刷新使用这个方法
     */
    public void notifyChangeData(List<T> t) {
        if (t != null) {
            mDatas.clear();
            mDatas.addAll(t);
            notifyDataSetChanged();
        }
    }

    public void changeData(List<T> t, int position) {
        if (t != null && mDatas != null && position <= mDatas.size() && position != -1) {
            mDatas.addAll(position, t);
            notifyDataSetChanged();
        }
    }

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context context) {
        mContext = context;
    }
}
