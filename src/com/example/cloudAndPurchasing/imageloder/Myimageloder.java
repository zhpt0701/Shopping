package com.example.cloudAndPurchasing.imageloder;

import android.content.Context;
import android.graphics.Bitmap;
import com.example.cloudAndPurchasing.R;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

/**
 * Created by Administrator on 2016/6/8 0008.
 */
public class Myimageloder {
    public static ImageLoader imageLoader;
    public static DisplayImageOptions options;
    public Myimageloder(Context context){
        imageLoader = ImageLoader.getInstance();
        if (context != null){
            imageLoader.init(ImageLoaderConfiguration.createDefault(context));
            //显示图片的配置
            options = new DisplayImageOptions.Builder()
                    .displayer(new RoundedBitmapDisplayer(100))
                    .showImageOnLoading(R.drawable.head_photopath)
                    .showImageOnFail(R.drawable.head_photopath)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .displayer(new CircleBitmapDisplayer())
                    .cacheInMemory(true)                               //启用内存缓存
                    .cacheOnDisk(true)
                    .build();
        }
    }
}
