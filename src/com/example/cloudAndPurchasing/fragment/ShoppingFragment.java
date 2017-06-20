package com.example.cloudAndPurchasing.fragment;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.*;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.cloudAndPurchasing.MyActivity;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.activity.activitycloud.activityinstall.ActivityAddWe;
import com.example.cloudAndPurchasing.activity.activityshopping.ActivityGoodsDateil;
import com.example.cloudAndPurchasing.activity.activityshopping.ActivityShoppingEach;
import com.example.cloudAndPurchasing.adapter.AdapterCatogry;
import com.example.cloudAndPurchasing.adapter.AdapterDetail;
import com.example.cloudAndPurchasing.customcontrol.listviewupdown.PullToRefreshLayout;
import com.example.cloudAndPurchasing.http.HttpApi;
import com.example.cloudAndPurchasing.http.HttpTransfeData;
import com.example.cloudAndPurchasing.http.Numbers;
import com.example.cloudAndPurchasing.http.SaveShared;
import com.example.cloudAndPurchasing.imageloder.Imageloderinit;
import com.example.cloudAndPurchasing.kind.*;
import com.example.cloudAndPurchasing.manager.MyThreadPoolManager;
import com.example.cloudAndPurchasing.times.CToast;
import com.example.cloudAndPurchasing.zhi.LogUtil;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/3/21 0021.
 */
public class ShoppingFragment extends Fragment implements PullToRefreshLayout.OnRefreshListener, View.OnClickListener {
    /**
     * 自定属性控件；
     * @param code string 接
     * @param index 页码
     * @param pid 期id
     * @param imageloderinit 网络图片处理
     * @param statese http返回状态对象
     */
    private static ListView listView;
    private static ListView listview_content;
    private Button imageButton,imageButton_ol,btn;
    private PullToRefreshLayout layout;
    public static RadioButton radioButton;
    private AdapterDetail adapterdetail;
    private String code;
    private AdapterCatogry adapterCatogry;
    private ArrayList<Goods> arrayList_goods,goodses;
    private HandlerThread handlerThread;
    private Handler mhandler;
    private Imageloderinit imageloderinit;
    int id_ol =0;
    private int index = 0;
    private int postions = 0,number = 0;
    private Statese statese;
    private String cateid ;
    private String limitcount ,surpcount ;
    private ArrayList<Category> arrayList_ol,categorys;
    //抽象方法
    private AdapterDetail.DataOnclicklistner dataOnclicklistner ;
    private LinearLayout linearLayout_loading,linearLayout_delete,linearLayout_add_we;
    public static View view;
    private String pid ;
    //自定义toast窗口
    private CToast cToast;
    private int STATE = 0;
    private long time = 0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragmentshopping_layout,null);
        //初始化数据
        initData(view);
        //初始化控件
        initView(view);
        return view;
    }

    /**
     * 初始化数据
     * @param view
     */
    public void initData(View view){
        LogUtil.i("FragmentShopping initData start.","");
        index = 1;
        //初始化list
        goodses = new ArrayList<Goods>();
        goodses.clear();
        arrayList_ol = new ArrayList<Category>();
        imageloderinit = new Imageloderinit(getActivity());
        //线程绑定
        handlerThread = new HandlerThread("MyHandlerThread");
        handlerThread.start();
        mhandler = new Handler(handlerThread.getLooper());
        try {
            if (!StringUtils.isEmpty(CacheUtils.getCache(HttpApi.cartegory, true))){
                JSONObject jsonObject = new JSONObject(CacheUtils.getCache(HttpApi.cartegory,false));
                JSONArray jsonArray = null;
                jsonArray = new JSONArray(jsonObject.getString("data"));
                Log.i("this_content", jsonObject.getString("data") + "json");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    Category category = new Category();
                    category.setCategoryname(jsonObject1.getString("Name"));
                    category.setCoding(jsonObject1.getString("Code"));
                    category.setCategoryid(jsonObject1.getString("ID"));
                    arrayList_ol.add(category);
                }
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("categroy",arrayList_ol);
                message.setData(bundle);
                message.what = Numbers.ONE;
                handler.sendMessage(message);
            }else {
                MyThreadPoolManager.getInstance().execute(runnable);
            }
        }catch (Exception e){
            LogUtil.e("FragmentShopping initdata error：",e.toString());
        }finally {
            LogUtil.i("FragmentShopping initData end.","");
        }
        //初始化控件
        listView = (ListView)view.findViewById(R.id.listview_categroy);
        listview_content = (ListView)view.findViewById(R.id.listview_down);
        listView.setOnScrollListener(new PauseOnScrollListener(imageloderinit.imageLoader,true,false));
        listview_content.setOnScrollListener(new PauseOnScrollListener(imageloderinit.imageLoader,true,false));
    }
    //http获取分类名称
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            arrayList_ol = HttpTransfeData.httpcartegory(getActivity());
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("categroy",arrayList_ol);
            message.what = Numbers.ONE;
            message.setData(bundle);
            handler.sendMessage(message);
        }
    };

    /**
     * 初始化布局控件
     * @param v
     * @param
     */
    public void initView(View v){
        linearLayout_add_we = (LinearLayout)v.findViewById(R.id.linearlayout_this_add_we);
        linearLayout_loading = (LinearLayout)v.findViewById(R.id.linearlayout_shoppingdateil_loading);
        linearLayout_loading.setVisibility(View.VISIBLE);
        linearLayout_delete = (LinearLayout)v.findViewById(R.id.linearlayout_shoppingdateil_delete);
        imageButton = (Button)v.findViewById(R.id.imagebutton_shoppingeach);
        imageButton_ol = (Button)v.findViewById(R.id.imagebutton_shoppingdingwei);
        btn = (Button)v.findViewById(R.id.btn_add_we);
        layout = (PullToRefreshLayout)v.findViewById(R.id.layout_up);
        //设置监听事件
        imageButton.setOnClickListener(this);
        btn.setOnClickListener(this);
        imageButton_ol.setOnClickListener(this);
        layout.setOnRefreshListener(this);
    }

    /**
     * handler机制界面数据刷新
     * @param ONE 分类数据
     * @param TWO 对应分类的数据
     */
    private Handler handler = new Handler(Looper.getMainLooper()){
        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == Numbers.ONE){
                //loading 圈隐藏
                linearLayout_loading.setVisibility(View.GONE);
                //arraylist_l 获取分类的数据
                categorys = msg.getData().getParcelableArrayList("categroy");
                if (categorys != null){
                    linearLayout_loading.setVisibility(View.GONE);
                    if (categorys.size()>0){
                        linearLayout_delete.setVisibility(View.GONE);
                        //将数据传入adapter适配其中进行数据处理
                        adapterCatogry = new AdapterCatogry(getActivity(),categorys);
                        listView.setAdapter(adapterCatogry);
                        try {
                            if (((MyActivity)getActivity()).getState() !=0){
                                try {
                                    linearLayout_loading.setVisibility(View.VISIBLE);
                                    listView.setItemChecked(1, true);
                                    cateid = categorys.get(1).getCategoryid();
                                    id_ol= Integer.parseInt(categorys.get(1).getCoding());
                                    index = 1;
                                    number = 1;
                                    STATE = 1;
                                    code = categorys.get(1).getCoding();
                                    goodses.clear();
                                    MyThreadPoolManager.getInstance().execute(runnableGoods);
                                }catch (Exception e){
                                    LogUtil.e("FragmentShopping handler error:",e.toString());
                                }
                            }else {
                                postions = 1;
                                try {
                                    linearLayout_loading.setVisibility(View.VISIBLE);
                                    listView.setItemChecked(0,true);
                                    cateid = "-1";
                                    number = 1;
                                    code = categorys.get(0).getCoding();
                                    id_ol = Integer.parseInt(categorys.get(0).getCoding());
                                    index = 1;
                                    goodses.clear();
//                                    if (!StringUtils.isEmpty(CacheUtils.getCache(HttpApi.goods_ol, true))){
//                                        JSONObject jsonObject = new JSONObject(CacheUtils.getCache(HttpApi.goods_ol,false));
//                                        JSONArray jsonArray = null;
//                                        jsonArray = new JSONArray(jsonObject.getString("data"));
//                                        Log.i("this_content", jsonObject.getString("data") + "json");
//                                        for (int i = 0; i < jsonArray.length(); i++) {
//                                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
//                                            Goods goods = new Goods();
//                                            goods.setGoodsid(jsonObject1.getString("ID"));
//                                            goods.setGoodsname(jsonObject1.getString("Title"));
//                                            goods.setGoodsallpople(jsonObject1.getString("TotalCount"));
//                                            goods.setGoods_current(jsonObject1.getString("TradingCount"));
//                                            goods.setProductID(jsonObject1.getString("ProductID"));
//                                            goods.setState(jsonObject1.getString("State"));
//                                            goods.setGoodsxian(jsonObject1.getString("LimitCount"));
//                                            goods.setGoods_surplus(jsonObject1.getString("LeftCount"));
//                                            goods.setImagepath(jsonObject1.getString("MainImg"));
//                                            goods.setGoodspnumber(jsonObject1.getString("PNumber"));
//                                            goodses.add(goods);
//                                        }
//                                        Message message = new Message();
//                                        Bundle bundle = new Bundle();
//                                        bundle.putParcelableArrayList("goods",goodses);
//                                        message.setData(bundle);
//                                        message.what = Numbers.TWO;
//                                        handler.sendMessage(message);
//                                    }else {
                                        MyThreadPoolManager.getInstance().execute(runnableGoods);
//                                    }
                                }catch (Exception e){
                                    LogUtil.e("FragmentShopping handler error:",e.toString());
                                }
                            }
                            //listview item点击事件
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    try {
                                        if (number == 0){
                                            ((MyActivity)getActivity()).setState(0);
                                            linearLayout_loading.setVisibility(View.VISIBLE);
                                            linearLayout_delete.setVisibility(View.GONE);
                                            id_ol = Integer.parseInt(categorys.get(position).getCoding());
                                            if (categorys.get(position).getCoding().equals("0")){
                                                cateid = "-1";
                                            }else {
                                                cateid = categorys.get(position).getCategoryid();
                                            }
                                            index = 1;
                                            number = 1;
                                            STATE = position;
                                            linearLayout_add_we.setVisibility(View.GONE);
                                            code = categorys.get(position).getCoding();
                                            goodses.clear();
                                            listview_content.setAdapter(null);
//                                            listView_ol.removeAllViews()；
//                                            if (position == 0){
//                                                postions = 1;
//                                                if (!StringUtils.isEmpty(CacheUtils.getCache(HttpApi.goods_ol, true))){
//                                                    JSONObject jsonObject = new JSONObject(CacheUtils.getCache(HttpApi.goods_ol,false));
//                                                    JSONArray jsonArray = null;
//                                                    jsonArray = new JSONArray(jsonObject.getString("data"));
//                                                    Log.i("this_content", jsonObject.getString("data") + "json");
//                                                    for (int i = 0; i < jsonArray.length(); i++) {
//                                                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
//                                                        Goods goods = new Goods();
//                                                        goods.setGoodsid(jsonObject1.getString("ID"));
//                                                        goods.setGoodsname(jsonObject1.getString("Title"));
//                                                        goods.setGoodsallpople(jsonObject1.getString("TotalCount"));
//                                                        goods.setGoods_current(jsonObject1.getString("TradingCount"));
//                                                        goods.setProductID(jsonObject1.getString("ProductID"));
//                                                        goods.setState(jsonObject1.getString("State"));
//                                                        goods.setGoodsxian(jsonObject1.getString("LimitCount"));
//                                                        goods.setGoods_surplus(jsonObject1.getString("LeftCount"));
//                                                        goods.setImagepath(jsonObject1.getString("MainImg"));
//                                                        goods.setGoodspnumber(jsonObject1.getString("PNumber"));
//                                                        goodses.add(goods);
//                                                    }
//                                                    Message message = new Message();
//                                                    Bundle bundle = new Bundle();
//                                                    bundle.putParcelableArrayList("goods",goodses);
//                                                    message.setData(bundle);
//                                                    message.what = Numbers.TWO;
//                                                    handler.sendMessage(message);
//                                                }else {
                                                    MyThreadPoolManager.getInstance().execute(runnableGoods);
//                                                }
//                                            }else {
//                                                postions = 0;
//                                                MyThreadPoolManager.getInstance().execute(runnableGoods);
//                                            }
                                        }
                                    }catch (Exception e){
                                        LogUtil.e("FragmentShopping handler error:",e.toString());
                                    }
                                }
                            });
                        }catch (NullPointerException e){
                            e.printStackTrace();
                        }
                    }else {
                        linearLayout_delete.setVisibility(View.VISIBLE);
                    }
                }else {
                    linearLayout_delete.setVisibility(View.VISIBLE);
                }

            }else if (msg.what == Numbers.TWO){
                LogUtil.i("all_time",(System.currentTimeMillis()-time)+"alltime");
                linearLayout_loading.setVisibility(View.GONE);
                number = 0;
                arrayList_goods = new ArrayList<Goods>();
                arrayList_goods = msg.getData().getParcelableArrayList("goods");
                if (arrayList_goods != null){
                    linearLayout_delete.setVisibility(View.GONE);
                    if (arrayList_goods.size()>0){
                        if (STATE == 1){
                            linearLayout_add_we.setVisibility(View.GONE);
                        }else {
                            linearLayout_delete.setVisibility(View.GONE);
                        }
                        dataOnclicklistner = new AdapterDetail.DataOnclicklistner() {
                            @Override
                            protected void dataOnclicklistner(Integer tag, View v) {
                                switch (v.getId()){
                                    case R.id.imagebutton_gwc:
                                        String token = SaveShared.tokenget(getActivity());
                                        if (!TextUtils.isEmpty(token)){
                                            Log.i(getActivity()+"","gwc+shoppingcar");
                                            linearLayout_loading.setVisibility(View.VISIBLE);
                                            pid = arrayList_goods.get(tag).getGoodsid();
                                            limitcount = arrayList_goods.get(tag).getGoodsxian();
                                            surpcount = arrayList_goods.get(tag).getGoods_surplus();
                                            MyThreadPoolManager.getInstance().execute(runnableGoodsNumber);
                                        }else {
                                            cToast = CToast.makeText(getActivity(), "请登录", 800);
                                            cToast.show();
                                        }
                                        break;
                                }
                            }
                        };
                        adapterdetail = new AdapterDetail(code,getActivity(),arrayList_goods,dataOnclicklistner,imageloderinit.imageLoader,imageloderinit.options);
                        listview_content.setAdapter(adapterdetail);
                        if (arrayList_goods != null){
                            if (arrayList_goods.size()>0){
                                listview_content.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        Intent intent = new Intent();
                                        intent.putExtra("outid", arrayList_goods.get(position).getGoodsid());
                                        intent.putExtra("prid", arrayList_goods.get(position).getProductID());
                                        intent.putExtra("cloud", String.valueOf(Numbers.ONE));
                                        intent.putExtra("index", Numbers.ONE);
                                        intent.setClass(getActivity(), ActivityGoodsDateil.class);
                                        startActivity(intent);
                                    }
                                });
                            }
                        }
                    }else {
                        if (STATE == 1){
                            linearLayout_add_we.setVisibility(View.VISIBLE);
                        }else {
                            linearLayout_delete.setVisibility(View.VISIBLE);
                        }
                    }
                }else {
                    if (STATE == 1){
                        linearLayout_add_we.setVisibility(View.VISIBLE);
                    }else {
                        linearLayout_delete.setVisibility(View.VISIBLE);
                    }
                }
            }else if (msg.what == Numbers.THREE){
                statese = new Statese();
                statese = msg.getData().getParcelable("state");
                linearLayout_loading.setVisibility(View.GONE);
                if (statese != null){
                    if (statese.getState().equals(String.valueOf(Numbers.ONE))){
//                        MyActivity.radioButton_one.setChecked(true);
                        if(!TextUtils.isEmpty(statese.getData())){
                            if (statese.getData().equals("true")){
                                try {
                                    MyThreadPoolManager.getInstance().execute(runnableCarNumber);
                                }catch (Exception e){
                                    LogUtil.e("FragmentShopping handler error:",e.toString());
                                }
                            }
                        }
                        if (getActivity() != null){
                            cToast = CToast.makeText(getActivity(), "加入购物车成功~", 600);
                            cToast.show();
                        }
                    }
                }
            }else if (msg.what == Numbers.FOUR){
                linearLayout_loading.setVisibility(View.GONE);
                statese = new Statese();
                statese = msg.getData().getParcelable("content_number");
                if (statese != null){
                    if (statese.getState() != null){
                        if (statese.getState().equals(String.valueOf(Numbers.ONE))){
                            if (!limitcount.equals("0")){
                                if (Integer.parseInt(limitcount)>0){
                                    if (Integer.parseInt(statese.getData())<Integer.parseInt(limitcount)){
                                        try {
                                            MyThreadPoolManager.getInstance().execute(runnableAddGoods);
                                        }catch (Exception e){
                                            LogUtil.e("FragmentShopping handler error:",e.toString());
                                        }
                                    }else {
                                        if (getActivity() != null) {
                                            cToast = CToast.makeText(getActivity(), "没有更多商品了", 600);
                                            cToast.show();
                                        }
                                    }
                                }
                            }else {
                                if (Integer.parseInt(statese.getData())<Integer.parseInt(surpcount)){
                                    MyThreadPoolManager.getInstance().execute(runnableAddGoods);
                                }else {
                                    if (getActivity() != null){
                                        cToast = CToast.makeText(getActivity(), "没有更多商品了", 600);
                                        cToast.show();
                                    }
                                }
                            }
                        }else {
                            if (statese.getMsg().equals("token失效")){
                                MyThreadPoolManager.getInstance().execute(runnableSingle);
                            }else {
                                cToast = CToast.makeText(getActivity(),statese.getMsg(),600);
                                cToast.show();
                            }
                        }
                    }
                }
            }else if (msg.what == 5){
                statese = new Statese();
                statese = msg.getData().getParcelable("content");
                if (statese!=null) {
                    if (statese.getState() != null) {
                        if (statese.getState().equals(String.valueOf(Numbers.ONE))) {
                            if (!statese.getData().equals("null")) {
                                SaveShared.sharedcarnumbersave(getActivity(), statese.getData());
                                if (!TextUtils.isEmpty(statese.getData())) {
                                    MyActivity.textView.setText(statese.getData());
                                }
                            }
                        }
                    }
                }
            }else if (msg.what == 6){
                statese = msg.getData().getParcelable("this");
                if (statese != null){
                    if (statese.getState().equals(String.valueOf(Numbers.ONE))){
                        try{
                            MyThreadPoolManager.getInstance().execute(runnableGoodsNumber);
                        }catch (Exception e){
                            LogUtil.e("MyActivity handler error:",e.toString());
                        }
                    }
                }
            }

        }
    };


    public void setCurrentFragment (int index){
        ((MyActivity)getActivity()).setCurrentFargmentIndex(index);
    }
    /**
     * 获取商品件数
     */
    private Runnable runnableCarNumber = new Runnable() {
        @Override
        public void run() {
            statese = new Statese();
            //从sharedPreferences中获取储存的token
            String token = SaveShared.tokenget(getActivity());
            //http网络请求
            statese = HttpTransfeData.getcarnumberhttp(statese,getActivity(),token);
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putParcelable("content",statese);
            message.what = Numbers.FIVE;
            message.setData(bundle);
            handler.sendMessage(message);
        }
    };
    //添加商品请求
    private Runnable runnableAddGoods = new Runnable() {
        @Override
        public void run() {
            statese = new Statese();
            String token = SaveShared.tokenget(getActivity());
            String uid = SaveShared.uid(getActivity());
            String num = "1";
            statese = HttpTransfeData.goodsaddshoppingcat(getActivity(),pid,uid,token,num);
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putParcelable("state",statese);
            message.what = Numbers.THREE;
            message.setData(bundle);
            handler.sendMessage(message);
        }
    };
    /**
     * 获取购物车商品数量
     */
    private Runnable runnableGoodsNumber = new Runnable() {
        @Override
        public void run() {
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
    /**
     * 商品数据请求
     * @param coding
     */
    private Runnable runnableGoods = new Runnable() {
        @Override
        public void run() {
            time = System.currentTimeMillis();
            arrayList_goods = new ArrayList<Goods>();
            if (getActivity() != null){
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("data",Context.MODE_PRIVATE);
                //城市名称
                String cityname = null;
                if (!StringUtils.isEmpty(sharedPreferences.getString("address_ol", ""))){
                    if (sharedPreferences.getString("address_ol", "").substring(sharedPreferences.getString("address_ol", "").length() - 1, sharedPreferences.getString("address_ol", "").length()).equals("市")){
                        cityname = sharedPreferences.getString("address_ol", "").substring(0, sharedPreferences.getString("address_ol", "").length()-1);
                    }else {
                        cityname = "西安";
                    }
                }else {
                    cityname = "西安";
                }
                //根据城市名称获取相应的县区
                arrayList_goods = HttpTransfeData.httpgoodsdata(getActivity(),cateid,index,cityname,postions);
                if (arrayList_goods != null){
                    goodses.addAll(arrayList_goods);
                }
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("goods",goodses);
                message.what = Numbers.TWO;
                message.setData(bundle);
                handler.sendMessage(message);
            }
        }
    };

    /**
     * 下拉刷新
     * @param pullToRefreshLayout
     */
    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        new Handler()
        {
            @Override
            public void handleMessage(Message msg)
            {
                goodses.clear();
                index = 1;
                try {
                    MyThreadPoolManager.getInstance().execute(runnableGoods);
                }catch (Exception e){
                    LogUtil.e("FragmentShopping onRefresh error:",e.toString());
                }
                layout.refreshFinish(PullToRefreshLayout.SUCCEED);
            }
        }.sendEmptyMessageDelayed(0, 2000);
    }

    /**
     * 上拉加载
     * @param pullToRefreshLayout
     */
    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        new Handler()
        {
            @Override
            public void handleMessage(Message msg)
            {
                //
                postions = 0;
                index+=1;
                try {
                    MyThreadPoolManager.getInstance().execute(runnableGoods);
                }catch (Exception e){
                    LogUtil.e("FragmentShopping onLoadMore error:",e.toString());
                }
                layout.refreshFinish(PullToRefreshLayout.SUCCEED);
            }
        }.sendEmptyMessageDelayed(0, 2000);
    }

    /**
     * onclick 点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.imagebutton_shoppingeach:
                try{
                    intent.setClass(getActivity(), ActivityShoppingEach.class);
                    startActivity(intent);
                }catch (Exception e){
                    LogUtil.e("FragmentShopping onclick",e.toString());
                }
                break;
            case R.id.imagebutton_shoppingdingwei:
                onStart();
                break;
            case R.id.btn_add_we:
                intent.setClass(getActivity(), ActivityAddWe.class);
                startActivity(intent);
                break;
        }
    }
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
     * activity销毁时依附与界面的线程随之销毁
     */
    @Override
    public void onDestroy() {
        if (arrayList_goods != null){
            arrayList_goods.clear();
        }
        if (arrayList_ol != null){
            arrayList_ol.clear();
        }
        super.onDestroy();
    }
}

