package com.example.cloudAndPurchasing.activity.activityloadsingleup;

import android.app.Activity;
import android.content.Intent;
import android.os.*;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import com.example.cloudAndPurchasing.BaseFragmentActivity;
import com.example.cloudAndPurchasing.MyActivity;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.http.HttpTransfeData;
import com.example.cloudAndPurchasing.http.Numbers;
import com.example.cloudAndPurchasing.manager.MyThreadPoolManager;
import com.example.cloudAndPurchasing.tiltle.TeaskBar;
import com.example.cloudAndPurchasing.zhi.Is;
import com.example.cloudAndPurchasing.zhi.ValuePrice;

/**
 * Created by Administrator on 2016/3/29 0029.
 */
public class ActivityForget extends BaseFragmentActivity implements View.OnClickListener {
    private Button button,button_ol;
    private EditText editText,editText_ol;
    private Button imageButton;
    private TimeCount timeCount;
    private int num = 0;
    private String nh = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityforgetlayout);
        TeaskBar.onSystemoutcolor(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        initData();
        initview();
    }
    public void initData(){
    }
    /**
     * 获取验证码
     */
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
           String content =  HttpTransfeData.httpGetauthcode(getApplicationContext(), editText.getText().toString(),"2");
            Message message = new Message();
            Bundle bundle = new Bundle();
            message.what = Numbers.ONE;
            bundle.putString("conte",content);
            message.setData(bundle);
            handler.sendMessage(message);
        }
    };
    public void initview(){
        imageButton = (Button)findViewById(R.id.imagebutton_wjfanhui);
        button = (Button)findViewById(R.id.button_wjyanzheng);
        button_ol = (Button)findViewById(R.id.button_next);
        editText = (EditText)findViewById(R.id.editext_wjphone);
        editText_ol = (EditText)findViewById(R.id.edittext_wjyanzhengma);

        imageButton.setOnClickListener(this);
        button.setOnClickListener(this);
        button_ol.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.imagebutton_wjfanhui:
                finish();
                break;
            case R.id.button_wjyanzheng:
                if (editText.getText().toString().length() == 11){
                    if (Is.IsMoblieNo(editText.getText().toString())){
                        num = 1;
                        MyThreadPoolManager.getInstance().execute(runnable);
                        timeCount = new TimeCount(60000,1000);
                        timeCount.start();
                    }else {
                        Toast.makeText(getApplication(),"该号码不存在",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getApplication(),"请输入十一位手机号",Toast.LENGTH_SHORT).show();
                }

                break;
            case  R.id.button_next:
                if (editText.getText().length()==11){
                    if (editText_ol.getText().length() > 0){
                        if (num == 1){
                            if (editText_ol.getText().toString().equals(nh)){
                                intent.putExtra("phones",editText.getText().toString());
                                intent.putExtra("ym",editText_ol.getText().toString());
                                intent.setClass(this,ActivityAlterpass.class);
                                startActivity(intent);
                                finish();
                            }else {
                                Toast.makeText(this,"请输入正确的验证码",Toast.LENGTH_LONG).show();
                            }
                        }else {
                            Toast.makeText(this,"请点击获取验证码",Toast.LENGTH_LONG).show();
                        }
                    }else {
                        Toast.makeText(this,"请输入验证码",Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(this,"请输入手机号码",Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    /**
     * CountDownTimer倒计时控件
     */
    private class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }
        @Override
        public void onFinish() {//计时完毕时触发
            button.setText("重新验证");
            button.setClickable(true);
        }
        @Override
        public void onTick(long millisUntilFinished){//计时过程显示
            button.setClickable(false);
            button.setText(millisUntilFinished /1000+"秒");
        }
    }
    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    String checkcode = msg.getData().getString("conte");
                    if (checkcode != null){
                        nh = checkcode;
                    }
//                    Toast.makeText(getApplicationContext(),msg.getData().getString("conte"),Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
}
