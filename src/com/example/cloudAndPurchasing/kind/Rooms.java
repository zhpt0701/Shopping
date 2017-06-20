package com.example.cloudAndPurchasing.kind;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/5/13 0013.
 */
public class Rooms implements Parcelable{
    private String roomod;
    private String roomname;
    private String roomgoodsname;
    private String roomgoodsid;
    private String roomfriendid;
    private String roomfriendname;
    private String roomgoodsallnumber;
    private String roomgoodssurplus;
    private String roomtimes;
    private String limitCount;
    private String roomsimnagepath;
    private Bitmap roomgoodsimage;
    private Bitmap roomfriendimage;
    private String productID;
    private String createuserID;
    private String ID;
    private String TradingCount;
    private String PNumber;

    public String getLimitCount() {
        return limitCount;
    }

    public void setLimitCount(String limitCount) {
        this.limitCount = limitCount;
    }

    public String getTradingCount() {
        return TradingCount;
    }

    public void setTradingCount(String tradingCount) {
        TradingCount = tradingCount;
    }

    public String getPNumber() {
        return PNumber;
    }

    public void setPNumber(String PNumber) {
        this.PNumber = PNumber;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getCreateuserID() {
        return createuserID;
    }

    public void setCreateuserID(String createuserID) {
        this.createuserID = createuserID;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getRoomsimnagepath() {
        return roomsimnagepath;
    }

    public void setRoomsimnagepath(String roomsimnagepath) {
        this.roomsimnagepath = roomsimnagepath;
    }

    public String getRoomtimes() {
        return roomtimes;
    }

    public void setRoomtimes(String roomtimes) {
        this.roomtimes = roomtimes;
    }

    public Bitmap getRoomgoodsimage() {
        return roomgoodsimage;
    }

    public void setRoomgoodsimage(Bitmap roomgoodsimage) {
        this.roomgoodsimage = roomgoodsimage;
    }

    public Bitmap getRoomfriendimage() {
        return roomfriendimage;
    }

    public void setRoomfriendimage(Bitmap roomfriendimage) {
        this.roomfriendimage = roomfriendimage;
    }

    public String getRoomod() {
        return roomod;
    }

    public void setRoomod(String roomod) {
        this.roomod = roomod;
    }

    public String getRoomname() {
        return roomname;
    }

    public void setRoomname(String roomname) {
        this.roomname = roomname;
    }

    public String getRoomgoodsname() {
        return roomgoodsname;
    }

    public void setRoomgoodsname(String roomgoodsname) {
        this.roomgoodsname = roomgoodsname;
    }

    public String getRoomgoodsid() {
        return roomgoodsid;
    }

    public void setRoomgoodsid(String roomgoodsid) {
        this.roomgoodsid = roomgoodsid;
    }

    public String getRoomfriendid() {
        return roomfriendid;
    }

    public void setRoomfriendid(String roomfriendid) {
        this.roomfriendid = roomfriendid;
    }

    public String getRoomfriendname() {
        return roomfriendname;
    }

    public void setRoomfriendname(String roomfriendname) {
        this.roomfriendname = roomfriendname;
    }

    public String getRoomgoodsallnumber() {
        return roomgoodsallnumber;
    }

    public void setRoomgoodsallnumber(String roomgoodsallnumber) {
        this.roomgoodsallnumber = roomgoodsallnumber;
    }

    public String getRoomgoodssurplus() {
        return roomgoodssurplus;
    }

    public void setRoomgoodssurplus(String roomgoodssurplus) {
        this.roomgoodssurplus = roomgoodssurplus;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
