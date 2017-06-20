package com.example.cloudAndPurchasing.adapter.adaptersurpired;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.activity.activitysurpired.ActivityShareDetail;
import com.example.cloudAndPurchasing.http.HttpApi;
import com.example.cloudAndPurchasing.imageloder.Myimageloder;
import com.example.cloudAndPurchasing.kind.Commentes;
import com.example.cloudAndPurchasing.mothendsol.Tailoring;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/14 0014.
 */
public class AdapterListSharedateil extends BaseAdapter{
    private Context context;
    private ArrayList<Commentes> arrayList;
    private ImageLoader imageLoader;
    private Myimageloder myimageloder;
    private DisplayImageOptions options;
    public AdapterListSharedateil(ImageLoader imageLoader1,DisplayImageOptions options1,Context activityShareDetail, ArrayList<Commentes> arrayList1) {
        context = activityShareDetail;
        arrayList = arrayList1;
        imageLoader = imageLoader1;
        options = options1;
        myimageloder = new Myimageloder(activityShareDetail);
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
            convertView = LayoutInflater.from(context).inflate(R.layout.adapterlistsharelitem,null);
            viewHandler.textView_name = (TextView)convertView.findViewById(R.id.textview_yonghuusername);
            viewHandler.textView_time = (TextView)convertView.findViewById(R.id.textview_pinglunshijan);
            viewHandler.textView_title = (TextView)convertView.findViewById(R.id.textview_pinglunneirong);
            viewHandler.imageView = (ImageView)convertView.findViewById(R.id.imageview_pinglunzhe);
            convertView.setTag(viewHandler);
        }else {
            viewHandler = (ViewHandler)convertView.getTag();
        }
        Commentes commentes = arrayList.get(position);
        Log.i("this","this_ol"+commentes.getCommentpath());
        if (commentes.getCommentpath() != "null"){
//            byte[] bitmapArray;
//            bitmapArray = Base64.decode(commentes.getCommentpath(), Base64.DEFAULT);
//            Bitmap bitmap =
//                    BitmapFactory.decodeByteArray(bitmapArray, 0,
//                            bitmapArray.length);
//            Bitmap bitmap1 = Tailoring.phototailoring(bitmap);
//            viewHandler.imageView.setImageBitmap(bitmap1);
            myimageloder.imageLoader.displayImage(HttpApi.yu+commentes.getCommentpath(), viewHandler.imageView, myimageloder.options);
        }
            viewHandler.textView_name.setText(commentes.getCommentesname());
            viewHandler.textView_time.setText(commentes.getCommemtestime());
            viewHandler.textView_title.setText(commentes.getCommentescontent());
        return convertView;
    }
    class ViewHandler{
        TextView textView_name,textView_time,textView_title;
        ImageView imageView;
    }
}
