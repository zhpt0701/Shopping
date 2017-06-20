package com.example.cloudAndPurchasing.adapter.adaptercloud;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.activity.activitycloud.activitythis.ActivityAddress;
import com.example.cloudAndPurchasing.kind.AddressGoods;
import com.example.cloudAndPurchasing.tiltle.TeaskBar;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2016/4/29 0029.
 */
public class AdapterAddress extends BaseAdapter{
    /**
     * 变量参数
     * @param arraylist1
     * @param context
     * @param addressonclicklistener
     */
    private ArrayList<AddressGoods> arrayList1;
    private Context context;
    private AddressOnClickListener addressOnClickListener;
    HashMap<String,Boolean> states=new HashMap<String,Boolean>();

    /**
     * 构造方法参数传递
     * @param activityAddress
     * @param arrayList
     * @param addressOnClickListener1
     */
    public AdapterAddress(Context activityAddress, ArrayList<AddressGoods> arrayList,AddressOnClickListener addressOnClickListener1) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.addresslayoutitem,null);
            viewHandler.textView_name = (TextView)convertView.findViewById(R.id.textview_nameaddress);
            viewHandler.textView_phone = (TextView)convertView.findViewById(R.id.textview_addresshpone);
            viewHandler.textView_content = (TextView)convertView.findViewById(R.id.textview_shsppingaddress);
            viewHandler.checkBox = (RadioButton)convertView.findViewById(R.id.checkbox_default);
            viewHandler.button_editor = (Button)convertView.findViewById(R.id.button_editoraddress);
            viewHandler.button_delete = (Button)convertView.findViewById(R.id.button_deleteaddress);
            convertView.setTag(viewHandler);
        }else {
            viewHandler = (ViewHandler)convertView.getTag();
        }
        final RadioButton radio=(RadioButton) convertView.findViewById(R.id.checkbox_default);
        viewHandler.checkBox = radio;
        AddressGoods addressGoods = arrayList1.get(position);
        viewHandler.textView_name.setText(addressGoods.getName());
        viewHandler.textView_content.setText(addressGoods.getAddress());
        viewHandler.textView_phone.setText(addressGoods.getPhones());
        viewHandler.button_delete.setOnClickListener(addressOnClickListener);
        viewHandler.button_editor.setOnClickListener(addressOnClickListener);
        viewHandler.checkBox.setOnClickListener(addressOnClickListener);
        viewHandler.checkBox.setTag(position);
        viewHandler.button_editor.setTag(position);
        viewHandler.button_delete.setTag(position);
        viewHandler.checkBox.setChecked(addressGoods.getIsDefault());
//        boolean res=false;
//        if(states.get(String.valueOf(position)) == null || states.get(String.valueOf(position))== false){
//            res=false;
//            states.put(String.valueOf(position), false);
//        } else{
//            res = true;
//        }
//        viewHandler.checkBox.setChecked(res);
        return convertView;
    }

    public void updateviewite(ListView listView, Integer tag) {
        int vb = listView.getFirstVisiblePosition();
        if (tag-vb >= 0){
            View view1 = listView.getChildAt(tag-vb);
            ViewHandler handler1 = (ViewHandler)view1.getTag();
            handler1.checkBox = (RadioButton)view1.findViewById(R.id.checkbox_default);

            for (int c = 0;c<arrayList1.size();c++){
                arrayList1.get(c).setDefault(false);
            }
            arrayList1.get(tag).setDefault(true);
            AdapterAddress.this.notifyDataSetChanged();
        }
    }

    /**
     *adapter优化
     */
    class ViewHandler{
        RadioButton checkBox;
        TextView textView_name,textView_phone,textView_content;
        Button button_editor,button_delete;
    }
    //抽象类
    public static abstract class AddressOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            addressOnClickListener((Integer)v.getTag(),v);
        }
        //抽象方法
        protected abstract void addressOnClickListener(Integer tag, View v);
    }
}
