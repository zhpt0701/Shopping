package com.example.cloudAndPurchasing.popupwindows;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import com.example.cloudAndPurchasing.R;

/**
 * Created by Administrator on 2016/4/27 0027.
 */
public class SpinnerPopup extends PopupWindow implements View.OnClickListener {
    private View view;
    private Button button2;
    private String tanchu;
    public SpinnerPopup(Context context){
        view = LayoutInflater.from(context).inflate(R.layout.spinnerpouplayout,null);
        setContentView(view);
        setFocusable(true);
        setTouchable(true);
        setOutsideTouchable(true);
        setBackgroundDrawable(new BitmapDrawable());
        setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        Button button = (Button)view.findViewById(R.id.button_tongyi);
        Button button1 = (Button)view.findViewById(R.id.button_butongyi);
        button.setOnClickListener(this);
        button1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_tongyi:
              tanchu = "男";
                dismiss();
                break;
            case R.id.button_butongyi:
                tanchu = "女";
                dismiss();
                break;
        }
    }

    public String show(View v) {
        showAsDropDown(v);
        return tanchu;
    }
}
