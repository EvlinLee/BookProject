package com.zhangwan.app.bean;

import java.io.Serializable;

public class RecommendBannerBean implements Serializable{

    private static final long serialVersionUID = 3972721666907023866L;
    /**
         * picUrl : https://myvippic.b0.upaiyun.com/vippic/30901522661064606.png
         * intro : 官之途，民为本，为官一任，造福一方！
         军人出身的柳擎宇，毅然转业进入官场，成为乡镇镇长，然而上任当天却被完全架空，甚至被晾在办公室无人理睬！
         且看脾气火爆，办事雷厉风行的他，如何凭借着机智头脑和层出不穷的手段，翻手间覆灭种种阴谋，步步高升！几十次微妙的官位升迁，数千场激烈的明争暗斗争，历经波折，踏上权力之巅！
         柳擎宇一直本着为官一任造福一方的为官原则，时刻都把国家利益和人民利益放在首位，他不惧怕任何困难，坚决真心实意的为老百姓做实事和好事，坚决与腐败分子作斗争，从不妥协！

         * bookPic : http://img.17k.com/images/bookcover/2013/3168/15/633684-1378050061000.jpg
         * bookName : 巅峰人生
         * bookId : 4046
         */

        private String picUrl;
        private String intro;
        private String bookPic;
        private String bookName;
        private int bookId;

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public String getBookPic() {
            return bookPic;
        }

        public void setBookPic(String bookPic) {
            this.bookPic = bookPic;
        }

        public String getBookName() {
            return bookName;
        }

        public void setBookName(String bookName) {
            this.bookName = bookName;
        }

        public int getBookId() {
            return bookId;
        }

        public void setBookId(int bookId) {
            this.bookId = bookId;
        }
}
