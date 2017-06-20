package com.example.cloudAndPurchasing.activity.activitycloud.activityinstall;

import android.app.Activity;
import android.content.Intent;
import android.os.*;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import com.example.cloudAndPurchasing.BaseFragmentActivity;
import com.example.cloudAndPurchasing.MyActivity;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.adapter.adaptercloud.AdapterBulltin;
import com.example.cloudAndPurchasing.http.HttpTransfeData;
import com.example.cloudAndPurchasing.http.Numbers;
import com.example.cloudAndPurchasing.http.SaveShared;
import com.example.cloudAndPurchasing.kind.Bulltin;
import com.example.cloudAndPurchasing.kind.Image;
import com.example.cloudAndPurchasing.manager.MyThreadPoolManager;
import com.example.cloudAndPurchasing.tiltle.TeaskBar;
import com.example.cloudAndPurchasing.zhi.LogUtil;
import com.example.cloudAndPurchasing.zhi.ValuePrice;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/5/20 0020.
 */
public class ActivityNewNotice extends BaseFragmentActivity implements View.OnClickListener {
    private Button imageButton;
    private ListView listView;
    private AdapterBulltin adapterBulltin;
    private ArrayList<Bulltin> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitynewnoticelayout);

        TeaskBar.onSystemoutcolor(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        initData();
        initView();
    }

    private void initData() {
        LogUtil.i("ActivityNewNotice initData start.","");
        arrayList = new ArrayList<Bulltin>();
        try{
            MyThreadPoolManager.getInstance().execute(runnable);
        }catch (Exception e){
            LogUtil.e("ActivityNewNotice initData error:",e.toString());
        }finally {
            LogUtil.i("ActivityNewNotice initData end.","");
        }
    }
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            String token = SaveShared.tokenget(getApplication());
            String uid = SaveShared.uid(getApplicationContext());
            arrayList = HttpTransfeData.bulltinhttp(getApplication(),arrayList,token,uid);
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("bulltin",arrayList);
            message.what = Numbers.ONE;
            message.setData(bundle);
            handler.sendMessage(message);
        }
    };
    private void initView() {
        imageButton = (Button)findViewById(R.id.iamgebutton_setzuixin);
        listView = (ListView)findViewById(R.id.listview_bulletin);
        imageButton.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iamgebutton_setzuixin:
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
                    arrayList = msg.getData().getParcelableArrayList("bulltin");
                    if (arrayList != null){
                        adapterBulltin = new AdapterBulltin(getApplication(),arrayList);
                        listView.setAdapter(adapterBulltin);
                    }
                    break;
            }
        }
    };
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
