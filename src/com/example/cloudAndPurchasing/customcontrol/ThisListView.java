package com.example.cloudAndPurchasing.customcontrol;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by Administrator on 2016/4/28 0028.
 */
public class ThisListView extends ListView {
    public ThisListView(Context context) {
        super(context);
    }

    public ThisListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ThisListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
