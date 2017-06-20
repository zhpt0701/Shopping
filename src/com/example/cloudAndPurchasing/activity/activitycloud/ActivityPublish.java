package com.example.cloudAndPurchasing.activity.activitycloud;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.*;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.*;
import android.widget.*;
import com.example.cloudAndPurchasing.BaseFragmentActivity;
import com.example.cloudAndPurchasing.MyActivity;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.activity.activityshopping.ActivityGoodsDateil;
import com.example.cloudAndPurchasing.activity.activitysurpired.ActivityShareDetail;
import com.example.cloudAndPurchasing.adapter.adaptercloud.AdapterMypublish;
import com.example.cloudAndPurchasing.adapter.adaptersurpired.AdapterPhoto;
import com.example.cloudAndPurchasing.customcontrol.listviewupdown.PullToRefreshLayout;
import com.example.cloudAndPurchasing.customcontrol.listviewupdown.PullableListView;
import com.example.cloudAndPurchasing.http.HttpTransfeData;
import com.example.cloudAndPurchasing.http.Numbers;
import com.example.cloudAndPurchasing.http.SaveShared;
import com.example.cloudAndPurchasing.imageloder.Imageloderinit;
import com.example.cloudAndPurchasing.imageloder.Myimageloder;
import com.example.cloudAndPurchasing.kind.Image;
import com.example.cloudAndPurchasing.kind.Publish;
import com.example.cloudAndPurchasing.kind.Statese;
import com.example.cloudAndPurchasing.manager.MyThreadPoolManager;
import com.example.cloudAndPurchasing.tiltle.TeaskBar;
import com.example.cloudAndPurchasing.zhi.LogUtil;
import com.example.cloudAndPurchasing.zhi.ValuePrice;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/5 0005.
 */
public class ActivityPublish extends BaseFragmentActivity implements View.OnClickListener,PullToRefreshLayout.OnRefreshListener {
    private Button imageButton;
    private PullableListView listView;
    private PullToRefreshLayout pullToRefreshLayout;
    private AdapterMypublish adapterMyPublish;
    private ArrayList<Publish> arrayList,arrayList1;
    private TextView textView;
    private String cotent;
    private int index = 1;
    private int num = 0,th_ol = 0;
    private String id = null;
    private Statese statese;
    private Imageloderinit imageloderinit;
    private LinearLayout linearLayout,linearLayout_delete;
    private View view;
    private List list;
    private Boolean flag = false;
    private PopupWindow popupWindow;
    private ArrayList<View> arrayList2;
    private AdapterPhoto adapterPhoto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitypublishlayout);

        TeaskBar.onSystemoutcolor(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        initData();
        initview();
    }
    public void initData(){
        LogUtil.i("ActivityPublish initdata start.","");
        arrayList1 = new ArrayList<Publish>();
        index = 1;
        imageloderinit = new Imageloderinit(getApplication());
        arrayList = new ArrayList<Publish>();
        try{
            MyThreadPoolManager.getInstance().execute(runnable);
        }catch (Exception e){
            LogUtil.e("ActivityPublish initData error:",e.toString());
        }finally {
            LogUtil.i("ActivityPublish initData end.","");
        }
        cotent = getIntent().getStringExtra("shaidan");
    }
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            String uid = SaveShared.uid(getApplication());
            String token = SaveShared.tokenget(getApplication());
            arrayList = HttpTransfeData.spurisedhttpsharedateil(getApplication(), index, uid, token);
            arrayList1.addAll(arrayList);
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("publish",arrayList1);
            message.what = Numbers.ONE;
            message.setData(bundle);
            handler.sendMessage(message);
        }
    };
    public void initview(){
        linearLayout = (LinearLayout)findViewById(R.id.linearlayout_shoppingdateil_loading);
        linearLayout_delete = (LinearLayout)findViewById(R.id.linearlayout_shoppingdateil_delete);
        linearLayout.setVisibility(View.VISIBLE);
        pullToRefreshLayout = (PullToRefreshLayout)findViewById(R.id.linearlayout_thismypublish);
        imageButton = (Button)findViewById(R.id.imagebutton_publishback);
        listView = (PullableListView)findViewById(R.id.listview_mypublish);
        listView.setOnScrollListener(new PauseOnScrollListener(imageloderinit.imageLoader,true,false));
        textView = (TextView)findViewById(R.id.textview_shaidan);
        imageButton.setOnClickListener(this);
        pullToRefreshLayout.setOnRefreshListener(this);
    }
    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    linearLayout.setVisibility(View.GONE);
                    arrayList = msg.getData().getParcelableArrayList("publish");
                    if (arrayList != null){
                        if (arrayList.size()>0){
                            linearLayout_delete.setVisibility(View.GONE);
                            AdapterMypublish.CommendOnClickListener commendOnClickListener = new AdapterMypublish.CommendOnClickListener() {
                                @Override
                                protected void commedOnClickListener(Integer tag, View v) {
                                    switch (v.getId()){
                                        case R.id.button_zan:
                                            String token = SaveShared.tokenget(getApplication());
                                            if (token != ""){
                                                linearLayout.setVisibility(View.VISIBLE);
                                                th_ol = tag;
                                                id = arrayList.get(tag).getQID();
                                                num = Integer.parseInt(arrayList.get(tag).getFaverconunt());
                                                MyThreadPoolManager.getInstance().execute(runnable1);
                                            }else {
                                                Toast.makeText(getApplication(),"请登录",Toast.LENGTH_SHORT).show();
                                            }
                                            break;
                                        case R.id.button_share_publish:
                                            Intent intent = new Intent();
                                            intent.putExtra("commid",arrayList.get(tag).getQID());
                                            intent.putExtra("content_ol",arrayList.get(tag).getContent());
                                            intent.putExtra("time",arrayList.get(tag).getTime());
                                            intent.putExtra("name",arrayList.get(tag).getNickname());
                                            intent.putExtra("photopath",arrayList.get(tag).getImageviewthis());
                                            intent.putExtra("this", String.valueOf(Numbers.TWO));
                                            intent.putExtra("title",arrayList.get(tag).getTitle());
                                            intent.putExtra("praise",arrayList.get(tag).getFaverconunt());
                                            intent.putExtra("publish",arrayList.get(tag).getPublishnumber());
                                            intent.setClass(getApplication(), ActivityShareDetail.class);
                                            startActivity(intent);
                                            break;
                                    }

                                }
                            };
                            AdapterMypublish.PhotoOnItemClickListener photoOnItemClickListener = new AdapterMypublish.PhotoOnItemClickListener() {
                                @Override
                                protected void photoOnItemClickListener(Integer tag, AdapterView<?> parent, View view, int position, long id) {
                                    flag = true;
                                    list = new ArrayList();
                                    list.clear();
                                    Log.i("this", "zhpt0701" + arrayList.get(tag).getArrayList());
                                    list.addAll(arrayList.get(tag).getArrayList());
                                    popphoto(flag,position);
                                    backgroundAlpha(0.2f);
                                }
                            };
                            adapterMyPublish = new AdapterMypublish(imageloderinit.imageLoader,imageloderinit.options,getApplication(),arrayList,commendOnClickListener,photoOnItemClickListener);
                            listView.setAdapter(adapterMyPublish);
                            listView.setSelected(true);
                            listView.setSelection(adapterMyPublish.getCount()-10);
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Intent intent = new Intent();
                                    intent.putExtra("commid",arrayList.get(position).getQID());
                                    intent.putExtra("content_ol",arrayList.get(position).getContent());
                                    intent.putExtra("time",arrayList.get(position).getTime());
                                    intent.putExtra("name",arrayList.get(position).getNickname());
                                    intent.putExtra("photopath",arrayList.get(position).getImageviewthis());
                                    intent.putExtra("this", String.valueOf(Numbers.TWO));
                                    intent.putExtra("title",arrayList.get(position).getTitle());
                                    intent.putExtra("praise",arrayList.get(position).getFaverconunt());
                                    intent.putExtra("publish",arrayList.get(position).getPublishnumber());
                                    intent.setClass(getApplication(), ActivityShareDetail.class);
                                    startActivity(intent);
                                }
                            });
                            listView.setSelected(true);
                            listView.setSelection(adapterMyPublish.getCount()-10);

                        }else {
                            linearLayout_delete.setVisibility(View.VISIBLE);
                        }
                    }else {
                        linearLayout_delete.setVisibility(View.VISIBLE);
                    }
                    break;
                case 2:
                    linearLayout.setVisibility(View.GONE);
                    statese = new Statese();
                    statese = msg.getData().getParcelable("con");
                    if (statese!=null){
                        if (statese.getData() == "true"){
                            num++;
                            adapterMyPublish.updateviewnum(listView,th_ol,num);
                        }
                        Toast.makeText(getApplication(),statese.getMsg(),Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    private void backgroundAlpha(float v) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = v;
        getWindow().setAttributes(lp);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageview_photo_this:
                popupWindow.dismiss();
                backgroundAlpha(1f);
                break;
            case R.id.imagebutton_publishback:
                finish();
                break;
        }
    }
    //http点赞
    private Runnable runnable1 = new Runnable() {
        @Override
        public void run() {
            statese = new Statese();
            String uid = SaveShared.uid(getApplication());
            String token = SaveShared.tokenget(getApplication());
            statese = HttpTransfeData.supporthttp(getApplication(),uid,id,token,statese);
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putParcelable("con",statese);
            message.what = Numbers.TWO;
            message.setData(bundle);
            handler.sendMessage(message);
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
                super.handleMessage(msg);
                arrayList1 = new ArrayList<Publish>();
                index = 1;
                MyThreadPoolManager.getInstance().execute(runnable);
                pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
            }
        }.sendEmptyMessageDelayed(0,2000);
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
                super.handleMessage(msg);
                index++;
                MyThreadPoolManager.getInstance().execute(runnable);
                pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
            }
        }.sendEmptyMessageDelayed(0,2000);
    }
    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    private void popphoto(Boolean flag,Integer postion) {
        popupWindow = new PopupWindow();
        View view = LayoutInflater.from(this).inflate(R.layout.photolayout,null);
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
            View view1 = LayoutInflater.from(this).inflate(R.layout.photoimage,null);
            ImageView imageView_ol=(ImageView)view1.findViewById(R.id.imageview_photo_this);
            imageloderinit.imageLoader.displayImage(list.get(c).toString(), imageView_ol, imageloderinit.options);
            arrayList2.add(view1);
            imageView_ol.setOnClickListener(this);
        }
        adapterPhoto = new AdapterPhoto(arrayList2,this);
        //设置Adapter
        viewPager1.setAdapter(adapterPhoto);
        viewPager1.setCurrentItem(postion);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                popupWindow.dismiss();
                backgroundAlpha(1f);
            }
        });
        if (flag){
            popupWindow.showAtLocation(view, Gravity.CENTER,0,0);
        }
    }
    /**
     * 销毁线程
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
