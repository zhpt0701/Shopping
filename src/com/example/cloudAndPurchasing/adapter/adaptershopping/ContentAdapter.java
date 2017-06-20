package com.example.cloudAndPurchasing.adapter.adaptershopping;

import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.cloudAndPurchasing.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/7/16 0016.
 */
public class ContentAdapter extends BaseAdapter{
    private Context context;
    private ArrayList<String> list1;
    public ContentAdapter(Context application, ArrayList<String> list) {
        context = application;
        list1 = list;
    }

    @Override
    public int getCount() {
        if (list1.size()>0){
            return list1.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (list1 != null){
            return list1.get(position);
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
            convertView = LayoutInflater.from(context).inflate(R.layout.each_layout,null);
            viewHandler.textView = (TextView)convertView.findViewById(R.id.textview_content_each);
            convertView.setTag(viewHandler);
        }else {
            viewHandler = (ViewHandler)convertView.getTag();
        }
        viewHandler.textView.setText(list1.get(position).toString());
        return convertView;
    }
     class ViewHandler{
         TextView textView;
     }
}
