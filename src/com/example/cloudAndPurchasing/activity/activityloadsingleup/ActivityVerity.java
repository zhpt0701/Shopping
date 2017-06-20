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
import com.example.cloudAndPurchasing.zhi.LogUtil;
import com.example.cloudAndPurchasing.zhi.ValuePrice;

/**
 * Created by Administrator on 2016/3/29 0029.
 */
public class ActivityVerity extends BaseFragmentActivity implements View.OnClickListener {
    private EditText editTextPhone,editTextCode;
    private Button button,button_ol;
    private Button imageButton;
    private TimeCount timeCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityveritylayout);

        TeaskBar.onSystemoutcolor(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        //初始化数据
        initData();
        //初始化控件
        initview();
    }
    public void initData(){
        LogUtil.i("ActivityVerity initData start.", "");
        try {
            MyThreadPoolManager.getInstance().execute(runnableCode);
        }catch (Exception e){
            LogUtil.e("ActivityVerity initData error:",e.toString());
        }finally {
            LogUtil.i("ActivityVerity initData end.","");
        }
    }
    //http获取验证码
    private Runnable runnableCode = new Runnable() {
        @Override
        public void run() {
            String hp = HttpTransfeData.httpGetauthcode(getApplicationContext(), editTextPhone.getText().toString(), "1");
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putString("ma",hp);
            message.what = Numbers.ONE;
            message.setData(bundle);
            handler.sendMessage(message);
        }
    };
    public void initview(){
        editTextPhone = (EditText)findViewById(R.id.editext_sqphone);
        editTextCode = (EditText)findViewById(R.id.edittext_sqyanzhengma);

        button = (Button)findViewById(R.id.button_sqyanzheng);
        button_ol = (Button)findViewById(R.id.button_sure);
        imageButton = (Button)findViewById(R.id.imagebutton_goback);
        //设置监听事件
        button.setOnClickListener(this);
        button_ol.setOnClickListener(this);
        imageButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch(v.getId()){

            case R.id.button_sqyanzheng://获取验证码
                if (editTextPhone.getText().toString().length() == 11){
                    if (Is.IsMoblieNo(editTextPhone.getText().toString())){
                        MyThreadPoolManager.getInstance().execute(runnableCode);
                        timeCount = new TimeCount(60000,1000);
                        timeCount.start();
                    }else {
                        Toast.makeText(this, "您输入的的号码不存在请重新输入", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(this,"您输入的号码格式不正确请输入十一位数字号码",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.button_sure://验证码提交
                intent.putExtra("zhi",ValuePrice.VALUE_FIVE);
                intent.setClass(this,MyActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.imagebutton_goback://
                intent.setClass(this, ActivitySigleUp.class);
                startActivity(intent);
                finish();
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
            intent.setClass(this, ActivitySigleUp.class);
            startActivity(intent);
            finish();
            return true;
        }else {
            return  super.onKeyDown(keyCode, event);
        }

    }
    /**
     *按钮倒计时
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
                    String token = msg.getData().getString("ma");
                    if (token != null){

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
