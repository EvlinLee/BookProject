package com.zhangwan.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.gxtc.commlibrary.helper.ImageHelper;
import com.gxtc.commlibrary.utils.NetworkUtil;
import com.gxtc.commlibrary.utils.ToastUtil;
import com.zhangwan.app.R;
import com.zhangwan.app.bean.HotBean;
import com.zhangwan.app.recyclerview.CommonAdapter;
import com.zhangwan.app.recyclerview.base.ViewHolder;
import com.zhangwan.app.ui.BookInfoActivity;

import java.util.List;

/**
 * 书籍展示
 * Created by Administrator on 2018/3/13 0013.
 */

public class BaseBookAdapter extends CommonAdapter<HotBean> {

    private String type;
    private Context mContext;
    private View.OnClickListener mOnClickListener;

    public void setOnClickListener(View.OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    public BaseBookAdapter(Context context, int layoutId, List<HotBean> datas, String type) {
        super(context, layoutId, datas);
        this.type = type;
        this.mContext = context;
    }

    @Override
    protected void convert(ViewHolder holder, final HotBean item, int position) {
        if (type.equals("hot")) {
            ImageHelper.getInstance().loadImage(mContext, item.getBookPic(), (ImageView) holder.getView(R.id.iv_showbook_img));
            holder.setText(R.id.tv_showbook_name, item.getBookName());
            holder.setText(R.id.tv_showbook_auto, item.getAuthor());
            holder.setText(R.id.tv_showbook_intro, item.getIntro());

            holder.getView(R.id.rl_showbook).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (NetworkUtil.isConnected(mContext)) {
                        BookInfoActivity.gotoBookInfoActivity(mContext, item.getBookId());
                    } else {
                        ToastUtil.showShort(mContext, mContext.getString(R.string.empty_net_error));
                    }
                }
            });
            holder.getView(R.id.read).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mOnClickListener != null){
                        view.setTag(item);
                        mOnClickListener.onClick(view);
                    }
                }
            });

        } else if (type.equals("like")) {
            ImageHelper.getInstance().loadImage(mContext, item.getBookPic(), (ImageView) holder.getView(R.id.iv_item_bookname_pic));
            holder.setText(R.id.iv_item_bookname_name, item.getBookName());
            holder.getView(R.id.ll_item_bookname_name).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (NetworkUtil.isConnected(mContext)) {
                        BookInfoActivity.gotoBookInfoActivity(mContext, item.getBookId());
                    } else {
                        ToastUtil.showShort(mContext, mContext.getString(R.string.empty_net_error));
                    }

                }
            });
        }
    }

}
