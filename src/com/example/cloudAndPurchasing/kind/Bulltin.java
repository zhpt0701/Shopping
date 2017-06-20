package com.example.cloudAndPurchasing.kind;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/5/23 0023.
 */
public class Bulltin implements Parcelable{


    /**
     * ID : 1
     * Title : 云购梦想上线了
     * Content : 花一元钱，实现梦想
     * State : V
     * CreateTime : 2016-05-31 15:18:57
     */

    private String ID;
    private String Title;
    private String Content;
    private String State;
    private String CreateTime;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public void setContent(String Content) {
        this.Content = Content;
    }

    public void setState(String State) {
        this.State = State;
    }

    public void setCreateTime(String CreateTime) {
        this.CreateTime = CreateTime;
    }

    public String getID() {
        return ID;
    }

    public String getTitle() {
        return Title;
    }

    public String getContent() {
        return Content;
    }

    public String getState() {
        return State;
    }

    public String getCreateTime() {
        return CreateTime;
    }
}
