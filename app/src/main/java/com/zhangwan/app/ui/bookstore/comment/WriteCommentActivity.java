package com.zhangwan.app.ui.bookstore.comment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gxtc.commlibrary.helper.ImageHelper;
import com.gxtc.commlibrary.utils.SpUtil;
import com.gxtc.commlibrary.utils.ToastUtil;
import com.zhangwan.app.R;
import com.zhangwan.app.base.BaseTitleActivity;
import com.zhangwan.app.bean.WriteCommentBean;
import com.zhangwan.app.http.service.BookStoreApiService;
import com.zhangwan.app.utils.StringUtils;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

//写评论
public class WriteCommentActivity extends BaseTitleActivity implements TextWatcher {
    @BindView(R.id.et_write_content)
    EditText etWriteContent;
    @BindView(R.id.tv_write_num)
    TextView tvWriteNum;
    @BindView(R.id.iv_write_pic)
    ImageView ivWritePic;
    @BindView(R.id.tv_write_bookname)
    TextView tvWriteBookname;
    @BindView(R.id.tv_write_author)
    TextView tvWriteAuthor;
    @BindView(R.id.tv_write_sure)
    TextView tvWriteSure;

    private int bookId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writecomment);
    }

    @Override
    public void initData() {
        getBaseHeadView().showTitle("写评论").showBackButton(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        bookId = getIntent().getIntExtra("bookId", 0);
        String bookName = getIntent().getStringExtra("bookName");
        String author = getIntent().getStringExtra("author");
        String pic = getIntent().getStringExtra("pic");
        ImageHelper.getInstance().loadImage(this, pic, ivWritePic);
        tvWriteBookname.setText(bookName);
        tvWriteAuthor.setText(author);

        etWriteContent.addTextChangedListener(this);
    }

    @OnClick(R.id.tv_write_sure)
    public void onViewClicked() {
        String comment = etWriteContent.getText().toString();
        if (StringUtils.isEmpty(comment)) {
            ToastUtil.showShort(this, "请输入评论内容！");
        } else {
            writeComment(comment);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (charSequence.length() <= 500) {
            tvWriteNum.setText(500 - charSequence.length() + "");
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    //  写评论接口
    private void writeComment(String comment) {
        BookStoreApiService.getInstance()
                .apiWriteComment(SpUtil.getToken(this), bookId, comment)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WriteCommentBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(WriteCommentBean writeCommentBean) {
                        if (writeCommentBean.getCode().equals("200")) {
                            ToastUtil.showLong(WriteCommentActivity.this, "评论成功！等待审核！");
                            finish();
                        }
                    }
                });
    }
}
