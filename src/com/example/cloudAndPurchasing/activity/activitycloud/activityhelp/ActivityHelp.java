package com.example.cloudAndPurchasing.activity.activitycloud.activityhelp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.*;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import com.example.cloudAndPurchasing.BaseFragmentActivity;
import com.example.cloudAndPurchasing.MyActivity;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.http.HttpTransfeData;
import com.example.cloudAndPurchasing.http.Numbers;
import com.example.cloudAndPurchasing.http.SaveShared;
import com.example.cloudAndPurchasing.kind.Feedback;
import com.example.cloudAndPurchasing.tiltle.TeaskBar;
import com.example.cloudAndPurchasing.zhi.ValuePrice;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/5 0005.
 */
public class ActivityHelp extends BaseFragmentActivity implements View.OnClickListener {
    private Button imageButton;
    private Button button,button_ol,button_ool;
    private ListView listView;
    private Handler mhamder;
    private int index=1;
    private AdapterHelp adapterHelp;
    private HandlerThread handlerThread;
    private ArrayList<Feedback> arrayList,arrayList1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityhelplayout);
        TeaskBar.onSystemoutcolor(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        initData();
        initView();
    }
    public void initData(){
        arrayList1 = new ArrayList<Feedback>();
        handlerThread = new HandlerThread("MyHandlerTheard");
        handlerThread.start();
        mhamder = new Handler(handlerThread.getLooper());

        mhamder.post(runnable);
    }
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            String uid = SaveShared.uid(getApplication());
            String token = SaveShared.tokenget(getApplication());
            arrayList = HttpTransfeData.feedbackhttp(getApplication(),index,uid,token);
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("content",arrayList);
            message.what = Numbers.ONE;
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
                   arrayList = msg.getData().getParcelableArrayList("content");
                    if (arrayList != null){
                        adapterHelp = new AdapterHelp(getApplication(),arrayList);
                    }
                    break;
            }
        }
    };
    public void initView(){
        imageButton = (Button)findViewById(R.id.imagebutton_helpback);
        button = (Button)findViewById(R.id.button_kefu_ol);
        button_ol = (Button)findViewById(R.id.button_qq);
        button_ool = (Button)findViewById(R.id.button_fankui);
        listView = (ListView)findViewById(R.id.listview_fankuicontent);

        imageButton.setOnClickListener(this);
        button_ool.setOnClickListener(this);
        button.setOnClickListener(this);
        button_ol.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.imagebutton_helpback:
                finish();
                break;
            case R.id.button_kefu_ol:
                Intent intent1 = new Intent(Intent.ACTION_CALL);
                intent1.setData(Uri.parse("tel:400-7823742"));
                startActivity(intent1);
                break;
            case R.id.button_qq:

                break;
            case R.id.button_fankui:
                intent.setClass(this,ActivityIdea.class);
                startActivity(intent);
                break;
        }
    }
    /**
     * 设置返回控制
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            Intent intent = new Intent();
            intent.putExtra("zhi", ValuePrice.VALUE_FIVE);
            intent.setClass(this, MyActivity.class);
            startActivity(intent);
            finish();
            return true;
        }else {
            return  super.onKeyDown(keyCode, event);
        }

    }
}
