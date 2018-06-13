package com.zhangwan.app.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.gxtc.commlibrary.helper.ImageHelper;
import com.zhangwan.app.R;
import com.zhangwan.app.base.BaseRecyclerAdapter;
import com.zhangwan.app.bean.AllCommentBean;

import java.util.List;

//全部评论
public class AllCommentAdapter extends BaseRecyclerAdapter<AllCommentBean> {
    public AllCommentAdapter(Context context, List<AllCommentBean> list) {
        super(context, list, R.layout.item_comment);
    }

    @Override
    public void bindData(ViewHolder holder, int position, AllCommentBean allCommentBean) {
        ImageHelper.getInstance().loadHeadRoundedImage(context, allCommentBean.getUserPic(), (ImageView) holder.getView(R.id.cv_item_comment));
        holder.setText(R.id.tv_item_comment_name, allCommentBean.getUserName());
        holder.setText(R.id.tv_item_comment_content, allCommentBean.getComment());
        holder.setText(R.id.tv_item_comment_thumbsup, allCommentBean.getThumbsupNum());
        holder.setText(R.id.tv_item_comment, allCommentBean.getIsThumbsup());
    }
}
