package com.zhangwan.app.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhangwan.app.R;
import com.zhangwan.app.bean.PayInReadDialogListBean;
import com.zhangwan.app.bean.PayListBean;
import com.zhangwan.app.recyclerview.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by laoshiren on 2018/3/29.
 * 充值中心
 */

public class PayCentreAdapter extends RecyclerView.Adapter<PayCentreAdapter.ViewHolder> /*implements View.OnClickListener*/ {

    private OnItemClickListener mOnItemClickListener = null;

    private LayoutInflater mInflater;
    private List<PayListBean> mDatas;

    private PayListBean bean;
    private List<Boolean> isClicks;//控件是否被点击,默认为false，如果被点击，改变值，控件根据值改变自身颜色
    private Context mContext;

    public PayCentreAdapter(Context context, List<PayListBean> datas) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
        mDatas = datas;


        isClicks = new ArrayList<>();
        for (int i = 0; i < mDatas.size(); i++) {
            isClicks.add(false);
        }
    }

    public void setOnItemClickLitener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.item_dialog_read_recyclerview, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.relativeLayout = view.findViewById(R.id.rl_item_dialog_read);
        viewHolder.tvIscount = view.findViewById(R.id.tv_item_dialog_read_recyclerview_iscount);
        viewHolder.tvPrice = view.findViewById(R.id.tv_item_dialog_read_recyclerview_price);
        viewHolder.tvBanl = view.findViewById(R.id.tv_item_dialog_read_recyclerview_banal);
        viewHolder.tvGive = view.findViewById(R.id.tv_item_dialog_read_recyclerview_give);

        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        bean = mDatas.get(i);

//        viewHolder.tvIscount.setText("送：" + bean.getIscount() + "%");
        DecimalFormat decimalFormat = new DecimalFormat("###################.###########");
        viewHolder.tvPrice.setText(decimalFormat.format(bean.getFee()) + "元");

        if ("0".equals(bean.getListType())) {//充币
            if (0 == bean.getPresent()) {      //不赠送书币
                viewHolder.tvBanl.setVisibility(View.VISIBLE);
                viewHolder.tvBanl.setText(bean.getNum() + "阅读币");
                viewHolder.tvGive.setVisibility(View.GONE);
            } else {
                viewHolder.tvBanl.setVisibility(View.VISIBLE);
                viewHolder.tvBanl.setText(bean.getNum() + "+");
                viewHolder.tvGive.setVisibility(View.VISIBLE);
                viewHolder.tvGive.setText(bean.getPresent() + "阅读币");
            }
        } else if ("1".equals(bean.getListType())) {//充会员
            viewHolder.tvBanl.setVisibility(View.GONE);
            viewHolder.tvGive.setVisibility(View.VISIBLE);
            viewHolder.tvGive.setText("VIP会员" + bean.getNum() + "个月");
        }


        //将数据保存在itemView的Tag中，以便点击时进行获取
        viewHolder.itemView.setTag(i);
        if (isClicks.get(i)) {
            viewHolder.relativeLayout.setBackground(mContext.getDrawable(R.drawable.shape_pay_centre_press));
            viewHolder.tvPrice.setTextColor(mContext.getResources().getColor(R.color.white));
            viewHolder.tvBanl.setTextColor(mContext.getResources().getColor(R.color.white));
            viewHolder.tvGive.setTextColor(mContext.getResources().getColor(R.color.white));
        } else {
            viewHolder.relativeLayout.setBackground(mContext.getDrawable(R.drawable.shape_read_dialog_recyclerview));
            viewHolder.tvPrice.setTextColor(mContext.getResources().getColor(R.color.text_color_black_2));
            viewHolder.tvBanl.setTextColor(mContext.getResources().getColor(R.color.text_color_black_2));
            viewHolder.tvGive.setTextColor(mContext.getResources().getColor(R.color.text_color_yellow));
        }

        // 如果设置了回调，则设置点击事件
        if (mOnItemClickListener != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < isClicks.size(); i++) {
                        isClicks.set(i, false);
                    }
                    isClicks.set(i, true);
                    notifyDataSetChanged();
                    mOnItemClickListener.onItemClick(viewHolder.itemView, i);
                }
            });
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View arg0) {
            super(arg0);
        }

        RelativeLayout relativeLayout;
        TextView tvIscount;
        TextView tvPrice;
        TextView tvBanl;
        TextView tvGive;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

}
