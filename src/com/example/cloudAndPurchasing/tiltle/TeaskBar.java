package com.example.cloudAndPurchasing.tiltle;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;
import com.example.cloudAndPurchasing.R;

/**
 * Created by zhangpengtao on 2016/3/21 0021.
 */
public class TeaskBar {
    //颜色背景
    public static void onSystemoutcolor(Activity activity) {
        if (Build.VERSION.SDK_INT >= 18){
            setTranslucentStatus(activity,true);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(activity);
        tintManager.setStatusBarTintEnabled(true);
        //使用颜色资源
        tintManager.setStatusBarTintResource(R.color.tiltle);
    }
    //图片背景
    public static void onSystemoutcoloront(Activity activity) {
        if (Build.VERSION.SDK_INT >= 18){
            setTranslucentStatus(activity,true);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(activity);
        tintManager.setStatusBarTintEnabled(true);
        //使用背景资源
        tintManager.setStatusBarTintResource(R.drawable.me_bj_01);
    }
    @TargetApi(19)
    private static void setTranslucentStatus(Activity activity, boolean b) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winparms = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (b){
            winparms.flags |= bits;
        }else {
            winparms.flags &= ~bits;
        }
        win.setAttributes(winparms);
    }
}
