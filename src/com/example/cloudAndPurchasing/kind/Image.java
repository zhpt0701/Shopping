package com.example.cloudAndPurchasing.kind;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/4/5 0005.
 */
public class Image implements Parcelable {

    /**
     * Category : 1
     * ShowImg : BilltImages/201605171439460.png
     * IsEnable : true
     * Sort : 1
     * Remark : 附图1
     */

    public int Category;
    public String ShowImg;
    public boolean IsEnable;
    public int Sort;
    public String Remark;
    public Bitmap bitmap;
    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void setCategory(int Category) {
        this.Category = Category;
    }

    public void setShowImg(String ShowImg) {
        this.ShowImg = ShowImg;
    }

    public void setIsEnable(boolean IsEnable) {
        this.IsEnable = IsEnable;
    }

    public void setSort(int Sort) {
        this.Sort = Sort;
    }

    public void setRemark(String Remark) {
        this.Remark = Remark;
    }

    public int getCategory() {
        return Category;
    }

    public String getShowImg() {
        return ShowImg;
    }

    public boolean getIsEnable() {
        return IsEnable;
    }

    public int getSort() {
        return Sort;
    }

    public String getRemark() {
        return Remark;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
