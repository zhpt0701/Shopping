package com.example.cloudAndPurchasing.adapter.adaptersurpired;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.activity.activitysurpired.ActivityCount;
import com.example.cloudAndPurchasing.kind.Count;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/13 0013.
 */
public class AdapterCount extends BaseAdapter{
    private Context context;
    private ArrayList<Count> arrayList1;
    public AdapterCount(Context activityCount, ArrayList<Count> arrayList) {
        context = activityCount;
        arrayList1 = arrayList;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.adaptercountitem,null);
            viewHandler.textView = (TextView)convertView.findViewById(R.id.textview_zhogjianghaoma);
            viewHandler.textView_ol = (TextView)convertView.findViewById(R.id.textview_zhongjiangname);
            convertView.setTag(viewHandler);
        }else {
            viewHandler = (ViewHandler)convertView.getTag();
        }
        Count count = arrayList1.get(position);
        viewHandler.textView_ol.setText(count.getCountname());
        if (position>4&&position<(arrayList1.size()-5)) {
            SpannableString spannableString = new SpannableString(count.getCounttime() + "->" + count.getCountnumber());
            spannableString.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.tiltle)),count.getCounttime().length(),count.getCounttime().length()+2+count.getCountnumber().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            viewHandler.textView.setText(spannableString);
        }else {
            SpannableString spannableString = new SpannableString(count.getCounttime() + "->" + count.getCountnumber());
            spannableString.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.huise)),count.getCounttime().length(),count.getCounttime().length()+2+count.getCountnumber().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            viewHandler.textView.setText(spannableString);
        }
        return convertView;
    }
    class ViewHandler{
        TextView textView,textView_ol;
    }
}
