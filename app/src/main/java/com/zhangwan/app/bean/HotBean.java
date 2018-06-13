package com.zhangwan.app.bean;

import java.io.Serializable;

/**
 * 热门小说
 * Created by Administrator on 2018/3/23 0023.
 */

public class HotBean implements Serializable {

    private static final long serialVersionUID = -763611817875650322L;

    /**
     * author : 梦入洪荒
     * intro : 官之途，民为本，为官一任，造福一方！
     * 军人出身的柳擎宇，毅然转业进入官场，成为乡镇镇长，然而上任当天却被完全架空，甚至被晾在办公室无人理睬！
     * 且看脾气火爆，办事雷厉风行的他，如何凭借着机智头脑和层出不穷的手段，翻手间覆灭种种阴谋，步步高升！几十次微妙的官位升迁，数千场激烈的明争暗斗争，历经波折，踏上权力之巅！
     * 柳擎宇一直本着为官一任造福一方的为官原则，时刻都把国家利益和人民利益放在首位，他不惧怕任何困难，坚决真心实意的为老百姓做实事和好事，坚决与腐败分子作斗争，从不妥协！
     * <p>
     * words : 7657733
     * bookPic : http://img.17k.com/images/bookcover/2013/3168/15/633684-1378050061000.jpg
     * type : 都市
     * bookName : 巅峰人生
     * bookId : 4046
     */

    private String author;
    private String intro;
    private int words;
    private String bookPic;
    private String type;
    private String bookName;
    private int total;
    private String chapter_id;
    private int bookId;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getChapter_id() {
        return chapter_id;
    }

    public void setChapter_id(String chapter_id) {
        this.chapter_id = chapter_id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public int getWords() {
        return words;
    }

    public void setWords(int words) {
        this.words = words;
    }

    public String getBookPic() {
        return bookPic;
    }

    public void setBookPic(String bookPic) {
        this.bookPic = bookPic;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
