package com.example.cloudAndPurchasing.adapter.adaptercloud;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.activity.activitycloud.activityinstall.ActivityNewNotice;
import com.example.cloudAndPurchasing.kind.Bulltin;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/5/23 0023.
 */
public class AdapterBulltin extends BaseAdapter{
    private Context context;
    private ArrayList<Bulltin> arrayList1;
    public AdapterBulltin(Context activityNewNotice, ArrayList<Bulltin> arrayList) {
        context = activityNewNotice;
        arrayList1 = arrayList;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.adapterbulltinlayout,null);
            viewHandler.textView = (TextView)convertView.findViewById(R.id.textview_bulltincontent);
            viewHandler.textView_ol = (TextView)convertView.findViewById(R.id.textview_bulltintimes);
            convertView.setTag(viewHandler);
        }else {
            viewHandler = (ViewHandler)convertView.getTag();
        }
        Bulltin bulltin = arrayList1.get(position);
        viewHandler.textView.setText(bulltin.getContent());
        viewHandler.textView_ol.setText(bulltin.getCreateTime());
        return convertView;
    }
    public class ViewHandler{
        TextView textView,textView_ol;
    }
}
