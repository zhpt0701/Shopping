package com.example.cloudAndPurchasing.activity.activitymain;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.*;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;
import com.example.cloudAndPurchasing.BaseFragmentActivity;
import com.example.cloudAndPurchasing.FragmentFactory;
import com.example.cloudAndPurchasing.MyActivity;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.adapter.adaptersurpired.AdapterCloudRoom;
import com.example.cloudAndPurchasing.customcontrol.listviewupdown.PullToRefreshLayout;
import com.example.cloudAndPurchasing.fragment.MainFragment;
import com.example.cloudAndPurchasing.http.HttpTransfeData;
import com.example.cloudAndPurchasing.http.Numbers;
import com.example.cloudAndPurchasing.http.SaveShared;
import com.example.cloudAndPurchasing.imageloder.Imageloderinit;
import com.example.cloudAndPurchasing.kind.*;
import com.example.cloudAndPurchasing.manager.MyThreadPoolManager;
import com.example.cloudAndPurchasing.tiltle.TeaskBar;
import com.example.cloudAndPurchasing.times.CToast;
import com.example.cloudAndPurchasing.zhi.LogUtil;
import com.example.cloudAndPurchasing.zhi.ValuePrice;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

import java.util.ArrayList;
import java.util.HashMap;

/**out
 * Created by Administrator on 2016/3/23 0023.
 */
public class ActivityCloudRoom extends BaseFragmentActivity implements View.OnClickListener, PullToRefreshLayout.OnRefreshListener {
//    @InjectView(id = R.id.imagebutton_back)
    private Button imageButton;
    private AdapterCloudRoom adapterCloudRoom;
    private CloudRoom cloudRoom,cloudRoomes;
    private PullToRefreshLayout pullToRefreshLayout;
    private Statese statese;
    private Button button,buttonAddGoods,buttonDelete;
    private ListView listView;
    private Handler mhandler ;
    private HandlerThread handlerThread;
    private PopupWindow popupWindow ;
    private Imageloderinit imageloderinit;
    private boolean flag = false;
    private Integer delete = 0,state = 0;
    private TextView textView;
    private int index = 0;
    private LinearLayout linearLayout,linearLayout_loading,linearlayout_delete;
    private HashMap<Integer,Rooms> hashMap;
    private String tag_ol = null,pid = null,gid = null;
    private String limitcount = null,surpcount = null;
    private CToast cToast;
    private ArrayList<CloudRoom.DataEntity> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitycloudroomlayout);
        
        TeaskBar.onSystemoutcolor(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        SharePreferencesUlits.saveString(this, Constants.ADDSHOPPINGACTIVITY, "addshoppingactivity");

    }

    @Override
    protected void onResume() {
        super.onResume();
        //初始化数据
        initData();
        //初始化控件
        initview();
    }

    /**
     * 初始化数据
     */
    public void initData(){
        LogUtil.i("ActivityCloudRoom initData start.","");
        linearLayout = (LinearLayout)findViewById(R.id.linearlayout_createroom);
        linearLayout_loading = (LinearLayout)findViewById(R.id.linearlayout_shoppingdateil_loading);
        listView = (ListView)findViewById(R.id.listview_yungoufangjian);
        index = 1;
        cloudRoom = new CloudRoom();
        arrayList = new ArrayList<CloudRoom.DataEntity>();
        hashMap = new HashMap<Integer, Rooms>();
        imageloderinit = new Imageloderinit(getApplication());
        handlerThread = new HandlerThread("MyHandlerThread");
        handlerThread.start();
        mhandler = new Handler(handlerThread.getLooper());
        try {
            if (!StringUtils.isEmpty(SharePreferencesUlits.getString(getApplication(), Constants.TOKEN,Constants.NUll_VALUE))){
                linearLayout.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
                linearLayout_loading.setVisibility(View.VISIBLE);
                MyThreadPoolManager.getInstance().execute(runnable);
            }else {
                listView.setVisibility(View.GONE);
                linearLayout.setVisibility(View.VISIBLE);
                linearLayout_loading.setVisibility(View.GONE);
            }
        }catch (Exception e){
            LogUtil.e("ActivityCloudRoom initData error:",e.toString());
        }finally {
            LogUtil.i("ActivityCloudRoom initData end.","");
        }
        listView.setOnScrollListener(new PauseOnScrollListener(imageloderinit.imageLoader,true,false));
    }
    //http获取房间数据
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            String token = SaveShared.tokenget(getApplication());
            String uid = SaveShared.uid(getApplication());
            cloudRoom = HttpTransfeData.couldroomdata(getApplication(), uid,token,index);
            if (cloudRoom.data.size()>0){
                arrayList.addAll(cloudRoom.data);
            }
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("room_ol", arrayList);
            bundle.putParcelable("state",cloudRoom);
            message.what = Numbers.ONE;
            message.setData(bundle);
            handler.sendMessage(message);
        }
    };

    /**
     * 删除或修改商品弹窗
     * @param flag
     * @param v
     * @param tag
     * @param pid_ol
     * @param gid_ol
     */
    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    private void addDeletePopupWindows(boolean flag,View v, final String tag, final String pid_ol, final String gid_ol) {
        popupWindow = new PopupWindow();
        View view = LayoutInflater.from(this).inflate(R.layout.adddeletepoplayout,null);
        popupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(view);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        buttonAddGoods = (Button)view.findViewById(R.id.button_adddeletepop_add);
        buttonDelete = (Button)view.findViewById(R.id.button_adddeletepop_delete);
        buttonAddGoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                SharePreferencesUlits.saveString(getApplication(),Constants.ROOMID,tag);
                popupWindow.dismiss();
                intent.setClass(getApplication(), ActivityCreateRoom.class);
                startActivity(intent);

            }
        });
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout_loading.setVisibility(View.VISIBLE);
                tag_ol = tag;
                pid = pid_ol;
                gid = gid_ol;
                MyThreadPoolManager.getInstance().execute(runnableDelete);
                popupWindow.dismiss();
            }
        });
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });
        if (flag){
            popupWindow.showAsDropDown(v,-150,0);
        }
    }
    private Runnable runnableDelete = new Runnable() {
        @Override
        public void run() {
            String token = SaveShared.tokenget(getApplication());
            String uid = SaveShared.uid(getApplication());
            statese = HttpTransfeData.deleteroomhttp(getApplication(),tag_ol,pid,gid,token,uid);
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putParcelable("state", statese);
            message.what = Numbers.TWO;
            message.setData(bundle);
            handler.sendMessage(message);
        }
    };

    /**
     * 初始化控件并设置button监听事件
     */
    public void initview(){
        linearlayout_delete = (LinearLayout)findViewById(R.id.linearlayout_shoppingdateil_delete);
        pullToRefreshLayout = (PullToRefreshLayout)findViewById(R.id.pulllayout_cloud);
        textView = (TextView)findViewById(R.id.textview_createroom);
        imageButton = (Button) findViewById(R.id.imagebutton_back);
        button = (Button)findViewById(R.id.button_chuanjianfangjian);
        imageButton.setOnClickListener(this);
        button.setOnClickListener(this);
        pullToRefreshLayout.setOnRefreshListener(this);
        //设置文字字体
        AssetManager assetManager = getAssets();
        Typeface tf=Typeface.createFromAsset(assetManager, "fonts/minijianzhiyi.ttf");//根据路径得到Typeface
        textView.setTypeface(tf);//
    }
    @Override
    public void onClick(View v) {
        Object tag = v.getTag();
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.imagebutton_back:
                SharePreferencesUlits.saveString(getApplication(),Constants.ROOMID,Constants.NUll_VALUE);
                finish();
                break;
            case R.id.button_chuanjianfangjian:
                if (cloudRoom.data != null){
                    if (cloudRoom.data.size()>0){
                        int roomNumber = 0;
                        String uid = SaveShared.uid(getApplication());
                        for (int i= 0;i<cloudRoom.data.size();i++){
                            if (cloudRoom.data.get(i).CreateUserID.equals(uid)){
                                roomNumber++;
                            }
                        }
                        if (roomNumber<3){
                            SharePreferencesUlits.saveString(getApplication(),Constants.ROOMID,Constants.NUll_VALUE);
                            intent.putExtra("cloud",String.valueOf(Numbers.ONE));
                            intent.setClass(this,ActivityCreateRoom.class);
                            startActivity(intent);
                        }else {
                            cToast = CToast.makeText(getApplication(),"每个人只允许创建三个房间~",900);
                            cToast.show();
                        }
                    }else {
                        intent.putExtra("cloud",String.valueOf(Numbers.ONE));
                        intent.setClass(this,ActivityCreateRoom.class);
                        startActivity(intent);
                    }
                }else {
                    intent.putExtra("cloud",String.valueOf(Numbers.ONE));
                    intent.setClass(this,ActivityCreateRoom.class);
                    startActivity(intent);
                }
                break;
        }
    }
    //设置弹框背景虚化
    public void backgroundAlpha(float bgAlpha)
    {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha;
        getWindow().setAttributes(lp);
    }

    /**
     * 主界面数据刷新
     */
    private Handler  handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    linearLayout_loading.setVisibility(View.GONE);
                    cloudRoom = msg.getData().getParcelable("state");
                    arrayList = msg.getData().getParcelableArrayList("room_ol");
                    if (cloudRoom.state == 1){
                        if (arrayList.size()>0){
                            linearlayout_delete.setVisibility(View.GONE);
                            String uid = SaveShared.uid(getApplication());
                            AdapterCloudRoom.CloudOnClickListener cloudOnClickListener = new AdapterCloudRoom.CloudOnClickListener() {
                                @Override
                                protected void cloudOnClickListener(Integer tag, View v) {
                                    Intent intent = new Intent();
                                    switch (v.getId()){
                                        case R.id.button_yaoqinghaoyou:
                                            if (!arrayList.get(tag).LeftCount.equals("null")){
                                                if (Integer.parseInt(arrayList.get(tag).LeftCount)>0){
                                                    intent.putExtra("roomid",arrayList.get(tag).RID);
                                                    intent.putExtra("cuser",arrayList.get(tag).CreateUserID);
                                                    intent.setClass(getApplicationContext(),ActivityInvite.class);
                                                    startActivity(intent);
                                                    // finish();
                                                }else {
                                                    cToast = CToast.makeText(getApplication(),"没有更多的商品了~",600);
                                                    cToast.show();
                                                }
                                            }else {
                                                cToast = CToast.makeText(getApplication(),"没有更多的商品了~",600);
                                                cToast.show();
                                            }
                                            break;
                                        case R.id.button_start:
                                            linearLayout_loading.setVisibility(View.VISIBLE);
                                            String token = SaveShared.tokenget(getApplication());
                                            if (!token.equals("")){
                                                pid = arrayList.get(tag).ID;
                                                limitcount = arrayList.get(tag).LimitCount;
                                                surpcount = arrayList.get(tag).LeftCount;
                                                try {
                                                    MyThreadPoolManager.getInstance().execute(runnableGoodsNumber);
                                                }catch (Exception e){
                                                    LogUtil.e("ActivityCloudRoom handler error:",e.toString());
                                                }
                                            }
                                            break;
                                        case R.id.imagebutton_add_delete:
                                            flag = true;
                                            delete = tag;
                                            addDeletePopupWindows(flag,v,arrayList.get(tag).RID,arrayList.get(tag).ProductID,arrayList.get(tag).ID);
                                            backgroundAlpha(0.5f);
                                            break;
                                    }
                                }
                            };
                            adapterCloudRoom = new AdapterCloudRoom(uid,getApplication(),arrayList,cloudOnClickListener,imageloderinit.imageLoader,imageloderinit.options);
                            listView.setAdapter(adapterCloudRoom);
                            if(adapterCloudRoom.getCount()>10){
                                listView.setSelected(true);
                                listView.setSelection(adapterCloudRoom.getCount()-5);
                            }
                        }else {
                            linearlayout_delete.setVisibility(View.VISIBLE);
                        }
                    }else {
                        if (cloudRoom.msg.equals("token失效")){
                            state = 1;
                            MyThreadPoolManager.getInstance().execute(runnableSingle);
                        }
                    }
                    break;
                case 2:
                    linearLayout_loading.setVisibility(View.GONE);
                    statese = new Statese();
                    statese = msg.getData().getParcelable("state");
                    if (statese != null){
                        if (statese.getState().equals(String.valueOf(Numbers.ONE))){
                            arrayList.clear();
                            MyThreadPoolManager.getInstance().execute(runnable);
                        }
                        cToast = CToast.makeText(getApplication(),statese.getMsg(),600);
                        cToast.show();
                    }
                    break;
                case 3:   //开始抢购
                    linearLayout_loading.setVisibility(View.GONE);
                    statese = new Statese();
                    statese = msg.getData().getParcelable("state");
                    if (statese != null){
                        if (statese.getState().equals(String.valueOf(Numbers.ONE))){
                            MyThreadPoolManager.getInstance().execute(runnableCarNumber);
                        }else {
                            cToast = CToast.makeText(getApplication(),statese.getMsg(),600);
                            cToast.show();
                        }
                    }
                    break;
                case 4:
                    statese = new Statese();
                    statese = msg.getData().getParcelable("content_number");
                    if (statese!=null){
                        if (statese.getState().equals(String.valueOf(Numbers.ONE))){
                            if (!limitcount.equals("0")){
                                if (Integer.parseInt(limitcount)>0){
                                    if (Integer.parseInt(limitcount)<Integer.parseInt(surpcount)){
                                        if (Integer.parseInt(statese.getData())<Integer.parseInt(limitcount)){
                                            MyThreadPoolManager.getInstance().execute(runnableAddGoods);
                                        }else {
                                            linearLayout_loading.setVisibility(View.GONE);
                                            cToast = CToast.makeText(getApplication(),"没有更多商品了~",600);
                                            cToast.show();
                                        }
                                    }else {
                                        linearLayout_loading.setVisibility(View.GONE);
                                        cToast = CToast.makeText(getApplication(),"没有更多商品了~",600);
                                        cToast.show();
                                    }
                                }
                            }else {
                                if (Integer.parseInt(statese.getData())<Integer.parseInt(surpcount)){
                                    MyThreadPoolManager.getInstance().execute(runnableAddGoods);
                                }else {
                                    linearLayout_loading.setVisibility(View.GONE);
                                    cToast = CToast.makeText(getApplication(),"没有更多商品了~",600);
                                    cToast.show();
                                }
                            }
                        }else {
                           if (statese.getMsg().equals("token失效")){
                               state = 2;
                               MyThreadPoolManager.getInstance().execute(runnableSingle);
                           }else {
                               cToast = CToast.makeText(getApplication(),statese.getMsg(),600);
                           }
                        }
                    }
                    break;
                case 5:
                    statese = new Statese();
                    statese = msg.getData().getParcelable("content");
                    if (statese != null){
                        if (statese.getState().equals(String.valueOf(Numbers.ONE))){
                            if (!StringUtils.isEmpty(statese.getData())){
                                SharePreferencesUlits.saveString(getApplication(),Constants.CAR_NUMBER,statese.getData());
                                MyActivity.textView.setText(statese.getData());
                            }
                            Intent intent = new Intent();
                            intent.putExtra("zhi", ValuePrice.VALUE_FOUR);
                            intent.putExtra("car",String.valueOf(Numbers.TWO));
                            intent.setClass(getApplication(), MyActivity.class);
                            MainFragment mainFragment = (MainFragment) FragmentFactory.getFragment(0);
                            mainFragment.setCurrentFragment(3);
                            startActivity(intent);
                            finish();
                        }
                    }
                    break;
                case 6:
                    statese = msg.getData().getParcelable("this");
                    if (statese != null){
                        if (statese.getState().equals(String.valueOf(Numbers.ONE))){
                            try{
                                if (state == 1){
                                    state = 0;
                                    MyThreadPoolManager.getInstance().execute(runnable);
                                }else {
                                   state = 0;
                                    MyThreadPoolManager.getInstance().execute(runnableGoodsNumber);
                                }
                            }catch (Exception e){
                                LogUtil.e("MyActivity handler error:",e.toString());
                            }
                        }
                    }
                    break;
            }

        }
    };
    /**
     * 获取购物车商品数量
     */
    private Runnable runnableGoodsNumber = new Runnable() {
        @Override
        public void run() {
            statese = new Statese();
            String token = SaveShared.tokenget(getApplication());
            statese = HttpTransfeData.httpshoppingcat(getApplication(),statese,token,pid);
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putParcelable("content_number",statese);
            message.setData(bundle);
            message.what = Numbers.FOUR;
            handler.sendMessage(message);
        }
    };
    /**
     * 重新登陆
     */
    private Runnable runnableSingle = new Runnable() {
        @Override
        public void run() {
            statese = new Statese();
            String user_ol = SaveShared.loadong_name(getApplicationContext());
            String pass = SaveShared.loading_pass(getApplicationContext());
            statese = HttpTransfeData.httppostloding(getApplicationContext(), user_ol, pass);
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putParcelable("this", statese);
            message.setData(bundle);
            message.what = Numbers.SIX;
            handler.sendMessage(message);
        }
    };
    /**
     * 获取商品件数
     */
    private Runnable runnableCarNumber = new Runnable() {
        @Override
        public void run() {
            try {
                statese = new Statese();
                String token = SaveShared.tokenget(getApplication());
                statese = HttpTransfeData.getcarnumberhttp(statese, getApplication(), token);
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putParcelable("content",statese);
                message.what = Numbers.FIVE;
                message.setData(bundle);
                handler.sendMessage(message);
            }catch (Exception e){
                LogUtil.e("MyActivity+Runable1",e.toString());
            }
        }
    };
    //添加商品请求
    private Runnable runnableAddGoods = new Runnable() {
        @Override
        public void run() {
            statese = new Statese();
            String token = SaveShared.tokenget(getApplication());
            String uid = SaveShared.uid(getApplication());
            String num = "1";
            statese = HttpTransfeData.goodsaddshoppingcat(getApplication(),pid,uid,token,num);
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putParcelable("state",statese);
            message.what = Numbers.THREE;
            message.setData(bundle);
            handler.sendMessage(message);
        }
    };
    /**
     * 设置返回控制
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            startActivity(new Intent(this , MyActivity.class));
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    //销毁线程
    @Override
    protected void onDestroy() {
        SharePreferencesUlits.saveString(this, Constants.ADDSHOPPINGACTIVITY, "");
        super.onDestroy();
    }
    //下拉刷新
    @Override
    public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
        new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                index = 1;
                arrayList = new ArrayList<CloudRoom.DataEntity>();
                MyThreadPoolManager.getInstance().execute(runnable);
                pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
            }
        }.sendEmptyMessageDelayed(0,1500);
    }
    //上拉加载
    @Override
    public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
        new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                index++;
                MyThreadPoolManager.getInstance().execute(runnable);
                pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
            }
        }.sendEmptyMessageDelayed(0,1500);
    }
}
