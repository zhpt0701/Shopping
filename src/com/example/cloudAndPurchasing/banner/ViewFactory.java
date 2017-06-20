package com.example.cloudAndPurchasing.banner;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.imageloder.BannerImageLoder;
import com.example.cloudAndPurchasing.imageloder.Imageloderinit;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * ImageView创建工厂
 */
public class ViewFactory {
    public static BannerImageLoder bannerImageLoder;
	/**
	 * 获取ImageView视图的同时加载显示url
	 * 
	 * @param
	 * @return
	 */
	public static ImageView getImageView(Context context, String url) {
            ImageView imageView = (ImageView)LayoutInflater.from(context).inflate(
                    R.layout.view_banner, null);
            ImageLoader.getInstance().displayImage(url, imageView);
            return imageView;
	}
}
