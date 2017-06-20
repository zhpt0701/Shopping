package com.example.cloudAndPurchasing.kind;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/6/20 0020.
 */
public class Feedback implements Parcelable{

    /**
     * ID : 1
     * MemID : 2
     * Content : sample string 3
     * CreateTime : 2016-06-20 16:41:02
     * ReplyRemark : sample string 4
     * ReplyTime : 2016-06-20 16:41:02
     */

    private String ID;
    private String MemID;
    private String Content;
    private String CreateTime;
    private String ReplyRemark;
    private String ReplyTime;

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setMemID(String MemID) {
        this.MemID = MemID;
    }

    public void setContent(String Content) {
        this.Content = Content;
    }

    public void setCreateTime(String CreateTime) {
        this.CreateTime = CreateTime;
    }

    public void setReplyRemark(String ReplyRemark) {
        this.ReplyRemark = ReplyRemark;
    }

    public void setReplyTime(String ReplyTime) {
        this.ReplyTime = ReplyTime;
    }

    public String getID() {
        return ID;
    }

    public String getMemID() {
        return MemID;
    }

    public String getContent() {
        return Content;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public String getReplyRemark() {
        return ReplyRemark;
    }

    public String getReplyTime() {
        return ReplyTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
