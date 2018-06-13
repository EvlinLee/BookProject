package com.zhangwan.app.bean;

import java.io.Serializable;

// 书架轮播
public class BookShelfBannerBean implements Serializable{
    private static final long serialVersionUID = -881240196076516134L;
    /**
     * intro : 一名苦逼的小武者的意识海中突然出现一本神秘古书，内含“武神演武”和“浩瀚岁月”。 　　“武神演武”，可以重演武神的一切神技。武道系、符文系、炼丹系、炼器系、驭兽系、冒险系、祭祀系......武神惊世骇俗的各系神技，在他身上得到重演。 　　“浩瀚岁月”，可以阅览古神无数年冒险浩瀚历史，所经历的无数隐秘......爆棚的阅历，令他步步抢先。 　　从此，各大豪门世家争相拉拢，行会大师为其醉心痴迷，绝世美女为他争风吃醋。小武者以傲世之姿，登上波澜壮阔的神武大陆舞台，一步步成为灿然星空下第一神武。
     * bookPic : https://myvippic.b0.upaiyun.com/vippic/2301512471996567.jpg
     * bookName : 神武觉醒
     * bookId : 1367
     */

    private String intro;
    private String bookPic;
    private String bookName;
    public  String author;
    public  String chapterId;
    public  String title;
    private int    bookId;
    private int    total;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
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
