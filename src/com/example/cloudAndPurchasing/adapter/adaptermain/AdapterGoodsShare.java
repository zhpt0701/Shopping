package com.example.cloudAndPurchasing.adapter.adaptermain;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.activity.activitymain.ActivitySingle;
import com.example.cloudAndPurchasing.http.HttpApi;
import com.example.cloudAndPurchasing.kind.Image;
import com.example.cloudAndPurchasing.kind.Publish;
import com.example.cloudAndPurchasing.kind.Share;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/15 0015.
 */
public class AdapterGoodsShare extends BaseAdapter{
    private ArrayList<Publish> arrayList1;
    private Context context;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;
    public AdapterGoodsShare(ImageLoader imageLoader1,DisplayImageOptions options1,Context activitySingle, ArrayList<Publish> arrayList) {
        arrayList1 = arrayList;
        context = activitySingle;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.adaptergoodsshareitem,null);
            viewHandler.imageView = (ImageView)convertView.findViewById(R.id.imagebutton_sharedetail_photo);
            viewHandler.textView_name = (TextView)convertView.findViewById(R.id.textview_sharedateilnickname);
            viewHandler.textView_title = (TextView)convertView.findViewById(R.id.textview_share_title);
            viewHandler.textView_content = (TextView)convertView.findViewById(R.id.textview_share_content);
            viewHandler.textView_time = (TextView)convertView.findViewById(R.id.textview_share_time);
            convertView.setTag(viewHandler);
        }else {
            viewHandler = (ViewHandler)convertView.getTag();
        }
        Publish share = arrayList1.get(position);
        try {
            JSONArray jsonArray = new JSONArray(share.getArrayList());
            imageLoader.displayImage(jsonArray.get(0).toString(),viewHandler.imageView,options);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        viewHandler.textView_name.setText(share.getNickname());
        viewHandler.textView_title.setText(share.getTitle());
        viewHandler.textView_content.setText(share.getContent());
        viewHandler.textView_time.setText(share.getTime());
        return convertView;
    }
    class ViewHandler{
        TextView textView_name,textView_title,textView_content,textView_time;
        ImageView imageView;
    }
}
