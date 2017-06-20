package com.example.cloudAndPurchasing.adapter.adaptercloud;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.activity.activitycloud.activitythis.ActivityLevel;
import com.example.cloudAndPurchasing.kind.Image;
import com.example.cloudAndPurchasing.kind.Level;
import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/28 0028.
 */
public class AdapterLevel extends BaseAdapter{
    private Context context;
    private ArrayList<Level> arrayList1;
    public AdapterLevel(Context activityLevel, ArrayList<Level> arrayList) {
        context = activityLevel;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.adapterlevelitem,null);
            viewHandler.textView_ex = (TextView)convertView.findViewById(R.id.textview_levejingyan);
            viewHandler.textView_name = (TextView)convertView.findViewById(R.id.textview_levename);
            viewHandler.imageView = (ImageView)convertView.findViewById(R.id.imageview_levelthephoto);
            convertView.setTag(viewHandler);
        }else {
            viewHandler = (ViewHandler)convertView.getTag();
        }
        Level level = arrayList1.get(position);
        viewHandler.textView_name.setText(level.getLevelname());
        viewHandler.textView_ex.setText(level.getJiangyan());
        viewHandler.imageView.setImageBitmap(level.getLevelphoto());
        return convertView;
    }
    class ViewHandler{
        TextView textView_name,textView_ex;
        ImageView imageView;
    }
}
