package com.example.cloudAndPurchasing.adapter.adaptermain;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.http.HttpApi;
import com.example.cloudAndPurchasing.kind.Goods;
import com.example.cloudAndPurchasing.utils.Arithmetic;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/15 0015.
 */
public class AdapterClassity extends BaseAdapter{
    private Context context;
    private ArrayList<Goods> arrayList1;
    private ACOnClickListener acOnClickListener;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;
    public AdapterClassity(Context activity, ArrayList<Goods> arrayList,ACOnClickListener acOnClickListener1,ImageLoader imageLoader1,DisplayImageOptions options1) {
        context = activity;
        arrayList1 = arrayList;
        acOnClickListener = acOnClickListener1;
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
        ViewHandler viewHandler;
        if (convertView == null){
            viewHandler = new ViewHandler();
            convertView = LayoutInflater.from(context).inflate(R.layout.adapterclassityitem,null);
            viewHandler.textView_goodsname = (TextView)convertView.findViewById(R.id.textview_fragmentmaingoodsname);
            viewHandler.textview_jingu = (TextView)convertView.findViewById(R.id.textview_fragmentmainjindu);
            viewHandler.imageView = (ImageView)convertView.findViewById(R.id.imageview_thisadapterclassitygoodsphotol);
            viewHandler.imageView_ol = (ImageView)convertView.findViewById(R.id.imagebutton_xiangou_main_ol);
            viewHandler.imageView_ool = (ImageView)convertView.findViewById(R.id.imagebutton_shiyuan_ol_main_ol);
            viewHandler.button = (Button)convertView.findViewById(R.id.button_fragmentmainaddcat);
            viewHandler.progressBar = (ProgressBar)convertView.findViewById(R.id.progressbar_fragmentmain);
            convertView.setTag(viewHandler);
        }else {
            viewHandler = (ViewHandler)convertView.getTag();
            viewHandler.progressBar = new ProgressBar(context);
        }
        Goods goods = arrayList1.get(position);
        double result = Arithmetic.div((Integer.parseInt(goods.getGoodsallpople()) - Integer.parseInt(goods.getGoods_surplus())) * 100, Double.parseDouble(goods.getGoodsallpople()));
        viewHandler.progressBar.setProgress(0);
        viewHandler.progressBar.setMax(Integer.parseInt(goods.getGoodsallpople()));
        viewHandler.progressBar.setProgress(Integer.parseInt(goods.getGoodsallpople())-Integer.parseInt(goods.getGoods_surplus()));
        String result_ol = result+"%";
        viewHandler.textview_jingu.setText("当前进度:"+result_ol);
        viewHandler.textView_goodsname.setText("(第"+goods.getGoodspnumber()+"期)"+goods.getGoodsname());
        viewHandler.button.setOnClickListener(acOnClickListener);
        viewHandler.button.setTag(position);
        if (!goods.getGoodsxian().equals("null")){
            if (Integer.parseInt(goods.getGoodsxian())>0){
                viewHandler.imageView_ol.setVisibility(View.VISIBLE);
            }else {
                viewHandler.imageView_ol.setVisibility(View.GONE);
            }
        }else {
            viewHandler.imageView_ol.setVisibility(View.GONE);
        }
        if (goods.getImagepath() != null){
            imageLoader.displayImage(goods.getImagepath(), viewHandler.imageView, options);
        }
        return convertView;
    }
    class ViewHandler{
        TextView textView_goodsname,textview_jingu;
        Button button;
        ImageView imageView,imageView_ol,imageView_ool;
        ProgressBar progressBar;
    }
    public static abstract class ACOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            acOnClickListener((Integer)v.getTag(),v);
        }

        protected abstract void acOnClickListener(Integer tag, View v);
    }
}
