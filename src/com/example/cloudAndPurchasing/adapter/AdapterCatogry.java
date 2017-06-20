package com.example.cloudAndPurchasing.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.kind.Category;
import com.example.cloudAndPurchasing.tiltle.TeaskBar;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/3/25 0025.
 */
public class AdapterCatogry extends BaseAdapter{
    private ArrayList<Category> arrayList;
    private Context context;
    private TextView textView;
    public AdapterCatogry(Context context,ArrayList<Category> arrayList){
        this.arrayList = arrayList;
        this.context = context;
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
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.categroyitemlayout,null);
        }
        textView = (TextView)convertView.findViewById(R.id.textview_categroy);
        Category category = arrayList.get(position);
        textView.setText(category.getCategoryname());
        return convertView;
    }
}
