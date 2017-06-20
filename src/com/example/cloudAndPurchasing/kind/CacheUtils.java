package com.example.cloudAndPurchasing.kind;

import android.util.Log;


import java.io.*;

/**
 * Description：类的功能描述
 * Copyright  ：Copyright（c）2016
 * Author     ：feng
 * Date       ：2016/7/16 23:11
 */
public class CacheUtils {

    public static void  setCache(String json , String url , int minutes) throws IOException {
        FileWriter writer = null;
        try{
            //1 sp -- 配置信息
            //2 sqlite  -- 存储有关联的数据
            //3  文件  ---存储网络缓存   sdcard   应用内部存储空间
            File cacheDir = UiUtils.getContext().getCacheDir();
            Log.e("path" , cacheDir + File.separator + MD5Encoder.encode(url)+"");
            //文件名：唯一性
            File cacheFile = new File(cacheDir + File.separator + MD5Encoder.encode(url));
            writer = new FileWriter(cacheFile);
            String strEmpireTime = String.valueOf(System.currentTimeMillis() +  minutes * 60 * 1000);
            writer.write(strEmpireTime + "\n");
            writer.write(json);
            writer.flush();

        }catch(Exception e) {

        } finally {
//            IOUtils.close(writer);
            if (writer != null){
                writer.close();
            }

        }

    }

    /**
     *
     * @param url          服务器路径
     * @param isFirstLine  true 读取缓存文件的第一行, false 读取缓存文件的第二行
     * @return
     */
    public static String getCache(String url , boolean isFirstLine) throws IOException {
        BufferedReader bReader = null;
        try{
            //1 sp -- 配置信息
            //2 sqlite  -- 存储有关联的数据
            //3  文件  ---存储网络缓存   sdcard   应用内部存储空间
            File cacheDir = UiUtils.getContext().getCacheDir();
            //文件名：唯一性
            File cacheFile = new File(cacheDir + File.separator + MD5Encoder.encode(url));
            FileReader reader = new FileReader(cacheFile);
            //reader.re
            bReader = new BufferedReader(reader);
            String strEmpireTime = bReader.readLine();
            long currentTime = System.currentTimeMillis();
            long savedTime = Long.parseLong(strEmpireTime);
            if (isFirstLine){
                if(currentTime > savedTime) {
                    //缓存无效
                    return null;
                } else {
                    //缓存有效
                    String str = null;
                    StringBuffer sb = new StringBuffer();
                    while((str=bReader.readLine()) != null) {
                        sb.append(str);
                    }

                    return sb.toString();
                }
            }else{
                    //缓存有效
                    String str = null;
                    StringBuffer sb = new StringBuffer();
                    while((str=bReader.readLine()) != null) {
                        sb.append(str);
                    }

                    return sb.toString();
            }


        }catch(Exception e) {

        } finally {
//            IOUtils.close(bReader);
            if (bReader != null){
                bReader.close();
            }
        }

        return null;
    }
}
