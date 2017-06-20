package com.example.cloudAndPurchasing.kind;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/5/4 0004.
 */
public class Single implements Parcelable {
    private String token;
    private String data;
    private String yesoron;
    private String nickname;
    private String ID;
    private String name;
    private String phone;
    private String url;
    private String levelid;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLevelid() {
        return levelid;
    }

    public void setLevelid(String levelid) {
        this.levelid = levelid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getYesoron() {
        return yesoron;
    }

    public void setYesoron(String yesoron) {
        this.yesoron = yesoron;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
