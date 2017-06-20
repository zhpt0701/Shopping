package com.example.cloudAndPurchasing.activity.activitysurpired;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.*;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.example.cloudAndPurchasing.BaseFragmentActivity;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.activity.ActivityShoppingdetail;
import com.example.cloudAndPurchasing.adapter.adaptersurpired.AdapterOldTimey;
import com.example.cloudAndPurchasing.customcontrol.listviewupdown.PullToRefreshLayout;
import com.example.cloudAndPurchasing.http.HttpTransfeData;
import com.example.cloudAndPurchasing.http.Numbers;
import com.example.cloudAndPurchasing.imageloder.Myimageloder;
import com.example.cloudAndPurchasing.kind.Constants;
import com.example.cloudAndPurchasing.kind.Record;
import com.example.cloudAndPurchasing.kind.SharePreferencesUlits;
import com.example.cloudAndPurchasing.manager.MyThreadPoolManager;
import com.example.cloudAndPurchasing.popupwindows.SharePopupwindows;
import com.example.cloudAndPurchasing.tiltle.TeaskBar;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/12 0012.
 */
public class ActivityOldTimey extends BaseFragmentActivity implements View.OnClickListener, PullToRefreshLayout.OnRefreshListener ,AdapterView.OnItemClickListener {
    private Button imageButton,imageButton_ol;
    private ListView listView;
    private AdapterOldTimey adapterOldTimey;
    private ArrayList<Record> arrayList,arrayList1;
    private HandlerThread handlerThread;
    private Handler mhandler;
    private PullToRefreshLayout pullToRefreshLayout;
    private Myimageloder myimageloder;
    private int index = 1;
    private String id_l = null;
    private SharedPreferences sharedPreferences;
    private LinearLayout linearLayout,linearLayout_ol;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityoldtimeylayout);

        TeaskBar.onSystemoutcolor(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        initData();
        initview();
    }
    public void initData(){
        String id = getIntent().getStringExtra("prid");
        String sum = getIntent().getStringExtra("num");
        SharedPreferences.Editor editor = getSharedPreferences("data",MODE_PRIVATE).edit();
        if (id!=null){
            editor.putString("old_id",id);
        }
        if (sum!=null){
            editor.putString("sum_ol",sum);
        }
        editor.commit();
        arrayList1 = new ArrayList<Record>();
        myimageloder = new Myimageloder(this);
        handlerThread = new HandlerThread("MyHandlerThread");
        handlerThread.start();
        mhandler = new Handler(handlerThread.getLooper());
        arrayList = new ArrayList<Record>();
        sharedPreferences = getSharedPreferences("data",MODE_PRIVATE);
        id_l = sharedPreferences.getString("old_id","");
        if (!TextUtils.isEmpty(id_l)){
            MyThreadPoolManager.getInstance().execute(runnable);
        }else {
            linearLayout_ol.setVisibility(View.GONE);
            linearLayout.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 请求网络数据
     */
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            arrayList = new ArrayList<Record>();
            arrayList = HttpTransfeData.wangqihttp(getApplication(),id_l,index);
            arrayList1.addAll(arrayList);
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("con_ol", arrayList1);
            message.setData(bundle);
            message.what= Numbers.ONE;
            handler.sendMessage(message);
        }
    };
    public void initview(){
        linearLayout = (LinearLayout)findViewById(R.id.linearlayout_shoppingdateil_loading);
        linearLayout_ol = (LinearLayout)findViewById(R.id.linearlayout_shoppingdateil_delete);
        linearLayout.setVisibility(View.VISIBLE);
        pullToRefreshLayout = (PullToRefreshLayout)findViewById(R.id.pull_groupbuy);
        listView = (ListView)findViewById(R.id.listview_oldtimey);
        imageButton = (Button)findViewById(R.id.imagebutton_oldtimey_back);
        imageButton_ol = (Button)findViewById(R.id.imagebutton_oldtimey_fenxiang);
        listView.setOnScrollListener(new PauseOnScrollListener(myimageloder.imageLoader,true,false));
        imageButton.setOnClickListener(this);
        imageButton_ol.setOnClickListener(this);
        pullToRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onClick(View v) {
       //Intent intent = new Intent();
        switch (v.getId()){
            case R.id.imagebutton_oldtimey_back:
                /*String ul = sharedPreferences.getString("sum_ol","");
                if (ul.equals(String.valueOf(Numbers.ONE))){
                    intent.setClass(this, ActivityGoodsDateil.class);
                }else if (ul.equals(String.valueOf(Numbers.TWO))){
                    intent.putExtra("back",String.valueOf(Numbers.ZERO));
                    intent.setClass(this,ActivityShoppingTimeDetail.class);
                }else{
                    intent.putExtra("back",String.valueOf(Numbers.ZERO));
                    intent.setClass(this,ActivityShoppingdetail.class);
                }
                startActivity(intent);*/
                finish();
                break;
            case R.id.imagebutton_oldtimey_fenxiang:
                SharePopupwindows sharePopupwindows = new SharePopupwindows(this,v);
//                sharePopupwindows.show();
                break;
        }
    }
    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    linearLayout.setVisibility(View.GONE);
                    arrayList = msg.getData().getParcelableArrayList("con_ol");
                    if (arrayList!=null){
                        if (arrayList.size()>0){
                            linearLayout_ol.setVisibility(View.GONE);
                            linearLayout_ol.setVisibility(View.GONE);
                            adapterOldTimey = new AdapterOldTimey(myimageloder.imageLoader,myimageloder.options,getApplication(),arrayList);
                            listView.setAdapter(adapterOldTimey);
                            listView.setOnItemClickListener(ActivityOldTimey.this);
                        }else {
                            linearLayout_ol.setVisibility(View.VISIBLE);
                        }
                    }else {
                        linearLayout.setVisibility(View.VISIBLE);
                    }
                    break;
                case 2:

                    break;
            }
        }
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        SharePreferencesUlits.saveString(getApplication(), Constants.PID,arrayList.get(position).getID());
        Intent intent = new Intent();
        intent.putExtra("back",String.valueOf(Numbers.TEN));
        intent.putExtra("th_ol",arrayList.get(position).getID());
        intent.setClass(getApplication(), ActivityShoppingdetail.class);
        startActivity(intent);
    }
    /**
     * 上拉刷新
     * @param pullToRefreshLayout
     */
    @Override
    public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
        new Handler(){
            @Override
            public void handleMessage(Message msg) {
                arrayList1 = new ArrayList<Record>();
                index = 1;
                MyThreadPoolManager.getInstance().execute(runnable);
                pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
            }
        }.sendEmptyMessageDelayed(0,2000);
    }

    /**
     * 下拉加载
     * @param pullToRefreshLayout
     */
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

    @Override
    protected void onDestroy() {
        if (arrayList != null){
            arrayList.clear();
        }
        if (arrayList1 != null){
            arrayList1.clear();
        }
        super.onDestroy();
    }
}
