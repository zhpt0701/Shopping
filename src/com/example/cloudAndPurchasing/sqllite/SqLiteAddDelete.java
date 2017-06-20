package com.example.cloudAndPurchasing.sqllite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.util.Log;
import android.widget.Toast;
import com.example.cloudAndPurchasing.activity.activitycloud.activitythis.ActivityThisInformation;
import com.example.cloudAndPurchasing.activity.activityshopping.ActivityShoppingEach;
import com.example.cloudAndPurchasing.kind.Single;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2016/5/5 0005.
 */
public class SqLiteAddDelete {
    private static Set<String>  myset = new HashSet<String>();
    /**
     * 存入个人信息
     * @param arrayList
     * @param applicationContext
     */
    public static void insert(String arrayList, Context applicationContext,String token,String pass) {
        MySqLite mySqLite = new MySqLite(applicationContext);
        SQLiteDatabase sqLiteDatabase = mySqLite.getReadableDatabase();
        try {
            JSONObject jsonObject = new JSONObject(arrayList);
            Log.i("skljfklsd","jfskljfla"+jsonObject.getString("LevelID"));
            ContentValues values = new ContentValues();
            values.put(Personal.PHONE,jsonObject.getString("MobilePhone"));
            values.put(Personal.NICK_NAME,jsonObject.getString("NickName"));
            values.put(Personal.ID,jsonObject.getString("ID"));
//            values.put(Personal.NAME,jsonObject.getString("Name"));
            values.put(Personal.PASS,pass);
            values.put(Personal.HEARDERURL,jsonObject.getString("HeaderUrl"));
            values.put(Personal.LEVELID,jsonObject.getString("LevelID"));
            values.put(Personal.TOKEN,token);
            long cn = sqLiteDatabase.insert(Personal.TABLE_NAMES,null,values);
            Log.i(applicationContext+"",cn+"23123213");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 存入搜索内容
     * @param activityShoppingEach
     * @param content
     */
    public static void insertEach(Context activityShoppingEach, String content) {
        ArrayList<String> list = new ArrayList<String>();
        MySqLite mySqLite = new MySqLite(activityShoppingEach);
        SQLiteDatabase sqLiteDatabase = mySqLite.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        Cursor cursor = sqLiteDatabase.query(EachTable.TABLE_NAMES,null,null,null,null,null,null);
        if (cursor.moveToFirst()){
            do {
                list.add(cursor.getString(cursor.getColumnIndex(EachTable.CONTENT)));
            }while (cursor.moveToNext());
        }
        if(list.size()>0){
            if (!list.contains(content)){
                contentValues.put(EachTable.CONTENT,content);
                contentValues.put(EachTable.TIME_THIS,System.currentTimeMillis());
                long cn = sqLiteDatabase.insert(EachTable.TABLE_NAMES,null,contentValues);
                Log.i(activityShoppingEach+"",cn+"23123213");
            }else {
                updata_each(activityShoppingEach,content);
            }
        }else {
            contentValues.put(EachTable.CONTENT,content);
            contentValues.put(EachTable.TIME_THIS,System.currentTimeMillis());
            long cn = sqLiteDatabase.insert(EachTable.TABLE_NAMES,null,contentValues);
            Log.i(activityShoppingEach+"",cn+"23123213");
        }
        if (cursor != null){
            cursor.close();
        }
    }

    /**
     * 查询前5条搜索内容
     * @param context
     * @return
     */
    public static ArrayList<String> FindAllEach(Context context){
        ArrayList<String> list = new ArrayList<String>();
        MySqLite mySqLite = new MySqLite(context);
        SQLiteDatabase sql = mySqLite.getWritableDatabase();
        String sql_op = "select * from "+
                EachTable.TABLE_NAMES+" order by "+EachTable.TIME_THIS+" desc";
//         String sql_Ol ="select * from " + EachTable.TABLE_NAMES +
//        " Limit "+String.valueOf(1)+ " Offset " +String.valueOf(5);
        Cursor cursor = sql.rawQuery(sql_op,null);
        if (cursor.moveToFirst()){
            do {
                if (list.size()<5){
                    list.add(cursor.getString(cursor.getColumnIndex(EachTable.CONTENT)));
                    Log.i("activity",cursor.getString(cursor.getColumnIndex(EachTable.CONTENT))+"23123213");
                }else {
                    break;
                }
            }while (cursor.moveToNext());
        }
        if (cursor != null){
            cursor.close();
        }
        return list;
    }

    /**
     * 清除表数据
     * @param activityThisInformation
     */
    public static void deletetable(Context activityThisInformation) {
        MySqLite mySqLite = new MySqLite(activityThisInformation);
        SQLiteDatabase sqLiteDatabase = mySqLite.getWritableDatabase();
        sqLiteDatabase.execSQL("DELETE FROM "+EachTable.TABLE_NAMES);
    }
    /**
    public static void delete(Context activityThisInformation) {
        MySqLite mySqLite = new MySqLite(activityThisInformation);
        SQLiteDatabase sqLiteDatabase = mySqLite.getReadableDatabase();
        sqLiteDatabase.execSQL("delete from "+Personal.TABLE_NAMES);
    }*/
    /**
     * 修改内容
     * @param context
     * @param content
     */
    public static void updata_each(Context context,String content){
        MySqLite mySqLite = new MySqLite(context);
        SQLiteDatabase database = mySqLite.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(EachTable.TIME_THIS,System.currentTimeMillis());
        long con = database.update(EachTable.TABLE_NAMES,values,"content=?",new String[]{content});
        Log.i(context+"",con+"this_ol");
    }
}
