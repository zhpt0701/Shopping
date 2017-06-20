package com.example.cloudAndPurchasing.fragment.fragmentsurpried;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.adapter.adaptersurpired.AdapterWinningPopup;
import com.example.cloudAndPurchasing.kind.Count;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/7/12 0012.
 */
public class AdapterWinningPopupol extends BaseAdapter {
    private Context context;
    private ArrayList<Count> arrayList1;
    private String luck;
    public AdapterWinningPopupol(Context context1, ArrayList<Count> arrayList,String lucknumber) {
        context = context1;
        arrayList1 = arrayList;
        luck = lucknumber;
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
        if (count.getCountnumber().equals(luck)){
            SpannableString spannableString = new SpannableString(count.getCountnumber());
            spannableString.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.tiltle)),0,count.getCountnumber().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            viewHandler.textView.setText(spannableString);
        }else {
            viewHandler.textView.setText(count.getCountnumber());
        }
        return convertView;
    }
    class ViewHandler{
        TextView textView;
    }
}
