package com.example.cloudAndPurchasing.kind;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ListView;

import java.util.List;

/**
 * Created by Administrator on 2016/5/19 0019.
 */
public class GoodsDateils implements Parcelable {

    /**
     * Statement : bbbbbbbbbbbbbbbbbbbb
     * Price : 1.0
     * LimitCount : 11
     * TotalCount : 11
     * TradingCount : 0
     * ID : 1
     * PNumber : 1111
     * Title : aaaaa
     * ProductID : 4828200143800396477
     * State : 0
     */

    private String Statement;
    private double Price;
    private int LimitCount;
    private int TotalCount;
    private int TradingCount;
    private String ID;
    private String LeftCount;
    private String money;
    private int PNumber;
    private String Title;
    private String ProductID;
    private String State;
    private List<String> ShowImg;

    public String getLeftCount() {
        return LeftCount;
    }

    public void setLeftCount(String leftCount) {
        LeftCount = leftCount;
    }

    public List<String> getShowImg() {
        return ShowImg;
    }

    public void setShowImg(List<String> showImg) {
        ShowImg = showImg;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public void setStatement(String Statement) {
        this.Statement = Statement;
    }

    public void setPrice(double Price) {
        this.Price = Price;
    }

    public void setLimitCount(int LimitCount) {
        this.LimitCount = LimitCount;
    }

    public void setTotalCount(int TotalCount) {
        this.TotalCount = TotalCount;
    }

    public void setTradingCount(int TradingCount) {
        this.TradingCount = TradingCount;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setPNumber(int PNumber) {
        this.PNumber = PNumber;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public void setProductID(String ProductID) {
        this.ProductID = ProductID;
    }

    public void setState(String State) {
        this.State = State;
    }

    public String getStatement() {
        return Statement;
    }

    public double getPrice() {
        return Price;
    }

    public int getLimitCount() {
        return LimitCount;
    }

    public int getTotalCount() {
        return TotalCount;
    }

    public int getTradingCount() {
        return TradingCount;
    }

    public String getID() {
        return ID;
    }

    public int getPNumber() {
        return PNumber;
    }

    public String getTitle() {
        return Title;
    }

    public String getProductID() {
        return ProductID;
    }

    public String getState() {
        return State;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
