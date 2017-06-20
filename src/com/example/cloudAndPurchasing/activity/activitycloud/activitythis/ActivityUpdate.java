package com.example.cloudAndPurchasing.activity.activitycloud.activitythis;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;
import com.example.cloudAndPurchasing.BaseFragmentActivity;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.http.HttpTransfeData;
import com.example.cloudAndPurchasing.http.Numbers;
import com.example.cloudAndPurchasing.kind.Statese;
import com.example.cloudAndPurchasing.kind.StringUtils;
import com.example.cloudAndPurchasing.tiltle.TeaskBar;
import com.example.cloudAndPurchasing.times.CToast;
import com.example.cloudAndPurchasing.zhi.LogUtil;

/**
 * Created by Administrator on 2016/5/20 0020.
 */
public class ActivityUpdate extends BaseFragmentActivity implements View.OnClickListener {
    private EditText editText;
    private ImageButton imageButton;
    private Button button,imageButton_ol;
    private TextView textView;
    private Statese con ,statese;
    private Handler mhandler;
    private HandlerThread handlerThread;
    private CToast cToast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityupdatelayout);

        TeaskBar.onSystemoutcolor(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        initData();
        initView();
    }
    //初始化数据
    private void initData() {
        handlerThread = new HandlerThread("MyHandlerThread");
        handlerThread.start();
        mhandler = new Handler(handlerThread.getLooper());
    }
    //初始化控件
    private void initView() {
        textView = (TextView)findViewById(R.id.textview_updata);
        editText = (EditText)findViewById(R.id.edittext_nickname);
        editText.setText(getIntent().getStringExtra("name"));
        imageButton = (ImageButton)findViewById(R.id.imagebutton_qingkong);
        button = (Button)findViewById(R.id.button_saveall);
        imageButton_ol = (Button)findViewById(R.id.imagebutton_updateback);
        textView.setText(getIntent().getStringExtra("up"));
        //设置监听
        imageButton.setOnClickListener(this);
        button.setOnClickListener(this);
        imageButton_ol.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_saveall:
                if(!StringUtils.isEmpty(editText.getText().toString())){
                    try{
                        mhandler.post(runnable);
                    }catch (Exception e){
                        LogUtil.e("ActivityUpdate onclick error:",e.toString());
                    }
                }else {
                   cToast = CToast.makeText(getApplication(),"请输入昵称~",600);
                    cToast.show();
                }
                break;
            case R.id.imagebutton_qingkong:
                editText.setText(null);
                break;
            case R.id.imagebutton_updateback:
                finish();
                break;
        }
    }
    //http更改昵称
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            con = new Statese();
            con = HttpTransfeData.updatecontent(getApplication(), editText.getText().toString());
            Message message = new Message();
            Bundle bundle = new Bundle();
            message.what = Numbers.ONE;
            bundle.putParcelable("com",con);
            message.setData(bundle);
            handler.sendMessage(message);
        }
    };
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    statese = new Statese();
                    statese = msg.getData().getParcelable("com");
                    if (statese != null){
                        if (statese.getState().equals(String.valueOf(Numbers.ONE))){
                            finish();
                            Toast.makeText(getApplication(),statese.getMsg(),Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getApplication(),statese.getMsg(),Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
            }
        }
    };
}
