package com.zhangwan.app.bean;

import java.io.Serializable;

/**
 * 轮播
 * Created by Administrator on 2018/3/23 0023.
 */

public class BannerBean  implements Serializable {

    private static final long serialVersionUID = -763618847875651322L;


    /**
     * picUrl : https://myvippic.b0.upaiyun.com/vippic/26821519478945659.png
     * intro :  　　枪林弹雨，刀光剑影依然是我们不朽的英雄梦……《橙红年代》将拍电视剧，由华策克顿旗下宽厚文化出品，中文在线、嘉映影业联合出品。男女主演已经曝光，陈伟霆马思纯携手加盟，演绎刘子光与胡蓉，超豪华阵容燃爆来袭。
     * bookPic : http://img.17k.com/images/bookcover/2017/307/1/61574-1493886556000.jpg
     * bookName : 橙红年代
     * bookId : 4145
     */

    private String picUrl;
    private String intro;
    private String bookPic;
    private String bookName;
    private int bookId;

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
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
