package com.example.cloudAndPurchasing.adapter.adaptersurpired;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/12 0012.
 */
public class AdapterRecord extends FragmentPagerAdapter{
    private ArrayList<Fragment> arrayList1;
    public AdapterRecord(FragmentManager fragmentManager, ArrayList<Fragment> arrayList) {
        super(fragmentManager);
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
