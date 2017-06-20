package com.example.cloudAndPurchasing.adapter.adapterfriend;

import android.content.Context;
import android.text.SpannableString;
import android.util.Log;
import com.example.cloudAndPurchasing.http.Numbers;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.http.*;
import com.example.cloudAndPurchasing.kind.Friend;
import com.example.cloudAndPurchasing.kind.Goods;
import com.example.cloudAndPurchasing.zhi.ValuePrice;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/3/30 0030.
 */
public class AdapterNewFriend extends BaseAdapter{
    private ArrayList<Friend> arrayList;
    private Context context;
    private ImageLoader imageLoader1;
    private DisplayImageOptions options1;
    private YesOnclickselectListener yesOnclickListener;
    public AdapterNewFriend(ImageLoader imageLoader,DisplayImageOptions options,Context context_ol,ArrayList<Friend> arrayList1,YesOnclickselectListener yesOnclickListener1){
        context = context_ol;
        arrayList = arrayList1;
        imageLoader1 = imageLoader;
        options1 = options;
        yesOnclickListener = yesOnclickListener1;
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
        Friend friend = arrayList.get(position);
       ViewHandler viewHandler ;
        if (convertView == null){

            viewHandler = new ViewHandler();
            convertView = LayoutInflater.from(context).inflate(R.layout.adapternewfriendlayout,null);
            viewHandler.textView = (TextView)convertView.findViewById(R.id.textview_friendcontent);
            viewHandler.imageButton = (ImageView)convertView.findViewById(R.id.imageview_this_ol_photo_th);
            viewHandler.spinner = (Button)convertView.findViewById(R.id.button_yes);
            viewHandler.textView_ol = (TextView)convertView.findViewById(R.id.textview_tongyi);
            convertView.setTag(viewHandler);
        }else {
            viewHandler = (ViewHandler)convertView.getTag();
        }
        SpannableString builder = new SpannableString("好友" + friend.getNickname()+"想加您为他的艾购好友");
        builder.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.blue)),2,2+friend.getNickname().length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        viewHandler.textView.setText(builder);
        viewHandler.spinner.setOnClickListener(yesOnclickListener);
        viewHandler.spinner.setTag(position);
        if (friend.getShifou() == true){
            if (friend.getNum() == String.valueOf(Numbers.ONE)){
                viewHandler.textView_ol.setText("已同意");
                viewHandler.textView_ol.setVisibility(View.VISIBLE);
                viewHandler.spinner.setVisibility(View.GONE);
            }else if (friend.getNum() == String.valueOf(Numbers.ZERO)){
                viewHandler.textView_ol.setText("已拒绝");
                viewHandler.textView_ol.setVisibility(View.VISIBLE);
                viewHandler.spinner.setVisibility(View.GONE);
            }
        }else {
            viewHandler.spinner.setVisibility(View.VISIBLE);
            viewHandler.textView_ol.setVisibility(View.GONE);
        }
        viewHandler.imageButton.setImageBitmap(friend.getPhoto());
        imageLoader1.displayImage(HttpApi.mytu+friend.getID(), viewHandler.imageButton,options1);
        return convertView;
    }

    /**
     * 刷新item
     * @param tag
     * @param ot
     */
    public void updateview_cl(Integer tag, String ot,ListView listView) {
        //只当要更新的view在可见的位置时才更新，不可见时，跳过不更新
        int vb = listView.getFirstVisiblePosition();
        Log.i("fkslkfksd","kfs;ldfksdfjsd;f"+tag);
        if (tag-vb >= 0){
            View view1 = listView.getChildAt(tag-vb);
            ViewHandler holder = (ViewHandler) view1.getTag();
            holder.spinner = (Button)view1.findViewById(R.id.button_yes);
            holder.textView_ol = (TextView)view1.findViewById(R.id.textview_tongyi);
            if (ot.equals("A")){
                holder.spinner.setVisibility(View.GONE);
                holder.textView_ol.setText("已同意");
                holder.textView_ol.setVisibility(View.VISIBLE);
            }else if (ot.equals("R")){
                holder.spinner.setVisibility(View.GONE);
                holder.textView_ol.setText("已拒绝");
                holder.textView_ol.setVisibility(View.VISIBLE);
            }
//            arrayList.get(tag).setShifou(true);
//            arrayList.get(tag).setNum(ot);
        }
    }

    public static class ViewHandler{
        TextView textView,textView_ol;
        Button spinner;
        ImageView imageButton;
    }
    public static abstract class YesOnclickselectListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            yesOnclickListener((Integer)v.getTag(),v);
        }

        protected abstract void yesOnclickListener(Integer tag, View v);
    }
}
