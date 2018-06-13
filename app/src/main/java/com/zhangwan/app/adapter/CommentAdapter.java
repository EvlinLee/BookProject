package com.zhangwan.app.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gxtc.commlibrary.helper.ImageHelper;
import com.gxtc.commlibrary.utils.SpUtil;
import com.zhangwan.app.R;
import com.zhangwan.app.base.BaseRecyclerAdapter;
import com.zhangwan.app.bean.BookInfoBean;
import com.zhangwan.app.bean.ThumbsupBean;
import com.zhangwan.app.http.ApiResponseBean;
import com.zhangwan.app.http.service.BookStoreApiService;
import com.zhangwan.app.utils.LoginDialogUtils;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 小说评论适配器
 * Created by Administrator on 2018/3/31 0031.
 */

public class CommentAdapter extends BaseRecyclerAdapter<BookInfoBean.CommentVosBean> {

    private Activity activity;

    public CommentAdapter(Context context, List<BookInfoBean.CommentVosBean> list, Activity activity) {
        super(context, list, R.layout.item_comment);
        this.activity = activity;
    }

    @Override
    public void bindData(ViewHolder holder, int position, final BookInfoBean.CommentVosBean commentVosBean) {
        final TextView thumbsup = holder.getViewV2(R.id.tv_item_comment_thumbsup);
        ImageHelper.getInstance().loadHeadRoundedImage(context, commentVosBean.getUserPic(), (ImageView) holder.getView(R.id.cv_item_comment));
        holder.setText(R.id.tv_item_comment_name, commentVosBean.getUserName());
        holder.setText(R.id.tv_item_comment_content, commentVosBean.getComment());
        holder.setText(R.id.tv_item_comment_thumbsup, commentVosBean.getThumbsupNum());

        if (commentVosBean.getIsThumbsup().equals("1")) {
            thumbsup.setClickable(false);
            thumbsup.setCompoundDrawablesWithIntrinsicBounds(R.drawable.thumbsup_select, 0, 0, 0);
        } else {
            thumbsup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (SpUtil.getIsLogin(context)) {
                        BookStoreApiService.getInstance()
                                .apiThumbsup(SpUtil.getToken(context), commentVosBean.getId())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<ApiResponseBean<ThumbsupBean>>() {
                                    @Override
                                    public void onCompleted() {

                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        Log.d("CommentAdapter", "onError: " + e);
                                    }

                                    @SuppressLint("SetTextI18n")
                                    @Override
                                    public void onNext(ApiResponseBean<ThumbsupBean> thumbsupBeanApiResponseBean) {
                                        thumbsup.setClickable(false);
                                        thumbsup.setText(Integer.parseInt(commentVosBean.getThumbsupNum()) + 1 + "");
                                        thumbsup.setCompoundDrawablesWithIntrinsicBounds(R.drawable.thumbsup_select, 0, 0, 0);
                                    }
                                });
                    } else {
                        LoginDialogUtils utils = new LoginDialogUtils(context);
                        utils.loginType(activity);
                    }
                }
            });
        }
    }
}

