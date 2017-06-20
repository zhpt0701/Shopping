package com.example.cloudAndPurchasing.fragment.fragmentsurpried;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.*;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.*;
import com.example.cloudAndPurchasing.MyActivity;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.activity.activitymain.ActivityCreateRoom;
import com.example.cloudAndPurchasing.activity.activitymain.ActivityInvite;
import com.example.cloudAndPurchasing.adapter.adaptersurpired.AdapterCloudRoom;
import com.example.cloudAndPurchasing.customcontrol.listviewupdown.PullToRefreshLayout;
import com.example.cloudAndPurchasing.http.HttpTransfeData;
import com.example.cloudAndPurchasing.http.Numbers;
import com.example.cloudAndPurchasing.http.SaveShared;
import com.example.cloudAndPurchasing.imageloder.Imageloderinit;
import com.example.cloudAndPurchasing.kind.*;
import com.example.cloudAndPurchasing.manager.MyThreadPoolManager;
import com.example.cloudAndPurchasing.times.CToast;
import com.example.cloudAndPurchasing.zhi.LogUtil;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2016/4/11 0011.
 */
public class FragmentCloudRoom extends Fragment implements PullToRefreshLayout.OnRefreshListener {
    private ListView listView;
    private PullToRefreshLayout layout;
    private ImageView imageView;
    private AdapterCloudRoom adapterCloudRoom;
    private CloudRoom cloudRoom;
    private Imageloderinit imageloderinit;
    private boolean flag = false;
    private Statese statese;
    private PopupWindow popupWindow;
    private Button button,button_ol;
    private Integer delete = 0,state = 0;
    private HashMap<Integer,Rooms> hashMap;
    private String tag_ol = null,pid = null,gid = null;
    private String limitcount = null,surpcount = null;
    private int index = 0;
    private LinearLayout linearLayout_loading,linearLayout_delete;
    private CToast cToast;
    private View view;
    private ArrayList<CloudRoom.DataEntity> arrayList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragmentcloudroom,null);
        return view;
    }
    public void initData(View view){
        LogUtil.i("FragmentCloudRoom initData start.","");
        index = 1;
        hashMap = new HashMap<Integer, Rooms>();
        imageloderinit = new Imageloderinit(getActivity());
        try{
            MyThreadPoolManager.getInstance().execute(runnable);
        }catch (Exception e){
            LogUtil.e("FragmentCloudRoom initData error:",e.toString());
        }finally {
            LogUtil.i("FragmentCloudRoom initData end.","");
        }
        cloudRoom = new CloudRoom();
        arrayList = new ArrayList<CloudRoom.DataEntity>();
    }

    @Override
    public void onResume() {
        //初始化控件
        initView(view);
        //初始化数据
        initData(view);
        super.onResume();

    }

    /**
     * 获取房间数据
     */
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            String uid = SaveShared.uid(getActivity());
            String token = SaveShared.tokenget(getActivity());
            cloudRoom = HttpTransfeData.couldroomdata(getActivity(),uid,token,index);
            if (cloudRoom.data.size()>0){
                arrayList.addAll(cloudRoom.data);
            }
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("room", arrayList);
            bundle.putParcelable("state",cloudRoom);
            message.what = Numbers.ONE;
            message.setData(bundle);
            handler.sendMessage(message);
        }
    };
    //popup弹框修改或删除商品
    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    private void apopupwonds(View v, boolean flag, final String tag, final String pid_ol, final String gid_ol) {
        popupWindow = new PopupWindow();
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.adddeletepoplayout,null);
        popupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(view);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        button = (Button)view.findViewById(R.id.button_adddeletepop_add);
        button_ol = (Button)view.findViewById(R.id.button_adddeletepop_delete);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                tag_ol = tag;
                pid = pid_ol;
                gid = tag_ol;
                SharePreferencesUlits.saveString(getActivity(),Constants.ROOMID,tag_ol);
                intent.setClass(getActivity(), ActivityCreateRoom.class);
                startActivity(intent);
                popupWindow.dismiss();
            }
        });
        button_ol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tag_ol = tag;
                popupWindow.dismiss();
                MyThreadPoolManager.getInstance().execute(runnableDeleteGoods);
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

    /**
     * 删除数据
     */
    private Runnable runnableDeleteGoods = new Runnable() {
        @Override
        public synchronized void run() {
            String token = SaveShared.tokenget(getActivity());
            String uid = SaveShared.uid(getActivity());
            statese = new Statese();
            statese = HttpTransfeData.deleteroomhttp(getActivity(),tag_ol,pid,gid,token,uid);
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putParcelable("state",statese);
            message.what = Numbers.THREE;
            message.setData(bundle);
            handler.sendMessage(message);
        }
    };
    /**
     * 初始化数据
     * @param view
     */
    public void initView(View view){
        linearLayout_delete = (LinearLayout)view.findViewById(R.id.linearlayout_shoppingdateil_delete);
        linearLayout_loading = (LinearLayout)view.findViewById(R.id.linearlayout_shoppingdateil_loading);
        linearLayout_loading.setVisibility(View.VISIBLE);
        layout = (PullToRefreshLayout)view.findViewById(R.id.layout_new_room);
        listView = (ListView)view.findViewById(R.id.listView_cloudroom);
        listView.setOnScrollListener(new PauseOnScrollListener(imageloderinit.imageLoader,true,false));
        imageView = (ImageView)view.findViewById(R.id.iamgeveiw_cloudroom);
        layout.setOnRefreshListener(this);
    }

    //界面数据刷新
    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    linearLayout_loading.setVisibility(View.GONE);
//                    if (arrayList_ol != null){
                    cloudRoom = msg.getData().getParcelable("state");
                    arrayList = msg.getData().getParcelableArrayList("room");
//                    }else {
//                        Toast.makeText(getActivity(),"无数据链接",Toast.LENGTH_LONG).show();
//                    }
                    if (cloudRoom.state == 1) {
                        if (arrayList != null) {
                            if (arrayList.size() > 0) {
                                linearLayout_delete.setVisibility(View.GONE);
                                AdapterCloudRoom.CloudOnClickListener cloudOnClickListener = new AdapterCloudRoom.CloudOnClickListener() {
                                    @Override
                                    protected void cloudOnClickListener(Integer tag, View v) {
                                        Intent intent = new Intent();
                                        switch (v.getId()) {
                                            case R.id.button_yaoqinghaoyou:
                                                if (!arrayList.get(tag).LeftCount.equals("null")) {
                                                    if (Integer.parseInt(arrayList.get(tag).LeftCount) > 0) {
                                                        intent.putExtra("roomid", arrayList.get(tag).RID);
                                                        intent.putExtra("cuser", arrayList.get(tag).CreateUserID);
                                                        intent.setClass(getActivity(), ActivityInvite.class);
                                                        startActivity(intent);
                                                        // finish();
                                                    } else {
                                                        cToast = CToast.makeText(getActivity(), "没有更多的商品了~", 600);
                                                        cToast.show();
                                                    }
                                                } else {
                                                    cToast = CToast.makeText(getActivity(), "没有更多的商品了~", 600);
                                                    cToast.show();
                                                }
                                                break;
                                            case R.id.button_start:
                                                String token = SaveShared.tokenget(getActivity());
                                                if (!StringUtils.isEmpty(token)) {
                                                    linearLayout_loading.setVisibility(View.VISIBLE);
                                                    pid = arrayList.get(tag).ID;
                                                    limitcount = arrayList.get(tag).LimitCount;
                                                    surpcount = arrayList.get(tag).LeftCount;
                                                    MyThreadPoolManager.getInstance().execute(runnableGoodsNumber);
                                                } else {
                                                    cToast = CToast.makeText(getActivity(), "您尚未登录请登录", 600);
                                                    cToast.show();
                                                }
                                                break;
                                            case R.id.imagebutton_add_delete:
                                                flag = true;
                                                delete = tag;
                                                apopupwonds(v, flag, arrayList.get(tag).RID, arrayList.get(tag).ProductID, arrayList.get(tag).ID);
                                                backgroundAlpha(0.5f);
                                                break;
                                        }
                                    }
                                };
                                String uid = SaveShared.uid(getActivity());
                                adapterCloudRoom = new AdapterCloudRoom(uid, getActivity(), arrayList, cloudOnClickListener, imageloderinit.imageLoader, Imageloderinit.options);
                                listView.setAdapter(adapterCloudRoom);
                            } else {
                                linearLayout_delete.setVisibility(View.VISIBLE);
                            }
                        }else {
                            linearLayout_delete.setVisibility(View.VISIBLE);
                        }
                    } else {
                        if (cloudRoom.msg.equals("token失效")){
                            MyThreadPoolManager.getInstance().execute(runnableSingle);
                        }
                    }

                    break;
                case 2:
                    statese = new Statese();
                    statese = msg.getData().getParcelable("state");
                    if (statese != null){
                        if (statese.getState().equals(String.valueOf(Numbers.ONE))){
                            if (statese.getState().equals(String.valueOf(Numbers.ONE))){
                                MyThreadPoolManager.getInstance().execute(runnableCarNumber);
                            }else {
                                cToast = CToast.makeText(getActivity(),statese.getMsg(),600);
                                cToast.show();
                            }
                        }
                    }
                    break;
                case 3:
                    statese = new Statese();
                    statese = msg.getData().getParcelable("state");
                    if (statese != null){
                        if (statese.getState().equals(String.valueOf(Numbers.ONE))){
                            arrayList.clear();
                            MyThreadPoolManager.getInstance().execute(runnable);
                            cToast = CToast.makeText(getActivity(),statese.getMsg(),600);
                            cToast.show();
                        }else {
                            cToast = CToast.makeText(getActivity(),statese.getMsg(),600);
                            cToast.show();
                        }
                    }
                    break;
                case 4:
                    linearLayout_loading.setVisibility(View.GONE);
                    statese = new Statese();
                    statese = msg.getData().getParcelable("content_number");
                    if (statese != null){
                        if (statese.getState() != null){
                            if (statese.getState().equals(String.valueOf(Numbers.ONE))){
                                if (!limitcount.equals("0")){
                                    if (Integer.parseInt(limitcount)>0){
                                        if (Integer.parseInt(limitcount)<Integer.parseInt(surpcount)){
                                            if (Integer.parseInt(statese.getData())<Integer.parseInt(limitcount)){
                                                MyThreadPoolManager.getInstance().execute(runnableAddCar);
                                            }else {
//                                                linearLayout_ol.setVisibility(View.GONE);
                                                cToast = CToast.makeText(getActivity(),"没有更多商品了~",600);
                                                cToast.show();                                            }
                                        }else {
//                                            linearLayout_ol.setVisibility(View.GONE);
                                            cToast = CToast.makeText(getActivity(),"没有更多商品了~",600);
                                            cToast.show();                                        }
                                    }
                                }else {
                                    if (Integer.parseInt(statese.getData())<Integer.parseInt(surpcount)){
                                        MyThreadPoolManager.getInstance().execute(runnableAddCar);
                                    }else {
//                                        linearLayout_ol.setVisibility(View.GONE);
                                        cToast = CToast.makeText(getActivity(),"没有更多商品了~",600);
                                        cToast.show();
                                    }
                                }
                            }else {
                                if (cloudRoom.msg.equals("token失效")){
                                    if (!StringUtils.isEmpty(SharePreferencesUlits.getString(getActivity(),Constants.PHONE,Constants.NUll_VALUE))){
                                        MyThreadPoolManager.getInstance().execute(runnableSingle);
                                    }
                                }
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
                                SharePreferencesUlits.saveString(getActivity(),Constants.CAR_NUMBER,statese.getData());
                                MyActivity.textView.setText(statese.getData());
                            }
                            MyActivity.radioButton_one.setChecked(true);
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
     * 重新登陆
     */
    private Runnable runnableSingle = new Runnable() {
        @Override
        public void run() {
            statese = new Statese();
            String user_ol = SaveShared.loadong_name(getActivity());
            String pass = SaveShared.loading_pass(getActivity());
            statese = HttpTransfeData.httppostloding(getActivity(), user_ol, pass);
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
        public synchronized void run() {
            try {
                statese = new Statese();
                String token = SaveShared.tokenget(getActivity());
                statese = HttpTransfeData.getcarnumberhttp(statese, getActivity(), token);
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
    /**
     * 获取购物车单个商品数量
     */
    private Runnable runnableGoodsNumber = new Runnable() {
        @Override
        public synchronized void run() {
            statese = new Statese();
            String token = SaveShared.tokenget(getActivity());
            statese = HttpTransfeData.httpshoppingcat(getActivity(),statese,token,pid);
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putParcelable("content_number",statese);
            message.setData(bundle);
            message.what = Numbers.FOUR;
            handler.sendMessage(message);
        }
    };
    //添加商品请求
    private Runnable runnableAddCar = new Runnable() {
        @Override
        public synchronized void run() {
            statese = new Statese();
            String token = SaveShared.tokenget(getActivity());
            String uid = SaveShared.uid(getActivity());
            String num = "1";
            statese = HttpTransfeData.goodsaddshoppingcat(getActivity(),pid,uid,token,num);
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putParcelable("state",statese);
            message.what = Numbers.TWO;
            message.setData(bundle);
            handler.sendMessage(message);
        }
    };
    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                arrayList = new ArrayList<CloudRoom.DataEntity>();
                index = 1;
                MyThreadPoolManager.getInstance().execute(runnable);
                layout.refreshFinish(PullToRefreshLayout.SUCCEED);
            }
        }.sendEmptyMessageDelayed(0,1500);
    }
    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                index++;
                MyThreadPoolManager.getInstance().execute(runnable);
                layout.refreshFinish(PullToRefreshLayout.SUCCEED);
            }
        }.sendEmptyMessageDelayed(0,1500);
    }
    //设置弹框后主屏幕背景色
    public void backgroundAlpha(float bgAlpha)
    {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha;
        getActivity().getWindow().setAttributes(lp);
    }
    //销毁线程
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
