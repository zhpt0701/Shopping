package com.example.cloudAndPurchasing.kind;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/4/13 0013.
 */
public class Count implements Parcelable{
    private String countid;
    private String countname;
    private String countnumber;
    private String counttime;
    private String countpeoplenumber;

    public String getCountid() {
        return countid;
    }

    public void setCountid(String countid) {
        this.countid = countid;
    }

    public String getCountname() {
        return countname;
    }

    public void setCountname(String countname) {
        this.countname = countname;
    }

    public String getCountnumber() {
        return countnumber;
    }

    public void setCountnumber(String countnumber) {
        this.countnumber = countnumber;
    }

    public String getCounttime() {
        return counttime;
    }

    public void setCounttime(String counttime) {
        this.counttime = counttime;
    }

    public String getCountpeoplenumber() {
        return countpeoplenumber;
    }

    public void setCountpeoplenumber(String countpeoplenumber) {
        this.countpeoplenumber = countpeoplenumber;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
