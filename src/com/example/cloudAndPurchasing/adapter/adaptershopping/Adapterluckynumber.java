package com.example.cloudAndPurchasing.adapter.adaptershopping;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.tiltle.Background;

import java.util.List;

/**
 * Created by Administrator on 2016/6/3 0003.
 */
public class Adapterluckynumber extends BaseAdapter{
    private List list1;
    private Context context1;
    public Adapterluckynumber(Context context,List list){
        list1 = list;
        context1 = context;
    }
    @Override
    public int getCount() {
        if (list1 != null){
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
            convertView = LayoutInflater.from(context1).inflate(R.layout.adapterlucknumberlayout,null);
            viewHandler.textView = (TextView)convertView.findViewById(R.id.textview_luckynumber);
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
