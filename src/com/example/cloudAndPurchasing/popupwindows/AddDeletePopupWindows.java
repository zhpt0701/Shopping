package com.example.cloudAndPurchasing.popupwindows;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.activity.activitymain.ActivityCloudRoom;
import com.example.cloudAndPurchasing.activity.activitymain.ActivityCreateRoom;
import com.example.cloudAndPurchasing.adapter.adaptersurpired.AdapterCloudRoom;

/**
 * Created by Administrator on 2016/4/14 0014.
 */
public class AddDeletePopupWindows extends PopupWindow implements View.OnClickListener {
    private View view;
    private Button button,button_ol,button_oll;
    private Context context1;
   public AddDeletePopupWindows(Context context){
       context1 = context;
       view = LayoutInflater.from(context1).inflate(R.layout.adddeletepoplayout,null);
       setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
       setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
       setContentView(view);
       setFocusable(true);
       setOutsideTouchable(true);
       setBackgroundDrawable(new BitmapDrawable());
       button = (Button)view.findViewById(R.id.button_adddeletepop_add);
       button_ol = (Button)view.findViewById(R.id.button_adddeletepop_delete);
       button.setOnClickListener(this);
       button_ol.setOnClickListener(this);
       setOnDismissListener(new OnDismissListener() {
           @Override
           public void onDismiss() {

           }
       });
   }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.button_adddeletepop_add:
                 intent.setClass(context1, ActivityCreateRoom.class);
                context1.startActivity(intent);
                break;
            case R.id.button_adddeletepop_delete:

                break;

        }
    }

    public void show(View view1) {
        showAsDropDown(view1);
    }

}
