package com.example.cloudAndPurchasing.fragment.fragmentsurpried;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.*;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.*;
import android.widget.*;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.activity.ActivityShoppingdetail;
import com.example.cloudAndPurchasing.activity.activitycloud.activityfriend.ActivityFriend;
import com.example.cloudAndPurchasing.activity.activityshopping.ActivityGoodsDateil;
import com.example.cloudAndPurchasing.activity.activitysurpired.ActivityShareDetail;
import com.example.cloudAndPurchasing.activity.activitysurpired.ActivityShoppingTimeDetail;
import com.example.cloudAndPurchasing.adapter.adaptersurpired.AdapterCloudRoom;
import com.example.cloudAndPurchasing.adapter.adaptersurpired.AdapterPhoto;
import com.example.cloudAndPurchasing.adapter.adaptersurpired.AdapterShopping;
import com.example.cloudAndPurchasing.adapter.adaptersurpired.AdapterSingleRecord;
import com.example.cloudAndPurchasing.customcontrol.listviewupdown.PullToRefreshLayout;
import com.example.cloudAndPurchasing.http.HttpMothed;
import com.example.cloudAndPurchasing.http.HttpTransfeData;
import com.example.cloudAndPurchasing.http.Numbers;
import com.example.cloudAndPurchasing.http.SaveShared;
import com.example.cloudAndPurchasing.imageloder.Imageloderinit;
import com.example.cloudAndPurchasing.imageloder.Myimageloder;
import com.example.cloudAndPurchasing.kind.Publish;
import com.example.cloudAndPurchasing.kind.Share;
import com.example.cloudAndPurchasing.kind.Winning;
import com.example.cloudAndPurchasing.manager.MyThreadPoolManager;
import com.example.cloudAndPurchasing.popupwindows.SharePopupwindows;
import com.example.cloudAndPurchasing.zhi.LogUtil;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/12 0012.
 */
public class FragmentSingleRecord extends Fragment implements PullToRefreshLayout.OnRefreshListener, View.OnClickListener {
    private ListView listView;
    private AdapterSingleRecord adapterSingleRecord;
    private ArrayList<Publish> arrayList,publishs;
    private ArrayList<View> arrayList2;
    private Imageloderinit myimageloder;
    private PullToRefreshLayout pullToRefreshLayout;
    private int index = 1;
    private List list;
    private Boolean flag = false;
    private AdapterPhoto adapterPhoto;
    private PopupWindow popupWindow;
    private LinearLayout linearLayout_loading,linearLayout_delete;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragmentsinglerecordlayout,null);
        initData();
        initView(view);
        return view;
    }
    //初始化数据
    public void initData(){
        LogUtil.i("FragmentSingleRecord initData start.","");
        publishs = new ArrayList<Publish>();
        myimageloder = new Imageloderinit(getActivity());
        index = 1;;
        try{
            MyThreadPoolManager.getInstance().execute(runnable);
        }catch (Exception e){
            LogUtil.e("FragmentSingleRecord initData error:",e.toString());
        }finally {
            LogUtil.i("FragmentSingleRecord initData end.","");
        }
    }

    /**
     * 数据请求
     */
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (getActivity()!= null){
                arrayList = new ArrayList<Publish>();
                if (getActivity() != null){
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
                    String uid = sharedPreferences.getString("fid", "");
                    String token = SaveShared.tokenget(getActivity());
                    arrayList = HttpTransfeData.spurisedhttpsharedateil(getActivity(), index, uid, token);
                    if (publishs != null){
                        publishs.addAll(arrayList);
                    }
                    Message message = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("share",publishs);
                    message.setData(bundle);
                    message.what = Numbers.ONE;
                    handler1.sendMessage(message);
                }

            }

        }
    };
    //初始化控件
    public void initView(View v){
        linearLayout_loading = (LinearLayout)v.findViewById(R.id.linearlayout_shoppingdateil_loading);
        linearLayout_delete = (LinearLayout)v.findViewById(R.id.linearlayout_shoppingdateil_delete);
        linearLayout_loading.setVisibility(View.VISIBLE);
        pullToRefreshLayout = (PullToRefreshLayout)v.findViewById(R.id.pulllayout_recoid);
        listView = (ListView)v.findViewById(R.id.listview_singlerecord);
        listView.setOnScrollListener(new PauseOnScrollListener(myimageloder.imageLoader,true,false));
        pullToRefreshLayout.setOnRefreshListener(this);
    }
    private Handler handler1 = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    linearLayout_loading.setVisibility(View.GONE);
                    arrayList = msg.getData().getParcelableArrayList("share");
                    if (arrayList!=null){
                        if (arrayList.size()>0){
                            linearLayout_delete.setVisibility(View.GONE);
                            AdapterSingleRecord.PhotoOnitemlistener photoOnitemlistener = new AdapterSingleRecord.PhotoOnitemlistener() {
                                @Override
                                protected void photoOnitemlistener(Integer tag, View view, AdapterView<?> parent, int position, long id) {
                                    flag = true;
                                    list = new ArrayList();
                                    list.clear();
                                    list.addAll(arrayList.get(tag).getArrayList());
                                    popphoto(flag,position);
                                    backgroundAlpha(0f);
                                }
                            };
                            adapterSingleRecord = new AdapterSingleRecord(myimageloder.imageLoader,myimageloder.options,getActivity(),arrayList,photoOnitemlistener);
                            listView.setAdapter(adapterSingleRecord);
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Intent intent = new Intent();
                                    intent.putExtra("commid",arrayList.get(position).getQID());
                                    intent.putExtra("content_ol",arrayList.get(position).getContent());
                                    intent.putExtra("time",arrayList.get(position).getTime());
                                    intent.putExtra("name",arrayList.get(position).getNickname());
                                    intent.putExtra("photopath",arrayList.get(position).getImageviewthis());
                                    intent.putExtra("this",String.valueOf(Numbers.FOUR));
                                    intent.putExtra("praise",arrayList.get(position).getFaverconunt());
                                    intent.putExtra("publish",arrayList.get(position).getPublishnumber());
                                    intent.setClass(getActivity(), ActivityShareDetail.class);
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
     * 下拉刷新
     * @param pullToRefreshLayout
     */
    @Override
    public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
        new Handler(){
            @Override
            public void handleMessage(Message msg) {
                publishs = new ArrayList<Publish>();
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
    //popupwindows弹框显示图片
    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    private void popphoto(Boolean flag,Integer postion) {
        popupWindow = new PopupWindow();
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.photolayout,null);
        popupWindow.setContentView(view);
        popupWindow.setHeight(WindowManager.LayoutParams.MATCH_PARENT);
        popupWindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        popupWindow.setClippingEnabled(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        ViewPager viewPager1 = (ViewPager)view.findViewById(R.id.viewpager_photo);
        arrayList2 = new ArrayList<View>();
        for (int c = 0 ; c < list.size();c++){
            View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.photoimage,null);
            ImageView imageView_ol=(ImageView)view1.findViewById(R.id.imageview_photo_this);
            myimageloder.imageLoader.displayImage(list.get(c).toString(), imageView_ol, myimageloder.options);
            arrayList2.add(view1);
            imageView_ol.setOnClickListener(this);
        }
        adapterPhoto = new AdapterPhoto(arrayList2,getActivity());
        //设置Adapter
        viewPager1.setAdapter(adapterPhoto);
        viewPager1.setCurrentItem(postion);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });
        if (flag){
            popupWindow.showAtLocation(view, Gravity.CENTER,0,0);
        }
    }
    //设置主屏背景色
    public void backgroundAlpha(float bgAlpha)
    {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha;
        getActivity().getWindow().setAttributes(lp);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageview_photo_this:
                popupWindow.dismiss();
                backgroundAlpha(1f);
                break;
        }

    }
}
