package com.example.cloudAndPurchasing.kind;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/15 0015.
 */
public class CloudRoom implements Parcelable {

    /**
     * state : 1
     * msg : 查询成功
     * msgCode : null
     * token : WABKO73WYZ
     * data : [{"RID":"5045670769362545348","MainImg":"http://101.200.167.129:3342/Images/Product/201606271337310.png","Duration":3,"TotalCount":69,"TradingCount":19,"LeftCount":0,"LimitCount":10,"CreateUserID":"5731941075755017826","ID":"96706436196778470","PNumber":"2","Title":"moto手表","ProductID":"5664489196715666301","State":0},{"RID":"5568368402836689093","MainImg":"http://101.200.167.129:3342/Images/Product/201606271500130.png","Duration":3,"TotalCount":1288,"TradingCount":1,"LeftCount":0,"LimitCount":0,"CreateUserID":"5731941075755017826","ID":"96706436196886063","PNumber":"12","Title":"6s plus~玫瑰金","ProductID":"5521492145879766770","State":0},{"RID":"5640493215692448347","MainImg":"http://101.200.167.129:3342/Images/Product/201606271500130.png","Duration":3,"TotalCount":1288,"TradingCount":0,"LeftCount":1288,"LimitCount":0,"CreateUserID":"5731941075755017826","ID":"96706436196917917","PNumber":"13","Title":"6s plus~玫瑰金","ProductID":"5521492145879766770","State":0}]
     */

    public int state;
    public String msg;
    public Object msgCode;
    public String token;
    public ArrayList<DataEntity> data;
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    public static class DataEntity implements Parcelable{
        /**
         * RID : 5045670769362545348
         * MainImg : http://101.200.167.129:3342/Images/Product/201606271337310.png
         * Duration : 3
         * TotalCount : 69
         * TradingCount : 19
         * LeftCount : 0
         * LimitCount : 10
         * CreateUserID : 5731941075755017826
         * ID : 96706436196778470
         * PNumber : 2
         * Title : moto手表
         * ProductID : 5664489196715666301
         * State : 0
         */

        public String RID;
        public String MainImg;
        public String Duration;
        public String TotalCount;
        public String TradingCount;
        public String LeftCount;
        public String LimitCount;
        public String CreateUserID;
        public String ID;
        public String PNumber;
        public String Title;
        public String ProductID;
        public String State;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {

        }
    }
}
