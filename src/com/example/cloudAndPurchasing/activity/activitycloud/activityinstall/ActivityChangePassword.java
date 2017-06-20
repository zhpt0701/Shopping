package com.example.cloudAndPurchasing.activity.activitycloud.activityinstall;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.cloudAndPurchasing.kind.Statese;
import com.example.cloudAndPurchasing.manager.MyThreadPoolManager;
import com.example.cloudAndPurchasing.tiltle.TeaskBar;
import com.example.cloudAndPurchasing.zhi.LogUtil;
import com.example.cloudAndPurchasing.zhi.ValuePrice;

/**
 * Created by Administrator on 2016/4/7 0007.
 */
public class ActivityChangePassword extends BaseFragmentActivity implements View.OnClickListener {
    private EditText editText,editTextPass,editTextNewPass;
    private Button button;
    private Button imageButton;
    private Statese statese;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitychangepasslayout);
        //改变状态栏背景颜色
        TeaskBar.onSystemoutcolor(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        //初始化数据
        initData();
        //初始化控件
        initView();
    }
    public void initData(){
        LogUtil.i("ActivityChangepassword initData start.","");
        sharedPreferences = getSharedPreferences("data",MODE_PRIVATE);
        LogUtil.i("ActivityChangepassword initdata end.","");
    }
    //http修改密码
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            statese = new Statese();
             statese = HttpTransfeData.httpoldpassword(getApplicationContext(),editText.getText().toString(),editTextNewPass.getText().toString(),sharedPreferences.getString("token",""));
            Message message = new Message();
            Bundle bundle = new Bundle();
            message.what = Numbers.ONE;
            bundle.putParcelable("con",statese);
            message.setData(bundle);
            handler.sendMessage(message);
        }
    };
    //初始化控件
    public void initView(){
        editText = (EditText)findViewById(R.id.editext_this_password);
        editTextPass = (EditText)findViewById(R.id.edittext_new_password);
        editTextNewPass = (EditText)findViewById(R.id.editext_ensure);
        button = (Button)findViewById(R.id.button_change);
        imageButton = (Button)findViewById(R.id.imagebutton_change_back);
        //设置监听
        button.setOnClickListener(this);
        imageButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.button_change:
                String newpass = editTextPass.getText().toString();
                final String ensure = editTextNewPass.getText().toString();
                if (newpass.length() >=6){
                    if (newpass.equals(ensure)){
                        try{
                            MyThreadPoolManager.getInstance().execute(runnable);
                        }catch (Exception e){
                            LogUtil.e("ActivityChangePassword onclick error:",e.toString());
                        }
                    }else {
                        Toast.makeText(this, "注意您的新密码输入不一致？？", Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(this, "请输入六位以上数字英文组合密码", Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.imagebutton_change_back:
                finish();
                break;
        }
    }
    //主界面刷新
    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    statese = new Statese();
                    statese = msg.getData().getParcelable("con");
                    if (statese.getState() == String.valueOf(Numbers.ONE)){
                        Toast.makeText(getApplication(), statese.getMsg(),Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        intent.putExtra("zhi", ValuePrice.VALUE_FIVE);
                        intent.setClass(getApplication(),MyActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        Toast.makeText(getApplication(), statese.getMsg(),Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };
}
