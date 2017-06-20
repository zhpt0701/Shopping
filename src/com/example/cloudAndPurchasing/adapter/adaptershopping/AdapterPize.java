package com.example.cloudAndPurchasing.adapter.adaptershopping;

import android.content.Context;
import android.content.Intent;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.adapter.adaptercloud.AdapterGridview;
import com.example.cloudAndPurchasing.kind.Pize;
import com.example.cloudAndPurchasing.tiltle.TeaskBar;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/6/3 0003.
 */
public class AdapterPize extends BaseAdapter{
    private ArrayList<Pize> arrayList;
    private Context context;
    private SelectLuckNumberOnclick selectLuckNumberOnclick;
    /**
     * 构造方法参数传递
     * @param context
     * @param arrayList
     * @param selectLuckNumberOnclick
     */
    public AdapterPize(Context context,ArrayList<Pize> arrayList,SelectLuckNumberOnclick selectLuckNumberOnclick){
        this.context = context;
        this.arrayList = arrayList;
        this.selectLuckNumberOnclick = selectLuckNumberOnclick;
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
        Pize pize = arrayList.get(position);
        ViewHandler viewHandler;
        if (convertView == null){
            viewHandler = new ViewHandler();
            convertView = LayoutInflater.from(context).inflate(R.layout.adapterpizelayout,null);
             viewHandler.textView_name= (TextView)convertView.findViewById(R.id.textview_jianpin_name);
            viewHandler.textView_number = (TextView)convertView.findViewById(R.id.textview_jianpin_renci);
//            viewHandler.gridView = (GridView)convertView.findViewById(R.id.gridview_luckynumber);
            viewHandler.btn = (Button)convertView.findViewById(R.id.btn_luckNumber);
            convertView.setTag(viewHandler);
        }else {
            viewHandler = (ViewHandler)convertView.getTag();
        }
        viewHandler.textView_name.setText(pize.getPeriodsTitle());
        SpannableString spannableString = new SpannableString("共参与"+pize.getCount()+"人次");
        spannableString.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.tiltle)),3,3+String.valueOf(pize.getCount()).length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        viewHandler.textView_number.setText(spannableString);
        viewHandler.btn.setOnClickListener(selectLuckNumberOnclick);
        viewHandler.btn.setTag(position);
//        if (pize.getLuckyNumbers()!= null){
//            Adapterluckynumber adapterluckynumber = new Adapterluckynumber(context,pize.getLuckyNumbers());
//            viewHandler.gridView.setAdapter(adapterluckynumber);
//        }
        return convertView;
    }
    class ViewHandler{
        GridView gridView;
        TextView textView_name,textView_number;
        Button btn;
    }
    //抽象类
    public static abstract class SelectLuckNumberOnclick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            selectLuckNumberOnclick((Integer)v.getTag(),v);
        }
        //抽象方法
        protected abstract void selectLuckNumberOnclick(Integer tag, View v);
    }
}
