package com.example.cloudAndPurchasing.adapter.adaptercloud;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.example.cloudAndPurchasing.activity.activitycloud.ActivityCloudRecord;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/1 0001.
 */
public class AdapterFragment extends FragmentPagerAdapter{
    private Context context;
    private ArrayList<Fragment> arrayList1;

    public AdapterFragment(FragmentManager fm,Context activityCloudRecord, ArrayList<Fragment> arrayList) {
        super(fm);
        context = activityCloudRecord;
        arrayList1 = arrayList;
    }
    @Override
    public Fragment getItem(int i) {
        if (arrayList1 != null){
            return arrayList1.get(i);
        }
        return null;
    }

    @Override
    public int getCount() {
        if (arrayList1 != null){
            return arrayList1.size();
        }
        return 0;
    }
}
