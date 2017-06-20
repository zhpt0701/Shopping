package com.example.cloudAndPurchasing.fragment;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.BitmapDrawable;
import android.os.*;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.*;
import android.widget.*;
import com.example.cloudAndPurchasing.LocationApplication;
import com.example.cloudAndPurchasing.MyActivity;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.activity.activitycloud.activityfriend.ActivityAddFriend;
import com.example.cloudAndPurchasing.activity.activitymain.ActivityCity;
import com.example.cloudAndPurchasing.activity.activitymain.ActivityCloudRoom;
import com.example.cloudAndPurchasing.activity.activitymain.ActivityRotaryTable;
import com.example.cloudAndPurchasing.activity.activitymain.ActivitySingle;
import com.example.cloudAndPurchasing.activity.activityshopping.ActivityGoodsDateil;
import com.example.cloudAndPurchasing.activity.activitysurpired.ActivityShoppingTimeDetail;
import com.example.cloudAndPurchasing.adapter.adaptermain.AdapterCityPopupone;
import com.example.cloudAndPurchasing.adapter.adaptermain.AdapterClassity;
import com.example.cloudAndPurchasing.adapter.adaptermain.AdapterNewGridView;
import com.example.cloudAndPurchasing.banner.ADInfo;
import com.example.cloudAndPurchasing.banner.CycleViewPager;
import com.example.cloudAndPurchasing.banner.ViewFactory;
import com.example.cloudAndPurchasing.customcontrol.listviewupdown.PullToRefreshLayout;
import com.example.cloudAndPurchasing.http.HttpApi;
import com.example.cloudAndPurchasing.http.HttpTransfeData;
import com.example.cloudAndPurchasing.http.Numbers;
import com.example.cloudAndPurchasing.http.SaveShared;
import com.example.cloudAndPurchasing.imageloder.BannerImageLoder;
import com.example.cloudAndPurchasing.imageloder.Imageloderinit;
import com.example.cloudAndPurchasing.kind.*;
import com.example.cloudAndPurchasing.manager.MyThreadPoolManager;
import com.example.cloudAndPurchasing.sqlliste.DataStore;
import com.example.cloudAndPurchasing.times.CToast;
import com.example.cloudAndPurchasing.zhi.LogUtil;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.StringCallback;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangpengtao on 2016/3/21 0021.
 */
public class MainFragment extends Fragment implements RadioGroup.OnCheckedChangeListener, View.OnClickListener, AdapterView.OnItemClickListener, PullToRefreshLayout.OnRefreshListener  {
    private RadioGroup radioGroup_ol;
    private GridView gridView,gridView_ol,gridView1;
    private PullToRefreshLayout pullToRefreshView;
    private AdapterNewGridView adapterNewGridView;
    private AdapterClassity adapterClassity;
    private ArrayList<Publish> arrayList,arrayList_time;
    private ArrayList<Goods> arrayList_goods,arrayList_ol,goodses;
    private ArrayList<ImageView> imageViews;
    private Button imageButton_friend,btn_location_cancel,btn_location_ok;
    private ViewPager viewPager;
    private boolean flag = false;
    private Bundle bundle,bundle1,bundle2;
    private Message messageBanner,messageRencent,messageCategroy;
    private ArrayList<View> arrayListview;
    private Imageloderinit imageloderinit;
    private TextView textView1,textview_locationCity;
    private ArrayList<TZone> arrayList_tZone,tZones;
    private PopupWindow popupWindow,popupWindowLocation;
    private ArrayList<Image> arrayListol;
    private Statese statese;
    private ImageView imageView;
    private String pid=null,th = "0";
    private int index = 0;
    private CycleViewPager cycleViewPager;
    private View view1,view_location;
    private String content = null;
    private Button button1,button_FJ ,Button_p,button_single,button_food,buttonall,button;
    public static String[] sh = {"New","Faster","Hot","PriceDesc","PriceAsc"};
    private FragmentManager fm;
    private LinearLayout linearLayout,linearLayout_ol;
    private FragmentTransaction ft;
    private IntentFilter intentFilter;
    //private ThreaderReceiver threaderReceiver;
    private String number_ol = null;
    private LocalBroadcastManager localBroadcastManager;
    private BannerImageLoder bannerImageLoder;
//    private LinearLayout linearLayout_ol;
    private String limitcount = null,surpcount = null;
    private LinearLayout linearLayout_city;
    private CToast cToast;
    private Handler mhandler;
    private ArrayList<RecentlyAnnounced.DataBean> recentlys;
    private AlertDialog.Builder alertDialog;
    private AlertDialog window;
    private Dialog dialog;
    private GestureDetector detector;
    private RecentlyAnnouncedAdapter announcedAdapter;
    private Handler countDownTimeHandler;
    private Handler countDownhandler;
    private HandlerThread countDownThread;
    private HandlerThread handlerThread;
    private final int STOP_THREAD = 11;
    private boolean isRefresh = false;
    private int great0Number = 0;
    Handler handler2 = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    announcedAdapter.notifyDataSetChanged();
                    isRefresh = false;
                    if (recentlys.size() > 0){
                        imageView.setVisibility(View.GONE);
                        for (RecentlyAnnounced.DataBean data : recentlys){
                            if (data.CountDown > 0 ){
                                isRefresh = true;
                            }
                        }

                        if (isRefresh){
                            handler2.sendEmptyMessageDelayed(1,99);
                        }else{
                            imageView.setVisibility(View.VISIBLE);
                        }


                    }else {
                        isRun = false;
                        imageView.setVisibility(View.VISIBLE);
                    }

                    break;
                case STOP_THREAD:
                    if (countDownhandler != null){
                        countDownhandler.removeCallbacksAndMessages(null);
                    }
                    break;
            }
        }

    };
    private CountDownRunnable downRunnable;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        great0Number = 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragmentmain_layout, null);
        if (LocationApplication.STATENUMBER == Numbers.ONE){
            if (!StringUtils.isEmpty(SharePreferencesUlits.getString(getActivity(),Constants.ADDRESS,Constants.NUll_VALUE))){
                if (!SharePreferencesUlits.getString(getActivity(),Constants.LOCATIONCITY,Constants.NUll_VALUE).equals(SharePreferencesUlits.getString(getActivity(),Constants.ADDRESS,Constants.NUll_VALUE))){
                    flag = true;
                    popuplocation();
                }
            }
        }
        //创建手势检测器
//        detector = new GestureDetector(getActivity(),getActivity());
        //初始化数据
        initData(view);
        //初始化控件
        initView(view);

        //-----------倒计时----------//
        OkHttpUtils.post(HttpApi.main_newpublish)
                .tag("RecentlyAnnounced")
                .params("cid", "1")
                .params("PagerIndex", "1")
                .params("PagerSize" , "3")
                .execute(new StringCallback() {
                    @Override
                    public void onResponse(boolean b, String s, Request request, Response response) {
                        processJson(s);
                    }

                    @Override
                    public void onError(boolean isFromCache, Call call, Response response, Exception e) {
                        super.onError(isFromCache, call, response, e);
                        imageView.setVisibility(View.VISIBLE);
                    }
                });
        return view ;
    }
    //自动定位城市切换
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void popuplocation() {
        view_location = LayoutInflater.from(getActivity()).inflate(R.layout.popuploactionlayout,null);
//        alertDialog = new AlertDialog.Builder(getActivity());
//        window = alertDialog.create();
//        window.setIcon(Color.parseColor("#FFFFFF"));
        dialog = new Dialog(getActivity(), R.style.dialog);
        dialog.setContentView(view_location);
        dialog.show();
//        window.setView(view_location,0,0,0,0);
        btn_location_cancel = (Button)view_location.findViewById(R.id.btn_locationcity_cancel);
        btn_location_ok = (Button)view_location.findViewById(R.id.btn_locationcity_ok);
        textview_locationCity = (TextView)view_location.findViewById(R.id.textview_citylocation);
        textview_locationCity.setText("是否切换到当前定位城市:"+SharePreferencesUlits.getString(getActivity(),Constants.LOCATIONCITY,Constants.NUll_VALUE));
        btn_location_ok.setOnClickListener(this);
        btn_location_cancel.setOnClickListener(this);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                LocationApplication.STATENUMBER = Numbers.TWO;
            }
        });
//        window.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!StringUtils.isEmpty(SharePreferencesUlits.getString(getActivity(),Constants.COUNTY_DISTRICT,Constants.NUll_VALUE))){
            button.setText(SharePreferencesUlits.getString(getActivity(),Constants.COUNTY_DISTRICT, Constants.NUll_VALUE));
        }else  if (!StringUtils.isEmpty(SharePreferencesUlits.getString(getActivity(),Constants.ADDRESS, Constants.NUll_VALUE))){
            button.setText(SharePreferencesUlits.getString(getActivity(),Constants.ADDRESS,Constants.NUll_VALUE));
        }else {
            button.setText("定位");
        }
        isRun = true;
        isRefresh = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        isRefresh =false;
    }
    private void processJson(String s) {
        RecentlyAnnounced announced = GsonUtil.processJson(s, RecentlyAnnounced.class);
        recentlys = (ArrayList<RecentlyAnnounced.DataBean>) announced.data;    //最近揭晓的详细信息
        if (announced.state == 1){
            if (recentlys != null){
                if (recentlys.size() > 0){
                    imageView.setVisibility(View.GONE);
                    for (RecentlyAnnounced.DataBean data : recentlys){          //转化作毫秒
                        data.CountDown = data.CountDown *1000;
                        Log.e("time" , "c: "+ data.CountDown);
                    }
                    if (announcedAdapter == null){
                        announcedAdapter = new RecentlyAnnouncedAdapter();
                    }
                    gridView.setAdapter(announcedAdapter);
                    countDownTime();                               //倒计时
                    handler2.sendEmptyMessageDelayed(1, 500);       // 刷新页面
                }else{
                    imageView.setVisibility(View.VISIBLE);
                }
            }

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
            LogUtil.e("downRunnable" , "downRunnable : "+downRunnable);
        }
        countDownhandler.post(downRunnable);
        Log.e("isRun" ,isRun+"");
    }

    boolean isRun = true;
    private class CountDownRunnable implements Runnable{

        @Override
        public void run() {
            while(isRun){
                if (recentlys.size() > 0){
                    try {
                        Thread.sleep(99);
                        for (RecentlyAnnounced.DataBean data : recentlys){
                            if (data.CountDown > 0){
                                data.CountDown = data.CountDown-99;
                                if (data.CountDown < 0){
                                    data.CountDown = 0;
                                }
                                Log.e("data.CountDown" ,"-------------"+data.CountDown+"----------");
                            }else{
                                data.CountDown =0;
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }

        }
    }


    public class RecentlyAnnouncedAdapter extends BaseAdapter {


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
        public View getView(final int position, View convertView, ViewGroup parent) {

            final ViewHolder viewHolder;
            Log.e("getview" , recentlys.get(position).CountDown+"+========");
            if (convertView == null) {
                convertView = View.inflate(getActivity(), R.layout.adapter_recentlyannounced, null);
                viewHolder = new ViewHolder();
                viewHolder.imgCountDownTime = (ImageView) convertView.findViewById(R.id.imgCountDownTime);
                viewHolder.imgLimitBuy = (ImageView) convertView.findViewById(R.id.imgLimitBuy);
                viewHolder.imgTen = (ImageView) convertView.findViewById(R.id.imgTen);
                viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
                viewHolder.tvCountDwonTime = (TextView) convertView.findViewById(R.id.tvCountDwonTime);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            Log.e("getview", recentlys.get(position).CountDown + "!!!!!!!!");
            viewHolder.imgLimitBuy.setImageResource(R.drawable.publishzhengzaijiexiao);
            imageloderinit.imageLoader.displayImage(recentlys.get(position).MainImg, viewHolder.imgCountDownTime, imageloderinit.options);
            viewHolder.tvTitle.setText("(第" + recentlys.get(position).PNumber + "期)" + recentlys.get(position).Title);
            Log.e("data.CountDown" ,recentlys.get(position).CountDown+"============="+StringUtils.formatTime(recentlys.get(position).CountDown)+"==========");
            viewHolder.tvCountDwonTime.setText(StringUtils.formatTime(recentlys.get(position).CountDown));

            if (recentlys.get(position).CountDown == 0){
                recentlys.remove(position);
            }

            return convertView;
        }
    }

    public static class ViewHolder {

        public ImageView imgCountDownTime;
        public ImageView imgLimitBuy;
        public ImageView imgTen;
        public TextView  tvTitle;
        public TextView  tvCountDwonTime;
    }

    /**
     * 初始化数据
     */
    public void initData(View view){
        LogUtil.i("Fragmentmain initdata start.","");
        handlerThread = new HandlerThread("MyHandlerThread");
        handlerThread.start();
        mhandler = new Handler(handlerThread.getLooper());
        index = 1;
        localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        goodses = new ArrayList<Goods>();
        arrayListol = new ArrayList<Image>();
        imageloderinit = new Imageloderinit(getActivity());
        bannerImageLoder = new BannerImageLoder(getActivity());
        button = (Button)view.findViewById(R.id.imagebutton_ID);
        try {
            if (!StringUtils.isEmpty(CacheUtils.getCache(HttpApi.Ad,true))){
                try {
                    JSONObject jsonObject = new JSONObject(CacheUtils.getCache(HttpApi.Ad,false));
                    JSONArray jsonArray = null;
                    jsonArray = new JSONArray(jsonObject.getString("data"));
                    Log.i("this_content", jsonObject.getString("data") + "json");
                    for (int c = 0; c<jsonArray.length(); c++){
                        JSONObject jsonObject1 = jsonArray.getJSONObject(c);
                        Image image = new Image();
                        image.setShowImg(jsonObject1.getString("ShowImg"));
                        image.setCategory(jsonObject1.getInt("Category"));
                        image.setIsEnable(jsonObject1.getBoolean("IsEnable"));
                        image.setRemark(jsonObject1.getString("Remark"));
                        arrayListol.add(image);
                    }
                    Message message = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("banner",arrayListol);
                    message.setData(bundle);
                    message.what = Numbers.ONE;
                    handlerUI.sendMessage(message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {
                MyThreadPoolManager.getInstance().execute(runnableFlish);
            }
            //MyThreadPoolManager.getInstance().execute(runnable);
            MyThreadPoolManager.getInstance().execute(runnableCategory);
            String token = SaveShared.tokenget(getActivity());
        }catch (Exception e){
            LogUtil.e("FragmentMain initdata error:",e.toString());
        }finally {
            LogUtil.i("FragmentMain initData end.","");
        }
        gridView = (GridView)view.findViewById(R.id.gridview_fragmentmain);
        gridView.setOnItemClickListener(this);
        gridView_ol = (GridView)view.findViewById(R.id.gridview_sp);
        gridView_ol.setOnScrollListener(new PauseOnScrollListener(imageloderinit.imageLoader, true, false));
       // gridView.setOnScrollListener(new PauseOnScrollListener(imageloderinit.imageLoader, true, false));
        arrayList = new ArrayList<Publish>();
        arrayList_goods = new ArrayList<Goods>();
        imageViews = new ArrayList<ImageView>();
        arrayListview = new ArrayList<View>();
    }


    public void setCurrentFragment (int index){
        ((MyActivity)getActivity()).setCurrentFargmentIndex(index);
    }
    /**
     * 实例化控件设置监听事件
     * @param v
     */
    public void initView(View v){
        imageView = (ImageView)v.findViewById(R.id.imageview_this_inforation);
        linearLayout_ol = (LinearLayout)v.findViewById(R.id.linearlayout_main_car);
        pullToRefreshView = (PullToRefreshLayout)v.findViewById(R.id.pulltorereslayout);
        button_food = (Button)v.findViewById(R.id.imagebutton_food);
        imageButton_friend = (Button)v.findViewById(R.id.imagebutton_friend);
        button_FJ = (Button)v.findViewById(R.id.imagebutton_room);
        Button_p = (Button)v.findViewById(R.id.imagebutton_ZP);
        linearLayout = (LinearLayout)v.findViewById(R.id.linearlayout_Gprs);
        button_single = (Button)v.findViewById(R.id.imagebutton_SD);
        radioGroup_ol = (RadioGroup)v.findViewById(R.id.radiogroup_fl);
        buttonall = (Button)v.findViewById(R.id.button_fragmentmainall);
        Log.i(getActivity()+"",SharePreferencesUlits.getString(getActivity(),Constants.ADDRESS, Constants.NUll_VALUE)+"address");
        if (!StringUtils.isEmpty(SharePreferencesUlits.getString(getActivity(),Constants.COUNTY_DISTRICT,Constants.NUll_VALUE))){
            button.setText(SharePreferencesUlits.getString(getActivity(),Constants.COUNTY_DISTRICT, Constants.NUll_VALUE));
        }else  if (!StringUtils.isEmpty(SharePreferencesUlits.getString(getActivity(),Constants.ADDRESS, Constants.NUll_VALUE))){
            button.setText(SharePreferencesUlits.getString(getActivity(),Constants.ADDRESS,Constants.NUll_VALUE));
        }else {
            button.setText("定位");
        }
        buttonall.setOnClickListener(this);
        linearLayout.setOnClickListener(this);
        button.setOnClickListener(this);
        imageButton_friend.setOnClickListener(this);
        radioGroup_ol.setOnCheckedChangeListener(this);
//        ImageView imageView = new ImageView(getActivity());
        button_food.setOnClickListener(this);
        button_FJ.setOnClickListener(this);
        button_single.setOnClickListener(this);
        Button_p.setOnClickListener(this);
        pullToRefreshView.setOnRefreshListener(this);
    }
    /**
     * 数据请求
     */
    private Runnable runnableFlish = new Runnable() {
        @Override
        public void run() {
            arrayListol = HttpTransfeData.httpad(getActivity());
            messageBanner = new Message();
            bundle = new Bundle();
            messageBanner.what = Numbers.ONE;
            bundle.putParcelableArrayList("banner",arrayListol);
            messageBanner.setData(bundle);
            handlerUI.sendMessage(messageBanner);
        }
    };
    /**
     * 数据请求
     *//*
    private Runnable runnable = new Runnable() {
        @Override
        public synchronized void run() {
            arrayList = HttpTransfeData.newpublish(getActivity());
            messageRencent = new Message();
            bundle1 = new Bundle();
            messageRencent.what = Numbers.TWO;
            bundle1.putParcelableArrayList("category",arrayList);
            messageRencent.setData(bundle1);
            handlerUI.sendMessage(messageRencent);
        }
    };*/
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.radiobutton_ms:
                goodses.clear();
                content = sh[0];
                index = 1;
                try {
                    MyThreadPoolManager.getInstance().execute(runnableCategory);
                }catch (Exception e){
                    LogUtil.e("FragmentMain onCheckedChanged error:",e.toString());
                }
                break;
            case R.id.radiobutton_xg:
                goodses.clear();
                content = sh[1];
                index = 1;
                try {
                    MyThreadPoolManager.getInstance().execute(runnableCategory);
                }catch (Exception e){
                    LogUtil.e("FragmentMain onCheckedChanged error:",e.toString());
                }
                break;
            case R.id.radiobutton_s:
                goodses.clear();
                content = sh[2];
                index = 1;
                try {
                    MyThreadPoolManager.getInstance().execute(runnableCategory);
                }catch (Exception e){
                    LogUtil.e("FragmentMain onCheckedChanged error:",e.toString());
                }
                break;
            case R.id.radiobutton_sd:
                goodses.clear();
                content = sh[3];
                index = 1;
                try {
                    MyThreadPoolManager.getInstance().execute(runnableCategory);
                }catch (Exception e){
                    LogUtil.e("FragmentMain onCheckedChanged error:",e.toString());
                }
                break;
            case R.id.radiobutton_jia:
                goodses.clear();
                content = sh[4];
                index = 1;
                try {
                    MyThreadPoolManager.getInstance().execute(runnableCategory);
                }catch (Exception e){
                    LogUtil.e("FragmentMain onCheckedChanged error:",e.toString());
                }
                break;
        }
    }

    /**
     * 获取分类商品信息
     */
    private Runnable runnableCategory = new Runnable() {
        @Override
        public synchronized void run() {
            arrayList_ol = HttpTransfeData.newfasterhttp(getActivity(),content,index,"4");
            if (arrayList_ol != null){
                goodses.addAll(arrayList_ol);
            }
            Message message = new Message();
            Bundle bundle = new Bundle();
            message.what = Numbers.THREE;
            bundle.putParcelableArrayList("zui",goodses);
            message.setData(bundle);
            handlerUI.sendMessage(message);
        }
    };
    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.button_fragmentmainall:
                ((MyActivity)getActivity()).setRadioGroupChecked(2);
                ((MyActivity)getActivity()).setState(3);
                break;
            case R.id.linearlayout_Gprs:
                flag = true;
                citypopupwinds(flag,v);
                backgroundAlpha(0.5f);
                break;
            case R.id.imagebutton_food:
                ((MyActivity) getActivity()).setRadioGroupChecked(Numbers.ONE);
                ((MyActivity)getActivity()).setState(Numbers.TWO);
                break;

            case R.id.imagebutton_friend:
                String token = SaveShared.tokenget(getActivity());
                if (token != ""){
                    intent.putExtra("back","2");
                    intent.setClass(getActivity(),ActivityAddFriend.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(getActivity(),"您尚未登录请登录",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.imagebutton_room:
                if (!StringUtils.isEmpty(SharePreferencesUlits.getString(getActivity(),Constants.TOKEN,Constants.NUll_VALUE))){
                    intent.setClass(getActivity(),ActivityCloudRoom.class);
                    startActivity(intent);
                }else {
                    cToast = CToast.makeText(getActivity(),"请登录~",600);
                    cToast.show();
                }
                break;
            case R.id.imagebutton_ZP:
                if (!StringUtils.isEmpty(SharePreferencesUlits.getString(getActivity(),Constants.TOKEN,Constants.NUll_VALUE))){
                    intent.setClass(getActivity(),ActivityRotaryTable.class);
                    startActivity(intent);
                }else {
                    cToast = CToast.makeText(getActivity(),"请登录~",600);
                    cToast.show();
                }
                break;
            case R.id.imagebutton_SD:
                intent.setClass(getActivity(),ActivitySingle.class);
                startActivity(intent);

                break;
            case R.id.button_cityqiehuan:
                popupWindow.dismiss();
                intent.setClass(getActivity(),ActivityCity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.btn_locationcity_cancel:
                LocationApplication.STATENUMBER = Numbers.TWO;
                dialog.dismiss();
                break;
            case R.id.btn_locationcity_ok:
                LocationApplication.STATENUMBER = Numbers.TWO;
                dialog.dismiss();
                button.setText(SharePreferencesUlits.getString(getActivity(),Constants.LOCATIONCITY,Constants.NUll_VALUE));
                SharePreferencesUlits.saveString(getActivity(),Constants.ADDRESS,SharePreferencesUlits.getString(getActivity(),Constants.LOCATIONCITY,Constants.NUll_VALUE));
                break;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String city = data.getStringExtra("city");
        if (!city.equals("")){
            button.setText(city);
        }else {
            if (!StringUtils.isEmpty(SharePreferencesUlits.getString(getActivity(), Constants.COUNTY_DISTRICT, Constants.NUll_VALUE))){
                button.setText(SharePreferencesUlits.getString(getActivity(),Constants.COUNTY_DISTRICT, Constants.NUll_VALUE));
            }else {
                button.setText(SharePreferencesUlits.getString(getActivity(),Constants.ADDRESS, Constants.NUll_VALUE));
            }
        }

    }
    /**
     * 城市弹出框
     * @param flag
     * @param
     */
    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    private void citypopupwinds(boolean flag,View view_ol) {
        arrayList_tZone = new ArrayList<TZone>();
        popupWindow = new PopupWindow();
        view1 = LayoutInflater.from(getActivity()).inflate(R.layout.citypopuplayoutitem,null);
        popupWindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(view1);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setClippingEnabled(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        textView1 = (TextView)view1.findViewById(R.id.textview_cityol);
        gridView1 = (GridView)view1.findViewById(R.id.gridview_cityol);
        linearLayout_city = (LinearLayout)view1.findViewById(R.id.linearlayout_city_content);
        button1 = (Button)view1.findViewById(R.id.button_cityqiehuan);
        linearLayout_city.setVisibility(View.VISIBLE);
         if (!StringUtils.isEmpty(SharePreferencesUlits.getString(getActivity(),Constants.ADDRESS,Constants.NUll_VALUE))){
             textView1.setText(SharePreferencesUlits.getString(getActivity(),Constants.ADDRESS,Constants.NUll_VALUE));
         }else {
            textView1.setText("定位");
         }
        MyThreadPoolManager.getInstance().execute(runableAddress);
        button1.setOnClickListener(this);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });
        if (flag){
            popupWindow.showAsDropDown(view_ol);
        }
    }
    private Runnable runableAddress = new Runnable() {
        @Override
        public void run() {
            int citysort = 0;
            if(!StringUtils.isEmpty(SharePreferencesUlits.getString(getActivity(),Constants.ADDRESS,Constants.NUll_VALUE))) {
                citysort = DataStore.selectezoneID(getActivity(),SharePreferencesUlits.getString(getActivity(),Constants.ADDRESS,Constants.NUll_VALUE));
            }else {
                citysort = DataStore.selectezoneID(getActivity(),textView1.getText().toString());
            }
            arrayList_tZone = DataStore.Zonedataselect(getActivity(), citysort);
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("zone",arrayList_tZone);
            message.what = Numbers.FOUR;
            message.setData(bundle);
            handlerUI.sendMessage(message);
        }
    };
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        switch (parent.getId()){
           case R.id.gridview_fragmentmain:
               intent.putExtra("outid" ,recentlys.get(position).ID);
               intent.putExtra("prid" ,recentlys.get(position).ProductID);
               intent.setClass(getActivity() , ActivityShoppingTimeDetail.class);
               startActivity(intent);
               break;

        }
    }
    private Handler handlerUI = new Handler(Looper.getMainLooper()){
        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    arrayListol = msg.getData().getParcelableArrayList("banner");
                    if (arrayListol.size()>0){
//                        final ArrayList<Object> bitmapList=new ArrayList<Object>();
//                        for (int c=0;c<arrayListol.size();c++){
//                            bitmapList.add(HttpApi.tu+arrayListol.get(c).getShowImg());
//                        }
//                        initbanner(bitmapList);
//                        ViewFactory viewFactory = new ViewFactory(getActivity(),imageloderinit.imageLoader,imageloderinit.options);
                        if (getActivity() != null){
                            List<ImageView> views = new ArrayList<ImageView>();
                            List<ADInfo> infos = new ArrayList<ADInfo>();
                            cycleViewPager = (CycleViewPager) getChildFragmentManager().
                                    findFragmentById(R.id.layout_bannerFrame);
                            for(int i = 0; i < arrayListol.size(); i ++){
                                ADInfo info = new ADInfo();
                                info.setUrl(HttpApi.tu_ool+arrayListol.get(i).getShowImg());
//                            info.set(arrayListol.get(i).getCategory());
                                infos.add(info);
                            }
                            // 将最后一个ImageView添加进来
                            views.add(ViewFactory.getImageView(getActivity(),infos.get(infos.size() - 1).getUrl()));
                            for (int i = 0; i < infos.size(); i++) {
                                views.add(ViewFactory.getImageView(getActivity(), infos.get(i).getUrl()));
                            }
                            // 将第一个ImageView添加进来
                            views.add(ViewFactory.getImageView(getActivity(), infos.get(0).getUrl()));

                            // 设置循环，在调用setData方法前调用
                            cycleViewPager.setCycle(true);

                            // 在加载数据前设置是否循环
                            cycleViewPager.setData(views, infos, mAdCycleViewListener);
                            //设置轮播
                            cycleViewPager.setWheel(true);

                            // 设置轮播时间，默认5000ms
                            cycleViewPager.setTime(2000);
                            //设置圆点指示图标组居中显示，默认靠右
                            cycleViewPager.setIndicatorCenter();
                        }

                }
                break;
                case 3:
                    arrayList_ol = msg.getData().getParcelableArrayList("zui");
                    if (arrayList_ol !=null){
                        AdapterClassity.ACOnClickListener acOnClickListener = new AdapterClassity.ACOnClickListener() {
                            @Override
                            protected void acOnClickListener(Integer tag, View v) {
                                switch (v.getId()){
                                    case R.id.button_fragmentmainaddcat:
                                        String token = SaveShared.tokenget(getActivity());
                                        if (!TextUtils.isEmpty(token)){
                                            try {
                                                linearLayout_ol.setVisibility(View.VISIBLE);
                                                th = "1";
                                                pid = arrayList_ol.get(tag).getGoodsid();
                                                limitcount = arrayList_ol.get(tag).getGoodsxian();
                                                surpcount = arrayList_ol.get(tag).getGoods_surplus();
                                                MyThreadPoolManager.getInstance().execute(runnableGoodsNumber);
                                            } catch (Exception e) {
                                                LogUtil.e("FragmentMain runable4",e.toString());
                                                e.printStackTrace();
                                            }
                                        }else {
                                            cToast = CToast.makeText(getActivity(),"请登录~",600);
                                            cToast.show();
                                        }
                                        break;
                                }
                            }
                        };
                        adapterClassity  = new AdapterClassity(getActivity(),arrayList_ol,acOnClickListener,imageloderinit.imageLoader,imageloderinit.options);
                        gridView_ol.setAdapter(adapterClassity);
                        gridView_ol.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent();
                                intent.putExtra("outid", arrayList_ol.get(position).getGoodsid());
                                intent.putExtra("prid",arrayList_ol.get(position).getProductID());
                                intent.putExtra("index" , Numbers.ZERO);
                                intent.putExtra("cloud", String.valueOf(Numbers.TWO));
                                intent.setClass(getActivity(), ActivityGoodsDateil.class);
                                startActivity(intent);
                            }
                        });
                    }
                    break;
                case 4:
                    linearLayout_city.setVisibility(View.GONE);
                    tZones= new ArrayList<TZone>();
                    tZones = msg.getData().getParcelableArrayList("zone");
                    if (tZones != null){
                        if (tZones.size()>0){
                            AdapterCityPopupone adapterCity_ol = new AdapterCityPopupone(getActivity(),tZones);
                            gridView1.setAdapter(adapterCity_ol);
                            if (!StringUtils.isEmpty(SharePreferencesUlits.getString(getActivity(),Constants.COUNTY_ID,Constants.NUll_VALUE))){
                                gridView1.setItemChecked(Integer.parseInt(SharePreferencesUlits.getString(getActivity(),Constants.COUNTY_ID,Constants.NUll_VALUE)), true);
                                textView1.setText(SharePreferencesUlits.getString(getActivity(), Constants.ADDRESS, Constants.NUll_VALUE));
                            }else {
                                gridView1.setItemChecked(0,true);
                                textView1.setText(button.getText().toString());
                            }
                        }
                        gridView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                textView1.setText(button1.getText().toString() + tZones.get(position).getZonename());
                                SharePreferencesUlits.saveString(getActivity(), Constants.COUNTY_DISTRICT, tZones.get(position).getZonename());
                                SharePreferencesUlits.saveString(getActivity(),Constants.COUNTY_ID,String.valueOf(position));
                                button.setText(tZones.get(position).getZonename());
                                popupWindow.dismiss();
                            }
                        });
                    }
                    break;
                case 5:
                    statese = new Statese();
                    statese = msg.getData().getParcelable("state");
                    if (statese != null){
                        if (statese.getState().equals(String.valueOf(Numbers.ONE))){
//                        MyActivity.radioButton_one.setChecked(true);
                            if(!TextUtils.isEmpty(statese.getData())){
                                if (statese.getData().equals("true")){
                                    MyThreadPoolManager.getInstance().execute(runnableCarNumber);
                                }
                            }
                            if (getActivity() != null){
                                cToast = CToast.makeText(getActivity(), "加入购物车成功~", 600);
                                cToast.show();
                            }
                        }
                    }
                    break;
                case 6:
                    linearLayout_ol.setVisibility(View.GONE);
                    statese = new Statese();
                    statese = msg.getData().getParcelable("contentNumber");
                    if (statese != null){
                        if (!TextUtils.isEmpty(statese.getState())){
                            if (statese.getState().equals(String.valueOf(Numbers.ONE))){
                                if (!limitcount.equals("0")){
                                    if (Integer.parseInt(limitcount)>0){
                                        if (Integer.parseInt(statese.getData())<Integer.parseInt(limitcount)){
                                            try {
                                                MyThreadPoolManager.getInstance().execute(runnableAddCar);
                                            }catch (Exception e){
                                                LogUtil.e("FragmentMain handler error:",e.toString());
                                            }
                                        }else {
                                            cToast = CToast.makeText(getActivity(),"没有更多商品了",600);
                                            cToast.show();
                                        }
                                    }
                                }else {
                                    if (Integer.parseInt(statese.getData())<Integer.parseInt(surpcount)){
                                        try {
                                            MyThreadPoolManager.getInstance().execute(runnableAddCar);
                                        }catch (Exception e){
                                            LogUtil.e("FragmentMain handler error:",e.toString());
                                        }
                                    }else {
                                        cToast = CToast.makeText(getActivity(),"没有更多商品了",600);
                                        cToast.show();                                    }
                                }
                            }else {
                                if (statese.getMsg().equals("token失效")){
                                    MyThreadPoolManager.getInstance().execute(runnableSingle);
                                    cToast = CToast.makeText(getActivity(),statese.getMsg(),600);
                                    cToast.show();
                                }
                            }
                        }
                    }
                    break;
                case 7:
                    statese = new Statese();
                    statese = msg.getData().getParcelable("content");
                    if (statese != null){
                        if (statese.getState().equals("1")){
                            if (!statese.getData().equals("null")){
                                try {
                                    MyActivity.textView.setText(statese.getData());
                                    SharePreferencesUlits.saveString(getActivity(),Constants.CAR_NUMBER,statese.getData());
                                } catch (Exception e) {
                                    LogUtil.i("MianFragment handler error:",e.toString());
                                    e.printStackTrace();
                                }

                            }
                        }
                    }
                    break;
                case 8:
                    statese = msg.getData().getParcelable("this");
                    if (statese != null){
                        if (statese.getState().equals(String.valueOf(Numbers.ONE))){
                            MyThreadPoolManager.getInstance().execute(runnableCarNumber);
                        }
                    }
                    break;
            }
        }
    };
    /**
     * 获取购物车商品数量
     */
    private Runnable runnableGoodsNumber = new Runnable() {
        @Override
        public synchronized void run() {
            try {
                Thread.sleep(1000);
                statese = new Statese();
                String token = SaveShared.tokenget(getActivity());
                statese = HttpTransfeData.httpshoppingcat(getActivity(),statese,token,pid);
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putParcelable("contentNumber",statese);
                message.setData(bundle);
                message.what = Numbers.SIX;
                handlerUI.sendMessage(message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };
    /**
     * 获取商品件数
     */
    private Runnable runnableCarNumber = new Runnable() {
        @Override
        public synchronized void run() {
            try {
                statese = new Statese();
                String token = SaveShared.tokenget(getActivity());
                statese = HttpTransfeData.getcarnumberhttp(statese, getActivity(), token);
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putParcelable("content",statese);
                message.what = Numbers.SEVEN;
                message.setData(bundle);
                handlerUI.sendMessage(message);
            }catch (Exception e){
                LogUtil.e("MyActivity+Runable1",e.toString());
            }
        }
    };
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
    /**
     * 重新登陆
     */
    private Runnable runnableSingle = new Runnable() {
        @Override
        public void run() {
            statese = new Statese();
            String user_ol = SaveShared.loadong_name(getActivity());
            String pass = SaveShared.loading_pass(getActivity());
            statese = HttpTransfeData.httppostloding(getActivity(), user_ol, pass);
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putParcelable("this", statese);
            message.setData(bundle);
            message.what = Numbers.EIGHT;
            handlerUI.sendMessage(message);
        }
    };
    /**
     * 添加购物车
     */
    private Runnable runnableAddCar = new Runnable() {
        @Override
        public synchronized void run() {
             statese = new Statese();
            String token = SaveShared.tokenget(getActivity());
            String uid = SaveShared.uid(getActivity());
            String num = "1";
            statese = HttpTransfeData.goodsaddshoppingcat(getActivity(),pid,uid,token,num);
            Message message = new Message();
            Bundle bundle = new Bundle();
            message.what = Numbers.FIVE;
            bundle.putParcelable("state",statese);
            message.setData(bundle);
            handlerUI.sendMessage(message);
        }
    };

    /**
     * popup弹框
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha)
    {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha;
        getActivity().getWindow().setAttributes(lp);
    }

    /**
     * 上拉刷新下拉加载
     * @param pullToRefreshLayout
     */
    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        new Handler()
        {
            @Override
            public void handleMessage(Message msg)
            {
                try {
                    if (!StringUtils.isEmpty(CacheUtils.getCache(HttpApi.Ad,true))){
                        try {
                            JSONObject jsonObject = new JSONObject(CacheUtils.getCache(HttpApi.Ad,false));
                            JSONArray jsonArray = null;
                            jsonArray = new JSONArray(jsonObject.getString("data"));
                            Log.i("this_content", jsonObject.getString("data") + "json");
                            for (int c = 0; c<jsonArray.length(); c++){
                                JSONObject jsonObject1 = jsonArray.getJSONObject(c);
                                Image image = new Image();
                                image.setShowImg(jsonObject1.getString("ShowImg"));
                                image.setCategory(jsonObject1.getInt("Category"));
                                image.setIsEnable(jsonObject1.getBoolean("IsEnable"));
                                image.setRemark(jsonObject1.getString("Remark"));
                                arrayListol.add(image);
                            }
                            Message message = new Message();
                            Bundle bundle = new Bundle();
                            bundle.putParcelableArrayList("banner",arrayListol);
                            message.setData(bundle);
                            message.what = Numbers.ONE;
                            handlerUI.sendMessage(message);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }else {
                        MyThreadPoolManager.getInstance().execute(runnableFlish);
                    }
                    MyThreadPoolManager.getInstance().execute(runnableCategory);
                } catch (Exception e) {
                    LogUtil.i("MianFragment onrefresh error:",e.toString());
                    e.printStackTrace();
                }
                goodses.clear();
                index = 1;
               // MyThreadPoolManager.getInstance().execute(runnable);
                pullToRefreshView.refreshFinish(PullToRefreshLayout.SUCCEED);
            }
        }.sendEmptyMessageDelayed(0, 2000);
    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        new Handler(){
            @Override
            public void handleMessage(Message msg) {
                //
                index+=1;
                MyThreadPoolManager.getInstance().execute(runnableCategory);
                pullToRefreshView.refreshFinish(PullToRefreshLayout.SUCCEED);
            }
        }.sendEmptyMessageDelayed(0, 2000);
    }

   /* //广播
    public class ThreaderReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            if (arrayList != null){
                arrayList.clear();
            }
            gridView.setAdapter(null);
            mhandler.post(runnable);
//            MyThreadPoolManager.getInstance().execute(runnable);

        }
    }*/




    //销毁线程
    @Override
    public void onDestroy() {
        if (arrayList_tZone != null){
            arrayList_tZone.clear();
        }
        if (arrayList != null){
            arrayList.clear();
        }
        if (arrayList_goods != null){
            arrayList_goods.clear();
        }
        if (arrayList_ol != null){
            arrayList_ol.clear();
        }
        if (arrayList_time != null){
            arrayList_time.clear();
        }
        if (arrayList_ol != null){
            arrayListol.clear();
        }
        if(arrayListview != null){
            arrayListview.clear();
        }
        //localBroadcastManager.unregisterReceiver(threaderReceiver);
        Log.e("onDestroy" , "onDestroy");
        //停止倒计时的downrunnable
        handler2.sendEmptyMessageDelayed(STOP_THREAD ,0);
        if (countDownThread != null){
            countDownThread.quit();
        }
        isRun = false;
        great0Number = 0;
        super.onDestroy();
    }
}
