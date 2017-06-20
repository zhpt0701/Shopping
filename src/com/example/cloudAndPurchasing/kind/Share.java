package com.example.cloudAndPurchasing.kind;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/4/13 0013.
 */
public class Share implements Parcelable{
    private String shareid;
    private String sharetitle;
    private String sharecontent;
    private String sharetime;
    private String sharegoodsname;
    private String sharename;
    private String shareimage;
    private String pid;
    private String QId;

    public String getQId() {
        return QId;
    }

    public void setQId(String QId) {
        this.QId = QId;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getShareimage() {
        return shareimage;
    }

    public void setShareimage(String shareimage) {
        this.shareimage = shareimage;
    }

    public String getShareid() {
        return shareid;
    }

    public void setShareid(String shareid) {
        this.shareid = shareid;
    }

    public String getSharetitle() {
        return sharetitle;
    }

    public void setSharetitle(String sharetitle) {
        this.sharetitle = sharetitle;
    }

    public String getSharecontent() {
        return sharecontent;
    }

    public void setSharecontent(String sharecontent) {
        this.sharecontent = sharecontent;
    }

    public String getSharetime() {
        return sharetime;
    }

    public void setSharetime(String sharetime) {
        this.sharetime = sharetime;
    }

    public String getSharegoodsname() {
        return sharegoodsname;
    }

    public void setSharegoodsname(String sharegoodsname) {
        this.sharegoodsname = sharegoodsname;
    }

    public String getSharename() {
        return sharename;
    }

    public void setSharename(String sharename) {
        this.sharename = sharename;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
