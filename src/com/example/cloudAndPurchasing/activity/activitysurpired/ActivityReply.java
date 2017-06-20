package com.example.cloudAndPurchasing.activity.activitysurpired;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import com.example.cloudAndPurchasing.BaseFragmentActivity;
import com.example.cloudAndPurchasing.MyActivity;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.http.HttpTransfeData;
import com.example.cloudAndPurchasing.tiltle.TeaskBar;

/**
 * Created by Administrator on 2016/5/16 0016.
 */
public class ActivityReply extends BaseFragmentActivity implements View.OnClickListener {
    private ImageButton imageButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.commentreplylayout);
        TeaskBar.onSystemoutcolor(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        initData();
        initview();
    }
    public void initData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String con = HttpTransfeData.commentnumberhttp(getApplication(),"1");
            }
        }).start();
    }
    public void initview(){
        imageButton = (ImageButton)findViewById(R.id.imagebutton_reply);

        imageButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.imagebutton_reply:
                intent.setClass(this, MyActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
