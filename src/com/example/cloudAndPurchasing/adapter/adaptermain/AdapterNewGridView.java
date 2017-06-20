package com.example.cloudAndPurchasing.adapter.adaptermain;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.kind.Publish;
import com.example.cloudAndPurchasing.zhi.LogUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Administrator on 2016/4/15 0015.
 */
public class AdapterNewGridView extends BaseAdapter {
    private Context context1;
    private ArrayList<Publish> arrayList1;
    private ViewHandler viewHandler;
    private boolean isPlay;
    private DisplayImageOptions options;
    private SimpleDateFormat simpleDateFormat;
    private ImageLoader imageLoader1;
    private Handler handler;
    private HandlerThread handlerThread = new HandlerThread("MyHandlerThread");
    private GridView gridView;
    private long longTime = 0;
    private LocalBroadcastManager localBroadcastManager;
//    private TimeCount time;
    Integer post_ol;
    private ViewHandler viewHandler1;
    private TextView textView1;

    private void updata(Integer post_ol, long longTime) {

    }

    public AdapterNewGridView(LocalBroadcastManager localBroadcastManager1,GridView gridView1,Context context,ArrayList<Publish> arrayList,ImageLoader imageLoader,DisplayImageOptions options1){
        context1 = context;
        arrayList1 = arrayList;
        imageLoader1 = imageLoader;
        options = options1;
        gridView = gridView1;
        localBroadcastManager = localBroadcastManager1;
    }
    @Override
    public int getCount() {
        if (arrayList1 != null) {
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
        if (convertView == null){
            viewHandler = new ViewHandler();
            convertView = LayoutInflater.from(context1).inflate(R.layout.adapternewgridviewitem,null);
            viewHandler.imageView = (ImageView)convertView.findViewById(R.id.imageview_fragmentmiangoodsphotoone);
            viewHandler.imageView_ol = (ImageView)convertView.findViewById(R.id.imagebutton_xiangou_mian);
            viewHandler.imageView_ool = (ImageView)convertView.findViewById(R.id.imagebutton_shiyuan_ol_main);
            viewHandler.textView = (TextView)convertView.findViewById(R.id.textview_fragmentmaintitle);
            viewHandler.textView_ol = (TextView)convertView.findViewById(R.id.textview_fragmentmainshijian);
            convertView.setTag(viewHandler);
        }else {
            viewHandler = (ViewHandler)convertView.getTag();
        }
        viewHandler.textView_ol = (TextView)convertView.findViewById(R.id.textview_fragmentmainshijian);
        try {
            if (Integer.parseInt(arrayList1.get(position).getTime())>0) {
                //将一个int类型的数值转换成时间格式
                viewHandler.imageView_ol.setImageResource(R.drawable.publishzhengzaijiexiao);
                int tempTime = Integer.parseInt(arrayList1.get(position).getTime());
                //SSS为毫秒，如果精确到时分秒改成HH:mm:ss即可
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm分ss秒SSS");
                Date date = new Date(tempTime);
                String time = simpleDateFormat.format(date);
                //因为1秒是等于1000毫秒的，正常显示格式为05:40:99,所以此处截取掉最后一位数字
                viewHandler.textView_ol.setText(time.substring(0, time.length() - 1));
                imageLoader1.displayImage(arrayList1.get(position).getImagepath(), viewHandler.imageView, options);
                viewHandler.textView.setText("(第"+arrayList1.get(position).getPublishnumber()+"期)"+arrayList1.get(position).getShoppingname());
                viewHandler.textView_ol.setTag(position);
            }
        }catch (Exception e){
            LogUtil.e("AdapterNewGridView getView error:",e.toString());
        }

        return convertView;
    }
    class ViewHandler{
        TextView textView,textView_ol;
        ImageView imageView,imageView_ol,imageView_ool;
    }
    boolean isFirstStart = true;
    int result = 0;
    volatile boolean flg = true;
    public void start() {
        handlerThread.start();
        handler = new Handler(handlerThread.getLooper());
        if (isFirstStart){
            isFirstStart = false;
        }else {
            handlerThread.quit();
        }
        handler.post(runnable);
    }
    private Runnable runnable = new Runnable() {
        @Override
        public synchronized void run() {
            while (flg) {
                try {
                    if (arrayList1 == null || result == arrayList1.size()) {
                        break;
                    }
                    Thread.sleep(50);//阻塞过程捕获中断异常来退出
                    for (Publish person : arrayList1) {
                        if (Integer.parseInt(person.getTime())>0) {
                            if ("1".equals(person.getTime())) {
                                person.setTime("0");
                                result++;
                                Intent intent = new Intent("com.example.cloudAndPurchasing.zh");
                                localBroadcastManager.sendBroadcast(intent);
                                flg = false;
                                break;
                            } else {
                                person.setTime((Integer.parseInt(person.getTime()) - 50) + "");
                            }
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;//捕获到异常之后，执行break跳出循环。
                }
//                while (!Thread.interrupted()){//非阻塞过程中通过判断中断标志来退出
//
//                }
            }
        }
    };
}
