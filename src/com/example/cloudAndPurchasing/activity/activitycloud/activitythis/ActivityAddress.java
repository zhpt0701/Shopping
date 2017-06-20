package com.example.cloudAndPurchasing.activity.activitycloud.activitythis;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.os.*;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;
import com.example.cloudAndPurchasing.BaseFragmentActivity;
import com.example.cloudAndPurchasing.MyActivity;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.activity.activitycloud.activitythis.ActivityAddAddress;
import com.example.cloudAndPurchasing.adapter.adaptercloud.AdapterAddress;
import com.example.cloudAndPurchasing.customcontrol.listviewupdown.PullToRefreshLayout;
import com.example.cloudAndPurchasing.customcontrol.listviewupdown.PullableListView;
import com.example.cloudAndPurchasing.http.HttpTransfeData;
import com.example.cloudAndPurchasing.http.Numbers;
import com.example.cloudAndPurchasing.http.SaveShared;
import com.example.cloudAndPurchasing.kind.AddressGoods;
import com.example.cloudAndPurchasing.kind.Statese;
import com.example.cloudAndPurchasing.manager.MyThreadPoolManager;
import com.example.cloudAndPurchasing.popupwindows.ShoppingPopup;
import com.example.cloudAndPurchasing.tiltle.TeaskBar;
import com.example.cloudAndPurchasing.times.CToast;
import com.example.cloudAndPurchasing.zhi.LogUtil;
import com.example.cloudAndPurchasing.zhi.ValuePrice;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/3/31 0031.
 */
public class ActivityAddress extends BaseFragmentActivity implements View.OnClickListener, PullToRefreshLayout.OnRefreshListener {
    private Button imageButton,imageButton_ol;
    private PullableListView listView;
    private PullToRefreshLayout pullToRefreshLayout;
    private LinearLayout linearLayout,linearLayout_delete;
    private ArrayList<AddressGoods> arrayList;
    private AdapterAddress adapterAddress;
    private boolean flag = false;
    private PopupWindow popupWindow;
    private Button button1,button2;
    private String addressid = null;
    private SharedPreferences sharedPreferences;
    private Statese statese;
    private int index = 1,count = 0;
    private CToast cToast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityaddresslayout);
        //设置状态栏颜色
        TeaskBar.onSystemoutcolor(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        //初始化数据
        initData();
        //初始化控件
        initview();
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.i("ActivityAddress initData start.","");
        sharedPreferences = getSharedPreferences("data",MODE_PRIVATE);
        arrayList = new ArrayList<AddressGoods>();
        try {
            MyThreadPoolManager.getInstance().execute(runnable);
        }catch (Exception e){
            LogUtil.i("ActivityAddress initData error:",e.toString());
        }
    }

    public void initData(){

    }

    /**
     * 调取线程进行数据请求
     */
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            String token = SaveShared.tokenget(getApplication());
            arrayList = HttpTransfeData.httpaddress(getApplication(),token);
            Message message = new Message();
            Bundle bundle = new Bundle();
            message.what = Numbers.ONE;
            bundle.putParcelableArrayList("address",arrayList);
            message.setData(bundle);
            mhandler.sendMessage(message);
        }
    };
    private Runnable runnableDeleteAddress = new Runnable() {
        @Override
        public void run() {
            statese = new Statese();
            statese = HttpTransfeData.deleteaddresshttp(getApplication(),sharedPreferences.getString("token",""),addressid);
            Message message = new Message();
            Bundle bundle = new Bundle();
            message.what = Numbers.TWO;
            bundle.putParcelable("con",statese);
            message.setData(bundle);
            mhandler.sendMessage(message);
        }
    };
    /**
     * 设置默认
     */
    private Runnable runnableDefult = new Runnable() {
        @Override
        public void run() {
            statese = new Statese();
            statese = HttpTransfeData.defluthttp(getApplication(),sharedPreferences.getString("token",""),addressid);
            Message message = new Message();
            Bundle bundle = new Bundle();
            message.what = Numbers.TWO;
            bundle.putParcelable("con",statese);
            message.setData(bundle);
            mhandler.sendMessage(message);
        }
    };
    /**
     * ui界面刷新
     */
    private Handler mhandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    linearLayout.setVisibility(View.GONE);
                    arrayList = msg.getData().getParcelableArrayList("address");
                    if (arrayList != null){
                        if (arrayList.size()>0){
                            count = arrayList.size();
                            linearLayout_delete.setVisibility(View.GONE);
                            listView.setVisibility(View.VISIBLE);
                            AdapterAddress.AddressOnClickListener addressOnClickListener = new AdapterAddress.AddressOnClickListener() {
                                @Override
                                protected void addressOnClickListener(Integer tag, View v) {
                                    switch (v.getId()){
                                        case R.id.button_editoraddress:
                                            Intent intent = new Intent();
                                            intent.putExtra("up", String.valueOf(Numbers.ONE));
                                            intent.putExtra("name", arrayList.get(tag).getName());
                                            intent.setClass(getApplicationContext(),ActivityAddAddress.class);
                                            startActivity(intent);
                                            finish();
                                            break;
                                        case R.id.button_deleteaddress:
                                            flag = true;
                                            addressid = arrayList.get(tag).getAddressid();
                                            addDeletePopupWindows(flag,v);
                                            break;
                                        case R.id.checkbox_default:
                                            addressid = arrayList.get(tag).getAddressid();
                                            adapterAddress.updateviewite(listView, tag);
                                            linearLayout.setVisibility(View.VISIBLE);
                                            MyThreadPoolManager.getInstance().execute(runnableDefult);
                                            break;
                                    }
                                }
                            };
                            adapterAddress = new AdapterAddress(getApplication(),arrayList,addressOnClickListener);
                            listView.setAdapter(adapterAddress);
                        }else {
                            listView.setVisibility(View.GONE);
                            linearLayout_delete.setVisibility(View.VISIBLE);
                        }
                    }else {
                        listView.setVisibility(View.GONE);
                        linearLayout_delete.setVisibility(View.VISIBLE);
                    }
                    break;
                case 2:
                    linearLayout.setVisibility(View.GONE);
                    statese = new Statese();
                    statese = msg.getData().getParcelable("con");
                    if (statese != null){
                        if (statese.getState().equals(String.valueOf(Numbers.ONE))){
                            MyThreadPoolManager.getInstance().execute(runnable);
                            cToast = CToast.makeText(getApplication(),statese.getMsg(),600);
                            cToast.show();
                        }else {
                            cToast = CToast.makeText(getApplication(),"默认地址设置失败,请重试~",600);
                            cToast.show();
                        }
                    }
                    break;
            }
        }
    };
    /**
     * 初始化控件
     */
    public void initview(){
        pullToRefreshLayout = (PullToRefreshLayout)findViewById(R.id.addressupdata);
        imageButton = (Button)findViewById(R.id.imagebutton_addressback);
        imageButton_ol = (Button)findViewById(R.id.imagebutton_add_address);
        listView = (PullableListView)findViewById(R.id.listview_address);
        linearLayout = (LinearLayout)findViewById(R.id.linearlayout_shoppingdateil_loading);
        linearLayout_delete = (LinearLayout)findViewById(R.id.linearlayout_shoppingdateil_delete);
        linearLayout.setVisibility(View.VISIBLE);
        //设置监听事件
        imageButton.setOnClickListener(this);
        imageButton_ol.setOnClickListener(this);
        pullToRefreshLayout.setOnRefreshListener(this);
    }
    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.imagebutton_addressback:
                finish();
                break;
            case R.id.imagebutton_add_address:
                if (count<3){
                    intent.putExtra("up", String.valueOf(Numbers.ONE));
                    intent.setClass(this,ActivityAddAddress.class);
                    startActivity(intent);
                }else {
                    cToast = CToast.makeText(getApplication(),"最多可添加三个收货地址~",600);
                    cToast.show();
                }
                break;
            case R.id.button_deleteaddresspop:
                linearLayout.setVisibility(View.VISIBLE);
                MyThreadPoolManager.getInstance().execute(runnableDeleteAddress);
                popupWindow.dismiss();
                break;
            case R.id.button_addressdeletequxiaopop:
                popupWindow.dismiss();
                break;
        }

    }

    /**
     * 删除弹框
     * @param flag
     * @param v
     */
    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    private void addDeletePopupWindows(boolean flag,View v) {
        popupWindow = new PopupWindow();
        View view = LayoutInflater.from(this).inflate(R.layout.addresspopup,null);
        popupWindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(WindowManager.LayoutParams.MATCH_PARENT);
        popupWindow.setContentView(view);
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        button1 = (Button)view.findViewById(R.id.button_deleteaddresspop);
        button2 = (Button)view.findViewById(R.id.button_addressdeletequxiaopop);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        if (flag){
            popupWindow.showAsDropDown(view);
        }
    }
    //下拉刷新
    @Override
    public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
        new Handler()
        {
            @Override
            public void handleMessage(Message msg)
            {
                //
//                arrayList = new ArrayList<AddressGoods>();
                index = 1;
//                mhandler.post(runnable);
                pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
            }
        }.sendEmptyMessageDelayed(0, 3000);
    }
    //上拉加载
    @Override
    public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
        new Handler()
        {
            @Override
            public void handleMessage(Message msg)
            {
                //
                index++;
//                mhandler.post(runnable);
                pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
            }
        }.sendEmptyMessageDelayed(0, 3000);
    }
}
