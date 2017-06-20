package com.example.cloudAndPurchasing.adapter.adaptercloud;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.fragment.fragmentcloud.FragmentPublish;
import com.example.cloudAndPurchasing.http.HttpApi;
import com.example.cloudAndPurchasing.kind.Image;
import com.example.cloudAndPurchasing.kind.PurchaseDateil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/5 0005.
 */
public class AdapterPublish extends BaseAdapter{
    private Context context;
    private ArrayList<PurchaseDateil> arrayList;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;
    public AdapterPublish(ImageLoader imageLoader1,DisplayImageOptions options1,Context fragmentPublish, ArrayList<PurchaseDateil> arrayList1) {
        context = fragmentPublish;
        arrayList = arrayList1;
        imageLoader = imageLoader1;
        options = options1;
    }

    @Override
    public int getCount() {
        if (arrayList != null){
            return arrayList.size();
        }
        return 0;
    }

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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PurchaseDateil purchaseDateil = arrayList.get(position);
        ViewHandler viewHandler;
        if (convertView == null){
            viewHandler = new ViewHandler();
            convertView = LayoutInflater.from(context).inflate(R.layout.adapterpulishiyem,null);
            viewHandler.textView = (TextView)convertView.findViewById(R.id.textview_publishgoodsname);
            viewHandler.textView_name = (TextView)convertView.findViewById(R.id.textview_huodezhi);
            viewHandler.textView_Indiana = (TextView)convertView.findViewById(R.id.textview_Indiana);
            viewHandler.textView_man_time = (TextView)convertView.findViewById(R.id.textview_man_time);
            viewHandler.imageView = (ImageView)convertView.findViewById(R.id.imagebutton_print);
            convertView.setTag(viewHandler);
        }else {
            viewHandler = (ViewHandler)convertView.getTag();
        }
        imageLoader.displayImage(HttpApi.tu_ool+purchaseDateil.getMainImg(),viewHandler.imageView,options);
        viewHandler.textView.setText("(第"+purchaseDateil.getPNumber()+"期)"+purchaseDateil.getTitle());
        SpannableString stringBuilder = new SpannableString("获得者: "+purchaseDateil.getWinner());
        stringBuilder.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.blue)),5,5+purchaseDateil.getWinner().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        viewHandler.textView_name.setText(stringBuilder);
        viewHandler.textView_man_time.setText("本期夺宝: "+purchaseDateil.getTotalCount()+"人次");
        SpannableString stringBuilder1 = new SpannableString("幸运码: "+purchaseDateil.getLuckyNumber());
        stringBuilder1.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.tiltle)),5,5+purchaseDateil.getLuckyNumber().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        viewHandler.textView_Indiana.setText(stringBuilder1);
        return convertView;
    }
    class ViewHandler{
        TextView textView,textView_name,textView_man_time,textView_Indiana;
        ImageView imageView;
    }
}
