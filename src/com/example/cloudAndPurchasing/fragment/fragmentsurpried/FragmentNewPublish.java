package com.example.cloudAndPurchasing.fragment.fragmentsurpried;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.cloudAndPurchasing.MyActivity;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.activity.ActivityShoppingdetail;
import com.example.cloudAndPurchasing.activity.activitysurpired.ActivityShoppingTimeDetail;
import com.example.cloudAndPurchasing.customcontrol.widget.XListView;
import com.example.cloudAndPurchasing.http.HttpApi;
import com.example.cloudAndPurchasing.http.Numbers;
import com.example.cloudAndPurchasing.imageloder.Imageloderinit;
import com.example.cloudAndPurchasing.kind.FRecentlyAnnounced;
import com.example.cloudAndPurchasing.kind.GsonUtil;
import com.example.cloudAndPurchasing.kind.StringUtils;
import com.example.cloudAndPurchasing.zhi.LogUtil;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.StringCallback;
import com.nostra13.universalimageloader.core.ImageLoader;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * feng
 * Created by Administrator on 2016/4/11 0011.
 */
public class FragmentNewPublish extends Fragment implements XListView.IXListViewListener, AdapterView.OnItemClickListener {
    private ImageLoader imageLoader;
    private XListView xListView;
    private RecentlyAnnouncedAdapter recentlyAnnouncedAdapter;
    private ArrayList<FRecentlyAnnounced.DataBean> recentlys;
    private MyActivity mcontext;
    private Imageloderinit imageloderinit;
    private Handler mHandler;
    private int p ;
    private List<FRecentlyAnnounced.DataBean> mData;
    private HandlerThread countDownThread;
    private Handler countDownhandler;
    private CountDownRunnable downRunnable;
    private final int STOP_THREAD = 11;
    private boolean isRefresh = false;
    private int great0Number = 0;
    Handler handler2 = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    recentlyAnnouncedAdapter.notifyDataSetChanged();
                    great0Number = 0;
                    if (recentlys.size() > 0 ){
                        for (FRecentlyAnnounced.DataBean data : recentlys){
                            if (data.CountDown > 0 ){
                                great0Number+=1;
                                isRefresh = true;
                            }
                        }

                        if (great0Number > 0){      //停止倒计时
                            //isRun = true;
                        }else{
                            isRun = false;
                            Log.e("handleMessage isRun : " , isRun+"");
                        }

                        if (isRefresh){
                            //每隔1毫秒更新一次界面，如果只需要精确到秒的倒计时此处改成1000即可
                            handler2.sendEmptyMessageDelayed(1,149);
                        }



                    }
                    break;
                case STOP_THREAD:
                    if (countDownhandler != null){
                        countDownhandler.removeCallbacksAndMessages(null);
                    }
                    LogUtil.e("remove" , "remove countDownhandler");
                    break;
            }
        }

    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentnewpublishlayout, container, false);
        mcontext = (MyActivity) getActivity();
        initData();
        initView(view);
        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        isRun = true;
        Log.e("onResume  isRun : " , isRun+"");
        isRefresh = true;

    }

    @Override
    public void onPause() {
        super.onPause();
        isRefresh =false;
    }

    private void initData() {
        mHandler = new Handler();
        imageloderinit = new Imageloderinit(getActivity());
        recentlys = new ArrayList<FRecentlyAnnounced.DataBean>();
        mData = new ArrayList<FRecentlyAnnounced.DataBean>();
        great0Number = 0;
        p = 1;
        recentlys.clear();
        mData.clear();

    }

    private void initView(View view) {
        xListView = (XListView)view.findViewById(R.id.xListView);
        xListView.setPullRefreshEnable(true);
        xListView.setPullLoadEnable(true);
        xListView.setAutoLoadEnable(true);
        xListView.setXListViewListener(this);
        xListView.setOnItemClickListener(this);
        getRecentlyAnnouncedData(p);

    }

    private void getRecentlyAnnouncedData(int p){
        OkHttpUtils.post(HttpApi.spurised_pulish_goods)
                .tag(this)
                .params("PageIndex",  p + "")
                .params("PageSize" , "10")
                .execute(new StringCallback() {
                    @Override
                    public void onResponse(boolean b, String s, Request request, Response response) {
                        processJson(s);
                        onLoad();
                    }

                    @Override
                    public void onError(boolean isFromCache, Call call, Response response, Exception e) {
                        super.onError(isFromCache, call, response, e);
                        onLoad();
                    }
                });
    }

    private void processJson(String s) {
        FRecentlyAnnounced fRecentlyAnnounced = GsonUtil.processJson(s, FRecentlyAnnounced.class);
        if (fRecentlyAnnounced.state == 1){
            if (fRecentlyAnnounced.data != null){
                if (fRecentlyAnnounced.data.size() > 0){

                    for (FRecentlyAnnounced.DataBean data : fRecentlyAnnounced.data){ //转化作毫秒

                        if(data.CountDown > 0){
                            data.CountDown = data.CountDown *1000;
                            Log.e("fdata.CountDown" , "----------    "+data.CountDown+"-----------");
                        }
                    }

                    if (p == 1){
                        recentlys.clear();
                    }
                    recentlys.addAll(fRecentlyAnnounced.data);
                    if (recentlyAnnouncedAdapter == null){
                        recentlyAnnouncedAdapter = new RecentlyAnnouncedAdapter();
                        xListView.setAdapter(recentlyAnnouncedAdapter);
                    }else{
                        recentlyAnnouncedAdapter.notifyDataSetChanged();
                    }

                    if (recentlys.size() > 0){
                        isRun = true;
                        countDownTime();                               //倒计时
                        handler2.sendEmptyMessageDelayed(1, 50);       // 刷新页面
                    }

                }
            }


        } else {
            Toast.makeText(mcontext, "没有更多数据了", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 实现倒计时
     *
     */
    private void countDownTime() {
        countDownThread = new HandlerThread("countdown_thread");
        countDownThread.start();
        countDownhandler = new Handler(countDownThread.getLooper());
        if (downRunnable == null){
            downRunnable = new CountDownRunnable();
        }
        countDownhandler.post(downRunnable);
        Log.e("isRun", isRun + "");

    }
    boolean isRun ;



    private class CountDownRunnable implements Runnable{

        @Override
        public void run() {
            while(isRun){
                Log.e("run() isRun :" ,isRun+"");
                if (recentlys.size() > 0){
                    try {
                        Thread.sleep(99);
                        for (FRecentlyAnnounced.DataBean data : recentlys){
                            if (data.CountDown > 0){
                                data.CountDown = data.CountDown-99;
                                if (data.CountDown < 0 || data.CountDown == 0){
                                    data.CountDown = Long.valueOf(0);
                                }
                                Log.e("fdata.CountDown" ,"-------------      "+data.CountDown+"----------");
                            }

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }

        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent  = new Intent();
        if (!StringUtils.isEmpty(recentlys.get(position).Winner)){
            intent.putExtra("th_ol",recentlys.get(position).ID);
            intent.putExtra("back","1");
            intent.putExtra("prid", recentlys.get(position).ProductID);
            intent.setClass(getActivity(), ActivityShoppingdetail.class);
            startActivity(intent);
        }else {
            intent.putExtra("back",String.valueOf(Numbers.ONE));
            intent.putExtra("outid", recentlys.get(position).ID);
            intent.setClass(getActivity(), ActivityShoppingTimeDetail.class);
            startActivity(intent);
        }

    }
    public class RecentlyAnnouncedAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return recentlys.size();
        }

        @Override
        public Object getItem(int position) {
            return recentlys.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(getActivity(), R.layout.adapternewpublish_item, null);
                viewHolder = new ViewHolder();
                viewHolder.imgShopping = (ImageView) convertView.findViewById(R.id.imgShopping);
                viewHolder.imgRecentlyAnnounced = (ImageView) convertView.findViewById(R.id.imgRecentlyAnnounced);
                viewHolder.tvShoppingName = (TextView) convertView.findViewById(R.id.tvShoppingName);
                viewHolder.tvWinnerName = (TextView) convertView.findViewById(R.id.tvWinnerName);
                viewHolder.tvAttendanceNumber = (TextView) convertView.findViewById(R.id.tvAttendanceNumber);
                viewHolder.tvluckNumber = (TextView) convertView.findViewById(R.id.tvluckNumber);
                viewHolder.tvCountDownTime = (TextView) convertView.findViewById(R.id.tvCountDownTime);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();

            }

            imageloderinit.imageLoader.displayImage(recentlys.get(position).MainImg, viewHolder.imgShopping, imageloderinit.options);
            viewHolder.tvShoppingName.setText("(第" + recentlys.get(position).PNumber + "期)" + recentlys.get(position).Title);
            viewHolder.tvAttendanceNumber.setText("本期夺宝:"+recentlys.get(position).TotalCount+"人次");
            if (recentlys.get(position).CountDown > 0 || recentlys.get(position).CountDown == 0){           // 显示倒计时
                viewHolder.imgRecentlyAnnounced.setVisibility(View.VISIBLE);
                viewHolder.imgRecentlyAnnounced.setImageResource(R.drawable.publishzhengzaijiexiao);
                viewHolder.tvWinnerName.setVisibility(View.GONE);
                viewHolder.tvCountDownTime.setVisibility(View.VISIBLE);
                viewHolder.tvCountDownTime.setText(StringUtils.formatTime(recentlys.get(position).CountDown));
                viewHolder.tvluckNumber.setVisibility(View.GONE);
            }else{
                viewHolder.imgRecentlyAnnounced.setVisibility(View.GONE);
                viewHolder.tvCountDownTime.setVisibility(View.GONE);
                viewHolder.tvWinnerName.setVisibility(View.VISIBLE);
                viewHolder.tvWinnerName.setText("获得者:" + recentlys.get(position).Winner);
                viewHolder.tvluckNumber.setVisibility(View.VISIBLE);
                viewHolder.tvluckNumber.setText("幸运码:"+recentlys.get(position).WinnerID);
            }

            Log.e("getView" , "getView");
            if (recentlys.get(position).CountDown == 0){
                recentlys.remove(position);
            }

            return convertView;
        }

    }

    public static class ViewHolder {
        public ImageView imgShopping;
        public ImageView imgRecentlyAnnounced;
        public TextView  tvShoppingName;
        public TextView  tvWinnerName;
        public TextView  tvAttendanceNumber;
        public TextView  tvluckNumber;
        public TextView  tvCountDownTime;
    }

    @Override
    public void onRefresh() {
        isRun = false;
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                p = 1;
                Log.e("onRefresh() isRun : " , isRun+"");
                if (!isRun){
                    getRecentlyAnnouncedData(p);
                }
            }
        }, 2500);
    }



    @Override
    public void onLoadMore() {
        isRun = false;
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.e("onLoadMore() isRun :" , isRun+"");
                if (!isRun){
                    getRecentlyAnnouncedData(++p);
                }
            }
        }, 2500);

    }


    // 获得数据后一定要调用onLoad()方法，否则刷新会一直进行，根本停不下来
    private void onLoad() {
        xListView.stopRefresh();//停止刷新
        xListView.stopLoadMore();//停止加载更多
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //停止倒计时的downrunnable
        isRun = false;
        isRefresh = false;
        great0Number = 0;
        handler2.sendEmptyMessageDelayed(STOP_THREAD ,0);
        if (countDownThread != null){
            countDownThread.quit();
        }
    }
}
