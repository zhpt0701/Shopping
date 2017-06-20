package com.example.cloudAndPurchasing.adapter.adaptercloud;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.customcontrol.ThisGridView;
import com.example.cloudAndPurchasing.http.HttpApi;
import com.example.cloudAndPurchasing.imageloder.Myimageloder;
import com.example.cloudAndPurchasing.kind.Publish;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/5 0005.
 */
public class AdapterMypublish extends BaseAdapter{
    private Context context;
    private ArrayList<Publish> arrayList;
    private CommendOnClickListener commendOnClickListener;
    private PhotoOnItemClickListener photoOnItemClickListener;
    private ImageLoader imageLoader;
    private Myimageloder myimageloder;
    private DisplayImageOptions options;
    public AdapterMypublish(ImageLoader imageLoader1,DisplayImageOptions options1,Context activityPublish, ArrayList<Publish> arrayList1,CommendOnClickListener comm,PhotoOnItemClickListener phot) {
        context = activityPublish;
        arrayList = arrayList1;
        commendOnClickListener = comm;
        photoOnItemClickListener = phot;
        imageLoader = imageLoader1;
        options = options1;
        myimageloder = new Myimageloder(activityPublish);
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
       Publish publish = arrayList.get(position);
        ViewHandler viewHandler;
        if (convertView == null){
            viewHandler = new ViewHandler();
            convertView = LayoutInflater.from(context).inflate(R.layout.adapterpublishitem,null);
            viewHandler.textView = (TextView)convertView.findViewById(R.id.textview_publishnackname);
            viewHandler.textView_time = (TextView)convertView.findViewById(R.id.textview_time);
            viewHandler.textView_content = (TextView)convertView.findViewById(R.id.textview_content);
            viewHandler.textView_number = (TextView)convertView.findViewById(R.id.textview_zannumber);
            viewHandler.button = (Button)convertView.findViewById(R.id.button_zan);
            viewHandler.button_publish = (Button)convertView.findViewById(R.id.button_share_publish);
            viewHandler.textView_publish = (TextView)convertView.findViewById(R.id.textview_publish_number);
            viewHandler.gridView = (ThisGridView)convertView.findViewById(R.id.gridview_this_photo);
            viewHandler.imageView = (ImageView)convertView.findViewById(R.id.imageview_this);
            convertView.setTag(viewHandler);
        }else {
            viewHandler = (ViewHandler)convertView.getTag();
        }
        if (publish.getArrayList() != null){
            AdapterGridview adapterGridview = new AdapterGridview(imageLoader,options,publish.getArrayList(),context);
            viewHandler.gridView.setAdapter(adapterGridview);
        }
        if (!publish.getImagepath().equals("null")){
//            byte[] bytes = Base64.decode(publish.getImagepath(), Base64.DEFAULT);
//            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
//            Bitmap bitmap1 = Tailoring.phototailoring(bitmap);
//            viewHandler.imageView.setImageBitmap(bitmap1);
            myimageloder.imageLoader.displayImage(HttpApi.yu+publish.getImagepath(),viewHandler.imageView,myimageloder.options);
        }
        if (publish.getNickname() != "null"){
            viewHandler.textView.setText(publish.getNickname());
        }
        viewHandler.textView.setText(publish.getTitle());
        viewHandler.textView_time.setText(publish.getTime());
        viewHandler.textView_content.setText(publish.getContent());
        if (!publish.getFaverconunt().equals("null")){
            viewHandler.textView_number.setText(publish.getFaverconunt());
        }else {
            viewHandler.textView_number.setText("0");
        }
        if (!publish.getPublishnumber().equals("null")){
           viewHandler.textView_publish.setText(publish.getPublishnumber());
        }else {
            viewHandler.textView_publish.setText("0");
        }
        viewHandler.button_publish.setOnClickListener(commendOnClickListener);
        viewHandler.button_publish.setTag(position);
        viewHandler.button.setOnClickListener(commendOnClickListener);
        viewHandler.gridView.setOnItemClickListener(photoOnItemClickListener);
        viewHandler.gridView.setTag(position);
        viewHandler.button.setTag(position);
        return convertView;
    }

    public static class ViewHandler{
        TextView textView,textView_time,textView_content,textView_number,textView_publish;
       public static ThisGridView gridView;
       public static Button button,button_publish;
        ImageView imageView;
    }
   public static abstract class CommendOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            commedOnClickListener((Integer)v.getTag(),v);
        }

        protected abstract void commedOnClickListener(Integer tag, View v);
    }
   public static abstract class PhotoOnItemClickListener implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            photoOnItemClickListener((Integer)parent.getTag(),parent,view, position,id);
        }
       protected abstract void photoOnItemClickListener(Integer tag, AdapterView<?> parent, View view, int position, long id);
   }
    //点赞刷新
    public void updateviewnum(ListView listView, Integer tag, int num) {
        int frist = listView.getFirstVisiblePosition();
        if (tag -frist>=0){
            View view1 = listView.getChildAt(tag-frist);
            ViewHandler viewHandler1 = (ViewHandler)view1.getTag();
            viewHandler1.textView_number = (TextView)view1.findViewById(R.id.textview_zannumber);
            viewHandler1.textView_number.setText("("+String.valueOf(num)+")");
            arrayList.get(tag).setFaverconunt(String.valueOf(num));
        }
    }
}
