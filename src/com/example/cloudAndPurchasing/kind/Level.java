package com.example.cloudAndPurchasing.kind;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2016/4/28 0028.
 */
public class Level {
    private String levelname;
    private String levelid;
    private String nackname;
    private Bitmap levelphoto;
    private String jiangyan;

    public String getJiangyan() {
        return jiangyan;
    }

    public void setJiangyan(String jiangyan) {
        this.jiangyan = jiangyan;
    }

    public String getLevelname() {
        return levelname;
    }

    public void setLevelname(String levelname) {
        this.levelname = levelname;
    }

    public String getLevelid() {
        return levelid;
    }

    public void setLevelid(String levelid) {
        this.levelid = levelid;
    }

    public String getNackname() {
        return nackname;
    }

    public void setNackname(String nackname) {
        this.nackname = nackname;
    }

    public Bitmap getLevelphoto() {
        return levelphoto;
    }

    public void setLevelphoto(Bitmap levelphoto) {
        this.levelphoto = levelphoto;
    }
}
