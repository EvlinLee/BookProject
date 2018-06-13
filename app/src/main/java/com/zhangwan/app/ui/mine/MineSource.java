package com.zhangwan.app.ui.mine;

import com.gxtc.commlibrary.data.BaseSource;
import com.zhangwan.app.bean.BookBean;
import com.zhangwan.app.bean.MessageBean;
import com.zhangwan.app.http.ApiCallBack;

import java.util.List;
import java.util.Map;

/**
 * Created by zzg on 2018/3/23.
 */

public interface MineSource extends BaseSource {
     void getTicketListData(String parm, int number, int size,ApiCallBack<List<String>> callBack);
     void setFeedback(String token,  String content, String type,String phone, ApiCallBack<Object> callBack);
}
