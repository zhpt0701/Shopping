package com.example.cloudAndPurchasing.adapter.adaptermain;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.http.HttpApi;
import com.example.cloudAndPurchasing.imageloder.Imageloderinit;
import com.example.cloudAndPurchasing.kind.Goods;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/13 0013.
 */
public class AdapterAddGoods extends BaseAdapter{
    private Context context1;
    private ArrayList<Goods> arrayList1;
    private AddOnClickLisenter addOnClickLisenter;
    private ImageLoader imageLoader1;
    private DisplayImageOptions options1;
    public AdapterAddGoods(ImageLoader imageLoader,DisplayImageOptions options,Context context,ArrayList<Goods> arrayList,AddOnClickLisenter addOnClickLisenter1){
        context1 = context;
        arrayList1 = arrayList;
        imageLoader1 = imageLoader;
        options1 = options;
        addOnClickLisenter = addOnClickLisenter1;
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
            convertView = LayoutInflater.from(context1).inflate(R.layout.adapteraddgoodsitem,null);
            viewHandler.button = (Button)convertView.findViewById(R.id.button_jiarufangjian);
            viewHandler.imageView = (ImageView)convertView.findViewById(R.id.imageview_goodsphoto);
            viewHandler.textView = (TextView)convertView.findViewById(R.id.textview_addgoodsallpople);
            viewHandler.textviewname = (TextView)convertView.findViewById(R.id.textview_shoppingaddname);
            viewHandler.textView_ol = (TextView)convertView.findViewById(R.id.textview_addgoodssuplespople);
            viewHandler.progressBar = (ProgressBar)convertView.findViewById(R.id.progressbar_addgoods_ool);
            convertView.setTag(viewHandler);
        }else {
            viewHandler = (ViewHandler)convertView.getTag();
            viewHandler.progressBar = new ProgressBar(context1);
        }
        Goods goods = arrayList1.get(position);
        viewHandler.button.setOnClickListener(addOnClickLisenter);
        viewHandler.button.setTag(position);
        viewHandler.textviewname.setText("(第"+goods.getGoodspnumber()+"期)"+goods.getGoodsname());
        viewHandler.progressBar.setMax(Integer.parseInt(goods.getGoodsallpople()));
        viewHandler.progressBar.setProgress(0);
        viewHandler.progressBar.setProgress(Integer.parseInt(goods.getGoodsallpople())-Integer.parseInt(goods.getGoods_surplus()));
        viewHandler.textView.setText("总需:" + goods.getGoodsallpople() + "人次");
        viewHandler.textView_ol.setText("剩余:" + goods.getGoods_surplus() + "人次");
        imageLoader1.displayImage(goods.getImagepath(), viewHandler.imageView, options1);
        return convertView;
    }
    class ViewHandler{
        TextView textView,textView_ol,textviewname;
        ProgressBar progressBar;
        Button button;
        ImageView imageView;
    }
    public static abstract class AddOnClickLisenter implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            addOnClickLisenter((Integer)v.getTag(),v);
        }

        protected abstract void addOnClickLisenter(Integer tag, View v);
    }
}
