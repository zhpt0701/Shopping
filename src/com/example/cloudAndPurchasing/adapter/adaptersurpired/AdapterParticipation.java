package com.example.cloudAndPurchasing.adapter.adaptersurpired;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.http.HttpApi;
import com.example.cloudAndPurchasing.kind.PurchaseDateil;
import com.example.cloudAndPurchasing.utils.Arithmetic;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/13 0013.
 */
public class AdapterParticipation extends BaseAdapter{
    private Context context;
    private ArrayList<PurchaseDateil> array;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;
    private ParticiationOnClickListener particiationOnClickListener;
    public AdapterParticipation(ImageLoader imageLoader1,DisplayImageOptions options1,Context activity, ArrayList<PurchaseDateil> p1,ParticiationOnClickListener particiationOnClickListener1) {
        particiationOnClickListener = particiationOnClickListener1;
        context = activity;
        imageLoader = imageLoader1;
        options = options1;
        array = p1;
    }

    @Override
    public int getCount() {
        if (array != null){
            return array.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (array != null){
            return array.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHandler viewhandler;
        if (convertView == null){
            viewhandler = new ViewHandler();
            convertView = LayoutInflater.from(context).inflate(R.layout.adapterparticipationitem,null);
            viewhandler.textView_goodsname = (TextView)convertView.findViewById(R.id.textview_participationgoodsname);
            viewhandler.textview_number = (TextView)convertView.findViewById(R.id.textview_participationnumber);
            viewhandler.textView_name = (TextView)convertView.findViewById(R.id.textview_participation_name);
            viewhandler.textview_luck = (TextView)convertView.findViewById(R.id.textview_participation_lucknumber);
            viewhandler.imageView = (ImageView)convertView.findViewById(R.id.imagebutton_participation);
            viewhandler.textview_jidu = (TextView)convertView.findViewById(R.id.textview_adapterparticition);
            viewhandler.textView_join = (TextView)convertView.findViewById(R.id.textview_participation);
            viewhandler.button = (Button)convertView.findViewById(R.id.button_partiction_cat);
            viewhandler.button_ol = (Button)convertView.findViewById(R.id.button_partiction_select);
            viewhandler.progressBar = (ProgressBar)convertView.findViewById(R.id.progressbar_participationpople);
            viewhandler.linearLayout = (LinearLayout)convertView.findViewById(R.id.Linearlayout_bianhuan);
            convertView.setTag(viewhandler);

        }else {
            viewhandler = (ViewHandler)convertView.getTag();
        }
        PurchaseDateil purchaseDateil = array.get(position);
        imageLoader.displayImage(HttpApi.tu_ool+purchaseDateil.getMainImg(), viewhandler.imageView, options);
        viewhandler.textView_goodsname.setText("(第"+purchaseDateil.getPNumber()+"期)"+purchaseDateil.getTitle());
        if (!purchaseDateil.getWinner().equals("null")){
            viewhandler.textView_name.setVisibility(View.VISIBLE);
            viewhandler.textview_luck.setVisibility(View.VISIBLE);
            SpannableString style_OL = new SpannableString("幸运码: "+purchaseDateil.getLuckyNumber());
            style_OL.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.tiltle)), 5, 5+purchaseDateil.getLuckyNumber().length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //设置指定位置文字的颜色
            SpannableString style = new SpannableString("获得者: "+purchaseDateil.getWinner());
            style.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.blue)), 5, 5+purchaseDateil.getWinner().length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //设置指定位置文字的颜色
            viewhandler.textview_luck.setText(style_OL);
            viewhandler.textView_name.setText(style);
            viewhandler.linearLayout.setVisibility(View.GONE);
        }else {
            viewhandler.linearLayout.setVisibility(View.VISIBLE);
            double baifen = Arithmetic.div(100*(purchaseDateil.getTotalCount()-purchaseDateil.getSurpluspople()),purchaseDateil.getTotalCount());
            String jidu = baifen+"%";
            viewhandler.textview_jidu.setText("当前进度: "+jidu);
            viewhandler.textView_join.setVisibility(View.GONE);
//            viewhandler.textView_join.setText("本次参与: "+purchaseDateil.getTradingCount()+"人次");
            viewhandler.progressBar.setProgress(0);
            viewhandler.progressBar.setMax(purchaseDateil.getTotalCount());
            viewhandler.progressBar.setProgress(purchaseDateil.getTotalCount()-purchaseDateil.getSurpluspople());
            viewhandler.button.setOnClickListener(particiationOnClickListener);
            viewhandler.button_ol.setOnClickListener(particiationOnClickListener);
            viewhandler.button_ol.setTag(position);
            viewhandler.button.setTag(position);
            viewhandler.textView_name.setVisibility(View.GONE);
            viewhandler.textview_luck.setVisibility(View.GONE);
        }
        if (Integer.parseInt(purchaseDateil.getTradingCount())> 0){
            viewhandler.textview_number.setVisibility(View.VISIBLE);
            viewhandler.textview_number.setText("本期参与: "+(purchaseDateil.getTotalCount()-purchaseDateil.getSurpluspople())+"人次");
        }else {
            viewhandler.textview_number.setVisibility(View.GONE);
        }
        return convertView;
    }
    class ViewHandler{
        TextView textView_name,textView_goodsname,textview_number
                ,textview_luck,textView_join,textview_jidu;
        Button button,button_ol;
        ProgressBar progressBar;
        ImageView imageView;
        LinearLayout linearLayout;
    }
    public static abstract class ParticiationOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            particiationOnClickListener((Integer)v.getTag(),v);
        }

        protected abstract void particiationOnClickListener(Integer tag, View v);
    }
}
