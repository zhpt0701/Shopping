package com.example.cloudAndPurchasing.kind;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/4/5 0005.
 */
public class Winning implements Parcelable{
    private String winningid;
    private String winninggoodsname;
    private String Winningallman_time;
    private String winningman_time;
    private String winningluckCode;
    private String winningpublishtime;
    private String showImg;
    private String ID;
    private String address;
    private String AID;
    private int Pnumber;
    private String awardState;
    private String winnername;
    private String TotalCount;
    private String LeftCount;
    private String TradingCount;
    private String CourierNO;
    private String CourierCompany;
    private String IsDelivery;
    private String CustomCode;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLeftCount() {
        return LeftCount;
    }

    public void setLeftCount(String leftCount) {
        LeftCount = leftCount;
    }

    public String getIsDelivery() {
        return IsDelivery;
    }

    public void setIsDelivery(String isDelivery) {
        IsDelivery = isDelivery;
    }

    public String getCustomCode() {
        return CustomCode;
    }

    public void setCustomCode(String customCode) {
        CustomCode = customCode;
    }

    public String getAwardState() {
        return awardState;
    }

    public void setAwardState(String awardState) {
        this.awardState = awardState;
    }

    public String getWinnername() {
        return winnername;
    }

    public void setWinnername(String winnername) {
        this.winnername = winnername;
    }

    public String getAID() {
        return AID;
    }

    public void setAID(String AID) {
        this.AID = AID;
    }

    public String getShowImg() {
        return showImg;
    }

    public void setShowImg(String showImg) {
        this.showImg = showImg;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public int getPnumber() {
        return Pnumber;
    }

    public void setPnumber(int pnumber) {
        Pnumber = pnumber;
    }

    public String getTotalCount() {
        return TotalCount;
    }

    public void setTotalCount(String totalCount) {
        TotalCount = totalCount;
    }

    public String getTradingCount() {
        return TradingCount;
    }

    public void setTradingCount(String tradingCount) {
        TradingCount = tradingCount;
    }

    public String getCourierNO() {
        return CourierNO;
    }

    public void setCourierNO(String courierNO) {
        CourierNO = courierNO;
    }

    public String getCourierCompany() {
        return CourierCompany;
    }

    public void setCourierCompany(String courierCompany) {
        CourierCompany = courierCompany;
    }

    public String getWinningid() {
        return winningid;
    }

    public void setWinningid(String winningid) {
        this.winningid = winningid;
    }

    public String getWinninggoodsname() {
        return winninggoodsname;
    }

    public void setWinninggoodsname(String winninggoodsname) {
        this.winninggoodsname = winninggoodsname;
    }

    public String getWinningallman_time() {
        return Winningallman_time;
    }

    public void setWinningallman_time(String winningallman_time) {
        Winningallman_time = winningallman_time;
    }

    public String getWinningman_time() {
        return winningman_time;
    }

    public void setWinningman_time(String winningman_time) {
        this.winningman_time = winningman_time;
    }

    public String getWinningluckCode() {
        return winningluckCode;
    }

    public void setWinningluckCode(String winningluckCode) {
        this.winningluckCode = winningluckCode;
    }

    public String getWinningpublishtime() {
        return winningpublishtime;
    }

    public void setWinningpublishtime(String winningpublishtime) {
        this.winningpublishtime = winningpublishtime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
