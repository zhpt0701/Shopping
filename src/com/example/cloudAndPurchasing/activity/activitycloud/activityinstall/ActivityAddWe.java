package com.example.cloudAndPurchasing.activity.activitycloud.activityinstall;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import com.example.cloudAndPurchasing.BaseFragmentActivity;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.tiltle.TeaskBar;

/**
 * Created by Administrator on 2016/5/20 0020.
 */
public class ActivityAddWe extends BaseFragmentActivity implements View.OnClickListener {
    private Button imageButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityaddwelayout);

        TeaskBar.onSystemoutcolor(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        initData();
        initView();
    }

    private void initData() {

    }

    private void initView() {
        imageButton = (Button)findViewById(R.id.iamgebutton_setaddwe);
        imageButton.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iamgebutton_setaddwe:
                finish();
                break;
        }
    }
}
