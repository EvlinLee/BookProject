package com.zhangwan.app.adapter;

import android.content.Context;

import com.zhangwan.app.R;
import com.zhangwan.app.base.BaseRecyclerAdapter;
import com.zhangwan.app.bean.PayInReadDialogListBean;

import java.util.List;

/**
 * Created by laoshiren on 2018/3/29.
 */

public class PayInReadDialogRecyclerviviewAdapter extends BaseRecyclerAdapter<PayInReadDialogListBean> {

    public PayInReadDialogRecyclerviviewAdapter(Context context, List<PayInReadDialogListBean> list, int itemLayoutId) {
        super(context, list, itemLayoutId);
    }


    @Override
    public void bindData(ViewHolder holder, int position, PayInReadDialogListBean bean) {

        holder.setText(R.id.tv_item_dialog_read_recyclerview_iscount, "送" + bean.getIscount());
        holder.setText(R.id.tv_item_dialog_read_recyclerview_price, bean.getPrice() + "元");
        holder.setText(R.id.tv_item_dialog_read_recyclerview_banal, bean.getBanl() + "+");
        holder.setText(R.id.tv_item_dialog_read_recyclerview_give, bean.getGive() + "阅读币");


    }
}
