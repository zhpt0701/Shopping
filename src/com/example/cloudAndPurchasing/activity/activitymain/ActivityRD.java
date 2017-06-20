package com.example.cloudAndPurchasing.activity.activitymain;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import com.example.cloudAndPurchasing.BaseFragmentActivity;
import com.example.cloudAndPurchasing.MyActivity;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.tiltle.TeaskBar;
import com.example.cloudAndPurchasing.zhujie.TestPerson;

/**
 * Created by Administrator on 2016/3/23 0023.
 */
public class ActivityRD extends BaseFragmentActivity implements View.OnClickListener {
//    @InjectView(id = R.id.imagebutton_break)
    private Button imageButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityrdlayout);
        TeaskBar.onSystemoutcolor(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        initData();
        initview();
    }
    public void initData(){

    }
    public void initview(){
        imageButton = (Button)findViewById(R.id.imagebutton_break);
        imageButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.imagebutton_break:
                intent.setClass(this, MyActivity.class);
                break;
        }
        startActivity(intent);
        finish();
    }
}
