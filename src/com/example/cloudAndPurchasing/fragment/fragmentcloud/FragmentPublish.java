package com.example.cloudAndPurchasing.fragment.fragmentcloud;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.*;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.activity.ActivityShoppingdetail;
import com.example.cloudAndPurchasing.adapter.adaptercloud.AdapterOngoing;
import com.example.cloudAndPurchasing.adapter.adaptercloud.AdapterPublish;
import com.example.cloudAndPurchasing.adapter.adaptersurpired.AdapterParticipation;
import com.example.cloudAndPurchasing.customcontrol.listviewupdown.PullToRefreshLayout;
import com.example.cloudAndPurchasing.http.HttpTransfeData;
import com.example.cloudAndPurchasing.http.Numbers;
import com.example.cloudAndPurchasing.http.SaveShared;
import com.example.cloudAndPurchasing.imageloder.Imageloderinit;
import com.example.cloudAndPurchasing.kind.PurchaseDateil;
import com.example.cloudAndPurchasing.manager.MyThreadPoolManager;
import com.example.cloudAndPurchasing.zhi.LogUtil;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/1 0001.
 */
public class FragmentPublish extends Fragment implements PullToRefreshLayout.OnRefreshListener {
    private ListView listView;
    private AdapterPublish adapterPubish;
    private ArrayList<PurchaseDateil> arrayList,purchaseDateils;
    public static int index = 1;
    private PullToRefreshLayout pullToRefreshLayout;
    private SharedPreferences sharedPreferences;
    private Imageloderinit imageloderinit;
    private LinearLayout linearLayout_loading,linearLayout_delete;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragmentpublishlayout,null);

        //初始化数据
        initData();
        //初始化控件
        initview(view);
        return view;
    }

    /**
     * 初始化数据
     */
    public void initData(){
        purchaseDateils = new ArrayList<PurchaseDateil>();
        imageloderinit = new Imageloderinit(getActivity());
        sharedPreferences = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        arrayList = new ArrayList<PurchaseDateil>();
        try {
            MyThreadPoolManager.getInstance().execute(runnable);
        }catch (Exception e){
            LogUtil.e("FragmentPublish runable", e.toString());
        }
    }

    /**
     * 初始化控件
     * @param view
     */
    public void initview(View view){
        linearLayout_loading = (LinearLayout)view.findViewById(R.id.linearlayout_shoppingdateil_loading);
        linearLayout_delete = (LinearLayout)view.findViewById(R.id.linearlayout_shoppingdateil_delete);
        linearLayout_loading.setVisibility(View.VISIBLE);
        listView = (ListView)view.findViewById(R.id.listview_publish);
        pullToRefreshLayout = (PullToRefreshLayout)view.findViewById(R.id.pulllayout_publishlayout);
        listView.setOnScrollListener(new PauseOnScrollListener(imageloderinit.imageLoader,true,false));
        pullToRefreshLayout.setOnRefreshListener(this);
    }
    /**
     * 开线程获取网络数据
     */
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (getActivity() != null){
                arrayList = new ArrayList<PurchaseDateil>();
                String token = SaveShared.tokenget(getActivity());
                String state= "3";
                String uid = SaveShared.uid(getActivity());
                arrayList = HttpTransfeData.couldshoppinghttp(getActivity(), token, index, state,uid);
                if (arrayList != null){
                    purchaseDateils.addAll(arrayList);
                }
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("content",purchaseDateils);
                message.what = Numbers.ONE;
                message.setData(bundle);
                handlerUi.sendMessage(message);
            }

        }
    };
    /**
     * 主线程界面刷新
     */
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
                            adapterPubish = new AdapterPublish(imageloderinit.imageLoader,imageloderinit.options,getActivity(),arrayList);
                            listView.setAdapter(adapterPubish);
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Intent intent = new Intent();
                                    intent.putExtra("th_ol",arrayList.get(position).getID());
                                    intent.putExtra("back",String.valueOf(Numbers.FIVE));
                                    intent.setClass(getActivity(), ActivityShoppingdetail.class);
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

                    break;
            }
        }
    };

    /**
     * 上下拉刷新
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
        if (purchaseDateils != null){
            purchaseDateils.clear();
        }
        super.onDestroy();
    }
}
