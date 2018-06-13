package com.zhangwan.app.bean;

import java.io.Serializable;

/**
 * 猜你喜欢
 * Created by Administrator on 2018/3/27 0027.
 */

public class LikeBean implements Serializable {

    private static final long serialVersionUID = -761618847875650322L;

    /**
     * intro : “这个女人，我要了！”惨遭男朋友卖掉的慕薇薇，摇身一变成为了叶少辰的妻子。 休息室内，他的阴狠，让她崩溃，“你娶我的目的究竟是什么？” 男人笑得邪冶，“娶你，当然是为了羞辱你了！” 然而…… “不许你想那个男人，我要去断了他的腿！” “我的女人只有我能欺负，谁敢动她一根汗毛，就是找死！” “谁准你晚上不回家的，有通知我吗？” 说好的折磨怎么感觉变了味道…… 他一路帮她虐渣，护她如宝，直到她发现这个新婚丈夫，其实有着不为人知的秘密…… 一个绝对不能靠近的房间…… 一个紫瞳，眼睛会发光的男人…… 两张一模一样的脸…… 谁才是她真正的丈夫？ 这究竟是怎么回事？
     * bookPic : https://myvippic.b0.upaiyun.com/vippic/72731512553853175.jpg
     * bookName : 闪婚神秘老公
     * bookId : 2580
     */

    private String intro;
    private String bookPic;
    private String bookName;
    private int bookId;

    public String author;
    public String type;
    public String words;
    public String readNum;
    public String chapter_id ;// 阅读章节id
    public int total;   //章节总数

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

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }

    public String getReadNum() {
        return readNum;
    }

    public void setReadNum(String readNum) {
        this.readNum = readNum;
    }

    public String getChapter_id() {
        return chapter_id;
    }

    public void setChapter_id(String chapter_id) {
        this.chapter_id = chapter_id;
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
