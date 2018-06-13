package com.zhangwan.app.bean;

import java.util.List;

/**
 * 阅读历史
 * Created by Administrator on 2018/3/27 0027.
 */

public class HistoryBean {

    /**
     * total : 0
     * list : [{"id":"412","bookId":"4046","bookName":"巅峰人生","bookPic":"http://img.17k.com/images/bookcover/2013/3168/15/633684-1378050061000.jpg","totalNum":"2416","readNum":"7","newChapter":"完本感言（柳擎宇最终职务看这里）","chapterId":2883037,"isCollect":"1"}]
     */

    private int total;
    private List<ListBean> list;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id : 412
         * bookId : 4046
         * bookName : 巅峰人生
         * bookPic : http://img.17k.com/images/bookcover/2013/3168/15/633684-1378050061000.jpg
         * totalNum : 2416
         * readNum : 7
         * newChapter : 完本感言（柳擎宇最终职务看这里）
         * chapterId : 2883037
         * isCollect : 1
         */

        private String id;
        private String bookId;
        private String bookName;
        private String bookPic;
        private String totalNum;
        private String readNum;
        private String newChapter;
        private int chapterId;
        private String isCollect;

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

        public int getChapterId() {
            return chapterId;
        }

        public void setChapterId(int chapterId) {
            this.chapterId = chapterId;
        }

        public String getIsCollect() {
            return isCollect;
        }

        public void setIsCollect(String isCollect) {
            this.isCollect = isCollect;
        }
    }
}
