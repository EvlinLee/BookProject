package com.zhangwan.app.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.gxtc.commlibrary.base.BaseRecyclerAdapter;
import com.gxtc.commlibrary.helper.ImageHelper;
import com.zhangwan.app.R;
import com.zhangwan.app.bean.BookBean;
import com.zhangwan.app.bean.LikeBean;

import java.util.List;

/**
 * Created by zzg on 2018/3/23.
 */

public class GessLikeListAdater extends BaseRecyclerAdapter<LikeBean> {

    public GessLikeListAdater(Context context, List<LikeBean> list, int itemLayoutId) {
        super(context, list, itemLayoutId);
    }


    @Override
    public void bindData(ViewHolder holder, int position, LikeBean bean) {
        holder.setText(R.id.title,bean.getBookName());
        if(!TextUtils.isEmpty(bean.author)){
            holder.getView(R.id.descrite).setVisibility(View.VISIBLE);
            holder.setText(R.id.descrite,bean.author + (TextUtils.isEmpty(bean.words) || "0".equals(bean.words)?  "" :"\t"+bean.words +"字"));
        }else {
            holder.getView(R.id.descrite).setVisibility(View.GONE);
        }
        if(!TextUtils.isEmpty(bean.type)){
            holder.getView(R.id.type).setVisibility(View.VISIBLE);
            holder.setText(R.id.type,bean.type);
        }else {
            holder.getView(R.id.type).setVisibility(View.GONE);
        }
        holder.setText(R.id.content,bean.getIntro());
        ImageHelper.getInstance().loadImage(context,bean.getBookPic(),(ImageView) holder.getView(R.id.iv_bookimg));
    }
}
