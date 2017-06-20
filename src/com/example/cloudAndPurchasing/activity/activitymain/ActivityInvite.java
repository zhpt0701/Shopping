package com.example.cloudAndPurchasing.activity.activitymain;

import android.content.Intent;
import android.os.*;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import com.example.cloudAndPurchasing.BaseFragmentActivity;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.adapter.adaptermain.AdapterInvite;
import com.example.cloudAndPurchasing.http.HttpTransfeData;
import com.example.cloudAndPurchasing.http.Numbers;
import com.example.cloudAndPurchasing.http.SaveShared;
import com.example.cloudAndPurchasing.imageloder.Myimageloder;
import com.example.cloudAndPurchasing.kind.Friend;
import com.example.cloudAndPurchasing.kind.Statese;
import com.example.cloudAndPurchasing.manager.MyThreadPoolManager;
import com.example.cloudAndPurchasing.tiltle.TeaskBar;
import com.example.cloudAndPurchasing.zhi.LogUtil;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2016/4/13 0013.
 */
public class ActivityInvite extends BaseFragmentActivity implements View.OnClickListener {
    private Button imageButton;
    private Button button;
    private ListView listView;
    private ArrayList<Friend> arrayList;
    private AdapterInvite adapterInvite;
    private Boolean ba = false;
    private HandlerThread handlerThread;
    private Handler mhandler;
    private LinearLayout linearLayout;
    private Myimageloder imageloderinit;
    private Statese statese;
    private HashMap<Integer,Friend> hashMap;
    private int index = 0;
    private int c = 10,add = 0;
    private String content = null;
    private String roomid = null,user = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityinvitelayout);
        //改变状态栏颜色
        TeaskBar.onSystemoutcolor(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        //c初始化数据
        initData();
        //初始化控件
        initView();
    }
    public void initData(){
        LogUtil.i("ActivityInvite initData start.", "");
        index = 1;
        hashMap = new HashMap<Integer, Friend>();
        imageloderinit = new Myimageloder(this);
        handlerThread = new HandlerThread("MyHandlerThread");
        handlerThread.start();
        mhandler = new Handler(handlerThread.getLooper());
        arrayList = new ArrayList<Friend>();
        try {
            MyThreadPoolManager.getInstance().execute(runnableFriendMessage);
        }catch (Exception e){
            LogUtil.e("ActivityInvite initData error:",e.toString());
        }finally {
            LogUtil.i("ActivityInvite initData end.","");
        }
    }
    /**
     * 请求好友信息
     */
    private Runnable runnableFriendMessage = new Runnable() {
        @Override
        public void run() {
            String token = SaveShared.tokenget(getApplication());
            String uid = SaveShared.uid(getApplication());
            arrayList = HttpTransfeData.httpselectfriend(getApplication(),token,uid,index,c);
            Message message = new Message();
            Bundle bundle = new Bundle();
            message.what = Numbers.ONE;
            bundle.putParcelableArrayList("content",arrayList);
            message.setData(bundle);
            handler.sendMessage(message);
        }
    };
    /**
     * 房间邀请好友
     */
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            statese = new Statese();
            String token = SaveShared.tokenget(getApplication());
            String uid = SaveShared.uid(getApplication());
            statese = HttpTransfeData.pleasefriend(getApplication(),token,uid,content);
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putParcelable("state_ol",statese);
            message.setData(bundle);
            message.what = Numbers.TWO;
            handler.sendMessage(message);
        }
    };
    public void initView(){
        linearLayout = (LinearLayout)findViewById(R.id.linearlayout_friend_news);
        imageButton = (Button)findViewById(R.id.imagebutton_inviteback);
        button = (Button)findViewById(R.id.button_inviteover);
        listView = (ListView)findViewById(R.id.listview_invite);
        button.setOnClickListener(this);
        imageButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.button_inviteover:
                linearLayout.setVisibility(View.VISIBLE);
                roomid = getIntent().getStringExtra("roomid");
                user = getIntent().getStringExtra("cuser");
                content = SaveShared.buildfriendJson(getApplication(), hashMap,roomid,user);
                if (add>0){
                    MyThreadPoolManager.getInstance().execute(runnable);
                }else {
                    linearLayout.setVisibility(View.GONE);
                    Toast.makeText(getApplication(),"请选择好友进行邀请",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.imagebutton_inviteback:
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
                    arrayList = msg.getData().getParcelableArrayList("content");
                    if (!arrayList.equals(null)){
                        AdapterInvite.InviteOnCheckedChangeListener inviteOnCheckedChangeListener = new AdapterInvite.InviteOnCheckedChangeListener() {
                            @Override
                            protected void inviteOnCheckedChangeListener(Integer tag, boolean isChecked) {
                                if (isChecked){
                                    add++;
                                    hashMap.put(tag,arrayList.get(tag));
                                    adapterInvite.updateviewcontent(listView,tag,true);
                                }else {
                                    add--;
                                    hashMap.remove(tag);
                                    adapterInvite.updateviewcontent(listView,tag,false);
                                }
                            }
                        };
                        adapterInvite = new AdapterInvite(imageloderinit.imageLoader,imageloderinit.options,getApplication(),arrayList,inviteOnCheckedChangeListener);
                        listView.setAdapter(adapterInvite);
                    }
                    break;
                case 2:
                    linearLayout.setVisibility(View.GONE);
                    statese = msg.getData().getParcelable("state_ol");
                    if (statese.getState().equals(String.valueOf(Numbers.ONE))){
                        if (statese.getMsg().equals("邀请成功")){
                            finish();
                            Toast.makeText(getApplication(),"好友邀请已发送",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getApplication(),statese.getMsg(),Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                    break;
            }
        }
    };
    //销毁线程
    @Override
    protected void onDestroy() {
        if (arrayList != null){
            arrayList.clear();
        }
        super.onDestroy();
    }
}
