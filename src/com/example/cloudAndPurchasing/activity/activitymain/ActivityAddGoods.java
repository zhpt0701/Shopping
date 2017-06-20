package com.example.cloudAndPurchasing.activity.activitymain;

import android.content.Intent;
import android.os.*;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;
import com.example.cloudAndPurchasing.BaseFragmentActivity;
import com.example.cloudAndPurchasing.MyActivity;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.adapter.adaptermain.AdapterAddGoods;
import com.example.cloudAndPurchasing.customcontrol.listviewupdown.PullToRefreshView;
import com.example.cloudAndPurchasing.http.HttpTransfeData;
import com.example.cloudAndPurchasing.http.Numbers;
import com.example.cloudAndPurchasing.http.SaveShared;
import com.example.cloudAndPurchasing.imageloder.Imageloderinit;
import com.example.cloudAndPurchasing.kind.*;
import com.example.cloudAndPurchasing.manager.MyThreadPoolManager;
import com.example.cloudAndPurchasing.tiltle.TeaskBar;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/13 0013.
 */
public class ActivityAddGoods extends BaseFragmentActivity implements View.OnClickListener, AdapterView.OnItemClickListener, PullToRefreshView.OnHeaderRefreshListener, PullToRefreshView.OnFooterRefreshListener {
    private Button imageButton;
    private HandlerThread handlerThread;
    private Handler mhandler;
    private Imageloderinit imageloderinit;
    private ArrayList<Goods> arrayList,arrayList1;
    private AdapterAddGoods adapterAddGoods;
    private Statese statese;
    private PullToRefreshView pullToRefreshLayout;
    private GridView gridView;
    private String pid = null,gid = null;
    private int index = 0;
    private LinearLayout linearLayout,linearLayout_ol;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityaddgoods);

        TeaskBar.onSystemoutcolor(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        initData();
        initView();
    }
    public void initData(){
        arrayList1 = new ArrayList<Goods>();
        index = 1;
        imageloderinit = new Imageloderinit(this);
        handlerThread = new HandlerThread("MyHandlerThread");
        handlerThread.start();
        mhandler = new Handler(handlerThread.getLooper());
        arrayList = new ArrayList<Goods>();
        MyThreadPoolManager.getInstance().execute(runnable);

    }

    /**
     * 获取搜索结果
     */
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            String con = getIntent().getStringExtra("each");
            arrayList = HttpTransfeData.eachgoods(getApplication(),con,index);
            arrayList1.addAll(arrayList);
            Message message = new Message();
            Bundle bundle = new Bundle();
            message.what = Numbers.ONE;
            bundle.putParcelableArrayList("con",arrayList1);
            message.setData(bundle);
            handler.sendMessage(message);
        }
    };
    public void initView(){
        linearLayout = (LinearLayout)findViewById(R.id.linearlayout_shoppingdateil_loading);
        linearLayout_ol = (LinearLayout)findViewById(R.id.linearlayout_shoppingdateil_delete);
        linearLayout.setVisibility(View.VISIBLE);
        imageButton = (Button)findViewById(R.id.imagebutton_addgoodsback);
        gridView = (GridView)findViewById(R.id.gridview_eachgoods);
        gridView.setOnScrollListener(new PauseOnScrollListener(imageloderinit.imageLoader,true,false));
        pullToRefreshLayout = (PullToRefreshView)findViewById(R.id.thiseachgoodsdateil);
        gridView.setOnItemClickListener(this);
        imageButton.setOnClickListener(this);
        pullToRefreshLayout.setOnFooterRefreshListener(this);
        pullToRefreshLayout.setOnHeaderRefreshListener(this);
    }

    @Override
    public void onClick(View v) {
       switch (v.getId()) {
           case R.id.imagebutton_addgoodsback:
               Intent intent = new Intent();
               intent.setClass(this, ActivityEach.class);
               startActivity(intent);
               finish();
               break;
       }
    }


   /* @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            Intent intent = new Intent();
            intent.setClass(this,ActivityEach.class);
            startActivity(intent);
            finish();
            return true;
        }else {
            return  super.onKeyDown(keyCode, event);
        }

    }*/
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    linearLayout.setVisibility(View.GONE);
                    arrayList = msg.getData().getParcelableArrayList("con");
                    if (arrayList != null){
                        linearLayout_ol.setVisibility(View.GONE);
                        if (arrayList.size()>0){
                            linearLayout_ol.setVisibility(View.GONE);
                            AdapterAddGoods.AddOnClickLisenter addOnClickLisenter = new AdapterAddGoods.AddOnClickLisenter() {
                                @Override
                                protected void addOnClickLisenter(final Integer tag, View v) {
                                    switch (v.getId()){
                                        case R.id.button_jiarufangjian:
                                            linearLayout.setVisibility(View.VISIBLE);
                                            gid = arrayList.get(tag).getGoodsid();
                                            pid = arrayList.get(tag).getProductID();
                                            MyThreadPoolManager.getInstance().execute(runnableAddGoods);
                                            break;
                                    }
                                }
                            };
                            adapterAddGoods = new AdapterAddGoods(imageloderinit.imageLoader,imageloderinit.options,getApplication(),arrayList,addOnClickLisenter);
                            gridView.setAdapter(adapterAddGoods);
                            gridView.setSelected(true);
                            gridView.setSelection(adapterAddGoods.getCount()-10);
                        }else {
                            linearLayout_ol.setVisibility(View.VISIBLE);
                        }
                    }else {
                        linearLayout_ol.setVisibility(View.VISIBLE);
                    }
                    break;
                case 2:
                    linearLayout.setVisibility(View.GONE);
                    statese = new Statese();
                    statese = msg.getData().getParcelable("state");
                    if (statese != null){
                        if (statese.getState().equals(String.valueOf(Numbers.ONE))){
                            if (!statese.getMsg().equals("token失效")){
                                if (statese.getMsg().equals("更改房间失败")){
                                    Toast.makeText(getApplication(),"更改商品失败",Toast.LENGTH_SHORT).show();
                                }else if (statese.getMsg().equals("更改房间成功")){
                                    Toast.makeText(getApplication(),"更改商品成功",Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(getApplication(),statese.getMsg(),Toast.LENGTH_SHORT).show();
                                }
                                finish();
                            }
                        }
                    }
                    break;
            }
        }

    };
    private Runnable runnableAddGoods = new Runnable() {
        @Override
        public void run() {
            String token = SaveShared.tokenget(getApplication());
            String uid = SaveShared.uid(getApplication());
            String roomid = SharePreferencesUlits.getString(getApplication(),Constants.ROOMID,Constants.NUll_VALUE);
            statese = new Statese();
            if (!StringUtils.isEmpty(roomid)){
                statese = HttpTransfeData.updategoodshttp(getApplication(),token,roomid,uid,pid,gid);
                Message message = new Message();
                Bundle bundle = new Bundle();
                message.what = Numbers.TWO;
                bundle.putParcelable("state",statese);
                message.setData(bundle);
                handler.sendMessage(message);
            }else {
                statese = HttpTransfeData.crateroomhttp(getApplication(),token,pid,uid,gid);
                Message message = new Message();
                Bundle bundle = new Bundle();
                message.what = Numbers.TWO;
                bundle.putParcelable("state",statese);
                message.setData(bundle);
                handler.sendMessage(message);
            }
        }
    };

    @Override
    protected void onDestroy() {
        mhandler.removeCallbacks(runnable);
        mhandler.removeCallbacks(runnableAddGoods);
        super.onDestroy();
    }

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        pullToRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                arrayList1= new ArrayList<Goods>();
                index=1;
                MyThreadPoolManager.getInstance().execute(runnable);
                pullToRefreshLayout.onFooterRefreshComplete();
            }
        },1500);
    }

    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        pullToRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
               index++;
                MyThreadPoolManager.getInstance().execute(runnable);
                pullToRefreshLayout.onFooterRefreshComplete();
            }
        },1500);
    }
}
