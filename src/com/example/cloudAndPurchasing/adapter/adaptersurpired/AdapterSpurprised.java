package com.example.cloudAndPurchasing.adapter.adaptersurpired;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/11 0011.
 */
public class AdapterSpurprised extends FragmentPagerAdapter{
    private ArrayList<Fragment> arrayList1;
    private Context context;
    public AdapterSpurprised(FragmentManager fragmentManager, ArrayList<Fragment> arrayList, Context activity) {
        super(fragmentManager);
        arrayList1 = arrayList;
        context = activity;
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
