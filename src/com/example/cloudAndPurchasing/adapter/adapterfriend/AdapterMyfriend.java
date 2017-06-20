package com.example.cloudAndPurchasing.adapter.adapterfriend;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.http.HttpApi;
import com.example.cloudAndPurchasing.kind.Friend;
import com.example.cloudAndPurchasing.mothendsol.Tailoring;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/3/31 0031.
 */
public class AdapterMyfriend extends BaseAdapter{
    private Context context;
    private ArrayList<Friend> array;
    private ImageLoader imageLoader1;
    private DisplayImageOptions options1;
    public AdapterMyfriend(ImageLoader imageLoader,DisplayImageOptions options,Context activityFriend, ArrayList<Friend> arrayList) {
        context = activityFriend;
        imageLoader1 = imageLoader;
        options1 = options;
        array = arrayList;
    }

    @Override
    public int getCount() {
        if (array != null){
            return array.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (array != null){
            return array.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHandler viewhandler;
        if (convertView == null) {
            viewhandler = new ViewHandler();
            convertView = LayoutInflater.from(context).inflate(R.layout.adaptermyfrienditem, null);
            viewhandler.imageview = (ImageView) convertView.findViewById(R.id.imageview_friend_photo_OL);
            viewhandler.textview = (TextView) convertView.findViewById(R.id.textview_nickname);
            viewhandler.textview_ol = (TextView) convertView.findViewById(R.id.textview_thisphonenumber);
            viewhandler.textview_ool = (TextView) convertView.findViewById(R.id.textview_zhuzhi);
            convertView.setTag(viewhandler);
        } else {
            viewhandler = (ViewHandler) convertView.getTag();
        }
        Friend friend = array.get(position);
        viewhandler.textview.setText(friend.getNickname());
        viewhandler.textview_ol.setText("手机号: " + friend.getPhonenumber());
        if (friend.getArea() != "null") {
            viewhandler.textview_ool.setText(friend.getArea() + " " + " ID:" + friend.getID());
        } else {
            viewhandler.textview_ool.setText("ID:" + friend.getID());
        }
        if (!friend.getImagepath().equals("null")) {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(friend.getImagepath(), Base64.DEFAULT);
            Bitmap bitmap =
                    BitmapFactory.decodeByteArray(bitmapArray, 0,
                            bitmapArray.length);
            Bitmap bitmap1 = Tailoring.phototailoring(bitmap);
            viewhandler.imageview.setImageBitmap(bitmap1);
//            imageLoader1.displayImage(HttpApi.tu_ol + friend.getImagepath(), viewhandler.imageview, options1);
        }
        return convertView;
    }
    class ViewHandler{
        TextView textview,textview_ol,textview_ool;
        ImageView imageview;
    }

}
