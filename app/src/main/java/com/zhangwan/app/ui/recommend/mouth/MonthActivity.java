package com.zhangwan.app.ui.recommend.mouth;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gxtc.commlibrary.utils.ToastUtil;
import com.zhangwan.app.R;
import com.zhangwan.app.adapter.HotListAdater;
import com.zhangwan.app.adapter.LimitListAdater;
import com.zhangwan.app.base.BaseTitleActivity;
import com.zhangwan.app.bean.BookBean;
import com.zhangwan.app.dialog.RuleDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.zhangwan.app.ui.bookstore.free.BookStoreFreeActivity.size;

public class MonthActivity extends BaseTitleActivity implements MouthContract.View, View.OnClickListener {
    @BindView(R.id.fragment_monthlyrea_through)       TextView     mThrough;
    @BindView(R.id.fragment_monthlyrea_vertical_list) RecyclerView mVerticalList;
    @BindView(R.id.fragment_monthlyrea_form_list)     RecyclerView mFormList;

    MouthContract.Presenter mPresenter;
    LimitListAdater freeAdater;
    HotListAdater commonAdater;
    private int number  = 0;
    private RuleDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month);
    }

    @Override
    public void initView() {
        super.initView();
        getBaseHeadView().showTitle("包月专区").showBackButton(R.drawable.back, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mFormList.setNestedScrollingEnabled(false);
        mVerticalList.setNestedScrollingEnabled(false);

        freeAdater = new LimitListAdater(this,new ArrayList<BookBean>(), R.layout.activity_book_store_free_item);
        commonAdater = new HotListAdater(this,new ArrayList<BookBean>(), R.layout.hot_list_item_layout);

        mFormList.setLayoutManager(new GridLayoutManager(this,3));
        mVerticalList.setLayoutManager(new LinearLayoutManager(this));

        mFormList.setAdapter(freeAdater);
        mVerticalList.setAdapter(commonAdater);
    }

    @Override
    public void initData() {
        super.initData();
        new MouthPresenter(this);
        //暂时先不做包月专区的
        getBaseEmptyView().showEmptyContent();
//        mPresenter.getMouthData(number,size);
//        mPresenter.getCommomData(number,size);
    }

    @Override
    public void initListener() {
        super.initListener();
        mThrough.setOnClickListener(this);
    }

    @Override
    public void showMouthData(List<BookBean> datas) {
        freeAdater.notifyChangeData(datas);
    }

    @Override
    public void showCommomData(List<BookBean> datas) {
        commonAdater.notifyChangeData(datas);
    }

    @Override
    public void setPresenter(MouthContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showLoad() {

    }

    @Override
    public void showLoadFinish() {

    }

    @Override
    public void showEmpty() {
       getBaseEmptyView().showEmptyContent();
    }

    @Override
    public void showReLoad() {

    }

    @Override
    public void showError(String code, String msg) {
        ToastUtil.showShort(this,msg);
    }

    @Override
    public void showNetError() {}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(dialog != null)
        dialog.dismiss();
        mPresenter.destroy();
    }

    @Override
    public void onClick(View view) {
         switch (view.getId()){
             case R.id.fragment_monthlyrea_through:
                 showDialog();
                 break;
         }
    }

    private void showDialog() {
        dialog = new RuleDialog(this);
        dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.btn_six_vip:
                        ToastUtil.showShort(MonthActivity.this,"半年VIP");
                     break;
                     case R.id.btn_three_vip:
                         ToastUtil.showShort(MonthActivity.this,"三个月VIP");
                     break;
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public static void junpToMonthActivity(Activity activity){
        Intent intent = new Intent(activity,MonthActivity.class);
        activity.startActivity(intent);

    }

}
