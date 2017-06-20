package com.example.cloudAndPurchasing.adapter.adaptercloud;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.http.HttpApi;
import com.example.cloudAndPurchasing.kind.Province;
import com.example.cloudAndPurchasing.kind.PurchaseDateil;
import com.example.cloudAndPurchasing.utils.Arithmetic;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/1 0001.
 */
public class AdapterOngoing extends BaseAdapter{
    private Context context;
    private ArrayList<PurchaseDateil> arrayList1;
    private CatOnclicklistner catOnclicklistner;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;
    public AdapterOngoing(ImageLoader imageLoader1,DisplayImageOptions options1,Context activity, ArrayList<PurchaseDateil> arrayList,CatOnclicklistner catOnclicklistner1) {
        context = activity;
        arrayList1 = arrayList;
        catOnclicklistner = catOnclicklistner1;
        imageLoader = imageLoader1;
        options = options1;
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
        PurchaseDateil purchaseDateil = arrayList1.get(position);
        ViewHandler viewHandler;
        if (convertView == null){
            viewHandler = new ViewHandler();
            convertView = LayoutInflater.from(context).inflate(R.layout.adapterongoingitem,null);
            viewHandler.button = (Button)convertView.findViewById(R.id.button_add_cat);
            viewHandler.textView = (TextView)convertView.findViewById(R.id.textview_ongoingname);
            viewHandler.textView_ol = (TextView)convertView.findViewById(R.id.textview_ongoingjindu);
            viewHandler.imageView = (ImageView)convertView.findViewById(R.id.imageview_ongoing);
            viewHandler.textview_join = (TextView)convertView.findViewById(R.id.textview_canyurenci);
            viewHandler.button_ol = (Button)convertView.findViewById(R.id.button_select_ol);
            viewHandler.progressBar = (ProgressBar)convertView.findViewById(R.id.progressbar_ongoings);
            convertView.setTag(viewHandler);
        }else {
            viewHandler = (ViewHandler)convertView.getTag();
        }
//        int sd = Integer.parseInt(purchaseDateil.getAllpeople())-Integer.parseInt(purchaseDateil.getThispeople());
        double result = Arithmetic.div(100*(purchaseDateil.getTotalCount()-purchaseDateil.getSurpluspople()),purchaseDateil.getTotalCount());
        String result_ol = String.valueOf(result)+"%";
        viewHandler.button.setOnClickListener(catOnclicklistner);
        viewHandler.button_ol.setOnClickListener(catOnclicklistner);
        viewHandler.button_ol.setTag(position);
        viewHandler.button.setTag(position);
        viewHandler.progressBar.setProgress(0);
        viewHandler.progressBar.setMax(purchaseDateil.getTotalCount());
        viewHandler.progressBar.setProgress(purchaseDateil.getTotalCount()-purchaseDateil.getSurpluspople());
        viewHandler.textView_ol.setText("当前进度："+result_ol);
        viewHandler.textView.setText("(第"+purchaseDateil.getPNumber()+"期)"+String.valueOf(purchaseDateil.getTitle()));
        viewHandler.textview_join.setText("参与"+String.valueOf(purchaseDateil.getTotalCount()-purchaseDateil.getSurpluspople())+"人次");
        imageLoader.displayImage(HttpApi.tu_ool+purchaseDateil.getMainImg(),viewHandler.imageView,options);
        return convertView;
    }
    class ViewHandler{
        TextView textView,textView_ol,textView_ool,textview_join;
        ImageView imageView;
        ProgressBar progressBar;
        Button button,button_ol;
    }
    public static abstract class CatOnclicklistner implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            catOnclicklistner((Integer)v.getTag(),v);
        }

        protected abstract void catOnclicklistner(Integer tag, View v);
    }
}
