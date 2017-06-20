package com.example.cloudAndPurchasing.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;
import com.example.cloudAndPurchasing.BaseFragmentActivity;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.activity.activityshopping.ActivityGoodsDateil;
import com.example.cloudAndPurchasing.activity.activityshopping.ActivityPastPublish;
import com.example.cloudAndPurchasing.activity.activitysurpired.ActivityCount;
import com.example.cloudAndPurchasing.activity.activitysurpired.ActivityInframeText;
import com.example.cloudAndPurchasing.activity.activitysurpired.ActivityOldTimey;
import com.example.cloudAndPurchasing.activity.activitysurpired.ActivityRecord;
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
import com.example.cloudAndPurchasing.zhi.LogUtil;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/11 0011.
 */
public class ActivityShoppingdetail extends BaseFragmentActivity implements View.OnClickListener, ViewPager.OnPageChangeListener, PullToRefreshLayout.OnRefreshListener , AdapterView.OnItemClickListener {
    /**
     * id
     * pid
     * prodactId
     * nextId 下期id
     * nextNumber 下期期号
     * index 页码
     */
    private TextView textView_jie,textView_name,textView_canyu
            ,textView_shifu,textView_zhogjianzhe,textView_id,
            textView_reci,textView_shijian,textView_shengming,
            textView_winning,textView_jinxingzhong;
    private Button button,button_ol,button_ool,button_jx,button_sd,button_tw;
    private PurchaseDateil purchaseDateil,purchaseDateil_ol;
    private Button imageButton;
//    private ViewPager viewPager;
    private ImageView imageView;
    private ListView listView;
    private ViewPager viewPager;
    private AdapterPhoto adapterPhoto;
    private ArrayList<View> views;
    private Imageloderinit imageloderinit;
    private PullToRefreshLayout pullToRefreshView;
    private String id ,pid ,purchaseDateilID
            ,prodactId ,nextId ,purchaseDateilNewId,nextNumber ;
    private int index = 1;
    private int STATENUMBER = 0;
    private CycleViewPager cycleViewPager;
    private AdapterParticipate adapterParticipate;
    private Myimageloder myimageloder;
    private ArrayList<Record> arrayList,records;
    private SharedPreferences sharedPreferences;
    private LinearLayout linearLayout,linearLayout_delete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityshoppingdetaillayout);
        //设置状态栏背景色
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

    public void initData(){
        LogUtil.i("ActivityShoppingdetail initData start.","");
        sharedPreferences = getSharedPreferences("data",MODE_PRIVATE);
        SharedPreferences.Editor sh = getSharedPreferences("data",MODE_PRIVATE).edit();
        myimageloder = new Myimageloder(this);
        imageloderinit = new Imageloderinit(this);
        records = new ArrayList<Record>();
        index = 1;
        pid = getIntent().getStringExtra("th_ol");
        if (pid!=null){
            sh.putString("pid_ol",pid);
        }
        String back = getIntent().getStringExtra("back");
        if (back != null){
            sh.putString("back_ol",back);
        }
        sh.commit();
        try{
            MyThreadPoolManager.getInstance().execute(runnable);
            MyThreadPoolManager.getInstance().execute(runnableDetail);
            MyThreadPoolManager.getInstance().execute(runnableRecord);
        }catch (Exception e){
            LogUtil.e("ActivityShoppingdetail initdata error:",e.toString());
        }finally {
            LogUtil.i("ActivityShoppingDetail initData end.","");
        }
        purchaseDateil = new PurchaseDateil();
    }
    /**
     * 参与记录
     */
    private Runnable runnableRecord = new Runnable() {
        @Override
        public synchronized void run() {
            arrayList = new ArrayList<Record>();
            id = sharedPreferences.getString("pid_ol","");
            arrayList = HttpTransfeData.httpjiexiao(getApplication(), id, index);
            if (arrayList != null){
                records.addAll(arrayList);
            }
            Message message2 = new Message();
            Bundle bundle2 = new Bundle();
            bundle2.putParcelableArrayList("con_ol", records);
            message2.setData(bundle2);
            message2.what= Numbers.THREE;
            handler.sendMessage(message2);
        }
    };
    /**
     * 中奖者
     */
    private Runnable runnableDetail = new Runnable() {
        @Override
        public synchronized void run() {
            purchaseDateil_ol = new PurchaseDateil();
            id = sharedPreferences.getString("pid_ol","");
            purchaseDateil_ol = HttpTransfeData.thewinninghttp(getApplication(),id);
            Message message1 = new Message();
            Bundle bundle1 = new Bundle();
            bundle1.putParcelable("win", purchaseDateil_ol);
            message1.setData(bundle1);
            message1.what = Numbers.TWO;
            handler.sendMessage(message1);
        }
    };
    /**
     * 获取揭晓详情
     */
    private Runnable runnable = new Runnable() {
        @Override
        public synchronized void run() {
            purchaseDateil = new PurchaseDateil();
            id = sharedPreferences.getString("pid_ol","");
            String token = SaveShared.tokenget(getApplication());
            String uid = SaveShared.uid(getApplication());
            purchaseDateil = HttpTransfeData.publishjsonhttp(Numbers.TWO,getApplication(), id, token, uid);
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putParcelable("dateil", purchaseDateil);
            message.what = Numbers.ONE;
            message.setData(bundle);
            handler.sendMessage(message);
        }
    };
    public void initView(){
        linearLayout = (LinearLayout)findViewById(R.id.linearlayout_shoppingdateil_loading);
        linearLayout_delete = (LinearLayout)findViewById(R.id.linearlayout_shoppingdateil_delete);
        linearLayout.setVisibility(View.VISIBLE);
        pullToRefreshView = (PullToRefreshLayout)findViewById(R.id.pulltouoanddownshopping);
        listView = (ListView)findViewById(R.id.listview_updategoodsdateil);
        listView.setOnScrollListener(new PauseOnScrollListener(imageloderinit.imageLoader,true,false));
        textView_jie = (TextView)findViewById(R.id.textview_shoppingdetail_jiexiao);
        textView_name = (TextView)findViewById(R.id.textview_shoppingdetail);
        textView_canyu = (TextView)findViewById(R.id.textview_shoppingdetail_numbers);
        textView_shifu = (TextView)findViewById(R.id.textview_Shoppingdetail_canjia);
        textView_zhogjianzhe = (TextView)findViewById(R.id.textview_zhongjiangzhe);
        textView_id = (TextView)findViewById(R.id.textview_userid);
        textView_reci = (TextView)findViewById(R.id.textveiw_caanyurenci);
        textView_shijian = (TextView)findViewById(R.id.textview_jiexiaoshijian);
        textView_shengming = (TextView)findViewById(R.id.textveiw_shopping_shengming);
        textView_winning = (TextView)findViewById(R.id.textview_zhongjianghaoma);
        textView_jinxingzhong = (TextView)findViewById(R.id.textview_jinxingzhongthi);
        imageView = (ImageView)findViewById(R.id.imageview_shopping_thisphoto);
        textView_jie.setText("已揭晓");
//        textView_shifu.setText("您还没有参加，赶紧参加吧!");
        textView_shengming.setText("声明:所有抽奖活动均与该公司无关");
        button = (Button)findViewById(R.id.button_shoppingdetail_fenxiang);
        button_ol = (Button)findViewById(R.id.button_zhongjiangxiangqing);
        button_ool = (Button)findViewById(R.id.button_jinxingzhong);
        button_jx = (Button)findViewById(R.id.button_detailshopping_wangqi);
        button_sd = (Button)findViewById(R.id.button_detailhopping_shai);
        button_tw = (Button)findViewById(R.id.button_detailhopping_touwen);
        imageButton = (Button)findViewById(R.id.imagebutton_shoppingdetail_oool);
        viewPager = (ViewPager)findViewById(R.id.viewpager_shoppingdetailth);
        //设置监听事件
        button_tw.setOnClickListener(this);
//        viewPager.setOnPageChangeListener(this);
        imageButton.setOnClickListener(this);
        button_jx.setOnClickListener(this);
        button_sd.setOnClickListener(this);
        button.setOnClickListener(this);
        button_ol.setOnClickListener(this);
        button_ool.setOnClickListener(this);
        pullToRefreshView.setOnRefreshListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.button_shoppingdetail_fenxiang:
                SharePopupwindows sharePopupwindows = new SharePopupwindows(this,v);
//                sharePopupwindows.show();
                break;
            case R.id.button_zhongjiangxiangqing:      //查看计算详情
                intent.putExtra("pid", id);
                intent.putExtra("back",String.valueOf(Numbers.ONE));
                intent.setClass(this, ActivityCount.class);
                startActivity(intent);
                //finish();
                break;
            case R.id.button_jinxingzhong: // 立即前往
                if (!TextUtils.isEmpty(nextNumber)){
                    if (!nextNumber.equals("0")){
                        intent.putExtra("outid", nextId);
                        intent.putExtra("index" , Numbers.TWO);
                        intent.putExtra("cloud", String.valueOf(Numbers.NINE));
                        intent.setClass(this, ActivityGoodsDateil.class);
                        startActivity(intent);
                    }else {

                        Toast.makeText(getApplication(),"下期商品正在上传...",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getApplication(),"下期商品正在上传...",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.imagebutton_shoppingdetail_oool:
                finish();
                break;
//                if (getIntent().getStringExtra("back") =="1"){
//                    intent.putExtra("zhi", ValuePrice.VALUE_THREE);
//                    intent.setClass(this,MyActivity.class);
//                }else{
//                    intent.putExtra("prid",id_ol);
//                    intent.putExtra("num",String.valueOf(Numbers.THREE));
//                    intent.setClass(this,ActivityOldTimey.class);
//                }
                /*try {
                    String back_ol = sharedPreferences.getString("back_ol","");
                    if (back_ol.equals(String.valueOf(Numbers.THREE))){
                        intent.putExtra("cloud_ol",String.valueOf(Numbers.ONE));
                        intent.setClass(this, ActivityCloudRecord.class);
                    }else if (back_ol.equals(String.valueOf(Numbers.FOUR))){
                        intent.putExtra("cloud_ol",String.valueOf(Numbers.TWO));
                        intent.setClass(this, ActivityCloudRecord.class);
                    }else if (back_ol.equals(String.valueOf(Numbers.SIX))){
                        intent.putExtra("cloud_ol",String.valueOf(Numbers.SEVEN));
                        intent.setClass(this, ActivityRecord.class);
                    }else if (back_ol.equals(String.valueOf(Numbers.FIVE))){
                        intent.putExtra("cloud_ol",String.valueOf(Numbers.THREE));
                        intent.setClass(this, ActivityCloudRecord.class);
                    }else if (back_ol.equals(String.valueOf(Numbers.EIGHT))){
                        intent.putExtra("cloud_ol",String.valueOf(Numbers.EIGHT));
                        intent.setClass(this,ActivityRecord.class);
                    }else if (back_ol.equals(String.valueOf(Numbers.NINE))){
                        intent.setClass(this, ActivityWinningRecord.class);
                    }else if (back_ol.equals(String.valueOf(Numbers.TEN))){
                        intent.setClass(this,ActivityOldTimey.class);
                    }else {
                        intent.putExtra("zhi", ValuePrice.VALUE_THREE);
                        intent.setClass(this,MyActivity.class);
                    }
                    startActivity(intent);
                    finish();
                }catch (Exception e){
                    LogUtil.e("ActivityShoppingdetail onclick error:",e.toString());
                }
                break;*/
            case R.id.button_detailshopping_wangqi:    //往期揭晓
                intent.putExtra("prid",prodactId);
                intent.putExtra("num",String.valueOf(Numbers.THREE));
                intent.setClass(this,ActivityOldTimey.class);
                startActivity(intent);
                //finish();
                break;
            case R.id.button_detailhopping_shai:        //往期晒单
                intent.putExtra("goodsid",prodactId);
                intent.putExtra("sum",String.valueOf(Numbers.TWO));
                intent.setClass(this, ActivityPastPublish.class);
                startActivity(intent);
                break;
            case R.id.button_detailhopping_touwen:      //图文详情
                intent.putExtra("goodsid",prodactId);
                intent.putExtra("back", String.valueOf(Numbers.TWO));
                intent.setClass(this, ActivityInframeText.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }
    @Override
    public void onPageSelected(int i) {
        switch (i){

        }
    }
    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    linearLayout.setVisibility(View.GONE);
                    purchaseDateil = new PurchaseDateil();
                    purchaseDateil = msg.getData().getParcelable("dateil");
                    if (purchaseDateil !=null) {
                        try {
                            linearLayout_delete.setVisibility(View.GONE);
                            prodactId = purchaseDateil.getProductID();
                            textView_name.setText("(第"+purchaseDateil.getPNumber()+"期)"+purchaseDateil.getTitle());
                            textView_canyu.setText("总需:"+purchaseDateil.getTotalCount());
                            nextId = purchaseDateil.getNextPID();
                            nextNumber = purchaseDateil.getNextPnumber();
                            if (!purchaseDateil.getNextPnumber().equals("null")){
                                if (!purchaseDateil.getNextPnumber().equals("0")){
                                    button_ool.setVisibility(View.VISIBLE);
                                    textView_jinxingzhong.setText("第"+purchaseDateil.getNextPnumber()+"期奖品正在火热进行中......");
                                }else {
                                    button_ool.setVisibility(View.GONE);
                                    textView_jinxingzhong.setText("下期商品正在上传...");
                                }
                            }else {
                                button_ool.setVisibility(View.GONE);
                                textView_jinxingzhong.setText("下期商品正在上传...");
                            }
                            views = new ArrayList<View>();
                            if (purchaseDateil.getPhotopath_ol()!= null){
                                if (purchaseDateil.getPhotopath_ol().size()>0){
                                    if (!purchaseDateil.getPhotopath_ol().equals("null")){
                                        for (int i = 0 ; i < purchaseDateil.getPhotopath_ol().size();i++){
                                            View view1 = LayoutInflater.from(getApplication()).inflate(R.layout.photoimage, null);
                                            ImageView imageView1=(ImageView)view1.findViewById(R.id.imageview_photo_this);
                                            imageloderinit.imageLoader.displayImage(HttpApi.tu_ool+purchaseDateil.getPhotopath_ol().get(i), imageView1, imageloderinit.options);
                                            views.add(view1);
                                        }
                                        AdapterPhoto adapterPhoto = new AdapterPhoto(views,getApplication());
                                        viewPager.setAdapter(adapterPhoto);
                                    }
                                }

//                            if (purchaseDateil.getPhotopath_ol().size()>0){
//                                List<ImageView> views = new ArrayList<ImageView>();
//                                List<ADInfo> infos = new ArrayList<ADInfo>();
//                                cycleViewPager = (CycleViewPager) getSupportFragmentManager().
//                                        findFragmentById(R.id.viewpager_shoppingdetailth);
//
//                                for(int i = 0; i < purchaseDateil.getPhotopath_ol().size(); i ++){
//                                    ADInfo info = new ADInfo();
//                                    info.setUrl(HttpApi.tu_ool+purchaseDateil.getPhotopath_ol().get(i));
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
//                            }
                            }
                            purchaseDateilNewId= purchaseDateil.getID();
                            prodactId = purchaseDateil.getProductID();
                        }catch (Exception e){
                            LogUtil.e("ActivityShoppingdetail handler error:",e.toString());
                        }

                    }else {
                        linearLayout_delete.setVisibility(View.VISIBLE);
                    }
                    break;
                case 2:
                    purchaseDateil_ol = new PurchaseDateil();
                    purchaseDateil_ol = msg.getData().getParcelable("win");
                    if (purchaseDateil_ol!=null){
                        purchaseDateilID = purchaseDateil_ol.getProductID();
                        if (!StringUtils.isEmpty(purchaseDateil_ol.getWinner())){
                            SpannableString spannableString = new SpannableString("中奖者:"+purchaseDateil_ol.getWinner());
                            spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.blue)),4,4+purchaseDateil_ol.getWinner().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            textView_zhogjianzhe.setText(spannableString);
                        }else {
                            if (STATENUMBER<3){
                                STATENUMBER++;
                                MyThreadPoolManager.getInstance().execute(runnableDetail);
                            }
                        }
                        if (!StringUtils.isEmpty(String.valueOf(purchaseDateil_ol.getAllpople()))){
                            SpannableString spannableString1 = new SpannableString("本期参与:"+String.valueOf(purchaseDateil_ol.getAllpople())+"人次");
                            spannableString1.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.tiltle)),5,4+String.valueOf(purchaseDateil.getAllpople()).length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            textView_reci.setText(spannableString1);
                        }else {
                            SpannableString spannableString1 = new SpannableString("本期参与:"+"0"+"人次");
                            spannableString1.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.tiltle)),5,5+String.valueOf(purchaseDateil.getTradingCount()).length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            textView_reci.setText(spannableString1);
                        }
                        if (!StringUtils.isEmpty(purchaseDateil_ol.getAdress())){
                            textView_id.setText("用户ID:"+purchaseDateil_ol.getWinnerID()+"("+purchaseDateil_ol.getAdress()+")");
                        }else {
                            if (!StringUtils.isEmpty(purchaseDateil_ol.getWinnerID())){
                                textView_id.setText("用户ID:"+purchaseDateil_ol.getWinnerID());
                            }else {
                                textView_id.setText("用户ID:");
                            }
                        }
                        if (!StringUtils.isEmpty(purchaseDateil_ol.getPublicDate())){
                            textView_shijian.setText("揭晓时间:"+purchaseDateil_ol.getPublicDate());
                        }else {
                            textView_shijian.setText("揭晓时间:");
                        }
                        if (!StringUtils.isEmpty(purchaseDateil_ol.getLuckyNumber())){
                            textView_winning.setText("中奖号码:"+purchaseDateil_ol.getLuckyNumber());
                        }else {
                            textView_winning.setText("中奖号码:");
                        }
                        if (purchaseDateil_ol.getPhotopath() != null){
                            if (!purchaseDateil_ol.getPhotopath().equals("null")){
//                                byte[] bytes = Base64.decode(purchaseDateil_ol.getPhotopath(), Base64.DEFAULT);
//                                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
//                                Bitmap bitmap1 = Tailoring.phototailoring(bitmap);
//                                imageView.setImageBitmap(bitmap1);
                                myimageloder.imageLoader.displayImage(HttpApi.yu+purchaseDateil_ol.getPhotopath(), imageView, myimageloder.options);
                            }
                        }
                    }
                    break;
                case 3:
                    arrayList = msg.getData().getParcelableArrayList("con_ol");
                    if (arrayList!=null){
                        adapterParticipate = new AdapterParticipate(myimageloder.imageLoader,myimageloder.options,getApplication(),arrayList);
                        listView.setAdapter(adapterParticipate);
                        listView.setOnItemClickListener(ActivityShoppingdetail.this);
                    }
                    break;
            }
        }
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        try {
            Intent intent = new Intent();
            intent.putExtra("memuid",arrayList.get(position).getRecorduserid());
            intent.putExtra("name",arrayList.get(position).getRecordnickname());
            intent.putExtra("address",arrayList.get(position).getRecordaddress());
            intent.putExtra("level","1");
            intent.putExtra("other",String.valueOf(Numbers.THREE));
            intent.setClass(getApplication(),ActivityRecord.class);
            startActivity(intent);
        }catch (Exception e){
            LogUtil.e("ActivityShoppingdetail handler error:",e.toString());
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
    public void onPageScrollStateChanged(int i) {

    }
    //下拉刷新
    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                records.clear();
                index = 1;
                try {
                    MyThreadPoolManager.getInstance().execute(runnableRecord);
                }catch (Exception e){
                    LogUtil.e("ActivityShoppingdetail onrefresh error:",e.toString());
                }
                pullToRefreshView.refreshFinish(PullToRefreshLayout.SUCCEED);
            }
        }.sendEmptyMessageDelayed(0,1500);
    }
    //上拉加载
    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                index++;
                try {
                    MyThreadPoolManager.getInstance().execute(runnableRecord);
                }catch (Exception e){
                    LogUtil.e("ActivityShoppingdetail onLoadMore error:",e.toString());
                }
                pullToRefreshView.refreshFinish(PullToRefreshLayout.SUCCEED);
            }
        }.sendEmptyMessageDelayed(0,1500);
    }

    /*@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            Intent intent = new Intent();
            String back_ol = sharedPreferences.getString("back_ol","");
            if (back_ol.equals(String.valueOf(Numbers.THREE))){
                intent.putExtra("cloud_ol",String.valueOf(Numbers.ONE));
                intent.setClass(this, ActivityCloudRecord.class);
            }else if (back_ol.equals(String.valueOf(Numbers.FOUR))){
                intent.putExtra("cloud_ol",String.valueOf(Numbers.TWO));
                intent.setClass(this, ActivityCloudRecord.class);
            }else if (back_ol.equals(String.valueOf(Numbers.SIX))){
                intent.putExtra("cloud_ol",String.valueOf(Numbers.SEVEN));
                intent.setClass(this, ActivityRecord.class);
            }else if (back_ol.equals(String.valueOf(Numbers.FIVE))){
                intent.putExtra("cloud_ol",String.valueOf(Numbers.THREE));
                intent.setClass(this, ActivityCloudRecord.class);
            }else if (back_ol.equals(String.valueOf(Numbers.EIGHT))){
                intent.putExtra("cloud_ol",String.valueOf(Numbers.EIGHT));
                intent.setClass(this,ActivityRecord.class);
            }else if (back_ol.equals(String.valueOf(Numbers.NINE))){
                intent.setClass(this, ActivityWinningRecord.class);
            }else if (back_ol.equals(String.valueOf(Numbers.TEN))){
                intent.setClass(this,ActivityOldTimey.class);
            }else {
                intent.putExtra("zhi", ValuePrice.VALUE_THREE);
                intent.setClass(this,MyActivity.class);
            }
            startActivity(intent);
            finish();
            return true;
        }else {
            return super.onKeyDown(keyCode, event);
        }
    }*/
    @Override
    protected void onDestroy() {
        if (arrayList != null){
            arrayList.clear();
        }
        if (records != null){
            records.clear();
        }
        super.onDestroy();
    }


}
