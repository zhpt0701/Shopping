package com.example.cloudAndPurchasing.activity.activitycloud.activityfriend;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.cloudAndPurchasing.BaseFragmentActivity;
import com.example.cloudAndPurchasing.MyActivity;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.http.Numbers;
import com.example.cloudAndPurchasing.kind.Constants;
import com.example.cloudAndPurchasing.kind.SharePreferencesUlits;
import com.example.cloudAndPurchasing.tiltle.TeaskBar;
import com.example.cloudAndPurchasing.zhi.LogUtil;

/**
 * Created by Administrator on 2016/4/8 0008.
 */
public class ActivityAddFriend extends BaseFragmentActivity implements View.OnClickListener, TextView.OnEditorActionListener {
    private EditText editText;
    private Button button;
    private Button imageButton;
    private Button button_ol;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityaddfriendlayout);

        TeaskBar.onSystemoutcolor(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        //初始化数据
        initData();
        //初始化控件
        initView();
    }
    public void initData(){
        LogUtil.i("ActivityAddFriend initData start.","");
        String bk = getIntent().getStringExtra("back");
        if (bk!= null){
            SharedPreferences.Editor sh = getSharedPreferences("data",MODE_PRIVATE).edit();
            sh.putString("bk",bk);
            sh.commit();
        }
        LogUtil.i("ActivityAddFriend initData end.","");
    }
    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    public void initView(){
        button_ol = (Button)findViewById(R.id.button_firend_ol);
        button = (Button)findViewById(R.id.button_findall);
        imageButton = (Button)findViewById(R.id.imagebutton_yaoqing_back);
        editText = (EditText)findViewById(R.id.edittext_sousuo);
        //设置监听
        button.setOnClickListener(this);
        imageButton.setOnClickListener(this);
        editText.setOnEditorActionListener(this);
        button_ol.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            /*case R.id.button_findall:
                intent.setClass(this,ActivitySelectNearby.class);
                startActivity(intent);
                finish();
                break;*/
            case R.id.imagebutton_yaoqing_back:
                finish();
                break;
            case R.id.button_firend_ol:
                String content = editText.getText().toString();
                if (!TextUtils.isEmpty(content)){
                    if (content.length()==11){
                        intent.putExtra("each",content);
                        intent.setClass(this,ActivitySelectNearby.class);
                        startActivity(intent);
                        finish();
                    }else {
                        Toast.makeText(this,"请输11位手机号码!",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(this,"请输入查找的号码!",Toast.LENGTH_SHORT).show();
                }
            break;
        }
    }

    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH){
            ((InputMethodManager)editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
            String content = editText.getText().toString();
            if (!TextUtils.isEmpty(content)){
                if (content.length()==11){
                    Intent intent = new Intent();
                    intent.putExtra("each",content);
                    intent.setClass(this,ActivitySelectNearby.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(this,"请输11位手机号码!",Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(this,"请输入查找的号码!",Toast.LENGTH_SHORT).show();
            }
        }
        return false;
    }
}
