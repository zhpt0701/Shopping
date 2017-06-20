package com.example.cloudAndPurchasing.fragment.fragmentcloud;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.*;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.*;
import android.widget.*;
import com.example.cloudAndPurchasing.MyActivity;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.activity.activityshopping.ActivityGoodsDateil;
import com.example.cloudAndPurchasing.adapter.adaptercloud.AdapterOngoing;
import com.example.cloudAndPurchasing.adapter.adaptersurpired.AdapterWinningPopup;
import com.example.cloudAndPurchasing.customcontrol.listviewupdown.PullToRefreshLayout;
import com.example.cloudAndPurchasing.http.HttpTransfeData;
import com.example.cloudAndPurchasing.http.Numbers;
import com.example.cloudAndPurchasing.http.SaveShared;
import com.example.cloudAndPurchasing.imageloder.Imageloderinit;
import com.example.cloudAndPurchasing.kind.Count;
import com.example.cloudAndPurchasing.kind.PurchaseDateil;
import com.example.cloudAndPurchasing.kind.Statese;
import com.example.cloudAndPurchasing.manager.MyThreadPoolManager;
import com.example.cloudAndPurchasing.zhi.LogUtil;
import com.example.cloudAndPurchasing.zhi.ValuePrice;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/1 0001.
 */
public class FragmentOngoing extends Fragment implements PullToRefreshLayout.OnRefreshListener {
    private ListView listView;
    private AdapterOngoing adapterOngoing;
    private PullToRefreshLayout pullToRefreshLayout,pullToRefreshLayout1;
    private ArrayList<PurchaseDateil> arrayList,purchaseDateils;
    public static int index = 1,index_ol = 1;
    private boolean falge = false;
    private GridView gridView;
    private TextView textView;
    private Button button;
    private PopupWindow popupWindow;
    private AdapterWinningPopup adapterPopup;
    private SharedPreferences sharedPreferences;
    private Imageloderinit imageloderinit;
    private View view;
    private int tradingcount = 0;
    private String pid = null,id = null;
    private Statese statese;
    private LinearLayout linearLayout_loading,linearLayout_delete;
    private ArrayList<Count> arrayList_ol,arrayList_ol1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
         view = inflater.inflate(R.layout.fragmentongindsitem,null);
        //初始化数据
        initData();
        //初始化控件
        initview(view);
        return view;
    }
    public void initData(){
        LogUtil.i("FragmentOnging initData start.","");
        index = 1;
        index_ol = 1;
        arrayList_ol1 = new ArrayList<Count>();
        purchaseDateils = new ArrayList<PurchaseDateil>();
        imageloderinit = new Imageloderinit(getActivity());
        sharedPreferences = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        arrayList = new ArrayList<PurchaseDateil>();
        try {
            MyThreadPoolManager.getInstance().execute(runnable);
        }catch (Exception e){
            LogUtil.e("FragmentOnging initData error:",e.toString());
        }finally {
            LogUtil.i("FragmentOnging initData end.","");
        }
    }
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (getActivity() != null){
                arrayList = new ArrayList<PurchaseDateil>();
                String token = SaveShared.tokenget(getActivity());
                String state= "1";
                String uid = SaveShared.uid(getActivity());
                arrayList = HttpTransfeData.couldshoppinghttp(getActivity(),token,index,state,uid);
                purchaseDateils.addAll(arrayList);
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("content",purchaseDateils);
                message.what = Numbers.ONE;
                message.setData(bundle);
                handlerUi.sendMessage(message);
            }

        }
    };
    private Handler handlerUi = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    linearLayout_loading.setVisibility(View.GONE);
                    arrayList = msg.getData().getParcelableArrayList("content");
                    if (arrayList != null){
                        if (arrayList.size()>0){
                            linearLayout_delete.setVisibility(View.GONE);
                            AdapterOngoing.CatOnclicklistner catOnclicklistner = new AdapterOngoing.CatOnclicklistner() {
                                @Override
                                protected void catOnclicklistner(Integer tag, View v) {
                                    switch (v.getId()){
                                        case R.id.button_select_ol:
                                            arrayList_ol1 = new ArrayList<Count>();
                                            falge = true;
                                            tradingcount = arrayList.get(tag).getTotalCount()-arrayList.get(tag).getSurpluspople();
                                            pid = arrayList.get(tag).getID();
                                            initpopupwind(falge);
                                            backgroungauth(0.5f);
                                            break;
                                        case R.id.button_add_cat:
                                            id = arrayList.get(tag).getID();
                                            Intent intent = new Intent();
                                            intent.putExtra("outid",id);
                                            intent.putExtra("cloud",String.valueOf(Numbers.FOUR));
                                            intent.putExtra("index" ,Numbers.FOUR);
                                            intent.setClass(getActivity(), ActivityGoodsDateil.class);
                                            startActivity(intent);
                                            break;
                                    }
                                }
                            };
                            adapterOngoing = new AdapterOngoing(imageloderinit.imageLoader,imageloderinit.options,getActivity(),arrayList,catOnclicklistner);
                            listView.setAdapter(adapterOngoing);
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Intent intent = new Intent();
                                    intent.putExtra("outid",arrayList.get(position).getID());
                                    intent.putExtra("cloud",String.valueOf(Numbers.FOUR));
                                    intent.putExtra("index" ,Numbers.FOUR);
                                    intent.setClass(getActivity(), ActivityGoodsDateil.class);
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
                    linearLayout_loading.setVisibility(View.GONE);
                    arrayList_ol = msg.getData().getParcelableArrayList("content_ol");
                    if (arrayList_ol!=null){
                        if (arrayList_ol.size()>0){
                            adapterPopup = new AdapterWinningPopup(getActivity(),arrayList_ol);
                            gridView.setAdapter(adapterPopup);
                        }

                    }
                    break;
                case 3:
                    linearLayout_loading.setVisibility(View.GONE);
                    statese = msg.getData().getParcelable("state1");
                    if (statese!=null){
                        if (statese.getState().equals(String.valueOf(Numbers.ONE))){
                            Intent intent = new Intent();
                            intent.putExtra("zhi", ValuePrice.VALUE_FOUR);
                            intent.setClass(getActivity(),MyActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        }
                        Toast.makeText(getActivity(),statese.getMsg(),Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void initpopupwind(boolean falge) {
        popupWindow = new PopupWindow();
        View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.numberlayout,null);
        popupWindow.setContentView(view1);
        popupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        gridView = (GridView)view1.findViewById(R.id.gridview_luckynunber_ol);
        button = (Button)view1.findViewById(R.id.button_luck_number_this);
        textView = (TextView)view1.findViewById(R.id.textview_luckynumber_sum);
        SpannableString style_OL = new SpannableString("本期参与"+tradingcount+"人次");
        style_OL.setSpan(new ForegroundColorSpan(Color.RED), 4, 4+String.valueOf(tradingcount).length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //设置指定位置文字的颜色
        textView.setText(style_OL);
        pullToRefreshLayout1 = (PullToRefreshLayout)view1.findViewById(R.id.popuplayout_updata);
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
                backgroungauth(1f);
            }
        });
        if (falge){
            popupWindow.showAtLocation(view1, Gravity.CENTER,0,0);
        }
    }

    /**
     * 开启线程获取网络数据
     */
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
     * 初始化控件
     * @param view
     */
    public void initview(View view){
        linearLayout_loading = (LinearLayout)view.findViewById(R.id.linearlayout_shoppingdateil_loading);
        linearLayout_delete = (LinearLayout)view.findViewById(R.id.linearlayout_shoppingdateil_delete);
        linearLayout_loading.setVisibility(View.VISIBLE);
        listView = (ListView)view.findViewById(R.id.listview_ongoing);
        pullToRefreshLayout = (PullToRefreshLayout)view.findViewById(R.id.pulllayout_oping);
        listView.setOnScrollListener(new PauseOnScrollListener(imageloderinit.imageLoader,true,false));
        pullToRefreshLayout.setOnRefreshListener(this);
    }

    /**
     * 设置背景颜色
     * @param v
     */
    private void backgroungauth(float v) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = v;
        getActivity().getWindow().setAttributes(lp);
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
                index = 1;
                purchaseDateils = new ArrayList<PurchaseDateil>();
                MyThreadPoolManager.getInstance().execute(runnable);
                pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
            }
        }.sendEmptyMessageDelayed(0,1500);
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
        }.sendEmptyMessageDelayed(0,1500);
    }

    @Override
    public void onDestroy() {
        if (arrayList_ol != null){
            arrayList_ol.clear();
        }
        if (arrayList != null){
            arrayList.clear();
        }
        if (arrayList_ol1 != null){
            arrayList_ol1.clear();
        }
        if (purchaseDateils != null){
            purchaseDateils.clear();
        }
        super.onDestroy();
    }
}
