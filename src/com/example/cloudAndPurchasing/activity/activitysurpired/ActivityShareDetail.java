package com.example.cloudAndPurchasing.activity.activitysurpired;

import android.annotation.TargetApi;
import android.graphics.drawable.BitmapDrawable;
import android.os.*;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;
import com.example.cloudAndPurchasing.BaseFragmentActivity;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.adapter.adaptercloud.AdapterGridviewPhoto;
import com.example.cloudAndPurchasing.adapter.adaptersurpired.AdapterListSharedateil;
import com.example.cloudAndPurchasing.adapter.adaptersurpired.AdapterPhoto;
import com.example.cloudAndPurchasing.customcontrol.listviewupdown.PullToRefreshLayout;
import com.example.cloudAndPurchasing.http.HttpTransfeData;
import com.example.cloudAndPurchasing.http.Numbers;
import com.example.cloudAndPurchasing.http.SaveShared;
import com.example.cloudAndPurchasing.imageloder.Imageloderinit;
import com.example.cloudAndPurchasing.kind.Commentes;
import com.example.cloudAndPurchasing.kind.Image;
import com.example.cloudAndPurchasing.kind.Statese;
import com.example.cloudAndPurchasing.manager.MyThreadPoolManager;
import com.example.cloudAndPurchasing.tiltle.TeaskBar;
import com.example.cloudAndPurchasing.zhi.LogUtil;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/14 0014.
 */
public class ActivityShareDetail extends BaseFragmentActivity implements AdapterView.OnItemClickListener, View.OnClickListener,PullToRefreshLayout.OnRefreshListener {
    private TextView textView_name,textView_time,
            textView_title,textView_ccontent,textView_perse,textView_favor;
    private GridView gridView;
    private ListView listView;
    private Imageloderinit imageloderinit;
    private Button imageButton,imageButton_ol;
    private AdapterGridviewPhoto adapterGridviewPhoto;
    private ArrayList<Image> arrayList;
    private AdapterListSharedateil adapterListSharedateil;
    private ArrayList<Commentes> arrayList_connentes,commenteses;
    private Button button_submit;
    private EditText editText;
    private Statese statese ;
    private PullToRefreshLayout pullToRefreshLayout;
    private int index = 1;
    private ArrayList<View> views;
    private PopupWindow popupWindow;
    private ImageButton imageButton_zan,imageButton_pinglun;
    private boolean flage = false;
    private AdapterPhoto adapterPhoto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitysharedetail);
        //改变状态栏颜色
        TeaskBar.onSystemoutcolor(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        //初始化数据
        initData();
        //初始化控件
        initView();
    }

    /**
     * 初始化数据
     */
    public void initData(){
        LogUtil.i("ActivityShareDetail initData start.","");
        index = 1;
        commenteses = new ArrayList<Commentes>();
        imageloderinit = new Imageloderinit(getApplicationContext());
        try {
            MyThreadPoolManager.getInstance().execute(runnable);
        }catch (Exception e){
            LogUtil.e("ActivityShareDetail initData error:",e.toString());
        }finally {
            LogUtil.e("ActivityShareDetail initData end.","");
        }
        arrayList = new ArrayList<Image>();
        String path = getIntent().getStringExtra("photopath");
        if (path != null){
            try {
                JSONArray jsonArray = new JSONArray(path);
                for (int c = 0 ; c<jsonArray.length();c++){
                    Image image = new Image();
                    image.setShowImg(jsonArray.get(c).toString());
                    arrayList.add(image);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        adapterGridviewPhoto = new AdapterGridviewPhoto(imageloderinit.imageLoader,imageloderinit.options,this,arrayList);
    }

    /**
     * 获取回复数据
     */
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            arrayList_connentes = new ArrayList<Commentes>();
            String id = getIntent().getStringExtra("commid");
            arrayList_connentes = HttpTransfeData.single_dateilhttp(getApplication(),index,id);
            if (arrayList_connentes != null){
                commenteses.addAll(arrayList_connentes);
            }
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("content",commenteses);
            message.what = Numbers.ONE;
            message.setData(bundle);
            handler.sendMessage(message);
        }
    };
    public void initView(){
        imageButton_zan = (ImageButton)findViewById(R.id.button_share_zanol);
        imageButton_pinglun = (ImageButton)findViewById(R.id.button_share_pinlun);
        pullToRefreshLayout = (PullToRefreshLayout)findViewById(R.id.pulltorereslayoutol);
        imageButton = (Button)findViewById(R.id.imagebutton_sharedetail_back);
        imageButton_ol = (Button)findViewById(R.id.imagebutton_sharedetail_fenxiang);
        textView_name = (TextView)findViewById(R.id.textview_sharedateilname);
        textView_time = (TextView)findViewById(R.id.textview_sharedateiltime);
        textView_title = (TextView)findViewById(R.id.textview_sharedateiltitle);
        textView_ccontent = (TextView)findViewById(R.id.textview_sharedateilcontent);
        textView_perse = (TextView)findViewById(R.id.textview_publishnumberol);
        textView_favor = (TextView)findViewById(R.id.textview_zannumberol);
        button_submit = (Button)findViewById(R.id.button_publish_submit);
        editText = (EditText)findViewById(R.id.edittext_publish);
        gridView = (GridView)findViewById(R.id.gridview_sharedateil);
        listView = (ListView)findViewById(R.id.listview_sharedateil_pinglun);
        String name = getIntent().getStringExtra("name");
        String times = getIntent().getStringExtra("time");
        String content = getIntent().getStringExtra("content_ol");
        String title = getIntent().getStringExtra("title");
        String favor = getIntent().getStringExtra("praise");
        String publish = getIntent().getStringExtra("publish");
        Log.i(getApplication()+"",favor+"7236482374823"+publish);
        if (title != null){
            textView_title.setText(title);
        }
        if (name != null){
            textView_name.setText(name);
        }
        if (times != null){
            textView_time.setText(times);
        }
        if (content != null){
            textView_ccontent.setText(content);
        }
        if (publish != null){
            textView_perse.setText(publish);
        }
        if (favor != null){
            textView_favor.setText(favor);
        }
        gridView.setOnScrollListener(new PauseOnScrollListener(imageloderinit.imageLoader, true, false));
        listView.setOnScrollListener(new PauseOnScrollListener(imageloderinit.imageLoader, true, false));
        gridView.setAdapter(adapterGridviewPhoto);
        pullToRefreshLayout.setOnRefreshListener(this);
        gridView.setOnItemClickListener(this);
        listView.setOnItemClickListener(this);
        imageButton.setOnClickListener(this);
        imageButton_zan.setOnClickListener(this);
        imageButton_pinglun.setOnClickListener(this);
        imageButton_ol.setOnClickListener(this);
        button_submit.setOnClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.gridview_sharedateil:
                flage = true;
                popphoto(flage,position);
                backgroundAlpha(0f);
                break;

            case R.id.listview_sharedateil_pinglun:

                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imagebutton_sharedetail_back:
                finish();
                break;
                /*Intent intent = new Intent();
                String num = getIntent().getStringExtra("this");
                if (num.equals(String.valueOf(Numbers.ONE))){
                    intent.putExtra("zhi", ValuePrice.VALUE_THREE);
                    intent.putExtra("sh",String.valueOf(Numbers.THREE));
                    intent.setClass(this, MyActivity.class);
                }else if (num.equals(String.valueOf(Numbers.THREE))){
                   intent.setClass(this, ActivitySingle.class);
                }else if(num.equals(String.valueOf(Numbers.FOUR))){
                    intent.putExtra("cloud_ol", String.valueOf(Numbers.NINE));
                    intent.setClass(this,ActivityRecord.class);
                }else {
                    intent.putExtra("shaidan", "我的晒单");
                    intent.setClass(this, ActivityPublish.class);
                }
                startActivity(intent);
                finish();
                break;*/
            case R.id.imagebutton_sharedetail_fenxiang:
//                SharePopupwindows sharePopupwindows = new SharePopupwindows(this,v);
//                sharePopupwindows.show();
                break;
            case R.id.button_publish_submit:
                String token = SaveShared.tokenget(this);
                if (token != null){
                    if (!token.equals("")){
                        if(editText.getText().toString().length()>0){
                            MyThreadPoolManager.getInstance().execute(runnable1);
                        }else {
                            Toast.makeText(getApplication(),"请输入内容",Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(getApplication(),"请登录",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getApplication(),"请登录",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.button_share_zanol:
                String token1 = SaveShared.tokenget(getApplication());
                if (token1 != null){
                    if (!token1.equals("")){
                        MyThreadPoolManager.getInstance().execute(runableJob);
                    }else {
                        Toast.makeText(getApplication(),"请登录",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getApplication(),"请登录",Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.button_share_pinlun:

                break;
            case R.id.imageview_photo_this:
                popupWindow.dismiss();
                backgroundAlpha(1f);
                break;
        }
    }

    /**
     * 评论
     */
    private Runnable runnable1 = new Runnable() {
        @Override
        public void run() {
            statese = new Statese();
            String id = getIntent().getStringExtra("commid");
            String token = SaveShared.tokenget(getApplication());
            String uid = SaveShared.uid(getApplication());
            statese = HttpTransfeData.publishthisgohttp(getApplication(),id,token,uid,editText.getText().toString());
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putParcelable("con",statese);
            message.what = Numbers.TWO;
            message.setData(bundle);
            handler.sendMessage(message);
        }
    };
    //点赞接口
    private Runnable runableJob = new Runnable() {
        @Override
        public void run() {
            statese = new Statese();
            String uid = SaveShared.uid(getApplication());
            String token = SaveShared.tokenget(getApplication());
            String id = getIntent().getStringExtra("commid");
            statese = HttpTransfeData.supporthttp(getApplication(),uid,id,token,statese);
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putParcelable("con",statese);
            message.what = Numbers.THREE;
            message.setData(bundle);
            handler.sendMessage(message);
        }
    };
    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    arrayList_connentes = msg.getData().getParcelableArrayList("content");
                    if (arrayList_connentes != null){
                        adapterListSharedateil = new AdapterListSharedateil(imageloderinit.imageLoader,imageloderinit.options,getApplication(),arrayList_connentes);
                        listView.setAdapter(adapterListSharedateil);
                    }
                    break;
                case 2:
                    statese = new Statese();
                    statese = msg.getData().getParcelable("con");
                    if (statese!=null){
                        if (statese.getState().equals(String.valueOf(Numbers.ONE))){
                            int pu = Integer.parseInt(textView_perse.getText().toString());
                            pu++;
                            textView_perse.setText(String.valueOf(pu));
                            editText.setText(null);
                            commenteses = new ArrayList<Commentes>();
                            MyThreadPoolManager.getInstance().execute(runnable);
                        }
                        Toast.makeText(getApplication(), statese.getMsg(),Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 3:
                    statese = new Statese();
                    statese = msg.getData().getParcelable("con");
                    if (statese!=null){
                        if (statese.getState() != null){
                            if (statese.getState().equals(String.valueOf(Numbers.ONE))){
                                if (statese.getData().equals("true")){
                                    int fa = Integer.parseInt(textView_favor.getText().toString());
                                    fa++;
                                    textView_favor.setText(String.valueOf(fa));
                                }
                            }
                            Toast.makeText(getApplication(),statese.getMsg(),Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
            }
        }
    };
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
        views = new ArrayList<View>();
        for (int c = 0 ; c < arrayList.size();c++){
            View view1 = LayoutInflater.from(this).inflate(R.layout.photoimage,null);
            ImageView imageView_ol=(ImageView)view1.findViewById(R.id.imageview_photo_this);
            imageloderinit.imageLoader.displayImage(arrayList.get(c).getShowImg(), imageView_ol, imageloderinit.options);
            views.add(view1);
            imageView_ol.setOnClickListener(this);
        }
        adapterPhoto = new AdapterPhoto(views,this);
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

    private void backgroundAlpha(float v) {
        WindowManager.LayoutParams lf = getWindow().getAttributes();
        lf.alpha = v;
        getWindow().setAttributes(lf);
    }

    @Override
    protected void onDestroy() {
        if (arrayList_connentes != null){
            arrayList_connentes.clear();
        }
        if (arrayList != null){
            arrayList.clear();
        }
        if (commenteses != null){
            commenteses.clear();
        }
        if (views != null){
            views.clear();
        }
        super.onDestroy();
    }

    /**
     * 下拉刷新
     * @param
     */
    @Override
    public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
        new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                commenteses.clear();
                index = 1;
                MyThreadPoolManager.getInstance().execute(runnable);
                pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
            }
        }.sendEmptyMessageDelayed(0,1500);
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
        }.sendEmptyMessageDelayed(0,1500);
    }
}
