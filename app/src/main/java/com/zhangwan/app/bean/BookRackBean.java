package com.zhangwan.app.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 我的书架
 * Created by Administrator on 2018/3/26 0026.
 */

public class BookRackBean implements Serializable {

    private static final long serialVersionUID = -5214557642905161272L;
    /**
     * bs : [{"id":"33","bookId":"4046","bookName":"巅峰人生","bookPic":"http://img.17k.com/images/bookcover/2013/3168/15/633684-1378050061000.jpg","totalNum":"2416","readNum":"7","newChapter":"完本感言（柳擎宇最终职务看这里）","readId":"2883037"}]
     * total : 1
     */

    private int total;
    private List<BsBean> bs;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<BsBean> getBs() {
        return bs;
    }

    public void setBs(List<BsBean> bs) {
        this.bs = bs;
    }

    public static class BsBean implements Serializable {
        private static final long serialVersionUID = -1791533266066033300L;
        /**
         * id : 33
         * bookId : 4046
         * bookName : 巅峰人生
         * bookPic : http://img.17k.com/images/bookcover/2013/3168/15/633684-1378050061000.jpg
         * totalNum : 2416
         * readNum : 7
         * newChapter : 完本感言（柳擎宇最终职务看这里）
         * readId : 2883037
         */

        private String id;
        private String bookId;
        private String bookName;
        private String bookPic;
        private String totalNum;
        private String readNum;
        private String newChapter;
        private String readId;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getBookId() {
            return bookId;
        }

        public void setBookId(String bookId) {
            this.bookId = bookId;
        }

        public String getBookName() {
            return bookName;
        }

        public void setBookName(String bookName) {
            this.bookName = bookName;
        }

        public String getBookPic() {
            return bookPic;
        }

        public void setBookPic(String bookPic) {
            this.bookPic = bookPic;
        }

        public String getTotalNum() {
            return totalNum;
        }

        public void setTotalNum(String totalNum) {
            this.totalNum = totalNum;
        }

        public String getReadNum() {
            return readNum;
        }

        public void setReadNum(String readNum) {
            this.readNum = readNum;
        }

        public String getNewChapter() {
            return newChapter;
        }

        public void setNewChapter(String newChapter) {
            this.newChapter = newChapter;
        }

        public String getReadId() {
            return readId;
        }

        public void setReadId(String readId) {
            this.readId = readId;
        }
    }
}
