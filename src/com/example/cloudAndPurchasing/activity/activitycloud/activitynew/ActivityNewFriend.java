package com.example.cloudAndPurchasing.activity.activitycloud.activitynew;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
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
import com.example.cloudAndPurchasing.adapter.AdapterCloudInvite;
import com.example.cloudAndPurchasing.adapter.adapterfriend.AdapterNewFriend;
import com.example.cloudAndPurchasing.customcontrol.listviewupdown.PullToRefreshLayout;
import com.example.cloudAndPurchasing.customcontrol.listviewupdown.PullableListView;
import com.example.cloudAndPurchasing.http.HttpApi;
import com.example.cloudAndPurchasing.http.HttpTransfeData;
import com.example.cloudAndPurchasing.http.Numbers;
import com.example.cloudAndPurchasing.http.SaveShared;
import com.example.cloudAndPurchasing.imageloder.Imageloderinit;
import com.example.cloudAndPurchasing.imageloder.Myimageloder;
import com.example.cloudAndPurchasing.kind.Friend;
import com.example.cloudAndPurchasing.kind.Statese;
import com.example.cloudAndPurchasing.manager.MyThreadPoolManager;
import com.example.cloudAndPurchasing.popupwindows.SpinnerPopup;
import com.example.cloudAndPurchasing.tiltle.TeaskBar;
import com.example.cloudAndPurchasing.zhi.LogUtil;
import com.example.cloudAndPurchasing.zhi.ValuePrice;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/3/30 0030.
 */
public class ActivityNewFriend extends BaseFragmentActivity implements View.OnClickListener, AdapterView.OnItemClickListener, PullToRefreshLayout.OnRefreshListener {
    private ListView listView;
    private Button imageButton;
    private ArrayList<Friend> arrayList,friends;
    private AdapterNewFriend adapterNewFriend;
    private PopupWindow popupWindow;
    private boolean flag = false;
    private Statese statese;
    private Myimageloder imageloderinit;
    private String yesoron = null;
    private String ID = null,name = null;
    private int index = 0;
    private LinearLayout linearLayout_ol,linearLayout_ool;
    private PullToRefreshLayout pullToRefreshLayout;
    private AdapterNewFriend.YesOnclickselectListener yesOnclickselectListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitynewfriendlayout);

        //改变状态栏颜色
        TeaskBar.onSystemoutcolor(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        //初始化数据
        initData();
        //初始化数据
        initview();
    }
    public void initData(){
        LogUtil.i("ActivityNewFriend initData start.","");
        friends = new ArrayList<Friend>();
        index = 1;
        listView = (ListView)findViewById(R.id.listview_newfriend);
        imageloderinit = new Myimageloder(this);
        listView.setOnScrollListener(new PauseOnScrollListener(imageloderinit.imageLoader,true,false));
        try{
            MyThreadPoolManager.getInstance().execute(runnable);
        }catch (Exception e){
            LogUtil.e("ActivityNewFriend initData error:",e.toString());
        }finally {
            LogUtil.i("ActivityNewFrieng initData end.","");
        }
    }

    /**
     * 获取消息
     */
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            arrayList = new ArrayList<Friend>();
            String token = SaveShared.tokenget(getApplication());
            arrayList = HttpTransfeData.newfriendhttp(arrayList,getApplication(),token,index);
            if (arrayList != null){
                friends.addAll(arrayList);
            }
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("con", friends);
            message.what = Numbers.ONE;
            message.setData(bundle);
            handler.sendMessage(message);
        }
    };
    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    private void spinnerpoup(boolean flag,final View view1, final Integer tag) {
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
                linearLayout_ool.setVisibility(View.VISIBLE);
                yesoron = "A";
                adapterNewFriend.updateview_cl(tag, yesoron, listView);
                MyThreadPoolManager.getInstance().execute(runnable1);
                popupWindow.dismiss();
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout_ool.setVisibility(View.VISIBLE);
                yesoron = "R";
                adapterNewFriend.updateview_cl(tag, yesoron, listView);
                MyThreadPoolManager.getInstance().execute(runnable1);
                popupWindow.dismiss();
            }
        });
        if (flag){
            popupWindow.showAsDropDown(view1);
        }
    }

    /**
     * 好友消息处理
     */
    private Runnable runnable1 = new Runnable() {
        @Override
        public void run() {
            int c = Numbers.TWO;
            statese = new Statese();
            String token = SaveShared.tokenget(getApplication());
            statese = HttpTransfeData.messagehandling(token,getApplication(),ID,name,yesoron,HttpApi.news_friend,c);
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putParcelable("con_ol",statese);
            message.what = Numbers.TWO;
            message.setData(bundle);
            handler.sendMessage(message);
        }
    };
    public void initview(){
        linearLayout_ool = (LinearLayout)findViewById(R.id.linearlayout_friend_news_loading);
        linearLayout_ol = (LinearLayout)findViewById(R.id.linearlayout_friend_news_this);
        linearLayout_ool.setVisibility(View.VISIBLE);
        pullToRefreshLayout = (PullToRefreshLayout)findViewById(R.id.linearlayout_thisfriendnews);
        imageButton = (Button)findViewById(R.id.imagebutton_newfriendback);
        listView.setOnItemClickListener(this);
        imageButton.setOnClickListener(this);
        pullToRefreshLayout.setOnRefreshListener(this);
        linearLayout_ool.setVisibility(View.VISIBLE);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imagebutton_newfriendback:
                finish();
                break;
        }
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
    private Handler  handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    linearLayout_ool.setVisibility(View.GONE);
                    arrayList = new ArrayList<Friend>();
                    arrayList = msg.getData().getParcelableArrayList("con");
                    if (arrayList != null){
                        if (arrayList.size()>0){
                            linearLayout_ol.setVisibility(View.GONE);
                            yesOnclickselectListener = new AdapterNewFriend.YesOnclickselectListener() {
                                @Override
                                protected void yesOnclickListener(Integer tag, View v) {
                                    switch (v.getId()){
                                        case R.id.button_yes:
                                            ID = arrayList.get(tag).getID();
                                            name = arrayList.get(tag).getNickname();
                                            flag = true;
                                            spinnerpoup(flag,v,tag);
                                            break;
                                    }
                                }
                            };
                        }else {
                            linearLayout_ol.setVisibility(View.VISIBLE);
                        }
                    }else {
                        linearLayout_ol.setVisibility(View.VISIBLE);
                    }
                    adapterNewFriend = new AdapterNewFriend(imageloderinit.imageLoader,imageloderinit.options,getApplicationContext(),arrayList,yesOnclickselectListener);
                    listView.setAdapter(adapterNewFriend);
                    break;
                case 2:
                    linearLayout_ool.setVisibility(View.GONE);
                    statese = new Statese();
                    statese = msg.getData().getParcelable("con_ol");
                    if (statese != null){
                        if (statese.getState().equals(String.valueOf(Numbers.ONE))){
                            friends = new ArrayList<Friend>();
                            friends.clear();
                            MyThreadPoolManager.getInstance().execute(runnable);
                            if (statese.getMsg().equals("修改成功")){
                                Toast.makeText(getApplication(),"消息处理成功",Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(getApplication(),statese.getMsg(),Toast.LENGTH_SHORT).show();
                        }

                    }
                    break;
            }
        }
    };
    @Override
    protected void onDestroy() {
        if (arrayList != null){
            arrayList.clear();
        }
        super.onDestroy();
    }

    @Override
    public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
        new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                friends = new ArrayList<Friend>();
                friends.clear();
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
//                MyThreadPoolManager.getInstance().execute(runnable);
                pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
            }
        }.sendEmptyMessageDelayed(0,1500);
    }
}
