package com.zhangwan.app.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 书城主编推荐
 * Created by Administrator on 2018/3/21 0021.
 */

public class StoreRecommendAdapter extends PagerAdapter {

    private List<View> viewList;

    public StoreRecommendAdapter(List<View> viewList) {
        this.viewList = viewList;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewList.get(position));
    }

    @Override
    public int getCount() {
        return viewList == null ? 0 : viewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(viewList.get(position));
        return (viewList.get(position));
//        try {
//            container.addView(viewList.get(position % viewList.size()));
//        } catch (Exception e) {
//
//        }
//        return viewList.get(position % viewList.size());
    }
}
