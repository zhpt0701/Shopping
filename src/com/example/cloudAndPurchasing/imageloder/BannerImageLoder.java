package com.example.cloudAndPurchasing.imageloder;

import android.content.Context;
import android.graphics.Bitmap;
import com.example.cloudAndPurchasing.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by Administrator on 2016/6/18 0018.
 */
public class BannerImageLoder {
    public static ImageLoader imageLoader;
    public static DisplayImageOptions options;
    public BannerImageLoder(Context context){
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        //显示图片的配置
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.jiazaishibai_03)
                .showImageOnFail(R.drawable.jiazaishibai_03)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
    }
}
