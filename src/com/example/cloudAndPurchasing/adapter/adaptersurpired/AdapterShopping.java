package com.example.cloudAndPurchasing.adapter.adaptersurpired;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.adapter.adaptercloud.AdapterGridview;
import com.example.cloudAndPurchasing.customcontrol.ThisGridView;
import com.example.cloudAndPurchasing.customcontrol.listviewupdown.PullableListView;
import com.example.cloudAndPurchasing.http.HttpApi;
import com.example.cloudAndPurchasing.imageloder.Myimageloder;
import com.example.cloudAndPurchasing.kind.Publish;
import com.example.cloudAndPurchasing.mothendsol.Tailoring;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/25 0025.
 */
public class AdapterShopping extends BaseAdapter{
    private Context content1;
    private ArrayList<Publish> arraylist;
    private ShoppingOnClickListener shopping;
    private PhotoOnitemlistener photoOnitemlistener;
    private ImageLoader imageLoader;
    private Myimageloder myimageloder;
    private DisplayImageOptions options;
    public AdapterShopping(ImageLoader imageLoader,DisplayImageOptions options,Context activity, ArrayList<Publish> arrayList,ShoppingOnClickListener shopping,PhotoOnitemlistener photoOnitemlistener) {
        content1 = activity;
        this.arraylist = arrayList;
        this.shopping = shopping;
        this.photoOnitemlistener = photoOnitemlistener;
        this.imageLoader = imageLoader;
        this.options = options;
        myimageloder = new Myimageloder(activity);
    }

    @Override
    public int getCount() {
        if (arraylist != null){
            return arraylist.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (arraylist != null){
            return arraylist.get(position);
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
        if (convertView == null){
            viewhandler = new ViewHandler();
            convertView = LayoutInflater.from(content1).inflate(R.layout.adaptershopingitem,null);
            viewhandler.textviewname = (TextView)convertView.findViewById(R.id.textview_publishnacknameol);
            viewhandler.textview_time = (TextView)convertView.findViewById(R.id.textview_timeol);
            viewhandler.textview_shoppingname = (TextView)convertView.findViewById(R.id.textview_contentol);
            viewhandler.textView_title = (TextView)convertView.findViewById(R.id.textview_publishtitle);
            viewhandler.textviewcontent = (TextView)convertView.findViewById(R.id.textveiw_contentol);
            viewhandler.textivewzan = (TextView)convertView.findViewById(R.id.textview_zannumberol);
            viewhandler.textview_publish = (TextView)convertView.findViewById(R.id.textview_publishnumberol);
            viewhandler.gridview = (ThisGridView)convertView.findViewById(R.id.gridview_this_photool);
            viewhandler.imagebutton = (ImageButton)convertView.findViewById(R.id.button_zanol);
            viewhandler.imagebutton_ol = (ImageButton)convertView.findViewById(R.id.button_publishol);
            viewhandler.imageView = (ImageView)convertView.findViewById(R.id.imageview_publish_ol);
            convertView.setTag(viewhandler);
        }else {
            viewhandler = (ViewHandler)convertView.getTag();
        }
        TextView textView1 = (TextView)convertView.findViewById(R.id.textview_zannumberol);
        viewhandler.textivewzan = textView1;
        Publish publish = arraylist.get(position);
        if (publish.getArrayList() != null){
            viewhandler.gridview.setOnScrollListener(new PauseOnScrollListener(imageLoader,true,false));
            AdapterGridview adapterGridview = new AdapterGridview(imageLoader,options,publish.getArrayList(),content1);
            viewhandler.gridview.setAdapter(adapterGridview);
        }
        if (publish.getImagepath() != "null"){
//            byte[] bitmapArray;
//            bitmapArray = Base64.decode(publish.getImagepath(), Base64.DEFAULT);
//            Bitmap bitmap =
//                    BitmapFactory.decodeByteArray(bitmapArray, 0,
//                            bitmapArray.length);
//            Bitmap bitmap1 = Tailoring.phototailoring(bitmap);
//            viewhandler.imageView.setImageBitmap(bitmap1);
            myimageloder.imageLoader.displayImage(HttpApi.yu+publish.getImagepath(),viewhandler.imageView,myimageloder.options);
        }

        viewhandler.imagebutton.setOnClickListener(shopping);
        viewhandler.imagebutton_ol.setOnClickListener(shopping);
        viewhandler.gridview.setOnItemClickListener(photoOnitemlistener);
        viewhandler.imagebutton.setTag(position);
        viewhandler.imagebutton_ol.setTag(position);
        viewhandler.gridview.setTag(position);
        viewhandler.textView_title.setText(publish.getTitle());
        viewhandler.textviewname.setText(publish.getNickname());
        viewhandler.textview_time.setText(publish.getTime());
        viewhandler.textview_shoppingname.setText(publish.getShoppingname());
        viewhandler.textviewcontent.setText(publish.getContent());
        viewhandler.textivewzan.setText(publish.getFaverconunt());
        viewhandler.textview_publish.setText(publish.getPublishnumber());
        return convertView;
    }

    class ViewHandler{
        TextView textView_title,textviewname,textview_time,textview_shoppingname,textviewcontent,textivewzan,textview_publish;
        ThisGridView gridview;
        ImageView imageView;
        ImageButton imagebutton,imagebutton_ol;
    }
    public static abstract class ShoppingOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            shopping((Integer)v.getTag(),v);
        }

        protected abstract void shopping(Integer tag, View v);
    }
    public static abstract class PhotoOnitemlistener implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            photoOnitemlistener((Integer)parent.getTag(),view,parent,position,id);
        }

        protected abstract void photoOnitemlistener(Integer tag, View view, AdapterView<?> parent, int position, long id);

    }
    public void updateviewthis(ListView listView, Integer tag, int c) {
        int first = listView.getFirstVisiblePosition();
        if (tag-first>=0){
            View view1 = listView.getChildAt(tag-first);
            ViewHandler viewHandler1 = (ViewHandler)view1.getTag();
            viewHandler1.textivewzan = (TextView)view1.findViewById(R.id.textview_zannumberol);
            viewHandler1.textivewzan.setText(String.valueOf(c));
            arraylist.get(tag).setFaverconunt(String.valueOf(c));
        }
    }
}
