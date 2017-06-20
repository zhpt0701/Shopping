package com.example.cloudAndPurchasing.adapter.adaptercloud;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.http.HttpApi;
import com.example.cloudAndPurchasing.http.Numbers;
import com.example.cloudAndPurchasing.kind.Winning;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/5 0005.
 */
public class AdapterWinning extends BaseAdapter{
    private Context context;
    private ArrayList<Winning> arrayList;
    private WinningOnClickListener  winningOnClicklListener;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;
    public AdapterWinning(ImageLoader imageLoader1,DisplayImageOptions options1,Context activityWinningRecord, ArrayList<Winning> arrayList1,WinningOnClickListener winn) {
        context = activityWinningRecord;
        arrayList = arrayList1;
        imageLoader = imageLoader1;
        options = options1;
        winningOnClicklListener = winn;
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
        Winning winning = arrayList.get(position);
        ViewHandler viewHandler ;
        if (convertView == null){
            viewHandler = new ViewHandler();
            convertView = LayoutInflater.from(context).inflate(R.layout.adapterwinningitem,null);
             viewHandler.button = (Button)convertView.findViewById(R.id.button_luckCode);
            viewHandler.imageView = (ImageView)convertView.findViewById(R.id.imageview_winning);
            viewHandler.textView = (TextView)convertView.findViewById(R.id.textview_winningname);
            viewHandler.textview_time = (TextView)convertView.findViewById(R.id.textview_winningaman_count);
            viewHandler.textView_man_time=(TextView)convertView.findViewById(R.id.textview_publishtime);
            viewHandler.textView_luckCode = (TextView)convertView.findViewById(R.id.textview_winningluckCode);
            viewHandler.button_ol = (Button)convertView.findViewById(R.id.button_select_luckCode);
            convertView.setTag(viewHandler);
        }else {
            viewHandler = (ViewHandler)convertView.getTag();
        }
        Button button1 = (Button)convertView.findViewById(R.id.button_luckCode);
        viewHandler.button = button1;
        if (winning.getIsDelivery().equals("2")){
            if (winning.getAwardState().equals("0")){
                viewHandler.button.setText("领取奖品");
                viewHandler.button.setBackgroundResource(R.drawable.button_submititem);
            }else if (winning.getAwardState().equals("1")){
                viewHandler.button.setText("去晒单");
                viewHandler.button.setBackgroundResource(R.drawable.button_submititem);
            }else if (winning.getAwardState().equals("2")){
                viewHandler.button.setText("去晒单");
                viewHandler.button.setBackgroundResource(R.drawable.button_submititem);
            }else if (winning.getAwardState().equals("3")){
                viewHandler.button.setText("去晒单");
                viewHandler.button.setBackgroundResource(R.drawable.button_submititem);
            }else if(winning.getAwardState().equals("4")){
                viewHandler.button.setText("去晒单");
                viewHandler.button.setBackgroundResource(R.drawable.button_submititem);
            }
        }else {
            if (winning.getAwardState().equals("0")){
                viewHandler.button.setText("领取奖品");
                viewHandler.button.setBackgroundResource(R.drawable.button_submititem);
            }else if (winning.getAwardState().equals("1")){
                viewHandler.button.setText("待发货");
                viewHandler.button.setBackgroundResource(R.drawable.textview_backthis);
            }else if (winning.getAwardState().equals("2")){
                viewHandler.button.setText("发货中");
                viewHandler.button.setBackgroundResource(R.drawable.textview_backthis);
            }else if (winning.getAwardState().equals("3")){
                viewHandler.button.setText("去晒单");
                viewHandler.button.setBackgroundResource(R.drawable.button_submititem);
            }else if(winning.getAwardState().equals("4")){
                viewHandler.button.setText("去晒单");
                viewHandler.button.setBackgroundResource(R.drawable.button_submititem);
            }
        }

        imageLoader.displayImage(winning.getShowImg(), viewHandler.imageView, options);
        viewHandler.textview_time.setText("总需:" + winning.getTotalCount() + "人次" + "  " + "总参与:" + winning.getTradingCount() + "人次");
        viewHandler.textView.setText("(第"+winning.getPnumber()+"期)"+winning.getWinninggoodsname());
        viewHandler.textView_man_time.setText("揭晓时间:" + winning.getWinningallman_time());
        viewHandler.textview_danhao = (TextView)convertView.findViewById(R.id.textview_danhao);
//        String str = winning.getCourierCompany();
//        boolean falg = TextUtils.isEmpty(str);
//        if (!falg){
//            viewHandler.textview_danhao.setText("物流信息:" + winning.getCourierCompany() + winning.getCourierNO());
//        }
        if (!winning.getCourierCompany().equals("null")){
            viewHandler.textview_danhao.setVisibility(View.VISIBLE);
            viewHandler.textview_danhao.setText("物流信息:" + winning.getCourierCompany() + winning.getCourierNO());
        }else {
            viewHandler.textview_danhao.setVisibility(View.INVISIBLE);
        }
        if(!winning.getCustomCode().equals("null")){
            viewHandler.button_ol.setVisibility(View.VISIBLE);
        }else {
            viewHandler.button_ol.setVisibility(View.GONE);
        }
        viewHandler.button.setOnClickListener(winningOnClicklListener);
        viewHandler.button_ol.setOnClickListener(winningOnClicklListener);
        viewHandler.button_ol.setTag(position);
        viewHandler.button.setTag(position);
        SpannableString spannableString = new SpannableString("幸运码:"+winning.getWinningluckCode());
        spannableString.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.tiltle)),4,4+winning.getWinningluckCode().length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        viewHandler.textView_luckCode.setText(spannableString);
        return convertView;
    }

    public static class ViewHandler{
        ImageView imageView;
        TextView textView_ol,textView,textView_man_time,textView_luckCode,textview_time,textview_danhao;
      public static Button button,button_ol;
    }
    public static abstract class WinningOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            winningOnClickListener((Integer)v.getTag(),v);
        }

        protected abstract void winningOnClickListener(Integer tag,View view);
    }

    /**
     * shaidan界面刷新
     * @param tag
     * @param listView
     */
    public void updateview(Integer tag, ListView listView) {
        int first = listView.getFirstVisiblePosition();
        if (tag-first>=0){
            View view1 = listView.getChildAt(tag-first);
            ViewHandler viewHandler1 = (ViewHandler)view1.getTag();
            viewHandler1.button = (Button)view1.findViewById(R.id.button_luckCode);
            viewHandler1.button.setText("待发货");
            arrayList.get(tag).setAwardState("1");
        }
    }
}
