package com.example.cloudAndPurchasing.activity.activitycloud;

import android.app.Activity;
import android.content.Intent;
import android.os.*;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import com.example.cloudAndPurchasing.BaseFragmentActivity;
import com.example.cloudAndPurchasing.MyActivity;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.activity.activitycloud.activitythis.ActivityThisInformation;
import com.example.cloudAndPurchasing.http.HttpTransfeData;
import com.example.cloudAndPurchasing.http.Numbers;
import com.example.cloudAndPurchasing.http.SaveShared;
import com.example.cloudAndPurchasing.kind.Statese;
import com.example.cloudAndPurchasing.manager.MyThreadPoolManager;
import com.example.cloudAndPurchasing.tiltle.TeaskBar;
import com.example.cloudAndPurchasing.zhi.ValuePrice;
import com.pingplusplus.android.PaymentActivity;

/**
 * Created by Administrator on 2016/3/30 0030.
 */
public class ActivityRecharge extends BaseFragmentActivity implements View.OnClickListener {
    private TextView textView,textView_ol,textViewMoney;
    private Button button,button_fifty,button_eight,button_one,button_two,button_five;
    private EditText editText;
    private Button checkBox,checkBox_ol;
    private Button imageButton;
    private LinearLayout linearLayout,linearLayout_ol,linearLayout_ool;
    private Statese statese;
    private String money_ol = "50",fu = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityrecharge);

        TeaskBar.onSystemoutcolor(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        initData();
        initview();
    }
    public void initData(){
        money_ol = "50";
    }

    /**
     * 生成订单
     */
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
           statese = new Statese();
            String toKen = SaveShared.tokenget(getApplication());
            String uid = SaveShared.uid(getApplication());
            String name = "艾购梦想";
            String shuo = "余额充值";
            Log.i("this","fuy7feiosd"+money_ol);
            statese = HttpTransfeData.paymenhttp(getApplication(),fu, "-1",money_ol,name,shuo,toKen,"2");
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putParcelable("content",statese);
            message.what = Numbers.TWO;
            message.setData(bundle);
            handler.sendMessage(message);
        }
    };

    public void initview(){
        linearLayout_ol = (LinearLayout)findViewById(R.id.linearlayout_zhifubao);
        linearLayout_ool = (LinearLayout)findViewById(R.id.linearlayout_weixin);
        linearLayout = (LinearLayout)findViewById(R.id.linearlayout_money);
        textView = (TextView)findViewById(R.id.textview_weixinzhifu);
        textView_ol = (TextView)findViewById(R.id.textview_zhifubaosm);
        checkBox = (Button)findViewById(R.id.chexkbor_zhifubao);
        checkBox_ol = (Button)findViewById(R.id.chexkbor_weixin);
        button = (Button)findViewById(R.id.button_dusubmit);
        button_fifty = (Button)findViewById(R.id.button_fifty);
        button_eight = (Button)findViewById(R.id.button_eighty);
        button_one = (Button)findViewById(R.id.button_one_hundred);
        button_two = (Button)findViewById(R.id.button_two_hundred);
        button_five = (Button)findViewById(R.id.button_five_hundred);
        editText = (EditText)findViewById(R.id.edittext_input);
        editText.setText("");
        imageButton = (Button)findViewById(R.id.imagebutton_duo_goback);
        editText.setOnClickListener(this);
        button.setOnClickListener(this);
        linearLayout_ool.setOnClickListener(this);
        linearLayout_ol.setOnClickListener(this);
        imageButton.setOnClickListener(this);
        button_one.setOnClickListener(this);
        button_two.setOnClickListener(this);
        button_five.setOnClickListener(this);
        button_fifty.setOnClickListener(this);
        button_eight.setOnClickListener(this);
        checkBox.setOnClickListener(this);
        checkBox_ol.setOnClickListener(this);
        button_fifty.setSelected(true);
        button_eight.setSelected(false);
        button_one.setSelected(false);
        button_two.setSelected(false);
        button_five.setSelected(false);
    }
    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.imagebutton_duo_goback:
                finish();
                break;
            case R.id.button_fifty:
                money_ol = "50";
                editText.setText("");
                textView.setTextColor(R.color.tiltle);
                button_fifty.setSelected(true);
                button_eight.setSelected(false);
                button_one.setSelected(false);
                button_two.setSelected(false);
                button_five.setSelected(false);
                break;
            case R.id.button_eighty:
                money_ol = "80";
                editText.setText("");
                button_eight.setSelected(true);
                button_fifty.setSelected(false);
                button_five.setSelected(false);
                button_one.setSelected(false);
                button_two.setSelected(false);
                break;
            case R.id.button_one_hundred:
                money_ol = "100";
                editText.setText("");
                button_one.setSelected(true);
                button_eight.setSelected(false);
                button_fifty.setSelected(false);
                button_five.setSelected(false);
                button_two.setSelected(false);
                break;
            case R.id.button_two_hundred:
                money_ol = "200";
                editText.setText("");
                button_two.setSelected(true);
                button_eight.setSelected(false);
                button_fifty.setSelected(false);
                button_five.setSelected(false);
                button_one.setSelected(false);
                break;
            case R.id.button_five_hundred:
                money_ol = "500";
                editText.setText("");
                button_five.setSelected(true);
                button_one.setSelected(false);
                button_eight.setSelected(false);
                button_fifty.setSelected(false);
                button_two.setSelected(false);
                break;
            case R.id.edittext_input:
                money_ol = "";
                button_five.setSelected(false);
                button_one.setSelected(false);
                button_eight.setSelected(false);
                button_fifty.setSelected(false);
                button_two.setSelected(false);
                break;
            case R.id.button_dusubmit:
                if (fu != null){
                    if (!TextUtils.isEmpty(editText.getText().toString())){
                        money_ol = editText.getText().toString();
                        linearLayout.setVisibility(View.VISIBLE);
                        MyThreadPoolManager.getInstance().execute(runnable);
                    }else if (!TextUtils.isEmpty(money_ol)){
                        linearLayout.setVisibility(View.VISIBLE);
                        MyThreadPoolManager.getInstance().execute(runnable);
                    }else {
                        Toast.makeText(getApplication(),"请选择或输入充值金额",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getApplication(),"请选择支付方式",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.chexkbor_zhifubao:
                fu = "alipay";
                textView.setSelected(false);
                textView_ol.setSelected(true);
                checkBox.setSelected(true);
                checkBox_ol.setSelected(false);
                break;
            case R.id.chexkbor_weixin:
                fu = "wx";
                textView_ol.setSelected(false);
                textView.setSelected(true);
                checkBox_ol.setSelected(true);
                checkBox.setSelected(false);
                break;
            case R.id.linearlayout_zhifubao:
                fu = "alipay";
                textView.setSelected(false);
                textView_ol.setSelected(true);
                checkBox.setSelected(true);
                checkBox_ol.setSelected(false);
                break;
            case R.id.linearlayout_weixin:
                fu = "wx";
                textView_ol.setSelected(false);
                textView.setSelected(true);
                checkBox_ol.setSelected(true);
                checkBox.setSelected(false);
                break;
        }
    }
    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:

                    break;
                case 2:
                    linearLayout.setVisibility(View.GONE);
                    statese = new Statese();
                    statese = msg.getData().getParcelable("content");
                    if (statese != null){
                        if (statese.getState() == "1"){
                            Intent intent1 = new Intent();
                            intent1.putExtra(PaymentActivity.EXTRA_CHARGE, statese.getData());
                            intent1.setClass(ActivityRecharge.this, PaymentActivity.class);
                            startActivityForResult(intent1, Numbers.ONE);
                        }
                    }
                    break;
                case 3:

                    break;
            }
        }
    };
    public boolean onTouchEvent(MotionEvent event) {
        if(null != this.getCurrentFocus()){
            /**
             * 点击空白位置 隐藏软键盘
             */
            InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
        return super .onTouchEvent(event);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //支付页面返回处理
        if (requestCode == Numbers.ONE) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getExtras().getString("pay_result");
            /* 处理返回值
             * "success" - 支付成功
             * "fail"    - 支付失败
             * "cancel"  - 取消支付
             * "invalid" - 支付插件未安装（一般是微信客户端未安装的情况）
             */
                String errorMsg = data.getExtras().getString("error_msg"); // 错误信息
                String extraMsg = data.getExtras().getString("extra_msg"); // 错误信息
                Log.i(getApplication() + "", result + "r82309riew09" + errorMsg);
                linearLayout.setVisibility(View.GONE);
                if (result.equals("success")){
                    Toast.makeText(this,"支付成功",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.putExtra("zhi", ValuePrice.VALUE_FIVE);
                    intent.setClass(getApplication(), MyActivity.class);
                    startActivity(intent);
                    finish();
                }else if (result.equals("fail")){
                    Toast.makeText(this,"支付失败",Toast.LENGTH_SHORT).show();
                }else if (result.equals("cancel")){
                    Toast.makeText(this,"取消支付",Toast.LENGTH_SHORT).show();
                }else if (result.equals("invalid")) {
                    Toast.makeText(this,"支付插件未安装",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
