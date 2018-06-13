package com.zhangwan.app.bean;

public class AllCommentBean {

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
