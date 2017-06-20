package com.example.cloudAndPurchasing.activity.activitymain;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;
import com.example.cloudAndPurchasing.BaseFragmentActivity;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.http.SaveShared;
import com.example.cloudAndPurchasing.tiltle.TeaskBar;

/**
 * Created by Administrator on 2016/4/13 0013.
 */
public class ActivityCreateRoom extends BaseFragmentActivity implements View.OnClickListener {
    private Button button;
    private Button imageButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitycreateroomlayout);
        TeaskBar.onSystemoutcolor(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        //初始化数据
        initData();
        //初始化控件
        initView();
    }

    public void initData(){
        SharedPreferences.Editor editor_ol = getSharedPreferences("data",MODE_PRIVATE).edit();
        String back_cloud = getIntent().getStringExtra("cloud");
        if (back_cloud != null){
            editor_ol.putString("cloud",back_cloud);
        }
        editor_ol.commit();
    }
    public void initView(){
        button = (Button)findViewById(R.id.button_add_shopping);
        imageButton = (Button)findViewById(R.id.imagebutton_createroom_back);

        button.setOnClickListener(this);
        imageButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.button_add_shopping:
                String token = SaveShared.tokenget(this);
                if (token != null){
                    if (!token.equals("")){
                        intent.setClass(this,ActivityEach.class);
                        startActivity(intent);
                        finish();
                    }else {
                        Toast.makeText(getApplication(),"请登录",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getApplication(),"请登录",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.imagebutton_createroom_back:
                finish();
                /*SharedPreferences sharedPreferences = getSharedPreferences("data",MODE_PRIVATE);
                if (sharedPreferences.getString("cloud","").equals(String.valueOf(Numbers.TWO))){
                    intent.putExtra("zhi", ValuePrice.VALUE_THREE);
                    intent.setClass(this,MyActivity.class);
                }else {
                    intent.setClass(this, ActivityCloudRoom.class);
                }
                startActivity(intent);
                finish();
                break;  */
        }
    }
    /**
     * 设置返回控制
     * @param keyCode
     * @param event
     * @return
     *//*
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            Intent intent = new Intent();
            SharedPreferences sharedPreferences = getSharedPreferences("data",MODE_PRIVATE);
            if (sharedPreferences.getString("cloud","").equals(String.valueOf(Numbers.TWO))){
                intent.putExtra("zhi", ValuePrice.VALUE_THREE);
                intent.setClass(this,MyActivity.class);
            }else {
                intent.setClass(this, ActivityCloudRoom.class);
            }
            startActivity(intent);
            finish();
            return true;
        }else {
            return  super.onKeyDown(keyCode, event);
        }

    }*/
}
