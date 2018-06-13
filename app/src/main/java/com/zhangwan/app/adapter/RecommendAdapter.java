package com.zhangwan.app.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.gxtc.commlibrary.helper.ImageHelper;
import com.gxtc.commlibrary.utils.NetworkUtil;
import com.gxtc.commlibrary.utils.ToastUtil;
import com.zhangwan.app.R;
import com.zhangwan.app.bean.RecommendBean;
import com.zhangwan.app.recyclerview.CommonAdapter;
import com.zhangwan.app.recyclerview.base.ViewHolder;
import com.zhangwan.app.ui.BookInfoActivity;

import java.util.List;

/**
 * 推荐
 * Created by Administrator on 2018/3/22 0022.
 */

public class RecommendAdapter extends CommonAdapter<RecommendBean> {
    public RecommendAdapter(Context context, int layoutId, List<RecommendBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, final RecommendBean recommendBean, int position) {
        holder.setText(R.id.tv_recommend_item_bookname, recommendBean.getBookName());
        holder.setText(R.id.tv_recommend_item_bookintro, recommendBean.getIntro());
        holder.setText(R.id.tv_recommend_item_author, recommendBean.getBookauthor());
        ImageHelper.getInstance().loadImage(mContext, recommendBean.getBookPic(), (ImageView) holder.getView(R.id.iv_recommend_item_bookimg));

        holder.setOnClickListener(R.id.rl_recommend_item, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (NetworkUtil.isConnected(mContext)) {
                    BookInfoActivity.gotoBookInfoActivity(mContext, recommendBean.getBookId());
                } else {
                    ToastUtil.showShort(mContext, mContext.getString(R.string.empty_net_error));
                }
            }
        });
    }
}
