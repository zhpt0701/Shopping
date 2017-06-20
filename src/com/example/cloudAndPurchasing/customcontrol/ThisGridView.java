package com.example.cloudAndPurchasing.customcontrol;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by Administrator on 2016/4/5 0005.
 */
public class ThisGridView extends GridView {
    public ThisGridView(Context context) {
        super(context);
    }

    public ThisGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public ThisGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO 自动生成的构造函数存根
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
