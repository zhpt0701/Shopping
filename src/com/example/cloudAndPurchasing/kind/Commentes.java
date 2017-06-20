package com.example.cloudAndPurchasing.kind;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/4/14 0014.
 */
public class Commentes implements Parcelable{
    private String commentesid;
    private String commentesname;
    private String commentescontent;
    private String commentestitle;
    private String commemtestime;
    private Bitmap commentbitmap;
    private String commentpath;
    private String state;
    private byte[] aByte;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public byte[] getaByte() {
        return aByte;
    }

    public void setaByte(byte[] aByte) {
        this.aByte = aByte;
    }

    public String getCommentpath() {
        return commentpath;
    }

    public void setCommentpath(String commentpath) {
        this.commentpath = commentpath;
    }

    public String getCommentestitle() {
        return commentestitle;
    }

    public void setCommentestitle(String commentestitle) {
        this.commentestitle = commentestitle;
    }

    public String getCommentesid() {
        return commentesid;
    }

    public void setCommentesid(String commentesid) {
        this.commentesid = commentesid;
    }

    public String getCommentesname() {
        return commentesname;
    }

    public void setCommentesname(String commentesname) {
        this.commentesname = commentesname;
    }

    public String getCommentescontent() {
        return commentescontent;
    }

    public void setCommentescontent(String commentescontent) {
        this.commentescontent = commentescontent;
    }

    public String getCommemtestime() {
        return commemtestime;
    }

    public void setCommemtestime(String commemtestime) {
        this.commemtestime = commemtestime;
    }

    public Bitmap getCommentbitmap() {
        return commentbitmap;
    }

    public void setCommentbitmap(Bitmap commentbitmap) {
        this.commentbitmap = commentbitmap;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
