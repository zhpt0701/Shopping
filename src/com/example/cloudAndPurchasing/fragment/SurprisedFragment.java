package com.example.cloudAndPurchasing.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import com.example.cloudAndPurchasing.MyActivity;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.activity.activitymain.ActivityCreateRoom;
import com.example.cloudAndPurchasing.adapter.adaptersurpired.AdapterSpurprised;
import com.example.cloudAndPurchasing.fragment.fragmentsurpried.FragmentCloudRoom;
import com.example.cloudAndPurchasing.fragment.fragmentsurpried.FragmentNewPublish;
import com.example.cloudAndPurchasing.fragment.fragmentsurpried.FragmentSingle;
import com.example.cloudAndPurchasing.http.Numbers;
import com.example.cloudAndPurchasing.kind.Constants;
import com.example.cloudAndPurchasing.kind.SharePreferencesUlits;
import com.example.cloudAndPurchasing.kind.StringUtils;
import com.example.cloudAndPurchasing.times.CToast;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/3/21 0021.
 */
public class SurprisedFragment extends Fragment implements ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener, View.OnClickListener {
    /**
     * 自定义控件变量
     * viewPager 界面滑动
     * cToast 弹框
     */
    public static ViewPager viewPager;
    private RadioGroup radioGroup;
    private AdapterSpurprised adapterSurpried;
    private ArrayList<Fragment> arrayList;
    private Button button;
    private CToast cToast;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(savedInstanceState != null)
        {
            String FRAGMENTS_TAG = "android:support:fragments";
            // remove掉保存的Fragment
            savedInstanceState.remove(FRAGMENTS_TAG);
        }
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragmentsurprised_layout,null);
        //初始化数据
        initData();
        //初始化控件
        initView(view);
        return  view ;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (((MyActivity)getActivity()).getState() == 3){
            viewPager.setCurrentItem(0);
            ((MyActivity)getActivity()).setState(0);
        }
    }
    /**
     * 初始化数据
     */
    public void initData(){
        arrayList = new ArrayList<Fragment>();
        arrayList.clear();
        arrayList.add(new FragmentNewPublish());
        arrayList.add(new FragmentCloudRoom());
        arrayList.add(new FragmentSingle());
        adapterSurpried = new AdapterSpurprised(getChildFragmentManager(),arrayList,getActivity());
    }
    public void setCurrentFragment (int index){
        ((MyActivity)getActivity()).setCurrentFargmentIndex(index);
    }

    /**
     * 初始化控件
     * @param v
     */
    public void initView(View v){
        button = (Button)v.findViewById(R.id.button_addroom);
        radioGroup = (RadioGroup)v.findViewById(R.id.radiogroup_surprised);
        viewPager = (ViewPager)v.findViewById(R.id.viewpager_surprised);
        viewPager .setOffscreenPageLimit(2);
        viewPager.setAdapter(adapterSurpried);
        viewPager.setOnPageChangeListener(this);
        radioGroup.setOnCheckedChangeListener(this);
        button.setOnClickListener(this);
        String sh = getActivity().getIntent().getStringExtra("sh");
        if (sh != null){
            if (sh.equals(String.valueOf(Numbers.THREE))){
                button.setVisibility(View.INVISIBLE);
                viewPager.setCurrentItem(2);
            }
        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        switch (i){
            case 0:
                button.setVisibility(View.INVISIBLE);
                radioGroup.check(R.id.radiobutton_shangpin);
                break;
            case 1:
                button.setVisibility(View.VISIBLE);
                radioGroup.check(R.id.radiobutton_cloud_shopping);
                break;
            case 2:
                button.setVisibility(View.INVISIBLE);
                radioGroup.check(R.id.radiobutton_shaidan);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        switch (checkedId){

            case R.id.radiobutton_shangpin:
                button.setVisibility(View.INVISIBLE);
                viewPager.setCurrentItem(0);
                break;
            case R.id.radiobutton_cloud_shopping:
                button.setVisibility(View.VISIBLE);
                viewPager.setCurrentItem(1);
                break;
            case R.id.radiobutton_shaidan:
                button.setVisibility(View.INVISIBLE);
                viewPager.setCurrentItem(2);
                break;
        }
    }
    //监听响应事件
    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.button_addroom:
                if (!StringUtils.isEmpty(SharePreferencesUlits.getString(getActivity(),Constants.TOKEN,Constants.NUll_VALUE))){
                    SharePreferencesUlits.saveString(getActivity(),Constants.ROOMID,Constants.NUll_VALUE);
                    intent.putExtra("cloud",String.valueOf(Numbers.TWO));
                    intent.setClass(getActivity(),ActivityCreateRoom.class);
                    startActivity(intent);
                }else {
                    cToast = CToast.makeText(getActivity(),"请登录~",600);
                    cToast.show();
                }
                break;
        }
    }
}
