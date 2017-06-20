package com.example.cloudAndPurchasing;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;
import com.example.cloudAndPurchasing.baidu.LocationService;
import com.example.cloudAndPurchasing.http.Numbers;
import com.github.anrwatchdog.ANRError;
import com.github.anrwatchdog.ANRWatchDog;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.model.HttpHeaders;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.umeng.socialize.PlatformConfig;

import java.io.*;

/**
 * 主Application，所有百度定位SDK的接口说明请参考线上文档：http://developer.baidu.com/map/loc_refer/index.html
 *
 * 百度定位SDK官方网站：http://developer.baidu.com/map/index.php?title=android-locsdk
 * 
 * 直接拷贝com.baidu.location.service包到自己的工程下，简单配置即可获取定位结果，也可以根据demo内容自行封装
 */
public class LocationApplication extends Application {
	public LocationService locationService;
    public Vibrator mVibrator;

    public static String CAR_NUMBER;
    public static int STATENUMBER = Numbers.ONE;
    ANRWatchDog anrWatchDog = new ANRWatchDog(2000);
    public Activity mActivity;

    public void addActivity(Activity activity){
        mActivity =  activity;
    }

    public void removeActivity(Activity activity){
        activity.finish();
    }

    private static LocationApplication instance;
    public LocationApplication (){

    }

    public static synchronized LocationApplication getInstance(){
        if (instance == null){
            instance = new LocationApplication();
        }
        return instance;
    }


    private static final String TAG = "JPush";
    private static Handler handler;
    private static int mainThreadid;
    private static Context context;
    @Override
    public void onCreate() {
        anrWatchDog.setANRListener(new ANRWatchDog.ANRListener() {
            @Override
            public void onAppNotResponding(ANRError error) {
                Log.e("ANR-Watchdog", "Detected Application Not Responding!");

                // Some tools like ACRA are serializing the exception, so we must make sure the exception serializes correctly
                try {
                    new ObjectOutputStream(new ByteArrayOutputStream()).writeObject(error);
                }
                catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                Log.i("ANR-Watchdog", "Error was successfully serialized");

                throw error;
            }
        });

        anrWatchDog.start();
        Log.d(TAG, "[ExampleApplication] onCreate");
        super.onCreate();
        context = getApplicationContext();
        handler = new Handler();
        mainThreadid = android.os.Process.myTid();
        /***
         * 初始化定位sdk，建议在Application中创建
         */
        locationService = new LocationService(getApplicationContext());
        mVibrator =(Vibrator)getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);

//        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
//        JPushInterface.init(this);
        configImageLoader();
//        SDKInitializer.initialize(getApplicationContext());
        PlatformConfig.setWeixin("wx967daebe835fbeac", "5bb696d9ccd75a38c8a0bfe0675559b3");
        //微信 appid appsecret
        PlatformConfig.setSinaWeibo("3921700954","04b48b094faeb16683c32669824ebdad");
        //新浪微博 appkey appsecret
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
        // QQ和Qzone appid appkey
        PlatformConfig.setAlipay("2015111700822536");
        //支付宝 appid
        PlatformConfig.setYixin("yxc0614e80c9304c11b0391514d09f13bf");
        //易信 appkey
        PlatformConfig.setTwitter("3aIN7fuF685MuZ7jtXkQxalyi", "MK6FEYG63eWcpDFgRYw4w9puJhzDl0tyuqWjZ3M7XJuuG7mMbO");
        //Twitter appid appkey
        PlatformConfig.setPinterest("1439206");
        //Pinterest appid
        PlatformConfig.setLaiwang("laiwangd497e70d4", "d497e70d4c3e4efeab1381476bac4c5e");
        //来往 appid appkey
//        Thread.currentThread().setUncaughtExceptionHandler(new MyUncaughtExceptionHandler());

        //-----------初始化okhttputils-----------//
        HttpHeaders headers = new HttpHeaders();
       /* headers.put("commonHeaderKey1", "commonHeaderValue1");    //所有的 header 不支持 中文
        headers.put("commonHeaderKey2", "commonHeaderValue2");*/
        /*HttpParams params = new HttpParams();
        params.put("commonParamsKey1", "commonParamsValue1");     //所有的 params 都 支持 中文
        params.put("commonParamsKey2", "这里支持中文参数");*/

        //必须调用初始化
        OkHttpUtils.init(this);
        //以下都不是必须的，根据需要自行选择
        OkHttpUtils.getInstance()//
                .debug("OkHttpUtils")                                              //是否打开调试
                .setConnectTimeout(OkHttpUtils.DEFAULT_MILLISECONDS)               //全局的连接超时时间
                .setReadTimeOut(OkHttpUtils.DEFAULT_MILLISECONDS)                  //全局的读取超时时间
                .setWriteTimeOut(OkHttpUtils.DEFAULT_MILLISECONDS)                 //全局的写入超时时间
                        //.setCookieStore(new MemoryCookieStore())                           //cookie使用内存缓存（app退出后，cookie消失）
                        //.setCookieStore(new PersistentCookieStore())                       //cookie持久化存储，如果cookie不过期，则一直有效
                .addCommonHeaders(headers)                                         //设置全局公共头
                /*.addCommonParams(params)*/;


    }
    /**
     *
     */
    private void configImageLoader() {
        // 初始化ImageLoader
        @SuppressWarnings("deprecation")
        DisplayImageOptions options = new DisplayImageOptions.Builder().showStubImage(R.drawable.banner_jiazaishibai_02) // 设置图片下载期间显示的图片
                .showImageOnLoading(R.drawable.banner_jiazaishibai_02)
                .showImageOnFail(R.drawable.banner_jiazaishibai_02)// 设置图片加载或解码过程中发生错误显示的图片
                .bitmapConfig(Bitmap.Config.RGB_565)
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
                        // .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
                .build(); // 创建配置过得DisplayImageOption对象

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext()).defaultDisplayImageOptions(options)
                .threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator()).tasksProcessingOrder(QueueProcessingType.LIFO).build();
        ImageLoader.getInstance().init(config);
    }
    public static Handler getHandler() {
        return handler;
    }
    public static Context getContext() {
        return context;
    }

    public static int getMainThreadId() {
        return mainThreadid;
    }

    /**
     * 异常捕获
     */
    private class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
        //当有未捕获的异常的时候调用
        //Throwable : 错误或者异常的父类，表示异常或者错误
        @Override
        public void uncaughtException(Thread thread, Throwable ex) {
            Toast.makeText(getApplicationContext() , "哥捕获了异常" , Toast.LENGTH_LONG);
            try {
                ex.printStackTrace();//打印出异常信息
                ex.printStackTrace(new PrintStream(new File(Environment.getExternalStorageDirectory()+"/error.log")));//将异常信息保存到文件中
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            //杀死进程，闪退
            //自己杀死自己，自杀
            android.os.Process.myPid();l : //获取当前应用程序的进程的pid
            android.os.Process.killProcess(android.os.Process.myPid());
        }

    }


}
