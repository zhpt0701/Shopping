package com.example.cloudAndPurchasing.adapter.adaptercloud;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.http.HttpApi;
import com.example.cloudAndPurchasing.kind.Image;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/6 0006.
 */
public class AdapterGridviewPhoto extends BaseAdapter{
    private Context context;
    private ArrayList<Image> images;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;
    public AdapterGridviewPhoto(ImageLoader imageLoader1,DisplayImageOptions options1,Context activitySurce, ArrayList<Image> arrayList) {
        context = activitySurce;
        images = arrayList;
        imageLoader = imageLoader1;
        options = options1;
    }

    @Override
    public int getCount() {
        if (images != null){
            return images.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (images != null){
            return images.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Image image = images.get(position);
        ViewHandler viewHandler;
        if (convertView == null){
            viewHandler = new ViewHandler();
            convertView = LayoutInflater.from(context).inflate(R.layout.adapteritem,null);
            viewHandler.imageView = (ImageView)convertView.findViewById(R.id.imageview_my_photo);
            convertView.setTag(viewHandler);
        }else {
            viewHandler = (ViewHandler)convertView.getTag();
        }
        imageLoader.displayImage(image.getShowImg(), viewHandler.imageView, options);
        return convertView;
    }

    class ViewHandler{
        ImageView imageView;
    }
}
