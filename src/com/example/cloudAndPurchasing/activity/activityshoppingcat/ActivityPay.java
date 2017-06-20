package com.example.cloudAndPurchasing.activity.activityshoppingcat;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.*;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.*;
import android.widget.*;
import com.example.cloudAndPurchasing.*;
import com.example.cloudAndPurchasing.activity.activitycloud.ActivityCloudRecord;
import com.example.cloudAndPurchasing.adapter.adaptershopping.AdapterPize;
import com.example.cloudAndPurchasing.adapter.adaptersurpired.AdapterWinningPopup;
import com.example.cloudAndPurchasing.customcontrol.listviewupdown.PullToRefreshLayout;
import com.example.cloudAndPurchasing.fragment.MainFragment;
import com.example.cloudAndPurchasing.fragment.ShoppingCarFragment;
import com.example.cloudAndPurchasing.http.HttpTransfeData;
import com.example.cloudAndPurchasing.http.Numbers;
import com.example.cloudAndPurchasing.http.SaveShared;
import com.example.cloudAndPurchasing.kind.Count;
import com.example.cloudAndPurchasing.kind.Pize;
import com.example.cloudAndPurchasing.kind.Statese;
import com.example.cloudAndPurchasing.kind.StringUtils;
import com.example.cloudAndPurchasing.manager.MyThreadPoolManager;
import com.example.cloudAndPurchasing.tiltle.TeaskBar;
import com.example.cloudAndPurchasing.zhi.LogUtil;
import com.example.cloudAndPurchasing.zhi.ValuePrice;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/8 0008.
 */
public class ActivityPay extends BaseFragmentActivity implements View.OnClickListener {
    /**
     *  arraylist object
     *
     */
    private Button button,button_ol,btn;
    private Button imageButton;
    private ArrayList<Pize> arrayList;
    private String pid,count;
    private ListView listView;
    private AdapterPize adapterPize;
    private int nmu = 0;
    private String sum = null;
    private TextView textView_jie,textView_reci;
    private Statese statese;
    private AdapterWinningPopup adapterPopup;
    private LinearLayout linearLayout,linearLayoutLoading;
    private PopupWindow popupWindow;
    private GridView gridView;
    private TextView textView;
    private PullToRefreshLayout pullToRefreshLayout;
    private int index,num_run = 0 ;
    private boolean falge = false;
    private ArrayList<Count> counts,arrayListLuckNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitypaylayout);
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
        index = 1;
        LogUtil.i("Activitypay initData start.","");
        try{
            MyThreadPoolManager.getInstance().execute(runnable);
        }catch (Exception e){
            LogUtil.e("activitypay initData error:",e.toString());
        }finally {
            LogUtil.i("activitypay initData end.","");
        }
        arrayList = new ArrayList<Pize>();
        arrayListLuckNumber = new ArrayList<Count>();
    }
    /**
     * 支付回调请求
     */
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            statese = new Statese();
            String token = SaveShared.tokenget(getApplication());
            String money = getIntent().getStringExtra("money");
            String number_ol = getIntent().getStringExtra("number");
            String con = getIntent().getStringExtra("method");
            statese = HttpTransfeData.Webhookhttp(getApplication(), money, number_ol, con, token);
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putParcelable("state_ol",statese);
            message.what = Numbers.ONE;
            message.setData(bundle);
            handler.sendMessage(message);
        }
    };
    public void initView(){
        //初始化控件
        linearLayout = (LinearLayout)findViewById(R.id.linearlayout_shoppingdateil_loading);
        linearLayout.setVisibility(View.VISIBLE);
        button = (Button)findViewById(R.id.button_go_on);
        button_ol = (Button)findViewById(R.id.button_select);
        imageButton = (Button)findViewById(R.id.imagebutton_result);
        textView_jie  = (TextView)findViewById(R.id.textview_jieguo);
        textView_reci = (TextView)findViewById(R.id.textview_renci);
        listView = (ListView)findViewById(R.id.listview_pize);
        //设置监听事件
        button.setOnClickListener(this);
        button_ol.setOnClickListener(this);
        imageButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.button_go_on:
                //跳转至主界面
                finish();
                break;
            case R.id.button_select:
                //跳转至艾购记录
                intent.setClass(this, ActivityCloudRecord.class);
                startActivity(intent);
                break;
            case R.id.imagebutton_result:
                //跳转至商品列表界面
                intent.putExtra("index",ValuePrice.VALUE_TWO);
                intent.setClass(this, MyActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    /**
     * ui界面刷新(handler机制)
     */
    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
         public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    statese = new Statese();
                    statese = msg.getData().getParcelable("state_ol");
                    linearLayout.setVisibility(View.GONE);
                    if (statese != null){
                        if (!StringUtils.isEmpty(statese.getState())){
                            if (statese.getState().equals(String.valueOf(Numbers.ONE))){
                                if (!StringUtils.isEmpty(statese.getData())){
                                    try {
                                        JSONArray jsonArray = new JSONArray(statese.getData());
                                        for (int i = 0;i<jsonArray.length();i++){
                                            List list = new ArrayList();
                                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                                            Pize pize = new Pize();
                                            pize.setID(jsonObject.getString("ID"));
                                            JSONArray jsonArrayluckNumber = new JSONArray(jsonObject.getString("LuckyNumbers"));
//                                            for (int c = 0;c<jsonArrayluckNumber.length();c++){
//                                                list.add(jsonArrayluckNumber.get(c));
//                                            }
                                            pize.setCount(String.valueOf(jsonArrayluckNumber.length()));
//                                            pize.setLuckyNumbers(list);
                                            pize.setPeriodsTitle(jsonObject.getString("PeriodsTitle"));
                                            pize.setPNumber(jsonObject.getInt("PNumber"));
                                            arrayList.add(pize);
                                            if (jsonArrayluckNumber.length() == 0){
                                                if (arrayList!= null){
                                                    arrayList.clear();
                                                }
                                                if (num_run < 3){
                                                    linearLayout.setVisibility(View.VISIBLE);
                                                    MyThreadPoolManager.getInstance().execute(runnable);
                                                }
                                                nmu = 0;
                                                break;
                                            }
                                            nmu++;
                                        }
                                        sum =  getIntent().getStringExtra("lucknumber");
                                        if (nmu !=0){
                                            textView_reci.setText("您成功参与了"+nmu+"件奖品共"+sum+"人次夺宝,信息如下:");
                                        }
                                        AdapterPize.SelectLuckNumberOnclick selectLuckNumberOnclick = new AdapterPize.SelectLuckNumberOnclick() {
                                            @Override
                                            protected void selectLuckNumberOnclick(Integer tag, View v) {
                                                switch (v.getId()){
                                                    case R.id.btn_luckNumber:
                                                        arrayListLuckNumber = new ArrayList<Count>();
                                                        arrayListLuckNumber.clear();
                                                        count = arrayList.get(tag).getCount();
                                                        pid = arrayList.get(tag).getID();
                                                        falge = true;
                                                        backgroungauth(0.5f);
                                                        initpopupwind(falge);
                                                        break;
                                                }
                                            }
                                        };
                                        adapterPize = new AdapterPize(getApplication(),arrayList,selectLuckNumberOnclick);
                                        listView.setAdapter(adapterPize);
                                    } catch (Exception e){
                                        LogUtil.e("activitypay handler error:",e.toString());
                                    }
                                }else {
                                    if (num_run<3){
                                        linearLayout.setVisibility(View.VISIBLE);
                                        num_run++;
                                        MyThreadPoolManager.getInstance().execute(runnable);
                                    }
                                }
                            }else {
                                Toast.makeText(getApplication(),statese.getMsg(),Toast.LENGTH_SHORT).show();
//                                if (num_run<3){
//                                    linearLayout.setVisibility(View.VISIBLE);
//                                    num_run++;
//                                    MyThreadPoolManager.getInstance().execute(runnable);
//                                }
                            }
                        }
                    }else {
                        if (num_run<3){
                            linearLayout.setVisibility(View.VISIBLE);
                            num_run++;
                            MyThreadPoolManager.getInstance().execute(runnable);
                        }
                    }
                    break;
                case 2:
                    linearLayoutLoading.setVisibility(View.GONE);
                    counts = new ArrayList<Count>();
                    counts = msg.getData().getParcelableArrayList("luckNumber");
                    if (counts != null){
                        if (counts.size()>0){
                            adapterPopup = new AdapterWinningPopup(getApplication(),counts);
                            gridView.setAdapter(adapterPopup);
                            if (adapterPopup.getCount()>50){
                                gridView.setSelected(true);
                                gridView.setSelection(adapterPopup.getCount()-50);
                            }
                        }
                    }
                    break;
            }
        }
    };

    /**
     * 弹框显示夺宝号
     * @param falge1
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void initpopupwind(boolean falge1) {
        popupWindow = new PopupWindow();
        View view = LayoutInflater.from(this).inflate(R.layout.numberlayout,null);
        popupWindow.setContentView(view);
        popupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        gridView = (GridView)view.findViewById(R.id.gridview_luckynunber_ol);
        btn = (Button)view.findViewById(R.id.button_luck_number_this);
        textView = (TextView)view.findViewById(R.id.textview_luckynumber_sum);
        linearLayoutLoading = (LinearLayout)view.findViewById(R.id.linearlayout_this_lucknumber);
        pullToRefreshLayout = (PullToRefreshLayout)view.findViewById(R.id.popuplayout_updata);
        SpannableString style_OL = new SpannableString("本期参与"+count+"人次");
        style_OL.setSpan(new ForegroundColorSpan(Color.RED), 4, 4 + count.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //设置指定位置文字的颜色
        textView.setText(style_OL);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                backgroungauth(1f);
            }
        });
        linearLayoutLoading.setVisibility(View.VISIBLE);
        MyThreadPoolManager.getInstance().execute(runnableLuckNumber);
        pullToRefreshLayout.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
                new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        arrayListLuckNumber = new ArrayList<Count>();
                        index = 1;
                        MyThreadPoolManager.getInstance().execute(runnableLuckNumber);
                        pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                    }
                }.sendEmptyMessageDelayed(0, 1500);
            }

            @Override
            public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
                new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        index++;
                        MyThreadPoolManager.getInstance().execute(runnableLuckNumber);
                        pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                    }
                }.sendEmptyMessageDelayed(0, 1500);
            }
        });
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                popupWindow.dismiss();
                backgroungauth(1f);
            }
        });
        if (falge1){
            popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        }

    }
    //查看夺宝号
    private Runnable runnableLuckNumber = new Runnable() {
        @Override
        public void run() {
            counts = new ArrayList<Count>();
            counts = HttpTransfeData.lucknumberhttp(getApplication(),counts, pid,index);
            arrayListLuckNumber.addAll(counts);
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("luckNumber",arrayListLuckNumber);
            message.setData(bundle);
            message.what = Numbers.TWO;
            handler.sendMessage(message);
        }
    };
    /**
     * 获取back键绑定跳转
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            Intent intent = new Intent();
            intent.setClass(this,ActivityIndent.class);
            startActivity(intent);
            finish();
            return true;
        }else {
            return super.onKeyDown(keyCode, event);
        }
    }
    /**
     * 子线程随界面销毁
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    /**
     * 弹框改变背景色
     * @param v
     */
    private void backgroungauth(float v) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = v;
        getWindow().setAttributes(lp);
    }
}
