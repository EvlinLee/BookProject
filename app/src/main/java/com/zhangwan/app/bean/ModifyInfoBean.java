package com.zhangwan.app.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 修改个人信息
 * Created by Administrator on 2018/3/31 0031.
 */

public class ModifyInfoBean implements Serializable {
    private String province;
    private String city;
    private String sex;
    private String nikeName;
    private String pic;
    private String birthDate;

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNikeName() {
        return nikeName;
    }

    public void setNikeName(String nikeName) {
        this.nikeName = nikeName;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
