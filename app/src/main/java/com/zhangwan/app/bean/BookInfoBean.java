package com.zhangwan.app.bean;

import java.util.List;

/**
 * 书籍详情
 * Created by Administrator on 2018/3/24 0024.
 */

public class BookInfoBean {

    /**
     * id : 1359
     * bookName : 武极宗师
     * bookPic : https://myvippic.b0.upaiyun.com/vippic/49001512471996203.jpg
     * author : 风消逝
     * type : 玄幻
     * status : 1
     * intro : 当今时代，武道崛起。 　　一个平民，得到神秘的属性异能，平凡庸碌的人生就此改变，向着浩瀚星空，一步步前行。 　　“我叫方成，我大器不晚成！”
     * newChapter : {"createTime":"1511130537000","title":"第三十三章 原始一击"}
     * commentNum : 2
     * commentVos : [{"id":"5","userName":"落叶","userPic":"http://wx.qlogo.cn/mmopen/RE4dttM14oOCaJ2mRG0W47eItuKibF5TedLStiaUiaEyacjRVf6CGqvep9gfxVXaj5vV3XzRZp7jeMZ4Z6GBpNZFYWOUNY6AeWU/0","comment":"试试","createTime":"1515202756000","thumbsupNum":"0","isThumbsup":"0"},{"id":"4","userName":"落叶","userPic":"http://wx.qlogo.cn/mmopen/RE4dttM14oOCaJ2mRG0W47eItuKibF5TedLStiaUiaEyacjRVf6CGqvep9gfxVXaj5vV3XzRZp7jeMZ4Z6GBpNZFYWOUNY6AeWU/0","comment":"1231231111","createTime":"1515036673000","thumbsupNum":"0","isThumbsup":"0"}]
     * isCollect : 1
     * readNum : 1155
     * clickNum : null
     * readId : 681381
     * totle : 1156
     */

    private String id;
    private String bookName;
    private String bookPic;
    private String author;
    private String type;
    private String status;
    private String intro;
    private NewChapterBean newChapter;
    private String commentNum;
    private String isCollect;
    private String readNum;
    private String clickNum;
    private String readId;
    private String totle;
    private List<CommentVosBean> commentVos;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public NewChapterBean getNewChapter() {
        return newChapter;
    }

    public void setNewChapter(NewChapterBean newChapter) {
        this.newChapter = newChapter;
    }

    public String getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(String commentNum) {
        this.commentNum = commentNum;
    }

    public String getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(String isCollect) {
        this.isCollect = isCollect;
    }

    public String getReadNum() {
        return readNum;
    }

    public void setReadNum(String readNum) {
        this.readNum = readNum;
    }

    public String getClickNum() {
        return clickNum;
    }

    public void setClickNum(String clickNum) {
        this.clickNum = clickNum;
    }

    public String getReadId() {
        return readId;
    }

    public void setReadId(String readId) {
        this.readId = readId;
    }

    public String getTotle() {
        return totle;
    }

    public void setTotle(String totle) {
        this.totle = totle;
    }

    public List<CommentVosBean> getCommentVos() {
        return commentVos;
    }

    public void setCommentVos(List<CommentVosBean> commentVos) {
        this.commentVos = commentVos;
    }

    public static class NewChapterBean {
        /**
         * createTime : 1511130537000
         * title : 第三十三章 原始一击
         */

        private String createTime;
        private String title;

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public static class CommentVosBean {
        /**
         * id : 5
         * userName : 落叶
         * userPic : http://wx.qlogo.cn/mmopen/RE4dttM14oOCaJ2mRG0W47eItuKibF5TedLStiaUiaEyacjRVf6CGqvep9gfxVXaj5vV3XzRZp7jeMZ4Z6GBpNZFYWOUNY6AeWU/0
         * comment : 试试
         * createTime : 1515202756000
         * thumbsupNum : 0
         * isThumbsup : 0
         */

        private String id;
        private String userName;
        private String userPic;
        private String comment;
        private String createTime;
        private String thumbsupNum;
        private String isThumbsup;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserPic() {
            return userPic;
        }

        public void setUserPic(String userPic) {
            this.userPic = userPic;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getThumbsupNum() {
            return thumbsupNum;
        }

        public void setThumbsupNum(String thumbsupNum) {
            this.thumbsupNum = thumbsupNum;
        }

        public String getIsThumbsup() {
            return isThumbsup;
        }

        public void setIsThumbsup(String isThumbsup) {
            this.isThumbsup = isThumbsup;
        }
    }
}
