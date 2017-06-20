package com.example.cloudAndPurchasing.adapter.adaptersurpired;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.activity.activitysurpired.ActivityOldTimey;
import com.example.cloudAndPurchasing.adapter.AdapterDetail;
import com.example.cloudAndPurchasing.http.HttpApi;
import com.example.cloudAndPurchasing.kind.Record;
import com.example.cloudAndPurchasing.mothendsol.Tailoring;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/12 0012.
 */
public class AdapterOldTimey extends BaseAdapter{
    private Context context;
    private ArrayList<Record> arrayList1;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;
    public AdapterOldTimey(ImageLoader imageLoader1,DisplayImageOptions options1, Context activityOldTimey, ArrayList<Record> arrayList) {
        context = activityOldTimey;
        arrayList1 = arrayList;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.adapteroldtimeyitem,null);
            viewHandler.textView_address = (TextView)convertView.findViewById(R.id.textview_address_green);
            viewHandler.textView_qi = (TextView)convertView.findViewById(R.id.textview_oldtimey_qihao);
            viewHandler.textView_name = (TextView)convertView.findViewById(R.id.textview_oldtimey_nickname);
            viewHandler.textView_ip = (TextView)convertView.findViewById(R.id.textview_oldtimey_ip);
            viewHandler.textView_luck = (TextView)convertView.findViewById(R.id.textview_oldtimey_lucknumber);
            viewHandler.textView_people = (TextView)convertView.findViewById(R.id.textview_oldtimey_poplenumber);
            viewHandler.imageView = (ImageView)convertView.findViewById(R.id.imageview_people_photo);
            convertView.setTag(viewHandler);
        }else {
            viewHandler = (ViewHandler)convertView.getTag();
        }
        Record record = arrayList1.get(position);
        if (!TextUtils.isEmpty(record.getRecordaddress())){
            viewHandler.textView_address.setText("("+record.getRecordaddress()+")");
        }
        viewHandler.textView_qi.setText(record.getRecordgoodstimepublish());
        SpannableString spannableString1 = new SpannableString("获奖者:"+record.getWinner());
        spannableString1.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.blue)),4,4+record.getWinner().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        viewHandler.textView_name.setText(spannableString1);
        viewHandler.textView_ip.setText("用户IP:"+record.getIP());
        SpannableString spannableString2 = new SpannableString("幸运号码:"+record.getRecordlucknumber());
        spannableString2.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.tiltle)),5,5+record.getRecordlucknumber().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        viewHandler.textView_luck.setText(spannableString2);
        SpannableString spannableString3 = new SpannableString("本期参与:"+record.getTradingCount()+"人次");
        spannableString3.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.tiltle)),5,5+record.getTradingCount().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        viewHandler.textView_people.setText(spannableString3);
//        if (!record.getRecordimage().equals("null")){
//            byte[] bitmapArray;
//            bitmapArray = Base64.decode(record.getRecordimage(), Base64.DEFAULT);
//            Bitmap bitmap =
//                    BitmapFactory.decodeByteArray(bitmapArray, 0,
//                            bitmapArray.length);
//            Bitmap bitmap1 = Tailoring.phototailoring(bitmap);
//            viewHandler.imageView.setImageBitmap(bitmap1);
//        }
            imageLoader.displayImage(HttpApi.mytu+record.getWinnerid(),viewHandler.imageView,options);

        return convertView;
    }
    class ViewHandler{
        TextView textView_qi,textView_name,textView_luck,textView_people,textView_ip,textView_address;
        ImageView imageView;
    }
}
