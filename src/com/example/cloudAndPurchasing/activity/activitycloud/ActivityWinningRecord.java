package com.example.cloudAndPurchasing.activity.activitycloud;

        import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.*;
import android.view.*;
import android.widget.*;
import com.example.cloudAndPurchasing.BaseFragmentActivity;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.activity.ActivityShoppingdetail;
import com.example.cloudAndPurchasing.activity.activitycloud.photo.ActivitySurce;
import com.example.cloudAndPurchasing.adapter.adaptercloud.AdapterWinning;
import com.example.cloudAndPurchasing.customcontrol.listviewupdown.PullToRefreshLayout;
import com.example.cloudAndPurchasing.http.HttpTransfeData;
import com.example.cloudAndPurchasing.http.Numbers;
import com.example.cloudAndPurchasing.http.SaveShared;
import com.example.cloudAndPurchasing.imageloder.Imageloderinit;
import com.example.cloudAndPurchasing.kind.Statese;
import com.example.cloudAndPurchasing.kind.Winning;
import com.example.cloudAndPurchasing.manager.MyThreadPoolManager;
import com.example.cloudAndPurchasing.tiltle.TeaskBar;
import com.example.cloudAndPurchasing.zhi.LogUtil;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/5 0005.
 */
public class ActivityWinningRecord extends BaseFragmentActivity implements View.OnClickListener, AdapterView.OnItemClickListener,PullToRefreshLayout.OnRefreshListener {
    /**
     * index 页码
     * id 中奖id
     *
     */
    private Button btn;
    private TextView textView,text,textView_name;
    private ListView listView;
    private PullToRefreshLayout pullToRefreshView;
    private AdapterWinning adapterWinning;
    private ArrayList<Winning> arrayList,winnings;
    private SharedPreferences sharedPreferences;
    private Imageloderinit imageloderinit;
    private Statese statese;
    private LinearLayout linearLayout,linearLayoutLoading,linearLayout_delete;
    private int index = 0;
    private String id;
    private boolean flag = false;
    private PopupWindow popupWindow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitywinningrecordlayout);
        //改变状态栏颜色
        TeaskBar.onSystemoutcolor(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        //初始化数据
        initData();
        //初始化控件
        initview();
    }
    public void initData(){
        LogUtil.i("ActivityWinningRecord initData start.","");
        winnings = new ArrayList<Winning>();
        String num = getIntent().getStringExtra("update");
        if (num == "1"){
            SharedPreferences sharedPreferences1 = getSharedPreferences("data",MODE_PRIVATE);
            String tag = sharedPreferences1.getString("tag","");
            adapterWinning.updateview(Integer.parseInt(tag),listView);
        }
        imageloderinit = new Imageloderinit(this);
        index = 1;
        sharedPreferences = getSharedPreferences("data",MODE_PRIVATE);
        arrayList = new ArrayList<Winning>();
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            winnings = new ArrayList<Winning>();
            index = 1;
            MyThreadPoolManager.getInstance().execute(runnable);
        }catch (Exception e){
            LogUtil.e("ActivityWinningRecord initData error:",e.toString());
        }finally {
            LogUtil.i("ActivityWinningRecord initData end.","");
        }
    }
    /**
     * 获取数据
     */
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            String token = SaveShared.tokenget(getApplication());
            String uid = SaveShared.uid(getApplication());
            arrayList = HttpTransfeData.winninghttp(token,uid,getApplication(),index);
            if (arrayList != null){
                winnings.addAll(arrayList);
            }
            Message message = new Message();
            Bundle bundle = new Bundle();
            message.what = Numbers.ONE;
            bundle.putParcelableArrayList("content",winnings);
            message.setData(bundle);
            handlerUi.sendMessage(message);
        }
    };
    //http领奖
    private Runnable runnablePrize = new Runnable() {
        @Override
        public void run() {
            String token = SaveShared.tokenget(getApplication());
            String uid = SaveShared.uid(getApplication());
            statese = new Statese();
            String name = "";
            String phone = "";
            String address = "";
            statese = HttpTransfeData.awardhttp(getApplication(),token,uid,id,name,phone,address);
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putParcelable("prize",statese);
            message.what = Numbers.TWO;
            message.setData(bundle);
            handlerUi.sendMessage(message);
        }
    };
    public void initview(){
        linearLayoutLoading = (LinearLayout)findViewById(R.id.linearlayout_shoppingdateil_loading);
        linearLayout_delete = (LinearLayout)findViewById(R.id.linearlayout_shoppingdateil_delete);
        linearLayoutLoading.setVisibility(View.VISIBLE);
        linearLayout = (LinearLayout)findViewById(R.id.linearlayout_address_select);
        textView_name = (TextView)findViewById(R.id.textview_namephone);
        text = (TextView)findViewById(R.id.textview_yuqi);
        pullToRefreshView = (PullToRefreshLayout)findViewById(R.id.linearlayoutpull_listview);
        textView = (TextView)findViewById(R.id.textview_addressselect);
        listView = (ListView)findViewById(R.id.listview_winningrecord);
        btn = (Button)findViewById(R.id.imagebutton_winning);
//        linearLayoutLoading.setVisibility(View.GONE);
        listView.setOnScrollListener(new PauseOnScrollListener(imageloderinit.imageLoader,true,false));
        SharedPreferences preferences = getSharedPreferences("data",MODE_PRIVATE);
//        if (preferences.getString("address_ool","") != ""){
//            linearLayout.setVisibility(View.VISIBLE);
//            textView_ol.setVisibility(View.GONE);
//            textView.setText(preferences.getString("address_ool",""));
//            textView_name.setText(preferences.getString("username","")+"  "+preferences.getString("userphone",""));
//        }else {
//            linearLayout.setVisibility(View.GONE);
//            textView_ol.setVisibility(View.VISIBLE);
//        }
        //设置监听
        listView.setOnItemClickListener(this);
        btn.setOnClickListener(this);
        pullToRefreshView.setOnRefreshListener(this);
    }

    /**
     * 界面刷新
     */
    private Handler handlerUi = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    linearLayoutLoading.setVisibility(View.GONE);
                    arrayList = msg.getData().getParcelableArrayList("content");
                    if (arrayList != null){
                        if (arrayList.size()>0) {
                            linearLayout_delete.setVisibility(View.GONE);
                            AdapterWinning.WinningOnClickListener winningOnClickListener = new AdapterWinning.WinningOnClickListener() {
                                @Override
                                protected void winningOnClickListener(Integer tag, View view) {
                                    switch (view.getId()) {
                                        case R.id.button_luckCode:
                                            if (arrayList.get(tag).getAwardState().equals("0")) {
                                                linearLayoutLoading.setVisibility(View.VISIBLE);
                                                id = arrayList.get(tag).getAID();
                                                if (arrayList.get(tag).getIsDelivery().equals(String.valueOf(Numbers.TWO))){
                                                    MyThreadPoolManager.getInstance().execute(runnablePrize);
                                                }else {
                                                    SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
                                                    editor.putString("tag", String.valueOf(tag));
                                                    editor.commit();
                                                    Intent intent = new Intent();
                                                    intent.putExtra("aid", id);
                                                    intent.putExtra("isdeliery", arrayList.get(tag).getIsDelivery());
                                                    intent.setClass(getApplication(), ChooseActivity.class);
                                                    startActivity(intent);
                                                }
                                            } else {
                                                if (arrayList.get(tag).getIsDelivery().equals("2")){
                                                    Intent intent = new Intent();
                                                    SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
                                                    editor.putString("pid", arrayList.get(tag).getID());
                                                    editor.putString("gid", arrayList.get(tag).getWinningid());
                                                    editor.commit();
                                                    intent.setClass(getApplicationContext(), ActivitySurce.class);
                                                    startActivity(intent);
                                                }else {
                                                    if (arrayList.get(tag).getAwardState().equals("3") || arrayList.get(tag).getAwardState().equals("4")){
                                                        Intent intent = new Intent();
                                                        SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
                                                        editor.putString("pid", arrayList.get(tag).getID());
                                                        editor.putString("gid", arrayList.get(tag).getWinningid());
                                                        editor.commit();
                                                        intent.setClass(getApplicationContext(), ActivitySurce.class);
                                                        startActivity(intent);
                                                    }
                                                }
                                            }
                                            break;
                                        case R.id.button_select_luckCode:
                                            Intent intent = new Intent();
                                            intent.putExtra("stateCode",String.valueOf(Numbers.THREE));
                                            intent.putExtra("address", arrayList.get(tag).getAddress());
                                            intent.putExtra("customcode", arrayList.get(tag).getCustomCode());
                                            intent.setClass(getApplication(), ActivityMA.class);
                                            startActivity(intent);
                                            break;
                                    }
                                }
                            };
                            adapterWinning = new AdapterWinning(imageloderinit.imageLoader, imageloderinit.options, getApplication(), arrayList, winningOnClickListener);
                            listView.setAdapter(adapterWinning);
                            listView.setSelected(true);
                            listView.setSelection(adapterWinning.getCount() - 10);
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Intent intent = new Intent();
                                    intent.putExtra("th_ol", arrayList.get(position).getID());
                                    intent.putExtra("back", String.valueOf(Numbers.NINE));
                                    intent.setClass(getApplication(), ActivityShoppingdetail.class);
                                    startActivity(intent);
                                }
                            });
                        }else {
                            linearLayout_delete.setVisibility(View.VISIBLE);
                        }
                    }else {
                        linearLayout_delete.setVisibility(View.VISIBLE);
                    }

                    break;
                case 2:
                    linearLayoutLoading.setVisibility(View.GONE);
                    statese = msg.getData().getParcelable("prize");
                    if (statese != null){
                        Intent intent = new Intent();
                        intent.putExtra("stateCode",String.valueOf(Numbers.ONE));
                        intent.putExtra("customcode", statese.getData());
                        intent.setClass(getApplication(), ActivityMA.class);
                        startActivity(intent);
                        Toast.makeText(getApplication(),statese.getMsg(),Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void popupaddress(Integer tag,boolean falg) {
        popupWindow = new PopupWindow();
        View view_ol = LayoutInflater.from(this).inflate(R.layout .addresspopuplayout,null);
        popupWindow.setContentView(view_ol);
        popupWindow.setHeight(WindowManager.LayoutParams.MATCH_PARENT);
        popupWindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });
        if (falg){
            popupWindow.showAsDropDown(view_ol,0,0, Gravity.CENTER);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imagebutton_winning:
                finish();
                break;
        }
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
    //销毁线程
    @Override
    protected void onDestroy() {
        if (arrayList != null){
            arrayList.clear();
        }
        if (winnings != null){
            winnings.clear();
        }
        super.onDestroy();
    }
    //弹框改变主屏背景
    public void backgroundAlpha(float bgAlpha)
    {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha;
        getWindow().setAttributes(lp);
    }
    //下拉刷新
    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        new Handler(){
            @Override
            public void handleMessage(Message msg) {
                winnings = new ArrayList<Winning>();
                index = 1;
                MyThreadPoolManager.getInstance().execute(runnable);
                pullToRefreshView.refreshFinish(PullToRefreshLayout.SUCCEED);
            }
        }.sendEmptyMessageDelayed(0,1500);
    }
    //上拉加载
    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        new Handler(){
            @Override
            public void handleMessage(Message msg) {
                index+=1;
                MyThreadPoolManager.getInstance().execute(runnable);
                pullToRefreshView.refreshFinish(PullToRefreshLayout.SUCCEED);
            }
        }.sendEmptyMessageDelayed(0,1500);
    }
}
