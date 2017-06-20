package com.example.cloudAndPurchasing.fragment.fragmentsurpried;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.*;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.*;
import android.widget.*;
import com.example.cloudAndPurchasing.MyActivity;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.activity.ActivityShoppingdetail;
import com.example.cloudAndPurchasing.activity.activityshopping.ActivityGoodsDateil;
import com.example.cloudAndPurchasing.adapter.adaptersurpired.AdapterParticipation;
import com.example.cloudAndPurchasing.adapter.adaptersurpired.AdapterWinningPopup;
import com.example.cloudAndPurchasing.customcontrol.listviewupdown.PullToRefreshLayout;
import com.example.cloudAndPurchasing.http.HttpTransfeData;
import com.example.cloudAndPurchasing.http.Numbers;
import com.example.cloudAndPurchasing.http.SaveShared;
import com.example.cloudAndPurchasing.imageloder.Imageloderinit;
import com.example.cloudAndPurchasing.imageloder.Myimageloder;
import com.example.cloudAndPurchasing.kind.Count;
import com.example.cloudAndPurchasing.kind.PurchaseDateil;
import com.example.cloudAndPurchasing.kind.Statese;
import com.example.cloudAndPurchasing.manager.MyThreadPoolManager;
import com.example.cloudAndPurchasing.zhi.LogUtil;
import com.example.cloudAndPurchasing.zhi.ValuePrice;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/12 0012.
 */
public class FragmentParticipation extends Fragment implements PullToRefreshLayout.OnRefreshListener {
    private ListView listView;
    private PullToRefreshLayout pullToRefreshLayout;
    private AdapterParticipation adapterParticipation;
    private ArrayList<PurchaseDateil> arrayList,purchaseDateils;
    private Imageloderinit imageloderinit ;
    private GridView gridView;
    private Button button;
    private TextView textView;
    private int index_ol = 1,toaltcount = 0;
    private String pid = null,id = null;
    private boolean falge = false;
    private PullToRefreshLayout pullToRefreshLayout1;
    private PopupWindow popupWindow;
    private AdapterWinningPopup adapterPopup;
    private int index=1;
    private LinearLayout linearLayout_loading,linearLayout_delete;
    private Statese statese;
    private ArrayList<Count> arrayList_ol,arrayList_ol1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragmentparticitionlayout,null);

        //初始化数据
        initData();
        //初始化控件
        initView(view);
        return view;
    }
    public void initData(){
        LogUtil.i("FragmentParticipation initData start.", "");
        purchaseDateils = new ArrayList<PurchaseDateil>();
        index = 1;
        imageloderinit = new Imageloderinit(getActivity());
        try {
            MyThreadPoolManager.getInstance().execute(runnable);
        }catch (Exception e){
           LogUtil.e("FragmentParticipation initData error:",e.toString());
        }finally {
            LogUtil.i("FragmentParticipation initdata end.","");
        }
        arrayList = new ArrayList<PurchaseDateil>();

    }
    //初始化控件
    public void initView(View v){
        linearLayout_loading = (LinearLayout)v.findViewById(R.id.linearlayout_shoppingdateil_loading);
        linearLayout_delete = (LinearLayout)v.findViewById(R.id.linearlayout_shoppingdateil_delete);
        linearLayout_loading.setVisibility(View.VISIBLE);
        pullToRefreshLayout = (PullToRefreshLayout)v.findViewById(R.id.pulllayout_partion);
        listView = (ListView)v.findViewById(R.id.listview_participation);
        listView.setOnScrollListener(new PauseOnScrollListener(imageloderinit.imageLoader,true,false));
        listView.setOnScrollListener(new PauseOnScrollListener(imageloderinit.imageLoader,true,false));
        pullToRefreshLayout.setOnRefreshListener(this);
    }
    //http网络数据请求
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (getActivity() != null){
                String token = SaveShared.tokenget(getActivity());
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
                String uid = sharedPreferences.getString("fid","");
                arrayList = HttpTransfeData.httpwho(getActivity(),token,uid,index);
                if (arrayList != null){
                    purchaseDateils.addAll(arrayList);
                }
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("array",purchaseDateils);
                message.what = Numbers.ONE;
                message.setData(bundle);
                handlerUi.sendMessage(message);
            }

        }
    };
    //handkerui界面刷新
    private Handler handlerUi = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    linearLayout_loading.setVisibility(View.GONE);
                    arrayList = msg.getData().getParcelableArrayList("array");
                    if (arrayList != null){
                        if (arrayList.size()>0){
                            linearLayout_delete.setVisibility(View.GONE);
                            AdapterParticipation.ParticiationOnClickListener particiationOnClickListener = new AdapterParticipation.ParticiationOnClickListener() {
                                @Override
                                protected void particiationOnClickListener(Integer tag, View v) {
                                    switch (v.getId()){
                                        case R.id.button_partiction_cat:
                                            id = arrayList.get(tag).getID();
                                            Intent intent = new Intent();
                                            intent.putExtra("outid",id);
                                            intent.putExtra("cloud", String.valueOf(Numbers.SIX));
                                            intent.setClass(getActivity(), ActivityGoodsDateil.class);
                                            startActivity(intent);
                                            break;
                                        case R.id.button_partiction_select:
                                            arrayList_ol1 = new ArrayList<Count>();
                                            falge = true;
                                            pid = arrayList.get(tag).getID();
                                            toaltcount = arrayList.get(tag).getTotalCount()-arrayList.get(tag).getSurpluspople();
                                            backgroungauth(0.5f);
                                            initpopupwind(falge);
                                            break;
                                    }
                                }
                            };
                            adapterParticipation = new AdapterParticipation(imageloderinit.imageLoader,imageloderinit.options,getActivity(),arrayList,particiationOnClickListener);
                            listView.setAdapter(adapterParticipation);
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                                    Intent intent = new Intent();
//                                    if (arrayList.get(position).getWinner() != "null"){
//                                        intent.putExtra("th_ol",arrayList.get(position).getID());
//                                        intent.putExtra("back",String.valueOf(Numbers.SIX));
//                                        intent.setClass(getActivity(), ActivityShoppingdetail.class);
//                                    }else {
//                                        intent.putExtra("outid",arrayList.get(position).getID());
//                                        intent.putExtra("cloud_back",String.valueOf(Numbers.SIX));
//                                        intent.setClass(getActivity(), ActivityGoodsDateil.class);
//                                    }
//                                    startActivity(intent);
//                                    getActivity().finish();
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
                    arrayList_ol = msg.getData().getParcelableArrayList("contentLuckNumber");
                    if (arrayList_ol != null){
                        adapterPopup = new AdapterWinningPopup(getActivity(),arrayList_ol);
                        gridView.setAdapter(adapterPopup);
                    }
                    break;
                case 3:
                    statese = msg.getData().getParcelable("state1");
                    if (statese != null){
                        if (statese.getState() == "1"){
                            Intent intent = new Intent();
                            intent.putExtra("zhi", ValuePrice.VALUE_FOUR);
                            intent.setClass(getActivity(),MyActivity.class);
                            startActivity(intent);
                        }
                        Toast.makeText(getActivity(),statese.getMsg(),Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };
    //下拉刷新
    @Override
    public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
        new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                purchaseDateils = new ArrayList<PurchaseDateil>();
                index++;
                MyThreadPoolManager.getInstance().execute(runnable);
                pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
            }
        }.sendEmptyMessageDelayed(0,2000);
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
        }.sendEmptyMessageDelayed(0,2000);
    }
    /**
     * 夺宝号弹框显示
     * @param falge1
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void initpopupwind(boolean falge1) {
        popupWindow = new PopupWindow();
        View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.numberlayout,null);
        popupWindow.setContentView(view1);
        popupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        gridView = (GridView)view1.findViewById(R.id.gridview_luckynunber_ol);
        button = (Button)view1.findViewById(R.id.button_luck_number_this);
        textView = (TextView)view1.findViewById(R.id.textview_luckynumber_sum);
        pullToRefreshLayout1 = (PullToRefreshLayout)view1.findViewById(R.id.popuplayout_updata);
        SpannableString style_OL = new SpannableString("本期参与"+String.valueOf(toaltcount)+"人次");
        style_OL.setSpan(new ForegroundColorSpan(Color.RED), 4, 4+String.valueOf(toaltcount).length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //设置指定位置文字的颜色
        textView.setText(style_OL);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        pullToRefreshLayout1.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
                new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        arrayList_ol1 = new ArrayList<Count>();
                        index_ol = 1;
                        MyThreadPoolManager.getInstance().execute(runnableLuckNumber);
                        pullToRefreshLayout1.refreshFinish(PullToRefreshLayout.SUCCEED);
                    }
                }.sendEmptyMessageDelayed(0,1500);
            }

            @Override
            public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
                new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        index_ol++;
                        MyThreadPoolManager.getInstance().execute(runnableLuckNumber);
                        pullToRefreshLayout1.refreshFinish(PullToRefreshLayout.SUCCEED);
                    }
                }.sendEmptyMessageDelayed(0,1500);
            }
        });
        MyThreadPoolManager.getInstance().execute(runnableLuckNumber);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                popupWindow.dismiss();
                backgroungauth(1f);
            }
        });
        if (falge){
            popupWindow.showAtLocation(view1, Gravity.CENTER, 0, 0);
        }

    }
    //http网络获取幸运号
    private Runnable runnableLuckNumber = new Runnable() {
        @Override
        public void run() {
            arrayList_ol = new ArrayList<Count>();
            arrayList_ol = HttpTransfeData.lucknumberhttp(getActivity(),arrayList_ol,pid,index_ol);
            arrayList_ol1.addAll(arrayList_ol);
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("contentLuckNumber",arrayList_ol1);
            message.setData(bundle);
            message.what = Numbers.TWO;
            handlerUi.sendMessage(message);
        }
    };
    /**
     * 弹框改变背景色
     * @param v
     */
    private void backgroungauth(float v) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = v;
        getActivity().getWindow().setAttributes(lp);
    }
    @Override
    public void onDestroy() {
        if (arrayList != null){
            arrayList.clear();
        }
        if (purchaseDateils != null){
            purchaseDateils.clear();
        }
        super.onDestroy();
    }
}
