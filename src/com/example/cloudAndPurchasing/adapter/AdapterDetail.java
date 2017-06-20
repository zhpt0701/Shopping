package com.example.cloudAndPurchasing.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.http.HttpApi;
import com.example.cloudAndPurchasing.http.Numbers;
import com.example.cloudAndPurchasing.kind.Goods;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/3/25 0025.
 */
public class  AdapterDetail extends BaseAdapter {
    /**
     * 自定义变量；
     * @param context 上下文传递数据
     * @param arraylist 接收传递数据
     * @param dataonclicklistner 自定义onclick点击事件
     * @param num 接收string参数
     */
    private Context context;
    private ArrayList<Goods> arrayList;
    private DataOnclicklistner dataOnclicklistner;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;
    private String num;

    /**
     * 构造方法传递参数
     * @param code
     * @param context
     * @param arrayList
     * @param dataOnclicklistner
     * @param imageLoader
     * @param options
     */
    public AdapterDetail(String code,Context context,ArrayList<Goods> arrayList,DataOnclicklistner dataOnclicklistner,ImageLoader imageLoader,DisplayImageOptions options){
        this.context = context;
        this.arrayList = arrayList;
        this.dataOnclicklistner = dataOnclicklistner;
        this.imageLoader = imageLoader;
        this.options = options;
        num = code;
    }
    @Override
    public int getCount() {
        if (arrayList != null){
            return arrayList.size();
        }
        return 0;
    }

    /**
     * item postion
     * @param position
     * @return
     */
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

    /**
     * view 复用(初始化)
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        viewHandler viewhandler;
        Goods goods = arrayList.get(position);
        //convertview item布局初始化
        if (convertView == null){
            viewhandler = new viewHandler();
            convertView = LayoutInflater.from(context).inflate(R.layout.goodsitemlayout,null);
            viewhandler.linearLayout = (LinearLayout)convertView.findViewById(R.id.linearlayout_xiangou);
            viewhandler.textView = (TextView)convertView.findViewById(R.id.textview_shangpinjianjie);
            viewhandler.textView_xiangou = (TextView)convertView.findViewById(R.id.textview_xiangourenci);
            viewhandler.textView_zongxu = (TextView)convertView.findViewById(R.id.textveiw_zongxushu);
            viewhandler.textView_shengyu = (TextView)convertView.findViewById(R.id.textveiw_shengyushu);
            viewhandler.imageButton = (ImageButton)convertView.findViewById(R.id.imagebutton_gwc);
            viewhandler.progressBar = (ProgressBar)convertView.findViewById(R.id.progressbar_ol);
            viewhandler.imageView = (ImageView)convertView.findViewById(R.id.imageview_shangpin_ol);
            viewhandler.imageView_ol = (ImageView)convertView.findViewById(R.id.imagebutton_xiangou_ol);
            viewhandler.imageView_olo = (ImageView)convertView.findViewById(R.id.imagebutton_shiyuan_ol_ol);
            viewhandler.textView_ol = (TextView)convertView.findViewById(R.id.textivew_pnumber);
            convertView.setTag(viewhandler);
        }else {
            viewhandler = (viewHandler)convertView.getTag();
            viewhandler.progressBar = new ProgressBar(context);
        }
        viewhandler.progressBar = (ProgressBar)convertView.findViewById(R.id.progressbar_ol);
        viewhandler.imageButton.setOnClickListener(dataOnclicklistner);
        viewhandler.imageButton.setTag(position);
        viewhandler.textView_ol.setText("(第"+goods.getGoodspnumber()+"期)");
        viewhandler.textView.setText(goods.getGoodsname());
        /**
         * 控制限购图标显隐
         */
        if (Integer.parseInt(goods.getGoodsxian())>0){
            viewhandler.linearLayout.setVisibility(View.VISIBLE);
            viewhandler.imageView_ol.setVisibility(View.VISIBLE);
            viewhandler.textView_xiangou.setText(goods.getGoodsxian());
        }else {
            viewhandler.linearLayout.setVisibility(View.GONE);
            viewhandler.imageView_ol.setVisibility(View.GONE);
        }
        if (num.equals(String.valueOf(Numbers.THREE))){
            viewhandler.imageView_olo.setVisibility(View.VISIBLE);
        }else {
            viewhandler.imageView_olo.setVisibility(View.GONE);
        }
        viewhandler.textView.setText(goods.getGoodsname());
        imageLoader.displayImage(goods.getImagepath(), viewhandler.imageView, options);
        viewhandler.progressBar.setProgress(0);
        viewhandler.progressBar.setMax(Integer.parseInt(goods.getGoodsallpople()));
        viewhandler.progressBar.setProgress(Integer.parseInt(goods.getGoodsallpople())-Integer.parseInt(goods.getGoods_surplus()));
        viewhandler.textView_zongxu.setText(goods.getGoodsallpople());
        viewhandler.textView_shengyu.setText(goods.getGoods_surplus());
        return convertView;
    }

    /**
     * adapter 优化
     * @param
     */
     class viewHandler{
        TextView textView,textView_xiangou,textView_zongxu,textView_shengyu,textView_ol;
        LinearLayout linearLayout;
        //progressbar 进度条
        ProgressBar progressBar;
        ImageButton imageButton;
        ImageView imageView,imageView_ol,imageView_olo;
    }

    /**
     * 抽象类
     *
     */
    public static abstract class DataOnclicklistner implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            dataOnclicklistner((Integer) v.getTag(), v);
        }
        //抽象onclick方法
        protected abstract void dataOnclicklistner(Integer tag, View v);
    }
}
