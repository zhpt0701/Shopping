package com.example.cloudAndPurchasing.kind;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/4/1 0001.
 */
public class City implements Parcelable {
    private int cityid;
    private String cityname;
    private String citypinyin;
    private CharSequence sortLetters;
    public City(String name, String pinyi) {
        super();
        cityname = name;
        citypinyin = pinyi;
    }
    public String getCitypinyin() {
        return citypinyin;
    }

    public void setCitypinyin(String citypinyin) {
        this.citypinyin = citypinyin;
    }

    public int getCityid() {
        return cityid;
    }

    public void setCityid(int cityid) {
        this.cityid = cityid;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
