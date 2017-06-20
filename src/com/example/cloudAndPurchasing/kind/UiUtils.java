package com.example.cloudAndPurchasing.kind;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Handler;
import android.view.View;

import com.example.cloudAndPurchasing.LocationApplication;

public class UiUtils {

    /**
     *
     * @return context
     */
    public static Context getContext(){
        return LocationApplication.getContext();
    }

    /**
     *
     * @return handler
     */
    public static Handler getMainThreadHandler(){
        return LocationApplication.getHandler();
    }

    /**
     *
     * @return mainthreadid
     */
    public static int getMainThreadId(){
        return LocationApplication.getMainThreadId();
    }

    /**
     * @param resId
     * @return String
     */
    public static String getString(int resId){
        return getContext().getResources().getString(resId);
    }

    /**
     *
     * @param resId
     * @return String[]
     */
    public static String[] getStringArray(int resId){
        return getContext().getResources().getStringArray(resId);
    }

    /**
     *
     * @param resId
     * @return drawable
     */
    public static Drawable getDrawable(int resId){
        return getContext().getResources().getDrawable(resId);
    }

    /**
     *
     * @param resId
     * @return color
     */
    public static int getColor(int resId){
        return getContext().getResources().getColor(resId);
    }

    public static ColorStateList getColorStateList(int resId){
        return getContext().getResources().getColorStateList(resId);
    }

    /**
     * px
     * @param resId
     * @return px
     */
    public static int getDimen(int resId){
        return getContext().getResources().getDimensionPixelSize(resId);
    }

    /**
     * dp-->px
     * @param dp
     * @return px
     */
    public static int dip2px(int dp) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp*density + 0.5);
    }

    /**
     * px-->dp
     * @param px
     * @return dp
     */
    public static int px2dip(int px) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (px/density + 0.5);
    }

    /**
     * 判断是否运行在主线程
     * @param
     * @return boolean
     */
    public static boolean isRunOnUiThread(){
        int mainThreadId = getMainThreadId();
        int currentThreadId = android.os.Process.myTid();
        return mainThreadId == currentThreadId;
    }

    /**
     * 保证运行在主线程
     * @param runnable
     */
    public static void runOnUiThread(Runnable runnable){
        if (isRunOnUiThread()) {
            runnable.run();
        }else {
            getMainThreadHandler().post(runnable);
        }
    }

    /**
     * 加载布局
     * @param resId
     * @return view
     */
    public static View inflateView(int resId) {
        return View.inflate(getContext(), resId, null);
    }

    //创建矩形图片,可设置圆角,和图片的颜色
    public static GradientDrawable getGradientDrawable(float radius , int color ){
        GradientDrawable gd = new GradientDrawable();
        gd.setGradientRadius(radius);
        gd.setGradientType(GradientDrawable.RECTANGLE);
        gd.setColor(color);
        return gd;
    }

    //设置图片的状态选择器
    public static StateListDrawable getStateListDrawable(Drawable pressedDrawable , Drawable normalDrawable){
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[] { android.R.attr.state_pressed }, pressedDrawable);//按下的图片
        stateListDrawable.addState(new int[] {}, normalDrawable);//增加默认状态的图片
        return stateListDrawable;
    }



}