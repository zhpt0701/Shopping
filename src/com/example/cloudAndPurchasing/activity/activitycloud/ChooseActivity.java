package com.example.cloudAndPurchasing.activity.activitycloud;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.*;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.widget.*;
import com.example.cloudAndPurchasing.BaseFragmentActivity;
import com.example.cloudAndPurchasing.MyActivity;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.activity.activitycloud.activitythis.ActivityAddAddress;
import com.example.cloudAndPurchasing.adapter.adaptercloud.AdapterAddress;
import com.example.cloudAndPurchasing.adapter.adaptercloud.AdapterchooseAddress;
import com.example.cloudAndPurchasing.customcontrol.listviewupdown.PullToRefreshLayout;
import com.example.cloudAndPurchasing.customcontrol.listviewupdown.PullableListView;
import com.example.cloudAndPurchasing.http.HttpTransfeData;
import com.example.cloudAndPurchasing.http.Numbers;
import com.example.cloudAndPurchasing.http.SaveShared;
import com.example.cloudAndPurchasing.kind.AddressGoods;
import com.example.cloudAndPurchasing.kind.Statese;
import com.example.cloudAndPurchasing.manager.MyThreadPoolManager;
import com.example.cloudAndPurchasing.tiltle.TeaskBar;
import com.example.cloudAndPurchasing.zhi.LogUtil;
import com.example.cloudAndPurchasing.zhi.ValuePrice;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/6/17 0017.
 */
public class ChooseActivity extends BaseFragmentActivity implements View.OnClickListener, PullToRefreshLayout.OnRefreshListener {
    private Button imageButton,imageButton_ol;
    private PullableListView listView;
    private PullToRefreshLayout pullToRefreshLayout;
    private TextView textView;
    private LinearLayout linearLayout_ol,linearLayout;
    private ArrayList<AddressGoods> arrayList;
    private AdapterchooseAddress adapterAddress;
    private boolean flag = false;
    private PopupWindow popupWindow;
    private Button button1,button2;
    private String addressid = null;
    private SharedPreferences sharedPreferences;
    private Statese statese;
    private int index = 1;
    private String number = "0";
    private String name = null,phone = null,addresss = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chooseactivitylayout);
        TeaskBar.onSystemoutcolor(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        initData();
        initview();
    }

    public void initData(){
        number = "0";
        String aid = getIntent().getStringExtra("aid");
        String isdelievery = getIntent().getStringExtra("isdeliery");
        SharedPreferences.Editor sh = getSharedPreferences("data",MODE_PRIVATE).edit();
        if (aid != null){
            sh.putString("aid",aid);
        }
        if (isdelievery != null){
            sh.putString("isdelevery",isdelievery);
        }
        sh.commit();
        sharedPreferences = getSharedPreferences("data",MODE_PRIVATE);
        arrayList = new ArrayList<AddressGoods>();
        try {
            MyThreadPoolManager.getInstance().execute(runnable);
        }catch (Exception e){
            LogUtil.e("ChooseActivity initData error:",e.toString());
        }finally {
            LogUtil.i("ChooseActivity initData end.","");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        sharedPreferences = getSharedPreferences("data",MODE_PRIVATE);
    }

    /**
     * 获取地址信息
     */
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            String token = SaveShared.tokenget(getApplication());
            arrayList = HttpTransfeData.httpaddress(getApplication(), token);
            Message message = new Message();
            Bundle bundle = new Bundle();
            message.what = Numbers.ONE;
            bundle.putParcelableArrayList("address",arrayList);
            message.setData(bundle);
            mhandler.sendMessage(message);
        }
    };
    /**
     * 删除地址
     */
    private Runnable runnableDelete = new Runnable() {
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
    private Handler mhandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    linearLayout_ol.setVisibility(View.GONE);
                    arrayList = new ArrayList<AddressGoods>();
                    arrayList = msg.getData().getParcelableArrayList("address");
                    if (arrayList != null){
                        if (arrayList.size()>0){
                            linearLayout.setVisibility(View.GONE);
                            listView.setVisibility(View.VISIBLE);
                            AdapterchooseAddress.CHooseAddressOnClickListener addressOnClickListener = new AdapterchooseAddress.CHooseAddressOnClickListener() {
                                @Override
                                protected void addressOnClickListener(Integer tag, View v) {
                                    switch (v.getId()){
                                        case R.id.button_choosedeleteaddress:
                                            flag = true;
                                            addressid = arrayList.get(tag).getAddressid();
                                            addDeletePopupWindows(flag, v);
                                            break;
                                    }
                                }
                            };
                            adapterAddress = new AdapterchooseAddress(getApplication(),arrayList,addressOnClickListener);
                            listView.setAdapter(adapterAddress);
                            listView.setSelected(true);
                            listView.setSelection(adapterAddress.getCount()-10);
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    if (number.equals("0")){
                                        linearLayout_ol.setVisibility(View.VISIBLE);
                                        name = arrayList.get(position).getName();
                                        phone = arrayList.get(position).getPhones();
                                        addresss = arrayList.get(position).getAddress();
                                        SharedPreferences.Editor editor = getSharedPreferences("data",MODE_PRIVATE).edit();
                                        editor.putString("address_ool",addresss);
                                        editor.putString("username",name);
                                        editor.putString("userphone", phone);
                                        editor.commit();
                                        number = "1";
                                        MyThreadPoolManager.getInstance().execute(runnable3);
                                    }
                                }
                            });
                        }else {
                            listView.setVisibility(View.GONE);
                            linearLayout.setVisibility(View.VISIBLE);
                        }
                    }else {
                        listView.setVisibility(View.GONE);
                        linearLayout.setVisibility(View.VISIBLE);
                    }
                    break;
                case 2:
                    statese = msg.getData().getParcelable("con");
                    if (statese != null){
                        if (statese.getState().equals(String.valueOf(Numbers.ONE))){
                            MyThreadPoolManager.getInstance().execute(runnable);
                        }
                        Toast.makeText(getApplication(),statese.getMsg(),Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 3:
                    linearLayout_ol.setVisibility(View.GONE);
                    statese = new Statese();
                    statese = msg.getData().getParcelable("con_ol");
                    number = "0";
                    if (statese!=null){
                        if (statese.getState().equals(String.valueOf(Numbers.ONE))){
                            Intent intent = new Intent();
                            if (sharedPreferences.getString("isdelevery","").equals(String.valueOf(Numbers.TWO))){
//                                intent.putExtra("cloudCode",);
                                intent.putExtra("stateCode",String.valueOf(Numbers.ONE));
                                intent.putExtra("customcode",statese.getData());
                                intent.setClass(getApplication(),ActivityMA.class);
                            }else {
                                intent.putExtra("stateCode",String.valueOf(Numbers.TWO));
                                intent.setClass(getApplication(),ActivityMA.class);
                            }
                            startActivity(intent);
                            finish();
                        }
                        Toast.makeText(getApplication(), statese.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };
    /**
     * 初始化控件
     */
    public void initview(){
        linearLayout_ol = (LinearLayout)findViewById(R.id.linearlayout_chooseactivity_progressbar);
        linearLayout_ol.setVisibility(View.VISIBLE);
        pullToRefreshLayout = (PullToRefreshLayout)findViewById(R.id.chooseaddressthis);
        imageButton = (Button)findViewById(R.id.imagebutton_chooseaddressback);
        imageButton_ol = (Button)findViewById(R.id.imagebutton_add_chooseaddress);
        listView = (PullableListView)findViewById(R.id.listview_chooseaddress);
        linearLayout = (LinearLayout)findViewById(R.id.linealryout_this_address);
        imageButton.setOnClickListener(this);
        imageButton_ol.setOnClickListener(this);
        pullToRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.imagebutton_chooseaddressback:
                finish();
                break;
            case R.id.imagebutton_add_chooseaddress:
                intent.putExtra("up", String.valueOf(Numbers.TWO));
                intent.setClass(this, ActivityAddAddress.class);
                startActivity(intent);
                break;
            case R.id.button_deleteaddresspop:
                linearLayout_ol.setVisibility(View.VISIBLE);
                MyThreadPoolManager.getInstance().execute(runnableDelete);
                popupWindow.dismiss();
                break;
            case R.id.button_addressdeletequxiaopop:
                popupWindow.dismiss();
                break;
        }

    }

    /**
     * 领奖接口
     */
    private Runnable runnable3 = new Runnable() {
        @Override
        public void run() {
            String token = SaveShared.tokenget(getApplication());
            String uid = SaveShared.uid(getApplication());
            String id = sharedPreferences.getString("aid","");
            statese = new Statese();
            statese = HttpTransfeData.awardhttp(getApplication(),token,uid,id,name,phone,addresss);
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putParcelable("con_ol",statese);
            message.what = Numbers.THREE;
            message.setData(bundle);
            mhandler.sendMessage(message);
        }
    };
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
//    @Override
//    protected void onResume() {
//        JPushInterface.onResume(this);
//        super.onResume();
//    }
//    @Override
//    protected void onPause() {
//        JPushInterface.onPause(this);
//        super.onPause();
//    }
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//    }
//    // 初始化 JPush。如果已经初始化，但没有登录成功，则执行重新登录。
//    private void init(){
//        JPushInterface.init(getApplicationContext());
//    }
    @Override
    public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
        new Handler()
        {
            @Override
            public void handleMessage(Message msg)
            {
                //
                index = 1;
//                mhandler.post(runnable);
                pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
            }
        }.sendEmptyMessageDelayed(0, 3000);
    }

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

    @Override
    protected void onDestroy() {
        if (arrayList != null){
            arrayList.clear();
        }
        super.onDestroy();
    }
}
