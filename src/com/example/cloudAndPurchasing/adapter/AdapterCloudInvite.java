package com.example.cloudAndPurchasing.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.http.HttpApi;
import com.example.cloudAndPurchasing.http.Numbers;
import com.example.cloudAndPurchasing.imageloder.Myimageloder;
import com.example.cloudAndPurchasing.kind.Friend;
import com.example.cloudAndPurchasing.zhi.ValuePrice;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/3/31 0031.
 */
public class AdapterCloudInvite  extends BaseAdapter{
    private ArrayList<Friend> arrayList_ol;
    private Context context;
    private ShifuOnClickListener shifuOnClickListener;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;
    public AdapterCloudInvite(ImageLoader imageLoader1,DisplayImageOptions options1,Context activityAsk, ArrayList<Friend> arrayList,ShifuOnClickListener shifuOnClickListener1) {
        arrayList_ol = arrayList;
        context = activityAsk;
        shifuOnClickListener = shifuOnClickListener1;
        imageLoader = imageLoader1;
        options = options1;
    }

    @Override
    public int getCount() {
        if (arrayList_ol != null){
            return arrayList_ol.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (arrayList_ol != null){
            return arrayList_ol.get(position);
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
        Friend friend = arrayList_ol.get(position);
        if (convertView == null){
            viewHandler = new ViewHandler();
            convertView = LayoutInflater.from(context).inflate(R.layout.adaptercloudinviteitem,null);
            viewHandler.textView = (TextView)convertView.findViewById(R.id.textview_haoyou);
            viewHandler.imageView = (ImageView)convertView.findViewById(R.id.imagebutton_friend_photo_th);
            viewHandler.spinner = (Button)convertView.findViewById(R.id.spinner_friend);
            viewHandler.textView_ol = (TextView)convertView.findViewById(R.id.textview_tongyiol);
            convertView.setTag(viewHandler);
        }else{
            viewHandler = (ViewHandler)convertView.getTag();
        }
        imageLoader.displayImage(HttpApi.mytu+friend.getCreateUser(),viewHandler.imageView,options);
        viewHandler.spinner.setOnClickListener(shifuOnClickListener);
        viewHandler.spinner.setTag(position);
        if (friend.getShifou() == true){
           if (friend.getNum().equals("A")){
               viewHandler.textView_ol.setText("已同意");
               viewHandler.spinner.setVisibility(View.GONE);
               viewHandler.textView_ol.setVisibility(View.VISIBLE);
           }else if (friend.getNum().equals("R")){
               viewHandler.textView_ol.setText("已拒绝");
               viewHandler.textView_ol.setVisibility(View.VISIBLE);
               viewHandler.spinner.setVisibility(View.GONE);
           }
        }else {
            viewHandler.spinner.setVisibility(View.VISIBLE);
            viewHandler.textView_ol.setVisibility(View.GONE);
        }
        SpannableString name = new SpannableString("好友"+friend.getNickname()+"邀请您加入他的梦想小屋");
        name.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.blue)),2,2+friend.getNickname().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        viewHandler.textView.setText(name);
        return convertView;
    }

    /**
     * jiemianshuaxin
     * @param tag
     * @param yesoron
     * @param listView
     */
    public void updateview_ol(Integer tag, String yesoron, ListView listView) {
        int iv = listView.getFirstVisiblePosition();
        if (tag-iv>=0){
            View view1 = listView.getChildAt(tag-iv);
            ViewHandler viewHandler = (ViewHandler)view1.getTag();
            viewHandler.spinner = (Button)view1.findViewById(R.id.spinner_friend);
            viewHandler.textView_ol = (TextView)view1.findViewById(R.id.textview_tongyiol);
            if (yesoron.equals("A")){
                viewHandler.spinner.setVisibility(View.GONE);
                viewHandler.textView_ol.setText("已同意");
                viewHandler.textView_ol.setVisibility(View.VISIBLE);
            }else if (yesoron.equals("R")){
                viewHandler.spinner.setVisibility(View.GONE);
                viewHandler.textView_ol.setText("已拒绝");
                viewHandler.textView_ol.setVisibility(View.VISIBLE);
            }
            Friend friend_ol = new Friend();
            friend_ol.setPhoto(arrayList_ol.get(tag).getPhoto());
            friend_ol.setNickname(arrayList_ol.get(tag).getNickname());
            friend_ol.setShifou(true);
            friend_ol.setNum(yesoron);
            arrayList_ol.set(tag,friend_ol);
        }
    }

    class ViewHandler{
        TextView textView,textView_ol;
        Button spinner;
        ImageView imageView;
    }
    public static abstract class ShifuOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            shifuOnClickListener((Integer)v.getTag(),v);
        }

        protected abstract void shifuOnClickListener(Integer tag, View v);
    }
}
