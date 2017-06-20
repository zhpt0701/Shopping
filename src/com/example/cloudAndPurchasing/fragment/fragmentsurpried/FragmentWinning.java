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
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.activity.ActivityShoppingdetail;
import com.example.cloudAndPurchasing.activity.activityshopping.ActivityGoodsDateil;
import com.example.cloudAndPurchasing.adapter.adaptersurpired.AdapterSelectWinning;
import com.example.cloudAndPurchasing.adapter.adaptersurpired.AdapterWinningPopup;
import com.example.cloudAndPurchasing.customcontrol.listviewupdown.PullToRefreshLayout;
import com.example.cloudAndPurchasing.http.HttpTransfeData;
import com.example.cloudAndPurchasing.http.Numbers;
import com.example.cloudAndPurchasing.http.SaveShared;
import com.example.cloudAndPurchasing.imageloder.Imageloderinit;
import com.example.cloudAndPurchasing.kind.Count;
import com.example.cloudAndPurchasing.kind.PurchaseDateil;
import com.example.cloudAndPurchasing.kind.Winning;
import com.example.cloudAndPurchasing.manager.MyThreadPoolManager;
import com.example.cloudAndPurchasing.popupwindows.WinningPopupWindows;
import com.example.cloudAndPurchasing.zhi.LogUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/12 0012.
 */
public class FragmentWinning extends Fragment implements PullToRefreshLayout.OnRefreshListener {
    private ListView listView;
    private ArrayList<Winning> arrayList,winnings;
    private AdapterSelectWinning adapterSelectWinning;
    private Imageloderinit imageloderinit;
    private PullToRefreshLayout pullToRefreshLayout1;
    private int index = 1,index_ol = 1;
    private boolean falge = false;
    private String uid = null;
    private GridView gridView;
    private Button button;
    private TextView textView;
    private PopupWindow popupWindow;
    private AdapterWinningPopupol adapterPopup;
    private String pid = null;
    private String toaltcount = null;
    private ArrayList<Count> arrayList_ol,arrayList_ol1;
    private PullToRefreshLayout pullToRefreshLayout;
    private String name = null,lucknumber = null;
    private LinearLayout linearLayout_ol,linearLayout_loading,linearLayout_delete;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragmentwinninglayout,null);
        //初始化数据
        initData();
        //初始化控件
        initView(view);
        return view;
    }
    public void initData(){
        LogUtil.i("FragmentWinniung initData start.","");
        winnings = new ArrayList<Winning>();
        index = 1;
        index_ol = 1;
        imageloderinit = new Imageloderinit(getActivity());
        arrayList = new ArrayList<Winning>();
        try{
            MyThreadPoolManager.getInstance().execute(runnable);
        }catch (Exception e){
            LogUtil.e("FragmentWinning initData error:",e.toString());
        }finally {
            LogUtil.i("FragmentWinning initData end.","");
        }
    }
    public void initView(View v){
        linearLayout_loading = (LinearLayout)v.findViewById(R.id.linearlayout_shoppingdateil_loading);
        linearLayout_delete = (LinearLayout)v.findViewById(R.id.linearlayout_shoppingdateil_delete);
        linearLayout_loading.setVisibility(View.VISIBLE);
        pullToRefreshLayout = (PullToRefreshLayout)v.findViewById(R.id.pulllayout_winnering);
        listView = (ListView)v.findViewById(R.id.listview_winning);
        listView.setOnScrollListener(new PauseOnScrollListener(imageloderinit.imageLoader,true,false));
        pullToRefreshLayout.setOnRefreshListener(this);
    }
    //http中奖数据获取
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (!getActivity().equals(null)){
                String token = SaveShared.tokenget(getActivity());
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
                String uid = sharedPreferences.getString("fid","");
                arrayList = HttpTransfeData.winninghttp(token,uid,getActivity(), index);
                if (arrayList != null){
                    winnings.addAll(arrayList);
                }
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("array",winnings);
                message.what = Numbers.ONE;
                message.setData(bundle);
                handlerUi.sendMessage(message);
            }
        }
    };
    //主界面更新
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
                            AdapterSelectWinning.SwOnClickListener swOnClickListener = new AdapterSelectWinning.SwOnClickListener() {
                                @Override
                                protected void swOnClickListener(Integer tag, View v) {
                                    switch (v.getId()){
                                        case R.id.button_swselect:
//                                        WinningPopupWindows winningPopupWindows = new WinningPopupWindows(getActivity());
//                                        winningPopupWindows.show(v);
                                            arrayList_ol1 = new ArrayList<Count>();
                                            falge = true;
                                            pid = arrayList.get(tag).getID();
                                            toaltcount = arrayList.get(tag).getTotalCount();
                                            lucknumber = arrayList.get(tag).getWinningluckCode();
                                            backgroungauth(0.5f);
                                            initpopupwind(falge);
                                            break;
                                    }
                                }
                            };
                            if(getActivity() != null){
                                name = getActivity().getIntent().getStringExtra("name");
                            }
                            adapterSelectWinning = new AdapterSelectWinning(imageloderinit.imageLoader,imageloderinit.options,name,getActivity(),arrayList,swOnClickListener);
                            listView.setAdapter(adapterSelectWinning);
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                                    Intent intent = new Intent();
//                                    intent.putExtra("th_ol",arrayList.get(position).getID());
//                                    intent.putExtra("back",String.valueOf(Numbers.EIGHT));
//                                    intent.setClass(getActivity(), ActivityShoppingdetail.class);
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
                    linearLayout_ol.setVisibility(View.GONE);
                    arrayList_ol = msg.getData().getParcelableArrayList("content_ol");
                    if (arrayList_ol!=null){
                        adapterPopup = new AdapterWinningPopupol(getActivity(),arrayList_ol,lucknumber);
                        gridView.setAdapter(adapterPopup);
                    }
                    break;
            }
        }
    };

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
        linearLayout_ol = (LinearLayout)view1.findViewById(R.id.linearlayout_this_lucknumber);
        linearLayout_ol.setVisibility(View.VISIBLE);
        pullToRefreshLayout1 = (PullToRefreshLayout)view1.findViewById(R.id.popuplayout_updata);
        SpannableString style_OL = new SpannableString("本期参与"+toaltcount+"人次");
        style_OL.setSpan(new ForegroundColorSpan(Color.RED), 4, 4 + toaltcount.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //设置指定位置文字的颜色
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
                new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        arrayList_ol1 = new ArrayList<Count>();
                        index_ol = 1;
                        MyThreadPoolManager.getInstance().execute(runnable1);
                        pullToRefreshLayout1.refreshFinish(PullToRefreshLayout.SUCCEED);
                    }
                }.sendEmptyMessageDelayed(0, 1500);
            }

            @Override
            public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
                new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        index_ol++;
                        MyThreadPoolManager.getInstance().execute(runnable1);
                        pullToRefreshLayout1.refreshFinish(PullToRefreshLayout.SUCCEED);
                    }
                }.sendEmptyMessageDelayed(0, 1500);
            }
        });
        MyThreadPoolManager.getInstance().execute(runnable1);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                popupWindow.dismiss();
                backgroungauth(1f);
            }
        });
        if (falge){
            Log.i(getActivity() + "", "8384023492" + falge1);
            popupWindow.showAtLocation(view1, Gravity.CENTER, 0, 0);
        }

    }
    private Runnable runnable1 = new Runnable() {
        @Override
        public void run() {
            arrayList_ol = new ArrayList<Count>();
            arrayList_ol = HttpTransfeData.lucknumberhttp(getActivity(),arrayList_ol,pid,index_ol);
            arrayList_ol1.addAll(arrayList_ol);
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("content_ol",arrayList_ol1);
            message.setData(bundle);
            message.what = Numbers.TWO;
            handlerUi.sendMessage(message);
        }
    };
    /**
     * 下拉刷新
     * @param pullToRefreshLayout
     */
    @Override
    public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
        new Handler(){
            @Override
            public void handleMessage(Message msg) {
                winnings = new ArrayList<Winning>();
                index = 1;
                MyThreadPoolManager.getInstance().execute(runnable);
                pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
            }
        }.sendEmptyMessageDelayed(0,1500);
    }
    /**
     * 弹框改变背景色
     * @param v
     */
    private void backgroungauth(float v) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = v;
        getActivity().getWindow().setAttributes(lp);
    }
    /**
     * 上拉加载
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
        }.sendEmptyMessageDelayed(0,1500);
    }

    @Override
    public void onDestroy() {
        if (arrayList != null){
            arrayList.clear();
        }
        if (arrayList_ol != null){
            arrayList_ol.clear();
        }
        if (winnings != null){
            winnings.clear();
        }
        super.onDestroy();
    }
}
