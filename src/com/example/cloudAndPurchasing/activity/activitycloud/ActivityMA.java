package com.example.cloudAndPurchasing.activity.activitycloud;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.cloudAndPurchasing.BaseFragmentActivity;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.http.Numbers;
import com.example.cloudAndPurchasing.tiltle.TeaskBar;
import com.example.cloudAndPurchasing.zhi.LogUtil;

/**
 * Created by Administrator on 2016/6/23 0023.
 */
public class ActivityMA extends BaseFragmentActivity implements View.OnClickListener {
    private Button button;
    private TextView textView,textView_ol,textView_address,textView_top,textview_count;
    private Handler mhandler;
    private HandlerThread handlerThread;
    private LinearLayout linearLayout,linearLayout_seccsen,linearLayout_content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitymalyout);

        TeaskBar.onSystemoutcolor(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        initData();
        initView();
    }
    public void initData(){
        LogUtil.i("ActivityMa initData start.","");
        linearLayout = (LinearLayout)findViewById(R.id.linearlayout_this_ems);
        linearLayout_content = (LinearLayout)findViewById(R.id.linearlayout_this_cloudcode);
        linearLayout_seccsen = (LinearLayout)findViewById(R.id.linearlayout_this_custcode);
        String content = getIntent().getStringExtra("stateCode");
        if (content != null){
            if (content.equals(String.valueOf(Numbers.ONE))){
                linearLayout_content.setVisibility(View.VISIBLE);
                linearLayout.setVisibility(View.GONE);
                linearLayout_seccsen.setVisibility(View.GONE);
            }else if (content.equals(String.valueOf(Numbers.TWO))){
                linearLayout_content.setVisibility(View.GONE);
                linearLayout.setVisibility(View.VISIBLE);
                linearLayout_seccsen.setVisibility(View.GONE);
            }else {
                linearLayout_seccsen.setVisibility(View.GONE);
                linearLayout_seccsen.setVisibility(View.VISIBLE);
                linearLayout.setVisibility(View.GONE);
            }
        }else {
            linearLayout_seccsen.setVisibility(View.GONE);
            linearLayout_content.setVisibility(View.GONE);
            linearLayout.setVisibility(View.VISIBLE);
        }
        LogUtil.i("ActivityMa initdata end.","");
    }
    public void initView(){
        button = (Button)findViewById(R.id.button_go_back);
        textView_address = (TextView)findViewById(R.id.textview_Consumption_address);
        textView = (TextView)findViewById(R.id.textivew_success);
        textView_ol = (TextView)findViewById(R.id.textview_Consumption);
        textView_top = (TextView)findViewById(R.id.textview_locktop);
        textview_count = (TextView)findViewById(R.id.textivew_success_content);
//        textView.setText("恭喜您,奖品领取成功!");
        textView_top.setText("向商家出示上方消费码即可消费");
        String num = getIntent().getStringExtra("customcode");
        if (num != null){
            if (num != "null"){
                textView_ol.setText("消费码:"+num);
                textview_count.setText("消费码:"+num);
            }
        }else {
            textView_ol.setText("消费码:");
            textview_count.setText("消费码:");
        }
        String address = getIntent().getStringExtra("address");
        if(address != null){
            if (!address.equals("null")){
                textView_address.setText("地址:"+address);
            }else {
                textView_address.setText("地址:");
            }
        }else {
            textView_address.setText("地址:");
        }
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_go_back:
                finish();
                break;
        }
    }
}
