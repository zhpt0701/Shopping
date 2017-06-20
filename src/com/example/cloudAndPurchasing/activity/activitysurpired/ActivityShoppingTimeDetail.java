package com.example.cloudAndPurchasing.activity.activitysurpired;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.*;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;
import com.example.cloudAndPurchasing.BaseFragmentActivity;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.activity.ActivityShoppingdetail;
import com.example.cloudAndPurchasing.activity.activityshopping.ActivityGoodsDateil;
import com.example.cloudAndPurchasing.activity.activityshopping.ActivityPastPublish;
import com.example.cloudAndPurchasing.adapter.adaptershopping.AdapterParticipate;
import com.example.cloudAndPurchasing.adapter.adaptersurpired.AdapterPhoto;
import com.example.cloudAndPurchasing.banner.ADInfo;
import com.example.cloudAndPurchasing.banner.CycleViewPager;
import com.example.cloudAndPurchasing.customcontrol.listviewupdown.PullToRefreshLayout;
import com.example.cloudAndPurchasing.http.HttpApi;
import com.example.cloudAndPurchasing.http.HttpTransfeData;
import com.example.cloudAndPurchasing.http.Numbers;
import com.example.cloudAndPurchasing.http.SaveShared;
import com.example.cloudAndPurchasing.imageloder.Imageloderinit;
import com.example.cloudAndPurchasing.imageloder.Myimageloder;
import com.example.cloudAndPurchasing.kind.PurchaseDateil;
import com.example.cloudAndPurchasing.kind.Record;
import com.example.cloudAndPurchasing.kind.StringUtils;
import com.example.cloudAndPurchasing.manager.MyThreadPoolManager;
import com.example.cloudAndPurchasing.popupwindows.SharePopupwindows;
import com.example.cloudAndPurchasing.tiltle.TeaskBar;
import com.example.cloudAndPurchasing.times.CToast;
import com.example.cloudAndPurchasing.zhi.LogUtil;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TimeZone;

/**
 * Created by Administrator on 2016/4/11 0011.
 */
public class ActivityShoppingTimeDetail extends BaseFragmentActivity implements View.OnClickListener, PullToRefreshLayout.OnRefreshListener {
    /**
     * 自定义变量
     * index 页码
     * nextid 下期商品id
     */
    private TextView textView_jie, textView_shoppingname, textView_shoppingnumber,
            textView_canjia, textView_time, textView_shengming, textView_jinxing, textview_jie;
    private Button imageButton;
    private Button button_fenxiang, button_jisuan, button_wangqi,
             button_shai, button_tuwen, button_canyu;
    private ViewPager viewPager;
    private Imageloderinit imageloderinit;
    private CycleViewPager cycleViewPager;
    private PurchaseDateil purchaseDateil;
    private PullToRefreshLayout pullToRefreshView;
    private ListView listView;
    private int index =1;
    private TimeCount time;
    private LinearLayout linearLayout,linearlayout_delete;
    private String pid = null,id = null;
    private AdapterParticipate adapterParticipate;
    private ArrayList<Record> arrayList,records;
    private Myimageloder myimageloder;
    private String nextid = null;
    private SharedPreferences share;
    private CToast cToast;
    private ArrayList<View> arrayList_image;
    private int c = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityshoppingtimedetaillayout);
        //改变状态栏颜色
        TeaskBar.onSystemoutcolor(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

    }

    @Override
    protected void onResume() {
        super.onResume();
        //初始化数据
        initData();
        //初始化控件
        initView();
    }

    public void initData() {
        LogUtil.i("ActivityShoppingTImeDetail initDate start.","");
        myimageloder = new Myimageloder(this);
        records = new ArrayList<Record>();
        index = 1;
        myimageloder = new Myimageloder(getApplication());
        imageloderinit = new Imageloderinit(getApplication());
        SharedPreferences.Editor editor = getSharedPreferences("data",MODE_PRIVATE).edit();
        String id = getIntent().getStringExtra("outid");
        if (id != null){
           editor.putString("prosid",id);
        }
        String back = getIntent().getStringExtra("back");
        if (!TextUtils.isEmpty(back)){
            editor.putString("detailBack",back);
        }
        editor.commit();
        try{
            MyThreadPoolManager.getInstance().execute(runnableDateil);
            MyThreadPoolManager.getInstance().execute(runnablePartake);
        }catch (Exception e){
            LogUtil.e("ActivityShoppingTimeDetail initData error:",e.toString());
        }finally {
            LogUtil.i("ActivityShoppingTImeDetail initDate end.","");
        }

    }
    //倒计时详情获取
    private Runnable runnableDateil = new Runnable() {
        @Override
        public synchronized void run() {
            share = getSharedPreferences("data",MODE_PRIVATE);
            pid = share.getString("prosid", "");
            String token = SaveShared.tokenget(getApplication());
            String uid = SaveShared.uid(getApplication());
            purchaseDateil = HttpTransfeData.publishjsonhttp(Numbers.ONE, getApplication(), pid, token, uid);
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putParcelable("dateil", purchaseDateil);
            message.what = Numbers.ONE;
            message.setData(bundle);
            handler.sendMessage(message);
        }
    };

    public void initView() {
        linearlayout_delete = (LinearLayout)findViewById(R.id.linearlayout_shoppingdateil_delete);
        linearLayout = (LinearLayout)findViewById(R.id.linearlayout_shoppingdateil_loading);
        linearLayout.setVisibility(View.VISIBLE);
        pullToRefreshView = (PullToRefreshLayout)findViewById(R.id.pulltoupdatetimedetail);
        listView = (ListView)findViewById(R.id.listview_updatetimedetaildateil);
        listView.setOnScrollListener(new PauseOnScrollListener(imageloderinit.imageLoader,true,false));
        imageButton = (Button) findViewById(R.id.imagebutton_shoppingnewdetail_back);
        textView_jie = (TextView) findViewById(R.id.textview_shoppingdetail_jiexiaozhong);
        textView_jie.setText("揭晓中");
        textView_shoppingname = (TextView) findViewById(R.id.textview_shoppingnewdetail_name);
        textView_shoppingnumber = (TextView) findViewById(R.id.textview_shoppingnewdetail_numbers);
        textView_canjia = (TextView) findViewById(R.id.textview_Shoppingnewdetail_canjia);
        textView_time = (TextView) findViewById(R.id.textview_shoppingnewdetail_time);
        textView_shengming = (TextView) findViewById(R.id.textveiw_shoppingnewdetail_shengming);
        textView_jinxing = (TextView) findViewById(R.id.textview_jiexiao);
//        textView_jie = (TextView) findViewById(R.id.textview_jiexiao);
        viewPager = (ViewPager) findViewById(R.id.viewpager_shoppingnewdetail);
        button_fenxiang = (Button) findViewById(R.id.button_shoppingnewdetail_fenxiang);
        button_jisuan = (Button) findViewById(R.id.button_shoppingnewdetail_zhongjiangxiangqing);
        button_wangqi = (Button) findViewById(R.id.button_newshopping_wangqi);
        button_shai = (Button) findViewById(R.id.button_newshopping_shai);
        button_tuwen = (Button) findViewById(R.id.button_newshopping_dateil);
        button_canyu = (Button) findViewById(R.id.button_newshopping_jinxingzhong);
//        viewPager.setAdapter(adapterPhoto);
//        viewPager.setOnPageChangeListener(this);
        //设置监听
        imageButton.setOnClickListener(this);
        button_canyu.setOnClickListener(this);
        button_fenxiang.setOnClickListener(this);
        button_jisuan.setOnClickListener(this);
        button_shai.setOnClickListener(this);
        button_tuwen.setOnClickListener(this);
        button_wangqi.setOnClickListener(this);
        pullToRefreshView.setOnRefreshListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.imagebutton_shoppingnewdetail_back:
                finish();
                if (time != null){
                    time.cancel();
                }
                break;
            case R.id.button_shoppingnewdetail_fenxiang:
                SharePopupwindows sharePopupwindows = new SharePopupwindows(this, v);
//                sharePopupwindows.show();
                break;
            case R.id.button_shoppingnewdetail_zhongjiangxiangqing:
                if (c<0){
                    intent.putExtra("back",String.valueOf(Numbers.TWO));
                    intent.putExtra("pid", share.getString("prosid", ""));
                    intent.setClass(this, ActivityCount.class);
                    startActivity(intent);
                    time.cancel();
                    finish();
                }else {
                    cToast = CToast.makeText(getApplication(),"正在揭晓中~",600);
                    cToast.show();
                }
                break;
            case R.id.button_newshopping_wangqi:
                intent.putExtra("num",String.valueOf(Numbers.TWO));
                intent.putExtra("prid",id);
                intent.setClass(this, ActivityOldTimey.class);
                startActivity(intent);
                time.cancel();
                break;
            case R.id.button_newshopping_shai:
                intent.putExtra("goodsid",id);
                intent.putExtra("sum", String.valueOf(Numbers.THREE));
                intent.setClass(this, ActivityPastPublish.class);
                startActivity(intent);
                time.cancel();
                break;
            case R.id.button_newshopping_dateil:
                intent.putExtra("goodsid",id);
                intent.putExtra("back", String.valueOf(Numbers.THREE));
                intent.setClass(this, ActivityInframeText.class);
                startActivity(intent);
                time.cancel();
                break;
            case R.id.button_newshopping_jinxingzhong:
                intent.putExtra("outid", nextid);
                intent.putExtra("cloud", String.valueOf(Numbers.SEVEN));
                intent.setClass(this, ActivityGoodsDateil.class);
                startActivity(intent);
                time.cancel();
                break;
        }
    }

    /**
     * handler机制界面数据
     */
    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    linearLayout.setVisibility(View.GONE);
                    purchaseDateil = msg.getData().getParcelable("dateil");
                    if (purchaseDateil!=null) {
                        linearlayout_delete.setVisibility(View.GONE);
                        nextid = purchaseDateil.getNextPID();
                        id = purchaseDateil.getProductID();
                        if (!StringUtils.isEmpty(purchaseDateil.getNextPnumber())){
                            textView_jinxing.setText("第"+purchaseDateil.getNextPnumber()+"期正在火热进行中......");
                        }
                        textView_shengming.setText("声明:所有抽奖活动均与该公司无关");
                        textView_shoppingname.setText("(第"+purchaseDateil.getPNumber()+"期)"+purchaseDateil.getTitle());
                        textView_shoppingnumber.setText("总需:"+String.valueOf(purchaseDateil.getTotalCount()));
//                            textView_time.setText();
                        c = Integer.parseInt(purchaseDateil.getPublicDate());
                        time = new TimeCount(Long.parseLong(purchaseDateil.getPublicDate())*1000,50);
//                            time = new TimeCount(600000,1000);
                        time.start();
                        arrayList_image = new ArrayList<View>();
                        if (purchaseDateil.getPhotopath_ol()!= null){
                            if (purchaseDateil.getPhotopath_ol().size()>0){
                                if (!purchaseDateil.getPhotopath_ol().equals("null")){
                                    for (int i = 0 ; i < purchaseDateil.getPhotopath_ol().size();i++){
                                        View view1 = LayoutInflater.from(getApplication()).inflate(R.layout.photoimage, null);
                                        ImageView imageView1=(ImageView)view1.findViewById(R.id.imageview_photo_this);
                                        imageloderinit.imageLoader.displayImage(HttpApi.tu_ool+purchaseDateil.getPhotopath_ol().get(i), imageView1, imageloderinit.options);
                                        arrayList_image.add(view1);
                                    }
                                    AdapterPhoto adapterPhoto = new AdapterPhoto(arrayList_image,getApplication());
                                    viewPager.setAdapter(adapterPhoto);
                                }
                            }
//                        if (purchaseDateil.getPhotopath_ol().size()>0){
//                                List<ImageView> views = new ArrayList<ImageView>();
//                                List<ADInfo> infos = new ArrayList<ADInfo>();
//                                cycleViewPager = (CycleViewPager) getSupportFragmentManager().
//                                        findFragmentById(R.id.viewpager_shoppingnewdetail);
////
//                                for(int i = 0; i < purchaseDateil.getPhotopath_ol().size(); i ++){
//                                    ADInfo info = new ADInfo();
//                                    info.setUrl(HttpApi.tu_ool + purchaseDateil.getPhotopath_ol().get(i));
////                            info.set(arrayListol.get(i).getCategory());
//                                    infos.add(info);
//                                }
//                                // 将最后一个ImageView添加进来
//                                views.add(ViewFactory.getImageView(getApplication(), infos.get(infos.size() - 1).getUrl()));
//                                for (int i = 0; i < infos.size(); i++) {
//                                    views.add(ViewFactory.getImageView(getApplication(), infos.get(i).getUrl()));
//                                }
//                                // 将第一个ImageView添加进来
//                                views.add(ViewFactory.getImageView(getApplication(), infos.get(0).getUrl()));
//                                // 设置循环，在调用setData方法前调用
//                                cycleViewPager.setCycle(true);
//                                // 在加载数据前设置是否循环
//                                cycleViewPager.setData(views, infos, mAdCycleViewListener);
//                                //设置轮播
//                                cycleViewPager.setWheel(true);
//
//                                //设置圆点指示图标组居中显示，默认靠右
//                                cycleViewPager.setIndicatorCenter();
//                                arrayList = new ArrayList<View>();
//                                for (int c = 0; c < jsonArray.length(); c++) {
//                                    View view = LayoutInflater.from(getApplication()).inflate(R.layout.photoimage, null);
//                                    ImageView imageView_ol = (ImageView) view.findViewById(R.id.imageview_photo_this);
//                                    imageloderinit.imageLoader.displayImage(HttpApi.tu + jsonArray.getJSONObject(c).getString("ShowImg"), imageView_ol, imageloderinit.options);
//                                    arrayList.add(view);
//                                }
//                                adapterPhoto = new AdapterPhoto(arrayList, getApplication());
//                                viewPager.setAdapter(adapterPhoto);
                        }
                    }else {
                        linearlayout_delete.setVisibility(View.VISIBLE);
                    }
                 break;
                case 2:
                arrayList = msg.getData().getParcelableArrayList("partake");
                    if (arrayList!= null){
                        adapterParticipate = new AdapterParticipate(myimageloder.imageLoader,myimageloder.options,getApplication(),arrayList);
                        listView.setAdapter(adapterParticipate);
                        if (adapterParticipate.getCount()>12){
                            listView.setSelected(true);
                            listView.setSelection(adapterParticipate.getCount()-12);
                        }
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                try {
                                    Intent intent = new Intent();
                                    intent.putExtra("memuid",arrayList.get(position).getRecorduserid());
                                    intent.putExtra("name",arrayList.get(position).getRecordnickname());
                                    intent.putExtra("address",arrayList.get(position).getRecordaddress());
                                    intent.putExtra("level","1");
                                    intent.putExtra("other", String.valueOf(Numbers.FOUR));
                                    intent.setClass(getApplication(), ActivityRecord.class);
                                    startActivity(intent);
                                }catch (Exception e){
                                    LogUtil.e("ActivityShopppingTimeDetail handler error:",e.toString());
                                }
                            }
                        });
                    }
                break;
            }
        }
    };
    /**
     * 设置返回控制
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            finish();
            time.cancel();
            return true;
        }else {
            return  super.onKeyDown(keyCode, event);
        }
    }
    /**
     * 配置ImageLoder
     */
    private CycleViewPager.ImageCycleViewListener mAdCycleViewListener = new CycleViewPager.ImageCycleViewListener() {

        @Override
        public void onImageClick(ADInfo info, int position, View imageView) {
            if (cycleViewPager.isCycle()) {
//                Intent intent = new Intent();
//                intent.setClass(getActivity(),ActivityRD.class);
//                startActivity(intent);
//                getActivity().finish();
            }

        }

    };

    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                records.clear();
                index=1;
                MyThreadPoolManager.getInstance().execute(runnablePartake);
                pullToRefreshView.refreshFinish(PullToRefreshLayout.SUCCEED);
            }
        }.sendEmptyMessageDelayed(0,1500);
    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                index++;
                MyThreadPoolManager.getInstance().execute(runnablePartake);
                pullToRefreshView.refreshFinish(PullToRefreshLayout.SUCCEED);
            }
        }.sendEmptyMessageDelayed(0,1500);
    }

    /**
     *倒计时
     */
    private class TimeCount extends CountDownTimer{
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }
        //倒计时完毕触发
        @Override
        public void onFinish() {//计时完毕时触发
            textView_time.setText("0");
            Intent intent = new Intent();
            intent.putExtra("th_ol",share.getString("prosid",""));
            intent.setClass(getApplication(), ActivityShoppingdetail.class);
            startActivity(intent);
            finish();
        }
        //倒计时进行中
        @Override
        public void onTick(long millisUntilFinished){//计时过程显示
            textView_time.setClickable(false);
            SimpleDateFormat dateFormat = new SimpleDateFormat("mm分ss秒SS");
            dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
            if (Integer.parseInt(purchaseDateil.getPublicDate())<0){
                textView_time.setText("");
            }
            textView_time.setText(dateFormat.format(millisUntilFinished));
        }
    }
    //http获取参与记录数据
    private Runnable runnablePartake = new Runnable() {
        @Override
        public synchronized void run() {
            arrayList = new ArrayList<Record>();
            SharedPreferences share = getSharedPreferences("data",MODE_PRIVATE);
            pid = share.getString("pid","");
            arrayList = HttpTransfeData.httpjiexiao(getApplication(),pid,index);
            if (arrayList != null){
                records.addAll(arrayList);
            }
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("partake", records);
            message.setData(bundle);
            message.what= Numbers.TWO;
            handler.sendMessage(message);
        }
    };

    @Override
    protected void onDestroy() {
        if (arrayList != null){
            arrayList.clear();
        }
        if (arrayList_image != null){
            arrayList_image.clear();
        }
        if (records != null){
            records.clear();
        }
        super.onDestroy();
    }
}
