package com.example.cloudAndPurchasing.popupwindows;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.PopupWindow;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.adapter.adaptersurpired.AdapterWinningPopup;
import com.example.cloudAndPurchasing.kind.Count;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/14 0014.
 */
public class WinningPopupWindows extends PopupWindow implements View.OnClickListener, AdapterView.OnItemClickListener {
    private View view;
    private Context context1;
    private Button button;
    private GridView gridView;
    private Handler mhandler;
    private HandlerThread handlerThread;
    private AdapterWinningPopup adapterPopup;
    private ArrayList<Count> arrayList;
    public WinningPopupWindows(Context context){
        context1 = context;
        view = LayoutInflater.from(context1).inflate(R.layout.winningpopuplayout,null);
        setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        setHeight(WindowManager.LayoutParams.MATCH_PARENT);
        setContentView(view);
        setClippingEnabled(true);
        setFocusable(true);
        setOutsideTouchable(true);
        setBackgroundDrawable(new BitmapDrawable());
        initData();
        initView(view);

    }

    private void initData() {
        handlerThread = new HandlerThread("HandlerThread");
        handlerThread.start();
        mhandler = new Handler(handlerThread.getLooper());
        mhandler.post(runnable);
        adapterPopup = new AdapterWinningPopup(context1,arrayList);
    }
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {

        }
    };
    private void initView(View view) {
        button = (Button)view.findViewById(R.id.button_winningpopup);
        gridView = (GridView)view.findViewById(R.id.gridview_zhongjianghao);
        gridView.setAdapter(adapterPopup);
        button.setOnClickListener(this);
        gridView.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_winningpopup:
                dismiss();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    public void show(View view1) {
        Log.i("fjskldf","mfksflsfkls======");
        showAsDropDown(view);
    }
}
