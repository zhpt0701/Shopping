package com.example.cloudAndPurchasing.adapter.adaptercloud;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.kind.City;
import com.example.cloudAndPurchasing.kind.Province;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/1 0001.
 */
public class AdapterCityPopup extends BaseAdapter{
    private Context context;
    private ArrayList<Province> arrayList;
    public AdapterCityPopup(Context context1,ArrayList<Province> arrayList1){
        context = context1;
        arrayList1 = arrayList1;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.adaptercitypopuplayout,null);
        }
        Spinner spinner = (Spinner)convertView.findViewById(R.id.spinner_province);
        return convertView;
    }
}
