package com.example.cloudAndPurchasing.kind;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Administrator on 2016/4/1 0001.
 */
public class PurchaseDateil implements Parcelable{


    /**
     * MainImg : Product/201605171403120.png
     * TotalCount : 11
     * PublicDate : 2016-05-30 00:59:34
     * WinnerID : 0
     * Winner : null
     * LuckyNumber : 0
     * ID : 1
     * PNumber : 1111
     * Title : aaaaa
     * ProductID : 5023531596075373398
     * State : 2
     */

    private String MainImg;
    private int TotalCount;
    private String PublicDate;
    private String WinnerID;
    private String Winner;
    private String Photopath;
    private String LuckyNumber;
    private String ID;
    private int PNumber;
    private String Title;
    private String ProductID;
    private int State;
    private Bitmap bitmap;
    private int allpople;
    private int surpluspople;
    private String carteuser;
    private String createtime;
    private String updateuser;
    private String updatetime;
    private String orderno;
    private String memid;
    private List<String> photopath_ol;
    private String TradingCount;
    private String adress;
    private String Statement;
    private String nextPnumber;
    private String Announcedtime;
    private String NextPID;

    public String getNextPnumber() {
        return nextPnumber;
    }

    public void setNextPnumber(String nextPnumber) {
        this.nextPnumber = nextPnumber;
    }

    public String getNextPID() {
        return NextPID;
    }

    public void setNextPID(String nextPID) {
        NextPID = nextPID;
    }

    public List<String> getPhotopath_ol() {
        return photopath_ol;
    }

    public void setPhotopath_ol(List<String> photopath_ol) {
        this.photopath_ol = photopath_ol;
    }

    public String getAnnouncedtime() {
        return Announcedtime;
    }

    public void setAnnouncedtime(String announcedtime) {
        Announcedtime = announcedtime;
    }

    public String getTradingCount() {
        return TradingCount;
    }

    public void setTradingCount(String tradingCount) {
        TradingCount = tradingCount;
    }

    public String getStatement() {
        return Statement;
    }

    public void setStatement(String statement) {
        Statement = statement;
    }

    public String getPhotopath() {
        return Photopath;
    }

    public void setPhotopath(String photopath) {
        Photopath = photopath;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getCarteuser() {
        return carteuser;
    }

    public void setCarteuser(String carteuser) {
        this.carteuser = carteuser;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getUpdateuser() {
        return updateuser;
    }

    public void setUpdateuser(String updateuser) {
        this.updateuser = updateuser;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public String getMemid() {
        return memid;
    }

    public void setMemid(String memid) {
        this.memid = memid;
    }

    public int getSurpluspople() {
        return surpluspople;
    }

    public void setSurpluspople(int surpluspople) {
        this.surpluspople = surpluspople;
    }

    public int getAllpople() {
        return allpople;
    }

    public void setAllpople(int allpople) {
        this.allpople = allpople;
    }

    public String getWinner() {
        return Winner;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void setWinner(String winner) {
        Winner = winner;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    public void setMainImg(String MainImg) {
        this.MainImg = MainImg;
    }

    public void setTotalCount(int TotalCount) {
        this.TotalCount = TotalCount;
    }

    public void setPublicDate(String PublicDate) {
        this.PublicDate = PublicDate;
    }

    public void setWinnerID(String WinnerID) {
        this.WinnerID = WinnerID;
    }

    public void setLuckyNumber(String LuckyNumber) {
        this.LuckyNumber = LuckyNumber;
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

    public void setState(int State) {
        this.State = State;
    }

    public String getMainImg() {
        return MainImg;
    }

    public int getTotalCount() {
        return TotalCount;
    }

    public String getPublicDate() {
        return PublicDate;
    }

    public String getWinnerID() {
        return WinnerID;
    }

    public String getLuckyNumber() {
        return LuckyNumber;
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

    public int getState() {
        return State;
    }
}
