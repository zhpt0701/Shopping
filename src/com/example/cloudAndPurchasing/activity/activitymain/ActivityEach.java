package com.example.cloudAndPurchasing.activity.activitymain;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import com.example.cloudAndPurchasing.BaseFragmentActivity;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.adapter.adaptershopping.ContentAdapter;
import com.example.cloudAndPurchasing.sqllite.SqLiteAddDelete;
import com.example.cloudAndPurchasing.tiltle.TeaskBar;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/13 0013.
 */
public class ActivityEach extends BaseFragmentActivity implements View.OnClickListener, TextView.OnEditorActionListener, AdapterView.OnItemClickListener {
    private Button imageButton,button_each;
    private EditText editText;
    private ContentAdapter contentAdapter;
    private ArrayList<String> list;
    private HandlerThread handlerThread;
    private Handler mhandler;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityeachlayout);

        TeaskBar.onSystemoutcolor(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        initData();
        initView();
    }
    public void initData(){
        listView = (ListView)findViewById(R.id.listview_cloudroom_each);
        handlerThread = new HandlerThread("MyHandlerThread");
        handlerThread.start();
        mhandler = new Handler(handlerThread.getLooper());
        list = new ArrayList<String>();
        list = SqLiteAddDelete.FindAllEach(this);
        if (list != null){
            contentAdapter = new ContentAdapter(getApplication(),list);
            listView.setAdapter(contentAdapter);
        }
        listView.setOnItemClickListener(this);
    }
    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    public void initView(){
        button_each = (Button)findViewById(R.id.button_each_content);
        imageButton = (Button)findViewById(R.id.imagebutton_eachback);
        editText = (EditText)findViewById(R.id.edittext_each);
        imageButton.setOnClickListener(this);
        editText.setOnEditorActionListener(this);
        button_each.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.imagebutton_eachback:
                intent.setClass(this,ActivityCreateRoom.class);
                startActivity(intent);
                finish();
                break;
            case R.id.button_each_content:
                String content = editText.getText().toString();
                if (!TextUtils.isEmpty(content)){
                    SqLiteAddDelete.insertEach(this,content);
                    intent.putExtra("each",content);
                    intent.setClass(this,ActivityAddGoods.class);
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
                intent.putExtra("each",content);
                intent.setClass(this,ActivityAddGoods.class);
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
        SqLiteAddDelete.updata_each(getApplication(),list.get(position).toString());
        Intent intent = new Intent();
        intent.putExtra("each",list.get(position).toString());
        intent.setClass(this,ActivityAddGoods.class);
        startActivity(intent);
        finish();
    }
}
