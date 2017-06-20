package com.example.cloudAndPurchasing.kind;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Administrator on 2016/6/3 0003.
 * 接收支付返回结果的实体类
 */
public class Pize implements Parcelable{

    /**
     * ID : 4643400894197945616
     * PNumber : 1
     * PeriodsTitle : Pandora
     * LuckyNumbers : [100000263]
     */

    private String ID;
    private int PNumber;
    private String count;
    private String PeriodsTitle;
    private List<Integer> LuckyNumbers;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

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

    public void setPNumber(int PNumber) {
        this.PNumber = PNumber;
    }

    public void setPeriodsTitle(String PeriodsTitle) {
        this.PeriodsTitle = PeriodsTitle;
    }

    public void setLuckyNumbers(List<Integer> LuckyNumbers) {
        this.LuckyNumbers = LuckyNumbers;
    }

    public String getID() {
        return ID;
    }

    public int getPNumber() {
        return PNumber;
    }

    public String getPeriodsTitle() {
        return PeriodsTitle;
    }

    public List<Integer> getLuckyNumbers() {
        return LuckyNumbers;
    }
}
