package com.example.cloudAndPurchasing;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.*;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.cloudAndPurchasing.http.HttpTransfeData;
import com.example.cloudAndPurchasing.http.Numbers;
import com.example.cloudAndPurchasing.http.SaveShared;
import com.example.cloudAndPurchasing.kind.*;
import com.example.cloudAndPurchasing.popupwindows.PopupEdition;
import com.example.cloudAndPurchasing.service.VersionCode;
import com.example.cloudAndPurchasing.sqllite.MySqLite;
import com.example.cloudAndPurchasing.tiltle.TeaskBar;
import com.example.cloudAndPurchasing.zhi.LogUtil;

/**
 * Created by Administrator on 2016/5/25 0025.
 */
public class ActivityCartoon extends BaseFragmentActivity implements View.OnClickListener {
    private Button button;
    private TimeCount time;
    public LocationClient mLocationClient = null;
    public BDLocationListener  myListener = new MyLocationListener();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitycartoonlayout);

        TeaskBar.onSystemoutcolor(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        MySqLite mySqLite = new MySqLite(this);
        mySqLite.getWritableDatabase();

        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), MyLocationService.class);
        startService(intent);
        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
        mLocationClient.registerLocationListener( myListener );
        //初始化定位
        initLocation();
        mLocationClient.start();
        initData();
        interent();
    }
    //网络判断
    private void interent() {
        boolean internet = Internet.isNetworkConnected(this);
        if (internet){
            boolean wifi = Internet.isWifiConnected(this);
            boolean mo = Internet.isMobileConnected(this);
            if (wifi){
                int th = Internet.getConnectedType(this);
            }else if (mo){
                int th = Internet.getConnectedType(this);
            }

        }else {
            Toast.makeText(this, "当前没有网络链接", Toast.LENGTH_LONG).show();
        }
        LogUtil.v("ActivityCartoon","网络链接~");
    }
    private void initData() {
        time = new TimeCount(4000,1000);
        time.start();
        button = (Button)findViewById(R.id.button_cartoon);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_cartoon:
                time.cancel();
                Intent intent = new Intent();
                intent.setClass(this,MyActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
    private class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }
        @Override
        public void onFinish() {//计时完毕时触发
            LogUtil.v("ActivityCartoon","倒计时~");
            Intent intent = new Intent();
            intent.setClass(getApplication(),MyActivity.class);
            startActivity(intent);
            finish();
        }
        @Override
        public void onTick(long millisUntilFinished){//计时过程显示
            button.setText("跳过 "+millisUntilFinished /1000+"s");
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
                SharePreferencesUlits.saveString(getApplication(),Constants.LOCATIONCITY,str);
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
}
