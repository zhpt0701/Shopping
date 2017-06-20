package com.example.cloudAndPurchasing.activity.activityloadsingleup;

import android.app.Activity;
import android.content.Intent;
import android.os.*;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import com.example.cloudAndPurchasing.BaseFragmentActivity;
import com.example.cloudAndPurchasing.MyActivity;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.activity.activityloadsingleup.ActivityForget;
import com.example.cloudAndPurchasing.http.HttpTransfeData;
import com.example.cloudAndPurchasing.http.Numbers;
import com.example.cloudAndPurchasing.kind.Statese;
import com.example.cloudAndPurchasing.manager.MyThreadPoolManager;
import com.example.cloudAndPurchasing.tiltle.TeaskBar;
import com.example.cloudAndPurchasing.zhi.ValuePrice;
import org.apache.http.MethodNotSupportedException;

/**
 * Created by Administrator on 2016/3/29 0029.
 */
public class ActivityAlterpass extends BaseFragmentActivity implements View.OnClickListener {
    private Button imageButton;
    private EditText editText,editText_ol;
    private Button button;
    private Statese statese;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityalterpasslayout);

        TeaskBar.onSystemoutcolor(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        initData();
        initview();
    }
    public void initData(){

    }

    /**
     * 修改密码请求
     */
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
        String ph = getIntent().getStringExtra("phones");
        String y = getIntent().getStringExtra("ym");
        statese = HttpTransfeData.httpalterpassword(getApplicationContext(),editText.getText().toString(),ph,y);
        Message message = new Message();
        Bundle bundle = new Bundle();
        message.what = Numbers.ONE;
        bundle.putParcelable("con",statese);
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
                    statese = new Statese();
                    statese = msg.getData().getParcelable("con");
                    if (statese.getState().equals( String.valueOf(Numbers.ONE))){
                        Toast.makeText(getApplication(),statese.getMsg(),Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        intent.putExtra("zhi", ValuePrice.VALUE_FIVE);
                        intent.setClass(getApplication(), MyActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        Toast.makeText(getApplication(),statese.getMsg(),Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };
    public void initview(){
        imageButton = (Button)findViewById(R.id.imagebutton_czfanhui);
        button = (Button)findViewById(R.id.button_ok);
        editText = (EditText)findViewById(R.id.edittext_czmima);
        editText_ol = (EditText)findViewById(R.id.editext_czpassword);
        imageButton.setOnClickListener(this);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.imagebutton_czfanhui:
                finish();
                break;
            case R.id.button_ok:
                if (editText.getText().length() >=6 && editText.getText().length()<16){
                    if (editText.getText().toString().equals(editText_ol.getText().toString())){
                        MyThreadPoolManager.getInstance().execute(runnable);
                    }else {
                        Toast.makeText(getApplication(),"两次输入密码不相同",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getApplication(),"请输入6-16位数字字母组合密码",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
