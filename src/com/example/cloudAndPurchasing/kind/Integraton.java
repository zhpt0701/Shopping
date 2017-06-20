package com.example.cloudAndPurchasing.kind;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/6/8 0008.
 */
public class Integraton implements Parcelable{

    /**
     * MemID : 4791133423236132382
     * CumulativePoints : 10000
     * AvailablePoints : 9994
     * TotalExchangePoints : 0
     * UpdateUser : 800
     * UpdateTime : 2016-05-31 11:32:59
     */

    private String MemID;
    private int CumulativePoints;
    private int AvailablePoints;
    private int TotalExchangePoints;
    private String UpdateUser;
    private String UpdateTime;

    public void setMemID(String MemID) {
        this.MemID = MemID;
    }

    public void setCumulativePoints(int CumulativePoints) {
        this.CumulativePoints = CumulativePoints;
    }

    public void setAvailablePoints(int AvailablePoints) {
        this.AvailablePoints = AvailablePoints;
    }

    public void setTotalExchangePoints(int TotalExchangePoints) {
        this.TotalExchangePoints = TotalExchangePoints;
    }

    public void setUpdateUser(String UpdateUser) {
        this.UpdateUser = UpdateUser;
    }

    public void setUpdateTime(String UpdateTime) {
        this.UpdateTime = UpdateTime;
    }

    public String getMemID() {
        return MemID;
    }

    public int getCumulativePoints() {
        return CumulativePoints;
    }

    public int getAvailablePoints() {
        return AvailablePoints;
    }

    public int getTotalExchangePoints() {
        return TotalExchangePoints;
    }

    public String getUpdateUser() {
        return UpdateUser;
    }

    public String getUpdateTime() {
        return UpdateTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
