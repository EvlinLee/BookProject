package com.zhangwan.app.ui.mine;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.gxtc.commlibrary.utils.SpUtil;
import com.gxtc.commlibrary.utils.ToastUtil;
import com.gxtc.commlibrary.widget.LinesEditView;
import com.zhangwan.app.MyApplication;
import com.zhangwan.app.widget.RadioGroup;
import android.widget.TextView;


import com.zhangwan.app.R;
import com.zhangwan.app.base.BaseTitleActivity;
import com.zhangwan.app.widget.ClearableEditText;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 意见反馈
 * Created by Administrator on 2018/3/22.
 */

public class SuggestionFeedbackActivity extends BaseTitleActivity implements RadioGroup.OnCheckedChangeListener,MineContract.View{


    @BindView(R.id.activity_suggestion_feedback_content) LinesEditView mContent;

    @BindView(R.id.activity_suggestion_feedback_telephone)
    ClearableEditText                                                  mTelephone;
    @BindView(R.id.activity_suggestion_feedback_rg)
    RadioGroup                                                         rg;

    public MineContract.Presenter mPresenter;
    public String content;
    public String type = "4";
    public String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion_feedback);
    }

    @Override
    public void initView() {
        super.initView();

        getBaseHeadView().getHeadTitle().setText("意见反馈");
        getBaseHeadView().changeHeadBackground(R.color.main);
        getBaseHeadView().showBackButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    @Override
    public void initData() {
        new MinePresenter(this);
        rg.setOnCheckedChangeListener(this);
    }


    /**
     * 问题类别：1-产品建议，2-充值消费，3-账号问题，4-内容有误，5-求书，6-其他问题
     */

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch(checkedId){
            //章节错误
            case R.id.activity_suggestion_feedback_chapter:
                type  = "4";
                break;
            // 充值错误
            case R.id.activity_suggestion_feedback_recharge:
                type  = "2";
                break;
            // 更新缓慢
            case R.id.activity_suggestion_feedback_slow:
                type  = "1";
                break;
            //下载错误
            case R.id.activity_suggestion_feedback_download:
                type  = "6";
                break;
            //不流畅
            case R.id.activity_suggestion_feedback_unfluency:
                type  = "1";
                break;
            //耗流量
            case R.id.activity_suggestion_feedback_consumption:
                type  = "1";
                break;
            //书籍少
            case R.id.activity_suggestion_feedback_books:
                type  = "5";
                break;
            //充值起点
            case R.id.activity_suggestion_feedback_startingpoint:
                type  = "2";
                break;
        }
 }


   @OnClick({R.id.activity_suggestion_feedback_submit})
   public void Submit(View view){
        switch (view.getId()){
            case R.id.activity_suggestion_feedback_submit:
                if(TextUtils.isEmpty(type.trim())){
                    ToastUtil.showShort(MyApplication.getInstance(),"请选择问题类型");
                    return;
                }

                if(TextUtils.isEmpty(mContent.getContentText().trim())){
                    ToastUtil.showShort(MyApplication.getInstance(),"反馈内容不能为空");
                    return;
                }

                if(TextUtils.isEmpty(mTelephone.getText().toString().trim())){
                    ToastUtil.showShort(MyApplication.getInstance(),"联系方式不能为空");
                    return;
                }

                mPresenter.setFeedback(SpUtil.getToken(this),content,type,phone);
                break;

        }

    }

    @Override
    public void showData(Object datas) {
        ToastUtil.showShort(MyApplication.getInstance(),"反馈成功");
        finish();
    }

    @Override
    public void setPresenter(MineContract.Presenter presenter) {
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
       ToastUtil.showShort(MyApplication.getInstance(),"反馈失败");
    }

    @Override
    public void showReLoad() {

    }

    @Override
    public void showError(String code, String msg) {

    }

    @Override
    public void showNetError() {
        ToastUtil.showShort(MyApplication.getInstance(),"反馈失败");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
    }
}
