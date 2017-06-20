package com.example.cloudAndPurchasing.activity.activitycloud.activitynew;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.*;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.cloudAndPurchasing.BaseFragmentActivity;
import com.example.cloudAndPurchasing.MyActivity;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.activity.activitycloud.ChooseActivity;
import com.example.cloudAndPurchasing.activity.activitycloud.activitythis.ActivityAddress;
import com.example.cloudAndPurchasing.http.HttpTransfeData;
import com.example.cloudAndPurchasing.http.Numbers;
import com.example.cloudAndPurchasing.http.SaveShared;
import com.example.cloudAndPurchasing.kind.Statese;
import com.example.cloudAndPurchasing.manager.MyThreadPoolManager;
import com.example.cloudAndPurchasing.tiltle.TeaskBar;
import com.example.cloudAndPurchasing.zhi.ValuePrice;

/**
 * Created by Administrator on 2016/3/30 0030.
 */
public class ActivityNews extends BaseFragmentActivity implements View.OnClickListener {
    private Button imageButton;
    private LinearLayout button,button_ol;
    private Handler mhandler;
    private HandlerThread handlerThread;
    private Statese statese;
    private TextView textView_ol,textView_f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitynewslayout);

        TeaskBar.onSystemoutcolor(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);


    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
        iniiview();
    }

    public void initData(){
        MyThreadPoolManager.getInstance().execute(runnable);
    };
    public void iniiview(){
        textView_f = (TextView)findViewById(R.id.textview_pleasenews);
        textView_ol = (TextView)findViewById(R.id.textview_myfriend);
        button = (LinearLayout)findViewById(R.id.button_cloudroom);
        button_ol = (LinearLayout)findViewById(R.id.button_new_friend);
        imageButton = (Button)findViewById(R.id.imagebutton_newsback);
        button.setOnClickListener(this);
        button_ol.setOnClickListener(this);
        imageButton.setOnClickListener(this);

    }
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            statese = new Statese();
            String token_ol = SaveShared.tokenget(getApplication());
            statese = HttpTransfeData.httpmessageallnumber(getApplication(),token_ol);
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putParcelable("con",statese);
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
                    statese = msg.getData().getParcelable("con");
                    if (statese != null){
                        textView_f.setText("好友邀请:" + statese.getInviteCount());
                        textView_ol.setText("新朋友:"+statese.getAddFriendCount());
                    }
                    break;
            }
        }
    };
    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.button_new_friend:
                intent.setClass(this,ActivityNewFriend.class);
                startActivity(intent);
                break;
            case R.id.button_cloudroom:
                intent.setClass(this,ActivityAsk.class);
                startActivity(intent);
                break;
            case R.id.imagebutton_newsback:
                finish();
                break;
        }
    }
}
