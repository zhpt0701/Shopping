package com.example.cloudAndPurchasing.http;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by Administrator on 2016/5/27 0027.
 */
public class HttpUilt {
    private static AsyncHttpClient client =new AsyncHttpClient();
    static {
        client.setTimeout(11000);   //设置链接超时，如果不设置，默认为10s
    }
    //用一个完整url获取一个string对象
    public static void get(String urlString,AsyncHttpResponseHandler res)
    {
        client.get(urlString, res);
    }
    //url里面带参数
    public static void get(String urlString,RequestParams params,AsyncHttpResponseHandler res)
    {
      client.get(urlString, params,res);
    }
    //不带参数，获取json对象或者数组
    public static void get(String urlString,JsonHttpResponseHandler res){
        client.get(urlString, res);
    }
    //带参数，获取json对象或者数组
    public static void get(String urlString,RequestParams params,JsonHttpResponseHandler res){
            client.get(urlString, params,res);
    }

    //post请求
    //用一个完整url获取一个string对象
    public static void post(String urlString,AsyncHttpResponseHandler res)
    {
        client.post(urlString, res);
    }
    //url里面带参数
    public static void post(String urlString,RequestParams params,AsyncHttpResponseHandler res)
    {
        client.post(urlString, params, res);
    }
    //不带参数，获取json对象或者数组
    public static void post(String urlString,JsonHttpResponseHandler res){
        client.post(urlString,res);
    }
    //带参数，获取json对象或者数组
    public static void post(String urlString,RequestParams params,JsonHttpResponseHandler res){
        client.post(urlString,params,res);
    }
}
