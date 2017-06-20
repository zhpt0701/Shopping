package com.example.cloudAndPurchasing.activity.activitycloud;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.*;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import com.example.cloudAndPurchasing.BaseFragmentActivity;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.activity.activitycloud.activitythis.ActivityThisInformation;
import com.example.cloudAndPurchasing.http.HttpTransfeData;
import com.example.cloudAndPurchasing.http.Numbers;
import com.example.cloudAndPurchasing.http.SaveShared;
import com.example.cloudAndPurchasing.kind.Statese;
import com.example.cloudAndPurchasing.manager.MyThreadPoolManager;
import com.example.cloudAndPurchasing.tiltle.TeaskBar;
import com.example.cloudAndPurchasing.times.CToast;
import com.example.cloudAndPurchasing.zhi.LogUtil;
import org.apache.http.conn.scheme.HostNameResolver;

/**
 * Created by Administrator on 2016/4/6 0006.
 */
public class ActivityConvert extends BaseFragmentActivity implements View.OnClickListener {
    private Button button,button_one,button_three,
            button_five,button_ten,button_thirty,button_fifty;
    private Button imageButton;
    private String number = null;
    private HandlerThread handlerThread;
    private Handler mhandler;
    private Statese statese;
    private String token = null;
    private long chimate = 0;
    private SharedPreferences sharedPreferences;
    private CToast cToast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityconvertlayout);
        TeaskBar.onSystemoutcolor(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        initData();
        initView();
    }

    /**
     * 初始化数据
     */
    public void initData(){
        LogUtil.i("ActivityConvert initData start.","");
        sharedPreferences = getSharedPreferences("data",MODE_PRIVATE);
        token = SaveShared.tokenget(this);
        LogUtil.i("ActivityConvert initData end.","");
    }
    /**
     * 初始化控件
     */
    public void initView(){
        imageButton = (Button)findViewById(R.id.imagebutton_convert_back);
        button = (Button)findViewById(R.id.button_convert);
        button_one = (Button)findViewById(R.id.button_immediately_one);
        button_three = (Button)findViewById(R.id.button_immediately_three);
        button_five = (Button)findViewById(R.id.button_immediately_five);
        button_ten = (Button)findViewById(R.id.button_immediately_ten);
        button_thirty = (Button)findViewById(R.id.button_immediately_thirty);
        button_fifty = (Button)findViewById(R.id.button_immediately_fifty);
        number = "1000";
        button_one.setSelected(true);
        button.setOnClickListener(this);
        button_one.setOnClickListener(this);
        button_three.setOnClickListener(this);
        button_five.setOnClickListener(this);
        button_ten.setOnClickListener(this);
        button_thirty.setOnClickListener(this);
        button_fifty.setOnClickListener(this);
        imageButton.setOnClickListener(this);
    }
    /**
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.imagebutton_convert_back:
                finish();
                break;
            case R.id.button_immediately_one:
                button_one.setSelected(true);
                button_three.setSelected(false);
                button_five.setSelected(false);
                button_ten.setSelected(false);
                button_thirty.setSelected(false);
                button_fifty.setSelected(false);
                number = String.valueOf(Integer.parseInt(button_one.getText().toString())*1000);
                break;
            case R.id.button_immediately_three:
                button_one.setSelected(false);
                button_three.setSelected(true);
                button_five.setSelected(false);
                button_ten.setSelected(false);
                button_thirty.setSelected(false);
                button_fifty.setSelected(false);
                number = String.valueOf(Integer.parseInt(button_three.getText().toString())*1000);
                break;
            case R.id.button_immediately_five:
                button_one.setSelected(false);
                button_three.setSelected(false);
                button_five.setSelected(true);
                button_ten.setSelected(false);
                button_thirty.setSelected(false);
                button_fifty.setSelected(false);
                number = String.valueOf(Integer.parseInt(button_five.getText().toString())*1000);
                break;
            case R.id.button_immediately_ten:
                button_one.setSelected(false);
                button_three.setSelected(false);
                button_five.setSelected(false);
                button_ten.setSelected(true);
                button_thirty.setSelected(false);
                button_fifty.setSelected(false);
                number = String.valueOf(Integer.parseInt(button_ten.getText().toString())*1000);
                break;
            case R.id.button_immediately_thirty:
                button_one.setSelected(false);
                button_three.setSelected(false);
                button_five.setSelected(false);
                button_ten.setSelected(false);
                button_thirty.setSelected(true);
                button_fifty.setSelected(false);
                number = String.valueOf(Integer.parseInt(button_thirty.getText().toString())*1000);
                break;
            case R.id.button_immediately_fifty:
                button_one.setSelected(false);
                button_three.setSelected(false);
                button_five.setSelected(false);
                button_ten.setSelected(false);
                button_thirty.setSelected(false);
                button_fifty.setSelected(true);
                number = String.valueOf(Integer.parseInt(button_fifty.getText().toString())*1000);
                break;
            case R.id.button_convert:
                if (number != null){
                    if(number.length()>0){
                        if (!TextUtils.isEmpty(sharedPreferences.getString("integration",""))){
                            if (Integer.parseInt(sharedPreferences.getString("integration",""))>Integer.parseInt(number)){
                                MyThreadPoolManager.getInstance().execute(runnable);
                            }else {
                                cToast = CToast.makeText(this,"积分不足~",600);
                                cToast.show();
                            }
                        }else {
                            cToast = CToast.makeText(this,"积分不足~",600);
                            cToast.show();
                        }
                    }else {
                        cToast = CToast.makeText(this,"请选择金额！！！",600);
                        cToast.show();
                    }
                }else {
                    cToast = CToast.makeText(this,"请选择金额！！！",600);
                    cToast.show();
                }
                break;
        }
    }

    /**
     * 网络请求
     */
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            statese = new Statese();
            statese = HttpTransfeData.creditsexchangehttp(getApplication(),number,token);
            Message message = new Message();
            Bundle bundle = new Bundle();
            message.what = Numbers.ONE;
            bundle.putParcelable("content",statese);
            message.setData(bundle);
            handler.sendMessage(message);
        }
    };

    /**
     * 界面刷新
     */
    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    statese = new Statese();
                    statese = msg.getData().getParcelable("content");
                    if (statese != null){
                        if (statese.getState().equals(String.valueOf(Numbers.ONE))){
                            int num = Integer.parseInt(sharedPreferences.getString("integration",""))-Integer.parseInt(number);
                            SharedPreferences.Editor editor = getSharedPreferences("data",MODE_PRIVATE).edit();
                            editor.putString("integration",String.valueOf(num));
                            editor.putString("yue",String.valueOf(Integer.parseInt(sharedPreferences.getString("yue",""))+Integer.parseInt(number)/1000));
                            editor.commit();
                            finish();
                        }
                        Toast.makeText(getApplication(), statese.getMsg(),Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };
    /**
     * 销毁线程
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
