package com.example.cloudAndPurchasing.activity.activityshopping;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;
import com.example.cloudAndPurchasing.BaseFragmentActivity;
import com.example.cloudAndPurchasing.LocationApplication;
import com.example.cloudAndPurchasing.MyActivity;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.activity.activitysurpired.ActivityInframeText;
import com.example.cloudAndPurchasing.activity.activitysurpired.ActivityOldTimey;
import com.example.cloudAndPurchasing.activity.activitysurpired.ActivityRecord;
import com.example.cloudAndPurchasing.adapter.adaptershopping.AdapterParticipate;
import com.example.cloudAndPurchasing.adapter.adaptersurpired.AdapterPhoto;
import com.example.cloudAndPurchasing.banner.ADInfo;
import com.example.cloudAndPurchasing.banner.CycleViewPager;
import com.example.cloudAndPurchasing.customcontrol.listviewupdown.PullToRefreshLayout;
import com.example.cloudAndPurchasing.fragment.ShoppingCarFragment;
import com.example.cloudAndPurchasing.http.HttpApi;
import com.example.cloudAndPurchasing.http.HttpTransfeData;
import com.example.cloudAndPurchasing.http.Numbers;
import com.example.cloudAndPurchasing.http.SaveShared;
import com.example.cloudAndPurchasing.imageloder.Imageloderinit;
import com.example.cloudAndPurchasing.imageloder.Myimageloder;
import com.example.cloudAndPurchasing.kind.*;
import com.example.cloudAndPurchasing.manager.MyThreadPoolManager;
import com.example.cloudAndPurchasing.popupwindows.SharePopupwindows;
import com.example.cloudAndPurchasing.tiltle.TeaskBar;
import com.example.cloudAndPurchasing.times.CToast;
import com.example.cloudAndPurchasing.zhi.LogUtil;
import com.example.cloudAndPurchasing.zhi.ValuePrice;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/14 0014.
 */
public class ActivityGoodsDateil extends BaseFragmentActivity implements View.OnClickListener, PullToRefreshLayout.OnRefreshListener ,AdapterView.OnItemClickListener {
    private ArrayList<View> arrayList;
//    private ViewPager viewPager;
    private Button button_wangqi,button_tuwen,button_shaidan,
            button_fen,button_liji,button_shoppingcat,imageButton;
    private ProgressBar progressBar;
    private GoodsDateils goods,goods1;
    private PullToRefreshLayout pullToRefreshView;
    private Statese statese;
    private ListView listView;
    private CycleViewPager cycleViewPager;
    private Imageloderinit imageloderinit;
    private Button imageButton_ol;
    private String pid = null,id = null,productid = null;
    private int index = 1;
    private ViewPager viewPager;
    private Myimageloder myimageloder;
    private ArrayList<Record> arrayList1,arrayList2;
    private AdapterParticipate adapterParticipate;
    private SharedPreferences sharedPreferences;
    private LinearLayout linearLayout_ol,linearLayout;
    private int carnumber = 0;
    private int state ;
    private int limitcount = 0,surpcount = 0;
    private String currount = null;
    private TextView textView_name,textview_money,textView_xian,textView_zong,textView_sheng,textView_ming,textView_number;
    private CToast cToast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitygoodsdateillayout);

        //改变状态栏颜色
        TeaskBar.onSystemoutcolor(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        //初始化数据
        initData();
        //初始化控件
        initview();
    }
    public void initData(){
        LogUtil.i("ActivityGoodsDateil initData start.","");
        SharedPreferences.Editor sh = getSharedPreferences("data",MODE_PRIVATE).edit();
        sharedPreferences = getSharedPreferences("data",MODE_PRIVATE);
        myimageloder = new Myimageloder(this);
        arrayList2 = new ArrayList<Record>();
        index = 1;
        pid = getIntent().getStringExtra("outid");
        if (pid!=null){
            sh.putString("pid",pid);
        }
        String cloud = getIntent().getStringExtra("cloud");
        if (cloud != null){
            sh.putString("cloud",cloud);
        }
        sh.commit();
        imageloderinit = new Imageloderinit(this);
        try{
            MyThreadPoolManager.getInstance().execute(runnable);
            MyThreadPoolManager.getInstance().execute(runnableGoodsNumber);
            MyThreadPoolManager.getInstance().execute(runnableRecord);
        }catch (Exception e){
            LogUtil.e("ActivityGoodsDateil initData error:",e.toString());
        }finally {
            LogUtil.i("ActivityGoodsDateil initdata end.","");
        }
        goods = new GoodsDateils();

    }

    /**
     * 获取详情
     */
    private Runnable runnable = new Runnable() {
        @Override
        public synchronized void run() {
            id = sharedPreferences.getString("pid","");
            goods = HttpTransfeData.goodsdateilhttp(getApplicationContext(), id);
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putParcelable("gos",goods);
            message.what = Numbers.ONE;
            message.setData(bundle);
            handler.sendMessage(message);
        }
    };
    /**
     * 获取购物车商品数量
     */
    private Runnable runnableGoodsNumber = new Runnable() {
        @Override
        public synchronized void run() {
            Statese statese = new Statese();
            String token = SaveShared.tokenget(getApplication());
            id = sharedPreferences.getString("pid","");
            statese = HttpTransfeData.httpshoppingcat(getApplication(), statese, token, id);
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putParcelable("content_number",statese);
            message.setData(bundle);
            message.what = Numbers.FIVE;
            handler.sendMessage(message);
        }
    };

    /**
     * 初始化控件
     */
    public void initview(){
        linearLayout_ol = (LinearLayout)findViewById(R.id.linearlayout_shoppingdateil_loading);
        linearLayout = (LinearLayout)findViewById(R.id.linearlayout_shoppingdateil_delete);
        linearLayout_ol.setVisibility(View.VISIBLE);
        textView_number = (TextView)findViewById(R.id.textview_animation);
        pullToRefreshView = (PullToRefreshLayout)findViewById(R.id.pulltolistviewupdown);
        listView = (ListView)findViewById(R.id.listview_cayupublish);
        imageButton_ol = (Button)findViewById(R.id.imagebutton_add_shoppingcat);
        imageButton = (Button)findViewById(R.id.imagebutton_goodsdeatil_back);
        viewPager = (ViewPager)findViewById(R.id.viewpager_goodsdetildetail);
        progressBar = (ProgressBar)findViewById(R.id.progressbar_goodsdateil);
        textView_name = (TextView)findViewById(R.id.textview_goodsdetailname);
        textview_money = (TextView)findViewById(R.id.textview_goodsmoneny);
        textView_xian = (TextView)findViewById(R.id.textview_goodsdateilxiangou);
        textView_zong = (TextView)findViewById(R.id.textview_goodsdetailallpoeple);
        textView_sheng = (TextView)findViewById(R.id.textview_goodsdetailsurples);
        textView_ming = (TextView)findViewById(R.id.textview_goodsdetailshengming);
        button_fen = (Button)findViewById(R.id.button_goodsdateilfenxiang);
        button_wangqi = (Button)findViewById(R.id.button_goodsdetil_wangqi);
        button_tuwen = (Button)findViewById(R.id.button_goodsdetil_dateil);
        button_liji = (Button)findViewById(R.id.button_shoppingcat_lijiqiangou);
        button_shaidan = (Button)findViewById(R.id.button_goodsdetil_shai);
        button_shoppingcat = (Button)findViewById(R.id.button_newshopping_jiaru);
        //设置监听
        imageButton_ol.setOnClickListener(this);
        button_fen.setOnClickListener(this);
        imageButton.setOnClickListener(this);
        button_shoppingcat.setOnClickListener(this);
        button_shaidan.setOnClickListener(this);
        button_wangqi.setOnClickListener(this);
        button_liji.setOnClickListener(this);
        button_tuwen.setOnClickListener(this);
        pullToRefreshView.setOnRefreshListener(this);
        if (!StringUtils.isEmpty(SharePreferencesUlits.getString(this, Constants.CAR_NUMBER, Constants.NUll_VALUE))){
            textView_number.setText(SharePreferencesUlits.getString(this, Constants.CAR_NUMBER, Constants.NUll_VALUE));
        }else {
            textView_number.setText("0");
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.imagebutton_goodsdeatil_back:
                finish();
                break;
                /*try {
                    String back = sharedPreferences.getString("cloud","");
                    if (back.equals(String.valueOf(Numbers.THREE))){
                        intent.putExtra("cloud_ol",String.valueOf(Numbers.ONE));
                        intent.setClass(this, ActivityCloudRecord.class);
                    }else if (back.equals(String.valueOf(Numbers.FOUR))){
                        intent.putExtra("cloud_ol",String.valueOf(Numbers.TWO));
                        intent.setClass(this, ActivityCloudRecord.class);
                    }else if (back.equals(String.valueOf(Numbers.FIVE))){
                        intent.putExtra("cloud_ol",String.valueOf(Numbers.THREE));
                        intent.setClass(this, ActivityCloudRecord.class);
                    }else if (back.equals(String.valueOf(Numbers.TWO))){
                        intent.putExtra("zhi", ValuePrice.VALUE_ONE);
                        intent.setClass(this,MyActivity.class);
                    }else if (back.equals(String.valueOf(Numbers.SIX))){
                        intent.putExtra("cloud_ol",String.valueOf(Numbers.SIX));
                        intent.setClass(this, ActivityRecord.class);
                    }else if (back.equals(String.valueOf(Numbers.EIGHT))){
                        intent.putExtra("each_ol",getIntent().getStringExtra("content"));
                        intent.setClass(this,ActivityShoppingdata.class);
                    }else if (back.equals(String.valueOf(Numbers.NINE))){
                        intent.setClass(this, ActivityShoppingdetail.class);
                    }else if (back.equals(String.valueOf(Numbers.SEVEN))){
                        intent.setClass(this,ActivityShoppingTimeDetail.class);
                    }else {
                        intent.putExtra("zhi", ValuePrice.VALUE_TWO);
                        intent.setClass(this,MyActivity.class);
                    }
                    startActivity(intent);
                    finish();
                }catch (Exception e){
                    LogUtil.e("ActivityGooddateil imagebutton_goodsdeatil_back error:", e.toString());
                }
                break;*/
            case R.id.button_goodsdateilfenxiang:
                SharePopupwindows sharePopupwindows = new SharePopupwindows(this,v);
//                sharePopupwindows.show();
                break;
            case R.id.button_goodsdetil_dateil:    //图文详情
                intent.putExtra("goodsid",pid);
                intent.putExtra("back", String.valueOf(Numbers.ONE));
                intent.setClass(this, ActivityInframeText.class);
                startActivity(intent);
                break;
            case R.id.button_goodsdetil_shai:  //往期晒单
                intent.putExtra("goodsid",pid);
                intent.putExtra("sum", String.valueOf(Numbers.ONE));
                intent.setClass(this, ActivityPastPublish.class);
                startActivity(intent);
                break;
            case R.id.button_goodsdetil_wangqi:  //往期揭晓
                intent.putExtra("prid",pid);
                intent.putExtra("num", String.valueOf(Numbers.ONE));
                intent.setClass(this, ActivityOldTimey.class);
                startActivity(intent);
                break;
            case R.id.button_newshopping_jiaru:   //加入购物车
                String token1 = SaveShared.tokenget(this);
                state = Numbers.ONE;
                if (!TextUtils.isEmpty(token1)){
                    if (limitcount>0){
                        if (!StringUtils.isEmpty(currount)){
                            if (Integer.parseInt(currount)<limitcount){
                                linearLayout_ol.setVisibility(View.VISIBLE);
                                MyThreadPoolManager.getInstance().execute(runnableAddGoods);
                            }else {
                                cToast = CToast.makeText(getApplication(),"没有更多的商品了~",600);
                                cToast.show();
                            }
                        }
                    }else {
                        if (!TextUtils.isEmpty(currount)){
                            if (Integer.parseInt(currount)<surpcount){
                                MyThreadPoolManager.getInstance().execute(runnableAddGoods);
                            }else {
                                cToast = CToast.makeText(getApplication(),"没有更多的商品了~",600);
                                cToast.show();
                            }
                        }
                    }
                }else {
                    cToast = CToast.makeText(getApplication(),"请登录~",600);
                    cToast.show();
                }
                break;
            case R.id.imagebutton_add_shoppingcat:
                intent.putExtra("car",String.valueOf(Numbers.ONE));
                intent.putExtra("zhi", ValuePrice.VALUE_FOUR);
                intent.setClass(getApplication(), MyActivity.class);
                ((MyActivity) LocationApplication.getInstance().mActivity).setCurrentFargmentIndex(3);
                startActivity(intent);
                finish();
                break;
            case R.id.button_shoppingcat_lijiqiangou:   //立即抢购
                state = Numbers.TWO;
                String token = SaveShared.tokenget(this);
                if (!TextUtils.isEmpty(token)){

                    if (limitcount>0){
                        //判断购物车中的该商品的数量是否小于限购数量
                        if (Integer.parseInt(currount)<limitcount){
                            linearLayout_ol.setVisibility(View.VISIBLE);
                            MyThreadPoolManager.getInstance().execute(runnableAddGoods);
                        }else {
                            cToast = CToast.makeText(getApplication(),"没有更多的商品了~",600);
                            cToast.show();                            }
                    }else {
                        if (!StringUtils.isEmpty(currount)){
                            //判断购物车中的该商品的数量是否小于剩余数量
                            if (Integer.parseInt(currount)<surpcount){
                                MyThreadPoolManager.getInstance().execute(runnableAddGoods);
                            }else {
                                cToast = CToast.makeText(getApplication(),"没有更多的商品了~",600);
                                        cToast.show();
                            }

                        }
                    }
                }else {
                    cToast = CToast.makeText(getApplication(),"请登录~",600);
                    cToast.show();
                }
            break;
        }
    }
    /**
     * 向购物车中添加商品请求
     */
    private Runnable runnableAddGoods = new Runnable() {
        @Override
        public synchronized void run() {
            statese = new Statese();
            String token = SaveShared.tokenget(getApplication());
            String uid = SaveShared.uid(getApplication());
            String num = "1";
            statese = HttpTransfeData.goodsaddshoppingcat(getApplication(),id,uid,token,num);
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putParcelable("state",statese);
            message.what = Numbers.TWO;
            message.setData(bundle);
            handler.sendMessage(message);
        }
    };
    /**
     * 界面更新
     */
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == Numbers.ONE){
                linearLayout_ol.setVisibility(View.GONE);
                goods1 = new GoodsDateils();
                goods1 = msg.getData().getParcelable("gos");
                if (goods1!=null){
                    try {
                        linearLayout.setVisibility(View.GONE);
                        id = goods1.getID();
                        pid = goods1.getProductID();
                        if (goods1.getTotalCount()>0){
                            progressBar.setProgress(0);
                            progressBar.setMax(goods1.getTotalCount());
                            progressBar.setProgress((goods1.getTotalCount()-Integer.parseInt(goods1.getLeftCount())));
                        }else {
                            progressBar.setMax(100);
                            progressBar.setProgress(0);
                        }
                        textView_name.setText("(第"+goods1.getPNumber()+"期)"+goods1.getTitle());
//                    textview_money.setText("价值:￥"+goods.getPrice());
                        limitcount = goods1.getLimitCount();
                        if (!StringUtils.isEmpty(goods1.getLeftCount())){
                            surpcount = Integer.parseInt(goods1.getLeftCount());
                        }
                        if (goods1.getLimitCount() > 0){
                            textView_xian.setVisibility(View.VISIBLE);
                            textView_xian.setText("限购"+goods1.getLimitCount()+"人次");
                        }else {
                            textView_xian.setVisibility(View.GONE);
                        }
                        textView_zong.setText(goods1.getTotalCount()+"");
                        if (!StringUtils.isEmpty(goods1.getLeftCount())){
                            textView_sheng.setText(goods1.getLeftCount()+"");
                        }else {
                            textView_sheng.setText("0");
                        }
                        arrayList = new ArrayList<View>();
                        if (goods1.getShowImg()!=null){
                            if (!goods1.getShowImg().equals("null")){
                                for (int c = 0 ; c < goods.getShowImg().size();c++){
                                    View view = LayoutInflater.from(getApplication()).inflate(R.layout.photoimage,null);
                                    ImageView imageView_ol=(ImageView)view.findViewById(R.id.imageview_photo_this);
                                    imageloderinit.imageLoader.displayImage(HttpApi.tu_ool+goods.getShowImg().get(c), imageView_ol, imageloderinit.options);
                                    arrayList.add(view);
                                }
                                AdapterPhoto adapterPhoto = new AdapterPhoto(arrayList,getApplication());
                                viewPager.setAdapter(adapterPhoto);
                            }
                        }
//                    if (goods.getShowImg().size()>0){
//                        if (goods.getShowImg() != null){
//                            List<ImageView> views = new ArrayList<ImageView>();
//                            List<ADInfo> infos = new ArrayList<ADInfo>();
//                            cycleViewPager = (CycleViewPager) getSupportFragmentManager().
//                                    findFragmentById(R.id.viewpager_goodsdetildetail);
////
//                            for(int i = 0; i < goods.getShowImg().size(); i ++){
//                                ADInfo info = new ADInfo();
//                                info.setUrl(HttpApi.pople+goods.getShowImg().get(i));
////                            info.set(arrayListol.get(i).getCategory());
//                                infos.add(info);
//                            }
//                            // 将最后一个ImageView添加进来
//                            views.add(ViewFactory.getImageView(getApplication(), infos.get(infos.size() - 1).getUrl()));
//                            for (int i = 0; i < infos.size(); i++) {
//                                views.add(ViewFactory.getImageView(getApplication(), infos.get(i).getUrl()));
//                            }
//                            // 将第一个ImageView添加进来
//                            views.add(ViewFactory.getImageView(getApplication(), infos.get(0).getUrl()));
//                            // 设置循环，在调用setData方法前调用
//                            cycleViewPager.setCycle(true);
//                            // 在加载数据前设置是否循环
//                            cycleViewPager.setData(views, infos, mAdCycleViewListener);
//                            //设置轮播
//                            cycleViewPager.setWheel(true);
//
//                            //设置圆点指示图标组居中显示，默认靠右
//                            cycleViewPager.setIndicatorCenter();
//                        }
//                    }
                    }catch (Exception e){
                       LogUtil.e("ActivityGoodsdateil handler 461：",e.toString());
                    }
                }else {
                    linearLayout.setVisibility(View.VISIBLE);
                }

            }else if (msg.what == 2){
                statese = msg.getData().getParcelable("state");
                linearLayout_ol.setVisibility(View.GONE);
                if (statese!=null){
                    if (statese.getState().equals(String.valueOf(Numbers.ONE))){
                        MyThreadPoolManager.getInstance().execute(runnableCarNumber);
                        if (state == Numbers.ONE){
                            cToast = CToast.makeText(getApplication(), "加入购物车成功~", 600);
                            cToast.show();
                        }
                    }else {
                        cToast = CToast.makeText(getApplication(), statese.getMsg(), 600);
                        cToast.show();
                    }
                }
            }else if (msg.what == 4){
                arrayList1 = msg.getData().getParcelableArrayList("con_ol");
                if (arrayList1!=null){
                    if (arrayList1.size()>0){
                        adapterParticipate = new AdapterParticipate(myimageloder.imageLoader,myimageloder.options,getApplication(),arrayList1);
                        listView.setAdapter(adapterParticipate);
                        if (adapterParticipate.getCount()>12){
                            listView.setSelected(true);
                            listView.setSelection(adapterParticipate.getCount()-12);
                        }
                        listView.setOnItemClickListener(ActivityGoodsDateil.this);
                    }
                }
            }else if (msg.what == 5){
                statese = msg.getData().getParcelable("content_number");
                if (statese!=null){
                    if (statese.getState().equals(String.valueOf(Numbers.ONE))){
                        if (!TextUtils.isEmpty(statese.getMsg())){
                            if (statese.getMsg().equals("token失效")){
                                String user = SaveShared.loadong_name(getApplication());
                                if (!user.equals("")){
                                    MyThreadPoolManager.getInstance().execute(runnableSingle);
                                }
                            }else {
                                if (!statese.getData().equals(null)){
                                    if (!StringUtils.isEmpty(statese.getData())){
                                        currount = statese.getData();
                                        carnumber = Integer.parseInt(currount);
                                    }else {
                                        currount = "0";
                                    }
                                }
                            }
                        }
                    }else {
                        textView_number.setText("0");
                        currount = "0";
                    }
                }

            }else if (msg.what ==6){
                statese = new Statese();
                statese = msg.getData().getParcelable("this");
                if (statese!=null){
                    if (statese.getState().equals(String.valueOf(Numbers.ONE))){
                        MyThreadPoolManager.getInstance().execute(runnableGoodsNumber);
                    }
                }
            }else if (msg.what == 7){
                statese = msg.getData().getParcelable("content");
                if (statese != null){
                    if (statese.getState().equals(String.valueOf(Numbers.ONE))){
                        if (!StringUtils.isEmpty(statese.getData())){
                            textView_number.setText(statese.getData());
                            MyActivity.textView.setText(statese.getData());
                            LocationApplication.CAR_NUMBER = String.valueOf(statese.getData());
//                            ShoppingCarFragment.textView_ol.setText(statese.getData());
                            SharePreferencesUlits.saveString(getApplication(), Constants.CAR_NUMBER, statese.getData());
                        }else {
                            textView_number.setText("0");
                        }
                    }
                    if (state != Numbers.ONE){
                        ((MyActivity)LocationApplication.getInstance().mActivity).setCurrentFargmentIndex(3);
                        finish();
                    }
                }
            }
        }
    };
    /**
     *  参与记录
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        SharedPreferences.Editor sh1 = getSharedPreferences("data",MODE_PRIVATE).edit();
        sh1.putString("cloud",String.valueOf(Numbers.ZERO));
        sh1.commit();
        Intent intent = new Intent();
        intent.putExtra("memuid",arrayList1.get(position).getRecorduserid());
        intent.putExtra("name",arrayList1.get(position).getRecordnickname());
        intent.putExtra("address",arrayList1.get(position).getRecordaddress());
        intent.putExtra("level","1");
        intent.putExtra("other",String.valueOf(Numbers.TWO));
        intent.setClass(getApplication(),ActivityRecord.class);
        startActivity(intent);
       // finish();
    }


  /*  *//**
     * 设置返回控制
     * @param keyCode
     * @param event
     * @return
     *//*
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            Intent intent = new Intent();
            String back = sharedPreferences.getString("cloud","");
            if (back.equals(String.valueOf(Numbers.THREE))){
                intent.putExtra("cloud_ol", String.valueOf(Numbers.ONE));
                intent.setClass(this, ActivityCloudRecord.class);
            }else if (back.equals(String.valueOf(Numbers.FOUR))){
                intent.putExtra("cloud_ol",String.valueOf(Numbers.TWO));
                intent.setClass(this, ActivityCloudRecord.class);
            }else if (back.equals(String.valueOf(Numbers.FIVE))){
                intent.putExtra("cloud_ol",String.valueOf(Numbers.THREE));
                intent.setClass(this, ActivityCloudRecord.class);
            }else if (back.equals(String.valueOf(Numbers.TWO))){
                intent.putExtra("zhi", ValuePrice.VALUE_ONE);
                intent.setClass(this,MyActivity.class);
            }else if (back.equals(String.valueOf(Numbers.SIX))){
                intent.putExtra("cloud_ol",String.valueOf(Numbers.SIX));
                intent.setClass(this, ActivityRecord.class);
            }else if (back.equals(String.valueOf(Numbers.EIGHT))){
                intent.putExtra("each_ol",getIntent().getStringExtra("content"));
                intent.setClass(this,ActivityShoppingdata.class);
            }else if (back.equals(String.valueOf(Numbers.NINE))){
                intent.setClass(this, ActivityShoppingdetail.class);
            }else {
                intent.putExtra("zhi", ValuePrice.VALUE_TWO);
                intent.setClass(this,MyActivity.class);
            }
            startActivity(intent);
            finish();
            return true;
        }else {
            return  super.onKeyDown(keyCode, event);
        }
    }*/
    /**
     * banner广告图片点击事件
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
    /**
     *从新登陆
     */
    private Runnable runnableSingle = new Runnable() {
        @Override
        public synchronized void run() {
            String user = SaveShared.loadong_name(getApplication());
            String pass = SaveShared.loading_pass(getApplication());
            Statese statese = new Statese();
            statese = HttpTransfeData.httppostloding(getApplication(),
                    user,pass);
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putParcelable("this", statese);
            message.setData(bundle);
            message.what = Numbers.SIX;
            handler.sendMessage(message);
        }
    };

    /**
     * 销毁线程
     */
    @Override
    protected void onDestroy() {
        if (arrayList2 != null){
            arrayList2.clear();
        }
        if (arrayList1 != null){
            arrayList1.clear();
        }
        super.onDestroy();
    }


    /**
     * 获取往期记录数据
     */
    private Runnable runnableRecord = new Runnable() {
        @Override
        public synchronized void run() {
            SharedPreferences sharedPreferences = getSharedPreferences("data",MODE_PRIVATE);
            id = sharedPreferences.getString("pid","");
            arrayList1 = new ArrayList<Record>();
            arrayList1 = HttpTransfeData.httpjiexiao(getApplication(),id,index);
            arrayList2.addAll(arrayList1);
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("con_ol", arrayList2);
            message.setData(bundle);
            message.what= Numbers.FOUR;
            handler.sendMessage(message);
        }
    };
    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                arrayList2.clear();
                index=1;
                MyThreadPoolManager.getInstance().execute(runnableRecord);
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
                index+=1;
                MyThreadPoolManager.getInstance().execute(runnableRecord);
                pullToRefreshView.refreshFinish(PullToRefreshLayout.SUCCEED);
            }
        }.sendEmptyMessageDelayed(0,1500);
    }
    /**
     * 获取商品件数
     */
    private Runnable runnableCarNumber = new Runnable() {
        @Override
        public synchronized void run() {
            try {
                statese = new Statese();
                String token = SaveShared.tokenget(getApplicationContext());
                statese = HttpTransfeData.getcarnumberhttp(statese, getApplicationContext(), token);
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putParcelable("content",statese);
                message.what = Numbers.SEVEN;
                message.setData(bundle);
                handler.sendMessage(message);
            }catch (Exception e){
                LogUtil.e("MyActivity+Runable1",e.toString());
            }
        }
    };

}
