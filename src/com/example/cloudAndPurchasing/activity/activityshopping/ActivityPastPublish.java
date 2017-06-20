package com.example.cloudAndPurchasing.activity.activityshopping;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.*;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;
import com.example.cloudAndPurchasing.BaseFragmentActivity;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.activity.activitysurpired.ActivityShareDetail;
import com.example.cloudAndPurchasing.adapter.adaptercloud.AdapterMypublish;
import com.example.cloudAndPurchasing.adapter.adaptermain.AdapterGoodsShare;
import com.example.cloudAndPurchasing.adapter.adaptersurpired.AdapterPhoto;
import com.example.cloudAndPurchasing.customcontrol.listviewupdown.PullToRefreshLayout;
import com.example.cloudAndPurchasing.http.HttpTransfeData;
import com.example.cloudAndPurchasing.http.Numbers;
import com.example.cloudAndPurchasing.http.SaveShared;
import com.example.cloudAndPurchasing.imageloder.Imageloderinit;
import com.example.cloudAndPurchasing.kind.Publish;
import com.example.cloudAndPurchasing.kind.Statese;
import com.example.cloudAndPurchasing.manager.MyThreadPoolManager;
import com.example.cloudAndPurchasing.tiltle.TeaskBar;
import com.example.cloudAndPurchasing.zhi.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/25 0025.
 */
public class ActivityPastPublish extends BaseFragmentActivity implements PullToRefreshLayout.OnRefreshListener, View.OnClickListener , AdapterView.OnItemClickListener {
    private PullToRefreshLayout pullToRefreshLayout;
    private ListView listView;
    private Button button;
    private Statese statese;
    private Imageloderinit imageloderinit;
    private LinearLayout linearLayout,linearLayout_ol;
    private AdapterMypublish adapterMyPublish;
    private int index = 1;
    private PopupWindow popupWindow;
    private AdapterPhoto adapterPhoto;
    private List list;
    private String id = null;
    private boolean flag = false;
    private Integer tag_ol =0;
    private ArrayList<View> arrayList3;
    private ArrayList<Publish> arrayList,arrayList1;
    private AdapterGoodsShare adapterGoodsShare;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitypastpublishlayout);

        TeaskBar.onSystemoutcolor(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        initData();
        initView();
    }
    public void initData(){
        LogUtil.i("AcrtivityPastPublish initData start.","");
        arrayList1 = new ArrayList<Publish>();
        imageloderinit = new Imageloderinit(this);
        try{
            MyThreadPoolManager.getInstance().execute(runnable);
        }catch (Exception e){
            LogUtil.e("ActivityPastpublish initData error:",e.toString());
        }finally {
            LogUtil.i("ActivityPastpublish initData end.","");
        }
    }
    public void initView(){
        linearLayout_ol = (LinearLayout)findViewById(R.id.linearlayout_information_publish);
        linearLayout_ol.setVisibility(View.VISIBLE);
        pullToRefreshLayout = (PullToRefreshLayout)findViewById(R.id.pulltorefrelayout);
        listView = (ListView)findViewById(R.id.pull_listview);
        button = (Button)findViewById(R.id.button_pastback);
        linearLayout = (LinearLayout)findViewById(R.id.linearlayout_zanwu);
        pullToRefreshLayout.setOnRefreshListener(this);
        button.setOnClickListener(this);
    }

    /**
     * 获取往期晒单数据
     */
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            arrayList = new ArrayList<Publish>();
            String token = SaveShared.tokenget(getApplication());
            String id = getIntent().getStringExtra("goodsid");
            arrayList = HttpTransfeData.pasthttp(arrayList,getApplication(),id,token,index);
            arrayList1.addAll(arrayList);
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("array",arrayList1);
            message.setData(bundle);
            message.what = Numbers.ONE;
            handler.sendMessage(message);
        }
    };
//    private Runnable runnable1 = new Runnable() {
//        @Override
//        public void run() {
//            statese = new Statese();
//            String uid = SaveShared.uid(getApplication());
//            String token = SaveShared.tokenget(getApplication());
//            statese = HttpTransfeData.supporthttp(getApplication(),uid,id,token,statese);
//            Message message = new Message();
//            Bundle bundle = new Bundle();
//            bundle.putParcelable("con",statese);
//            message.what = Numbers.TWO;
//            message.setData(bundle);
//            handler.sendMessage(message);
//        }
//    };
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

    @Override
    public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
        new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                index +=1;
                MyThreadPoolManager.getInstance().execute(runnable);
                pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
            }
        }.sendEmptyMessageDelayed(0,2000);
    }
    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    linearLayout_ol.setVisibility(View.GONE);
                    arrayList = msg.getData().getParcelableArrayList("array");
                    if (arrayList.size()>0){
                        linearLayout.setVisibility(View.GONE);
                        adapterGoodsShare = new AdapterGoodsShare(imageloderinit.imageLoader,imageloderinit.options,getApplication(),arrayList);
                        listView.setAdapter(adapterGoodsShare);
                        listView.setOnItemClickListener(ActivityPastPublish.this);

                    }else {
                        linearLayout.setVisibility(View.VISIBLE);
                    }
                    break;
                case 2:
                    statese = new Statese();
                    statese = msg.getData().getParcelable("con");
                    if (!statese.equals(null)){
                        if (statese.getData().equals("true")){
                            int num = Integer.parseInt(arrayList.get(tag_ol).getCommendnumber());
                            num++;
                            adapterMyPublish.updateviewnum(listView, tag_ol, num);
                        }
                        Toast.makeText(getApplication(),statese.getMsg(),Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        intent.putExtra("commid",arrayList.get(position).getQID());
        intent.putExtra("content_ol",arrayList.get(position).getContent());
        intent.putExtra("time",arrayList.get(position).getTime());
        intent.putExtra("name",arrayList.get(position).getNickname());
        intent.putExtra("photopath",arrayList.get(position).getImagepath());
        intent.putExtra("this",String.valueOf(Numbers.THREE));
        intent.putExtra("title",arrayList.get(position).getTitle());
        intent.putExtra("praise",arrayList.get(position).getFavorCount());
        intent.putExtra("publish",arrayList.get(position).getCommendnumber());
        intent.setClass(getApplication(),ActivityShareDetail.class);
        startActivity(intent);
    }
    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    private void popphoto(Boolean flag) {
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
        arrayList3 = new ArrayList<View>();
        for (int c = 0 ; c < list.size();c++){
            View view1 = LayoutInflater.from(this).inflate(R.layout.photoimage,null);
            ImageView imageView_ol=(ImageView)view1.findViewById(R.id.imageview_photo_this);
            imageloderinit.imageLoader.displayImage(list.get(c).toString(), imageView_ol, imageloderinit.options);
            arrayList3.add(view1);
            imageView_ol.setOnClickListener(this);
        }
        adapterPhoto = new AdapterPhoto(arrayList3,this);
        //设置Adapter
        viewPager1.setAdapter(adapterPhoto);
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

    private void backgroundAlpha(float v) {
        WindowManager.LayoutParams lf = getWindow().getAttributes();
        lf.alpha = v;
        getWindow().setAttributes(lf);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_pastback:
                finish();
                break;
               /* Intent intent = new Intent();
                String num = getIntent().getStringExtra("sum");
                if (num.equals(String.valueOf(Numbers.ONE))){
                    intent.setClass(this,ActivityGoodsDateil.class);
                }else if (num.equals(String.valueOf(Numbers.TWO))){
                    intent.setClass(this, ActivityShoppingdetail.class);
                }else {
                    intent.setClass(this, ActivityShoppingTimeDetail.class);
                }
                startActivity(intent);
                finish();
                break;*/
            case R.id.imageview_photo_this:
                popupWindow.dismiss();
                backgroundAlpha(1f);
            break;
        }
    }

}
