package com.zhangwan.app.bean;

import java.util.List;

public class DayShareBean {

    /**
     * code : 200
     * msg : success
     * status : success
     * result : {"bookChapterContent":["　　","　　\u201c裴诗茵，今晚九点，继续到昨晚的总统套房见我！不然的话，后果自负！\u201d程逸奔那遥远如地狱般邪魅寒冷的声音在裴诗茵的脑。","　　"],"bookIntro":"坑爹的！只不过不小心撞到酷美男而已，谁知这男人英俊得像神祇，却腹黑得像恶魔。 有钱了不起啊，亿万总裁又如何？这个游戏她不玩了，总可以了吧！ 可程大总裁没这么好说话，他温柔的看着她：\u201c不玩没关系，那是你的自由，不过，你爸可是提前预支了你的嫁妆，我给他的那个项目价值超过一亿！赔我一亿，咱们之间就结束！\u201d \u201c一亿？\u201d开玩笑，一毛她倒有不少！ 超级蛋白质，笨蛋、白痴、神经质！ 好！陪你玩，玩到底，大不了玩一辈子，谁怕谁！ \u2026\u2026 四年后，她带着宝贝儿重返B市。 \u201c程逸奔还我女儿！\u201d \u201c没问题，只要你给我生个儿子，你女儿就还你\u2026\u2026\u201d 此文搞笑、暴虐、暧昧，有伤心、痛心、揪心、开心，更有激烈的情感对碰与化不开的浓情\u2026\u2026","bookPic":"https://myvippic.b0.upaiyun.com/vippic/89231512558423734.jpg","userId":391,"bookName":"惹上总裁要小心","bookChapterTitle":"第一章 死不承认","url":"http://hc.p2pzcw.com/book/book_share.html?userId=391&bookId=3001","state":0}
     */

    private String code;
    private String msg;
    private String status;
    private ResultBean result;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * bookChapterContent : ["　　","　　\u201c裴诗茵，今晚九点，继续到昨晚的总统套房见我！不然的话，后果自负！\u201d程逸奔那遥远如地狱般邪魅寒冷的声音在裴诗茵的脑。","　　"]
         * bookIntro : 坑爹的！只不过不小心撞到酷美男而已，谁知这男人英俊得像神祇，却腹黑得像恶魔。 有钱了不起啊，亿万总裁又如何？这个游戏她不玩了，总可以了吧！ 可程大总裁没这么好说话，他温柔的看着她：“不玩没关系，那是你的自由，不过，你爸可是提前预支了你的嫁妆，我给他的那个项目价值超过一亿！赔我一亿，咱们之间就结束！” “一亿？”开玩笑，一毛她倒有不少！ 超级蛋白质，笨蛋、白痴、神经质！ 好！陪你玩，玩到底，大不了玩一辈子，谁怕谁！ …… 四年后，她带着宝贝儿重返B市。 “程逸奔还我女儿！” “没问题，只要你给我生个儿子，你女儿就还你……” 此文搞笑、暴虐、暧昧，有伤心、痛心、揪心、开心，更有激烈的情感对碰与化不开的浓情……
         * bookPic : https://myvippic.b0.upaiyun.com/vippic/89231512558423734.jpg
         * userId : 391
         * bookName : 惹上总裁要小心
         * bookChapterTitle : 第一章 死不承认
         * url : http://hc.p2pzcw.com/book/book_share.html?userId=391&bookId=3001
         * state : 0
         */

        private String bookIntro;
        private String bookPic;
        private int userId;
        private String bookName;
        private String bookChapterTitle;
        private String url;
        private int state;
        private List<String> bookChapterContent;

        public String getBookIntro() {
            return bookIntro;
        }

        public void setBookIntro(String bookIntro) {
            this.bookIntro = bookIntro;
        }

        public String getBookPic() {
            return bookPic;
        }

        public void setBookPic(String bookPic) {
            this.bookPic = bookPic;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getBookName() {
            return bookName;
        }

        public void setBookName(String bookName) {
            this.bookName = bookName;
        }

        public String getBookChapterTitle() {
            return bookChapterTitle;
        }

        public void setBookChapterTitle(String bookChapterTitle) {
            this.bookChapterTitle = bookChapterTitle;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public List<String> getBookChapterContent() {
            return bookChapterContent;
        }

        public void setBookChapterContent(List<String> bookChapterContent) {
            this.bookChapterContent = bookChapterContent;
        }
    }
}
