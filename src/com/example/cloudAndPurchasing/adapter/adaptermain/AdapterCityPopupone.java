package com.example.cloudAndPurchasing.adapter.adaptermain;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.kind.City;
import com.example.cloudAndPurchasing.kind.TZone;
import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/15 0015.
 */
public class AdapterCityPopupone extends BaseAdapter {
    private Context context1;
    private ArrayList<TZone> arrayList1;
    public AdapterCityPopupone(Context context,ArrayList<TZone> arrayList){
        context1 = context;
        arrayList1 = arrayList;
    }

    @Override
    public int getCount() {
        if(arrayList1 != null){
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
            convertView = LayoutInflater.from(context1).inflate(R.layout.adaptercityopupitem,null);
            viewHandler.button = (TextView)convertView.findViewById(R.id.textview_cy);
            convertView.setTag(viewHandler);
        }else {
            viewHandler = (ViewHandler)convertView.getTag();
        }
        TZone tZone = arrayList1.get(position);
        viewHandler.button.setText(tZone.getZonename());
        return convertView;
    }
    class ViewHandler{
        TextView button;
    }
}
