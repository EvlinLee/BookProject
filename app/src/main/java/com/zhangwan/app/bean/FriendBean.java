package com.zhangwan.app.bean;

import java.util.List;

public class FriendBean {

    /**
     * reward : 60
     * count : 3
     * list : [{"reward":20,"nikename":"Daisy_江","createTime":" 1507537660 "},{"reward":20,"nikename":"Daisy_江","createTime":" 1507537660 "},{"reward":20,"nikename":"Daisy_江","createTime":" 1507537660 "}]
     */

    private int reward;
    private int count;
    private List<ListBean> list;

    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * reward : 20
         * nikename : Daisy_江
         * createTime :  1507537660
         */

        private int reward;
        private String nikename;
        private String createTime;

        public int getReward() {
            return reward;
        }

        public void setReward(int reward) {
            this.reward = reward;
        }

        public String getNikename() {
            return nikename;
        }

        public void setNikename(String nikename) {
            this.nikename = nikename;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }
}
