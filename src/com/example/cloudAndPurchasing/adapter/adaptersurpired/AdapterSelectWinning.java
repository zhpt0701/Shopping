package com.example.cloudAndPurchasing.adapter.adaptersurpired;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.http.HttpApi;
import com.example.cloudAndPurchasing.kind.PurchaseDateil;
import com.example.cloudAndPurchasing.kind.Winning;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/13 0013.
 */
public class AdapterSelectWinning extends BaseAdapter{
    private Context context;
    private ArrayList<Winning> arrayList1;
    private SwOnClickListener swOnClickListener1;
    private String name;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;
    public AdapterSelectWinning(ImageLoader imageLoader1,DisplayImageOptions options1,String name1,Context activity, ArrayList<Winning> arrayList,SwOnClickListener swOnClickListener) {
        context = activity;
        arrayList1 = arrayList;
        swOnClickListener1 = swOnClickListener;
        name = name1;
        imageLoader = imageLoader1;
        options= options1;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.adapterswitem,null);
            viewHandler.textView_goodsname = (TextView)convertView.findViewById(R.id.textview_swgoodsname);
            viewHandler.textView_peoplenumber = (TextView)convertView.findViewById(R.id.textview_swnumber);
            viewHandler.textView_lucky = (TextView)convertView.findViewById(R.id.textview_sw_lucky);
            viewHandler.textView_time = (TextView)convertView.findViewById(R.id.textview_sw_time);
            viewHandler.button = (Button)convertView.findViewById(R.id.button_swselect);
            viewHandler.imageView = (ImageView)convertView.findViewById(R.id.imagebutton_sw);
            convertView.setTag(viewHandler);

        }else {
            viewHandler = (ViewHandler)convertView.getTag();
        }
        Winning winning = arrayList1.get(position);
        imageLoader.displayImage(winning.getShowImg(),viewHandler.imageView,options);
        viewHandler.textView_goodsname.setText("(第"+winning.getPnumber()+"期)"+winning.getWinninggoodsname());
        viewHandler.textView_time.setText("揭晓时间:"+winning.getWinningallman_time());
        viewHandler.textView_peoplenumber.setText("总参与:"+winning.getTotalCount()+"人次");
        viewHandler.textView_lucky.setText(winning.getWinningluckCode());
        viewHandler.button.setOnClickListener(swOnClickListener1);
        viewHandler.button.setTag(position);
        return convertView;
    }
    class ViewHandler{
        TextView textView_goodsname,textView_peoplenumber,textView_lucky,textView_time;
        ImageView imageView;
        Button button;
    }
    public static abstract class SwOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            swOnClickListener((Integer)v.getTag(),v);
        }

        protected abstract void swOnClickListener(Integer tag, View v);
    }
}