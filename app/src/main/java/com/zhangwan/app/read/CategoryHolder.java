package com.zhangwan.app.read;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhangwan.app.R;
import com.zhangwan.app.read.utils.BookManager;
import com.zhangwan.app.read.widget.page.TxtChapter;

/**
 * Created by newbiechen on 17-5-16.
 */

public class CategoryHolder extends ViewHolderImpl<TxtChapter> {

    private TextView mTvChapter;
    private TextView mTvChapterFree;
    private ImageView mIvChapterLock;

    @Override
    public void initView() {
        mTvChapter = findById(R.id.category_tv_chapter);
        mTvChapterFree = findById(R.id.category_tv_free);
        mIvChapterLock = findById(R.id.iv_item_chatper_lock);
    }

    @Override
    public void onBind(TxtChapter value, int pos) {
        //首先判断是否该章已下载
        Drawable drawable = null;

        //TODO:目录显示设计的有点不好，需要靠成员变量是否为null来判断。
        //如果没有链接地址表示是本地文件
        if (value.getLink() == null) {
            drawable = ContextCompat.getDrawable(getContext(), R.drawable.selector_category_load);
        } else {
            if (value.getBookId() != null
                    && BookManager
                    .isChapterCached(value.getBookId(), value.getTitle())) {
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.selector_category_load);
            } else {
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.selector_category_unload);
            }
        }

        mTvChapter.setSelected(false);
        mTvChapter.setTextColor(ContextCompat.getColor(getContext(), R.color.read_chatper_textcolor_2));
        mTvChapter.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
        mTvChapter.setText(value.getTitle());

        if (0 == value.getFee()) {//免费
            mTvChapterFree.setText("免费");
            mTvChapterFree.setVisibility(View.VISIBLE);
            mIvChapterLock.setVisibility(View.GONE);
        } else if (0 != value.getFee() && 0 == value.getBought()) {//未解锁
            mTvChapterFree.setVisibility(View.GONE);
            mIvChapterLock.setVisibility(View.VISIBLE);
            mIvChapterLock.setImageResource(R.drawable.icon_chatper_suo);
        } else if (0 != value.getFee() && 1 == value.getBought()) {//已解锁
            mTvChapterFree.setVisibility(View.GONE);
            mIvChapterLock.setVisibility(View.VISIBLE);
            mIvChapterLock.setImageResource(R.drawable.icon_chatper_deblock);
        }
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_category;
    }

    public void setSelectedChapter() {
        mTvChapter.setTextColor(ContextCompat.getColor(getContext(), R.color.main));
        mTvChapter.setSelected(true);
    }
}
