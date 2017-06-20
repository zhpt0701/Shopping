package com.example.cloudAndPurchasing.activity.activityshopping;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.cloudAndPurchasing.BaseFragmentActivity;
import com.example.cloudAndPurchasing.MyActivity;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.adapter.AdapterDetail;
import com.example.cloudAndPurchasing.customcontrol.listviewupdown.PullToRefreshLayout;
import com.example.cloudAndPurchasing.customcontrol.listviewupdown.PullableListView;
import com.example.cloudAndPurchasing.http.HttpTransfeData;
import com.example.cloudAndPurchasing.http.Numbers;
import com.example.cloudAndPurchasing.http.SaveShared;
import com.example.cloudAndPurchasing.imageloder.Imageloderinit;
import com.example.cloudAndPurchasing.kind.*;
import com.example.cloudAndPurchasing.manager.MyThreadPoolManager;
import com.example.cloudAndPurchasing.tiltle.TeaskBar;
import com.example.cloudAndPurchasing.times.CToast;
import com.example.cloudAndPurchasing.zhi.LogUtil;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/5/19 0019.
 */
public class ActivityShoppingdata extends BaseFragmentActivity implements View.OnClickListener, PullToRefreshLayout.OnRefreshListener {
    private Button button;
    private Button imageButton;
    private PullableListView listView;
    private TextView textView;
    private String title = null;
    private Imageloderinit imageloderinit;
    private AdapterDetail adapterdetail;
    private ArrayList<Goods> arrayList,arrayList1;
    private int index = 0;
    private String pid =null;
    private Statese statese;
    private String code = "0";
    private LinearLayout linearLayout_loading,linearLayout_delete;
    private PullToRefreshLayout pullToRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityshoppingdatalayout);

        TeaskBar.onSystemoutcolor(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        initData();
        initView();
    }

    private void initView() {
        linearLayout_loading = (LinearLayout)findViewById(R.id.linearlayout_shoppingdateil_loading);
        linearLayout_delete = (LinearLayout)findViewById(R.id.linearlayout_shoppingdateil_delete);
        linearLayout_loading.setVisibility(View.VISIBLE);
//        textView = (TextView)findViewById(R.id.textview_eachdata);
        imageButton = (Button)findViewById(R.id.imagebutton_eachdataback);
        pullToRefreshLayout = (PullToRefreshLayout)findViewById(R.id.pulltorelayout_shoppingeach);
//        button = (Button)findViewById(R.id.button_eachdata);
//        textView.setText(title);
        imageButton.setOnClickListener(this);
//        button.setOnClickListener(this);
        pullToRefreshLayout.setOnRefreshListener(this);
    }
    private void initData() {
        LogUtil.i("ActivityShoppingdata initData start.","");
        index = 1;
        arrayList1 = new ArrayList<Goods>();
        imageloderinit = new Imageloderinit(this);
        listView = (PullableListView)findViewById(R.id.listview_eachdata);
        title = getIntent().getStringExtra("each_ol");
        try{
            MyThreadPoolManager.getInstance().execute(runnable);
        }catch (Exception e){
            LogUtil.e("ActivityShoppingdata initData error:",e.toString());
        }finally {
            LogUtil.i("ActivityShoppingData initData end.","");
        }
    }
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            arrayList = new ArrayList<Goods>();
            arrayList = HttpTransfeData.httpshoppingeach(getApplication(),title,index);
            if (arrayList != null){
                arrayList1.addAll(arrayList);
            }
            Message message = new Message();
            Bundle bundle = new Bundle();
            message.what = Numbers.ONE;
            bundle.putParcelableArrayList("data",arrayList1);
            message.setData(bundle);
            handler.sendMessage(message);
        }
    };
    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.imagebutton_eachdataback:
                intent.setClass(this,ActivityShoppingEach.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    private CToast cToast;
    private String limitcount;
    private String surpcount;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == Numbers.ONE){
                linearLayout_loading.setVisibility(View.GONE);
                arrayList = msg.getData().getParcelableArrayList("data");
                if (arrayList != null){
                    if (arrayList.size()>0){
                        linearLayout_delete.setVisibility(View.GONE);
                    }else {
                        linearLayout_delete.setVisibility(View.VISIBLE);
                    }
                }else {
                    linearLayout_delete.setVisibility(View.VISIBLE);
                }
                AdapterDetail.DataOnclicklistner dataOnclicklistner = new AdapterDetail.DataOnclicklistner() {
                    @Override
                    protected void dataOnclicklistner(Integer tag, View v) {
                        switch (v.getId()){
                            case R.id.imagebutton_gwc:
                                if (!StringUtils.isEmpty(SharePreferencesUlits.getString(getApplication(), Constants.TOKEN,Constants.NUll_VALUE))){
                                    pid = arrayList.get(tag).getGoodsid();;
                                    limitcount = arrayList.get(tag).getGoodsxian();
                                    surpcount = arrayList.get(tag).getGoods_surplus();
                                    MyThreadPoolManager.getInstance().execute(runnableGoodsNumber);
                                }else {
                                    cToast = CToast.makeText(getApplication(),"请登录~",600);
                                    cToast.show();
                                }

                                break;
                        }
                    }
                };
                adapterdetail = new AdapterDetail(code,getApplication(),arrayList,dataOnclicklistner,imageloderinit.imageLoader,imageloderinit.options);
                listView.setAdapter(adapterdetail);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent();
                        intent.putExtra("outid", arrayList.get(position).getGoodsid());
                        intent.putExtra("prid", arrayList.get(position).getProductID());
                        intent.putExtra("cloud",String.valueOf(Numbers.EIGHT));
                        intent.putExtra("content",title);
                        intent.setClass(getApplication(), ActivityGoodsDateil.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }else if (msg.what == Numbers.THREE){
                statese = new Statese();
                statese = msg.getData().getParcelable("content_number");
                if (statese != null){
                    if (statese.getState() != null){
                        if (statese.getState().equals(String.valueOf(Numbers.ONE))){
                            if (!limitcount.equals("0")){
                                if (Integer.parseInt(limitcount)>0){
                                    if (Integer.parseInt(statese.getData())<Integer.parseInt(limitcount)){
                                        try {
                                            MyThreadPoolManager.getInstance().execute(runnable1);
                                        }catch (Exception e){
                                            LogUtil.e("FragmentShopping handler error:",e.toString());
                                        }
                                    }else {
                                        cToast = CToast.makeText(getApplication(), "没有更多商品了", 600);
                                        cToast.show();
                                    }
                                }
                            }else {
                                if (Integer.parseInt(statese.getData())<Integer.parseInt(surpcount)){
                                    MyThreadPoolManager.getInstance().execute(runnable1);
                                }else {
                                    cToast = CToast.makeText(getApplication(), "没有更多商品了", 600);
                                    cToast.show();
                                }
                            }
                        }else {
                            if (statese.getMsg().equals("token失效")){
                                MyThreadPoolManager.getInstance().execute(runnableSingle);
                            }else {
                                cToast = CToast.makeText(getApplication(),statese.getMsg(),600);
                                cToast.show();
                            }
                        }
                    }
                }
            }else if (msg.what == Numbers.FOUR){
                statese = new Statese();
                statese = msg.getData().getParcelable("state");
                linearLayout_loading.setVisibility(View.GONE);
                if (statese != null){
                    if (statese.getState().equals(String.valueOf(Numbers.ONE))){
                        if(!TextUtils.isEmpty(statese.getData())){
                            if (statese.getData().equals("true")){
                                try {
                                    MyThreadPoolManager.getInstance().execute(runnableCarNumber);
                                }catch (Exception e){
                                    LogUtil.e("FragmentShopping handler error:",e.toString());
                                }
                            }
                        }
                        cToast = CToast.makeText(getApplication(), "加入购物车成功~", 600);
                        cToast.show();
                    }
                }
            }else if (msg.what == Numbers.FIVE){
                statese = new Statese();
                statese = msg.getData().getParcelable("content");
                if (statese!=null) {
                    if (statese.getState() != null) {
                        if (statese.getState().equals(String.valueOf(Numbers.ONE))) {
                            if (!statese.getData().equals("null")) {
                                if (!TextUtils.isEmpty(statese.getData())) {
                                    MyActivity.textView.setText(statese.getData());
                                }
                                SaveShared.sharedcarnumbersave(getApplication(), statese.getData());
                            }
                        }
                    }
                }
            }else if (msg.what == Numbers.SIX){
                statese = msg.getData().getParcelable("this");
                if (statese != null){
                    if (statese.getState().equals(String.valueOf(Numbers.ONE))){
                        try{
                            MyThreadPoolManager.getInstance().execute(runnableGoodsNumber);
                        }catch (Exception e){
                            LogUtil.e("MyActivity handler error:",e.toString());
                        }
                    }
                }
            }
        }
    };
    /**
     * 获取购物车商品数量
     */
    private Runnable runnableGoodsNumber = new Runnable() {
        @Override
        public void run() {
            statese = new Statese();
            String token = SaveShared.tokenget(getApplication());
            statese = HttpTransfeData.httpshoppingcat(getApplication(),statese,token,pid);
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putParcelable("content_number",statese);
            message.setData(bundle);
            message.what = Numbers.THREE;
            handler.sendMessage(message);
        }
    };
    /**
     * 获取商品件数
     */
    private Runnable runnableCarNumber = new Runnable() {
        @Override
        public void run() {
            statese = new Statese();
            //从sharedPreferences中获取储存的token
            String token = SaveShared.tokenget(getApplication());
            //http网络请求
            statese = HttpTransfeData.getcarnumberhttp(statese,getApplication(),token);
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putParcelable("content",statese);
            message.what = Numbers.FIVE;
            message.setData(bundle);
            handler.sendMessage(message);
        }
    };
    /**
     * 重新登陆
     */
    private Runnable runnableSingle = new Runnable() {
        @Override
        public void run() {
            statese = new Statese();
            String user_ol = SaveShared.loadong_name(getApplication());
            String pass = SaveShared.loading_pass(getApplication());
            statese = HttpTransfeData.httppostloding(getApplication(), user_ol, pass);
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putParcelable("this", statese);
            message.setData(bundle);
            message.what = Numbers.SIX;
            handler.sendMessage(message);
        }
    };
    private Runnable runnable1 = new Runnable() {
        @Override
        public void run() {
            statese = new Statese();
            String token = SaveShared.tokenget(getApplication());
            String uid = SaveShared.uid(getApplication());
            String num = "1";
            statese = HttpTransfeData.goodsaddshoppingcat(getApplication(),pid,uid,token,num);
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putParcelable("state",statese);
            message.what = Numbers.FOUR;
            message.setData(bundle);
            handler.sendMessage(message);
        }
    };
    @Override
    public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
        new  Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                index = 1;
                arrayList1 = new ArrayList<Goods>();
                MyThreadPoolManager.getInstance().execute(runnable);
                pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
            }
        }.sendEmptyMessageDelayed(0,2000);
    }

    @Override
    public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
        new  Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                index++;
                MyThreadPoolManager.getInstance().execute(runnable);
                pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
            }
        }.sendEmptyMessageDelayed(0,2000);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
