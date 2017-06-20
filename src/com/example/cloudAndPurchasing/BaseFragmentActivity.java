package com.example.cloudAndPurchasing;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.example.cloudAndPurchasing.manager.AppManager;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

/**
 * Created by Administrator on 2016/8/1 0001.
 */
public class BaseFragmentActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        PushAgent mPushAgent = PushAgent.getInstance(getApplication());
//        mPushAgent.enable();
//        PushAgent.getInstance(getApplication()).onAppStart();
//        String device_token = UmengRegistrar.getRegistrationId(getApplication());
        // 添加Activity到堆栈
//        File cacheDir = StorageUtils.getOwnCacheDirectory(this, "/sdcard/" + System.currentTimeMillis() + ".png");
//        ImageLoaderConfiguration config = new ImageLoaderConfiguration
//                .Builder(this)
//                .memoryCacheExtraOptions(480, 800) // maxwidth, max height，即保存的每个缓存文件的最大长宽
//                .threadPoolSize(3)//线程池内加载的数量
//                .threadPriority(Thread.NORM_PRIORITY - 2)
//                .denyCacheImageMultipleSizesInMemory()
//                .memoryCache(new UsingFreqLimitedMemoryCache(2* 1024 * 1024)) // You can pass your own memory cache implementation/你可以通过自己的内存缓存实现
//                .memoryCacheSize(2 * 1024 * 1024)
//                .discCacheSize(50 * 1024 * 1024)
//                .discCacheFileNameGenerator(new Md5FileNameGenerator())//将保存的时候的URI名称用MD5 加密
//                .tasksProcessingOrder(QueueProcessingType.LIFO)
//                .discCacheFileCount(1) //缓存的文件数量
//                .discCache(new UnlimitedDiskCache(cacheDir))//自定义缓存路径
//                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
//                .imageDownloader(new BaseImageDownloader(this,5 * 1000, 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)超时时间
//                .writeDebugLogs() // Remove for releaseapp
//                .build();//开始构建
//        ImageLoader.getInstance().init(config);
        AppManager.getAppManager().addActivity(this);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 结束Activity从堆栈中移除
        AppManager.getAppManager().finishActivity(this);
    }

}
