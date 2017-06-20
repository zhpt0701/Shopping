package com.example.cloudAndPurchasing.activity.activitycloud.activityfriend;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.*;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;
import com.example.cloudAndPurchasing.BaseFragmentActivity;
import com.example.cloudAndPurchasing.MyActivity;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.activity.activitysurpired.ActivityOldTimey;
import com.example.cloudAndPurchasing.activity.activitysurpired.ActivityRecord;
import com.example.cloudAndPurchasing.adapter.adapterfriend.AdapterMyfriend;
import com.example.cloudAndPurchasing.customcontrol.listviewupdown.PullToRefreshLayout;
import com.example.cloudAndPurchasing.customcontrol.listviewupdown.PullableListView;
import com.example.cloudAndPurchasing.http.*;
import com.example.cloudAndPurchasing.imageloder.Imageloderinit;
import com.example.cloudAndPurchasing.imageloder.Myimageloder;
import com.example.cloudAndPurchasing.kind.Friend;
import com.example.cloudAndPurchasing.manager.MyThreadPoolManager;
import com.example.cloudAndPurchasing.tiltle.TeaskBar;
import com.example.cloudAndPurchasing.zhi.LogUtil;
import com.example.cloudAndPurchasing.zhi.ValuePrice;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import org.apache.http.client.HttpClient;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/3/30 0030.
 */
public class ActivityFriend extends BaseFragmentActivity implements View.OnClickListener,PullToRefreshLayout.OnRefreshListener {
    private Button imageButton;
    private ListView listView;
    private Button button;
    private LinearLayout linearLayout_ol,linearLayout_delete;
    private ArrayList<Friend> arrayList,friends;
    private AdapterMyfriend adapterMyfriend;
    private Myimageloder imageloderinit;
    private int index = 0,size = 0;
    private PullToRefreshLayout pullToRefreshLayout;
    private PullableListView pullableListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityfriendlayout);

        TeaskBar.onSystemoutcolor(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

    }

    @Override
    protected void onResume() {
        super.onResume();
        //初始化数据
        initData();
        //初始化控件
        initview();
    }

    public void initData(){
        friends = new ArrayList<Friend>();
        imageloderinit = new Myimageloder(this);
        index = 1;
        size = 10;
        String bf = getIntent().getStringExtra("back");
        if (bf != null){
            SharedPreferences.Editor editor = getSharedPreferences("data",MODE_PRIVATE).edit();
            editor.putString("bf",bf);
            editor.commit();
        }
        arrayList = new ArrayList<Friend>();
        try {
            MyThreadPoolManager.getInstance().execute(runnable);
        }catch (Exception e){
            LogUtil.e("ActivityFriend initData start.",e.toString());
        }finally {
            LogUtil.i("ActrivityFrieng initData end.","");
        }

    }
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            String token = SaveShared.tokenget(getApplication());
            String uid = SaveShared.uid(getApplication());
            arrayList = HttpTransfeData.httpselectfriend(getApplication(),token,uid,index,size);
            friends.addAll(arrayList);
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("content",friends);
            message.what = Numbers.ONE;
            message.setData(bundle);
            handler1.sendMessage(message);
        }
    };
    public void initview(){
        linearLayout_ol = (LinearLayout)findViewById(R.id.linearlayout_shoppingdateil_loading);
        linearLayout_delete = (LinearLayout)findViewById(R.id.linearlayout_shoppingdateil_delete);
        linearLayout_ol.setVisibility(View.VISIBLE);
        pullToRefreshLayout = (PullToRefreshLayout)findViewById(R.id.pulltorelayout);
        imageButton = (Button)findViewById(R.id.imagebutton_myfriendback);
        pullableListView = (PullableListView)findViewById(R.id.listview_myfriend);
        button = (Button)findViewById(R.id.button_add_friend);
        pullableListView.setOnScrollListener(new PauseOnScrollListener(imageloderinit.imageLoader,true,false));
        button.setOnClickListener(this);
        imageButton.setOnClickListener(this);
        pullableListView.setAdapter(adapterMyfriend);
        pullToRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.imagebutton_myfriendback:
                try {
                    finish();
                }catch (Exception e){
                    LogUtil.e("ActivityFriend onclick",e.toString());
                }
                break;
            case R.id.button_add_friend:
                intent.putExtra("back","1");
                intent.setClass(this,ActivityAddFriend.class);
                startActivity(intent);
            break;
        }
    }
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
        }.sendEmptyMessageDelayed(0,2000);
    }

    @Override
    public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
        new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                index++;
                MyThreadPoolManager.getInstance().execute(runnable);
                pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
            }
        }.sendEmptyMessageDelayed(0,2000);
    }
    private Handler handler1 = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    linearLayout_ol.setVisibility(View.GONE);
                    arrayList = msg.getData().getParcelableArrayList("content");
                    if (arrayList != null){
                        if (arrayList.size()>0){
                            linearLayout_delete.setVisibility(View.GONE);
                            adapterMyfriend = new AdapterMyfriend(imageloderinit.imageLoader,imageloderinit.options,getApplication(),arrayList);
                            pullableListView.setAdapter(adapterMyfriend);
                            pullableListView.setSelected(true);
                            pullableListView.setSelection(adapterMyfriend.getCount()-10);
                            pullableListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Intent intent = new Intent();
                                    String pid = arrayList.get(position).getID();
                                    intent.putExtra("address",arrayList.get(position).getArea());
                                    intent.putExtra("name",arrayList.get(position).getNickname());
                                    intent.putExtra("memuid",arrayList.get(position).getID());
                                    intent.putExtra("level",arrayList.get(position).getLevel());
                                    intent.putExtra("other",String.valueOf(Numbers.ONE));
                                    intent.setClass(getApplication(),ActivityRecord.class);
                                    startActivity(intent);
                                }
                            });
                        }else {
                            linearLayout_delete.setVisibility(View.VISIBLE);
                        }
                    }else {
                        linearLayout_delete.setVisibility(View.VISIBLE);
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
        if (friends != null){
            friends.clear();
        }
        super.onDestroy();
    }
}
