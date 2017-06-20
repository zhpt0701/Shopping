package com.example.cloudAndPurchasing.popupwindows;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.http.HttpTransfeData;
import com.example.cloudAndPurchasing.kind.Statese;

/**
 * Created by Administrator on 2016/4/7 0007.
 */
public class ShoppingPopup extends PopupWindow implements View.OnClickListener {
    private Button button,button_ol;
    private View view;
    private Context contextol;
    private String vb = null;
    private Integer goodsid = 0;
    private HandlerThread handlerThread ;
    private Handler mhander;
    private Statese statese;
    public ShoppingPopup(Context context,Integer tag){
        goodsid = tag;
        contextol = context;
        Log.i(context+"sljfkl","sljflsjlsd--oi");
        view = LayoutInflater.from(context).inflate(R.layout.shoppingpop,null);
        setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        setHeight(WindowManager.LayoutParams.MATCH_PARENT);
        setContentView(view);
        setFocusable(true);
        setOutsideTouchable(true);
        setBackgroundDrawable(new BitmapDrawable());
        button = (Button)view.findViewById(R.id.button_ensure_delete);
        button_ol = (Button)view.findViewById(R.id.button_cancel_delete);
        handlerThread = new HandlerThread("MyHandlerThread");
        handlerThread.start();
        mhander = new Handler(handlerThread.getLooper());
        button.setOnClickListener(this);
        button_ol.setOnClickListener(this);
        Log.i(context+"sljfkl","sljflsjlsd--");
    }
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            statese = new Statese();
            SharedPreferences sharedPreferences = contextol.getSharedPreferences("data",contextol.MODE_PRIVATE);
            String token = sharedPreferences.getString("token","");
            String uid = sharedPreferences.getString("UID","");
            statese = HttpTransfeData.shoppingcatdeletehttp(contextol,token,uid,String.valueOf(goodsid));
        }
    };
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.button_ensure_delete:
                mhander.post(runnable);
                mhander.removeCallbacks(runnable);
                dismiss();
                break;
            case R.id.button_cancel_delete:
                dismiss();
                break;
        }
    }

    public void show(View view1) {
        showAsDropDown(view,0,0, Gravity.CENTER);
    }
}
