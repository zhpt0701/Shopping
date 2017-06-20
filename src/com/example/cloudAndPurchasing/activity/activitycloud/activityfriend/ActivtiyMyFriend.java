package com.example.cloudAndPurchasing.activity.activitycloud.activityfriend;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;
import com.example.cloudAndPurchasing.BaseFragmentActivity;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.tiltle.TeaskBar;

/**
 * Created by Administrator on 2016/3/22 0022.
 */
public class ActivtiyMyFriend extends BaseFragmentActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitymyfrienglayout);
        TeaskBar.onSystemoutcolor(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        initData();
        initview();
    }
    public void initData(){

    }
    public void initview(){

    }
}
