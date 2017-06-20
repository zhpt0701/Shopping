package com.example.cloudAndPurchasing.kind;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/3/25 0025.
 */
public class Goods implements Parcelable {

    private String goodsid;
    private String Imagepath;
    private String goodsname;
    private String goodsmoney;
    private String goodsallpople;
    private String goods_surplus;
    private Bitmap goods_image;
    private String goods_current;
    private long goods_time;
    private double pregass;
    private String goodsxian;
    private String goodsnumber;
    private String goodspnumber;
    private String ProductID;
    private String State;
    private String ID;
    private String count;
    private String datatime;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getDatatime() {
        return datatime;
    }

    public void setDatatime(String datatime) {
        this.datatime = datatime;
    }

    private boolean xuanzhong;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getGoods_current() {
        return goods_current;
    }

    public void setGoods_current(String goods_current) {
        this.goods_current = goods_current;
    }

    public double getPregass() {
        return pregass;
    }

    public void setPregass(double pregass) {
        this.pregass = pregass;
    }

    public String getImagepath() {
        return Imagepath;
    }

    public void setImagepath(String imagepath) {
        Imagepath = imagepath;
    }

    public long getGoods_time() {
        return goods_time;
    }

    public void setGoods_time(long goods_time) {
        this.goods_time = goods_time;
    }

    public String getGoodspnumber() {
        return goodspnumber;
    }

    public void setGoodspnumber(String goodspnumber) {
        this.goodspnumber = goodspnumber;
    }

    public String getProductID() {
        return ProductID;
    }

    public void setProductID(String productID) {
        ProductID = productID;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public boolean getXuanzhong() {
        return xuanzhong;
    }

    public void setXuanzhong(boolean xuanzhong) {
        this.xuanzhong = xuanzhong;
    }

    public String getGoodsxian() {
        return goodsxian;
    }

    public String getGoodsnumber() {
        return goodsnumber;
    }

    public void setGoodsnumber(String goodsnumber) {
        this.goodsnumber = goodsnumber;
    }

    public void setGoodsxian(String goodsxian) {
        this.goodsxian = goodsxian;
    }

    public String getGoodsmoney() {
        return goodsmoney;
    }

    public void setGoodsmoney(String goodsmoney) {
        this.goodsmoney = goodsmoney;
    }

    public String getGoodsallpople() {
        return goodsallpople;
    }

    public void setGoodsallpople(String goodsallpople) {
        this.goodsallpople = goodsallpople;
    }

    public String getGoods_surplus() {
        return goods_surplus;
    }

    public void setGoods_surplus(String goods_surplus) {
        this.goods_surplus = goods_surplus;
    }

    public Bitmap getGoods_image() {
        return goods_image;
    }

    public void setGoods_image(Bitmap goods_image) {
        this.goods_image = goods_image;
    }

    public String getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(String goodsid) {
        this.goodsid = goodsid;
    }

    public String getGoodsname() {
        return goodsname;
    }

    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
