package com.example.cloudAndPurchasing.activity.activitycloud.activityfriend;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import com.example.cloudAndPurchasing.BaseFragmentActivity;
import com.example.cloudAndPurchasing.MyActivity;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.adapter.adapterfriend.AdapterNearby;
import com.example.cloudAndPurchasing.customcontrol.listviewupdown.PullToRefreshLayout;
import com.example.cloudAndPurchasing.customcontrol.listviewupdown.PullableListView;
import com.example.cloudAndPurchasing.http.HttpTransfeData;
import com.example.cloudAndPurchasing.http.Numbers;
import com.example.cloudAndPurchasing.http.SaveShared;
import com.example.cloudAndPurchasing.imageloder.Myimageloder;
import com.example.cloudAndPurchasing.kind.Constants;
import com.example.cloudAndPurchasing.kind.Friend;
import com.example.cloudAndPurchasing.kind.SharePreferencesUlits;
import com.example.cloudAndPurchasing.kind.Statese;
import com.example.cloudAndPurchasing.manager.MyThreadPoolManager;
import com.example.cloudAndPurchasing.tiltle.TeaskBar;
import com.example.cloudAndPurchasing.times.CToast;
import com.example.cloudAndPurchasing.zhi.LogUtil;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/8 0008.
 */
public class ActivitySelectNearby extends BaseFragmentActivity implements View.OnClickListener, PullToRefreshLayout.OnRefreshListener {
    private String content = null;
    private ListView listView;
    private Button imageButton;
    private ArrayList<Friend> arrayList;
    private AdapterNearby adapterNearby;
    private Myimageloder imageloderinit;
    private Statese statese;
    private LinearLayout linearLayout,linearLayout_delete;
    private PullToRefreshLayout pullToRefreshLayout;
    private PullableListView pullableListView;
    private String id = null,name = null;
    private CToast cToast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityselectnearylayout);

        TeaskBar.onSystemoutcolor(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        initData();
        initView();
    }

    /**
     * 初始化数据
     * @param
     */
    public void initData(){
        LogUtil.i("ActivitySelectNearby initData start.","");
        imageloderinit = new Myimageloder(getApplication());
        arrayList = new ArrayList<Friend>();
        try {
            MyThreadPoolManager.getInstance().execute(runnable);
        }catch (Exception e){
            LogUtil.e("ActivitySelectNearby initData error:",e.toString());
        }finally {
            LogUtil.i("ActivitySelectNearby initdata end.","");
        }
        Log.e("content" , content);
    }
    public void initView(){
        linearLayout = (LinearLayout)findViewById(R.id.linearlayout_shoppingdateil_loading);
        linearLayout_delete = (LinearLayout)findViewById(R.id.linearlayout_shoppingdateil_delete);
        linearLayout.setVisibility(View.VISIBLE);
        pullToRefreshLayout = (PullToRefreshLayout)findViewById(R.id.pulltorelayout_each);
        pullableListView = (PullableListView)findViewById(R.id.listview_each);
        imageButton = (Button)findViewById(R.id.imagebutton_fujin_back);
        pullableListView.setOnScrollListener(new PauseOnScrollListener(imageloderinit.imageLoader,true,false));
        imageButton.setOnClickListener(this);
        pullToRefreshLayout.setOnRefreshListener(this);
    }
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            content = getIntent().getStringExtra("each");
            Log.i("ActivityselectNearby",content);
            String token = SaveShared.tokenget(getApplication());
            String uid = SaveShared.uid(getApplication());
            arrayList = HttpTransfeData.httpfriend(getApplication(),token,uid,content);
            Message message = new Message();
            Bundle bundle = new Bundle();
            message.what = Numbers.ONE;
            bundle.putParcelableArrayList("friend_ol",arrayList);
            message.setData(bundle);
            handler.sendMessage(message);
        }
    };
    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.imagebutton_fujin_back:
                intent.setClass(this,ActivityAddFriend.class);
                startActivity(intent);
                finish();
                break;
        }
    }
    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    linearLayout.setVisibility(View.GONE);
                    arrayList = msg.getData().getParcelableArrayList("friend_ol");
                    if (arrayList != null){
                        if (arrayList.size()>0){
                            linearLayout_delete.setVisibility(View.GONE);
                            AdapterNearby.NearbyOnClickListner nearbyOnClickListner = new AdapterNearby.NearbyOnClickListner() {
                                @Override
                                protected void nearbyOnClickListner(Integer tag, View v) {
                                    switch (v.getId()){
                                        case R.id.imagebutton_tianjia:
                                            if (!arrayList.get(tag).getPhonenumber().equals(SharePreferencesUlits.getString(getApplicationContext(), "phone", Constants.NUll_VALUE))){
                                                linearLayout.setVisibility(View.VISIBLE);
                                                id = arrayList.get(tag).getID();
                                                name = arrayList.get(tag).getNickname();
                                                MyThreadPoolManager.getInstance().execute(runnable1);
                                            }else {
                                               cToast = CToast.makeText(getApplication(), "不可以加自己为好友~", 600);
                                                cToast.show();
                                            }

                                            break;
                                    }
                                }
                            };
                            adapterNearby = new AdapterNearby(imageloderinit.imageLoader,imageloderinit.options,getApplication(),arrayList,nearbyOnClickListner);
                            pullableListView.setAdapter(adapterNearby);
                        }else {
                            linearLayout_delete.setVisibility(View.VISIBLE);
                        }
                    }else {
                        linearLayout.setVisibility(View.VISIBLE);
                    }
                    break;
                case 2:
                    linearLayout.setVisibility(View.GONE);
                    statese = new Statese();
                    statese = msg.getData().getParcelable("state");
                    if (statese != null){
                        if (statese.getState().equals(String.valueOf(Numbers.ONE))){
                            if (!statese.getState().equals("新增成功")){
                                Toast.makeText(getApplication(),"好友邀请已发送",Toast.LENGTH_SHORT).show();
                                SharedPreferences sharedPreferences = getSharedPreferences("data",MODE_PRIVATE);
                                finish();
                            }else {
                                Toast.makeText(getApplication(),statese.getMsg(),Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(getApplication(),statese.getMsg(),Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
            }
        }
    };
    private Runnable runnable1 = new Runnable() {
        @Override
        public void run() {
           statese = new Statese();
            String token = SaveShared.tokenget(getApplication());
            String uid = SaveShared.uid(getApplication());
            String name_ol = SaveShared.name_ol(getApplication());
            statese = HttpTransfeData.addfriendhttp(id,name,name_ol,getApplication(),uid,token);
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putParcelable("state", statese);
            message.what = Numbers.TWO;
            message.setData(bundle);
            handler.sendMessage(message);
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
                pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
            }
        }.sendEmptyMessageDelayed(0,2000);
    }

    @Override
    public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
        new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
            }
        }.sendEmptyMessageDelayed(0,2000);
    }
}
