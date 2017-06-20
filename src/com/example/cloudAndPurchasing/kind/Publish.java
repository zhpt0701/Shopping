package com.example.cloudAndPurchasing.kind;

import android.graphics.Bitmap;
import android.media.*;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/5 0005.
 */
public class Publish implements Parcelable{
    private String publishid;
    private String nickname;
    private String shoppingname;
    private String publishnumber;
    private String publishcontent;
    private String imageviewthis;
    private String imagepath;
    private String time;
    private String QID;
    private String Faverconunt;
    private String Announcedtime;

    public String getAnnouncedtime() {
        return Announcedtime;
    }

    public void setAnnouncedtime(String announcedtime) {
        Announcedtime = announcedtime;
    }

    public String getFavorCount() {
        return FavorCount;
    }

    public void setFavorCount(String favorCount) {
        FavorCount = favorCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFaverconunt() {
        return Faverconunt;
    }

    public void setFaverconunt(String faverconunt) {
        Faverconunt = faverconunt;
    }

    public List<String> getArrayList() {
        return arrayList;
    }

    public void setArrayList(List<String> arrayList) {
        this.arrayList = arrayList;
    }

    private List<String> arrayList;
    private String ProductID;
    private String content;
    private String publichdate;
    private String commendnumber;
    private String FavorCount;
    private String title;

    public String getProductID() {
        return ProductID;
    }

    public void setProductID(String productID) {
        ProductID = productID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPublichdate() {
        return publichdate;
    }

    public void setPublichdate(String publichdate) {
        this.publichdate = publichdate;
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public String getQID() {
        return QID;
    }

    public void setQID(String QID) {
        this.QID = QID;
    }

    public String getShoppingname() {
        return shoppingname;
    }

    public void setShoppingname(String shoppingname) {
        this.shoppingname = shoppingname;
    }

    public String getPublishnumber() {
        return publishnumber;
    }

    public void setPublishnumber(String publishnumber) {
        this.publishnumber = publishnumber;
    }

    public String getPublishid() {
        return publishid;
    }

    public void setPublishid(String publishid) {
        this.publishid = publishid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPublishcontent() {
        return publishcontent;
    }

    public void setPublishcontent(String publishcontent) {
        this.publishcontent = publishcontent;
    }

    public String getImageviewthis() {
        return imageviewthis;
    }

    public void setImageviewthis(String imageviewthis) {
        this.imageviewthis = imageviewthis;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCommendnumber() {
        return commendnumber;
    }

    public void setCommendnumber(String commendnumber) {
        this.commendnumber = commendnumber;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
