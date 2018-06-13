package com.zhangwan.app.bean;

/**
 * Created by zzg on 2018/3/30.
 */

public class MessageBean {

    /**
     * total : 0
     * create_time : 1523182060000
     * chapter_id : 2883050
     * pic : http://img.17k.com/images/bookcover/2013/3168/15/633684-1378050061000.jpg
     * book_name : 巅峰人生
     * content : 湿哒哒
     * bookId : 4046
     */

    private int total;
    private long create_time;
    private int chapter_id;
    private String pic;
    private String book_name;
    private String content;
    private int bookId;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public int getChapter_id() {
        return chapter_id;
    }

    public void setChapter_id(int chapter_id) {
        this.chapter_id = chapter_id;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
}
