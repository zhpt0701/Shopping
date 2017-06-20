package com.example.cloudAndPurchasing.activity.activitycloud.activityinstall;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.cloudAndPurchasing.BaseFragmentActivity;
import com.example.cloudAndPurchasing.MyActivity;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.http.SaveShared;
import com.example.cloudAndPurchasing.kind.Constants;
import com.example.cloudAndPurchasing.kind.SharePreferencesUlits;
import com.example.cloudAndPurchasing.kind.StringUtils;
import com.example.cloudAndPurchasing.tiltle.TeaskBar;
import com.example.cloudAndPurchasing.zhi.ValuePrice;

/**
 * Created by Administrator on 2016/4/6 0006.
 */
public class ActivityInstall extends BaseFragmentActivity implements View.OnClickListener {
    private TextView textView,textView_ol;
    private Button button;
    private Button imageButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityinstalllayout);
        TeaskBar.onSystemoutcolor(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        initData();
        initView();
    }
    public void initData(){

    }
    public void initView(){
        textView = (TextView)findViewById(R.id.textveiw_zhanghao);
        textView_ol = (TextView)findViewById(R.id.textview_yungouID);
        button = (Button)findViewById(R.id.button_anquan);
        imageButton = (Button)findViewById(R.id.iamgebutton_security);
        button.setOnClickListener(this);
        imageButton.setOnClickListener(this);
        SharedPreferences sharedPreferences = getSharedPreferences("data",MODE_PRIVATE);
        textView.setText(sharedPreferences.getString("phone",""));
        if (!StringUtils.isEmpty(SharePreferencesUlits.getString(this, Constants.LOGIN_NAME,Constants.NUll_VALUE))){
            textView_ol.setText(SharePreferencesUlits.getString(this, Constants.LOGIN_NAME,Constants.NUll_VALUE));
        }
    }
    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.button_anquan:
                intent.setClass(this,ActivityChangePassword.class);
                startActivity(intent);
                break;
            case R.id.iamgebutton_security:
                finish();
                break;
        }
    }
}
