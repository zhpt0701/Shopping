package com.example.cloudAndPurchasing.adapter.adaptercloud;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.activity.activitycloud.ActivityIntegral;
import com.example.cloudAndPurchasing.adapter.AdapterDetail;
import com.example.cloudAndPurchasing.kind.Day;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/19 0019.
 */
public class AdapterSgin extends RecyclerView.Adapter<AdapterSgin.ViewHandler>{
    private Context context;
    private ArrayList<Day> arrayList1;
    public AdapterSgin(Context activityIntegral, ArrayList<Day> arrayList) {
        context = activityIntegral;
        arrayList1 = arrayList;
    }

    @Override
    public ViewHandler onCreateViewHolder(ViewGroup viewGroup, int i) {
        View convertView = LayoutInflater.from(context).inflate(R.layout.adapterlayoutitem,viewGroup,false);
        ViewHandler viewHandler = new ViewHandler(convertView);
        viewHandler.textView = (TextView)convertView.findViewById(R.id.textview_day);

        viewHandler.textview_ol = (TextView)convertView.findViewById(R.id.textview_integral);

        return viewHandler;
    }

    @Override
    public void onBindViewHolder(ViewHandler viewHandler, int i) {
        Day day = arrayList1.get(i);
        viewHandler.textView.setText("第" + day.getDay() + "天");
        viewHandler.textview_ol.setText(day.getJifan()+"积分");
    }
    @Override
    public int getItemCount() {
        if (arrayList1 != null){
            return arrayList1.size();
        }
        return 0;
    }
    class ViewHandler extends RecyclerView.ViewHolder{
        public ViewHandler(View itemView) {
            super(itemView);
        }
        TextView textView,textview_ol;
        ImageView imageView;
    }
}
