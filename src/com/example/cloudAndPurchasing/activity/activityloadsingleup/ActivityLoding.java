package com.example.cloudAndPurchasing.activity.activityloadsingleup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.*;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;
import com.example.cloudAndPurchasing.BaseFragmentActivity;
import com.example.cloudAndPurchasing.MyActivity;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.activity.activityloadsingleup.ActivityForget;
import com.example.cloudAndPurchasing.activity.activityloadsingleup.ActivitySigleUp;
import com.example.cloudAndPurchasing.http.HttpTransfeData;
import com.example.cloudAndPurchasing.http.Numbers;
import com.example.cloudAndPurchasing.http.SaveShared;
import com.example.cloudAndPurchasing.kind.*;
import com.example.cloudAndPurchasing.manager.MyThreadPoolManager;
import com.example.cloudAndPurchasing.tiltle.TeaskBar;
import com.example.cloudAndPurchasing.times.CToast;
import com.example.cloudAndPurchasing.zhi.ValuePrice;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/28 0028.
 */
public class ActivityLoding extends BaseFragmentActivity implements View.OnClickListener {
    private EditText editText_name,editText_password;
    private Button imagebutton_Fh,button_wangjimima,button_zhuce,button_denglu;
    private ImageButton imagebutton_qq,imagebutton_weixin,imagebutton_weibo;
    private int ONE = 1;
    private Statese statese,statese1;
    private LinearLayout linearLayout_ol;
    private CToast cToast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitylodinglayout);
        TeaskBar.onSystemoutcolor(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        initData();
        initview();
    }
    public void initData(){
        if (getIntent().getStringExtra("con_ol") != null){
            Toast.makeText(this,getIntent().getStringExtra("con_ol"),Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 登陆
     */
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            statese = new Statese();
            statese = HttpTransfeData.httppostloding(getApplicationContext(),
                    editText_name.getText().toString(),editText_password.getText().toString());
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putParcelable("this", statese);
            message.setData(bundle);
            message.what = ONE;
            handler.sendMessage(message);
        }
    };

    /**
     * 实例化控件并设置（onclick）监听事件
     */
    public void initview(){
        linearLayout_ol = (LinearLayout)findViewById(R.id.linearlayout_loading_progressbar);
        editText_name = (EditText)findViewById(R.id.edittext_name);
        editText_password = (EditText)findViewById(R.id.edittext_loadingpassword);
        button_wangjimima = (Button)findViewById(R.id.button_wangjimina);
        button_zhuce = (Button)findViewById(R.id.button_singleup);
        button_denglu = (Button)findViewById(R.id.button_denglu);
        imagebutton_Fh = (Button)findViewById(R.id.imagebutton_fanhui);
        imagebutton_qq = (ImageButton)findViewById(R.id.imagebutton_qqdenglu);
        imagebutton_weibo = (ImageButton)findViewById(R.id.imagebutton_weibodenglu);
        imagebutton_weixin = (ImageButton)findViewById(R.id.imagebutton_weixindenglu);
        //设置监听
        button_wangjimima.setOnClickListener(this);
        button_zhuce.setOnClickListener(this);
        button_denglu.setOnClickListener(this);
        imagebutton_Fh.setOnClickListener(this);
        imagebutton_qq.setOnClickListener(this);
        imagebutton_weixin.setOnClickListener(this);
        imagebutton_weibo.setOnClickListener(this);
    }

    /**
     * onclick响应方法
     * @param v
     */
    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch(v.getId()){
            case R.id.button_wangjimina:
                intent.setClass(this,ActivityForget.class);
                startActivity(intent);
                break;
            case R.id.button_singleup:
                intent.setClass(this, ActivitySigleUp.class);
                startActivity(intent);
                break;
            case R.id.button_denglu:
                if (editText_name.getText().length() > 0){
                    if (editText_password.getText().length() > 0){
                        SaveShared.loading(editText_name.getText().toString(),editText_password.getText().toString(),getApplication());
                        linearLayout_ol.setVisibility(View.VISIBLE);
                        MyThreadPoolManager.getInstance().execute(runnable);
                    }else {
                        cToast = CToast.makeText(getApplication(),"输入登录密码",1000);
                        cToast.show();
                    }
                }else {
                    cToast = CToast.makeText(getApplication(),"请输入账号信息",1000);
                    cToast.show();
                }

                break;
            case R.id.imagebutton_fanhui:
                finish();
                break;
            case R.id.imagebutton_qqdenglu:

                break;
            case R.id.imagebutton_weixindenglu:

                break;
            case R.id.imagebutton_weibodenglu:

                break;
        }
    }
    /**
     * http获取艾购币数量
     */
    private Runnable runnableNumber = new Runnable() {
        @Override
        public void run() {
            statese = new Statese();
            String token = SaveShared.tokenget(getApplication());
            statese = HttpTransfeData.sumhttp(getApplication(),token);
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putParcelable("content",statese);
            message.what = Numbers.TWO;
            message.setData(bundle);
            handler.sendMessage(message);
        }
    };
    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == ONE){
                linearLayout_ol.setVisibility(View.GONE);
                statese1 = new Statese();
                statese1 = msg.getData().getParcelable("this");
                if (statese1 != null){
                    if (statese1.getState().equals(String.valueOf(Numbers.ONE))){
                        MyThreadPoolManager.getInstance().execute(runnableNumber);
                    }
                    cToast = CToast.makeText(getApplication(),statese1.getMsg(),1000);
                    cToast.show();
                }
            }else if (msg.what == Numbers.TWO){
                statese = new Statese();
                statese = msg.getData().getParcelable("content");
                if (statese != null){
                    if (statese.getState().equals(String.valueOf(Numbers.ONE))){
                        if (!statese.getData().equals("null")) {
                            SharedPreferences.Editor editor = getApplication().getSharedPreferences("data", Context.MODE_PRIVATE).edit();
                            editor.putString("yue",statese.getData());
                            editor.commit();
                        }else {
                            SharedPreferences.Editor editor = getApplication().getSharedPreferences("data", Context.MODE_PRIVATE).edit();
                            editor.putString("yue","0");
                            editor.commit();
                        }
                    }

                }
                finish();
            }

        }
    };
}
