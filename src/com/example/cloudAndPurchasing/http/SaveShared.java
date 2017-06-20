package com.example.cloudAndPurchasing.http;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.example.cloudAndPurchasing.kind.Constants;
import com.example.cloudAndPurchasing.kind.Friend;
import com.example.cloudAndPurchasing.kind.Goods;
import com.example.cloudAndPurchasing.kind.Rooms;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/5 0005.
 */
public class SaveShared {
    private static String phone = null,nickname = null,name = null,level = null,url = null;
    /**
     * 存储个人数据
     * @param applicationContext
     * @param cont
     * @param token
     * @paramphone
     * @param pwd
     */
    public static void init(Context applicationContext, String cont,String token,String pwd) {
        if (applicationContext != null){
            SharedPreferences.Editor sharedPreferences = applicationContext
                    .getSharedPreferences("data",Context.MODE_PRIVATE).edit();
            try {
                JSONObject jsonObject = new JSONObject(cont);
                phone = jsonObject.getString("MobilePhone");
                nickname = jsonObject.getString("NickName");
                level = jsonObject.getString("LevelID");
                url = jsonObject.getString("HeaderUrl");
                sharedPreferences.putString("token",token);
                sharedPreferences.putString("phone",phone);
                sharedPreferences.putString("pwd",pwd);
                sharedPreferences.putString("UID",jsonObject.getString("ID"));
                sharedPreferences.putString(Constants.LOGIN_NAME,jsonObject.getString("LoginName"));
                sharedPreferences.putString("nickname",nickname);
                sharedPreferences.putString("name",name);
                sharedPreferences.putString("level",level);
                sharedPreferences.putString("url",url);
                sharedPreferences.commit();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * token更新
     * @param application
     * @param token
     */
    public static void sharedtokensave(Context application, String token) {
        try {
            SharedPreferences.Editor editor = application.getSharedPreferences("data",Context.MODE_PRIVATE).edit();
            editor.putString("token",token);
            editor.commit();
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    /**
     * 获取token值
     * @param context
     * @return
     */
    public static String tokenget(Context context){
        if (context != null){
            SharedPreferences sharedPreferences = context.getSharedPreferences("data",Context.MODE_PRIVATE);
            String token = sharedPreferences.getString("token","");
            return token;
        }
        return null;
    }
    /**
     * uid
     * @param context
     * @return
     */
    public static String uid(Context context){
        if (context != null){
            SharedPreferences sharedPreferences = context.getSharedPreferences("data",Context.MODE_PRIVATE);
            String uid = sharedPreferences.getString("UID","");
            return uid;
        }
        return null;
    }
    //构建JSON字符串
    public static String buildJson(Context context,HashMap<Integer,Goods> hashMap) throws JSONException {
        String jsondata = null;
        JSONArray json=new JSONArray();
        Iterator iter = hashMap.entrySet().iterator();
        while (iter.hasNext()) {
            JSONObject jsonObj=new JSONObject();
            Map.Entry entry = (Map.Entry) iter.next();
            Object key = entry.getKey();
            Goods goods =hashMap.get(key);
            jsonObj.put("ID", goods.getID());
            jsonObj.put("Count",goods.getCount());
            jsonObj.put("UnitPrice", goods.getGoodsmoney());
            //把每个数据当作一对象添加到数组里
            json.put(jsonObj);
        }
        jsondata=json.toString();
        Log.i("JSON", jsondata);
        return jsondata;
    }
    //构建JSON字符串(房间)
    public static String buildJsonroom(Context context,HashMap<Integer,Rooms> hashMap) throws JSONException {
        String jsondata = null;
        JSONArray json=new JSONArray();
        Iterator iter = hashMap.entrySet().iterator();
        while (iter.hasNext()) {
            JSONObject jsonObj=new JSONObject();
            Map.Entry entry = (Map.Entry) iter.next();
            Object key = entry.getKey();
            Rooms rooms =hashMap.get(key);
            Log.i("fwoefosl","d032yidhs"+key);
            jsonObj.put("ID", rooms.getID());
            Log.i("fwoefosl","d032yidhs"+rooms.getID());
            jsonObj.put("Count","1");
            jsonObj.put("UnitPrice", "1");
            //把每个数据当作一对象添加到数组里
            json.put(jsonObj);
        }
        jsondata=json.toString();
        Log.i("JSON", jsondata);
        return jsondata;
    }

    /**
     * 拼接jsonarray
     * @param application
     * @param hashMap
     * @param id
     * @param user
     * @return
     */
    public static String buildfriendJson(Application application, HashMap<Integer, Friend> hashMap,String id,String user) {
        String jsondata = null;
        JSONArray json=new JSONArray();
        Iterator iter = hashMap.entrySet().iterator();
        while (iter.hasNext()) {
            JSONObject jsonObj=new JSONObject();
            Map.Entry entry = (Map.Entry) iter.next();
            Object key = entry.getKey();
            Friend friend =hashMap.get(key);
            Log.i("fwoefosl", "d032yidhs" + key);
            try {
                jsonObj.put("MemID", Long.parseLong(friend.getID()));
                jsonObj.put("RoomID", Long.parseLong(id));
                jsonObj.put("Remark", "艾购房间");
                jsonObj.put("UpdateUser",SaveShared.uid(application));
                jsonObj.put("CreateUser", Long.parseLong(user));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //把每个数据当作一对象添加到数组里
            json.put(jsonObj);
        }
        jsondata=json.toString();
        Log.i("JSON", jsondata);
        return jsondata;
    }

    /**
     * 获取用户名
     * @param context
     * @return
     */
    public static String name_ol(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("data",Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("nickname","");
        return name;
    }

    /**
     * 获取登录名
     * @param context
     * @return
     */
    public static String loadong_name(Context context){
        if (context != null){
            SharedPreferences sharedPreferences = context.getSharedPreferences("data",Context.MODE_PRIVATE);
            String phone = sharedPreferences.getString("phone_ol","");
            return phone;
        }
       return null;
    }

    /**
     * 获取密码；
     * @param context
     * @return
     */
    public static String loading_pass(Context context){
        if(context != null){
            SharedPreferences sharedPreferences = context.getSharedPreferences("data",Context.MODE_PRIVATE);
            String pass = sharedPreferences.getString("pass_ol","");
            return pass;
        }
       return null;
    }
    /**
     * 自动登录信息储存
     * @param phone
     * @param password
     * @param context
     */
    public static void loading(String phone,String password,Context context){
        if (context != null){
            SharedPreferences.Editor editor = context.getSharedPreferences("data",Context.MODE_PRIVATE).edit();
            editor.putString("phone_ol",phone);
            editor.putString("pass_ol",password);
            editor.commit();
        }
    }

    /**
     * 存储商品件数
     * @param application
     * @param data
     */
    public static void sharedcarnumbersave(Context application, String data) {
        if (application != null){
            SharedPreferences.Editor editor = application.getSharedPreferences("data",Context.MODE_PRIVATE).edit();
            editor.putString("car_number",data);
            editor.commit();
        }
    }

    /**
     * 获取商品件数
     * @param context
     * @return
     */
    public static String getcarnumber(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("data",Context.MODE_PRIVATE);
        String number = sharedPreferences.getString("car_number","");
        return number;
    }
}
