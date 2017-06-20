package com.example.cloudAndPurchasing.adapter.adaptersurpired;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/12 0012.
 */
public class AdapterPhoto extends PagerAdapter{
    private ArrayList<View> arrayList1;
    private Context context;

    public AdapterPhoto(ArrayList<View> arrayList,Context context1){
        context = context1;
        arrayList1 = arrayList;

    }
    @Override
    public int getCount() {
        if (arrayList1 != null){
            return arrayList1.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ((ViewPager) container).addView(arrayList1.get(position),0);
        return arrayList1.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView(arrayList1.get(position));
    }
}
