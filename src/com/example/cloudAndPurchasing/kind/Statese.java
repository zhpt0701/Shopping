package com.example.cloudAndPurchasing.kind;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/5/20 0020.
 */
public class Statese implements Parcelable{
    private String msg;
    private String state;
    private String msgcode;
    private int AddFriendCount;
    private int inviteCount;
    private String intergl;
    private String chargal;
    private String data;
    private ArrayList<Count> arrayList;

    public ArrayList<Count> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<Count> arrayList) {
        this.arrayList = arrayList;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getChargal() {
        return chargal;
    }

    public void setChargal(String chargal) {
        this.chargal = chargal;
    }

    public String getIntergl() {
        return intergl;
    }

    public void setIntergl(String intergl) {
        this.intergl = intergl;
    }

    public int getAddFriendCount() {
        return AddFriendCount;
    }

    public void setAddFriendCount(int addFriendCount) {
        AddFriendCount = addFriendCount;
    }

    public int getInviteCount() {
        return inviteCount;
    }

    public void setInviteCount(int inviteCount) {
        this.inviteCount = inviteCount;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMsgcode() {
        return msgcode;
    }

    public void setMsgcode(String msgcode) {
        this.msgcode = msgcode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
