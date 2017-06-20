package com.example.cloudAndPurchasing.popupwindows;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.adapter.adaptermain.AdapterCityPopupone;
import com.example.cloudAndPurchasing.http.Numbers;
import com.example.cloudAndPurchasing.kind.TZone;
import com.example.cloudAndPurchasing.sqlliste.DataStore;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/15 0015.
 */
public class CityPopupWindows extends PopupWindow implements View.OnClickListener{
    private Context context1;
    private ArrayList<TZone> arrayList,arrayList1;
    private View view;
    private TextView textView;
    private GridView gridView;
    private Button button,button_ol;
    private AdapterCityPopupone adapterCity;
    public CityPopupWindows(final Context context, final int citysort,Button button1){
        button_ol = button1;
        context1 = context;
        arrayList = new ArrayList<TZone>();


        view = LayoutInflater.from(context1).inflate(R.layout.citypopuplayoutitem,null);
        setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        setContentView(view);

        setOutsideTouchable(true);
        setFocusable(true);
        setClippingEnabled(true);
        setBackgroundDrawable(new BitmapDrawable());
        textView = (TextView)view.findViewById(R.id.textview_cityol);
        gridView = (GridView)view.findViewById(R.id.gridview_cityol);
        button = (Button)view.findViewById(R.id.button_cityqiehuan);
        textView.setText("西安");
        new Thread(new Runnable() {
            @Override
            public void run() {
                arrayList = DataStore.Zonedataselect(context, citysort);
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("zone",arrayList);
                message.what = Numbers.ONE;
                message.setData(bundle);
                handler.sendMessage(message);
            }
        }).start();
        button.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

        }
    }
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

        }
    };
    public void shows(View v) {
        showAsDropDown(v);
    }
}
