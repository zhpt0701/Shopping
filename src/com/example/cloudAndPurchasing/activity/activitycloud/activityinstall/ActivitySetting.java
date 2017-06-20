package com.example.cloudAndPurchasing.activity.activitycloud.activityinstall;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import com.example.cloudAndPurchasing.BaseFragmentActivity;
import com.example.cloudAndPurchasing.MyActivity;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.kind.Image;
import com.example.cloudAndPurchasing.tiltle.TeaskBar;
import com.example.cloudAndPurchasing.zhi.ValuePrice;

/**
 * Created by Administrator on 2016/5/20 0020.
 */
public class ActivitySetting extends BaseFragmentActivity implements View.OnClickListener {
    private Button imageButton;
    private Button button,button1,button2,button_us;
    private String token = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitysettinglayout);

        TeaskBar.onSystemoutcolor(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        initData();
        initview();
    }
    private void initData() {
        SharedPreferences dh = getSharedPreferences("data", MODE_PRIVATE);
        token = dh.getString("token", "");
    }

    private void initview() {
        imageButton = (Button)findViewById(R.id.iamgebutton_setsecurity);
        button = (Button)findViewById(R.id.button_settingan);
        button1 = (Button)findViewById(R.id.button_gonggao);
        button2 = (Button)findViewById(R.id.button_addall);
        button_us = (Button)findViewById(R.id.button_us);
        imageButton.setOnClickListener(this);
        button.setOnClickListener(this);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button_us.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.iamgebutton_setsecurity:
                finish();
                break;
            case R.id.button_settingan:
                if (!token.equals("")){
                    intent.setClass(this,ActivityInstall.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(this,"请登录",Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.button_gonggao:
                intent.setClass(this,ActivityNewNotice.class);
                startActivity(intent);
                break;
            case R.id.button_addall:
                intent.setClass(this,ActivityAddWe.class);
                startActivity(intent);
                break;
            case R.id.button_us:
                intent.setClass(this,ActivityUs.class);
                startActivity(intent);
                break;
        }
    }
}
