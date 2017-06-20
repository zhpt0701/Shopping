package com.example.cloudAndPurchasing.http;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Administrator on 2016/5/18 0018.
 */
public class PhotoTime {
    /**
     * 时间转换
     * @param countdown
     * @return
     */
    public static String times(String countdown) {
        int h = 0;
        int d = 0;
        int s = 0;
        int temp = (Integer.parseInt(countdown))%3600;
        if (Integer.parseInt(countdown) > 3600) {
            h = (Integer.parseInt(countdown))/ 3600;
            if (temp != 0) {
                if (temp > 60) {
                    d = temp / 60;
                    if (temp % 60 != 0) {
                        s = temp % 60;
                    }
                } else {
                    s = temp;
                }
            }
        } else {
            d = (Integer.parseInt(countdown)) / 60;
            if ((Integer.parseInt(countdown)) % 60 != 0) {
                s = (Integer.parseInt(countdown)) % 60;
            }
        }
        return h + "时" + d + "分" + s + "秒";
    }
    /**
     * 网络图片获取
     * @param mainImg
     * @return
     */

    public static Bitmap bitmapimage(String mainImg) {
        Bitmap bitmap = null;
        try {
            URL myFileUrl = new URL(mainImg);
            HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

}
