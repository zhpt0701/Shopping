package com.example.cloudAndPurchasing.activity.activitycloud.activitynew;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.*;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;
import com.example.cloudAndPurchasing.BaseFragmentActivity;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.activity.activitycloud.activitynew.ActivityNews;
import com.example.cloudAndPurchasing.adapter.AdapterCloudInvite;
import com.example.cloudAndPurchasing.customcontrol.listviewupdown.PullToRefreshLayout;
import com.example.cloudAndPurchasing.customcontrol.listviewupdown.PullableListView;
import com.example.cloudAndPurchasing.http.HttpApi;
import com.example.cloudAndPurchasing.http.HttpTransfeData;
import com.example.cloudAndPurchasing.http.Numbers;
import com.example.cloudAndPurchasing.http.SaveShared;
import com.example.cloudAndPurchasing.imageloder.Myimageloder;
import com.example.cloudAndPurchasing.kind.Friend;
import com.example.cloudAndPurchasing.kind.Statese;
import com.example.cloudAndPurchasing.manager.MyThreadPoolManager;
import com.example.cloudAndPurchasing.tiltle.TeaskBar;
import com.example.cloudAndPurchasing.zhi.LogUtil;
import com.example.cloudAndPurchasing.zhi.ValuePrice;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/3/30 0030.
 */
public class ActivityAsk extends BaseFragmentActivity implements View.OnClickListener, PullToRefreshLayout.OnRefreshListener {
    private ListView listView;
    private PullToRefreshLayout pullToRefreshLayout;
    private Button imageButton;
    private AdapterCloudInvite adapterCloudInvite;
    private ArrayList<Friend> arrayList,friends;
    private PopupWindow popupWindow;
    private boolean flag = false;
    private LinearLayout linearLayout,linearLayout_delete;
    private Myimageloder myimageloder;
    private String yesoron = null;
    private String ID = null,name = null;
    private Statese statese;
    private int index = 0;
    private int del = 0,rovmen = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityasklayout);
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
        LogUtil.i("ActivityAsk initdata start.","");
        myimageloder = new Myimageloder(this);
        index = 1;
        friends = new ArrayList<Friend>();
        arrayList = new ArrayList<Friend>();
        try{
            MyThreadPoolManager.getInstance().execute(runnable);
        }catch (Exception e){
            LogUtil.e("ActivityAsk initData error:",e.toString());
        }finally {
            LogUtil.i("ActivityAsk initData end.","");
        }
    }

    /**
     * 获取消息
     */
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            String token = SaveShared.tokenget(getApplication());
            arrayList = HttpTransfeData.httpnewsask(getApplication(),token);
            if (arrayList != null){
                friends.addAll(arrayList);
            }
            Message message = new Message();
            Bundle bundle = new Bundle();
            message.what = Numbers.ONE;
            bundle.putParcelableArrayList("content",friends);
            message.setData(bundle);
            handler.sendMessage(message);
        }
    };
    public void initview(){
        linearLayout = (LinearLayout)findViewById(R.id.linearlayout_shoppingdateil_loading);
        linearLayout_delete = (LinearLayout)findViewById(R.id.linearlayout_shoppingdateil_delete);
        linearLayout.setVisibility(View.VISIBLE);
        pullToRefreshLayout = (PullToRefreshLayout)findViewById(R.id.linearlayout_thisyungounews);
        imageButton = (Button)findViewById(R.id.imagebutton_friendback);
        listView = (ListView)findViewById(R.id.listview_cloudfriendask);
        imageButton.setOnClickListener(this);
        pullToRefreshLayout.setOnRefreshListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imagebutton_friendback:
                finish();
                break;
        }
    }
    private void spinnerpoup(boolean flag, final View view1, final Integer tag) {
        popupWindow = new PopupWindow();
        View view = LayoutInflater.from(this).inflate(R.layout.spinnerpouplayout,null);
        popupWindow.setContentView(view);
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        Button button = (Button)view.findViewById(R.id.button_tongyi);
        Button button1 = (Button)view.findViewById(R.id.button_butongyi);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout.setVisibility(View.VISIBLE);
                yesoron = "A";
                adapterCloudInvite.updateview_ol(tag, yesoron, listView);
                MyThreadPoolManager.getInstance().execute(runnable1);
                popupWindow.dismiss();
                del = tag;
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout.setVisibility(View.VISIBLE);
                yesoron = "R";
                adapterCloudInvite.updateview_ol(tag, yesoron, listView);
                MyThreadPoolManager.getInstance().execute(runnable1);
                popupWindow.dismiss();
                del = tag;
            }
        });
        if (flag){
            popupWindow.showAsDropDown(view1);
        }
    }

    /**
     * 消息处理
     */
    private Runnable runnable1 = new Runnable() {
        @Override
        public void run() {
            int c = Numbers.ONE;
            statese = new Statese();
            String token1 = SaveShared.tokenget(getApplication());
            statese = HttpTransfeData.messagehandling(token1,getApplication(),ID,name,yesoron,HttpApi.counld_news_frend,c);
            Message message = new Message();
            Bundle bundle = new Bundle();
            message.what = Numbers.TWO;
            bundle.putParcelable("con",statese);
            message.setData(bundle);
            handler.sendMessage(message);
        }
    };
    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    linearLayout.setVisibility(View.GONE);
                    arrayList = msg.getData().getParcelableArrayList("content");
                    if (arrayList !=null){
                        if (arrayList.size()>0){
                            linearLayout_delete.setVisibility(View.GONE);
                            AdapterCloudInvite.ShifuOnClickListener shifuOnClickListener = new AdapterCloudInvite.ShifuOnClickListener() {
                                @Override
                                protected void shifuOnClickListener(Integer tag, View v) {
                                    switch (v.getId()){
                                        case R.id.spinner_friend:
                                            ID = arrayList.get(tag).getRoomID();
                                            name = arrayList.get(tag).getID();
                                            flag = true;
                                            spinnerpoup(flag,v,tag);
                                            break;
                                    }
                                }
                            };
                            adapterCloudInvite = new AdapterCloudInvite(myimageloder.imageLoader,myimageloder.options,getApplication(),arrayList,shifuOnClickListener);
                            listView.setAdapter(adapterCloudInvite);
                            rovmen = 0;
                        }else {
                            linearLayout_delete.setVisibility(View.VISIBLE);
                        }

                    }else {
                        linearLayout_delete.setVisibility(View.VISIBLE);
                    }
                    break;
                case 2:
                    linearLayout.setVisibility(View.GONE);
                    statese = new Statese();
                    statese = msg.getData().getParcelable("con");
                    if (statese != null){
                        if (statese.getState() != null){
                            if (statese.getState().equals(String.valueOf(Numbers.ONE))){
                                friends = new ArrayList<Friend>();
                                MyThreadPoolManager.getInstance().execute(runnable);
                                if (statese.getMsg() !="null"){
                                    if (statese.getMsg().equals("修改成功")){
                                        Toast.makeText(getApplication(),"消息处理成功",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }else {
                                Toast.makeText(getApplication(),statese.getMsg(),Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                    break;
            }
        }
    };

    /**
     * 上拉刷新下拉加载
     * @param pullToRefreshLayout
     */
    @Override
    public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
        new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                friends = new ArrayList<Friend>();
                index = 1;
                MyThreadPoolManager.getInstance().execute(runnable);
                pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
            }
        }.sendEmptyMessageDelayed(0,1500);
    }
    @Override
    public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
        new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
//                index++;
//                mhandler.post(runnable);
                pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
            }
        }.sendEmptyMessageDelayed(0,1500);
    }

    @Override
    protected void onDestroy() {
        if (arrayList != null){
            arrayList.clear();
        }
        if (friends != null){
            friends.clear();
        }
        super.onDestroy();
    }
}
