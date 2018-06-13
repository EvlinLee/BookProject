package com.zhangwan.app.bean;

/**
 * 删除历史记录
 * Created by Administrator on 2018/3/27 0027.
 */

public class DeleteHistoryBean {

    /**
     * code : 200
     * msg : success
     * status : success
     * result :
     */

    private String code;
    private String msg;
    private String status;
    private String result;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
