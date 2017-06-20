package com.example.cloudAndPurchasing.fragment;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.*;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.*;
import android.widget.*;
import com.example.cloudAndPurchasing.LocationApplication;
import com.example.cloudAndPurchasing.MyActivity;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.activity.activitymain.ActivityCloudRoom;
import com.example.cloudAndPurchasing.activity.activityshopping.ActivityGoodsDateil;
import com.example.cloudAndPurchasing.activity.activityshoppingcat.ActivityIndent;
import com.example.cloudAndPurchasing.adapter.adaptershopping.AdapterIndent;
import com.example.cloudAndPurchasing.customcontrol.listviewupdown.PullToRefreshLayout;
import com.example.cloudAndPurchasing.http.HttpTransfeData;
import com.example.cloudAndPurchasing.http.Numbers;
import com.example.cloudAndPurchasing.http.SaveShared;
import com.example.cloudAndPurchasing.imageloder.Imageloderinit;
import com.example.cloudAndPurchasing.kind.*;
import com.example.cloudAndPurchasing.manager.MyThreadPoolManager;
import com.example.cloudAndPurchasing.popupwindows.SharePopupwindows;
import com.example.cloudAndPurchasing.times.CToast;
import com.example.cloudAndPurchasing.zhi.LogUtil;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import org.json.JSONException;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;

/**
 * Created by Administrator on 2016/3/21 0021.
 */
public class ShoppingCarFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener, PullToRefreshLayout.OnRefreshListener {
    private Button imageButton,button_car_ok,button_car_canler;
    private Button button,button_car_add,button_car_jian;
    private EditText editText_ol;
    private LinearLayout linearLayout,linearLayout_car;
    private ListView listView;
    private String vb = null;
    public static TextView textView,textView_ol;
    private PopupWindow popupWindow,popupWindow1;
    private boolean falg = false,falg1 = false;
    private CheckBox checkBox;
    private PullToRefreshLayout layout;
    private View view;
    private Imageloderinit imageloderinit;
    private AdapterIndent.Shoppingonclick shoppingonclick;
    boolean flag = true,flag1 = true;
    private ArrayList<Goods> arrayList,arrayList_ol,arrayList_sum;
    private static AdapterIndent adapterIndent;
    private Integer del_ol = 0;
    private Statese statese;
    private String sum = null;
    private int gn=0,
            count = 0;
    private int index = 0;
    private String ip = null;
    private String number_ol = null;
    private Button button_delete,button_over,button_car;
    private HashMap<Integer,Goods> hashMap;
    private String num = "0";
    private String pid = null;
    private LocalBroadcastManager localBroadcastManager;
    private LinearLayout linearLayout_ol,linearLayout_delete;
    private SharedPreferences sharedPreferences;
    private CToast cToast;
    private String address;
    private String money_ol;
    private String limit_count;
    private String num_ol;
    private String surplus;
    private Integer tag_ol;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
         view = inflater.inflate(R.layout.fragmentfshoppingcat_layout,null);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ip = getLocalIpAddress();
        initData(view);
        initview(view);
    }

    //    private void Ipinit() {
//        //获取wifi服务
//        WifiManager wifiManager = (WifiManager)getActivity().getSystemService(Context.WIFI_SERVICE);
//        //判断wifi是否开启
//        if (!wifiManager.isWifiEnabled()) {
//            wifiManager.setWifiEnabled(true);
//        }
//        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
//        int ipAddress = wifiInfo.getIpAddress();
//         ip = intToIp(ipAddress);
//    }
    //编辑ip地址
    private String intToIp(int ipAddress) {
        return (ipAddress & 0xFF ) + "." +
                ((ipAddress >> 8 ) & 0xFF) + "." +
                ((ipAddress >> 16 ) & 0xFF) + "." +
                ( ipAddress >> 24 & 0xFF) ;
    }
        //gprs获取ip；
        public String getLocalIpAddress()
        {
            try
            {
                for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();)
                {
                    NetworkInterface intf = en.nextElement();
                    for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();)
                    {
                        InetAddress inetAddress = enumIpAddr.nextElement();
                        if (!inetAddress.isLoopbackAddress())
                        {
                            Log.e("WifiPreference IpAddress", inetAddress.getHostAddress().toString());
                            return ((inetAddressToInt(inetAddress) & 0xff)+"."+(inetAddressToInt(inetAddress)>>8 & 0xff)+"."
                                    +(inetAddressToInt(inetAddress)>>16 & 0xff)+"."+(inetAddressToInt(inetAddress)>>24 & 0xff));
                        }
                    }
                }
            }
            catch (SocketException ex)
            {
                Log.e("WifiPreference IpAddress error:", ex.toString());
            }
            return null;
        }
        //转换数据类型
        public static int inetAddressToInt(InetAddress inetAddr)
            throws IllegalArgumentException {
        byte[] addr = inetAddr.getAddress();
        return ((addr[3] & 0xff) << 24) | ((addr[2] & 0xff) << 16) |
                ((addr[1] & 0xff) << 8) | (addr[0] & 0xff);
    }
    /**
     * 初始化数据
     * @param v
     */
    public void initData(View v){
        LogUtil.i("FragmentShoppingCar initData start","");
        sharedPreferences = getActivity().getSharedPreferences("data",Context.MODE_PRIVATE);
        linearLayout = (LinearLayout)v.findViewById(R.id.linearlayout_shoppingdateil_loading);
        linearLayout_delete = (LinearLayout)v.findViewById(R.id.linearlayout_shoppingdateil_delete);
        linearLayout.setVisibility(View.VISIBLE);
        localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        button_car = (Button)v.findViewById(R.id.button_car_shopping);
        address = sharedPreferences.getString("addressLocation","");
        String car = getActivity().getIntent().getStringExtra("car");
        if (car != null){
            if (car.equals(String.valueOf(Numbers.ONE))){
                button_car.setVisibility(View.VISIBLE);
                MyActivity.frameLayout.setVisibility(View.GONE);
            }else if (car.equals(String.valueOf(Numbers.TWO))){
                button_car.setVisibility(View.VISIBLE);
                MyActivity.frameLayout.setVisibility(View.GONE);
            }
        }
        index = 1;
        hashMap = new HashMap<Integer, Goods>();
        arrayList_sum = new ArrayList<Goods>();
        imageloderinit = new Imageloderinit(getActivity());
        try {
            MyThreadPoolManager.getInstance().execute(runnable);
        }catch (Exception e){
            LogUtil.e("FragmentShoppingCar initData",e.toString());
        }finally {
            LogUtil.i("FragmentShoppingCar end.","");
        }
        listView = (ListView)v.findViewById(R.id.listview_indent);
        listView.setOnScrollListener(new PauseOnScrollListener(imageloderinit.imageLoader,true,false));
        textView = (TextView)v.findViewById(R.id.textveiw_shopping_number);
        arrayList = new ArrayList<Goods>();
        arrayList_ol = new ArrayList<Goods>();
    }

    /**
     * 请求数据
     */
      private Runnable runnable = new Runnable() {
        @Override
         public synchronized void run() {
            String uid = SaveShared.uid(getActivity());
            String token = SaveShared.tokenget(getActivity());
            if (token != ""){
                arrayList = HttpTransfeData.shoppingcathttp(getActivity(),uid,token,index);
                if (arrayList != null){
                    arrayList_sum.addAll(arrayList);
                }
                Message message = new Message();
                message.what = Numbers.THREE;
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("th_ol",arrayList_sum);
                message.setData(bundle);
                handler.sendMessage(message);
            }else {
                linearLayout.setVisibility(View.GONE);
            }
         }
      };
    /**
     * 删除商品
     */
    private Runnable runnableDeleteGoods = new Runnable() {
        @Override
        public synchronized void run() {
            statese = new Statese();
            String token = SaveShared.tokenget(getActivity());
            String uid = SaveShared.uid(getActivity());
            statese = HttpTransfeData.shoppingcatdeletehttp(getActivity(),token,uid,vb);
            Message message = new Message();
            message.what = Numbers.TWO;
            Bundle bundle = new Bundle();
            bundle.putParcelable("msg", statese);
            message.setData(bundle);
            handler.sendMessage(message);
        }
    };

    /**
     * 初始化控件
     * @param v
     */
    public void initview(View v){
        linearLayout.setVisibility(View.VISIBLE);
        textView_ol = (TextView)v.findViewById(R.id.textvieww_car_numberol);
        button = (Button)v.findViewById(R.id.button_submit_indent);
        imageButton = (Button)v.findViewById(R.id.imagebutton_shoppingcat);
        layout = (PullToRefreshLayout)v.findViewById(R.id.layout_catup);
        checkBox = (CheckBox)v.findViewById(R.id.checked_all);
        button_car.setOnClickListener(this);
        textView.setText("0");
        String token = SaveShared.tokenget(getActivity());
        if (!StringUtils.isEmpty(token)){
            if (!StringUtils.isEmpty(LocationApplication.CAR_NUMBER)){
                textView_ol.setText(LocationApplication.CAR_NUMBER);
                LocationApplication.CAR_NUMBER = null;
            }else if (!StringUtils.isEmpty(SharePreferencesUlits.getString(getActivity(), Constants.CAR_NUMBER,Constants.NUll_VALUE))){
                number_ol = SharePreferencesUlits.getString(getActivity(), Constants.CAR_NUMBER,Constants.NUll_VALUE);
                textView_ol.setText(number_ol);
                MyActivity.textView.setText(number_ol);
            }else {
                textView_ol.setText("0");
            }
        }else {
            textView_ol.setText("0");
            linearLayout.setVisibility(View.GONE);
        }
        button.setOnClickListener(this);
        imageButton.setOnClickListener(this);
        listView.setOnItemClickListener(this);
        checkBox.setOnClickListener(this);
        layout.setOnRefreshListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_submit_indent:
                try {
                    if (hashMap.size()>0){
                        sum = SaveShared.buildJson(getActivity(),hashMap);
//                        mhandler.post(runnableOrderNo);
                        MyThreadPoolManager.getInstance().execute(runnableOrderNo);
                    }else {
                        if (getActivity() != null){
                            cToast = CToast.makeText(getActivity(),"请选择商品",600);
                            cToast.show();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.imagebutton_shoppingcat:
                SharePopupwindows sharePopupwindows = new SharePopupwindows(getActivity(),view);
//                sharePopupwindows.show();
                break;
            case R.id.checked_all:
                if (checkBox.isChecked()){
                    Message message = new Message();
                    message.what = Numbers.THREE;
                    Bundle bundle = new Bundle();
                    int all = 0;
                    for (int c = 0 ; c<arrayList.size();c++){
                        arrayList.get(c).setXuanzhong(true);
                        int money  = Integer.parseInt(arrayList.get(c).getGoodsmoney())*Integer.parseInt(arrayList.get(c).getCount());
                        all+=money;
                        gn+=Integer.parseInt(arrayList.get(c).getCount());
                        hashMap.put(c,arrayList.get(c));
                    }
                    textView.setText(all+"");
                    bundle.putParcelableArrayList("th_ol",arrayList);
                    message.setData(bundle);
                    handler.handleMessage(message);

                }else {
                    Message message = new Message();
                    message.what = Numbers.THREE;
                    Bundle bundle = new Bundle();
                    for (int c = 0 ; c<adapterIndent.getCount();c++){
                        arrayList.get(c).setXuanzhong(false);
                    }
                    gn = 0;
                    hashMap = new HashMap<Integer, Goods>();
                    textView.setText("0");
                    bundle.putParcelableArrayList("th_ol", arrayList);
                    message.setData(bundle);
                    handler.sendMessage(message);
                }
                break;
            case R.id.button_ensure_delete:
                linearLayout_ol.setVisibility(View.VISIBLE);
//                mhandler.post(runnableDeleteGoods);
                MyThreadPoolManager.getInstance().execute(runnableDeleteGoods);
                break;
            case R.id.button_cancel_delete:
                popupWindow.dismiss();
                backgroundAlha(1f);
                break;
            case R.id.button_car_shopping:
                try {
                    Intent intent1 = new Intent();
                    String car = getActivity().getIntent().getStringExtra("car");
                    if (car.equals(String.valueOf(Numbers.ONE))){
                        intent1.setClass(getActivity(), ActivityGoodsDateil.class);
                    }else {
                        intent1.setClass(getActivity(), ActivityCloudRoom.class);
                    }
                    startActivity(intent1);
                }catch (Exception e){
                    LogUtil.e("FragmentShoppingcar button_car_shopping 245：",e.toString());
                }
                break;
            case R.id.car_imagebutton_jian:
                try {
                    if (editText_ol.getText().toString() != null){
                        if (!TextUtils.isEmpty(editText_ol.getText().toString())){
                            if (!editText_ol.getText().toString().equals("0")){
                                int c = Integer.parseInt(editText_ol.getText().toString());
                                if (c>1){
                                    c--;
                                    editText_ol.setText(String.valueOf(c));
                                }
                            }else {
                                editText_ol.setText("1");
                            }
                        }else {
                            editText_ol.setText("1");
                        }
                    }else {
                        editText_ol.setText("1");
                    }
                }catch (Exception e){
                    LogUtil.e("FragmentCar car_imagebutton_jian error：",e.toString());
                }

                break;
            case R.id.car_imagebutton_add:
                try {
                    if (editText_ol.getText().toString() != null && getActivity() != null){
                        if (!TextUtils.isEmpty(editText_ol.getText().toString())){
                            int c_ol = Integer.parseInt(editText_ol.getText().toString());
                            if (Integer.parseInt(limit_count)>0){
                                if (c_ol<Integer.parseInt(limit_count)){
                                    c_ol++;
                                    editText_ol.setText(String.valueOf(c_ol));
                                }else {
                                    cToast = CToast.makeText(getActivity(),"您选择的商品数量已经达到限购界限~",600);
                                    cToast.show();
                                }
                            }else {
                                if (Integer.parseInt(surplus)>c_ol){
                                    c_ol++;
                                    editText_ol.setText(String.valueOf(c_ol));
                                }else {
                                    cToast = CToast.makeText(getActivity(),"没有更多商品了~",600);
                                    cToast.show();
                                }
                            }
                        } else {
                            editText_ol.setText("1");
                        }
                    }else {
                        editText_ol.setText("1");
                    }
                }catch (Exception e){
                    LogUtil.e("FragmentShoppingCar car_imagebutton_add error:",e.toString());
                }
                break;


            case R.id.button_car_ok:
                try {
                    if (editText_ol.getText().toString()!= null && getActivity() != null){
                        if (!TextUtils.isEmpty(editText_ol.getText().toString())){
                            if (Integer.parseInt(editText_ol.getText().toString())<=Integer.parseInt(surplus)){
                                if (Integer.parseInt(limit_count)>0){
                                    if (Integer.parseInt(editText_ol.getText().toString())<=Integer.parseInt(limit_count)){
                                        if (editText_ol.getText().toString().equals("0")){
                                            editText_ol.setText("1");
                                        }
                                        num = String.valueOf(Integer.parseInt(editText_ol.getText().toString())-Integer.parseInt(num_ol));
                                        gn+=Integer.parseInt(num);
                                        updateview(tag_ol, Integer.parseInt(editText_ol.getText().toString()));
                                        addmoney(1*Integer.parseInt(num),true);
                                        linearLayout_car.setVisibility(View.VISIBLE);
                                        MyThreadPoolManager.getInstance().execute(runnableAddGoods);
                                    }else {
                                        cToast = CToast.makeText(getActivity(),"没有更多商品了~",600);
                                        cToast.show();
                                    }
                                }else {
                                    if (editText_ol.getText().toString().equals("0")){
                                        editText_ol.setText("1");
                                    }
                                    num = String.valueOf(Integer.parseInt(editText_ol.getText().toString())-Integer.parseInt(num_ol));
                                    gn+=Integer.parseInt(num);
                                    updateview(tag_ol, Integer.parseInt(editText_ol.getText().toString()));
                                    addmoney(1*Integer.parseInt(num),true);
                                    linearLayout_car.setVisibility(View.VISIBLE);
                                    MyThreadPoolManager.getInstance().execute(runnableAddGoods);
                                }
                            }else {
                                cToast = CToast.makeText(getActivity(),"没有更多商品了~",600);
                                cToast.show();
                            }
                        }else {
                            cToast = CToast.makeText(getActivity(),"请输入商品数量~",600);
                            cToast.show();
                        }
                    }else {
                        cToast = CToast.makeText(getActivity(),"请输入商品数量~",600);
                        cToast.show();
                    }
                }catch (Exception e){
                    LogUtil.e("FragmentCar button_car_ok error：",e.toString());
                }
                break;
            case R.id.button_car_canler:
                popupWindow1.dismiss();
                break;
        }
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    /**
     * 数据刷新
     */
    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 2:
//                    arrayList_ol = new ArrayList<Goods>();
//                    arrayList_ol = msg.getData().getParcelableArrayList("th");
                    linearLayout_ol.setVisibility(View.GONE);
                    statese = new Statese();
                    statese = msg.getData().getParcelable("msg");
                    if (statese != null){
                        if (!StringUtils.isEmpty(statese.getState())){
                            if (statese.getState().equals(String.valueOf(Numbers.ONE))){
                                if (getActivity() != null){
                                    number_ol = SaveShared.getcarnumber(getActivity());
                                    if (!TextUtils.isEmpty(number_ol)){
                                        int num = Integer.parseInt(number_ol);
                                        num--;
                                        if (num>=0){
                                            textView_ol.setText(String.valueOf(num));
                                            MyActivity.textView.setText(String.valueOf(num));
                                            SaveShared.sharedcarnumbersave(getActivity(),String.valueOf(num));
                                        }else {
                                            textView_ol.setText("0");
                                            MyActivity.textView.setText("0");
                                            SaveShared.sharedcarnumbersave(getActivity(),String.valueOf(0));
                                        }
                                    }else {
                                        textView_ol.setText("0");
                                        MyActivity.textView.setText("0");
                                    }
                                }
                                gn = 0;
                                hashMap.clear();
                                MyThreadPoolManager.getInstance().execute(runnable);
                                popupWindow.dismiss();
                                backgroundAlha(1f);
                                cToast = CToast.makeText(getActivity(),statese.getMsg(),600);
                                cToast.show();
                            }else {
                                String token = SaveShared.tokenget(getActivity());
                                if (token != ""){
                                    if (getActivity() != null){
                                        number_ol = SaveShared.getcarnumber(getActivity());
                                        textView_ol.setText(number_ol);
                                        MyActivity.textView.setText(number_ol);
                                    }else {
                                        textView_ol.setText("0");
                                    }
                                }else {
                                    textView_ol.setText("0");
                                    linearLayout.setVisibility(View.GONE);
                                }
                            }
                        }
                    }else {
                        String token = SaveShared.tokenget(getActivity());
                        if (token != ""){
                            if (getActivity() != null){
                                number_ol = SaveShared.getcarnumber(getActivity());
                                textView_ol.setText(number_ol);
                                MyActivity.textView.setText(number_ol);
                            }else {
                                textView_ol.setText("0");
                            }
                        }else {
                            textView_ol.setText("0");
                            linearLayout.setVisibility(View.GONE);
                        }
                    }
                    break;
                case 3:
                    linearLayout.setVisibility(View.GONE);
                    arrayList_ol = new ArrayList<Goods>();
                    arrayList_ol.clear();
                    arrayList_ol = msg.getData().getParcelableArrayList("th_ol");
                    if (arrayList_ol != null){
                        if (arrayList_ol.size()>0){
                            count = arrayList_ol.size();
                            linearLayout_delete.setVisibility(View.GONE);
                            int all = 0;
                            gn = 0;
                            for (int c = 0 ; c<arrayList_ol.size();c++){
                                arrayList_ol.get(c).setXuanzhong(true);
                                int money  = Integer.parseInt(arrayList_ol.get(c).getGoodsmoney())*Integer.parseInt(arrayList_ol.get(c).getCount());
                                all+=money;
                                gn+=Integer.parseInt(arrayList_ol.get(c).getCount());
                                hashMap.put(c,arrayList_ol.get(c));
                            }
                            textView.setText(all+"");
                            shoppingonclick = new AdapterIndent.Shoppingonclick() {
                                @Override
                                protected void addonclick(Integer tag, View v) {
                                    switch (v.getId()){
                                        case R.id.imagebutton_add:
                                            if (Integer.parseInt(arrayList_ol.get(tag).getGoods_surplus())>Integer.parseInt(arrayList_ol.get(tag).getCount())){
                                                int c = Integer.parseInt(arrayList_ol.get(tag).getCount());
                                                if (Integer.parseInt(arrayList_ol.get(tag).getGoodsxian())>0){
                                                    if (Integer.parseInt(arrayList_ol.get(tag).getGoodsxian())>c){
                                                        c++;
                                                        pid = arrayList_ol.get(tag).getID();
                                                        num = "1";
                                                        MyThreadPoolManager.getInstance().execute(runnableAddGoods);
                                                        updateview(tag, c);
                                                        if (arrayList_ol.get(tag).getXuanzhong()&&Integer.parseInt(arrayList_ol.get(tag).getGoods_surplus())>=Integer.parseInt(arrayList_ol.get(tag).getCount())){
                                                            int money = 1;
                                                            adapterIndent.updateview_th(listView,tag,true);
                                                            gn+=1;
                                                            addmoney(money,true);
                                                        }
                                                    }else {
                                                        if (getActivity() != null){
                                                            cToast = CToast.makeText(getActivity(),"没有更多的限购商品了~",600);
                                                            cToast.show();
                                                        }
                                                    }
                                                }else {
                                                    c++;
                                                    pid = arrayList_ol.get(tag).getID();
                                                    num = "1";
                                                    MyThreadPoolManager.getInstance().execute(runnableAddGoods);
                                                    updateview(tag, c);
                                                    if (arrayList_ol.get(tag).getXuanzhong()&&Integer.parseInt(arrayList_ol.get(tag).getGoods_surplus())>=Integer.parseInt(arrayList_ol.get(tag).getCount())){
                                                        int money = 1;
                                                        adapterIndent.updateview_th(listView,tag,true);
                                                        gn+=1;
                                                        addmoney(money,true);
                                                    }
                                                }
                                            }
                                            break;
                                        case R.id.imagebutton_jian:
                                            if (Integer.parseInt(arrayList.get(tag).getCount())>1){
                                                int ol = Integer.parseInt(arrayList_ol.get(tag).getCount());
                                                ol--;
                                                pid = arrayList_ol.get(tag).getID();
                                                num = "-1";
                                                MyThreadPoolManager.getInstance().execute(runnableAddGoods);
                                                updateview(tag, ol);
                                                if (arrayList_ol.get(tag).getXuanzhong()){
                                                    int money = 1*(Integer.parseInt(arrayList_ol.get(tag).getGoodsmoney()));
                                                    adapterIndent.updateview_th(listView,tag,true);
                                                    if (gn>0){
                                                        gn-=1;
                                                    }
                                                    addmoney(money,false);
                                                }
                                            }
                                            break;
                                        case R.id.imagebutton_delete:
//                           ShoppingPopup shoppingPopup = new ShoppingPopup(getActivity(),tag);
//                           shoppingPopup.show(view);
                                            arrayList_sum = new ArrayList<Goods>();
                                            del_ol = tag;
                                            falg = true;
                                            backgroundAlha(0.5f);
                                            deletepopup(falg,arrayList_ol.get(tag).getGoodsid(),v);
                                            break;
                                        case R.id.button_all:
                                            if (Integer.parseInt(arrayList_ol.get(tag).getGoods_surplus())>Integer.parseInt(arrayList_ol.get(tag).getCount())){
                                                int vb = Integer.parseInt(arrayList_ol.get(tag).getGoods_surplus())-Integer.parseInt(arrayList_ol.get(tag).getCount());
                                                if (Integer.parseInt(arrayList_ol.get(tag).getGoodsxian())>0){
                                                    if (Integer.parseInt(arrayList_ol.get(tag).getGoodsxian())>Integer.parseInt(arrayList_ol.get(tag).getCount())){
                                                        int vb1 =0;
                                                        if (Integer.parseInt(arrayList_ol.get(tag).getGoods_surplus())>Integer.parseInt(arrayList_ol.get(tag).getGoodsxian())){
                                                            vb1 = Integer.parseInt(arrayList_ol.get(tag).getGoodsxian())-Integer.parseInt(arrayList_ol.get(tag).getCount());
                                                            updateview(tag, Integer.parseInt(arrayList_ol.get(tag).getGoodsxian()));
                                                        }else {
                                                            vb1 = Integer.parseInt(arrayList_ol.get(tag).getGoods_surplus())-Integer.parseInt(arrayList_ol.get(tag).getCount());
                                                            updateview(tag, Integer.parseInt(arrayList_ol.get(tag).getGoods_surplus()));
                                                        }
                                                        linearLayout.setVisibility(View.VISIBLE);
                                                        pid = arrayList_ol.get(tag).getID();
                                                        num = String.valueOf(vb1);
                                                        MyThreadPoolManager.getInstance().execute(runnableAddGoods);
                                                        if (arrayList_ol.get(tag).getXuanzhong()){
                                                            int money = 1*vb1;
                                                            adapterIndent.updateview_th(listView,tag,true);
                                                            gn+=vb1;
                                                            addmoney(money,true);
                                                        }
                                                    }else {
                                                        cToast = CToast.makeText(getActivity(),"没有更多的限购商品了~",600);
                                                        cToast.show();
                                                    }
                                                }else {
                                                    updateview(tag, Integer.parseInt(arrayList_ol.get(tag).getGoods_surplus()));
                                                    linearLayout.setVisibility(View.VISIBLE);
                                                    pid = arrayList_ol.get(tag).getID();
                                                    num = String.valueOf(vb);
                                                    MyThreadPoolManager.getInstance().execute(runnableAddGoods);
                                                    if (arrayList_ol.get(tag).getXuanzhong()){
                                                        int money = 1*vb;
                                                        adapterIndent.updateview_th(listView,tag,true);
                                                        gn+=vb;
                                                        addmoney(money,true);
                                                    }
                                                }
                                            }
                                            break;
//                                    case R.id.checkbox:
//                                        if (!arrayList_ol.get(tag).getXuanzhong()){
//                                            int money = (Integer.parseInt(arrayList_ol.get(tag).getCount()))*(Integer.parseInt(arrayList_ol.get(tag).getGoodsmoney()));
//                                            adapterIndent.updateview_th(listView,tag,true);
//                                            gn+=Integer.parseInt(arrayList_ol.get(tag).getCount());
//                                            addmoney(money,true);
//                                            hashMap.put(tag,arrayList.get(tag));
//                                        }else {
//                                            int money = (Integer.parseInt(arrayList_ol.get(tag).getCount()))*(Integer.parseInt(arrayList_ol.get(tag).getGoodsmoney()));
//                                            adapterIndent.updateview_th(listView,tag,false);
//                                            if (gn >0){
//                                                gn-=Integer.parseInt(arrayList_ol.get(tag).getCount());
//                                            }
//                                            if (hashMap.size()>0){
//                                                hashMap.remove(tag);
//                                            }
//                                            addmoney(money,false);
//                                        }
//                                        boolean allGroupSameState = true;
//                                        for (int i = 0; i < arrayList_ol.size(); i++) {
//                                            if (arrayList_ol.get(i).getXuanzhong() != true) {
//                                                allGroupSameState = false;
//                                                break;
//                                            }
//                                        }
//                                        if (allGroupSameState) {
//                                            checkBox.setChecked(true);
//                                        } else {
//                                            checkBox.setChecked(false);
//                                        }
//                                        break;
                                        case R.id.editext_shoppingnumber_ol:
                                            falg1 = true;
                                            backgroundAlha(0.5f);
                                            tag_ol = tag;
                                            pid = arrayList_ol.get(tag).getID();
                                            money_ol = arrayList_ol.get(tag).getGoodsmoney();
                                            limit_count = arrayList_ol.get(tag).getGoodsxian();
                                            num_ol = arrayList_ol.get(tag).getCount();
                                            surplus = arrayList_ol.get(tag).getGoods_surplus();
                                            carpoup(falg1,tag);
                                            break;
                                    }
                                }
                            };
                            adapterIndent = new AdapterIndent(localBroadcastManager,getActivity(),arrayList_ol,shoppingonclick,imageloderinit.imageLoader,imageloderinit.options);
                            listView.setAdapter(adapterIndent);
                        }else {
                            linearLayout_delete.setVisibility(View.VISIBLE);
                            textView.setText("0");
                            textView_ol.setText("0");
                            MyActivity.textView.setText("0");
                            SaveShared.sharedcarnumbersave(getActivity(),String.valueOf(0));
                        }
                    }else {
                        textView_ol.setText("0");
                        MyActivity.textView.setText("0");
                        SaveShared.sharedcarnumbersave(getActivity(),String.valueOf(0));
                        textView.setText("0");
                        linearLayout_delete.setVisibility(View.VISIBLE);
                    }
                    break;
                case 4:
                    statese = new Statese();
                    if (linearLayout_car != null){
                        linearLayout_car.setVisibility(View.GONE);
                    }
                    linearLayout.setVisibility(View.GONE);
                    statese = msg.getData().getParcelable("state");
                    if (statese != null){
                        if (statese.getState() != null){
                            if (statese.getState().equals(String.valueOf(Numbers.ONE))){
//                                Toast.makeText(getActivity(),statese.getMsg(),Toast.LENGTH_SHORT).show();
                                if (popupWindow1 != null){
                                    popupWindow1.dismiss();
                                }
                            }
                        }
                    }
                    break;
                case 5:
                    statese = new Statese();
                    statese = msg.getData().getParcelable("orderNo");
                    if (statese != null){
                        if (!StringUtils.isEmpty(statese.getIntergl())){
                            Intent intent = new Intent();
                            intent.putExtra("array",sum);
                            intent.putExtra("money",textView.getText().toString());
                            intent.putExtra("number",String.valueOf(gn));
                            intent.putExtra("count",String.valueOf(count));
                            intent.putExtra("IP",ip);
                            intent.putExtra("orderNo",statese.getIntergl());
                            intent.putExtra("number_ol","1");
                            intent.putExtra("qf","1");
                            intent.setClass(getActivity(),ActivityIndent.class);
                            startActivity(intent);
                        }else {
                            cToast = CToast.makeText(getActivity(),statese.getMsg(),600);
                            cToast.show();
                        }
                    }
                    break;
            }

        }
    };

    /**
     * 购物车输入框
     * @param falg1
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void carpoup(boolean falg1, final Integer tag) {
        popupWindow1 = new PopupWindow();
        View view2 = LayoutInflater.from(getActivity()).inflate(R.layout.carpopuplayout,null);
        popupWindow1.setContentView(view2);
        popupWindow1.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        popupWindow1.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow1.setFocusable(true);
        popupWindow1.setTouchable(true);
        popupWindow1.setOutsideTouchable(true);
        popupWindow1.setBackgroundDrawable(new BitmapDrawable());
        button_car_add = (Button)view2.findViewById(R.id.car_imagebutton_add);
        button_car_jian = (Button)view2.findViewById(R.id.car_imagebutton_jian);
        button_car_ok = (Button)view2.findViewById(R.id.button_car_ok);
        button_car_canler = (Button)view2.findViewById(R.id.button_car_canler);
        editText_ol = (EditText)view2.findViewById(R.id.ecittext_carnum_ol);
        linearLayout_car = (LinearLayout)view2.findViewById(R.id.linearlayout_this_car_loading);
        editText_ol.setText(num_ol);
        button_car_ok.setOnClickListener(this);
        button_car_canler.setOnClickListener(this);
        button_car_jian.setOnClickListener(this);
        button_car_add.setOnClickListener(this);
        popupWindow1.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlha(1f);
                popupWindow1.dismiss();

            }
        });
        if (falg1){
            popupWindow1.showAtLocation(view2, Gravity.CENTER, 0, 0);
        }
    }

    /**
     * 修改商品数量
     */
    private Runnable runnableAddGoods= new Runnable() {
        @Override
        public synchronized void run() {
            statese = new Statese();
            String token = SaveShared.tokenget(getActivity());
            String uid = SaveShared.uid(getActivity());
            statese = HttpTransfeData.goodsaddshoppingcat(getActivity(),pid,uid,token,num);
            Message message = new Message();
            Bundle bundle = new Bundle();
            message.what = Numbers.FOUR;
            bundle.putParcelable("state",statese);
            message.setData(bundle);
            handler.sendMessage(message);
        }
    };
    /**
     * 弹出框
     * @param falg
     * @param tag
     * @param v
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void deletepopup(boolean falg, String tag, View v) {
        vb = tag;
        popupWindow = new PopupWindow();
        View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.shoppingpop,null);
        popupWindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(WindowManager.LayoutParams.MATCH_PARENT);
        popupWindow.setContentView(view1);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        button_delete = (Button)view1.findViewById(R.id.button_ensure_delete);
        button_over = (Button)view1.findViewById(R.id.button_cancel_delete);
        linearLayout_ol = (LinearLayout)view1.findViewById(R.id.linearlayout_this_lcar_delete);
        button_delete.setOnClickListener(this);
        button_over.setOnClickListener(this);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                popupWindow.dismiss();
                backgroundAlha(1f);
            }
        });
        if (falg){
            popupWindow.showAsDropDown(view1, 0, 0, Gravity.CENTER);
        }
    }
    //item刷新
    private void updateview(Integer tag, int ol) {
        int frist = listView.getFirstVisiblePosition();
        if (tag - frist>=0){
            View view1 = listView.getChildAt(tag-frist);
            adapterIndent.updateView_fg(tag,view1,ol);
        }

    }
    //增加金钱数量
    private void addmoney(int money,boolean flagth) {
        if (flagth){
            if (Integer.parseInt(textView.getText().toString()) == 0){
                textView.setText(money+"");
            }else {
                int money_ol = Integer.parseInt(textView.getText().toString())+money;
                textView.setText(money_ol+"");
            }
        }else {
            if (Integer.parseInt(textView.getText().toString()) == 0){
                textView.setText("0");
            }else {
                int money_ol = Integer.parseInt(textView.getText().toString())-money;
                textView.setText(money_ol+"");
            }
        }
    }

    /**
     * 刷新
     * @param pullToRefreshLayout
     */
    @Override
    public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
        new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                arrayList_sum.clear();
                index = 1;
                MyThreadPoolManager.getInstance().execute(runnable);
                layout.refreshFinish(PullToRefreshLayout.SUCCEED);
            }
        }.sendEmptyMessageDelayed(0,1500);
    }
    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                index++;
                MyThreadPoolManager.getInstance().execute(runnable);
                layout.refreshFinish(PullToRefreshLayout.SUCCEED);
            }
        }.sendEmptyMessageDelayed(0,1500);
    }
    @Override
    public void onDestroy() {
        if (arrayList != null){
            arrayList.clear();
        }
        if (arrayList_sum != null){
            arrayList_sum.clear();
        }
        if (arrayList_ol != null){
            arrayList_ol.clear();
        }
        super.onDestroy();
    }
    public void backgroundAlha(float bgha){
        if (getActivity() != null){
            WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
            lp.alpha = bgha;
            getActivity().getWindow().setAttributes(lp);
        }
    }
    //广播
    public class ThreaderReceiverol extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            linearLayout.setVisibility(View.VISIBLE);
            MyThreadPoolManager.getInstance().execute(runnableEditGoods);
        }
    }
    /**
     * 创建订单
     */
    private  Runnable runnableOrderNo = new Runnable() {
        @Override
        public synchronized void run() {
            statese = new Statese();
            String uid = SaveShared.uid(getActivity());
            String token = SaveShared.tokenget(getActivity());
            statese = HttpTransfeData.indenthttp(getActivity(), address, uid, token, textView.getText().toString(),sum,ip);
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putParcelable("orderNo",statese);
            message.what = Numbers.FIVE;
            message.setData(bundle);
            handler.sendMessage(message);
        }
    };
    /**
     * http输入框增加商品数量
     */
    private Runnable runnableEditGoods = new Runnable() {
        @Override
        public synchronized void run() {
            statese = new Statese();
            String token = SaveShared.tokenget(getActivity());
            String uid = SaveShared.uid(getActivity());
            String pid1 = AdapterIndent.pid_ol;
            String num1 = adapterIndent.num_ol;
            statese = HttpTransfeData.goodsaddshoppingcat(getActivity(),pid1,uid,token,num1);
            Message message = new Message();
            Bundle bundle = new Bundle();
            message.what = Numbers.FOUR;
            bundle.putParcelable("state",statese);
            message.setData(bundle);
            handler.sendMessage(message);
        }
    };
}
