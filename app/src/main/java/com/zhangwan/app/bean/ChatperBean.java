package com.zhangwan.app.bean;

import java.util.List;

/**
 * Created by laoshiren on 2018/3/27.
 * 章节目录实体
 */

public class ChatperBean {


    /**
     * total : 2416
     * cList : [{"isFee":0,"bought":0,"fee":0,"id":2882974,"title":"第1章 被架空的镇长","bookId":4046},{"isFee":0,"bought":0,"fee":0,"id":2882990,"title":"第2章 单打独斗","bookId":4046},{"isFee":0,"bought":0,"fee":0,"id":2883004,"title":"第3章 赤膊上阵","bookId":4046},{"isFee":0,"bought":0,"fee":0,"id":2883020,"title":"第4章 危机重重","bookId":4046},{"isFee":0,"bought":0,"fee":0,"id":2883037,"title":"第5章 冠冕堂皇的理由","bookId":4046},{"isFee":0,"bought":0,"fee":0,"id":2883050,"title":"第6章 强势镇长初露锋芒","bookId":4046},{"isFee":0,"bought":0,"fee":0,"id":2883063,"title":"第7章 借力打力","bookId":4046},{"isFee":0,"bought":0,"fee":0,"id":2883077,"title":"第8章 继续打脸","bookId":4046},{"isFee":0,"bought":0,"fee":0,"id":2883090,"title":"第9章 分化拉拢","bookId":4046},{"isFee":0,"bought":0,"fee":0,"id":2883104,"title":"第10章 柳擎宇发飙","bookId":4046}]
     */

    private int total;
    private List<CListBean> cList;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<CListBean> getCList() {
        return cList;
    }

    public void setCList(List<CListBean> cList) {
        this.cList = cList;
    }

    public static class CListBean {
        /**
         * isFee : 0
         * bought : 0
         * fee : 0
         * id : 2882974
         * title : 第1章 被架空的镇长
         * bookId : 4046
         */

        private int isFee;
        private int bought;
        private int fee;
        private int id;
        private String title;
        private int bookId;

        public int getIsFee() {
            return isFee;
        }

        public void setIsFee(int isFee) {
            this.isFee = isFee;
        }

        public int getBought() {
            return bought;
        }

        public void setBought(int bought) {
            this.bought = bought;
        }

        public int getFee() {
            return fee;
        }

        public void setFee(int fee) {
            this.fee = fee;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getBookId() {
            return bookId;
        }

        public void setBookId(int bookId) {
            this.bookId = bookId;
        }
    }
}
