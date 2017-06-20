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
import com.example.cloudAndPurchasing.kind.Count;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/14 0014.
 */
public class AdapterWinningPopup extends BaseAdapter {
    private Context context;
    private ArrayList<Count> arrayList1;
    private String luck;
    public AdapterWinningPopup(Context context1, ArrayList<Count> arrayList) {
        context = context1;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.adapterwinngitem,null);
            viewHandler.textView = (TextView)convertView.findViewById(R.id.textview_winngpopup);
            convertView.setTag(viewHandler);
        }else {
            viewHandler = (ViewHandler)convertView.getTag();
        }
        Count count = arrayList1.get(position);
        viewHandler.textView.setText(count.getCountnumber());
        return convertView;
    }
    class ViewHandler{
        TextView textView;
    }
}
