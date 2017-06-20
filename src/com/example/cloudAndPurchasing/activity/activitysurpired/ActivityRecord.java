package com.example.cloudAndPurchasing.activity.activitysurpired;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;
import com.example.cloudAndPurchasing.BaseFragmentActivity;
import com.example.cloudAndPurchasing.MyActivity;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.activity.ActivityShoppingdetail;
import com.example.cloudAndPurchasing.activity.activitycloud.activityfriend.ActivityFriend;
import com.example.cloudAndPurchasing.activity.activityshopping.ActivityGoodsDateil;
import com.example.cloudAndPurchasing.adapter.adaptersurpired.AdapterRecord;
import com.example.cloudAndPurchasing.adapter.adaptersurpired.AdapterSpurprised;
import com.example.cloudAndPurchasing.fragment.fragmentsurpried.FragmentParticipation;
import com.example.cloudAndPurchasing.fragment.fragmentsurpried.FragmentSingleRecord;
import com.example.cloudAndPurchasing.fragment.fragmentsurpried.FragmentWinning;
import com.example.cloudAndPurchasing.http.HttpApi;
import com.example.cloudAndPurchasing.http.HttpTransfeData;
import com.example.cloudAndPurchasing.http.Numbers;
import com.example.cloudAndPurchasing.http.SaveShared;
import com.example.cloudAndPurchasing.imageloder.Myimageloder;
import com.example.cloudAndPurchasing.imageloder.PhotoLevel;
import com.example.cloudAndPurchasing.kind.Friend;
import com.example.cloudAndPurchasing.tiltle.TeaskBar;
import com.example.cloudAndPurchasing.zhi.ValuePrice;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/12 0012.
 */
public class ActivityRecord extends BaseFragmentActivity implements ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener, View.OnClickListener {
    private ViewPager viewPager;
    private ImageView imageView,imageView_ol;
    private Button imageButton;
    private RadioButton radioButton,radioButton_ol,radioButton_ool;
    private TextView textView,textView_ol,textView_level,textView_ad;
    private RadioGroup radioGroup;
    private Friend friend;
    private AdapterRecord adapterRecord;
    private Myimageloder myimageloder ;
    private ArrayList<Fragment> arrayList;
    private FragmentManager fragmentManager;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityrecord);
        //改变状态栏颜色
        TeaskBar.onSystemoutcoloront(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        //初始化数据
        initData();
        //初始化控件
        initview();
    }
    public void initData(){
        sharedPreferences = getSharedPreferences("data",MODE_PRIVATE);
        myimageloder = new Myimageloder(this);
        String uid = getIntent().getStringExtra("memuid");
        String name = getIntent().getStringExtra("name");
        String address = getIntent().getStringExtra("address");
        String level = getIntent().getStringExtra("level");
        String num = getIntent().getStringExtra("other");
        SharedPreferences.Editor sh = getSharedPreferences("data",MODE_PRIVATE).edit();
        if (uid!=null){
            sh.putString("fid",uid);
        }
        if (address!=null){
            sh.putString("faddress",address);
        }
        if (name!=null){
            sh.putString("fname",name);
        }
        if (level!=null){
            sh.putString("flevel",level);
        }
        if (num != null){
            sh.putString("other",num);
        }
        sh.commit();
        friend = new Friend();
        fragmentManager = getSupportFragmentManager();
        arrayList = new ArrayList<Fragment>();
        arrayList.add(new FragmentParticipation());
        arrayList.add(new FragmentWinning());
        arrayList.add(new FragmentSingleRecord());
        adapterRecord = new AdapterRecord(fragmentManager,arrayList);
    }
    //初始化控件
    public void initview(){
        viewPager = (ViewPager)findViewById(R.id.viewpager_jilu);
        imageButton = (Button)findViewById(R.id.imagebutton_activityrecord_back);
        imageView = (ImageView)findViewById(R.id.imageview_otherpople);
        imageView_ol = (ImageView)findViewById(R.id.imageview_levelfrend);
        textView = (TextView)findViewById(R.id.textview_otherpople_name);
        textView_ol = (TextView)findViewById(R.id.textview_otherpeople_ip);
        radioGroup = (RadioGroup)findViewById(R.id.radiogroup_otherpoeple);
        textView_level = (TextView)findViewById(R.id.textview_otherpople_levelname);
        textView_ad = (TextView)findViewById(R.id.textview_otherpople_address);
        radioButton = (RadioButton)findViewById(R.id.radiobutton_recordcayu);
        radioButton_ol = (RadioButton)findViewById(R.id.radiobutton_recordzhongjiang);
        radioButton_ool = (RadioButton)findViewById(R.id.radiobutton_recordshaidan);
        viewPager .setOffscreenPageLimit(2);
        viewPager.setAdapter(adapterRecord);
        String friend_cloud = getIntent().getStringExtra("cloud_ol");
        if (friend_cloud != null){
            if (friend_cloud.equals(String.valueOf(Numbers.SEVEN))){
                radioButton.setChecked(true);
                viewPager.setCurrentItem(0);
            }else if (friend_cloud.equals(String.valueOf(Numbers.EIGHT))){
                radioButton_ol.setChecked(true);
                viewPager.setCurrentItem(1);
            }else if (friend_cloud.equals(String.valueOf(Numbers.NINE))){
                radioButton_ool.setChecked(true);
                viewPager.setCurrentItem(2);
            }
        }

        viewPager.setOnPageChangeListener(this);
        radioGroup.setOnCheckedChangeListener(this);
        imageButton.setOnClickListener(this);
        if (!sharedPreferences.getString("faddress","").equals("")){
            if (!sharedPreferences.getString("faddress","").equals("null")){
                if (!sharedPreferences.getString("faddress","").equals("暂无地址")){
                    textView_ad.setText(sharedPreferences.getString("faddress",""));
                }else {
                    textView_ad.setText("");
                }
            }
        }
        textView.setText(sharedPreferences.getString("fname",""));
        textView_level.setText(PhotoLevel.level[Integer.parseInt(sharedPreferences.getString("flevel","").trim())-1]);
        textView_ol.setText(sharedPreferences.getString("fid",""));
        if (sharedPreferences.getString("flevel","") != ""){
            imageView_ol.setImageResource(PhotoLevel.tupian[Integer.parseInt(sharedPreferences.getString("flevel",""))-1]);
        }
        myimageloder.imageLoader.displayImage(HttpApi.mytu+sharedPreferences.getString("fid",""),imageView,myimageloder.options);
    }
    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        switch (i){
            case 0:
                radioGroup.check(R.id.radiobutton_recordcayu);
                break;
            case 1:
                radioGroup.check(R.id.radiobutton_recordzhongjiang);
                break;
            case 2:
                radioGroup.check(R.id.radiobutton_recordshaidan);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.radiobutton_recordcayu:
                viewPager.setCurrentItem(0);
                break;
            case R.id.radiobutton_recordzhongjiang:
                viewPager.setCurrentItem(1);
                break;
            case R.id.radiobutton_recordshaidan:
                viewPager.setCurrentItem(2);
                break;
        }
    }
    //onclick监听事件
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imagebutton_activityrecord_back:
                finish();
                break;
        }
    }
}
