package com.zhangwan.app.ui.mine;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.gxtc.commlibrary.utils.WindowUtil;
import com.zhangwan.app.R;
import com.zhangwan.app.base.BaseTitleActivity;
import com.zhangwan.app.utils.MyDialogUtil;

import butterknife.BindView;
import butterknife.OnClick;
/**
 * Created by zzg on 2018/3/26.
 */
public class BookTicketActivity extends BaseTitleActivity implements TextWatcher {

    @BindView(R.id.ed_ticket_code) EditText mEditText;
    @BindView(R.id.commit)         TextView commit;
    boolean flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_ticket);
    }

    @Override
    public void initView() {
        super.initView();
        getBaseHeadView().showTitle("兑换书券").showBackButton(R.drawable.back, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void initData() {
        super.initData();
//        getBaseEmptyView().showEmptyContent();
        commit.setBackgroundColor(getResources().getColor(R.color.color_c1c1c1));
        commit.setClickable(false);
    }

    @Override
    public void initListener() {
        super.initListener();
        mEditText.addTextChangedListener(this);
    }

    @OnClick({R.id.commit})
    public void OnClick(View view){
        WindowUtil.closeInputMethod(this);
        showDialog();
    }

    private void showDialog() {
        View   view  = getLayoutInflater().inflate(R.layout.exchange_ticket_dialog_layout,null);
        TextView tvStatus =  view.findViewById(R.id.exchange_status);
        TextView tvResult =  view.findViewById(R.id.exchange_resulte);
        if(flag){
            tvStatus.setText("兑换成功");
            tvResult.setText("恭喜获得新书圈一张");
            flag = false;
        }else {
            tvStatus.setText("兑换失败");
            tvResult.setText("该兑换码无效");
            flag = true;
        }
        final Dialog dialog = MyDialogUtil.commentDialog(this,view);
        view.findViewById(R.id.btn_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mEditText.removeTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

    @Override
    public void afterTextChanged(Editable editable) {
        if(TextUtils.isEmpty(editable.toString())){
            commit.setBackgroundColor(getResources().getColor(R.color.color_c1c1c1));
            commit.setClickable(false);
        }else {
            commit.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_green_selector));
            commit.setClickable(true);
        }
    }
}
