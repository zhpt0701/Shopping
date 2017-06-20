package com.example.cloudAndPurchasing.activity.activitysurpired;

import android.content.SharedPreferences;
import android.os.*;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;
import com.example.cloudAndPurchasing.BaseFragmentActivity;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.adapter.adaptersurpired.AdapterCount;
import com.example.cloudAndPurchasing.customcontrol.listviewupdown.PullToRefreshLayout;
import com.example.cloudAndPurchasing.http.HttpTransfeData;
import com.example.cloudAndPurchasing.http.Numbers;
import com.example.cloudAndPurchasing.kind.Count;
import com.example.cloudAndPurchasing.kind.Statese;
import com.example.cloudAndPurchasing.manager.MyThreadPoolManager;
import com.example.cloudAndPurchasing.tiltle.TeaskBar;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/11 0011.
 */
public class ActivityCount extends BaseFragmentActivity implements View.OnClickListener, PullToRefreshLayout.OnRefreshListener {
    private Button imageButton;
    private TextView textView_count,textView_pople,textView_xingyun;
    private CheckBox button;
    private ListView listView;
    private AdapterCount adapterCount;
    private ArrayList<Count> arrayList,counts;
    private LinearLayout linearLayout,linearLayout_ol;
    private boolean flg = false;
    private Handler mhandler ;
    private Statese statese;
    private int index = 1;
    private PullToRefreshLayout pullToRefreshLayout;
    private HandlerThread handlerThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitycountlayout);

        TeaskBar.onSystemoutcolor(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        initData();
        initView();
    }
    public void initData(){

        counts = new ArrayList<Count>();
        handlerThread = new HandlerThread("MyHandlerThread");
        handlerThread.start();
        mhandler = new Handler(handlerThread.getLooper());
        arrayList = new ArrayList<Count>();
        MyThreadPoolManager.getInstance().execute(runnable);

    }
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            statese = new Statese();
            String id = getIntent().getStringExtra("pid");
            statese = HttpTransfeData.sumcounthttp(getApplication(),id,index);
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putParcelable("content_ol", statese);
            message.what = Numbers.ONE;
            message.setData(bundle);
            handler1.sendMessage(message);
        }
    };
    public void initView(){
        listView = (ListView)findViewById(R.id.listview_zhongjiangrenyuan);
        textView_count = (TextView)findViewById(R.id.textview_count_value);
        textView_pople = (TextView)findViewById(R.id.textview_count_poplevlue);
        textView_xingyun = (TextView)findViewById(R.id.textview_count_result);
        pullToRefreshLayout = (PullToRefreshLayout)findViewById(R.id.layout_pulltycontent);
        linearLayout = (LinearLayout)findViewById(R.id.linearlayout_zhankai);
        linearLayout_ol = (LinearLayout)findViewById(R.id.linearlayout_shousuo);
        imageButton = (Button)findViewById(R.id.imagebutton_count_back);
        button = (CheckBox)findViewById(R.id.button_unwindall);
        imageButton.setOnClickListener(this);
        button.setOnClickListener(this);
        button.setText("展开全部");
        pullToRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imagebutton_count_back:
                finish();
                break;
               /* Intent intent = new Intent();
                if (getIntent().getStringExtra("back").equals(String.valueOf(Numbers.ONE))){
                    intent.setClass(this, ActivityShoppingdetail.class);
                }else {
                    intent.setClass(this,ActivityShoppingTimeDetail.class);
                }
                startActivity(intent);
                finish();
                break;*/
            case R.id.button_unwindall:
                if (!button.isChecked()){
                    linearLayout.setVisibility(View.GONE);
                    button.setText("展开全部");
                    flg = true;
                }else {
                    linearLayout.setVisibility(View.VISIBLE);
                    button.setText("收起");
                    flg = false;
                }
                break;
        }
    }
    /**
     * 设置返回控制
     * @param keyCode
     * @param event
     * @return
     *//*
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            Intent intent = new Intent();
            if (getIntent().getStringExtra("back").equals(String.valueOf(Numbers.ONE))){
                intent.setClass(this, ActivityShoppingdetail.class);
            }else {
                intent.setClass(this,ActivityShoppingTimeDetail.class);
            }
            startActivity(intent);
            finish();
            return true;
        }else {
            return  super.onKeyDown(keyCode, event);
        }
    }*/
    private Handler handler1 = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    statese = msg.getData().getParcelable("content_ol");
                    if (statese!=null){
                        if (statese.getState().equals(String.valueOf(Numbers.ONE))){
                            if (!statese.getChargal().equals("0")){
                                SharedPreferences.Editor sh = getSharedPreferences("data",MODE_PRIVATE).edit();
                                sh.putString("count_ol",statese.getChargal());
                                sh.putString("lucknumber_ol",statese.getData());
                                sh.putString("allpople",statese.getIntergl());
                                sh.commit();
                                textView_count.setText("="+statese.getChargal());
                                textView_xingyun.setText(statese.getData());
                                textView_pople.setText(statese.getIntergl());
                            }else {
                                SharedPreferences sharedPreferences = getSharedPreferences("data",MODE_PRIVATE);
                                textView_count.setText("="+sharedPreferences.getString("count_ol",""));
                                textView_xingyun.setText(sharedPreferences.getString("lucknumber_ol",""));
                                textView_pople.setText(sharedPreferences.getString("allpople",""));
                            }
                            if (statese.getArrayList()!=null){
                                counts.addAll(statese.getArrayList());
                                adapterCount = new AdapterCount(getApplication(),counts);
                                listView.setAdapter(adapterCount);
                            }
                        }

                    }
                    break;
            }
        }
    };
    /**
     * 上拉刷新
     * @param pullToRefreshLayout
     */
    @Override
    public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
        new Handler(){
            @Override
            public void handleMessage(Message msg) {
                counts = new ArrayList<Count>();
                index = 1;
                MyThreadPoolManager.getInstance().execute(runnable);
                pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
            }
        }.sendEmptyMessageDelayed(0,2000);
    }

    @Override
    public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
        new Handler(){
            @Override
            public void handleMessage(Message msg) {
                index++;
                MyThreadPoolManager.getInstance().execute(runnable);
                pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
            }
        }.sendEmptyMessageDelayed(0,2000);
    }
}
