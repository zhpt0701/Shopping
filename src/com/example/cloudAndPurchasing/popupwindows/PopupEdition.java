package com.example.cloudAndPurchasing.popupwindows;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.adapter.adaptermain.AdapterCityPopupone;
import com.example.cloudAndPurchasing.http.Numbers;
import com.example.cloudAndPurchasing.kind.TZone;
import com.example.cloudAndPurchasing.service.VersionService;
import com.example.cloudAndPurchasing.sqlliste.DataStore;
import junit.runner.Version;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/7/6 0006.
 */
public class PopupEdition extends PopupWindow implements View.OnClickListener {
    private String version;
    private View view;
    private TextView textView,textView_ol;
    private Button button,button_ol;
    private Context context1;
    private String net_path = null;

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public PopupEdition(Context context,boolean fg,String path){
        net_path = path;
        context1 = context;
        view = LayoutInflater.from(context).inflate(R.layout.popupeditionlayout,null);
        setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        setHeight(WindowManager.LayoutParams.MATCH_PARENT);
        setContentView(view);

        setOutsideTouchable(true);
        setFocusable(true);
        setClippingEnabled(true);
        setBackgroundDrawable(new BitmapDrawable());
        textView = (TextView)view.findViewById(R.id.textview_edition_title);
        textView_ol = (TextView)view.findViewById(R.id.textview_edition_content);
        button = (Button)view.findViewById(R.id.button_shaohou);
        button_ol = (Button)view.findViewById(R.id.button_once);
        button_ol.setOnClickListener(this);
        button.setOnClickListener(this);
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = null;
            info = manager.getPackageInfo(context.getPackageName(), 0);
            version = info.versionName;
            JSONObject jsonObject = new JSONObject(path);
            if (!version.equals(jsonObject.getString("Version"))){
                textView.setText("新版本");
                textView_ol.setText(jsonObject.getString("UpdateFile"));
            }else {
                textView.setText("暂无新版本");
                textView_ol.setText("当前版本"+jsonObject.getString("Remark"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                arrayList = DataStore.Zonedataselect(context, citysort);
//                Message message = new Message();
//                Bundle bundle = new Bundle();
//                bundle.putParcelableArrayList("zone",arrayList);
//                message.what = Numbers.ONE;
//                message.setData(bundle);
//                handler.sendMessage(message);
//            }
//        }).start();
//        button.setOnClickListener(this);
        if (fg){
            showAsDropDown(view,0,0,Gravity.CENTER);
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_shaohou:
                dismiss();
                break;
            case R.id.button_once:
                if (net_path != null){
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    Uri content_url = Uri.parse(net_path);
                    intent.setData(content_url);
                    context1.startActivity(intent);
                }else {
                    Toast.makeText(context1,"暂无新版本",Toast.LENGTH_SHORT).show();
                }
//                Intent intent = new Intent();
//                intent.setClass(context1, VersionService.class);
//                context1.startService(intent);
//                dismiss();
                break;
        }
    }
}
