package com.zhangwan.app.bean;

import java.io.Serializable;

/**
 * 推荐
 * Created by laoshiren on 2018/3/21.
 */

public class RecommendBean implements Serializable {

    private static final long serialVersionUID = -763618847875650323L;

    /**
     * total : 0
     * bookauthor : 零下5度01
     * intro : 大千世界，无奇不有，光怪陆离，浩瀚无尽，种族林立，唯有强者可攀上巅峰，俯视天下。 　　乱世之中，群雄并起，百家争鸣，万族林立，强者之路，唯大毅力者可掌造化，与天争道。 　　修行之道：通后天、返先天，凝聚顶三花、聚五气归元，通天灵、御神魂，窃阴阳生死、夺天地造化，参天道、释命格，与天争命。 　　天生异变，双日横空，一个奇特的生命带着天地本源，自曜日中诞生…… 　　破生死玄关、凝不败之躯，聚天地造化、铸不朽金身!
     * bookPic : https://myvippic.b0.upaiyun.com/vippic/35741512471996636.jpg
     * chapter_id : 681782
     * bookName : 造化炼体决
     * bookId : 1364
     */

    private int total;
    private String bookauthor;
    private String intro;
    private String bookPic;
    private int chapter_id;
    private String bookName;
    private int bookId;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getBookauthor() {
        return bookauthor;
    }

    public void setBookauthor(String bookauthor) {
        this.bookauthor = bookauthor;
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

    public int getChapter_id() {
        return chapter_id;
    }

    public void setChapter_id(int chapter_id) {
        this.chapter_id = chapter_id;
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
