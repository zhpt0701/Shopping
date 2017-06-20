package com.example.cloudAndPurchasing.kind;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2016/7/29 0029.
 */
public class SharePreferencesUlits {
    private static SharedPreferences sp;
    /**
     * 保存boolean信息
     * key : 保存信息key
     * value ： 保存信息的值
     */
    public static void saveBoolean(Context context,String key,boolean value){
        if (sp == null) {
            sp = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        }
        sp.edit().putBoolean(key, value).commit();
    }

    /**
     * 获取boolean信息
     * key : 保存信息key
     * defValue ：缺省的值
     */
    public static boolean getBoolean(Context context,String key,boolean defValue){
        if (sp == null) {
            sp = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        }
        return sp.getBoolean(key, defValue);
    }

    /**
     * 保存String信息
     * key : 保存信息key
     * value ： 保存信息的值
     */
    public static void saveString(Context context,String key,String value){
        if (sp == null) {
            sp = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        }
        sp.edit().putString(key, value).commit();
    }

    /**
     * 获取String信息
     * key : 保存信息key
     * defValue ：缺省的值
     */
    public static String getString(Context context,String key,String defValue){
        if (sp == null) {
            sp = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        }
        return sp.getString(key, defValue);
    }

}
