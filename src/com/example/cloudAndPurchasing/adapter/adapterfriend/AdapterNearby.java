package com.example.cloudAndPurchasing.adapter.adapterfriend;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.http.HttpApi;
import com.example.cloudAndPurchasing.kind.Friend;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/8 0008.
 */
public class AdapterNearby extends BaseAdapter{
    private ArrayList<Friend> arrayList1;
    private Context context;
    private NearbyOnClickListner nearbyOnClickListner;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;
    public AdapterNearby(ImageLoader imageLoader1,DisplayImageOptions options1,Context activitySelectNearby, ArrayList<Friend> arrayList,NearbyOnClickListner nearbyOnClickListner1) {
        context = activitySelectNearby;
        nearbyOnClickListner = nearbyOnClickListner1;
        arrayList1 = arrayList;
        imageLoader = imageLoader1;
        options = options1;
    }

    @Override
    public int getCount() {
        if (arrayList1 != null){
            return arrayList1.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (arrayList1 != null){
            return arrayList1.get(position);
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
            convertView = LayoutInflater.from(context).inflate(R.layout.adapternearbyitem,null);
            viewHandler.imageview = (ImageView)convertView.findViewById(R.id.imageview_tianjia_photo_nearby);
            viewHandler.textview = (TextView)convertView.findViewById(R.id.textview_nickname_nearby);
            viewHandler.textview_ol = (TextView)convertView.findViewById(R.id.textview_thisphonenumber_nearby);
            viewHandler.textview_ool = (TextView)convertView.findViewById(R.id.textview_zhuzhi_nearby);
            viewHandler.imageButton = (Button)convertView.findViewById(R.id.imagebutton_tianjia);
            convertView.setTag(viewHandler);
        }else {
            viewHandler = (ViewHandler)convertView.getTag();
        }
        Friend friend = arrayList1.get(position);
        if (friend.getSex() != "null"){
            viewHandler.textview.setText(friend.getNickname()+"   "+friend.getSex());
        }else {
            viewHandler.textview.setText(friend.getNickname());
        }
        viewHandler.textview_ol.setText("手机号:" + friend.getPhonenumber());
        if (friend.getArea() != "null"){
            viewHandler.textview_ool.setText(friend.getArea()+"  "+"ID:"+friend.getID());
        }else {
            viewHandler.textview_ool.setText("ID:"+friend.getID());
        }
        imageLoader.displayImage(HttpApi.mytu + friend.getID(), viewHandler.imageview,options);
        viewHandler.imageButton.setOnClickListener(nearbyOnClickListner);
        viewHandler.imageButton.setTag(position);
        return convertView;
    }
    class ViewHandler{
        TextView textview,textview_ol,textview_ool;
        ImageView imageview;
        Button imageButton;
    }
    public static abstract class NearbyOnClickListner implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            nearbyOnClickListner((Integer)v.getTag(),v);
        }

        protected abstract void nearbyOnClickListner(Integer tag, View v);
    }
}
