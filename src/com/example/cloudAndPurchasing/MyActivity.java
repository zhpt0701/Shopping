package com.example.cloudAndPurchasing;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.cloudAndPurchasing.baidu.LocationService;
import com.example.cloudAndPurchasing.http.HttpTransfeData;
import com.example.cloudAndPurchasing.http.Numbers;
import com.example.cloudAndPurchasing.http.SaveShared;
import com.example.cloudAndPurchasing.kind.*;
import com.example.cloudAndPurchasing.manager.MyThreadPoolManager;
import com.example.cloudAndPurchasing.popupwindows.PopupEdition;
import com.example.cloudAndPurchasing.service.VersionCode;
import com.example.cloudAndPurchasing.sqllite.MySqLite;
import com.example.cloudAndPurchasing.tiltle.TeaskBar;
import com.example.cloudAndPurchasing.times.CToast;
import com.example.cloudAndPurchasing.zhi.LogUtil;
import com.example.cloudAndPurchasing.zhi.ValuePrice;
import com.umeng.analytics.MobclickAgent;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * created by zhangpengtao on 2016/3/21.
 */
public class MyActivity extends BaseFragmentActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {
    /**
     * 自定义变量控件
     * radioGroup
     * currIndex
     * version 版本
     * contentIntent intent传值
     * locationService 服务
     * sharedPreferences 数据存储
     */
    public static RadioGroup radioGroup;
    public static FrameLayout frameLayout;
    private long finshApp = 0;
    public LinearLayout linearLayout;
    private Statese statese;
    public static RadioButton radioButton,radioButton_ol,radioButton_ool,radioButton_one,radioButton_two;
    public  int currIndex = 0;
    private String contentIntent;
    private String version;
    private int versionCode;
    private View view_location;
    private AlertDialog.Builder alertDialog;
    private AlertDialog window;
    private Button btn_verion_cancel;
    private Button btn_verion_ok;
    private TextView textView_verion;
    private String net_path;
    private TextView textView_verion_title;
    private Dialog dialog;

    public int getState() {
        return state;
    }
    public static TextView textView;
    private VersionOL versionOL;
    private LocationService locationService;
    private SharedPreferences sharedPreferences;
    public void setState(int state) {

        this.state = state;
    }
    private CToast cToast;
    private int state = 0;
    public LocationClient mLocationClient = null;
    public BDLocationListener  myListener = new MyLocationListener();
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        LocationApplication.getInstance().addActivity(this);
        TeaskBar.onSystemoutcolor(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
        mLocationClient.registerLocationListener( myListener );
        //创建sqllite数据库
        MySqLite mySqLite = new MySqLite(this);
        mySqLite.getWritableDatabase();

        //初始化数据
        initData();
        //初始化控件
        initview();
    }

    //网络判断
    private void interent() {
        PackageManager manager = this.getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(getPackageName(), 0);
            version = info.versionName;
            versionCode = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        boolean internet = Internet.isNetworkConnected(this);
        if (internet){
            boolean wifi = Internet.isWifiConnected(this);
            boolean mo = Internet.isMobileConnected(this);
            if (wifi){
                int th = Internet.getConnectedType(this);
            }else if (mo){
                int th = Internet.getConnectedType(this);
            }
            //初始化定位
            initLocation();
            mLocationClient.start();
        }else {
            Toast.makeText(this,"当前没有网络链接",Toast.LENGTH_LONG).show();
        }
    }
    /**
     * 设置返回控制
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            if ((System.currentTimeMillis() - finshApp) > 2000) {
                cToast = CToast.makeText(this, "再按一次后退键退出程序",
                        600);
                cToast.show();
                finshApp = System.currentTimeMillis();
            }else {
                sharedPreferences = getSharedPreferences("data",MODE_PRIVATE);
                sharedPreferences.getString("address_ol","");

                this.finish();
            }
            return true;
        }else {
            return  super.onKeyDown(keyCode, event);
        }

    }
    /**
     *
     */
    private void initjm() {
        contentIntent = getIntent().getStringExtra("index");
         Log.e("fore" , contentIntent+"");
        if (contentIntent != null){
            if (contentIntent.equals(ValuePrice.VALUE_ONE)){
                radioButton_ol.setChecked(true);
            }else if (contentIntent.equals(ValuePrice.VALUE_TWO)){
                radioButton.setChecked(true);
            }else if (contentIntent.equals(ValuePrice.VALUE_THREE)){
                radioButton_ool.setChecked(true);
            }else if (contentIntent.equals(ValuePrice.VALUE_FOUR)){
                radioButton_one.setChecked(true);
            }else if (contentIntent.equals(ValuePrice.VALUE_FIVE)){
                radioButton_two.setChecked(true);
            }
        }

    }
    public void setRadioGroupChecked(int index) {
        switch (index) {
            case 0:
                radioButton_ol.setChecked(true);
                break;
            case 1:
                radioButton.setChecked(true);
                break;
            case 2:
                radioButton_ool.setChecked(true);
                break;
            case 3:
                radioButton_one.setChecked(true);
                break;
            case 4:
                radioButton_two.setChecked(true);
                break;
        }
    }


    /**
     * 获取版本
     */
    private Runnable runnableVersion = new Runnable() {
        @Override
        public synchronized void run() {
            statese = new Statese();
            statese = HttpTransfeData.httppostvertion(getApplicationContext(),statese, String.valueOf(versionCode));
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putParcelable("version",statese);
            message.what = Numbers.THREE;
            message.setData(bundle);
            handler1.sendMessage(message);
        }
    };
    /**
     * 初始化数据
     * @param（ArrayList）
     */
    public void initData(){
        LogUtil.i("MyActivity initData start.","");
        String token = SaveShared.tokenget(getApplicationContext());
        try {
            MyThreadPoolManager.getInstance().execute(runnableVersion);
            MyThreadPoolManager.getInstance().execute(runnableServiceTime);
            if (!token.equals("")){
                MyThreadPoolManager.getInstance().execute(runnableCarNumber);
            }
        }catch (Exception e){
            LogUtil.e("MyActivity initData error:",e.toString());
        }finally {
            LogUtil.i("MyActivity initData end.","");
        }
    }
    //获取服务器时间
    private Runnable runnableServiceTime = new Runnable() {
        @Override
        public synchronized void run() {
            try{
                HttpTransfeData.httpgettime(getApplication());
            }catch (Exception e){
                LogUtil.e("MyActivity+Runable",e.toString());
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
                String token = SaveShared.tokenget(getApplication());
                statese = HttpTransfeData.getcarnumberhttp(statese, getApplication(), token);
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putParcelable("content",statese);
                message.what = Numbers.ONE;
                message.setData(bundle);
                handler1.sendMessage(message);
            }catch (Exception e){
                LogUtil.e("MyActivity+Runable1",e.toString());
            }
        }
    };
    /**
     * 初始化控件设置监听事件
     * @param （RadioGroup）
     * @param
     */
    public void initview(){
        frameLayout = (FrameLayout)findViewById(R.id.framelayout_ol);
        textView = (TextView)findViewById(R.id.textview_car_number);
        radioGroup = (RadioGroup)findViewById(R.id.radiogroup_main);
        radioButton = (RadioButton)findViewById(R.id.radiobutton_sp);
        radioButton_ol = (RadioButton)findViewById(R.id.radiobutton_sy);
        radioButton_ool = (RadioButton)findViewById(R.id.radiobutton_wx);
        radioButton_one = (RadioButton)findViewById(R.id.radiobutton_gwc);
        radioButton_two = (RadioButton)findViewById(R.id.radiobutton_yg);
        radioGroup.setOnCheckedChangeListener(this);
        radioButton_ol.setChecked(true);
        if (SaveShared.getcarnumber(this) != null){
            if (!SaveShared.getcarnumber(this).equals("null")){
                if (!SaveShared.getcarnumber(this).equals("")){
                    textView.setText(SaveShared.getcarnumber(this));
                }else {
                    textView.setText("0");
                }
            }else {
                textView.setText("0");
            }
        }else {
            textView.setText("0");
        }
        initjm();
    }
    /**
     * 重新登陆
     */
    private Runnable runnableSingle = new Runnable() {
        @Override
        public void run() {
            statese = new Statese();
            String user_ol = SaveShared.loadong_name(getApplicationContext());
            String pass = SaveShared.loading_pass(getApplicationContext());
            statese = HttpTransfeData.httppostloding(getApplicationContext(), user_ol, pass);
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putParcelable("this", statese);
            message.setData(bundle);
            message.what = Numbers.TWO;
            handler1.sendMessage(message);
        }
    };
    /**
     * 滑动事件
     * @param group
     * @param checkedId
     */
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        switch (checkedId){
            case R.id.radiobutton_sy:
                currIndex = 0;
                break;
            case R.id.radiobutton_sp:
                currIndex = 1;
                break;
            case R.id.radiobutton_wx:
                currIndex = 2;
                break;
            case R.id.radiobutton_gwc:
                currIndex = 3;
                break;
            case R.id.radiobutton_yg:
                currIndex = 4;
                break;
        }

        setCurrentFragment(currIndex);
    }

    /**
     * 界面刷新
     */
    private Handler handler1 = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    statese = new Statese();
                    statese = msg.getData().getParcelable("content");
                    if (statese!=null){
                        if (!StringUtils.isEmpty(statese.getState())){
                            if (statese.getState().equals("1")){
                                if (!StringUtils.isEmpty(statese.getData())){
                                    textView.setText(statese.getData());
                                    SharePreferencesUlits.saveString(getApplication(),Constants.CAR_NUMBER,statese.getData());
                                }else {
                                    textView.setText("0");
                                }
                            }else {
                                if (statese.getMsg().equals("token失效")) {
                                    String user_ol = SaveShared.loadong_name(getApplicationContext());
                                    if (!user_ol.equals("")) {
                                        try {
                                            MyThreadPoolManager.getInstance().execute(runnableSingle);
                                        } catch (Exception e) {
                                            LogUtil.e("MyActivity handler error:", e.toString());
                                        }
                                    }
                                }
                            }
                        }

                    }
                    break;
                case 2:
                    statese = new Statese();
                    statese = msg.getData().getParcelable("this");
                    if (statese != null){
                        if (statese.getState().equals(String.valueOf(Numbers.ONE))){
                            try{
                                MyThreadPoolManager.getInstance().execute(runnableCarNumber);
                            }catch (Exception e){
                                LogUtil.e("MyActivity handler error:",e.toString());
                            }
                        }
                    }
                    break;
                case 3:
                    versionOL = VersionCode.versioncontent(getApplicationContext());
                    statese = new Statese();
                    statese = msg.getData().getParcelable("version");
                    if (statese!= null){
                        if (!statese.getState().equals(String.valueOf(Numbers.ONE))){
                            try {
                                JSONObject jsonObject = new JSONObject(statese.getData());
                                if (String.valueOf(versionCode).equals(jsonObject.getString("Version"))){
                                    popuplocation(statese.getData());
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
//                            PopupEdition popupEdition = new PopupEdition(getApplication(), fb,statese.getData());

                        }
                    }
                    break;

            }
        }
    };

    private void popuplocation(String content) {
        view_location = LayoutInflater.from(this).inflate(R.layout.popupeditionlayout,null);
//        alertDialog = new AlertDialog.Builder(this);
//        window = alertDialog.create();
//        window.setView(view_location,0,0,0,0);
        dialog = new Dialog(this, R.style.dialog);
        dialog.setContentView(view_location);
        dialog.show();
        btn_verion_cancel = (Button) view_location.findViewById(R.id.button_shaohou);
        btn_verion_ok = (Button) view_location.findViewById(R.id.button_once);
        textView_verion_title = (TextView)view_location.findViewById(R.id.textview_edition_title);
        textView_verion = (TextView) view_location.findViewById(R.id.textview_edition_content);
        try {
            PackageManager manager = getApplication().getPackageManager();
            PackageInfo info = null;
            info = manager.getPackageInfo(getApplication().getPackageName(), 0);
            JSONObject jsonObject = new JSONObject(content);
            if (versionCode<jsonObject.getInt("Version")){
                textView_verion_title.setText("新版本");
                net_path = jsonObject.getString("UpdateFile");
                textView_verion.setText(jsonObject.getString("UpdateFile"));
            }else {
                textView_verion_title.setText("暂无新版本");
                textView_verion.setText("当前版本" + jsonObject.getString("Remark"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        btn_verion_ok.setOnClickListener(this);
        btn_verion_cancel.setOnClickListener(this);
//        window.show();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_shaohou:
                dialog.dismiss();
                break;
            case R.id.button_once:
                Intent intent = new Intent();
                if (!StringUtils.isEmpty(net_path)){
                    dialog.dismiss();
                    intent.setAction("android.intent.action.VIEW");
                    Uri content_url = Uri.parse(net_path);
                    intent.setData(content_url);
                    startActivity(intent);
                }else {
                    dialog.dismiss();
                    Toast.makeText(getApplication(), "暂无新版本", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    /**
     * 销毁线程
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocationApplication.getInstance().removeActivity(this);

    }

    /**
     * fragment界面切换
     * @param position
     */
    public void setCurrentFragment(int position) {
        try{
            Fragment fragment = FragmentFactory.getFragment(position);
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();//通过FragmentManager得到fragment事务
            transaction.replace(R.id.viewpager_fragment, fragment);//将fragment放到content_home中进行显示
            transaction.commit();//将事务进行提交
        }catch (IllegalStateException e){
            e.printStackTrace();
        }
    }
    public void setCurrentFargmentIndex(int index){
        this.currIndex =index;
    }
    public void onResume() {
        Log.e("currIndex" , currIndex+"");
        super.onResume();
        interent();
        MobclickAgent.onResume(this);
        setCurrentFragment(currIndex);
        setRadioGroupChecked(currIndex);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
    //定位设置
    private void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span=1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }

    /**
     *
     * 定位结果回调，重写onReceiveLocation方法
     *
     */
    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            // TODO Auto-generated method stub
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                StringBuffer sb = new StringBuffer(256);
                /**
                 * 时间也可以使用systemClock.elapsedRealtime()方法 获取的是自从开机以来，每次回调的时间；
                 * location.getTime() 是指服务端出本次结果的时间，如果位置不发生变化，则时间不变
                 */
                String str = location.getProvince()+location.getCity();
                logMsg(location.getCity(), str);
            }
        }

    }
    /**
     * 显示请求字符串
     * @param str
     */
    public void logMsg(String str,String privence) {
        try {
            if (!StringUtils.isEmpty(str)){
                mLocationClient.stop();
                SharePreferencesUlits.saveString(getApplication(), Constants.LOACTIONADDRESS, privence);
                if (StringUtils.isEmpty(SharePreferencesUlits.getString(getApplication(),Constants.ADDRESS,Constants.NUll_VALUE))){
                    Log.i(getApplication() + "", str + "address");
                    SharePreferencesUlits.saveString(getApplication(),Constants.ADDRESS,str);
                }
            }
        } catch (Exception e) {
            LogUtil.e("MyActivity logmsg error:",e.toString());
            e.printStackTrace();
        }
    }
}
