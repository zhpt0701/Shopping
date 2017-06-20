package com.example.cloudAndPurchasing.activity.activityshopping;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import com.example.cloudAndPurchasing.BaseFragmentActivity;
import com.example.cloudAndPurchasing.MyActivity;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.activity.activitymain.ActivityAddGoods;
import com.example.cloudAndPurchasing.adapter.adaptershopping.ContentAdapter;
import com.example.cloudAndPurchasing.sqllite.SqLiteAddDelete;
import com.example.cloudAndPurchasing.tiltle.TeaskBar;
import com.example.cloudAndPurchasing.zhi.LogUtil;
import com.example.cloudAndPurchasing.zhi.ValuePrice;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/5/19 0019.
 */
public class ActivityShoppingEach extends BaseFragmentActivity implements View.OnClickListener, TextView.OnEditorActionListener, AdapterView.OnItemClickListener {
    private ListView listView;
    private Button imageButton,button;
    private TextView textView;
    private EditText editText;
    private ContentAdapter contentAdapter;
    private ArrayList<String> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityshoppingeachlayout);

        TeaskBar.onSystemoutcolor(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        initData();
        initView();
    }

    private void initData() {
        LogUtil.i("activityShopppingEach initData start.","");
        listView = (ListView)findViewById(R.id.listveiw_eachshopping);
        list = new ArrayList<String>();
        list = SqLiteAddDelete.FindAllEach(this);
        if (list != null){
            contentAdapter = new ContentAdapter(getApplication(),list);
            listView.setAdapter(contentAdapter);
        }
        listView.setOnItemClickListener(this);
        LogUtil.i("activityShopppingEach initData end.","");
    }

    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    private void initView() {
        button = (Button)findViewById(R.id.button_each_contentol);
        editText = (EditText)findViewById(R.id.edittext_shopingeach);
        imageButton = (Button)findViewById(R.id.imagebutton_eachshoppingback);
        textView = (TextView)findViewById(R.id.textview_eachshopping);
        editText.setOnEditorActionListener(this);
        imageButton.setOnClickListener(this);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.imagebutton_eachshoppingback:
                intent.putExtra("zhi", ValuePrice.VALUE_TWO);
                intent.setClass(this, MyActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.button_each_contentol:
                String content = editText.getText().toString();
                if (!TextUtils.isEmpty(content)){
                    //向数据库中存入搜索内容
                    SqLiteAddDelete.insertEach(this,content);
                    String roomid = getIntent().getStringExtra("roomsid");
                    if (roomid!=null){
                        intent.putExtra("roomssid",roomid);
                    }
                    intent.putExtra("each_ol",content);
                    intent.setClass(this,ActivityShoppingdata.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(this, "请输入查找信息!", Toast.LENGTH_SHORT).show();
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
                SqLiteAddDelete.insertEach(this,content);
                Intent intent = new Intent();
                String roomid = getIntent().getStringExtra("roomsid");
                if (roomid!= null){
                    intent.putExtra("roomssid",roomid);
                }
                intent.putExtra("each_ol",content);
                intent.setClass(this,ActivityShoppingdata.class);
                startActivity(intent);
                finish();
            }else {
                Toast.makeText(this, "请输入查找信息!", Toast.LENGTH_SHORT).show();
            }
        }
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        String roomid = getIntent().getStringExtra("roomsid");
        SqLiteAddDelete.updata_each(getApplication(),list.get(position).toString());
        if (roomid!= null){
            intent.putExtra("roomssid",roomid);
        }
        intent.putExtra("each_ol",list.get(position).toString());
        intent.setClass(this,ActivityShoppingdata.class);
        startActivity(intent);
        finish();
    }
}
