package com.example.cloudAndPurchasing.kind;

import android.os.Parcel;
import android.os.Parcelable;

import java.sql.DataTruncation;

/**
 * Created by Administrator on 2016/3/25 0025.
 */
public class Category implements Parcelable {
    private String categoryid;
    private String categoryname;
    private String coding;

    public String getCoding() {
        return coding;
    }

    public void setCoding(String coding) {
        this.coding = coding;
    }

    public String getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(String categoryid) {
        this.categoryid = categoryid;
    }


    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}

