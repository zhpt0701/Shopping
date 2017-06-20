package com.example.cloudAndPurchasing.activity.activitycloud;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;
import com.example.cloudAndPurchasing.BaseFragmentActivity;
import com.example.cloudAndPurchasing.MyActivity;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.fragment.fragmentcloud.FragmentOngoing;
import com.example.cloudAndPurchasing.adapter.adaptercloud.AdapterFragment;
import com.example.cloudAndPurchasing.fragment.fragmentcloud.FragmentAll;
import com.example.cloudAndPurchasing.fragment.fragmentcloud.FragmentPublish;
import com.example.cloudAndPurchasing.http.Numbers;
import com.example.cloudAndPurchasing.tiltle.TeaskBar;
import com.example.cloudAndPurchasing.zhi.ValuePrice;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/1 0001.
 */
public class ActivityCloudRecord extends BaseFragmentActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener, ViewPager.OnPageChangeListener {
    private ViewPager viewPager;
    private Button imageButton;
    private RadioButton radioButton,radioButton_ol,radioButton_ool;
    private RadioGroup radioGroup;
    private AdapterFragment adapterFragment;
    private ArrayList<Fragment> arrayList;
    private long chicktim = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitycloudrecordlayout);

        TeaskBar.onSystemoutcolor(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        initData();
        initview();
    }
    public void initData(){
        arrayList = new ArrayList<Fragment>();
        arrayList.add(new FragmentAll());
        arrayList.add(new FragmentOngoing());
        arrayList.add(new FragmentPublish());
        adapterFragment = new AdapterFragment(getSupportFragmentManager(),this,arrayList);
    }
    public void initview(){
        radioGroup = (RadioGroup)findViewById(R.id.radiogroup_record);
        viewPager = (ViewPager)findViewById(R.id.viewpager_record);
        imageButton = (Button)findViewById(R.id.imagebutton_record);
        radioButton = (RadioButton)findViewById(R.id.radiobutton_all);
        radioButton_ol = (RadioButton)findViewById(R.id.radiobutton_ongoing);
        radioButton_ool = (RadioButton)findViewById(R.id.radiobutton_publish);
        viewPager .setOffscreenPageLimit(2);
        viewPager.setAdapter(adapterFragment);
        String cloud = getIntent().getStringExtra("cloud_ol");
        if (cloud != null){
            if (cloud.equals(String.valueOf(Numbers.ONE))){
                radioButton.setChecked(true);
                viewPager.setCurrentItem(0);
            }else if (cloud.equals(String.valueOf(Numbers.TWO))){
                radioButton_ol.setChecked(true);
                viewPager.setCurrentItem(1);
            }else if (cloud.equals(String.valueOf(Numbers.THREE))){
                radioButton_ool.setChecked(true);
                viewPager.setCurrentItem(2);
            }
        }
        imageButton.setOnClickListener(this);
        radioGroup.setOnCheckedChangeListener(this);
        viewPager.setOnPageChangeListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imagebutton_record:
                finish();
                break;
        }
    }
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.radiobutton_all:
                FragmentAll.index = 1;
                FragmentAll.index_ol = 1;
                viewPager.setCurrentItem(0);
                break;
            case R.id.radiobutton_ongoing:
                FragmentOngoing.index = 1;
                FragmentOngoing.index_ol = 1;
                viewPager.setCurrentItem(1);
                break;
            case R.id.radiobutton_publish:
                FragmentPublish.index = 1;
                viewPager.setCurrentItem(2);
                break;
        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        switch (i){
            case 0:
                FragmentAll.index = 1;
                FragmentAll.index_ol = 1;
                radioGroup.check(R.id.radiobutton_all);
                break;
            case 1:
                FragmentOngoing.index = 1;
                FragmentOngoing.index_ol = 1;
                radioGroup.check(R.id.radiobutton_ongoing);
                break;
            case 2:
                FragmentPublish.index = 1;
                radioGroup.check(R.id.radiobutton_publish);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
