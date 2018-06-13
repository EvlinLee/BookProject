package com.zhangwan.app.bean;

/**
 * 登录
 * Created by Administrator on 2018/3/13 0013.
 */

public class LoginBean {

    /**
     * code : 200
     * msg : 登录成功！
     * status : success
     * result : {"userBalacne":0,"userPic":"http://thirdwx.qlogo.cn/mmopen/Y7hLYYe1rCd9ITWmLAljIPNEaOHCeByzonHpXlGKGYAiaRZjSxBG6J1OeOsjia1ibWUerhXHibwUvyn7fb57WHPMDJJwS2Gl6Mbe/132","userNo":"5845381","userName":"17687470287","userId":371,"isVip":0,"token":"77e9dddeedf3167fde9ce819a4ffe9a5"}
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
         * userBalacne : 0
         * userPic : http://thirdwx.qlogo.cn/mmopen/Y7hLYYe1rCd9ITWmLAljIPNEaOHCeByzonHpXlGKGYAiaRZjSxBG6J1OeOsjia1ibWUerhXHibwUvyn7fb57WHPMDJJwS2Gl6Mbe/132
         * userNo : 5845381
         * userName : 17687470287
         * userId : 371
         * isVip : 0
         * token : 77e9dddeedf3167fde9ce819a4ffe9a5
         */

        private int userBalacne;
        private String userPic;
        private String userNo;
        private String userName;
        private int userId;
        private int isVip;
        private String token;

        public int getUserBalacne() {
            return userBalacne;
        }

        public void setUserBalacne(int userBalacne) {
            this.userBalacne = userBalacne;
        }

        public String getUserPic() {
            return userPic;
        }

        public void setUserPic(String userPic) {
            this.userPic = userPic;
        }

        public String getUserNo() {
            return userNo;
        }

        public void setUserNo(String userNo) {
            this.userNo = userNo;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getIsVip() {
            return isVip;
        }

        public void setIsVip(int isVip) {
            this.isVip = isVip;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
