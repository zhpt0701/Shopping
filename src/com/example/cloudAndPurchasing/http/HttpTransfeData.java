package com.example.cloudAndPurchasing.http;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import com.example.cloudAndPurchasing.ase.AESUtils;
import com.example.cloudAndPurchasing.customcontrol.photocontol.Bimp;
import com.example.cloudAndPurchasing.kind.*;
import com.example.cloudAndPurchasing.mothendsol.Tailoring;
import com.example.cloudAndPurchasing.zhi.LogUtil;
import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangpengtao on 2016/5/3 0003.
 */
public class HttpTransfeData {
    private static String key_ol = "Custom-Auth-Name";
    /**
     * 账号注册
     * @param activitySigleUp
     * @param yanzheng
     * @param ph
     * @param pwd
     * @return
     */
    public static Statese httppostsingle(Context activitySigleUp, String yanzheng, String ph, String pwd) {
    String url = HttpApi.single;
        String fn = null;
        Statese statese = new Statese();
        HttpPost httpPost = new HttpPost(url);
        HttpClient httpClient = new DefaultHttpClient();
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
//        String bv = AESUtils.encode(pwd);
        try {
            String vb = AESUtils.enectyol(pwd);
            pairs.add(new BasicNameValuePair("Mobile", ph));
            pairs.add(new BasicNameValuePair("Pwd",vb));
            pairs.add(new BasicNameValuePair("code",yanzheng));
            pairs.add(new BasicNameValuePair("PromotionType","0"));
            pairs.add(new BasicNameValuePair("PromotionValue","0"));
            pairs.add(new BasicNameValuePair("IsAES","1"));
            UrlEncodedFormEntity encodedFormEntity = new UrlEncodedFormEntity(pairs,"utf-8");
            httpPost.setEntity(encodedFormEntity);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            LogUtil.d("HttpTransfeData httppostsingle", "" + httpResponse.getStatusLine().getStatusCode());
            if (httpResponse.getStatusLine().getStatusCode() == 200){
                HttpEntity httpEntity = httpResponse.getEntity();
                String nb = EntityUtils.toString(httpEntity,"utf-8");
                JSONObject jsonObject = new JSONObject(nb);
                if (jsonObject.getString("state").equals(String.valueOf(Numbers.ONE))){
                    fn = jsonObject.getString("msg");
                }else {
                    fn = jsonObject.getString("msg");
                }
                statese.setMsg(fn);
                statese.setState(jsonObject.getString("state"));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData httppostsingle error：", "" + e.toString());
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData httppostsingle error：", "" + e.toString());
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData httppostsingle error：", "" + e.toString());
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData httppostsingle error：", "" + e.toString());
        }
        return statese;
    }

    /**
     * 获取验证码
     * @param activitySigleUp
     * @param s
     * @return
     */
    public static String httpGetauthcode(Context activitySigleUp, String s,String num) {
        String auth = null;
        String url = HttpApi.phone_auth_code+s+"&CheckStr="+num;
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        try {
            HttpResponse httpResponse = httpClient.execute(httpGet);
            LogUtil.d("HttpTransfeData httpGetauthcode", "" + httpResponse.getStatusLine().getStatusCode());
            if (httpResponse.getStatusLine().getStatusCode() == 200){
                HttpEntity httpEntity = httpResponse.getEntity();
                String res = EntityUtils.toString(httpEntity,"utf-8");
                Log.i(activitySigleUp+"",res+"-=-=-=");
                JSONObject jsonObject = new JSONObject(res);
                if (jsonObject.getString("state").equals(String.valueOf(Numbers.ONE))){
                    auth = jsonObject.getString("data");
                }else {
                    auth = jsonObject.getString("data");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData httpGetauthcode error：", "" + e.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData httpGetauthcode error：", "" + e.toString());
        }catch (Exception e){
            LogUtil.e("HttpTransfeData httpGetauthcode error：", "" + e.toString());
        }
        return auth;
    }

    /**
     * 登陆
     * @param applicationContext
     * @param s
     * @param s1
     * @return
     */
    public static Statese httppostloding(Context applicationContext, String s, String s1) {
       ArrayList<Single> array = new ArrayList<Single>();
        Statese statese = new Statese();
        String fan = null;
        String denglu = HttpApi.Loding;
        HttpPost httpPost = new HttpPost(denglu);
        HttpClient client = new DefaultHttpClient();
        try {
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            String vb = AESUtils.enectyol(s1);
            pairs.add(new BasicNameValuePair("Account",s));
            pairs.add(new BasicNameValuePair("Pwd",vb));
            pairs.add(new BasicNameValuePair("IsAES","1"));
            UrlEncodedFormEntity encodedFormEntity = new UrlEncodedFormEntity(pairs,"utf-8");
            httpPost.setEntity(encodedFormEntity);
            HttpParams params = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(params, 10000); //设置连接超时
            HttpConnectionParams.setSoTimeout(params, 10000); //设置请求超时
            httpPost.setParams(params);
            HttpResponse httpResponse = client.execute(httpPost);
            LogUtil.d("HttpTransfeData httppostloding", "" + httpResponse.getStatusLine().getStatusCode());
            if (httpResponse.getStatusLine().getStatusCode() == 200){
                HttpEntity entity1 = httpResponse.getEntity();
                String vb1 = EntityUtils.toString(entity1,"utf-8");
                JSONObject jsonObject = new JSONObject(vb1);
                Log.i(applicationContext+"","dfkwe9fwe0u"+vb1);
                String dl = jsonObject.getString("state");
                if (dl.equals(String.valueOf(Numbers.ONE))){
                    array = HttpMothed.loadingjson(vb1,applicationContext,vb);
                    fan = jsonObject.getString("msg");
                }else {
                     fan=jsonObject.getString("msg");
                }
                statese.setMsg(fan);
                statese.setState(dl);
                return statese;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData httppostloding error：", "" + e.toString());
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData httppostloding error：", "" + e.toString());
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData httppostloding error：", "" + e.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData httppostloding error：", "" + e.toString());
        }catch (Exception e){
            e.printStackTrace();
            LogUtil.e("HttpTransfeData httppostloding error：", "" + e.toString());
        }
        return null;
    }

    /**
     * 手机号修改密码
     * @param applicationContext
     * @param s
     * @param s1
     * @param s2
     */
    public static Statese httpalterpassword(Context applicationContext, String s,String s1,String s2) {
        Statese statese = new Statese();
        String nl = HttpApi.Alter_password;
        HttpPost httpPost = new HttpPost(nl);
        HttpClient httpClient = new DefaultHttpClient();
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        String new_password = AESUtils.enectyol(s);
        pairs.add(new BasicNameValuePair("mobile",s1));
        pairs.add(new BasicNameValuePair("code",s2));
        pairs.add(new BasicNameValuePair("newPassword",new_password));
        pairs.add(new BasicNameValuePair("IsAES","1"));
        try {
            UrlEncodedFormEntity encodedFormEntity = new UrlEncodedFormEntity(pairs,"utf-8");
            httpPost.setEntity(encodedFormEntity);
            HttpParams params = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(params, 10000); //设置连接超时
            HttpConnectionParams.setSoTimeout(params, 10000); //设置请求超时
            httpPost.setParams(params);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            LogUtil.d("HttpTransfeData httpalterpassword", "" + httpResponse.getStatusLine().getStatusCode());
            if (httpResponse.getStatusLine().getStatusCode() == 200){
                HttpEntity httpEntity = httpResponse.getEntity();
                String alter = EntityUtils.toString(httpEntity,"utf-8");
                JSONObject jsonObject = new JSONObject(alter);
                if (jsonObject.getString("state").equals(String.valueOf(Numbers.ONE))){

                }
                statese.setState(jsonObject.getString("state"));
                statese.setMsg(jsonObject.getString("msg"));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData httpalterpassword error：", "" + e.toString());
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData httpalterpassword error：", "" + e.toString());
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData httpalterpassword error：", "" + e.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData httpalterpassword error：", "" + e.toString());
        }catch (Exception e){
            e.printStackTrace();
            LogUtil.e("HttpTransfeData httpalterpassword error：", "" + e.toString());
        }
        return statese;
    }

    /**
     * 根据旧密码修改新密码
     * @param applicationContext
     * @param password
     * @param ensure
     * @return
     */
    public static Statese httpoldpassword(Context applicationContext, String password, String ensure,String token) {
        Statese statese = new Statese();
        String url = HttpApi.new_password;
        HttpPost httpPost = new HttpPost(url);
        HttpClient httpClient = new DefaultHttpClient();
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        String old = AESUtils.enectyol(password);
        String newps = AESUtils.enectyol(ensure);
        pairs.add(new BasicNameValuePair("oldPwd", old));
        pairs.add(new BasicNameValuePair("newPwd",newps));
        pairs.add(new BasicNameValuePair("isDelToken", "true"));
        pairs.add(new BasicNameValuePair("ID","1"));
        try {
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(pairs,"utf-8");
            httpPost.setEntity(urlEncodedFormEntity);
            httpPost.setHeader(key_ol, token);
            HttpParams params = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(params, 10000); //设置连接超时
            HttpConnectionParams.setSoTimeout(params, 10000); //设置请求超时
            httpPost.setParams(params);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            LogUtil.d("HttpTransfeData httpoldpassword", "" + httpResponse.getStatusLine().getStatusCode());
            if (httpResponse.getStatusLine().getStatusCode() == 200){
                HttpEntity httpEntity = httpResponse.getEntity();
                String alter = EntityUtils.toString(httpEntity,"utf-8");
                JSONObject jsonObject = new JSONObject(alter);
                if (jsonObject.getString("state").equals(String.valueOf(Numbers.ONE))){

                }
                if (!StringUtils.isEmpty(jsonObject.getString("token"))){
                    SaveShared.sharedtokensave(applicationContext,jsonObject.getString("token"));
                }
                statese.setState(jsonObject.getString("state"));
                statese.setMsg(jsonObject.getString("msg"));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData httpoldpassword error：", "" + e.toString());
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData httpoldpassword error：", "" + e.toString());
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData httpoldpassword error：", "" + e.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData httpoldpassword error：", "" + e.toString());
        }catch (Exception e){
            LogUtil.e("HttpTransfeData httpoldpassword error：", "" + e.toString());
        }
        return statese;
    }

    /**
     *binner广告
     */
    public static ArrayList<Image> httpad(Context context) {
        ArrayList<Image> arrayList = new ArrayList<Image>();
        String url = HttpApi.Ad;
        HttpGet httpGet = new HttpGet(url);
        HttpClient httpClient = new DefaultHttpClient();
        try {
            HttpParams params = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(params, 10000); //设置连接超时
            HttpConnectionParams.setSoTimeout(params, 10000); //设置请求超时
            httpGet.setParams(params);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            LogUtil.d("HttpTransfeData httpad", "" + httpResponse.getStatusLine().getStatusCode());
            if (httpResponse.getStatusLine().getStatusCode() == 200){
                HttpEntity httpEntity = httpResponse.getEntity();
                String vb = EntityUtils.toString(httpEntity,"uft-8");
                CacheUtils.setCache(vb,url,30);
                JSONObject jsonObject = new JSONObject(vb);
                if (jsonObject.getString("state").equals(String.valueOf(Numbers.ONE))){
                    arrayList = HttpMothed.banner(context,jsonObject.getString("data"));
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData httpad error：", "" + e.toString());
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData httpad error：", "" + e.toString());
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData httpad error：", "" + e.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData httpad error：", "" + e.toString());
        }catch (Exception e){
            LogUtil.e("HttpTransfeData httpad error：", "" + e.toString());
        }
        return arrayList;
    }

    /**
     * 获取服务时间
     * @param application
     */
    public static void httpgettime(Application application) {
        String url = HttpApi.service_time;
        HttpGet httpGet = new HttpGet(url);
        HttpClient httpClient = new DefaultHttpClient();
        try {
            HttpResponse httpResponse = httpClient.execute(httpGet);
            LogUtil.d("HttpTransfeData httpgettime", "" + httpResponse.getStatusLine().getStatusCode());
            if (httpResponse.getStatusLine().getStatusCode() == 200){
                HttpEntity httpEntity = httpResponse.getEntity();
                String times = EntityUtils.toString(httpEntity,"utf-8");
            }
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData httpgettime error：", "" + e.toString());
        }catch (Exception e){
            LogUtil.e("HttpTransfeData httpgettime error：", "" + e.toString());
        }
    }

    /**
     * 获取版本信息
     * @param application
     */
    public static Statese httppostvertion(Context application,Statese statese,String vertion) {
        String url = HttpApi.vertion;
        HttpPost httpPost = new HttpPost(url);
        HttpClient httpClient = new DefaultHttpClient();
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("SysID","1"));
        pairs.add(new BasicNameValuePair("Version",vertion));
        try {
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(pairs,"utf-8");
            httpPost.setEntity(urlEncodedFormEntity);
            HttpParams params = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(params, 10000); //设置连接超时
            HttpConnectionParams.setSoTimeout(params, 10000); //设置请求超时
            httpPost.setParams(params);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            LogUtil.d("HttpTransfeData httppostvertion", "" + httpResponse.getStatusLine().getStatusCode());
            if (httpResponse.getStatusLine().getStatusCode() == 200){
                HttpEntity httpEntity = httpResponse.getEntity();
                String bh = EntityUtils.toString(httpEntity,"utf-8");
                Log.i("HttpTransfeData httppostvertion", "" + bh);
                JSONObject jsonObject = new JSONObject(bh);
                statese.setData(jsonObject.getString("data"));
                statese.setState(jsonObject.getString("state"));
                statese.setMsg(jsonObject.getString("msg"));
                return statese;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData httppostvertion error：", "" + e.toString());
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData httppostvertion error：", "" + e.toString());
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData httppostvertion error：", "" + e.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData httppostvertion error：", "" + e.toString());
        }catch (Exception e){
            LogUtil.e("HttpTransfeData httppostvertion error：", "" + e.toString());
        }
        return null;
    }

    /**
     * 商品类别
     * @param activity
     * @return
     */
    public static ArrayList<Category> httpcartegory(Context activity) {
        ArrayList<Category> arrayList = new ArrayList<Category>();
        String url = HttpApi.cartegory;
        HttpGet httpGet = new HttpGet(url);
        HttpClient httpClient = new DefaultHttpClient();
        try {
            HttpParams params = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(params, 10000); //设置连接超时
            HttpConnectionParams.setSoTimeout(params, 10000); //设置请求超时
            httpGet.setParams(params);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            LogUtil.d("HttpTransfeData httpcartegory", "" + httpResponse.getStatusLine().getStatusCode());
            if (httpResponse.getStatusLine().getStatusCode() == 200){
                HttpEntity httpEntity = httpResponse.getEntity();
                String content = EntityUtils.toString(httpEntity,"utf-8");
                CacheUtils.setCache(content,url,30);
                JSONObject jsonObject = new JSONObject(content);
                if (jsonObject.getString("state").equals(String.valueOf(Numbers.ONE))){
                    arrayList = HttpMothed.catrgory(activity,content);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData httpcartegory error：", "" + e.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData httpcartegory error：", "" + e.toString());
        }catch (Exception e){
            LogUtil.e("HttpTransfeData httpcartegory error：", "" + e.toString());
        }
        return arrayList;
    }

    /**
     * 商品数据
     * @param activity
     * @param coding
     * @return
     */
    public static ArrayList<Goods> httpgoodsdata(Context activity, String coding,int index,String cityname,int num) {
        ArrayList<Goods> arrayList = new ArrayList<Goods>();
        String url = HttpApi.goods_ol;
        HttpPost httpPost = new HttpPost(url);
        HttpClient httpClient = new DefaultHttpClient();
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("cid","1"));
        pairs.add(new BasicNameValuePair("CityName",cityname));
        pairs.add(new BasicNameValuePair("cateID",coding));
        pairs.add(new BasicNameValuePair("PageIndex",String.valueOf(index)));
        pairs.add(new BasicNameValuePair("PageSize","10"));
        try {
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(pairs,"utf-8");
            httpPost.setEntity(urlEncodedFormEntity);
            HttpParams params = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(params, 10000); //设置连接超时
            HttpConnectionParams.setSoTimeout(params, 10000); //设置请求超时
            httpPost.setParams(params);
            long time = System.currentTimeMillis();
            HttpResponse httpResponse = httpClient.execute(httpPost);
            LogUtil.d("HttpTransfeData httpgoodsdata", "" + httpResponse.getStatusLine().getStatusCode());
            if (httpResponse.getStatusLine().getStatusCode() == 200){
                HttpEntity entity = httpResponse.getEntity();
                String goodscontent = EntityUtils.toString(entity,"utf-8");
                LogUtil.i("HttpTransfeData httpgoodsdata",(System.currentTimeMillis()-time)+"shui");
//                if (num == Numbers.ONE){
//                    CacheUtils.setCache(goodscontent,url,5);
//                }
                JSONObject jsonObject = new JSONObject(goodscontent);
                arrayList = HttpMothed.goodscateoryjson(activity,jsonObject.getString("data"));
                return arrayList;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            LogUtil.d("HttpTransfeData httpgoodsdata error：", "" + e.toString());
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData httpgoodsdata492", "" + e.toString());
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData httpgoodsdata495", "" + e.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData httpgoodsdata498", "" + e.toString());
        } catch (Exception e){
            LogUtil.e("HttpTransfeData httpgoodsdata499", "" + e.toString());
        }
        return null;
    }

    /**
     * 城市数据
     * @param activity
     */
    public static void citydata(FragmentActivity activity) {
        String url = HttpApi.city_ol;
        HttpGet httpGet = new HttpGet(url);
        HttpClient httpClient = new DefaultHttpClient();
        try {
            HttpParams params = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(params, 10000); //设置连接超时
            HttpConnectionParams.setSoTimeout(params, 10000); //设置请求超时
            httpGet.setParams(params);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            LogUtil.d("HttpTransfeData citydata", "" + httpResponse.getStatusLine().getStatusCode());
            if (httpResponse.getStatusLine().getStatusCode() == 200){
                HttpEntity httpEntity = httpResponse.getEntity();
                String content = EntityUtils.toString(httpEntity,"utf-8");
            }
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData citydata error：", "" + e.toString());
        }catch (Exception e){
            LogUtil.e("HttpTransfeData citydata error：", "" + e.toString());
        }
    }

    /**
     * 惊喜无限揭晓数据获取
     * @param activity
     * @return
     */
    public static ArrayList<PurchaseDateil> spurisedhttpdata(Context activity,int index) {
        ArrayList<PurchaseDateil> arrayList = new ArrayList<PurchaseDateil>();
        String url = HttpApi.spurised_pulish_goods;
        HttpPost httpPost = new HttpPost(url);
        HttpClient httpClient = new DefaultHttpClient();
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("PageIndex",String.valueOf(index)));
        pairs.add(new BasicNameValuePair("PageSize","10"));
        try {
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(pairs,"utf-8");
            httpPost.setEntity(urlEncodedFormEntity);
            HttpParams params = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(params, 10000); //设置连接超时
            HttpConnectionParams.setSoTimeout(params, 10000); //设置请求超时
            httpPost.setParams(params);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            LogUtil.d("HttpTransfeData spurisedhttpdata", "" + httpResponse.getStatusLine().getStatusCode());
            if (httpResponse.getStatusLine().getStatusCode() == 200){
                HttpEntity httpEntity = httpResponse.getEntity();
                String data_ol = EntityUtils.toString(httpEntity,"utf-8");
                Log.i("HttpTransfeData spurisedhttpdata", "" + data_ol);
                JSONObject jsonObject = new JSONObject(data_ol);
                if (jsonObject.getString("state").equals(String.valueOf(Numbers.ONE))){
                    arrayList = HttpMothed.spurisedjson(activity,jsonObject.getString("data"));
                }
                return arrayList;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }catch (Exception e){
            LogUtil.e("HttpTransfeData spurisedhttpdata error：", "" + e.toString());
        }
        return null;
    }

    /**
     * 惊喜无限云购房间列表数据
     * @param activity
     * @return
     */
    public static CloudRoom couldroomdata(Context activity,String num,String toekn, int index) {
        String url = HttpApi.spuris_room;
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader(key_ol,toekn);
        HttpClient httpClient = new DefaultHttpClient();
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("uid",num));
        pairs.add(new BasicNameValuePair("pageindex",String.valueOf(index)));
        pairs.add(new BasicNameValuePair("pagesize","10"));
        try {
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(pairs,"utf-8");
            httpPost.setEntity(urlEncodedFormEntity);
            HttpParams params = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(params, 10000); //设置连接超时
            HttpConnectionParams.setSoTimeout(params, 10000); //设置请求超时
            httpPost.setParams(params);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            LogUtil.d("HttpTransfeData couldroomdata", "" + httpResponse.getStatusLine().getStatusCode());
            if (httpResponse.getStatusLine().getStatusCode() == 200){
                HttpEntity httpEntity = httpResponse.getEntity();
                String content = EntityUtils.toString(httpEntity,"utf-8");
                Log.i("HttpTransfeData couldroomdata", "" + content);
                Gson gson = new Gson();
                CloudRoom cloudRoom = gson.fromJson(content, CloudRoom.class);
                JSONObject jsonObject = new JSONObject(content);
//                if (jsonObject.getString("state").equals(String.valueOf(Numbers.ONE))){
//                    arrayList = HttpMothed.jsonroomdata(activity,jsonObject.getString("data"));
//                }
                if (!StringUtils.isEmpty(jsonObject.getString("token"))){
                    SaveShared.sharedtokensave(activity,jsonObject.getString("token"));
                }
                return cloudRoom;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }catch (Exception e){
            LogUtil.e("HttpTransfeData couldroomdata error：", "" + e.toString());
        }
        return null;
    }

    /**
     * 即将揭晓
     * @param activity
     * @return
     */
    public static ArrayList<Publish> newpublish(Context activity) {
        ArrayList<Publish> arrayList = new ArrayList<Publish>();
        String url = HttpApi.main_newpublish;
        HttpPost httpPost = new HttpPost(url);
        HttpClient httpClient = new DefaultHttpClient();
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("cid","1"));
        try {
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(pairs,"utf-8");
            httpPost.setEntity(urlEncodedFormEntity);
            HttpParams params = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(params, 10000); //设置连接超时
            HttpConnectionParams.setSoTimeout(params, 10000); //设置请求超时
            httpPost.setParams(params);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            LogUtil.d("HttpTransfeData newpublish", "" + httpResponse.getStatusLine().getStatusCode());
            if (httpResponse.getStatusLine().getStatusCode() == 200){
                HttpEntity httpEntity = httpResponse.getEntity();
                String content = EntityUtils.toString(httpEntity,"utf-8");
                Log.i("HttpTransfeData newpublish",""+content);
                JSONObject jsonObject = new JSONObject(content);
                if (jsonObject.getString("state").equals(String.valueOf(Numbers.ONE))){
                    arrayList = HttpMothed.newgoodsjson(activity,jsonObject.getString("data"));
                }
                return arrayList;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }catch (Exception e){
            LogUtil.e("HttpTransfeData newpublish error：", "" + e.toString());
        }
        return null;
    }

    /**
     * 最新最快goods数据
     * @param activity
     * @param s
     * @return
     */
    public static ArrayList<Goods> newfasterhttp(FragmentActivity activity, String s,int index,String size) {
        ArrayList<Goods> arrayList = new ArrayList<Goods>();
        String url = HttpApi.main_new_flase;
        HttpPost httpPost = new HttpPost(url);
        HttpClient httpClient = new DefaultHttpClient();
        List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
        pairs.add(new BasicNameValuePair("CID","1"));
        pairs.add(new BasicNameValuePair("Sort",s));
        pairs.add(new BasicNameValuePair("PageIndex",String.valueOf(index)));
        pairs.add(new BasicNameValuePair("PageSize",size));
        try {
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(pairs,"utf-8");
            httpPost.setEntity(urlEncodedFormEntity);
            HttpParams params = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(params, 10000); //设置连接超时
            HttpConnectionParams.setSoTimeout(params, 10000); //设置请求超时
            httpPost.setParams(params);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            LogUtil.d("HttpTransfeData newfasterhttp", "" + httpResponse.getStatusLine().getStatusCode());
            if (httpResponse.getStatusLine().getStatusCode() == 200){
                HttpEntity httpEntity = httpResponse.getEntity();
                String content = EntityUtils.toString(httpEntity,"utf-8");
                JSONObject jsonObject = new JSONObject(content);
                if (jsonObject.getString("state").equals(String.valueOf(Numbers.ONE))){
                    arrayList = HttpMothed.maingoodsjson(activity,jsonObject.getString("data"));
                }
                return arrayList;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }catch (Exception e){
            LogUtil.e("HttpTransfeData newfasterhttp error：", "" + e.toString());
        }
        return null;
    }

    /**
     *商品详情信息获取
     * @param applicationContext
     * @return
     */
    public static GoodsDateils goodsdateilhttp(Context applicationContext, String pid) {
        GoodsDateils goods = new GoodsDateils();
        String url = HttpApi.goods_dateil+pid;
        HttpGet httpGet = new HttpGet(url);
        HttpClient httpClient = new DefaultHttpClient();
        try {
            HttpParams params = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(params,10000);
            HttpConnectionParams.setSoTimeout(params, 10000);
            httpGet.setParams(params);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            LogUtil.d("HttpTransfeData goodsdateilhttp", "" + httpResponse.getStatusLine().getStatusCode());
            if (httpResponse.getStatusLine().getStatusCode() == 200){
                HttpEntity httpEntity = httpResponse.getEntity();
                String content = EntityUtils.toString(httpEntity,"utf-8");
                JSONObject jsonObject = new JSONObject(content);
                if (jsonObject.getString("state").equals(String.valueOf(Numbers.ONE))){
                    goods = HttpMothed.goodsdateiljson(applicationContext,jsonObject.getString("data"));
                }
                return goods;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }catch (Exception e){
            LogUtil.e("HttpTransfeData goodsdateilhttp error：",e.toString());
        }
        return null;
    }

    /**
     * 图文详情数据请求
     * @param application
     * @param id
     * @return
     */
    public static String imagetexthttp(Context application, String id) {
        String url = null;
        String url1 = HttpApi.image_text+id;
        HttpGet httpGet = new HttpGet(url1);
        HttpClient httpClient = new DefaultHttpClient();
        try {
            HttpParams params = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(params, 10000); //设置连接超时
            HttpConnectionParams.setSoTimeout(params, 10000); //设置请求超时
            httpGet.setParams(params);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            LogUtil.d("HttpTransfeData imagetexthttp", "" + httpResponse.getStatusLine().getStatusCode());
            if (httpResponse.getStatusLine().getStatusCode() == 200){
                HttpEntity httpEntity = httpResponse.getEntity();
                String content = EntityUtils.toString(httpEntity,"utf-8");
                JSONObject jsonObject = new JSONObject(content);
                if (jsonObject.getString("state").equals(String.valueOf(Numbers.ONE))){
                    url = jsonObject.getString("data").replace("/Images",HttpApi.tu_ol+"/Images");
                    LogUtil.e("HttpTransfeData imagetexthttp",""+url);

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }catch (Exception e){
            LogUtil.e("HttpTransfeData imagetexthttp error：",e.toString());
        }
        return url;
    }

    /**
     *往期数据请求
     * @param application
     * @param id
     * @return
     */
    public static ArrayList<Record> wangqihttp(Application application, String id,int index) {
        ArrayList<Record> arrayList = new ArrayList<Record>();
        String url = HttpApi.wanqi;
        HttpPost httpPost = new HttpPost(url);
        HttpClient httpClient = new DefaultHttpClient();
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        Log.i(application+"","this_old_time"+id);
        pairs.add(new BasicNameValuePair("id",id));
        pairs.add(new BasicNameValuePair("PageIndex",String.valueOf(index)));
        pairs.add(new BasicNameValuePair("PageSize","10"));
        try {
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(pairs,"utf-8");
            httpPost.setEntity(urlEncodedFormEntity);
            HttpParams params = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(params, 10000);
            HttpConnectionParams.setSoTimeout(params, 10000);
            httpPost.setParams(params);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            LogUtil.d("HttpTransfeData wangqihttp", "" + httpResponse.getStatusLine().getStatusCode());
            if (httpResponse.getStatusLine().getStatusCode() == 200){
                HttpEntity httpEntity = httpResponse.getEntity();
                String content = EntityUtils.toString(httpEntity,"utf-8");
                JSONObject jsonObject = new JSONObject(content);
                if (jsonObject.getString("state").equals(String.valueOf(Numbers.ONE))){
                    arrayList = HttpMothed.thistimey(arrayList,application,jsonObject.getString("data"));
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }catch (Exception e){
            LogUtil.e("HttpTransfeData wangqihttp error：", "" + e.toString());
        }
        return arrayList;
    }

    /**
     * 云购房间关键字搜索
     * @param application
     * @param con
     * @return
     */
    public static ArrayList<Goods> eachgoods(Application application, String con,int index) {
        ArrayList<Goods> arrayList = new ArrayList<Goods>();
        String url = HttpApi.room_each;
        HttpPost httpPost = new HttpPost(url);
        HttpClient httpClient = new DefaultHttpClient();
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("cid","1"));
        pairs.add(new BasicNameValuePair("Keywords",con));
        pairs.add(new BasicNameValuePair("pageIndex",String.valueOf(index)));
        pairs.add(new BasicNameValuePair("pageSize","10"));
        try {
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(pairs,"utf-8");
            httpPost.setEntity(urlEncodedFormEntity);
            HttpParams params = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(params, 10000); //设置连接超时
            HttpConnectionParams.setSoTimeout(params, 10000); //设置请求超时
            httpPost.setParams(params);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            LogUtil.d("HttpTransfeData eachgoods", "" + httpResponse.getStatusLine().getStatusCode());
            if (httpResponse.getStatusLine().getStatusCode() == 200){
                HttpEntity httpEntity = httpResponse.getEntity();
                String content = EntityUtils.toString(httpEntity,"utf-8");
                JSONObject jsonObject = new JSONObject(content);
                if (jsonObject.getString("state").equals(String.valueOf(Numbers.ONE))){
                    arrayList = HttpMothed.eachroomjson(application,jsonObject.getString("data"));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }catch (Exception e){
            LogUtil.e("HttpTransfeData eachgoods error：", "" + e.toString());
        }
        return arrayList;
    }

    /**
     * 创建云购房间
     * @param runnable
     * @param token
     * @param uid
     * @return
     */
    public static Statese crateroomhttp(Context context, String token, String pid,String uid,String gid) {
        Statese statese = new Statese();
        String url = HttpApi.crate_room;
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader(key_ol,token);
        HttpClient httpClient = new DefaultHttpClient();
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("ID",gid));
        pairs.add(new BasicNameValuePair("UID",uid));
        pairs.add(new BasicNameValuePair("ProductID",pid));
        pairs.add(new BasicNameValuePair("Remark",null));
        pairs.add(new BasicNameValuePair("Name","1"));
        try {
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(pairs,"utf-8");
            httpPost.setEntity(urlEncodedFormEntity);
            HttpParams params = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(params, 10000); //设置连接超时
            HttpConnectionParams.setSoTimeout(params, 10000); //设置请求超时
            httpPost.setParams(params);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            LogUtil.d("HttpTransfeData crateroomhttp", "" + httpResponse.getStatusLine().getStatusCode());
            if (httpResponse.getStatusLine().getStatusCode() == 200){
                HttpEntity httpEntity = httpResponse.getEntity();
                String content = EntityUtils.toString(httpEntity,"utf-8");
                JSONObject jsonObject = new JSONObject(content);
                statese.setState(jsonObject.getString("state"));
                statese.setMsg(jsonObject.getString("msg"));
                Log.i("jsklfjsd","jfsklfjfiwe"+content);
                if (!jsonObject.getString("token").equals("null")){
                    SaveShared.sharedtokensave(context,jsonObject.getString("token"));
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }catch (Exception e){
            LogUtil.e("HttpTransfeData crateroomhttp error：", "" + e.toString());
        }
        return statese;
    }

    /**
     * 更换房间商品数据
     * @param application
     * @param token
     * @param roomid
     * @return
     */
    public static Statese updategoodshttp(Application application, String token, String roomid,String uid,String pid,String gid) {
        Statese statese = new Statese();
        String url = HttpApi.updata_goods;
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader(key_ol,token);
        HttpClient httpClient = new DefaultHttpClient();
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("UID",uid));
        pairs.add(new BasicNameValuePair("ID",roomid));
        pairs.add(new BasicNameValuePair("PID",gid));
        pairs.add(new BasicNameValuePair("ProductID",pid));
        try {
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(pairs,"utf-8");
            httpPost.setEntity(urlEncodedFormEntity);
            HttpParams params = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(params, 10000); //设置连接超时
            HttpConnectionParams.setSoTimeout(params, 10000); //设置请求超时
            httpPost.setParams(params);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            LogUtil.d("HttpTransfeData updategoodshttp", "" + httpResponse.getStatusLine().getStatusCode());
            if (httpResponse.getStatusLine().getStatusCode() == 200){
                HttpEntity httpEntity = httpResponse.getEntity();
                String content = EntityUtils.toString(httpEntity,"utf-8");
                JSONObject  jsonObject = new JSONObject(content);
                if (!StringUtils.isEmpty(jsonObject.getString("token"))){
                    SaveShared.sharedtokensave(application,jsonObject.getString("token"));
                }
                statese.setMsg(jsonObject.getString("msg"));
                statese.setState(jsonObject.getString("state"));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }catch (Exception e){
            LogUtil.e("HttpTransfeData updategoodshttp error：", "" + e.toString());
        }
        return statese;
    }

    /**
     * 删除房间
     * @param application
     * @param tag
     * @param token
     * @return
     */
    public static Statese deleteroomhttp(Context application, String tag,String pid,String gid, String token,String uid) {
        Statese statese = new Statese();
        String url = HttpApi.delete_room+"id="+tag;
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader(key_ol,token);
        HttpClient httpClient = new DefaultHttpClient();
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("UID",uid));
        pairs.add(new BasicNameValuePair("ID",tag));
        pairs.add(new BasicNameValuePair("ProductID",pid));
        pairs.add(new BasicNameValuePair("Remark",null));
        pairs.add(new BasicNameValuePair("Name",null));
        try {
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(pairs,"utf-8");
            httpPost.setEntity(urlEncodedFormEntity);
            HttpParams params = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(params, 10000); //设置连接超时
            HttpConnectionParams.setSoTimeout(params, 10000); //设置请求超时
            httpPost.setParams(params);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            LogUtil.d("HttpTransfeData deleteroomhttp", "" + httpResponse.getStatusLine().getStatusCode());
            if (httpResponse.getStatusLine().getStatusCode() == 200){
                HttpEntity httpEntity = httpResponse.getEntity();
                String content = EntityUtils.toString(httpEntity,"utf-8");
                JSONObject jsonObject = new JSONObject(content);
                statese.setState(jsonObject.getString("state"));
                statese.setMsg(jsonObject.getString("msg"));
                if (!StringUtils.isEmpty(jsonObject.getString("token"))){
                    SaveShared.sharedtokensave(application,jsonObject.getString("token"));
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }catch (Exception e){
            LogUtil.e("HttpTransfeData deleteroomhttp error：",e.toString());
        }
        return statese;
    }

    /**
     * 购物车商品列表数据
     * @param activity
     * @param uid
     * @return
     */
    public static ArrayList<Goods> shoppingcathttp(Context activity, String uid,String token,int index) {
       ArrayList<Goods> arrayList = new ArrayList<Goods>();
        String url = HttpApi.shopping_catlist;
        HttpPost httpPost = new HttpPost(url);
        HttpClient httpClient = new DefaultHttpClient();
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("uid",uid));
        pairs.add(new BasicNameValuePair("PageIndex",String.valueOf(index)));
        pairs.add(new BasicNameValuePair("PageSize","10"));
        try {
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(pairs,"utf-8");
            httpPost.setEntity(urlEncodedFormEntity);
            HttpParams params = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(params, 10000); //设置连接超时
            HttpConnectionParams.setSoTimeout(params, 10000); //设置请求超时
            httpPost.setParams(params);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            LogUtil.d("HttpTransfeData shoppingcathttp", "" + httpResponse.getStatusLine().getStatusCode());
            if (httpResponse.getStatusLine().getStatusCode() == 200){
                HttpEntity httpEntity = httpResponse.getEntity();
                String content = EntityUtils.toString(httpEntity,"utf-8");
                Log.i("HttpTransfeData shoppingcathttp", "" + content);
                JSONObject jsonObject = new JSONObject(content);
                if (jsonObject.getString("state").equals(String.valueOf(Numbers.ONE))){
                    arrayList = HttpMothed.shoppingcatjson(jsonObject.getString("data"));
                }
                return arrayList;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }catch (Exception e){
            LogUtil.e("HttpTransfeData deleteroomhttp error：", e.toString());
        }
        return null;
    }

    /**
     * 购物车添加商品请求
     * @param application
     * @param goodsid
     * @param uid
     * @param token
     * @return
     */
    public static Statese goodsaddshoppingcat(Context application, String goodsid, String uid,String token,String num) {
        Statese statese = new Statese();
        String url = HttpApi.add_shoppingcat;
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader(key_ol, token);
        HttpClient httpClient = new DefaultHttpClient();
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("pid",goodsid));
        pairs.add(new BasicNameValuePair("count",String.valueOf(num)));
        pairs.add(new BasicNameValuePair("uid", uid));
        try {
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(pairs,"utf-8");
            httpPost.setEntity(urlEncodedFormEntity);
            HttpParams params = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(params, 10000); //设置连接超时
            HttpConnectionParams.setSoTimeout(params, 10000); //设置请求超时
            httpPost.setParams(params);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            Log.i("HttpTransfeData goodsaddshoppingcat", "" + httpResponse.getStatusLine().getStatusCode());
            if (httpResponse.getStatusLine().getStatusCode() == 200){
                HttpEntity httpEntity = httpResponse.getEntity();
                String content = EntityUtils.toString(httpEntity,"utf-8");
                JSONObject jsonObject = new JSONObject(content);
                Log.i(application+"","87878"+content);
                if (!StringUtils.isEmpty(jsonObject.getString("token"))){
                    SaveShared.sharedtokensave(application,jsonObject.getString("token"));
                }
                statese.setState(jsonObject.getString("state"));
                statese.setMsg(jsonObject.getString("msg"));
                statese.setData(jsonObject.getString("data"));
                return statese;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }catch (Exception e){
            LogUtil.e("HttpTransfeData goodsaddshoppingcat error：", e.toString());
        }
        return null;
    }

    /**
     * 删除购物车商品
     * @param contextol
     * @param token
     * @param uid
     * @param goodsid
     * @return
     */
    public static Statese shoppingcatdeletehttp(Context contextol, String token, String uid, String goodsid) {
        Statese con = new Statese();
        String url = HttpApi.delete_shoppingcat;
        HttpPost httpPost = new HttpPost(url);
        HttpClient httpClient = new DefaultHttpClient();
        httpPost.setHeader(key_ol,token);
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("uid",uid));
        pairs.add(new BasicNameValuePair("ids",goodsid));
        try {
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(pairs,"utf-8");
            httpPost.setEntity(urlEncodedFormEntity);
            HttpParams params = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(params, 10000); //设置连接超时
            HttpConnectionParams.setSoTimeout(params, 10000); //设置请求超时
            httpPost.setParams(params);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            LogUtil.d("HttpTransfeData shoppingcatdeletehttp", "" + httpResponse.getStatusLine().getStatusCode());
            if (httpResponse.getStatusLine().getStatusCode() == 200){
                HttpEntity httpEntity = httpResponse.getEntity();
                String content = EntityUtils.toString(httpEntity,"utf-8");
                JSONObject jsonObject = new JSONObject(content);
                con.setMsg(jsonObject.getString("msg"));
                con.setState(jsonObject.getString("state"));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }catch (Exception e){
            LogUtil.e("HttpTransfeData shoppingcatdeletehttp error：",e.toString());
        }
        return con;
    }

    /**
     * 我的和他人的晒单列表
     * @param activity
     * @param index
     * @return
     */
    public static ArrayList<Publish> spurisedhttpsharedateil(Context activity, int index,String uid,String token) {
        ArrayList<Publish> arrayList = new ArrayList<Publish>();
        String url = HttpApi.whopu;
        HttpPost httpPost = new HttpPost(url);
        HttpClient httpClient = new DefaultHttpClient();
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("UID",uid));
        pairs.add(new BasicNameValuePair("PageIndex",String.valueOf(index)));
        pairs.add(new BasicNameValuePair("PageSize","10"));
        try {
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(pairs,"utf-8");
            httpPost.setEntity(urlEncodedFormEntity);
            HttpParams params = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(params, 10000); //设置连接超时
            HttpConnectionParams.setSoTimeout(params, 10000); //设置请求超时
            httpPost.setParams(params);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            LogUtil.d("HttpTransfeData spurisedhttpsharedateil", "" + httpResponse.getStatusLine().getStatusCode());
            if (httpResponse.getStatusLine().getStatusCode() == 200){
                HttpEntity httpEntity = httpResponse.getEntity();
                String content = EntityUtils.toString(httpEntity,"utf-8");
                LogUtil.i("HttpTransfeData spurisedhttpsharedateil",content);
                JSONObject jsonObject = new JSONObject(content);
                if (jsonObject.getString("state").equals(String.valueOf(Numbers.ONE))){
                    arrayList = HttpMothed.Pulishdateiljson(jsonObject.getString("data"));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }catch (Exception e){
            LogUtil.e("HttpTransfeData spurisedhttpsharedateil error：", e.toString());
        }
        return arrayList;
    }
    /**
     * 获取惊喜无限晒单列表
     * @param activity
     * @param index
     * @return
     */
    public static ArrayList<Publish> spurisedhttpsharedateil_ol(Context activity, int index,String token) {
        ArrayList<Publish> arrayList = new ArrayList<Publish>();
        String url = HttpApi.allgoods;
        HttpPost httpPost = new HttpPost(url);
        HttpClient httpClient = new DefaultHttpClient();
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("PageIndex",String.valueOf(index)));
        pairs.add(new BasicNameValuePair("PageSize","10"));
        try {
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(pairs,"utf-8");
            httpPost.setEntity(urlEncodedFormEntity);
            HttpParams params = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(params, 10000); //设置连接超时
            HttpConnectionParams.setSoTimeout(params, 10000); //设置请求超时
            httpPost.setParams(params);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            LogUtil.d("HttpTransfeData spurisedhttpsharedateil_ol", "" + httpResponse.getStatusLine().getStatusCode());
            if (httpResponse.getStatusLine().getStatusCode() == 200){
                HttpEntity httpEntity = httpResponse.getEntity();
                String content = EntityUtils.toString(httpEntity,"utf-8");
                JSONObject jsonObject = new JSONObject(content);
                if (jsonObject.getString("state").equals(String.valueOf(Numbers.ONE))){
                    arrayList = HttpMothed.Pulishdateiljson(jsonObject.getString("data"));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }catch (Exception e){
            LogUtil.e("HttpTransfeData spurisedhttpsharedateil_ol error：", e.toString());
        }
        return arrayList;
    }
    /**
     * 点赞接口
     * @param context
     * @param num
     * @return
     */
    public static Statese supporthttp(Context context,String num,String id,String token,Statese statese) {
        String url = HttpApi.zan+id+"&uid="+num;
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader(key_ol, token);
        HttpClient httpClient = new DefaultHttpClient();
        try {
            HttpParams params = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(params, 10000); //设置连接超时
            HttpConnectionParams.setSoTimeout(params, 10000); //设置请求超时
            httpPost.setParams(params);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            LogUtil.d("HttpTransfeData supporthttp", "" + httpResponse.getStatusLine().getStatusCode());
            if (httpResponse.getStatusLine().getStatusCode() == 200){
                HttpEntity httpEntity = httpResponse.getEntity();
                String content = EntityUtils.toString(httpEntity,"utf-8");
                JSONObject jsonObject = new JSONObject(content);
                if (!StringUtils.isEmpty(jsonObject.getString("token"))){
                    SaveShared.sharedtokensave(context,jsonObject.getString("token"));
                }
                statese.setMsg(jsonObject.getString("msg"));
                statese.setState(jsonObject.getString("state"));
                statese.setData(jsonObject.getString("data"));
                return statese;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }catch (Exception e){
            LogUtil.e("HttpTransfeData supporthttp error：", e.toString());
        }
        return null;
    }

    /**
     *评论接口
     * @param activity
     * @param num
     * @return
     */
    public static String commentnumberhttp(Context activity, String num) {
        String con = null;
        String url = HttpApi.reply+num;
        HttpPost httpPost = new HttpPost(url);
        HttpClient httpClient = new DefaultHttpClient();
        try {
            HttpParams params = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(params, 10000); //设置连接超时
            HttpConnectionParams.setSoTimeout(params, 10000); //设置请求超时
            httpPost.setParams(params);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            LogUtil.d("HttpTransfeData commentnumberhttp", "" + httpResponse.getStatusLine().getStatusCode());
            if (httpResponse.getStatusLine().getStatusCode() == 200){
                HttpEntity httpEntity = httpResponse.getEntity();
                String content = EntityUtils.toString(httpEntity,"utf-8");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e){
            LogUtil.e("HttpTransfeData commentnumberhttp error：", e.toString());
        }
        return null;
    }

    /**
     * 商品分类商品搜索
     * @param application
     * @param title
     * @return
     */
    public static ArrayList<Goods> httpshoppingeach(Context application, String title,int index) {
        ArrayList<Goods> arrayList = new ArrayList<Goods>();
        String url = HttpApi.goods_each;
        HttpPost httpPost = new HttpPost(url);
        HttpClient httpClient = new DefaultHttpClient();
//        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
//        Log.i("fjwoehds","9r24e9o"+title);
//        pairs.add(new BasicNameValuePair("cid","1"));
//        pairs.add(new BasicNameValuePair("PageIndex",String.valueOf(index)));
//        pairs.add(new BasicNameValuePair("PageSize","10"));
//        pairs.add(new BasicNameValuePair("Keywords ",title));
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("cid","1");
            jsonObject.put("PageIndex",String.valueOf(index));
            jsonObject.put("PageSize","10");
            jsonObject.put("Keywords",title);
            StringEntity entity = new StringEntity(jsonObject.toString(),"utf-8");//解决中文乱码问题
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            httpPost.setEntity(entity);
//            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(pairs,"utf-8");
//            httpPost.setEntity(urlEncodedFormEntity);
            HttpParams params = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(params, 10000); //设置连接超时
            HttpConnectionParams.setSoTimeout(params, 10000); //设置请求超时
            httpPost.setParams(params);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            LogUtil.d("HttpTransfeData httpshoppingeach", "" + httpResponse.getStatusLine().getStatusCode());
            if (httpResponse.getStatusLine().getStatusCode() == 200){
                HttpEntity httpEntity = httpResponse.getEntity();
                String content = EntityUtils.toString(httpEntity,"utf-8");
                Log.i(application+"","123456"+content);
                JSONObject  jsonObject1 = new JSONObject(content);
                if (jsonObject1.getString("state").equals(String.valueOf(Numbers.ONE))){
                    arrayList = HttpMothed.eachdatajson(application,jsonObject1.getString("data"));
                }
                return arrayList;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }catch (Exception e){
            LogUtil.e("HttpTransfeData httpshoppingeach error：", e.toString());
        }
        return null;
    }

    /**
     * 获取地址列表
     * @param application
     * @return
     */
    public static ArrayList<AddressGoods> httpaddress(Context application,String token) {
        ArrayList<AddressGoods> arrayList = new ArrayList<AddressGoods>();
        String url = HttpApi.addresslist;
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader(key_ol,token);
        HttpClient httpClient = new DefaultHttpClient();
        try {
            HttpParams params = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(params, 10000); //设置连接超时
            HttpConnectionParams.setSoTimeout(params, 10000); //设置请求超时
            httpGet.setParams(params);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            LogUtil.d("HttpTransfeData httpaddress", "" + httpResponse.getStatusLine().getStatusCode());
            if (httpResponse.getStatusLine().getStatusCode() == 200){
                HttpEntity httpEntity = httpResponse.getEntity();
                String content = EntityUtils.toString(httpEntity,"utf-8");
                JSONObject jsonObject = new JSONObject(content);
                if (jsonObject.getString("state").equals(String.valueOf(Numbers.ONE))){
                   arrayList = HttpMothed.addressjson(application,jsonObject.getString("data"));
                }
                if (!StringUtils.isEmpty(jsonObject.getString("token"))){
                    SaveShared.sharedtokensave(application, jsonObject.getString("token"));
                }
                return arrayList;
            }
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData httpaddress error：",e.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }catch (Exception e){
            LogUtil.e("HttpTransfeData httpaddress error：", e.toString());
        }
        return null;
    }

    /**
     * updata
     * @param application
     * @param s
     * @return
     */
    public static Statese updatecontent(Context application, String s) {
        Statese con = new Statese();
        String url = HttpApi.nickname;
        String token = SaveShared.tokenget(application);
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader(key_ol,token);
        HttpClient httpClient = new DefaultHttpClient();
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("newNickName",s));
        try {
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(pairs,"utf-8");
            httpPost.setEntity(urlEncodedFormEntity);
            HttpParams params = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(params, 10000); //设置连接超时
            HttpConnectionParams.setSoTimeout(params, 10000); //设置请求超时
            httpPost.setParams(params);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            LogUtil.d("HttpTransfeData updatecontent", "" + httpResponse.getStatusLine().getStatusCode());
            if (httpResponse.getStatusLine().getStatusCode() == 200){
                HttpEntity httpEntity = httpResponse.getEntity();
                String content = EntityUtils.toString(httpEntity,"utf-8");
                JSONObject jsonObject = new JSONObject(content);
                if (jsonObject.getString("state").equals(String.valueOf(Numbers.ONE))){
                    con.setMsg(jsonObject.getString("msg"));
                    con.setState(jsonObject.getString("state"));
                    SharedPreferences.Editor editor = application.getSharedPreferences("data",application.MODE_PRIVATE).edit();
                    editor.putString("token",jsonObject.getString("token"));
                    editor.putString("nickname",s);
                    editor.commit();
                }
                Log.i(application+"",""+content);
            }
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData updatecontent error：",e.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData updatecontent error：",e.toString());
        }catch (Exception e){
            LogUtil.e("HttpTransfeData updatecontent error：",e.toString());
        }
        return con;
    }

    /**
     * 最新公告数据请求
     * @param runnable
     * @param arrayList
     * @return
     */
    public static ArrayList<Bulltin> bulltinhttp(Context runnable,ArrayList<Bulltin> arrayList,String token,String uid) {
        String url = HttpApi.bulltin;
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader(key_ol,token);
        HttpClient  httpClient = new DefaultHttpClient();
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("PageIndex","1"));
        pairs.add(new BasicNameValuePair("PageSize","2"));
        try {
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(pairs,"utf-8");
            httpPost.setEntity(urlEncodedFormEntity);
            HttpParams params = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(params, 10000); //设置连接超时
            HttpConnectionParams.setSoTimeout(params, 10000); //设置请求超时
            httpPost.setParams(params);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            LogUtil.d("HttpTransfeData bulltinhttp", httpResponse.getStatusLine().getStatusCode() + "");
            if (httpResponse.getStatusLine().getStatusCode() == 200){
                HttpEntity httpEntity = httpResponse.getEntity();
                String content = EntityUtils.toString(httpEntity,"utf-8");
                LogUtil.i("HttpTransfeData bulltinhttp",content);
                JSONObject jsonObject = new JSONObject(content);
                if (jsonObject.getString("state").equals(String.valueOf(Numbers.ONE))){
                    arrayList = HttpMothed.noticehttp(arrayList,runnable,jsonObject.getString("data"));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }catch (Exception e){
            LogUtil.e("HttpTransfeData bulltinhttp error：", e.toString());
        }
        return arrayList;
    }

    /**
     * 地址提交
     * @param s
     * @param s1
     * @param s2
     * @param txt
     * @param isflag
     * @return
     */
    public static Statese addaddresshttp(Context context,String s, String s1, String s2, String txt, boolean isflag,String token,String uid) {
        Statese statese = new Statese();
        String url = HttpApi.addaddress;
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader(key_ol,token);
        HttpClient httpClient = new DefaultHttpClient();
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("Addressee",s));
        pairs.add(new BasicNameValuePair("MPhone1",s1));
        pairs.add(new BasicNameValuePair("MID",uid));
        pairs.add(new BasicNameValuePair("PostCode", "710000"));
        pairs.add(new BasicNameValuePair("Address1",txt+s2));
        pairs.add(new BasicNameValuePair("IsDefault", String.valueOf(isflag)));
        try {
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(pairs,"utf-8");
            httpPost.setEntity(urlEncodedFormEntity);
            HttpParams params = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(params, 10000); //设置连接超时
            HttpConnectionParams.setSoTimeout(params, 10000); //设置请求超时
            httpPost.setParams(params);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            LogUtil.d("HttpTransfeData addaddresshttp", httpResponse.getStatusLine().getStatusCode() + "");
            if (httpResponse.getStatusLine().getStatusCode() == 200){
                HttpEntity httpEntity = httpResponse.getEntity();
                String content = EntityUtils.toString(httpEntity,"utf-8");
                LogUtil.i("HttpTransfeData addaddresshttp",content);
                JSONObject jsonObject = new JSONObject(content);
                if (!StringUtils.isEmpty(jsonObject.getString("token"))){
                    SaveShared.sharedtokensave(context, jsonObject.getString("token"));
                }
                statese.setMsg(jsonObject.getString("msg"));
                statese.setState(jsonObject.getString("state"));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData addaddresshttp error：", e.toString());
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData addaddresshttp error：", e.toString());
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData addaddresshttp error：", e.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData addaddresshttp error：", e.toString());
        }catch (Exception e){
            LogUtil.e("HttpTransfeData addaddresshttp error：", e.toString());
        }
        return statese;
    }

    /**
     * 删除地址
     * @param token
     * @return
     */
    public static Statese deleteaddresshttp(Context context,String token,String id) {
        Statese statese = new Statese();
        String url = HttpApi.deleteaddress;
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader(key_ol,token);
        HttpClient httpClient = new DefaultHttpClient();
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("ID",id));
        try {
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(pairs,"utf-8");
            httpPost.setEntity(urlEncodedFormEntity);
            HttpParams params = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(params, 10000); //设置连接超时
            HttpConnectionParams.setSoTimeout(params, 10000); //设置请求超时
            httpPost.setParams(params);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            LogUtil.d("HttpTransfeData deleteaddresshttp", httpResponse.getStatusLine().getStatusCode() + "");
            if (httpResponse.getStatusLine().getStatusCode() == 200){
                HttpEntity httpEntity = httpResponse.getEntity();
                String content = EntityUtils.toString(httpEntity);
                LogUtil.i("HttpTransfeData deleteaddresshttp",content);
                JSONObject jsonObject = new JSONObject(content);
                if (jsonObject.getString("state").equals(String.valueOf(Numbers.ONE))){

                }
                if (!StringUtils.isEmpty(jsonObject.getString("token"))){
                    SaveShared.sharedtokensave(context,jsonObject.getString("token"));
                }
                statese.setState(jsonObject.getString("state"));
                statese.setMsg(jsonObject.getString("msg"));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData deleteaddresshttp error：", e.toString());
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData deleteaddresshttp error：", e.toString());
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData deleteaddresshttp error：", e.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData deleteaddresshttp error：", e.toString());
        }catch (Exception e){
            LogUtil.e("HttpTransfeData deleteaddresshttp error：", e.toString());
        }
        return statese;
    }
    /**
     * 设置默认地址
     * @param context
     * @param token
     * @return
     */
    public static Statese defluthttp(Context context,String token,String id) {
        Statese statese = new Statese();
        String url = HttpApi.address+id;
        HttpGet httpGet = new HttpGet(url);
        HttpClient httpClient = new DefaultHttpClient();
        httpGet.setHeader(key_ol,token);
        try {
            HttpParams params = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(params, 10000); //设置连接超时
            HttpConnectionParams.setSoTimeout(params, 10000); //设置请求超时
            httpGet.setParams(params);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            LogUtil.d("HttpTransfeData defluthttp", "" + httpResponse.getStatusLine().getStatusCode());
            if (httpResponse.getStatusLine().getStatusCode() == 200){
                HttpEntity httpEntity = httpResponse.getEntity();
                String content = EntityUtils.toString(httpEntity,"utf-8");
                Log.i("HttpTransfeData defluthttp",content);
                JSONObject jsonObject = new JSONObject(content);
                if (jsonObject.getString("state").equals(String.valueOf(Numbers.ONE))){
                }
                if (!jsonObject.getString("token").equals("null")){
                    SaveShared.sharedtokensave(context, jsonObject.getString("token"));
                }
                statese.setState(jsonObject.getString("state"));
                statese.setMsg(jsonObject.getString("msg"));
            }
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData defluthttp error：", e.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData defluthttp error：", e.toString());
        }catch (Exception e){
            LogUtil.e("HttpTransfeData defluthttp error：", e.toString());
        }
        return statese;
    }
    /**
     * 好友邀请消息
     * @param application
     * @param token
     * @return
     */
    public static ArrayList<Friend> newfriendhttp(ArrayList<Friend> arrayList,Context application, String token,int index) {
        String url = HttpApi.news;
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader(key_ol,token);
        HttpClient httpClient = new DefaultHttpClient();
        try {
            HttpParams params = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(params, 10000); //设置连接超时
            HttpConnectionParams.setSoTimeout(params, 10000); //设置请求超时
            httpGet.setParams(params);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            LogUtil.d("HttpTransfeData newfriendhttp", "" + httpResponse.getStatusLine().getStatusCode());
            if (httpResponse.getStatusLine().getStatusCode() == 200){
                HttpEntity httpEntity = httpResponse.getEntity();
                String content = EntityUtils.toString(httpEntity,"utf-8");
                LogUtil.i("HttpTransfeData newfriendhttp",content);
                JSONObject jsonObject = new JSONObject(content);
                if (jsonObject.getString("state").equals(String.valueOf(Numbers.ONE))){
                    arrayList = HttpMothed.friendnewjson(arrayList,application,jsonObject.getString("data"));
                }
                if (!StringUtils.isEmpty(jsonObject.getString("token"))){
                    SaveShared.sharedtokensave(application, jsonObject.getString("token"));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }catch (Exception e){
            LogUtil.e("HttpTransfeData newfriendhttp error：", e.toString());
        }
        return arrayList;
    }
    /**
     * 云购房间好友消息处理
     * @param application
     * @param token
     * @return
     */
    public static ArrayList<Friend> httpnewsask(Application application, String token) {
        ArrayList<Friend> arrayList = new ArrayList<Friend>();
        String url = HttpApi.counld_news;
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader(key_ol,token);
//        HttpPost httpPost = new HttpPost(url);
        HttpClient httpClient = new DefaultHttpClient();
        try {
//            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
//            pairs.add(new BasicNameValuePair("UID",SaveShared.uid(application)));
//            pairs.add(new BasicNameValuePair("PageIndex","1"));
//            pairs.add(new BasicNameValuePair("PageSize","10"));
//            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(pairs,"utf-8");
//            httpPost.setEntity(urlEncodedFormEntity);
            HttpParams params = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(params, 10000); //设置连接超时
            HttpConnectionParams.setSoTimeout(params, 10000); //设置请求超时
            httpGet.setParams(params);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            LogUtil.d("HttpTransfeData httpnewsask", "" + httpResponse.getStatusLine().getStatusCode());
            if (httpResponse.getStatusLine().getStatusCode() == 200){
                HttpEntity httpEntity = httpResponse.getEntity();
                String content = EntityUtils.toString(httpEntity,"utf-8");
                LogUtil.i("HttpTransfeData httpnewsask",content);
                JSONObject jsonObject = new JSONObject(content);
                if (jsonObject.getString("state").equals(String.valueOf(Numbers.ONE))){
                    arrayList = HttpMothed.askjson(application,arrayList,jsonObject.getString("data"));
                }
                if (!StringUtils.isEmpty(jsonObject.getString("token"))){
                    SaveShared.sharedtokensave(application, jsonObject.getString("token"));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData newfriendhttp error：", e.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData newfriendhttp error：", e.toString());
        }catch (Exception e){
            LogUtil.e("HttpTransfeData newfriendhttp error：", e.toString());
        }
        return arrayList;
    }

    /**
     * haoyou消息处理
     * @param token
     * @param application
     * @param id
     * @param yesoron
     * @param url
     * @return
     */
    public static Statese messagehandling(String token, Context application, String id,String name,String yesoron, String url,int c) {
        Statese statese = new Statese();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader(key_ol,token);
        HttpClient httpClient = new DefaultHttpClient();
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        if (c == Numbers.TWO){
            pairs.add(new BasicNameValuePair("MemPID", id));
//            pairs.add(new BasicNameValuePair("MemName",name));
            pairs.add(new BasicNameValuePair("State",yesoron));
        }else {
            pairs.add(new BasicNameValuePair("RoomID",id));
            pairs.add(new BasicNameValuePair("MemID",name));
//            pairs.add(new BasicNameValuePair("name",name));
            pairs.add(new BasicNameValuePair("State",yesoron));
        }
        try {
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(pairs,"utf-8");
            httpPost.setEntity(urlEncodedFormEntity);
            HttpParams params = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(params, 10000); //设置连接超时
            HttpConnectionParams.setSoTimeout(params, 10000); //设置请求超时
            httpPost.setParams(params);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            LogUtil.d("HttpTransfeData messagehandling", "" + httpResponse.getStatusLine().getStatusCode());
            if (httpResponse.getStatusLine().getStatusCode() == 200){
                HttpEntity httpEntity = httpResponse.getEntity();
                String content = EntityUtils.toString(httpEntity,"utf-8");
                LogUtil.i("HttpTransfeData messagehandling",content);
                JSONObject jsonObject = new JSONObject(content);
                statese.setMsg(jsonObject.getString("msg"));
                statese.setState(jsonObject.getString("state"));
                if (!jsonObject.getString("token").equals("null")){
                    SaveShared.sharedtokensave(application, jsonObject.getString("token"));
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData messagehandling error：", e.toString());
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData messagehandling error：", e.toString());
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData messagehandling error：", e.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData messagehandling error：", e.toString());
        }catch (Exception e){
            LogUtil.e("HttpTransfeData messagehandling error：", e.toString());
        }
        return statese;
    }

    /**
     * 消息总数获取
     * @param activity
     * @param token
     * @return
     */
    public static Statese httpmessageallnumber(Context activity,String token) {
        Statese statese = new Statese();
        String url = HttpApi.newsnumberall;
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader(key_ol,token);
        HttpClient httpClient = new DefaultHttpClient();
        try {
            HttpParams params = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(params, 10000); //设置连接超时
            HttpConnectionParams.setSoTimeout(params, 10000); //设置请求超时
            httpGet.setParams(params);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            LogUtil.d("HttpTransfeData httpmessageallnumber", "" + httpResponse.getStatusLine().getStatusCode());
            if (httpResponse.getStatusLine().getStatusCode() == 200){
                HttpEntity httpEntity = httpResponse.getEntity();
                String content = EntityUtils.toString(httpEntity,"utf-8");
                JSONObject jsonObject = new JSONObject(content);
                LogUtil.i("HttpTransfeData httpmessageallnumber",content);
                if (jsonObject.getString("state").equals(String.valueOf(Numbers.ONE))){
                    statese = HttpMothed.messageallnumberjson(activity,jsonObject.getString("data"));
                }
                if (!StringUtils.isEmpty(jsonObject.getString("token"))){
                    SaveShared.sharedtokensave(activity, jsonObject.getString("token"));
                }
                statese.setState(jsonObject.getString("state"));
                statese.setMsg(jsonObject.getString("msg"));
                return statese;
            }
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData httpmessageallnumber error：", e.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData httpmessageallnumber error：", e.toString());
        }catch (Exception e){
            LogUtil.e("HttpTransfeData httpmessageallnumber error：", e.toString());
        }
        return null;
    }

    /**
     *我的积分请求
     * @param activity
     * @param token
     * @return
     */
    public static Integraton integralhttp(Context activity, String token,String uid) {
        Integraton integraton = new Integraton();
        String url = HttpApi.inegarl;
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader(key_ol,token);
        HttpClient httpClient = new DefaultHttpClient();
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("MemID",uid));
        try {
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(pairs,"utf-8");
            httpPost.setEntity(urlEncodedFormEntity);
            HttpParams params = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(params, 10000); //设置连接超时
            HttpConnectionParams.setSoTimeout(params, 10000); //设置请求超时
            httpPost.setParams(params);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            LogUtil.d("HttpTransfeData integralhttp", httpResponse.getStatusLine().getStatusCode()+"");
            if (httpResponse.getStatusLine().getStatusCode() == 200){
                HttpEntity httpEntity = httpResponse.getEntity();
                String content = EntityUtils.toString(httpEntity,"utf-8");
                LogUtil.i("HttpTransfeData integralhttp",content);
                JSONObject jsonObject = new JSONObject(content);
                if (jsonObject.getString("state").equals(String.valueOf(Numbers.ONE))){
                    integraton = HttpMothed.integrtionjsoncontent(integraton,activity,jsonObject.getString("data"));
                }
                if(!StringUtils.isEmpty(jsonObject.getString("token"))){
                    SaveShared.sharedtokensave(activity,jsonObject.getString("token"));
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData integralhttp error：", e.toString());
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData integralhttp error：", e.toString());
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData integralhttp error：", e.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData integralhttp error：", e.toString());
        }catch (Exception e){
            LogUtil.e("HttpTransfeData integralhttp error：", e.toString());
        }
        return integraton;
    }

    /**
     * 积分兑换
     * @param application
     * @param number
     * @param token
     * @return
     */
    public static Statese creditsexchangehttp(Application application, String number,String token) {
        Statese statese = new Statese();
        String url = HttpApi.inefarlexchange+number;
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader(key_ol, token);
        HttpClient httpClient = new DefaultHttpClient();
        try {
            HttpParams params = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(params, 10000); //设置连接超时
            HttpConnectionParams.setSoTimeout(params, 10000); //设置请求超时
            httpPost.setParams(params);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            LogUtil.d("HttpTransfeData creditsexchangehttp", httpResponse.getStatusLine().getStatusCode()+"");
            if (httpResponse.getStatusLine().getStatusCode() == 200){
                HttpEntity httpEntity = httpResponse.getEntity();
                String content = EntityUtils.toString(httpEntity,"utf-8");
                LogUtil.i("HttpTransfeData creditsexchangehttp",content);
                JSONObject jsonObject = new JSONObject(content);
                if (!jsonObject.getString("token").equals("null")){
                    SaveShared.sharedtokensave(application, jsonObject.getString("token"));
                }
                statese.setMsg(jsonObject.getString("msg"));
                statese.setState(jsonObject.getString("state"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }catch (Exception e){
            LogUtil.e("HttpTransfeData creditsexchangehttp error：", e.toString());
        }
        return statese;
    }

    /**
     * 所有云购记录
     * @param activity
     * @param token
     * @return
     */
    public static ArrayList<PurchaseDateil> couldshoppinghttp(Context activity, String token,int index,String state,String uid) {
        ArrayList<PurchaseDateil> arrayList = new ArrayList<PurchaseDateil>();
        String url = HttpApi.could_all;
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader(key_ol,token);
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("UID",uid));
        pairs.add(new BasicNameValuePair("State",state));
        pairs.add(new BasicNameValuePair("PageIndex",String.valueOf(index)));
        pairs.add(new BasicNameValuePair("PageSize","10"));
        HttpClient httpClient = new DefaultHttpClient();
        try {
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(pairs,"utf-8");
            httpPost.setEntity(urlEncodedFormEntity);
            HttpParams params = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(params, 10000); //设置连接超时
            HttpConnectionParams.setSoTimeout(params, 10000); //设置请求超时
            httpPost.setParams(params);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            LogUtil.d("HttpTransfeData couldshoppinghttp", "" + httpResponse.getStatusLine().getStatusCode());
            if (httpResponse.getStatusLine().getStatusCode() == 200){
                HttpEntity httpEntity = httpResponse.getEntity();
                String content = EntityUtils.toString(httpEntity,"utf-8");
                LogUtil.i("HttpTransfeData couldshoppinghttp",content);
                JSONObject jsonObject = new JSONObject(content);
                if (jsonObject.getString("state").equals(String.valueOf(Numbers.ONE))){
                    arrayList = HttpMothed.couldthjson(arrayList, activity, jsonObject.getString("data"));
                }
                if (activity != null){
                    if (!StringUtils.isEmpty(jsonObject.getString("token"))){
                        SaveShared.sharedtokensave(activity,jsonObject.getString("token"));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }catch (Exception e){
            LogUtil.e("HttpTransfeData couldshoppinghttp error：", e.toString());
        }
        return arrayList;
    }

    /**
     * 中奖记录数据
     * @param token
     * @param application
     * @return
     */
    public static ArrayList<Winning> winninghttp(String token,String uid, Context application,int index) {
        ArrayList<Winning> arrayList = new ArrayList<Winning>();
        String url = HttpApi.lottery;
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader(key_ol,token);
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("uid",uid));
        pairs.add(new BasicNameValuePair("PageIndex",String.valueOf(index)));
        pairs.add(new BasicNameValuePair("PageSize","10"));
        HttpClient httpClient = new DefaultHttpClient();
        try {
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(pairs,"utf-8");
            httpPost.setEntity(urlEncodedFormEntity);
            HttpParams params = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(params, 10000); //设置连接超时
            HttpConnectionParams.setSoTimeout(params, 10000); //设置请求超时
            httpPost.setParams(params);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            LogUtil.d("HttpTransfeData winninghttp", "" + httpResponse.getStatusLine().getStatusCode());
            if (httpResponse.getStatusLine().getStatusCode() == 200){
                HttpEntity httpEntity = httpResponse.getEntity();
                String content = EntityUtils.toString(httpEntity);
                Log.i("HttpTransfeData winninghttp",content);
                JSONObject jsonObject = new JSONObject(content);
                if (jsonObject.getString("state").equals(String.valueOf(Numbers.ONE))){
                    arrayList = HttpMothed.winnjson(application, arrayList, jsonObject.getString("data"));
                }
                if (!jsonObject.getString("token").equals("null")){
                    SaveShared.sharedtokensave(application,jsonObject.getString("token"));
                }
                return arrayList;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }catch (Exception e){
            LogUtil.e("HttpTransfeData winninghttp error：", e.toString());
        }
        return null;
    }

    /**
     *回复列表
     * @param application
     * @return
     */
    public static ArrayList<Commentes> single_dateilhttp(Application application,int index,String id) {
        ArrayList<Commentes> arrayList = new ArrayList<Commentes>();
        String url = HttpApi.single_dateil;
        HttpPost httpPost = new HttpPost(url);
        HttpClient httpClient = new DefaultHttpClient();
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("ID",id));
        pairs.add(new BasicNameValuePair("PageIndex",String.valueOf(index)));
        pairs.add(new BasicNameValuePair("PageSize","10"));
        try {
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(pairs,"utf-8");
            httpPost.setEntity(urlEncodedFormEntity);
            HttpParams params = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(params, 10000); //设置连接超时
            HttpConnectionParams.setSoTimeout(params, 10000); //设置请求超时
            httpPost.setParams(params);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            LogUtil.d("HttpTransfeData single_dateilhttp", "" + httpResponse.getStatusLine().getStatusCode());
            if (httpResponse.getStatusLine().getStatusCode() == 200){
                HttpEntity httpEntity = httpResponse.getEntity();
                String content = EntityUtils.toString(httpEntity,"utf-8");
                LogUtil.i("HttpTransfeData single_dateilhttp",content);
                JSONObject jsonObject = new JSONObject(content);
                if (jsonObject.getString("state").equals(String.valueOf(Numbers.ONE))){
                    arrayList = HttpMothed.jsoncontent(arrayList,application,jsonObject.getString("data"));
                }
                return arrayList;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }catch (Exception e){
            LogUtil.e("HttpTransfeData single_dateilhttp error：", e.toString());
        }
        return null;
    }

    /**
     * 好友搜索接口
     * @return
     */
    public static ArrayList<Friend> httpfriend(Context context,String token,String uid,String phone) {
        ArrayList<Friend> arrayList = new ArrayList<Friend>();
        String url = HttpApi.firend+phone;
        HttpGet httpGet = new HttpGet(url);
//        httpGet.setHeader(key_ol,token);
        HttpClient httpClient = new DefaultHttpClient();
        try {
            HttpParams params = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(params, 10000); //设置连接超时
            HttpConnectionParams.setSoTimeout(params, 10000); //设置请求超时
            httpGet.setParams(params);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            Log.i("HttpTransfeData httpfriend", "" + phone);
            if (httpResponse.getStatusLine().getStatusCode() == 200){
                HttpEntity httpEntity = httpResponse.getEntity();
                String content = EntityUtils.toString(httpEntity,"utf-8");
                Log.i("HttpTransfeData httpfriend",content);
                JSONObject jsonObject = new JSONObject(content);
                if (jsonObject.getString("state").equals(String.valueOf(Numbers.ONE))){
                    arrayList = HttpMothed.friengjson(arrayList,context,jsonObject.getString("data"));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }catch (Exception e){
            LogUtil.e("HttpTransfeData httpfriend", e.toString());
        }
        return arrayList;
    }

    /**
     * 好友列表
     * @param application
     * @param token
     * @param uid
     * @return
     */
    public static ArrayList<Friend> httpselectfriend(Application application, String token, String uid,int indrx,int size) {
        ArrayList<Friend> arrayList = new ArrayList<Friend>();
        String url = HttpApi.my_friend;
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader(key_ol,token);
        HttpClient httpClient = new DefaultHttpClient();
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("PageIndex",String.valueOf(indrx)));
        pairs.add(new BasicNameValuePair("PageSize",String.valueOf(size)));
        try {
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(pairs,"utf-8");
            httpPost.setEntity(urlEncodedFormEntity);
            HttpParams params = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(params, 10000); //设置连接超时
            HttpConnectionParams.setSoTimeout(params, 10000); //设置请求超时
            httpPost.setParams(params);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            LogUtil.d("HttpTransfeData httpselectfriend", httpResponse.getStatusLine().getStatusCode() + "");
            if (httpResponse.getStatusLine().getStatusCode() == 200){
                HttpEntity httpEntity = httpResponse.getEntity();
                String content = EntityUtils.toString(httpEntity,"utf-8");
                Log.i("HttpTransfeData httpselectfriend",content);
                JSONObject jsonObject = new JSONObject(content);
                if (jsonObject.getString("state").equals(String.valueOf(Numbers.ONE))){
                    arrayList = HttpMothed.myfriendjson(arrayList,jsonObject.getString("data"));
                }
                if (!StringUtils.isEmpty(jsonObject.getString("token"))){
                    SaveShared.sharedtokensave(application,jsonObject.getString("token"));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData httpselectfriend error：", e.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData httpselectfriend error：", e.toString());
        }catch (Exception e){
            LogUtil.e("HttpTransfeData httpselectfriend error：", e.toString());
        }
        return arrayList;
    }

    /**
     * 领奖
     * @param application
     * @param token
     * @param uid
     * @param id
     * @return
     */
    public static Statese awardhttp(Application application, String token, String uid, String id,String name,String phone,String adress) {
        Statese statese = new Statese();
        String url = HttpApi.award;
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader(key_ol,token);
        HttpClient httpClient = new DefaultHttpClient();
        try {
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("AwardID",id));
            pairs.add(new BasicNameValuePair("AcceptorName",name));
            pairs.add(new BasicNameValuePair("AcceptorMobile", phone));
            pairs.add(new BasicNameValuePair("AcceptorAddress", adress));
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(pairs,"utf-8");
            httpPost.setEntity(urlEncodedFormEntity);
            HttpParams params = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(params, 10000); //设置连接超时
            HttpConnectionParams.setSoTimeout(params, 10000); //设置请求超时
            httpPost.setParams(params);
            HttpResponse  httpResponse = httpClient.execute(httpPost);
            Log.d("HttpTransfeData awardhttp", httpResponse.getStatusLine().getStatusCode()+"");
            if (httpResponse.getStatusLine().getStatusCode() == 200){
                HttpEntity httpEntity = httpResponse.getEntity();
                String content = EntityUtils.toString(httpEntity,"utf-8");
                Log.i("HttpTransfeData awardhttp",content);
                JSONObject jsonObject = new JSONObject(content);
                statese.setMsg(jsonObject.getString("msg"));
                statese.setState(jsonObject.getString("state"));
                statese.setData(jsonObject.getString("data"));
                if (!StringUtils.isEmpty(jsonObject.getString("token"))){
                    SharePreferencesUlits.saveString(application,Constants.TOKEN,jsonObject.getString("token"));
                }
                return statese;
            }
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData awardhttp error：", e.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData awardhttp error：", e.toString());
        }catch (Exception e){
            LogUtil.e("HttpTransfeData awardhttp error：", e.toString());
        }
        return null;
    }

    /**
     * 创建订单
     * @param activity
     * @param uid
     * @param token
     * @param money
     * @return
     */
    public static Statese indenthttp(Context activity,String address,String uid,String token,String money,String sum,String ip) {
        Statese statese = new Statese();
        String url = HttpApi.indent;
        HttpPost httpPost = new HttpPost(url);
//        httpPost.setHeader(key_ol,token);
        HttpClient httpClient = new DefaultHttpClient();
        try {
            JSONArray jsonArray = new JSONArray(sum);
            JSONObject jsonObject1=new JSONObject();
            jsonObject1.put("MemID",uid);
            jsonObject1.put("TradeIPAdress",ip);
            if (!TextUtils.isEmpty(address)){
                jsonObject1.put("TradeAddress",address);
            }else {
                jsonObject1.put("TradeAddress","");
            }
            jsonObject1.put("Amount",money);
            jsonObject1.put("PeriodsList", jsonArray);
            StringEntity entity = new StringEntity(jsonObject1.toString(),"utf-8");//解决中文乱码问题
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            LogUtil.i("HttpTransfeData indenthttp",jsonObject1.toString());
            httpPost.setEntity(entity);
            HttpParams params = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(params, 10000); //设置连接超时
            HttpConnectionParams.setSoTimeout(params, 10000); //设置请求超时
            httpPost.setParams(params);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            LogUtil.d("HttpTransfeData indenthttp", httpResponse.getStatusLine().getStatusCode() + "");
            if (httpResponse.getStatusLine().getStatusCode() == 200){
                HttpEntity httpEntity = httpResponse.getEntity();
                String content = EntityUtils.toString(httpEntity,"utf-8");
                Log.i("HttpTransfeData indenthttp",content);
                JSONObject jsonObject = new JSONObject(content);
                statese.setIntergl(jsonObject.getString("data"));
                statese.setState(jsonObject.getString("state"));
                statese.setMsg(jsonObject.getString("msg"));
            }
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData indenthttp error：", e.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData indenthttp error：", e.toString());
        }catch (Exception e){
            LogUtil.e("HttpTransfeData indenthttp error：", e.toString());
        }
        return statese;
    }

    /**
     * 签到
     * @param application
     * @return
     */
    public static Statese singininhttp(Context application,String isgin,String token) {
        Statese statese = new Statese();
        String url = HttpApi.qiandao;
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader(key_ol,token);
        HttpClient httpClient = new DefaultHttpClient();
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("MemID",SaveShared.uid(application)));
        pairs.add(new BasicNameValuePair("MemName",SaveShared.name_ol(application)));
        pairs.add(new BasicNameValuePair("isSign",isgin));
        try {
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(pairs,"utf-8");
            httpPost.setEntity(urlEncodedFormEntity);
            HttpParams params = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(params, 10000); //设置连接超时
            HttpConnectionParams.setSoTimeout(params, 10000); //设置请求超时
            httpPost.setParams(params);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            LogUtil.d("HttpTransfeData singininhttp", httpResponse.getStatusLine().getStatusCode() + "");
            if (httpResponse.getStatusLine().getStatusCode() == 200){
                HttpEntity httpEntity = httpResponse.getEntity();
                String content = EntityUtils.toString(httpEntity,"utf-8");
                Log.i("HttpTransfeData singininhttp",content);
                JSONObject jsonObject = new JSONObject(content);
                if (!jsonObject.getString("token").equals("null")){
                    SaveShared.sharedtokensave(application,jsonObject.getString("token"));
                }
                statese.setMsg(jsonObject.getString("msg"));
                statese.setState(jsonObject.getString("state"));
                statese.setData(jsonObject.getString("data"));
                statese.setMsgcode(jsonObject.getString("msgCode"));
                return statese;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData singininhttp error：", e.toString());
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData singininhttp error：", e.toString());
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData singininhttp error：", e.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData singininhttp error：", e.toString());
        }catch (Exception e){
            LogUtil.e("HttpTransfeData singininhttp error：", e.toString());
        }
        return null;
    }

    /**
     * 添加好友
     * @param application
     * @param uid
     * @param token
     * @return
     */
    public static Statese addfriendhttp(String id,String name,String name_ol,Context application, String uid, String token) {
        Statese statese = new Statese();
        String url = HttpApi.add_friend;
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader(key_ol, token);
        HttpClient httpClient = new DefaultHttpClient();
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("MemID",id));
        pairs.add(new BasicNameValuePair("MemName",name));
        pairs.add(new BasicNameValuePair("MemPID",uid));
        pairs.add(new BasicNameValuePair("MemPName",name_ol));
        try {
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(pairs,"utf-8");
            httpPost.setEntity(urlEncodedFormEntity);
            HttpParams params = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(params, 10000); //设置连接超时
            HttpConnectionParams.setSoTimeout(params, 10000); //设置请求超时
            httpPost.setParams(params);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            LogUtil.d("HttpTransfeData addfriendhttp", httpResponse.getStatusLine().getStatusCode()+"");
            if (httpResponse.getStatusLine().getStatusCode() == 200){
                HttpEntity httpEntity = httpResponse.getEntity();
                String content = EntityUtils.toString(httpEntity,"utf-8");
                LogUtil.i("HttpTransfeData addfriendhttp",content);
                JSONObject jsonObject = new JSONObject(content);
                statese.setState(jsonObject.getString("state"));
                statese.setMsg(jsonObject.getString("msg"));
                if (!StringUtils.isEmpty(jsonObject.getString("token"))){
                    SaveShared.sharedtokensave(application,jsonObject.getString("token"));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData addfriendhttp error：", e.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData addfriendhttp error：", e.toString());
        }catch (Exception e){
            LogUtil.e("HttpTransfeData addfriendhttp error：", e.toString());
        }
        return statese;
    }

    /**
     * 余额查询
     * @param activity
     * @param token
     * @return
     */
    public static Statese sumhttp(Context activity, String token) {
        String content_ol = null;
        String url = HttpApi.sum;
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader(key_ol, token);
        HttpClient httpClient = new DefaultHttpClient();
        Statese statese = new Statese();
        try {
            HttpParams params = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(params, 10000); //设置连接超时
            HttpConnectionParams.setSoTimeout(params, 10000); //设置请求超时
            httpGet.setParams(params);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            LogUtil.d("HttpTransfeData sumhttp", httpResponse.getStatusLine().getStatusCode()+"");
            if (httpResponse.getStatusLine().getStatusCode() == 200){
                HttpEntity httpEntity = httpResponse.getEntity();
                String content = EntityUtils.toString(httpEntity,"utf-8");
                LogUtil.i("HttpTransfeData sumhttp",content);
                JSONObject jsonObject = new JSONObject(content);
                if (!jsonObject.getString("data").equals("null")){
                    statese.setData(String.valueOf(jsonObject.getInt("data")));
                }else {
                    statese.setData("0");
                }
                statese.setState(jsonObject.getString("state"));
                statese.setMsg(jsonObject.getString("msg"));
                if (!StringUtils.isEmpty(jsonObject.getString("token"))){
                    SaveShared.sharedtokensave(activity, jsonObject.getString("token"));
                }
                return statese;
            }
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData sumhttp error：", e.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData sumhttp error：", e.toString());
        }catch (Exception e){
            LogUtil.e("HttpTransfeData sumhttp error：", e.toString());
        }
        return null;
    }

    /**
     * 揭晓详情
     * @param application
     * @param id
     * @param token
     * @param uid
     * @return
     */
    public static PurchaseDateil publishjsonhttp(int c,Application application, String id, String token, String uid) {
        PurchaseDateil purchaseDateil = new PurchaseDateil();
        String url = null;
        url = HttpApi.jiexiao + id;
        HttpGet httpGet = new HttpGet(url);
        HttpClient httpClient = new DefaultHttpClient();
        try {
            HttpParams params = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(params, 8000); //设置连接超时
            HttpConnectionParams.setSoTimeout(params, 10000); //设置请求超时
            httpGet.setParams(params);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            LogUtil.d("HttpTransfeData publishjsonhttp", httpResponse.getStatusLine().getStatusCode() + "");
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                HttpEntity httpEntity = httpResponse.getEntity();
                String content = EntityUtils.toString(httpEntity, "utf-8");
                LogUtil.i("HttpTransfeData publishjsonhttp",content);
                JSONObject jsonObject = new JSONObject(content);
                if (jsonObject.getString("state").equals(String.valueOf(Numbers.ONE))){
                    purchaseDateil = HttpMothed.Contentjson(purchaseDateil,jsonObject.getString("data"));
                }
                return purchaseDateil;
            }
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData publishjsonhttp error：", e.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData publishjsonhttp error：", e.toString());
        }catch (Exception e){
            LogUtil.e("HttpTransfeData publishjsonhttp error：", e.toString());
        }
        return null;
    }

    /**
     * 支付成功回调接口
     * @param application
     * @param data_ol
     * @param token
     * @return
     */
    public static Statese Webhookhttp(Context application,String money,String data_ol,String con,String token) {
        Statese statese = new Statese();
        String url = HttpApi.webhook;
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader(key_ol,token);
        HttpClient httpClient = new DefaultHttpClient();
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("OrderNo",data_ol);
            jsonObject1.put("Channel",con);
            jsonObject1.put("Amount", money);
            jsonObject1.put("ProductName", "艾购");
            jsonObject1.put("Desciption", "支付");
            StringEntity entity = new StringEntity(jsonObject1.toString(),"utf-8");//解决中文乱码问题
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            httpPost.setEntity(entity);
            LogUtil.i("HttpTransfeData Webhookhttp",jsonObject1.toString());
            HttpParams params = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(params, 10000); //设置连接超时
            HttpConnectionParams.setSoTimeout(params, 10000); //设置请求超时
            httpPost.setParams(params);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            LogUtil.d("HttpTransfeData Webhookhttp", "" + httpResponse.getStatusLine().getStatusCode());
            if (httpResponse.getStatusLine().getStatusCode() == 200){
                HttpEntity httpEntity = httpResponse.getEntity();
                String content = EntityUtils.toString(httpEntity,"utf-8");
                LogUtil.i("HttpTransfeData Webhookhttp",content);
                JSONObject jsonObject = new JSONObject(content);
                statese.setMsg(jsonObject.getString("msg"));
                statese.setState(jsonObject.getString("state"));
                statese.setData(jsonObject.getString("data"));
                return statese;
            }
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData Webhookhttp error：", e.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData Webhookhttp error：", e.toString());
        }catch (Exception e){
            LogUtil.e("HttpTransfeData Webhookhttp error：", e.toString());
        }
        return null;
    }

    /**
     * 云购房间好友邀请
     * @param application
     * @param token
     * @param uid
     * @return
     */
    public static Statese pleasefriend(Context application, String token, String uid,String conten) {
        Statese statese = new Statese();
        String url = HttpApi.please;
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader(key_ol,token);
        HttpClient httpClient = new DefaultHttpClient();
        try {
            JSONArray jsonArray = new JSONArray(conten);
            Log.i(application + "", "zhpot" + jsonArray.toString());
            StringEntity entity = new StringEntity(jsonArray.toString(),"utf-8");//解决中文乱码问题
            entity.setContentEncoding("utf-8");
            entity.setContentType("application/json");
            httpPost.setEntity(entity);
            HttpParams params = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(params, 10000); //设置连接超时
            HttpConnectionParams.setSoTimeout(params, 10000); //设置请求超时
            httpPost.setParams(params);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == 200){
                HttpEntity httpEntity = httpResponse.getEntity();
                String content = EntityUtils.toString(httpEntity,"utf-8");
                LogUtil.i("HttpTransfeData pleasefriend",content);
                JSONObject jsonObject = new JSONObject(content);
                statese.setMsg(jsonObject.getString("msg"));
                statese.setState(jsonObject.getString("state"));
                statese.setData(jsonObject.getString("data"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }catch (Exception e){
            LogUtil.e("HttpTransfeData pleasefriend error：", e.toString());
        }
        return statese;
    }

    /**
     * 获取chanagl
     * @param application
     * @param number_ol
     * @param money
     * @param name
     * @param shuo
     * @return
     */
    public static Statese paymenhttp(Context application,String con, String number_ol, String money, String name, String shuo,String token,String num) {
        Statese statese = new Statese();
        String url = HttpApi.changal;
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader(key_ol,token);
        HttpClient httpClient = new DefaultHttpClient();
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("OrderNo",number_ol));
        pairs.add(new BasicNameValuePair("Channel",con));
        pairs.add(new BasicNameValuePair("Amount",money));
        pairs.add(new BasicNameValuePair("ProductName",name));
        pairs.add(new BasicNameValuePair("Desciption",shuo));
        pairs.add(new BasicNameValuePair("PayType", num));
        LogUtil.i("HttpTransfeData paymenhttp info：", con);
        try {
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(pairs,"utf-8");
            httpPost.setEntity(urlEncodedFormEntity);
            HttpParams params = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(params, 10000); //设置连接超时
            HttpConnectionParams.setSoTimeout(params, 10000); //设置请求超时
            httpPost.setParams(params);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            LogUtil.d("HttpTransfeData paymenhttp", "" + httpResponse.getStatusLine().getStatusCode());
            if (httpResponse.getStatusLine().getStatusCode() == 200){
                HttpEntity httpEntity = httpResponse.getEntity();
                String content = EntityUtils.toString(httpEntity,"utf-8");
                LogUtil.i("HttpTransfeData paymenhttp",content);
                JSONObject jsonObject = new JSONObject(content);
                statese.setState(jsonObject.getString("state"));
                statese.setData(jsonObject.getString("data"));
                statese.setMsg(jsonObject.getString("msg"));
                if (!jsonObject.getString("token").equals("null")){
                    SaveShared.sharedtokensave(application,jsonObject.getString("token"));
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData paymenhttp error：", e.toString());
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData paymenhttp error：", e.toString());
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData paymenhttp error：", e.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData paymenhttp error：", e.toString());
        }catch (Exception e){
            LogUtil.e("HttpTransfeData paymenhttp error：", e.toString());
        }
        return statese;
    }

    /**
     * 评论
     * @param application
     * @param id
     * @param token
     * @param uid
     * @return
     */
    public static Statese publishthisgohttp(Application application, String id, String token, String uid,String content) {
        Statese statese = new Statese();
        String url = HttpApi.publish;
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader(key_ol,token);
        HttpClient httpClient = new DefaultHttpClient();
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("UID",uid));
        pairs.add(new BasicNameValuePair("ReviewID",id));
        pairs.add(new BasicNameValuePair("Content",content));
        try {
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(pairs,"utf-8");
            httpPost.setEntity(urlEncodedFormEntity);
            HttpParams params = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(params, 10000); //设置连接超时
            HttpConnectionParams.setSoTimeout(params, 10000); //设置请求超时
            httpPost.setParams(params);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            Log.i("HttpTransfeData publishthisgohttp",""+httpResponse.getStatusLine().getStatusCode());
            if (httpResponse.getStatusLine().getStatusCode() == 200){
                HttpEntity httpEntity = httpResponse.getEntity();
                String content_ol = EntityUtils.toString(httpEntity,"utf-8");
                LogUtil.i("HttpTransfeData publishthisgohttp",content);
                JSONObject jsonObject = new JSONObject(content_ol);
                statese.setState(jsonObject.getString("state"));
                statese.setMsg(jsonObject.getString("msg"));
                statese.setData(jsonObject.getString("data"));
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData publishthisgohttp error：", e.toString());
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData publishthisgohttp error：", e.toString());
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData publishthisgohttp error：", e.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData publishthisgohttp error：", e.toString());
        }catch (Exception e){
            LogUtil.e("HttpTransfeData publishthisgohttp error：", e.toString());
        }
        return statese;
    }

    /**
     * 中奖者接口
     * @param application
     * @param id
     * @return
     */
    public static PurchaseDateil thewinninghttp(Application application, String id) {
        PurchaseDateil purchaseDateil = new PurchaseDateil();
        String url = HttpApi.winning+id;
        Log.i(application+"","this_ol_content"+id);
        HttpGet httpGet = new HttpGet(url);
        HttpClient httpClient = new DefaultHttpClient();
        try {
            HttpParams params = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(params, 10000); //设置连接超时
            HttpConnectionParams.setSoTimeout(params, 10000); //设置请求超时
            httpGet.setParams(params);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            if (httpResponse.getStatusLine().getStatusCode() == 200){
                HttpEntity httpEntity = httpResponse.getEntity();
                String content = EntityUtils.toString(httpEntity,"utf-8");
                Log.i("HttpTransfeData thewinninghttp",content);
                JSONObject jsonObject = new JSONObject(content);
                if (jsonObject.getString("state").equals(String.valueOf(Numbers.ONE))){
                    purchaseDateil = HttpMothed.winningjson(application,purchaseDateil,jsonObject.getString("data"));
                }
                return purchaseDateil;
            }
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData thewinninghttp error：", e.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData thewinninghttp error：", e.toString());
        }catch (Exception e){
            LogUtil.e("HttpTransfeData thewinninghttp error：", e.toString());
        }
        return null;
    }

    /**
     * 晒单
     * @param application
     * @param token
     * @param uid
     * @param s
     * @return
     */
    public static Statese shaidanhttp(String id,String gid,Application application, String token, String uid, String s,String title) {
        Statese statese = new Statese();
        String url = HttpApi.publish_ol;
        MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE,"----------ThIs_Is_tHe_bouNdaRY_$", Charset.defaultCharset());
        HttpPost request = new HttpPost(url);
//        request.setHeader(key_ol,token);
        HttpClient httpClient = new DefaultHttpClient();
        try {
            for (int c = 0 ; c < Bimp.tempSelectBitmap.size() ; c++){
                String path = Tailoring.ziphoneluntan(Bimp.tempSelectBitmap.get(c).getImagePath());
                File file = new File(path);
                multipartEntity.addPart("",new FileBody(file,file.getName()));
            }
            multipartEntity.addPart("PeriodID", new StringBody(id, Charset.forName("UTF-8")));
            multipartEntity.addPart("ProductID",new StringBody(gid,Charset.forName("UTF-8")));
            multipartEntity.addPart("UID",new StringBody(uid, Charset.forName("UTF-8")));
            multipartEntity.addPart("Content",new StringBody(s,Charset.forName("UTF-8")));
            multipartEntity.addPart("Title",new StringBody(title,Charset.forName("UTF-8")));
            request.setEntity(multipartEntity);
            Log.i("发送消息收到的返回：" ,gid+"+"+ id);
            request.addHeader("Content-Type","multipart/form-data; boundary=----------ThIs_Is_tHe_bouNdaRY_$");
            HttpParams params = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(params, 10000); //设置连接超时
            HttpConnectionParams.setSoTimeout(params, 10000); //设置请求超时
            request.setParams(params);
            HttpResponse response = httpClient.execute(request);
//            InputStream is = response.getEntity().getContent();
//            BufferedReader in = new BufferedReader(new InputStreamReader(is));
//            StringBuffer buffer = new StringBuffer();
//            String line = "";
            LogUtil.i("发送消息收到的返回：" ,""+response.getStatusLine().getStatusCode());
            if (response.getStatusLine().getStatusCode() == 200){
//                while ((line = in.readLine()) != null) {
//                    buffer.append(line);
//                }
                HttpEntity httpEntity = response.getEntity();
                String content = EntityUtils.toString(httpEntity,"utf-8");
                LogUtil.i("HttpTransfeData shaidanhttp",content);
                JSONObject jsonObject = new JSONObject(content);
                statese.setState(jsonObject.getString("state"));
                statese.setMsg(jsonObject.getString("msg"));
                statese.setData(jsonObject.getString("data"));
            }
//            Log.i("发送消息收到的返回：" ,""+ buffer.toString());
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData shaidanhttp error：", e.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData shaidanhttp error：", e.toString());
        }catch (Exception e){
            LogUtil.e("HttpTransfeData shaidanhttp error：", e.toString());
        }
        return statese;
    }

    /**
     * 计算详情
     * @param application
     * @param id
     * @return
     */
    public static Statese sumcounthttp(Application application, String id,int index) {
        Statese statese = new Statese();
        String url = HttpApi.count+id;
        HttpPost httpPost = new HttpPost(url);
        HttpClient httpClient = new DefaultHttpClient();
        try {
//            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
            HttpParams params = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(params, 10000); //设置连接超时
            HttpConnectionParams.setSoTimeout(params, 10000); //设置请求超时
            httpPost.setParams(params);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == 200){
                HttpEntity httpEntity = httpResponse.getEntity();
                String content = EntityUtils.toString(httpEntity,"utf-8");
                Log.i("HttpTransfeData sumcounthttp",content);
                JSONObject jsonObject = new JSONObject(content);
                statese.setMsg(jsonObject.getString("msg"));
                statese.setState(jsonObject.getString("state"));
                if (jsonObject.getString("state").equals(String.valueOf(Numbers.ONE))){
                    JSONObject jsonObject1 = new JSONObject(jsonObject.getString("data"));
                    statese.setChargal(jsonObject1.getString("Total"));
                    statese.setIntergl(jsonObject1.getString("TotalCount"));
                    statese.setData(jsonObject1.getString("LuckyNumber"));
                    JSONArray jsonArray = new JSONArray(jsonObject1.getString("JoinRecord"));
                    ArrayList<Count> arrayList1 = new ArrayList<Count>();
                    for (int i = 0;i<jsonArray.length();i++){
                        Count count = new Count();
                        JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                        count.setCountnumber(jsonObject2.getString("Number"));
                        count.setCountname(jsonObject2.getString("UNick"));
                        count.setCountid(jsonObject2.getString("MemID"));
                        count.setCounttime(jsonObject2.getString("TradeDate"));
                        arrayList1.add(count);
                    }
                    statese.setArrayList(arrayList1);
                    return statese;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData sumcounthttp error：",e.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData sumcounthttp error：", e.toString());
        }catch (Exception e){
            LogUtil.e("HttpTransfeData sumcounthttp error：", e.toString());
        }
        return null;
    }

    /**
     * 上传个人图像
     * @param application
     * @param uid
     * @param bitmap
     * @return
     */
    public static Statese thisphotohttp(Context application, String uid, String token,Bitmap bitmap,int c) {
        Statese statese = new Statese();
        String url = HttpApi.thistu;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();// outputstream
        if (c == Numbers.TWO){
            Bitmap bitmap1 = Tailoring.ziphone(bitmap);
            bitmap1.compress(Bitmap.CompressFormat.PNG, 100, baos);
        }else {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        }
        byte[] appicon = baos.toByteArray();// 转为byte数组
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader(key_ol, token);
        HttpClient httpClient = new DefaultHttpClient();
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("MemID",uid));
        pairs.add(new BasicNameValuePair("PhotoName",String.valueOf(System.currentTimeMillis())));
        pairs.add(new BasicNameValuePair("Photo", Base64.encodeToString(appicon, Base64.DEFAULT)));
        pairs.add(new BasicNameValuePair("PhotoFormat","JPEG"));
        try {
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(pairs);
            httpPost.setEntity(urlEncodedFormEntity);
            HttpParams params = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(params, 10000); //设置连接超时
            HttpConnectionParams.setSoTimeout(params, 10000); //设置请求超时
            httpPost.setParams(params);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == 200){
                HttpEntity httpEntity = httpResponse.getEntity();
                String content = EntityUtils.toString(httpEntity,"utf-8");
                Log.i("HttpTransfeData thisphotohttp",content);
                JSONObject jsonObject = new JSONObject(content);
                if (!jsonObject.getString("token").equals("null")){
                    SaveShared.sharedtokensave(application, jsonObject.getString("token"));
                }
                statese.setState(jsonObject.getString("state"));
                statese.setMsg(jsonObject.getString("msg"));
                return statese;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData thisphotohttp error：", e.toString());
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData thisphotohttp error：", e.toString());
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData thisphotohttp error：", e.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData thisphotohttp error：", e.toString());
        }catch (Exception e){
            LogUtil.e("HttpTransfeData thisphotohttp error：", e.toString());
        }
        return null;
    }

    /**
     * 参与记录
     * @param application
     * @param id
     * @return
     */
    public static ArrayList<Record> httpjiexiao(Context application, String id,int index) {
        ArrayList<Record> arrayList = new ArrayList<Record>();
        String url = HttpApi.groupbuy;
        HttpPost httpPost = new HttpPost(url);
        HttpClient httpClient = new DefaultHttpClient();
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("ID",id));
        pairs.add(new BasicNameValuePair("PageIndex",String.valueOf(index)));
        pairs.add(new BasicNameValuePair("PageSize","10"));
        try {
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(pairs);
            httpPost.setEntity(urlEncodedFormEntity);
            HttpParams params = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(params, 10000); //设置连接超时
            HttpConnectionParams.setSoTimeout(params, 10000); //设置请求超时
            httpPost.setParams(params);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == 200){
                HttpEntity httpEntity = httpResponse.getEntity();
                String content = EntityUtils.toString(httpEntity,"utf-8");
                LogUtil.i("HttpTransfeData httpjiexiao",content);
                JSONObject jsonObject = new JSONObject(content);
                arrayList = HttpMothed.groupbuyjson(application,arrayList,jsonObject.getString("data"));
            }
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData httpjiexiao error：", e.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData httpjiexiao error：", e.toString());
        }catch (Exception e){
            LogUtil.e("HttpTransfeData httpjiexiao error：", e.toString());
        }
        return arrayList;
    }

    /**
     * 他人相关接口
     * @param activity
     * @param token
     * @param uid
     * @return
     */
    public static ArrayList<PurchaseDateil> httpwho(FragmentActivity activity, String token, String uid,int index) {
        ArrayList<PurchaseDateil> arrayList = new ArrayList<PurchaseDateil>();
        String url = HttpApi.who;
        HttpPost httpPost = new HttpPost(url);
        HttpClient httpClient = new DefaultHttpClient();
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("uid", uid));
        pairs.add(new BasicNameValuePair("PageIndex", String.valueOf(index)));
        pairs.add(new BasicNameValuePair("PageSize", "10"));
        try {
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(pairs, "utf-8");
            httpPost.setEntity(urlEncodedFormEntity);
            HttpParams params = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(params, 10000); //设置连接超时
            HttpConnectionParams.setSoTimeout(params, 10000); //设置请求超时
            httpPost.setParams(params);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                HttpEntity httpEntity = httpResponse.getEntity();
                String content = EntityUtils.toString(httpEntity, "utf-8");
                JSONObject jsonObject = new JSONObject(content);
                Log.i("HttpTransfeData httpwho",content);
                arrayList = HttpMothed.whojson(activity, arrayList, jsonObject.getString("data"));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData httpwho error：", e.toString());
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData httpwho error：", e.toString());
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData httpwho error：", e.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData httpwho error：", e.toString());
        }catch (Exception e){
            LogUtil.e("HttpTransfeData httpwho error：", e.toString());
        }
        return arrayList;
    }
    /**
     * 意见反馈列表
     * @param application
     * @param index
     * @param uid
     * @return
     */
    public static ArrayList<Feedback> feedbackhttp(Application application, int index, String uid,String token) {
        ArrayList<Feedback> arrayList = new ArrayList<Feedback>();
        String url = HttpApi.feedback;
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader(key_ol,token);
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("MemID",uid));
        pairs.add(new BasicNameValuePair("PageIndex",String.valueOf(index)));
        pairs.add(new BasicNameValuePair("PageSize","10"));
        try {
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(pairs,"utf-8");
            httpPost.setEntity(urlEncodedFormEntity);
            HttpClient httpClient = new DefaultHttpClient();
            HttpParams params = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(params, 10000); //设置连接超时
            HttpConnectionParams.setSoTimeout(params, 10000); //设置请求超时
            httpPost.setParams(params);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() ==200){
                HttpEntity httpEntity = httpResponse.getEntity();
                String content = EntityUtils.toString(httpEntity,"utf-8");
                LogUtil.i("HttpTransfeData feedbackhttp",content);
                JSONObject jsonObject = new JSONObject(content);
                if (jsonObject.getString("state").equals(String.valueOf(Numbers.ONE))){
                    arrayList = HttpMothed.feedbackjson(application,arrayList,jsonObject.getString("data"));
                }
                if (!StringUtils.isEmpty(jsonObject.getString("token"))){
                    SaveShared.sharedtokensave(application,jsonObject.getString("token"));
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData feedbackhttp error：", e.toString());
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData feedbackhttp error：", e.toString());
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData feedbackhttp error：", e.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData feedbackhttp error：", e.toString());
        }catch (Exception e){
            LogUtil.e("HttpTransfeData feedbackhttp error：", e.toString());
        }
        return arrayList;
    }

    /**
     * 添加意见反馈
     * @param application
     * @param s
     * @param s1
     * @param token
     * @param uid
     * @return
     */
    public static Statese addfeedbackhttp(Application application, String s, String s1, String token, String uid) {
        Statese statese = new Statese();
        String url = HttpApi.addfeedback;
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader(key_ol,token);
        HttpClient httpClient = new DefaultHttpClient();
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("ID",s1));
        pairs.add(new BasicNameValuePair("MemID",uid));
        pairs.add(new BasicNameValuePair("Content",s));
        pairs.add(new BasicNameValuePair("CreateTime",String.valueOf(System.currentTimeMillis())));
        pairs.add(new BasicNameValuePair("ReplyRemark",""));
        pairs.add(new BasicNameValuePair("ReplyTime",""));
        try {
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(pairs,"utf-8");
            httpPost.setEntity(urlEncodedFormEntity);
            HttpParams params = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(params, 10000); //设置连接超时
            HttpConnectionParams.setSoTimeout(params, 10000); //设置请求超时
            httpPost.setParams(params);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == 200){
                HttpEntity httpEntity = httpResponse.getEntity();
                String content = EntityUtils.toString(httpEntity,"utf-8");
                Log.i("HttpTransfeData addfeedbackhttp",content);
                JSONObject jsonObject = new JSONObject(content);
                statese.setData(jsonObject.getString("data"));
                statese.setState(jsonObject.getString("state"));
                statese.setMsg(jsonObject.getString("msg"));
                if (!jsonObject.getString("token").equals("null")){
                    SaveShared.sharedtokensave(application,jsonObject.getString("token"));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData addfeedbackhttp error：", e.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData addfeedbackhttp error：", e.toString());
        }catch (Exception e){
            LogUtil.e("HttpTransfeData addfeedbackhttp error：", e.toString());
        }
        return statese;
    }

    /**
     * 所有晒单接口
     * @param application
     * @param token
     * @param index
     * @return
     */
    public static ArrayList<Publish> allgoodspublishhttp(Application application, String token, int index) {
        ArrayList<Publish> arrayList = new ArrayList<Publish>();
        String url = HttpApi.allgoods;
        HttpPost httpPost = new HttpPost(url);
        HttpClient httpClient = new DefaultHttpClient();
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("PageIndex",String.valueOf(index)));
        pairs.add(new BasicNameValuePair("PageSize","10"));
        try {
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(pairs,"utf-8");
            httpPost.setEntity(urlEncodedFormEntity);
            HttpParams params = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(params, 10000); //设置连接超时
            HttpConnectionParams.setSoTimeout(params, 10000); //设置请求超时
            httpPost.setParams(params);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == 200){
                HttpEntity httpEntity = httpResponse.getEntity();
                String content = EntityUtils.toString(httpEntity,"utf-8");
                Log.i("HttpTransfeData allgoodspublishhttp",content);
                JSONObject jsonObject = new JSONObject(content);
                if (jsonObject.getString("state").equals(String.valueOf(Numbers.ONE))){
                    arrayList = HttpMothed.allgoodsjson(arrayList,jsonObject.getString("data"));
                }
                return arrayList;
            }
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData allgoodspublishhttp error：", e.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData allgoodspublishhttp error：", e.toString());
        }catch (Exception e){
            LogUtil.e("HttpTransfeData allgoodspublishhttp error：", e.toString());
        }
        return null;
    }

    /**
     * 往期数据请求
     * @param arrayList
     * @param application
     * @param id
     * @param token
     * @param index
     * @return
     */
    public static ArrayList<Publish> pasthttp(ArrayList<Publish> arrayList, Application application, String id, String token, int index) {
//        String url = HttpApi.wanqi;
        String url = HttpApi.pastpublish;
        HttpPost httpPost =  new HttpPost(url);
//        httpPost.setHeader(key_ol,token);
        HttpClient httpClient = new DefaultHttpClient();
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("ID",id));
        pairs.add(new BasicNameValuePair("PageIndex",String.valueOf(index)));
        pairs.add(new BasicNameValuePair("PageSize","10"));
        try {
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(pairs,"utf-8");
            httpPost.setEntity(urlEncodedFormEntity);
            HttpParams params = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(params, 10000); //设置连接超时
            HttpConnectionParams.setSoTimeout(params, 10000); //设置请求超时
            httpPost.setParams(params);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == 200){
                HttpEntity httpEntity = httpResponse.getEntity();
                String content = EntityUtils.toString(httpEntity);
                Log.i("HttpTransfeData pasthttp",content);
                JSONObject jsonObject = new JSONObject(content);
                arrayList = HttpMothed.pasrjson(arrayList,jsonObject.getString("data"));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData pasthttp error：", e.toString());
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData pasthttp error：", e.toString());
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData pasthttp error：", e.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData pasthttp error：",e.toString());
        }catch (Exception e){
            LogUtil.e("HttpTransfeData pasthttp error：", e.toString());
        }
        return arrayList;
    }

    /**
     * 请求幸运码
     * @param activity
     * @param arrayList_ol
     * @return
     */
    public static ArrayList<Count> lucknumberhttp(Context activity, ArrayList<Count> arrayList_ol,String pid,int index) {
        String url = HttpApi.luckynumber;
        HttpPost httpPost = new HttpPost(url);
        HttpClient httpClient =  new DefaultHttpClient();
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        Log.i("HttpTransfeData lucknumberhttp",pid);
        pairs.add(new BasicNameValuePair("ID",pid));
        pairs.add(new BasicNameValuePair("PageIndex",String.valueOf(index)));
        pairs.add(new BasicNameValuePair("PageSize","50"));
        try {
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(pairs,"utf-8");
            httpPost.setEntity(urlEncodedFormEntity);
            HttpParams params = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(params, 10000); //设置连接超时
            HttpConnectionParams.setSoTimeout(params, 10000); //设置请求超时
            httpPost.setParams(params);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            Log.i("HttpTransfeData lucknumberhttp",httpResponse.getStatusLine().getStatusCode()+"");
            if (httpResponse.getStatusLine().getStatusCode() == 200){
                HttpEntity httpEntity = httpResponse.getEntity();
                String content = EntityUtils.toString(httpEntity,"utf-8");
                Log.i("HttpTransfeData lucknumberhttp",content);
                JSONObject jsonObject = new JSONObject(content);
                JSONArray jsonArray = new JSONArray(jsonObject.getString("data"));
                for (int i = 0;i<jsonArray.length();i++){
                    Count count = new Count();
                    count.setCountnumber(jsonArray.get(i).toString());
                    arrayList_ol.add(count);
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData lucknumberhttp error：", e.toString());
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData lucknumberhttp error：", e.toString());
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData lucknumberhttp error：", e.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData lucknumberhttp error：", e.toString());
        }catch (Exception e){
            LogUtil.e("HttpTransfeData lucknumberhttp error：", e.toString());
        }
        return arrayList_ol;
    }

    /**
     * 获取购物车商品数量
     * @param application
     * @param statese
     * @param token
     * @param uid
     * @return
     */
    public static Statese httpshoppingcat(Context application, Statese statese,String token,String uid) {
       String url = HttpApi.goods_number+uid;
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader(key_ol,token);
        HttpClient httpClient = new DefaultHttpClient();
        try {
            HttpParams params = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(params,8000);
            HttpConnectionParams.setSoTimeout(params,10000);
            httpGet.setParams(params);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            LogUtil.e("HttpTransfeData httpshoppingcat", httpResponse.getStatusLine().getStatusCode() + "");
            if (httpResponse.getStatusLine().getStatusCode() == 200){
                HttpEntity httpEntity = httpResponse.getEntity();
                String content = EntityUtils.toString(httpEntity);
                Log.i("HttpTransfeData httpshoppingcat",content);
                if (!TextUtils.isEmpty(content)){
                    JSONObject jsonObject = new JSONObject(content);
                    statese.setData(jsonObject.getString("data"));
                    statese.setState(jsonObject.getString("state"));
                    statese.setMsg(jsonObject.getString("msg"));
                    if (!StringUtils.isEmpty(jsonObject.getString("token"))){
                        SharePreferencesUlits.saveString(application,Constants.TOKEN,jsonObject.getString("token"));
                    }
                }
                return statese;
            }
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData httpshoppingcat error：", e.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData httpshoppingcat error：", e.toString());
        }catch (Exception e){
            LogUtil.e("HttpTransfeData httpshoppingcat error：", e.toString());
        }
        return null;
    }

    /**
     * 获取car商品件数
     * @param statese
     * @param application
     * @param token
     * @return
     */
    public static Statese getcarnumberhttp(Statese statese,Context application, String token) {
        //获取url地址
        String url = HttpApi.car_number;
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader(key_ol,token);
        HttpClient httpClient = new DefaultHttpClient();
        try {
            HttpParams params = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(params, 10000); //设置连接超时
            HttpConnectionParams.setSoTimeout(params, 10000); //设置请求超时
            httpGet.setParams(params);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            //判断网络请求是否成功
            if (httpResponse.getStatusLine().getStatusCode() == 200){
                HttpEntity httpEntity = httpResponse.getEntity();
                String content = EntityUtils.toString(httpEntity);
                JSONObject jsonObject = new JSONObject(content);
                LogUtil.i("HttpTransfeData getcarnumberhttp",content);
                statese.setData(jsonObject.getString("data"));
                statese.setState(jsonObject.getString("state"));
                statese.setMsg(jsonObject.getString("msg"));
                if (!jsonObject.getString("token").equals("null")){
                    SaveShared.sharedtokensave(application,jsonObject.getString("token"));
                }
                return statese;
            }
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData getcarnumberhttp error：",e.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            LogUtil.e("HttpTransfeData getcarnumberhttp error：",e.toString());
        }catch (Exception e){
            LogUtil.e("HttpTransfeData getcarnumberhttp error：",e.toString());
        }
        return null;
    }
}
