package com.example.cloudAndPurchasing.adapter.adaptershopping;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.http.HttpApi;
import com.example.cloudAndPurchasing.imageloder.Myimageloder;
import com.example.cloudAndPurchasing.kind.Goods;
import com.example.cloudAndPurchasing.kind.Record;
import com.example.cloudAndPurchasing.mothendsol.Tailoring;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/6/15 0015.
 */
public class AdapterParticipate extends BaseAdapter{
    private Context context1;
    private ArrayList<Record> arrayList1;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;
    public AdapterParticipate(ImageLoader imageLoader1,DisplayImageOptions options1,Context context,ArrayList<Record> arrayList){
        arrayList1 = arrayList;
        context1 = context;
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
            convertView = LayoutInflater.from(context1).inflate(R.layout.adapterparticlayout,null);
            viewHandler.textView1 = (TextView)convertView.findViewById(R.id.textview_pname);
            viewHandler.textView2 = (TextView)convertView.findViewById(R.id.textview_pip);
            viewHandler.textView3 = (TextView)convertView.findViewById(R.id.textview_pnumbewr);
            viewHandler.textView4 = (TextView)convertView.findViewById(R.id.textivew_ptimes);
            viewHandler.imageView = (ImageView)convertView.findViewById(R.id.imageview_thisphotopople);
            convertView.setTag(viewHandler);
        }else {
            viewHandler = (ViewHandler)convertView.getTag();
        }
        Record record = arrayList1.get(position);
        viewHandler.textView1.setText(record.getRecordnickname());
        if (!record.getRecordaddress().equals("null")){
            if (!record.getIP().equals("null")){
                viewHandler.textView2.setText("("+record.getRecordaddress()+" IP:"+record.getIP()+")");
            }
        }
        viewHandler.textView3.setText("参与了"+record.getRecordpoplenumber()+"人次");
        viewHandler.textView4.setText(record.getRecordgoodstimepublish());
//        if (record.getRecordimage() != "null"){
//            byte[] bitmapArray;
//            bitmapArray = Base64.decode(record.getRecordimage(), Base64.DEFAULT);
//            Bitmap bitmap =
//                    BitmapFactory.decodeByteArray(bitmapArray, 0,
//                            bitmapArray.length);
//            Bitmap bitmap1 = Tailoring.phototailoring(bitmap);
//            viewHandler.imageView.setImageBitmap(bitmap1);
//        }
        imageLoader.displayImage(HttpApi.yu+record.getRecordimage(),viewHandler.imageView,options);
        return convertView;
    }
    public class ViewHandler{
        ImageView imageView;
        TextView textView1,textView2,textView3,textView4;
    }
}
