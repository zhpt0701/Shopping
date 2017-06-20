package com.example.cloudAndPurchasing.adapter.adaptersurpired;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.adapter.adaptercloud.AdapterGridview;
import com.example.cloudAndPurchasing.kind.Publish;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/13 0013.
 */
public class AdapterSingleRecord extends BaseAdapter {
    private Context context1;
    private ArrayList<Publish> arraylist1;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;
    private PhotoOnitemlistener photoOnitemlistener;
    public AdapterSingleRecord(ImageLoader imageLoader1,DisplayImageOptions options1,Context activity, ArrayList<Publish> arrayList,PhotoOnitemlistener photoOnitemlistener1) {
        context1 = activity;
        arraylist1 = arrayList;
        imageLoader = imageLoader1;
        options = options1;
        photoOnitemlistener = photoOnitemlistener1;
    }

    @Override
    public int getCount() {
        if (arraylist1 != null){
            return arraylist1.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (arraylist1 != null){
            return arraylist1.get(position);
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
            convertView = LayoutInflater.from(context1).inflate(R.layout.adaptersinglerecorditem,null);
            viewhandler.textview_title = (TextView)convertView.findViewById(R.id.textview_title);
            viewhandler.textview_time = (TextView)convertView.findViewById(R.id.textview_sharetime);
            viewhandler.textview_content = (TextView)convertView.findViewById(R.id.textveiw_sharecontent);
            viewhandler.imageview = (GridView)convertView.findViewById(R.id.imgeview_share);
            convertView.setTag(viewhandler);
        }else {
            viewhandler = (ViewHandler)convertView.getTag();
        }
        Publish publish = arraylist1.get(position);
        viewhandler.textview_title.setText(publish.getTitle());
        viewhandler.textview_time.setText(publish.getTime());
        viewhandler.textview_content.setText(publish.getContent());
        if (publish.getArrayList() != null){
            viewhandler.imageview.setOnScrollListener(new PauseOnScrollListener(imageLoader,true,false));
            AdapterGridview adapterGridview = new AdapterGridview(imageLoader,options,publish.getArrayList(),context1);
            viewhandler.imageview.setAdapter(adapterGridview);
        }
        viewhandler.imageview.setOnItemClickListener(photoOnitemlistener);
        viewhandler.imageview.setTag(position);
        return convertView;
    }
    public static abstract class PhotoOnitemlistener implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            photoOnitemlistener((Integer)parent.getTag(),view,parent,position,id);
        }

        protected abstract void photoOnitemlistener(Integer tag, View view, AdapterView<?> parent, int position, long id);

    }
    class ViewHandler{
        TextView textview_title,textview_time,textview_content;
        GridView imageview;
    }
}
