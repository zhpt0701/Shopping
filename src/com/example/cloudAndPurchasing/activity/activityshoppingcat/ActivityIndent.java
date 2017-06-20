package com.example.cloudAndPurchasing.activity.activityshoppingcat;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.cloudAndPurchasing.BaseFragmentActivity;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.baidu.LocationService;
import com.example.cloudAndPurchasing.http.HttpTransfeData;
import com.example.cloudAndPurchasing.http.Numbers;
import com.example.cloudAndPurchasing.http.SaveShared;
import com.example.cloudAndPurchasing.kind.Goods;
import com.example.cloudAndPurchasing.kind.Statese;
import com.example.cloudAndPurchasing.kind.StringUtils;
import com.example.cloudAndPurchasing.manager.MyThreadPoolManager;
import com.example.cloudAndPurchasing.tiltle.TeaskBar;
import com.example.cloudAndPurchasing.times.CToast;
import com.example.cloudAndPurchasing.zhi.LogUtil;
import com.pingplusplus.android.PaymentActivity;
import com.pingplusplus.android.PingppLog;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/7 0007.
 */
public class ActivityIndent extends BaseFragmentActivity implements View.OnClickListener {
    /**
     * 全局变量
     * data_ol
     * locationService 定位服务
     * address 地址
     * list 数据集合
     */
    private TextView textView_number,textView_wx,textView_zfb,textView_money,textView_yu,textView_qita;
    private Button button_yu,button_zhifubao,button_weixinzhifu,button_ensure;
    private Button imageButton;
    private Statese statese;
    private String data_ol;
    private LocationService locationService;
    private String money = "0",number,sum,
    name,shuo,count,
    con,number_ol;
    private LinearLayout linearLayout;
    private ArrayList<Goods> list;
    private String address;
    private SharedPreferences sharedPreferences;
    private CToast cToast;
    private String orderNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityindentlayout);
        //改变状态栏颜色
        TeaskBar.onSystemoutcolor(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        //初始化数据
        initData();
        //初始化控件
        initView();
    }
    public void initData(){
        sharedPreferences = getApplication().getSharedPreferences("data",MODE_PRIVATE);
        con = "Balance";
        LogUtil.i("ActivityIndent initData start","");
        list = new ArrayList<Goods>();
        money = getIntent().getStringExtra("money");
//        money = "1";
        number = getIntent().getStringExtra("number");
        count = getIntent().getStringExtra("count");
        orderNo = getIntent().getStringExtra("orderNo");
        sum = getIntent().getStringExtra("array");
        address = sharedPreferences.getString("addressLocation","");
        Log.i("privence",sharedPreferences.getString("addressLocation","")+"addressindent");

        LogUtil.i("ActivityIndent end.","");
    }
    public void initView(){
        textView_wx = (TextView)findViewById(R.id.textview_weixinzhifu);
        textView_zfb = (TextView)findViewById(R.id.textview_zhifubaosm);
        linearLayout = (LinearLayout)findViewById(R.id.linearlayout_ping);
        imageButton = (Button)findViewById(R.id.imagebutton_dingdan_back);
        textView_number = (TextView)findViewById(R.id.textview_goods_number);
        textView_money = (TextView)findViewById(R.id.textview_money);
        textView_yu = (TextView)findViewById(R.id.textview_yu_e);
        textView_qita = (TextView)findViewById(R.id.textview_Money_Number);
        button_ensure = (Button)findViewById(R.id.button_zhifu);
        button_yu = (Button)findViewById(R.id.button_pay);
        button_weixinzhifu = (Button)findViewById(R.id.button_weixinzhifu);
        button_zhifubao = (Button)findViewById(R.id.button_zhifubao);
        if (!StringUtils.isEmpty(count)){
            textView_number.setText("商品数量:  "+count+" 件");
            textView_money.setText("应付金额:  ￥"+money);
            textView_qita.setText("￥"+money);
        }else {
            textView_number.setText("商品数量:  "+0+" 件");
            textView_money.setText("应付金额:  ￥"+0);
        }

        try{
            sharedPreferences = getSharedPreferences("data",MODE_PRIVATE);
            if (!TextUtils.isEmpty(sharedPreferences.getString("yue", ""))){
                textView_yu.setText("(账户余额:"+ sharedPreferences.getString("yue", "")+"艾购币)");
            }
        }catch (Exception e){
            LogUtil.i("ActivityIndent initData error:",e.toString());
        }
        //设置监听事件
        button_yu.setSelected(true);
        button_zhifubao.setSelected(false);
        button_weixinzhifu.setSelected(false);
        button_yu.setOnClickListener(this);
        button_zhifubao.setOnClickListener(this);
        button_ensure.setOnClickListener(this);
        button_weixinzhifu.setOnClickListener(this);
        imageButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.button_zhifu:
//                Intent intent = new Intent();
//                intent.putExtra("money",money);
//                intent.putExtra("number",orderNo);
//                intent.putExtra("method",con);
//                intent.putExtra("lucknumber",number);
//                intent.setClass(getApplication(), ActivityPay.class);
//                startActivity(intent);
//                finish();
                if (con!=null){
                    try {
                        if (con.equals("Balance")){
                            if (Integer.parseInt(sharedPreferences.getString("yue",""))>=Integer.parseInt(money)){
                                linearLayout.setVisibility(View.VISIBLE);
                                MyThreadPoolManager.getInstance().execute(runnablePayment);
                            }else {
                                cToast = CToast.makeText(getApplication(),"余额不足请选择其他支付方式~",600);
                                cToast.show();
                            }
                        }else {
                            linearLayout.setVisibility(View.VISIBLE);
                            MyThreadPoolManager.getInstance().execute(runnablePayment);
                        }
                    }catch (Exception e){
                        LogUtil.e("ActivityIndent onclick error:",e.toString());
                    }
                }else {
                   cToast = CToast.makeText(getApplication(),"请选择支付方式",600);
                    cToast.show();
                }
                break;
            case R.id.button_zhifubao:
                button_zhifubao.setSelected(true);
                button_weixinzhifu.setSelected(false);
                button_yu.setSelected(false);
                textView_wx.setSelected(false);
                textView_zfb.setSelected(true);
                con = "alipay";
                break;
            case R.id.button_weixinzhifu:
                button_weixinzhifu.setSelected(true);
                button_yu.setSelected(false);
                button_zhifubao.setSelected(false);
                textView_wx.setSelected(true);
                textView_zfb.setSelected(false);
                con = "wx";
                break;
            case R.id.button_pay:
                textView_wx.setSelected(false);
                textView_zfb.setSelected(false);
                button_yu.setSelected(true);
                button_zhifubao.setSelected(false);
                button_weixinzhifu.setSelected(false);
                con = "Balance";
                break;
            case R.id.imagebutton_dingdan_back:
                finish();
                break;
        }
    }
    /**
     * 获取changal
     */
    private Runnable runnablePayment= new Runnable() {
        @Override
        public void run() {
            statese = new Statese();
            name = "艾购梦想";
            String num_ol = getIntent().getStringExtra("number_ol");
            String token = SaveShared.tokenget(getApplication());
            statese = HttpTransfeData.paymenhttp(getApplication(),con,orderNo,money,name,shuo,token,num_ol);
            Message message = new Message();
            Bundle bundle = new Bundle();
            message.what = Numbers.THREE;
            bundle.putParcelable("state1",statese);
            message.setData(bundle);
            handlerUi.sendMessage(message);
        }
    };

    /**
     * activity回调方法
     * @param requestCode
     * @param resultCode
     * @param data
     */
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
                Log.i(getApplication()+"",result+"r82309riew09");
                linearLayout.setVisibility(View.GONE);
                if (result.equals("success")){
                    Toast.makeText(this,"支付成功",Toast.LENGTH_SHORT).show();
                    //intent携带参数跳转
                    Intent intent = new Intent();
                    intent.putExtra("money",money);
                    intent.putExtra("number",orderNo);
                    intent.putExtra("method",con);
                    intent.putExtra("lucknumber",number);
                    intent.setClass(getApplication(), ActivityPay.class);
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

    /**
     * handler机制界面刷新
     */
    private Handler handlerUi = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 3:
                    statese = msg.getData().getParcelable("state1");
                    if (linearLayout != null){
                        linearLayout.setVisibility(View.GONE);
                    }
                    if (statese != null){
                        if (statese.getState().equals(String.valueOf(Numbers.ONE))){
                            if (statese.getMsg().equals("token失效")){
                                try{
                                    MyThreadPoolManager.getInstance().execute(runnableSingle);
                                }catch (Exception e){
                                    LogUtil.e("ActivityIndent handler error:",e.toString());
                                }
                            } else {
                                data_ol =statese.getData();
                                PingppLog.DEBUG = true;
                                Intent intent1 = new Intent();
                                intent1.putExtra(PaymentActivity.EXTRA_CHARGE, data_ol);
                                intent1.setClass(ActivityIndent.this, PaymentActivity.class);
                                startActivityForResult(intent1, Numbers.ONE);
                            }
                        }else {
                            if(statese.getMsg().equals("支付成功")){
                                int c = Integer.parseInt(sharedPreferences.getString("yue", ""));
                                if (c>0){
                                    c-=Integer.parseInt(money);
                                    SharedPreferences.Editor sh = getSharedPreferences("data",MODE_PRIVATE).edit();
                                    sh.putString("yue",String.valueOf(c));
                                    sh.commit();
                                }
                                Toast.makeText(getApplication(),statese.getMsg(),Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent();
                                intent.putExtra("money",money);
                                intent.putExtra("number",orderNo);
                                intent.putExtra("method",con);
                                intent.putExtra("lucknumber", number);
                                intent.setClass(getApplication(), ActivityPay.class);
                                startActivity(intent);
                                finish();
                            }else if (statese.getMsg().equals("订单不存在")){
                                cToast = CToast.makeText(getApplication(),"订单不存在~",800);
                                cToast.show();
                            }else if (statese.getMsg().equals("token失效")){
                                MyThreadPoolManager.getInstance().execute(runnableSingle);
                            }
                        }
                    }
                    break;
                case 4:
                    statese = new Statese();
                    statese = msg.getData().getParcelable("this");
                    if (!statese.equals(null)){
                        if (statese.getState().equals(String.valueOf(Numbers.ONE))){
                            try{
                                MyThreadPoolManager.getInstance().execute(runnablePayment);
                            }catch (Exception e){
                                LogUtil.e("activityIndent handler error:",e.toString());
                            }
                        }
                    }
                    break;
            }
        }
    };
    /**
     * 登陆
     */
    private Runnable runnableSingle = new Runnable() {
        @Override
        public void run() {
            String phone = SaveShared.loadong_name(getApplication());
            String pass = SaveShared.loading_pass(getApplication());
            statese = new Statese();
            statese = HttpTransfeData.httppostloding(getApplication(),
                    phone,pass);
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putParcelable("this", statese);
            message.setData(bundle);
            message.what = Numbers.FOUR;
            handlerUi.sendMessage(message);
        }
    };
    //销毁线程
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
