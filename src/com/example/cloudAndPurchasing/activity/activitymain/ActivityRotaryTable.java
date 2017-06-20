package com.example.cloudAndPurchasing.activity.activitymain;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.cloudAndPurchasing.BaseFragmentActivity;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.customcontrol.LuckyPanView;
import com.example.cloudAndPurchasing.tiltle.TeaskBar;

import java.util.Random;

/**
 * Created by Administrator on 2016/3/24 0024.
 */
public class ActivityRotaryTable extends BaseFragmentActivity implements View.OnClickListener {
    private Button imageButton;
    private LuckyPanView mLuckyPanView;
    private ImageView mStartBtn;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityrotarytablelayout);

        TeaskBar.onSystemoutcolor(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        initData();
        initview();
    }
    public void initData(){

    }
    public void initview(){
        imageButton = (Button)findViewById(R.id.imagebutton_home);
        mLuckyPanView = (LuckyPanView) findViewById(R.id.id_luckypan);
        mStartBtn = (ImageView) findViewById(R.id.id_start_btn);
        textView = (TextView)findViewById(R.id.textview_the_winning);
        mStartBtn.setOnClickListener(this);
        imageButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.imagebutton_home:
                /*intent.setClass(this, MyActivity.class);
                startActivity(intent);*/
                finish();
                break;
            case R.id.id_start_btn:
                if (!mLuckyPanView.isStart())
                {
                    int b = 0;
                    int c = 2;
                    int d = 0;
                    Random random=new Random();
                    b = random.nextInt(100);
//                    for (int bc = 0 ; bc <6;bc++ ){
//                        if (b >= c & b < d){
//                            break;
//                        }
//                        c = +d;
//                    }
                    if (b>=0 & b<10){
                        c = 0;
                    }else if (b>=10 & b<20){
                        c= 1;
                    }else if (b>=20 & b<25){
                        c = 2;
                    }else if (b>=25 & b<40){
                        c = 3;
                    }else if (b>=40 & b<95){
                        c = 4;
                    }else if (b>=95 & b<100){
                        c = 5;
                    }
                    mStartBtn.setImageResource(R.drawable.stop_th);
                    mLuckyPanView.luckyStart(c);
                } else
                {
                    if (!mLuckyPanView.isShouldEnd())

                    {
                        mStartBtn.setImageResource(R.drawable.start_ol);
                        mLuckyPanView.luckyEnd();
                    }
                }
                break;
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
            intent.setClass(this,MyActivity.class);
            startActivity(intent);
            finish();
            return true;
        }else {
            return  super.onKeyDown(keyCode, event);
        }

    }*/
}
