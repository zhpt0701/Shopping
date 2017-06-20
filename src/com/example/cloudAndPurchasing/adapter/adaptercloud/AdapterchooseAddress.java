package com.example.cloudAndPurchasing.adapter.adaptercloud;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.kind.AddressGoods;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2016/6/17 0017.
 */
public class AdapterchooseAddress extends BaseAdapter{
    private ArrayList<AddressGoods> arrayList1;
    private Context context;
    private CHooseAddressOnClickListener addressOnClickListener;
    HashMap<String,Boolean> states=new HashMap<String,Boolean>();
    public AdapterchooseAddress(Context activityAddress, ArrayList<AddressGoods> arrayList,CHooseAddressOnClickListener addressOnClickListener1) {
        arrayList1 = arrayList;
        context = activityAddress;
        addressOnClickListener = addressOnClickListener1;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHandler viewHandler;
        if (convertView == null){
            viewHandler = new ViewHandler();
            convertView = LayoutInflater.from(context).inflate(R.layout.chooseactivityitem,null);
            viewHandler.textView_name = (TextView)convertView.findViewById(R.id.textview_choosenameaddress);
            viewHandler.textView_phone = (TextView)convertView.findViewById(R.id.textview_chooseaddresshpone);
            viewHandler.textView_content = (TextView)convertView.findViewById(R.id.textview_chooseshsppingaddress);
            viewHandler.button_delete = (Button)convertView.findViewById(R.id.button_choosedeleteaddress);
            convertView.setTag(viewHandler);
        }else {
            viewHandler = (ViewHandler)convertView.getTag();
        }
        AddressGoods addressGoods = arrayList1.get(position);
        viewHandler.textView_name.setText(addressGoods.getName());
        viewHandler.textView_content.setText(addressGoods.getAddress());
        viewHandler.textView_phone.setText(addressGoods.getPhones());
        viewHandler.button_delete.setOnClickListener(addressOnClickListener);
        viewHandler.button_delete.setTag(position);
        return convertView;
    }

    class ViewHandler{
        RadioButton checkBox;
        TextView textView_name,textView_phone,textView_content;
        Button button_editor,button_delete;
    }
    public static abstract class CHooseAddressOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            addressOnClickListener((Integer)v.getTag(),v);
        }

        protected abstract void addressOnClickListener(Integer tag, View v);
    }
}
