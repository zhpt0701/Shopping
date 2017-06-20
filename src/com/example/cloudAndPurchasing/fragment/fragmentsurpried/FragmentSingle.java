package com.example.cloudAndPurchasing.fragment.fragmentsurpried;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.*;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.*;
import android.widget.*;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.activity.activitysurpired.ActivityReply;
import com.example.cloudAndPurchasing.activity.activitysurpired.ActivityShareDetail;
import com.example.cloudAndPurchasing.adapter.adaptercloud.AdapterMypublish;
import com.example.cloudAndPurchasing.adapter.adaptersurpired.AdapterPhoto;
import com.example.cloudAndPurchasing.adapter.adaptersurpired.AdapterShopping;
import com.example.cloudAndPurchasing.banner.ADInfo;
import com.example.cloudAndPurchasing.banner.CycleViewPager;
import com.example.cloudAndPurchasing.banner.ViewFactory;
import com.example.cloudAndPurchasing.customcontrol.listviewupdown.PullToRefreshLayout;
import com.example.cloudAndPurchasing.customcontrol.listviewupdown.PullableListView;
import com.example.cloudAndPurchasing.http.HttpApi;
import com.example.cloudAndPurchasing.http.HttpTransfeData;
import com.example.cloudAndPurchasing.http.Numbers;
import com.example.cloudAndPurchasing.http.SaveShared;
import com.example.cloudAndPurchasing.imageloder.BannerImageLoder;
import com.example.cloudAndPurchasing.imageloder.Imageloderinit;
import com.example.cloudAndPurchasing.kind.Image;
import com.example.cloudAndPurchasing.kind.Publish;
import com.example.cloudAndPurchasing.kind.Statese;
import com.example.cloudAndPurchasing.manager.MyThreadPoolManager;
import com.example.cloudAndPurchasing.zhi.LogUtil;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/11 0011.
 */
public class FragmentSingle extends Fragment implements AdapterView.OnItemClickListener, PullToRefreshLayout.OnRefreshListener, View.OnClickListener {
    private ListView listView;
    private PullToRefreshLayout layout;
    private AdapterShopping adapterShopping;
    private ArrayList<Publish> arrayList,publishs;
    private ArrayList<View> views;
    private AdapterPhoto adapterPhoto;
    private PopupWindow popupWindow;
    private Boolean flag = false;
    private Statese statese;
    private int index = 0,tag_ol = 0;
    private String id = null;
    private BannerImageLoder imageloderinit;
    private CycleViewPager cycleViewPager;
    private View view;
    private List list;
    private LinearLayout linearLayout,linearLayout_ol;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragmentsinglelayout,null);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        //初始化数据
        initData();
        //初始化控件
        initView(view);
    }

    /**
     * 初始化数据
     * 从后台获取数据
     * @param
     */
    public void initData(){
        publishs = new ArrayList<Publish>();
        index = 1;
        imageloderinit = new BannerImageLoder(getActivity());
        arrayList = new ArrayList<Publish>();
        try {
            MyThreadPoolManager.getInstance().execute(runnable);
        }catch (Exception e){
            LogUtil.e("FragmentSingle initData error：", e.toString());
        }
    }

    /**
     * 所有晒单数据请求
     */
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            String token = SaveShared.tokenget(getActivity());
            arrayList = HttpTransfeData.spurisedhttpsharedateil_ol(getActivity(),index,token);
            if (arrayList != null){
                publishs.addAll(arrayList);
            }
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("publish",publishs);
            message.what = Numbers.ONE;
            message.setData(bundle);
            handler1.sendMessage(message);
        }
    };
    /**
     * 点赞
     */
    private Runnable runnableParise = new Runnable() {
        @Override
        public void run() {
            statese = new Statese();
            String uid = SaveShared.uid(getActivity());
            String token = SaveShared.tokenget(getActivity());
            statese = HttpTransfeData.supporthttp(getActivity(),uid,id,token,statese);
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putParcelable("con",statese);
            message.what = Numbers.TWO;
            message.setData(bundle);
            handler1.sendMessage(message);
        }
    };
    //popupwindows弹框
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
        views = new ArrayList<View>();
        for (int c = 0 ; c < list.size();c++){
            View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.photoimage,null);
            ImageView imageView_ol=(ImageView)view1.findViewById(R.id.imageview_photo_this);
            imageloderinit.imageLoader.displayImage(list.get(c).toString(), imageView_ol, imageloderinit.options);
            views.add(view1);
            imageView_ol.setOnClickListener(this);
        }
        adapterPhoto = new AdapterPhoto(views,getActivity());
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
            popupWindow.showAtLocation(view,Gravity.CENTER,0,0);
        }
    }
    //初始化控件
    public void initView(View view){
        linearLayout = (LinearLayout)view.findViewById(R.id.linearlayout_publish_content);
        linearLayout_ol = (LinearLayout)view.findViewById(R.id.linearlayout_orpublish);
        linearLayout.setVisibility(View.VISIBLE);
        listView = (ListView)view.findViewById(R.id.listview_single);
        layout = (PullToRefreshLayout)view.findViewById(R.id.layout_new_single);
        listView.setOnScrollListener(new PauseOnScrollListener(imageloderinit.imageLoader, true, false));
        listView.setOnItemClickListener(this);
        layout.setOnRefreshListener(this);
    }
    //listveiw item点击监听事件
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        intent.putExtra("commid",arrayList.get(position).getQID());
        intent.putExtra("content_ol",arrayList.get(position).getContent());
        intent.putExtra("time",arrayList.get(position).getTime());
        intent.putExtra("name",arrayList.get(position).getNickname());
        intent.putExtra("photopath",arrayList.get(position).getImageviewthis());
        intent.putExtra("title",arrayList.get(position).getTitle());
        intent.putExtra("this",String.valueOf(Numbers.ONE));
        intent.putExtra("praise",arrayList.get(position).getFaverconunt());
        intent.putExtra("publish",arrayList.get(position).getPublishnumber());
        intent.setClass(getActivity(),ActivityShareDetail.class);
        startActivity(intent);
    }
    public void backgroundAlpha(float bgAlpha)
    {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha;
        getActivity().getWindow().setAttributes(lp);
    }

    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
       new  Handler(){
           @Override
           public void handleMessage(Message msg) {
               super.handleMessage(msg);
               publishs.clear();
               index = 1;
               MyThreadPoolManager.getInstance().execute(runnable);
               layout.refreshFinish(PullToRefreshLayout.SUCCEED);
           }
       }.sendEmptyMessageDelayed(0,2000);
    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        new  Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                index++;
                MyThreadPoolManager.getInstance().execute(runnable);
                layout.refreshFinish(PullToRefreshLayout.SUCCEED);
            }
        }.sendEmptyMessageDelayed(0,2000);
    }
    private Handler handler1 = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what){
                case 1:
                    linearLayout.setVisibility(View.GONE);
                    arrayList = msg.getData().getParcelableArrayList("publish");
                    if (arrayList != null){
                        if (arrayList.size()>0){
                            linearLayout_ol.setVisibility(View.GONE);
                            AdapterShopping.ShoppingOnClickListener shoppingOnClickListener = new AdapterShopping.ShoppingOnClickListener() {
                                @Override
                                protected void shopping(Integer tag, View v) {
                                    String token = SaveShared.tokenget(getActivity());
                                    switch (v.getId()){
                                        case R.id.button_zanol:
                                            if (token != ""){
                                                tag_ol = tag;
                                                id = arrayList.get(tag).getQID();
                                                MyThreadPoolManager.getInstance().execute(runnableParise);
                                            }else {
                                                Toast.makeText(getActivity(),"请登录",Toast.LENGTH_SHORT).show();
                                            }
                                            break;
                                        case R.id.button_publishol:
                                            if (token!= ""){
                                                String id = arrayList.get(tag).getQID();
                                                Intent intent = new Intent();
                                                intent.putExtra("commid",id);
                                                intent.putExtra("content_ol",arrayList.get(tag).getContent());
                                                intent.putExtra("time",arrayList.get(tag).getTime());
                                                intent.putExtra("name",arrayList.get(tag).getNickname());
                                                intent.putExtra("photopath",arrayList.get(tag).getImageviewthis());
//                                        intent.putExtra("title",arrayList.get(tag).get)
                                                intent.putExtra("title",arrayList.get(tag).getTitle());
                                                intent.putExtra("this",String.valueOf(Numbers.ONE));
                                                intent.putExtra("praise",arrayList.get(tag).getFaverconunt());
                                                intent.putExtra("publish",arrayList.get(tag).getPublishnumber());
                                                intent.setClass(getActivity(),ActivityShareDetail.class);
                                                startActivity(intent);
                                            }else {
                                                Toast.makeText(getActivity(),"请登录",Toast.LENGTH_SHORT).show();
                                            }

                                            break;
                                    }
                                }
                            };
                            AdapterShopping.PhotoOnitemlistener photoOnitemlistener = new AdapterShopping.PhotoOnitemlistener() {
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
                            adapterShopping = new AdapterShopping(imageloderinit.imageLoader,imageloderinit.options,getActivity(),arrayList,shoppingOnClickListener,photoOnitemlistener);
                            listView.setAdapter(adapterShopping);
                            if (adapterShopping.getCount()>10){
                                listView.setSelected(true);
                                listView.setSelection(6);
                            }
                        }else {
                            linearLayout_ol.setVisibility(View.VISIBLE);
                        }
                    }else {
                        linearLayout_ol.setVisibility(View.VISIBLE);
                    }
                    break;
                case 2:
                    statese = new Statese();
                    statese = msg.getData().getParcelable("con");
                    if (statese!=null){
                        if (statese.getData() == "true"){
                            int c = Integer.parseInt(arrayList.get(tag_ol).getFaverconunt());
                            c++;
                            adapterShopping.updateviewthis(listView,tag_ol,c);
                        }
                        Toast.makeText(getActivity(),statese.getMsg(),Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };
    //销毁线程
    @Override
    public void onDestroy() {
        if (publishs != null){
            publishs.clear();
        }
        if (arrayList != null){
            arrayList.clear();
        }
        if (views != null){
            views.clear();
        }
        super.onDestroy();
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
