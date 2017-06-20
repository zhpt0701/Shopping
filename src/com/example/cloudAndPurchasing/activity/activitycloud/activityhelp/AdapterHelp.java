package com.example.cloudAndPurchasing.activity.activitycloud.activityhelp;

import android.app.Application;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.example.cloudAndPurchasing.kind.Feedback;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/7/4 0004.
 */
public class AdapterHelp extends BaseAdapter{
    public AdapterHelp(Application application, ArrayList<Feedback> arrayList) {

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return convertView;
    }
}
