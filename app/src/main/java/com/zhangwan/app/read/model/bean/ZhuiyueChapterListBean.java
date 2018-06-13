package com.zhangwan.app.read.model.bean;

import java.util.List;

public class ZhuiyueChapterListBean {

    /**
     * code : 200
     * msg : success
     * status : success
     * result : {"total":1605,"cList":[{"isFee":0,"bought":0,"fee":0,"id":681781,"title":"序章","bookId":1364},{"isFee":0,"bought":0,"fee":0,"id":681782,"title":"第一章：李言！","bookId":1364}]}
     */

    private String code;
    private String msg;
    private String status;
    private ResultBean result;

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

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * total : 1605
         * cList : [{"isFee":0,"bought":0,"fee":0,"id":681781,"title":"序章","bookId":1364},{"isFee":0,"bought":0,"fee":0,"id":681782,"title":"第一章：李言！","bookId":1364}]
         */

        private int total;
        private List<BookChapterBean> cList;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<BookChapterBean> getcList() {
            return cList;
        }

        public void setcList(List<BookChapterBean> cList) {
            this.cList = cList;
        }
    }
}
