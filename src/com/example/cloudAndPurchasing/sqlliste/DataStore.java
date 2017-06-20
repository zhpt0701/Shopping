package com.example.cloudAndPurchasing.sqlliste;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import com.example.cloudAndPurchasing.kind.City;
import com.example.cloudAndPurchasing.kind.TZone;
import com.example.cloudAndPurchasing.zhi.LogUtil;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/20 0020.
 */
public class DataStore {
    /**
     * 城市数据查询
     * @param runnable
     * @return
     */
    public static ArrayList<City> findallcityselect(Context runnable) {
       ArrayList<City> arrayList = new ArrayList<City>();
//        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(DBManger.DB_PATH + "/" + DBManger.packagesname, null);
//        Cursor cursor = sqLiteDatabase.rawQuery("select * from T_City", null);
//       if (cursor.moveToNext()){
//           do {
//                City city = new City(String,String);
//               city.setCityname(cursor.getString(cursor.getColumnIndex("CityName")));
//               city.setCityid(cursor.getInt(cursor.getColumnIndex("CitySort")));
//               Log.i("ksldfj",cursor.getColumnIndex("CitySort")+"-=-=-"+cursor.getString(cursor.getColumnIndex("CityName")));
//               arrayList.add(city);
//           }while (cursor.moveToNext());
//       }

        return arrayList;
    }

    /**
     * 对应区查询
     * @param context
     * @param citysort
     * @return
     */
    public static ArrayList<TZone> Zonedataselect(Context context, int citysort) {
        ArrayList<TZone> arrayList = new ArrayList<TZone>();
        Cursor cursor = null;
        try {
            SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(DBManger.DB_PATH + "/" + DBManger.packagesname, null);
            cursor = sqLiteDatabase.rawQuery("select * from T_Zone where CityID = ?",new String[]{String.valueOf(citysort)});
            if (cursor.moveToNext()){
                do {
                    TZone tZone = new TZone();
                    tZone.setZonename(cursor.getString(cursor.getColumnIndex("ZoneName")));
                    tZone.setZoneid(cursor.getInt(cursor.getColumnIndex("ZoneID")));
                    Log.i("ksldfj",cursor.getColumnIndex("ZoneName")+"-=-=-"+cursor.getString(cursor.getColumnIndex("ZoneID")));
                    arrayList.add(tZone);
                }while (cursor.moveToNext());
            }
            return arrayList;
        }catch (Exception e){
            e.printStackTrace();
            LogUtil.e("DataStore Zonedataselect error:",e.toString());
        }finally {
            if (cursor != null){
                cursor.close();
            }
        }
        return null;
    }

    /**
     * 根据城市名称获取id
     * @param activity
     * @param text
     * @return
     */
    public static int selectezoneID(Context activity, String text) {
        int id = 0;
        Cursor cursor = null;
        try {
            if (text != null){
                SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(DBManger.DB_PATH + "/" + DBManger.packagesname, null);
                cursor = sqLiteDatabase.rawQuery("select * from T_City where CityName = ?", new String[]{text});
                if (cursor.moveToFirst()){
                    id = cursor.getInt(cursor.getColumnIndex("CitySort"));
                }
            }
        }catch (Exception e){
            LogUtil.e("DataStore selectezoneID error:",e.toString());
            e.printStackTrace();
        }finally {
            if (cursor != null){
                cursor.close();
            }
        }
        return id;
    }
}
