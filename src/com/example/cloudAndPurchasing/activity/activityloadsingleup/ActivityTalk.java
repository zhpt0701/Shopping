package com.example.cloudAndPurchasing.activity.activityloadsingleup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import com.example.cloudAndPurchasing.BaseFragmentActivity;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.activity.activityloadsingleup.ActivitySigleUp;
import com.example.cloudAndPurchasing.http.HttpApi;
import com.example.cloudAndPurchasing.tiltle.TeaskBar;

/**
 * Created by Administrator on 2016/3/29 0029.
 */
public class ActivityTalk extends BaseFragmentActivity implements View.OnClickListener {
    private Button imageButton;
    private WebView webView;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitytalklayout);
        //改变状态栏背景是色
        TeaskBar.onSystemoutcolor(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        //初始化数据
        initData();
        //初始化控件
        initview();
    }

    /**
     * 初始化控件
     */
    public void initview(){
        progressBar = (ProgressBar)findViewById(R.id.progressbarthis);
        imageButton = (Button)findViewById(R.id.imagebutton_xyfanhui);
        webView = (WebView)findViewById(R.id.webview_ol);
        imageButton.setOnClickListener(this);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(HttpApi.XIAYIWEBVIEW);
    }

    /**
     * 初始化数据
     */
    public void initData(){

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imagebutton_xyfanhui:
                finish();
                break;
        }
    }
}

