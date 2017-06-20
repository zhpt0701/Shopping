package com.example.cloudAndPurchasing.activity.activitycloud.activitythis;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.drawable.BitmapDrawable;
import android.os.*;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import com.example.cloudAndPurchasing.BaseFragmentActivity;
import com.example.cloudAndPurchasing.MyActivity;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.activity.activitycloud.ChooseActivity;
import com.example.cloudAndPurchasing.activity.activitycloud.activitythis.adapteraddress.*;
import com.example.cloudAndPurchasing.customcontrol.SwicthView;
import com.example.cloudAndPurchasing.customcontrol.control.OnWheelChangedListener;
import com.example.cloudAndPurchasing.customcontrol.control.WheelView;
import com.example.cloudAndPurchasing.http.HttpTransfeData;
import com.example.cloudAndPurchasing.http.Numbers;
import com.example.cloudAndPurchasing.http.SaveShared;
import com.example.cloudAndPurchasing.kind.*;
import com.example.cloudAndPurchasing.manager.MyThreadPoolManager;
import com.example.cloudAndPurchasing.tiltle.TeaskBar;
import com.example.cloudAndPurchasing.zhi.LogUtil;
import com.example.cloudAndPurchasing.zhi.ValuePrice;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/31 0031.
 */
public class ActivityAddAddress extends BaseFragmentActivity implements View.OnClickListener,OnWheelChangedListener, SwicthView.OnChangedListener {
    private EditText editText,editText_ol,editText_ool;
    private Button button,button_ol,button_yes;
    private Button imageButton;
    private RadioGroup radioGroup;
    private PopupWindow popupWindow;
    private boolean flag = false;
    private WheelView wheelView,wheelView1,wheelView2;
    private RadioButton radioButton,radioButton1;
    private ArrayList<Province> arrayList;
    private ArrayWheelAdapter<String> arrayWheelAdapter;
    private Statese statese;
    private boolean isflag = false;
    String[] mProvinceDatas;
    Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
    Map<String, String[]> mDistrictDatasMap = new HashMap<String, String[]>();
    Map<String, String> mZipcodeDatasMap = new HashMap<String, String>();
    String mCurrentProviceName;
    String mCurrentCityName;
    String mCurrentDistrictName ="";
    String mCurrentZipCode ="";
    private int c= 0;
    private SwicthView swicthView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityaddaddresslayout);

        TeaskBar.onSystemoutcolor(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        initData();
        initview();
    }

    /**
     * 初始化数据
     */
    public void initData(){
        LogUtil.i("ActivityAddAddress initData start.","");
        arrayList = new ArrayList<Province>();
        //获取数据
        List<ProvinceModel> provinceList = null;
        AssetManager asset = getAssets();
        try {
            InputStream input = asset.open("province_data.xml");

            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser parser = spf.newSAXParser();
            XmlParserHandler handler = new XmlParserHandler();
            parser.parse(input, handler);
            input.close();
            provinceList = handler.getDataList();
            if (provinceList!= null && !provinceList.isEmpty()) {
                mCurrentProviceName = provinceList.get(0).getName();
                List<CityModel> cityList = provinceList.get(0).getCityList();
                if (cityList!= null && !cityList.isEmpty()) {
                    mCurrentCityName = cityList.get(0).getName();
                    List<DistrictModel> districtList = cityList.get(0).getDistrictList();
                    mCurrentDistrictName = districtList.get(0).getName();
                    mCurrentZipCode = districtList.get(0).getZipcode();

                }
            }
            //获取升级数据
            mProvinceDatas = new String[provinceList.size()];
            for (int i=0; i< provinceList.size(); i++) {
                mProvinceDatas[i] = provinceList.get(i).getName();
                List<CityModel> cityList = provinceList.get(i).getCityList();
                String[] cityNames = new String[cityList.size()];
                //获取市级数据
                for (int j=0; j< cityList.size(); j++) {
                    cityNames[j] = cityList.get(j).getName();
                    List<DistrictModel> districtList = cityList.get(j).getDistrictList();
                    String[] distrinctNameArray = new String[districtList.size()];
                    DistrictModel[] distrinctArray = new DistrictModel[districtList.size()];
                    //区级数据
                    for (int k=0; k<districtList.size(); k++) {
                        DistrictModel districtModel = new DistrictModel(districtList.get(k).getName(), districtList.get(k).getZipcode());
                        mZipcodeDatasMap.put(districtList.get(k).getName(), districtList.get(k).getZipcode());
                        distrinctArray[k] = districtModel;
                        distrinctNameArray[k] = districtModel.getName();
                    }
                    mDistrictDatasMap.put(cityNames[j], distrinctNameArray);
                }
                mCitisDatasMap.put(provinceList.get(i).getName(), cityNames);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            LogUtil.i("ActivityAddAddress initData end.","");
        }
    }

    /**
     * 数据请求
     */
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            String token = SaveShared.tokenget(getApplication());
           statese = new Statese();
            String uid = SaveShared.uid(getApplication());
           statese = HttpTransfeData.addaddresshttp(getApplication(),editText.getText().toString(),editText_ol.getText().toString(),editText_ool.getText().toString(),button.getText().toString(),isflag,token,uid);
            Message message = new Message();
            message.what = Numbers.ONE;
            Bundle bundle = new Bundle();
            bundle.putParcelable("con",statese);
            message.setData(bundle);
            mhander.sendMessage(message);
        }
    };
    /**
     * 界面刷新
     */
    private Handler mhander = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    statese = new Statese();
                    statese = msg.getData().getParcelable("con");
                    if (statese.getState() == String.valueOf(Numbers.ONE)){
                        finish();
                        Toast.makeText(getApplication(),statese.getMsg(),Toast.LENGTH_SHORT).show();
                    }else {
                        if (statese.getMsg() != null){
                            if (statese.getMsg().equals("token失效")){
                                String user_ol = SaveShared.loadong_name(getApplication());
                                if (user_ol != null){
                                    if (user_ol != ""){
                                        MyThreadPoolManager.getInstance().execute(runnable1);
                                    }
                                }
                            }
                        }
                    }
                    break;
                case 2:
                    statese = new Statese();
                    statese = msg.getData().getParcelable("this");
                    if (statese != null){
                        if (statese.getState().equals(String.valueOf(Numbers.ONE))){
                            try {
                                MyThreadPoolManager.getInstance().execute(runnable);
                            }catch (Exception e){
                                LogUtil.e("ActivityAddAddress mhander error:",e.toString());
                            }
                        }
                    }
                    break;
            }
        }
    };
    /**
     * 登陆
     */
    private Runnable runnable1 = new Runnable() {
        @Override
        public void run() {
            statese = new Statese();
            String user_ol = SaveShared.loadong_name(getApplication());
            String pass = SaveShared.loading_pass(getApplication());
            statese = HttpTransfeData.httppostloding(getApplicationContext(),user_ol,pass);
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putParcelable("this", statese);
            message.setData(bundle);
            message.what = Numbers.TWO;
            mhander.sendMessage(message);
        }
    };
    /**
     * 初始化控件
     */
    public void initview(){
        button = (Button)findViewById(R.id.button_city);
        button_ol = (Button)findViewById(R.id.button_this_ok);
        editText = (EditText)findViewById(R.id.edittext_goods_name);
        editText_ol = (EditText)findViewById(R.id.edittext_contact);
        editText_ool = (EditText)findViewById(R.id.edittext_detail_address);
        imageButton = (Button)findViewById(R.id.imagebutton_amendaddressbackol);
        swicthView = (SwicthView)findViewById(R.id.swicthview);
//        radioGroup = (RadioGroup)findViewById(R.id.radiogroup_fuxuan);
//        radioButton = (RadioButton)findViewById(R.id.radiobutton_one);
//        radioButton1 = (RadioButton)findViewById(R.id.radiobutton_two);
//        radioGroup.setOnCheckedChangeListener(this);
        swicthView.setChecked(false);
        swicthView.setOnChangedListener(this);
        button.setOnClickListener(this);
        button_ol.setOnClickListener(this);
        imageButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.button_this_ok:
                if (editText.getText().toString().length()>0){
                    if (editText_ol.getText().toString().length() == 11){
                        if (!button.getText().toString().equals("请选择城市")){
                            if (editText_ool.getText().toString().length()>0){
                                try {
                                    MyThreadPoolManager.getInstance().execute(runnable);
                                }catch (Exception e){
                                    LogUtil.e("ActivityAddAddress onclick error:",e.toString());
                                }

                            }else {
                                Toast.makeText(getApplication(),"请输入详细信息",Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(getApplication(),"请选择地址信息",Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(getApplication(),"请输入11位手机号码",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getApplication(),"请输入收货人名称",Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.button_city:
                InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(ActivityAddAddress.this.getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
                flag = true;
                initpopup(v, flag);
                back(0.5f);
                break;
            case R.id.imagebutton_amendaddressbackol:
                try {
                    finish();
                }catch (Exception e){
                    LogUtil.e("ActivityAddAddress onclick error:",e.toString());
                }
                break;
            case R.id.btn_confirm:
                button.setText(mCurrentProviceName+mCurrentCityName+mCurrentDistrictName);
                popupWindow.dismiss();
                break;
        }
    }
    /**
     * 城市列表弹出框
     * @param v
     * @param flag
     */
    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    private void initpopup(View v, boolean flag) {
        popupWindow = new PopupWindow();
        View view1 = LayoutInflater.from(this).inflate(R.layout.citypopuplayout,null);
        popupWindow.setContentView(view1);
        popupWindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        button_yes = (Button)view1.findViewById(R.id.btn_confirm);
        wheelView = (WheelView)view1.findViewById(R.id.id_province);
        wheelView1 = (WheelView)view1.findViewById(R.id.id_city);
        wheelView2 = (WheelView)view1.findViewById(R.id.id_district);
        button_yes.setOnClickListener(this);
        wheelView.addChangingListener(this);
        wheelView1.addChangingListener(this);
        wheelView2.addChangingListener(this);
        arrayWheelAdapter = new ArrayWheelAdapter<String>(getApplication(), mProvinceDatas);
        wheelView.setViewAdapter(arrayWheelAdapter);
        wheelView.setVisibleItems(7);
        wheelView1.setVisibleItems(7);
        wheelView2.setVisibleItems(7);
        updateCities();
        updateAreas();
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                back(1f);
            }
        });
        if (flag){
            popupWindow.showAsDropDown(v);
        }
    }

//    @Override
//    public void onCheckedChanged(RadioGroup group, int checkedId) {
//        switch (checkedId){
//            case R.id.radiobutton_one:
//                radioButton1.setChecked(false);
//                isflag = true;
//                Log.i(getApplication()+"","jfweifojkdsl"+isflag);
//                break;
//            case R.id.radiobutton_two:
//                radioButton.setChecked(false);
//                isflag = false;
//                Log.i(getApplication()+"","jfweifojkdsl342"+isflag);
//                break;
//        }
//    }

    /**
     * 弹框改变背景
     * @param cl
     */
    public void back(float cl){
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = cl;
        getWindow().setAttributes(lp);
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        if (wheel == wheelView) {
            updateCities();
        } else if (wheel == wheelView1) {
            updateAreas();
        } else if (wheel == wheelView2) {
            mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[newValue];
            mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
        }
    }
    //省列表
    private void updateCities() {
        int pCurrent = wheelView.getCurrentItem();
        mCurrentProviceName = mProvinceDatas[pCurrent];
        String[] cities = mCitisDatasMap.get(mCurrentProviceName);
        if (cities == null) {
            cities = new String[] { "" };
        }
        wheelView1.setViewAdapter(new ArrayWheelAdapter<String>(this, cities));
        wheelView1.setCurrentItem(0);
        updateAreas();
    }
    //市列表
    private void updateAreas() {
        int pCurrent = wheelView1.getCurrentItem();
        mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
        String[] areas = mDistrictDatasMap.get(mCurrentCityName);

        if (areas == null) {
            areas = new String[] { "" };
        }
        wheelView2.setViewAdapter(new ArrayWheelAdapter<String>(this, areas));
        wheelView2.setCurrentItem(0);
        mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[0];
    }

    @Override
    public void OnChanged(SwicthView wiperSwitch, boolean checkState) {
        if (checkState){
            isflag = true;
                Log.i(getApplication()+"",""+isflag);
        }else {
            isflag = false;
                Log.i(getApplication()+"",""+isflag);
        }
    }
}
