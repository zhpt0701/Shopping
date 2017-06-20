package com.example.cloudAndPurchasing.activity.activitycloud;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.*;
import android.support.annotation.BoolRes;
import android.support.v7.widget.*;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.example.cloudAndPurchasing.BaseFragmentActivity;
import com.example.cloudAndPurchasing.MyActivity;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.activity.activitycloud.activitythis.ActivityThisInformation;
import com.example.cloudAndPurchasing.adapter.adaptercloud.AdapterSgin;
import com.example.cloudAndPurchasing.baidu.Utils;
import com.example.cloudAndPurchasing.http.HttpMothed;
import com.example.cloudAndPurchasing.http.HttpTransfeData;
import com.example.cloudAndPurchasing.http.Numbers;
import com.example.cloudAndPurchasing.http.SaveShared;
import com.example.cloudAndPurchasing.kind.*;
import com.example.cloudAndPurchasing.manager.MyThreadPoolManager;
import com.example.cloudAndPurchasing.sqllite.EachTable;
import com.example.cloudAndPurchasing.tiltle.TeaskBar;

import android.text.format.Time;
import com.example.cloudAndPurchasing.times.CToast;
import com.example.cloudAndPurchasing.zhi.LogUtil;
import okhttp3.*;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/3/30 0030.
 */
public class ActivityIntegral extends BaseFragmentActivity implements View.OnClickListener {
    private TextView textView,textview_qiandao;
    private Button button,button_ol;
    private Button imageButton;
    private RecyclerView recyclerView;
    private ImageView imageView;
    private AdapterSgin adapterSgin;
    private ArrayList<Day> arrayList;
    private LinearLayoutManager manager;
    private String times;
    private Time time;
    private Statese statese;
    private String isgin = null;
    private SharedPreferences sharedPreferences;
    private CToast cToast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityintegral);
        //改变状态栏颜色

        TeaskBar.onSystemoutcolor(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

    }

    @Override
    protected void onResume() {
        super.onResume();
        //初始化数据
        initData();
        //初始化控件
        initview();
    }
    public void initData(){
        LogUtil.i("ActivityIntegral initdata start.","");
        sharedPreferences = getSharedPreferences("data",MODE_PRIVATE);
        time = new Time();
        time.setToNow();
        arrayList = new ArrayList<Day>();
        manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        int b = 1;
        for (int c = 0 ; c<4;c++){
            Day day = new Day();
            day.setDay(String.valueOf(b));
            if (b == 1){
                day.setJifan("50");
            }else if (b == 2){
                day.setJifan("80");
            }else {
                day.setJifan("100");
            }
            b++;
            arrayList.add(day);
        }
        adapterSgin = new AdapterSgin(this,arrayList);
        times = time.year+"年"+time.month+"月"+time.monthDay+"日";
        isgin = "3";
        try {
            MyThreadPoolManager.getInstance().execute(runnable);
        }catch (Exception e){
            LogUtil.e("ActivityIntegral runable",e.toString());
        }finally {
            LogUtil.i("ActivityIntegral initdata end.","");
        }
    }
    public void initview(){
        textview_qiandao = (TextView)findViewById(R.id.textview_qiandao);
        textView = (TextView)findViewById(R.id.textview_jifen);
        imageButton = (Button)findViewById(R.id.imagebutton_getback);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerview_qiandao);
        imageView = (ImageView)findViewById(R.id.imageview_qiandao);
        imageView.setSelected(true);
        button = (Button)findViewById(R.id.button_cz);
        button_ol = (Button)findViewById(R.id.button_qiandao);
        sharedPreferences = getSharedPreferences("data",MODE_PRIVATE);
        try {
            if (!StringUtils.isEmpty(SharePreferencesUlits.getString(getApplication(), Constants.INTEGRATION, Constants.NUll_VALUE))){
                textView.setText(SharePreferencesUlits.getString(getApplication(),Constants.INTEGRATION,Constants.NUll_VALUE));
            }else {
                textView.setText("0");
            }
            if (sharedPreferences.getString("integral","").equals("今天未签到")){
                button_ol.setText("签到");
                button_ol.setSelected(false);
            }else if (sharedPreferences.getString("integral","").equals("")){
                button_ol.setText("签到");
                button_ol.setSelected(false);
            }else {
                button_ol.setText("已签到");
                button_ol.setSelected(true);
            }
        }catch (Exception e){
            LogUtil.e("ActivityIntegral initview 131：",e.toString());
        }
        textview_qiandao.setText("50");
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapterSgin);
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        button.setOnClickListener(this);
        button_ol.setOnClickListener(this);
        imageButton.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.imagebutton_getback:
                finish();
                break;
            case R.id.button_cz:
                try {
                    intent.setClass(this,ActivityConvert.class);
                    startActivity(intent);
                }catch (Exception e){
                    LogUtil.e("ActivityIntegral button_cz",e.toString());
                }
                break;
            case R.id.button_qiandao:
                if (button_ol.getText().equals("已签到")){
                    cToast = CToast.makeText(getApplication(),"今日已签到",600);
                    cToast.show();
                }else {
                    isgin = "1";
                    MyThreadPoolManager.getInstance().execute(runnable);
                    button_ol.setText("已签到");
                    button_ol.setSelected(true);
                }
                break;
        }
    }
    /**
     * 签到请求
     */
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            statese = new Statese();
            String token = SaveShared.tokenget(getApplication());
            statese = HttpTransfeData.singininhttp(getApplication(),isgin,token);
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putParcelable("content",statese);
            message.what = Numbers.ONE;
            message.setData(bundle);
            handler.sendMessage(message);
        }
    };
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    statese = new Statese();
                    statese = msg.getData().getParcelable("content");
                    if (statese != null){
                        if (statese.getState().equals(String.valueOf(Numbers.ONE))){
                            if (statese.getMsgcode().equals("1")){
                                button_ol.setText("已签到");
                                button_ol.setSelected(true);
                            }else if (statese.getMsgcode().equals("2")){
                                int integral = Integer.parseInt(textView.getText().toString());
                                integral+=50;
                                textView.setText(String.valueOf(integral));
                                SharedPreferences.Editor editor = getSharedPreferences("data",MODE_PRIVATE).edit();
                                editor.putString("integration",String.valueOf(integral));
                                editor.commit();
                                button_ol.setText("已签到");
                                button_ol.setSelected(true);
                                cToast = CToast.makeText(getApplication(),statese.getMsg(),600);
                                cToast.show();
                            }else {
                                SharedPreferences.Editor editor = getSharedPreferences("data",MODE_PRIVATE).edit();
                                if (statese.getMsg().equals("今天未签到")){
                                    editor.putString("integral",statese.getMsg());
                                    button_ol.setText("签到");
                                    button_ol.setSelected(false);
                                }else {
                                    editor.putString("integral",statese.getMsg());
                                    button_ol.setText("已签到");
                                    button_ol.setSelected(true);
                                }
                                editor.commit();
                            }
                        }
                    }
                    break;
            }
        }
    };
}
