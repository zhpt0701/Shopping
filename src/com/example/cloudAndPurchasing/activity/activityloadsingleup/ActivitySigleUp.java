package com.example.cloudAndPurchasing.activity.activityloadsingleup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.*;
import android.util.Log;
import android.view.KeyEvent;
import com.example.cloudAndPurchasing.BaseFragmentActivity;
import com.example.cloudAndPurchasing.http.Numbers;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;
import com.example.cloudAndPurchasing.MyActivity;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.activity.activitycloud.activitythis.ActivityThisInformation;
import com.example.cloudAndPurchasing.http.*;
import com.example.cloudAndPurchasing.kind.Statese;
import com.example.cloudAndPurchasing.kind.StringUtils;
import com.example.cloudAndPurchasing.manager.MyThreadPoolManager;
import com.example.cloudAndPurchasing.tiltle.TeaskBar;
import com.example.cloudAndPurchasing.zhi.Is;
import com.example.cloudAndPurchasing.zhi.LogUtil;
import com.example.cloudAndPurchasing.zhi.ValuePrice;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by zhangpaengtap on 2016/3/28 0028.
 */
public class ActivitySigleUp extends BaseFragmentActivity implements View.OnClickListener {
    private ImageButton imageButton_qq,imageButton_weixin,imageButton_weibo;
    private Button imageButton_break,button_yanzheng,button_tijiao,button_xieyi;
    private EditText editText_phone,editText_yanzheng,editText_password,edittext_que;
    private CheckBox checkBox;
    private TimeCount time;
    private LinearLayout linearLayout;
    private Statese sd ,statese,statese1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitysigleup);

        TeaskBar.onSystemoutcolor(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        initData();
        initview();
    }

    public void initData() {
        LogUtil.i("ActivitySingleUp initData start.","");

        LogUtil.i("ActivitySingleup initdata end.","");
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
            message.what = Numbers.FOUR;
            message.setData(bundle);
            handler.sendMessage(message);
        }
    };
    /**
     * 实例化控件并设置监听事件
     * @param
     * @param
     */
    public void initview(){
        linearLayout = (LinearLayout)findViewById(R.id.linearlayout_shoppingdateil_loading);
        imageButton_break = (Button)findViewById(R.id.iamgebutton_fanhui);
        imageButton_qq = (ImageButton)findViewById(R.id.imagebutton_qqloading);
        imageButton_weixin = (ImageButton)findViewById(R.id.imagebutton_weixinloading);
        imageButton_weibo = (ImageButton)findViewById(R.id.imagebutton_weiboloading);
        button_yanzheng = (Button)findViewById(R.id.button_yanzheng);
        button_tijiao = (Button)findViewById(R.id.button_submit);
        button_xieyi = (Button)findViewById(R.id.button_xieyi);
        checkBox = (CheckBox)findViewById(R.id.chexkboox);
        editText_phone = (EditText)findViewById(R.id.editext_phone);
        editText_password = (EditText)findViewById(R.id.editext_password_ol);
        edittext_que = (EditText)findViewById(R.id.editext_quepassword_ol);
        editText_yanzheng = (EditText)findViewById(R.id.edittext_yanzhengma);
        imageButton_qq.setOnClickListener(this);
        imageButton_weixin.setOnClickListener(this);
        imageButton_weibo.setOnClickListener(this);
        imageButton_break.setOnClickListener(this);
        button_yanzheng.setOnClickListener(this);
        button_tijiao.setOnClickListener(this);
        button_xieyi.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.iamgebutton_fanhui:
                finish();
                break;
            case R.id.imagebutton_qqloading:
                intent.putExtra("zhi",ValuePrice.VALUE_FIVE);
                intent.setClass(this,ActivityVerity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.imagebutton_weiboloading:

                break;
            case R.id.imagebutton_weixinloading:

                break;
            case R.id.button_yanzheng:
                if (editText_phone.getText().toString().length() == 11){
                    if (Is.IsMoblieNo(editText_phone.getText().toString())){
                        MyThreadPoolManager.getInstance().execute(runnable);
                        time = new TimeCount(60000,1000);
                        time.start();
                    }else {
                        Toast.makeText(this,"您输入的的号码不存在请重新输入",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(this,"您输入的号码格式不正确请输入十一位数字号码",Toast.LENGTH_SHORT).show();
                }


                break;
            case R.id.button_submit:
                if (editText_phone.getText().toString().length() == 11){
                    if (Is.IsMoblieNo(editText_phone.getText().toString())){
                        if (editText_yanzheng.getText().toString() != null){
                            if (editText_password.getText().toString().length()>=6 && editText_password.getText().toString().length()<16){
                                if (editText_password.getText().toString().equals(edittext_que.getText().toString())){
                                    if (checkBox.isChecked()){
                                        try{
                                            linearLayout.setVisibility(View.VISIBLE);
                                            MyThreadPoolManager.getInstance().execute(runnableSingle);
                                        }catch (Exception e){
                                            LogUtil.e("ActivitySingleUp onclick error:",e.toString());
                                        }
                                    }else {
                                        Toast.makeText(getApplication(),"用户协议未选定",Toast.LENGTH_SHORT).show();
                                    }
                                }else {
                                    Toast.makeText(getApplication(),"两次输入的密码不一致",Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(getApplication(),"请输入6-16位数字字母组合密码",Toast.LENGTH_SHORT).show();
                            }

                        }else {
                            Toast.makeText(getApplication(),"请输入验证码",Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(getApplication(),"请输入十一位数字手机号码",Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(getApplication(),"您输入的号码格式不正确请输入十一位数字号码",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.button_xieyi:
                intent.setClass(this,ActivityTalk.class);
                startActivity(intent);
                break;
        }
    }

    /**
     * 数据提交
     */
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            String hp = HttpTransfeData.httpGetauthcode(getApplicationContext(), editText_phone.getText().toString(),"1");
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putString("ma",hp);
            message.what = Numbers.ONE;
            message.setData(bundle);
            handler.sendMessage(message);
        }
    };
    //http注册
    private Runnable runnableSingle = new Runnable() {
        @Override
        public void run() {
            sd = new Statese();
            sd = HttpTransfeData.httppostsingle(getApplicationContext(), editText_yanzheng.getText().toString()
                    , editText_phone.getText().toString()
                    , editText_password.getText().toString());
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putParcelable("con", sd);
            message.setData(bundle);
            message.what = Numbers.TWO;
            handler.sendMessage(message);
        }
    };
    //http登陆
    private Runnable runnableLogin = new Runnable() {
        @Override
        public void run() {
            statese1 = new Statese();
            SaveShared.loading(editText_phone.getText().toString(),editText_password.getText().toString(),getApplication());
            statese1 = HttpTransfeData.httppostloding(getApplication(),editText_phone.getText().toString(),edittext_que.getText().toString());
            Message message = new Message();
            message.what = Numbers.THREE;
            Bundle bundle = new Bundle();
            bundle.putParcelable("com_ol",statese1);
            message.setData(bundle);
            handler.sendMessage(message);
        }
    };
    /**
     *按钮倒计时
     */
    private class TimeCount extends CountDownTimer{
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }
        @Override
        public void onFinish() {//计时完毕时触发
            button_yanzheng.setText("重新验证");
            button_yanzheng.setClickable(true);
        }
        @Override
        public void onTick(long millisUntilFinished){//计时过程显示
            button_yanzheng.setClickable(false);
            button_yanzheng.setText(millisUntilFinished /1000+"秒");
        }
    }

    /**
     * 界面刷新数据传递
     */
    private Handler handler  = new Handler(Looper.getMainLooper()){
        String vb = null;
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    String token = msg.getData().getString("ma");
                    if (token != null){

                    }
//                    Toast.makeText(getApplicationContext(),msg.getData().getString("ma"),Toast.LENGTH_LONG).show();
                    break;
                case 2:
                    linearLayout.setVisibility(View.GONE);
                    statese = new Statese();
                    statese = msg.getData().getParcelable("con");
                    if (statese.getState().equals(String.valueOf(Numbers.ONE))){
                        linearLayout.setVisibility(View.VISIBLE);
                        MyThreadPoolManager.getInstance().execute(runnableLogin);
                    }else {
                        Toast.makeText(getApplication(), statese.getMsg(),Toast.LENGTH_SHORT).show();
                    }

                    break;
                case 3:
                    linearLayout.setVisibility(View.GONE);
                    statese1 = new Statese();
                    statese1 = msg.getData().getParcelable("com_ol");
                    if (statese1.getState().equals(String.valueOf(Numbers.ONE))){
                        linearLayout.setVisibility(View.VISIBLE);
                        MyThreadPoolManager.getInstance().execute(runnableNumber);
                        Toast.makeText(getApplication(),statese1.getMsg()+"正在获取个人信息......",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getApplication(), statese1.getMsg(),Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 4:
                    linearLayout.setVisibility(View.GONE);
                    statese = new Statese();
                    statese = msg.getData().getParcelable("content");
                    if (statese != null){
                        if (statese.getData().equals(String.valueOf(Numbers.ONE))){
                            if (!statese.getData().equals("null")) {
                                SharedPreferences.Editor editor = getApplication().getSharedPreferences("data", Context.MODE_PRIVATE).edit();
                                editor.putString("yue",statese.getData());
                                editor.commit();
                            }
                        }
                    }
                    finish();
                    break;
            }

        }
    };
    //销毁线程
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
