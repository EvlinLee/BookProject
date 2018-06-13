package com.zhangwan.app.bean;

import java.io.Serializable;

/**
 * Created by zzg on 2018/3/24.
 */

public class BookBean implements Serializable {
    private static final long serialVersionUID = -763618847875650311L;
    public  String intro;
    public String bookPic;
    public String bookName;
    public String bookId;
    public String author;
    public String type;
    public String words;
    public String readNum;
    public String chapter_id ;// 阅读章节id
    public int total;   //章节总数

    public String getReadId() {
        return chapter_id;
    }

    public void setReadId(String readId) {
        this.chapter_id = readId;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getReadNum() {
        return readNum;
    }

    public void setReadNum(String readNum) {
        this.readNum = readNum;
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

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
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

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }
}
