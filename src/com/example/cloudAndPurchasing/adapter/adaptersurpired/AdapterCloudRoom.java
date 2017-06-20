package com.example.cloudAndPurchasing.adapter.adaptersurpired;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.kind.CloudRoom;
import com.example.cloudAndPurchasing.kind.StringUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/12 0012.
 */
public class AdapterCloudRoom extends BaseAdapter{
    private Context context;
    private ArrayList<CloudRoom.DataEntity> cloudRoom;
    private CloudOnClickListener cloudOnClickListener;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;
    private String uid = null;
    public AdapterCloudRoom(String uid1,Context activity, ArrayList<CloudRoom.DataEntity> cloudRoom,CloudOnClickListener cloudOnClickListener1,ImageLoader imageLoader1,DisplayImageOptions options1) {
        context = activity;
        this.cloudRoom = cloudRoom;
        cloudOnClickListener = cloudOnClickListener1;
        imageLoader = imageLoader1;
        options = options1;
        uid = uid1;
    }

    @Override
    public int getCount() {
        if (cloudRoom != null){
            return cloudRoom.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (cloudRoom != null){
            return cloudRoom.get(position);
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
            convertView = LayoutInflater.from(context).inflate(R.layout.adapterclouditem,null);
            viewHandler.button = (Button)convertView.findViewById(R.id.button_yaoqinghaoyou);
            viewHandler.button_ol = (Button)convertView.findViewById(R.id.button_start);
            viewHandler.imageView = (ImageView)convertView.findViewById(R.id.imageview_cloudroomm);
            viewHandler.imageButton = (ImageButton)convertView.findViewById(R.id.imagebutton_add_delete);
            viewHandler.textView_all = (TextView)convertView.findViewById(R.id.textview_cloudroomall);
            viewHandler.textView_name = (TextView)convertView.findViewById(R.id.textview_cloudroomname);
            viewHandler.textView_surpres = (TextView)convertView.findViewById(R.id.textview_cloudroomshengyu);
            viewHandler.textView_time = (TextView)convertView.findViewById(R.id.textview_cloudroomtime);
            viewHandler.progressBar = (ProgressBar)convertView.findViewById(R.id.progressbar_layout_roomthis);
            viewHandler.imageView_ol = (ImageView)convertView.findViewById(R.id.imageview_cloudroomm_this);
            convertView.setTag(viewHandler);
        }else {
            viewHandler = (ViewHandler)convertView.getTag();
            // viewHandler.progressBar = new ProgressBar(context);
        }
        viewHandler.button_ol.setOnClickListener(cloudOnClickListener);
        viewHandler.button.setOnClickListener(cloudOnClickListener);
        viewHandler.imageButton.setOnClickListener(cloudOnClickListener);
        viewHandler.imageButton.setTag(position);
        viewHandler.button.setTag(position);
        viewHandler.button_ol.setTag(position);
        if (!StringUtils.isEmpty(cloudRoom.get(position).PNumber)){
            viewHandler.textView_name.setText("(第" + cloudRoom.get(position).PNumber + "期)" + cloudRoom.get(position).Title);
        }else {
            viewHandler.textView_name.setText("(第0期)" + "该商品已下线~");
        }
        if (!StringUtils.isEmpty(cloudRoom.get(position).TotalCount)){
            int num = Integer.parseInt(cloudRoom.get(position).TotalCount) - Integer.parseInt(cloudRoom.get(position).LeftCount);
            viewHandler.progressBar.setMax(Integer.parseInt(cloudRoom.get(position).TotalCount));
            viewHandler.progressBar.setProgress(num);
        }else {
            viewHandler.progressBar.setProgress(0);
            viewHandler.progressBar.setMax(100);
        }
        if (uid != null){
            if (uid.equals(cloudRoom.get(position).CreateUserID)){
                viewHandler.imageView_ol.setVisibility(View.VISIBLE);
            }else {
                viewHandler.imageView_ol.setVisibility(View.GONE);
            }
        }
        SpannableString spannableString = new SpannableString("限时:"+cloudRoom.get(position).Duration);
        spannableString.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.tiltle)),3,3+cloudRoom.get(position).Duration.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        viewHandler.textView_time.setText(spannableString);
        if (!StringUtils.isEmpty(cloudRoom.get(position).TotalCount)){
            SpannableString spannableString1 = new SpannableString("总需:"+cloudRoom.get(position).TotalCount+"人次");
            spannableString1.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.tiltle)),3,3+cloudRoom.get(position).TotalCount.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            viewHandler.textView_all.setText(spannableString1);
        }else {
            SpannableString spannableString1 = new SpannableString("总需:0人次");
            spannableString1.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.tiltle)),3,4, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            viewHandler.textView_all.setText(spannableString1);
        }
        if (!StringUtils.isEmpty(cloudRoom.get(position).LeftCount)){
            SpannableString spannableString2 = new SpannableString("剩余:"+cloudRoom.get(position).LeftCount+"人次");
            spannableString2.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.tiltle)),3,3+cloudRoom.get(position).LeftCount.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            viewHandler.textView_surpres.setText(spannableString2);
        }else {
            SpannableString spannableString2 = new SpannableString("剩余:0人次");
            spannableString2.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.tiltle)),3,4, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            viewHandler.textView_surpres.setText(spannableString2);
        }
        imageLoader.displayImage(cloudRoom.get(position).MainImg, viewHandler.imageView, options);
        return convertView;
    }
    public static class ViewHandler{
        TextView textView_name,textView_time,textView_all,textView_surpres;
        Button button,button_ol;
        public static ImageButton imageButton;
        ProgressBar progressBar;
        ImageView imageView,imageView_ol;
    }
    public static abstract class CloudOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            cloudOnClickListener((Integer)v.getTag(),v);
        }

        protected abstract void cloudOnClickListener(Integer tag, View v);
    }
    /**
     * 删除某个房间
     * @param listView
     * @param delete
     */
    public void updatedelete(ListView listView, Integer delete) {
        int vb = listView.getFirstVisiblePosition();
        if (delete - vb >= 0){
            cloudRoom.remove(delete);
            Log.i(context+"",delete+"ifweopwdjp"+cloudRoom.size());
            AdapterCloudRoom.this.notifyDataSetChanged();
        }
    }
}
