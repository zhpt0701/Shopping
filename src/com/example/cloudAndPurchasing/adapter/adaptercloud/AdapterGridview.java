package com.example.cloudAndPurchasing.adapter.adaptercloud;

import android.content.Context;
import android.graphics.Bitmap;
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
import java.util.List;

/**
 * Created by Administrator on 2016/4/5 0005.
 */
public class AdapterGridview extends BaseAdapter{
    private List<String> arrayList;
    private Context context;
    private ImageLoader imageLoader1;
    private DisplayImageOptions options1;
    public AdapterGridview(ImageLoader imageLoader,DisplayImageOptions options,List<String> arrayList1,Context context1){
        arrayList = arrayList1;
        context = context1;
        imageLoader1 = imageLoader;
        options1 = options;
    }
    @Override
    public int getCount() {
        if (arrayList != null){
            return arrayList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (arrayList != null){
            return arrayList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHandler viewHandler;
        if (convertView == null){
            viewHandler = new ViewHandler();
            convertView = LayoutInflater.from(context).inflate(R.layout.adaptergridviewitem,null);
            viewHandler.imageView = (ImageView)convertView.findViewById(R.id.imageview_photo);
            convertView.setTag(viewHandler);
        }else {
            viewHandler = (ViewHandler)convertView.getTag();
        }
        imageLoader1.displayImage(arrayList.get(position), viewHandler.imageView, options1);
        return convertView;
    }
    class ViewHandler{
        ImageView imageView;
    }
}
