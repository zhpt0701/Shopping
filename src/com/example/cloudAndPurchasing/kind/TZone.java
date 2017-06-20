package com.example.cloudAndPurchasing.kind;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/5/19 0019.
 */
public class TZone implements Parcelable{
    private int zoneid;
    private String zonename;
    private String zonezimu;

    public int getZoneid() {
        return zoneid;
    }

    public void setZoneid(int zoneid) {
        this.zoneid = zoneid;
    }

    public String getZonename() {
        return zonename;
    }

    public void setZonename(String zonename) {
        this.zonename = zonename;
    }

    public String getZonezimu() {
        return zonezimu;
    }

    public void setZonezimu(String zonezimu) {
        this.zonezimu = zonezimu;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
