package com.example.cloudAndPurchasing.activity.activitycloud.activityinstall;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.*;
import android.text.TextUtils;
import android.view.*;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import com.example.cloudAndPurchasing.BaseFragmentActivity;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.http.HttpTransfeData;
import com.example.cloudAndPurchasing.http.Numbers;
import com.example.cloudAndPurchasing.kind.Statese;
import com.example.cloudAndPurchasing.kind.StringUtils;
import com.example.cloudAndPurchasing.kind.VersionOL;
import com.example.cloudAndPurchasing.manager.MyThreadPoolManager;
import com.example.cloudAndPurchasing.popupwindows.PopupEdition;
import com.example.cloudAndPurchasing.service.VersionCode;
import com.example.cloudAndPurchasing.tiltle.TeaskBar;
import com.example.cloudAndPurchasing.zhi.LogUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.PipedOutputStream;

/**
 * Created by Administrator on 2016/7/6 0006.
 */
public class ActivityUs extends BaseFragmentActivity implements View.OnClickListener {
    private Button button,button_ol;
    private boolean fg = false;
    private String path = null;
    private Statese statese;
    private VersionOL versionOL;
    private boolean fb = false;
    private String version;
    private int versionCode;
    private View view;
    private PopupWindow popupWindow;
    private TextView textView;
    private TextView textView_ol;
    private Button button1;
    private Button button2;
    private String net_path = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityuslayout);

        TeaskBar.onSystemoutcolor(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        initData();
        initview();
    }

    private void initview() {
        button = (Button)findViewById(R.id.button_back_setting);
        button_ol = (Button)findViewById(R.id.button_banbenxinxi);
        button.setOnClickListener(this);
        button_ol.setOnClickListener(this);
    }

    private void initData() {
        LogUtil.i("ActivityUs initData start.","");
        versionOL = new VersionOL();
        PackageManager manager = this.getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(getPackageName(), 0);
            version = info.versionName;
            versionCode = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        LogUtil.i("ActivityUs initData end.","");
    }

    /**
     * 获取版本号
     */
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            statese = new Statese();
            statese = HttpTransfeData.httppostvertion(getApplicationContext(), statese,String.valueOf(versionCode));
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putParcelable("version",statese);
            message.what = Numbers.ONE;
            message.setData(bundle);
            handler.sendMessage(message);
        }
    };

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.button_back_setting:
                finish();
                break;
            case R.id.button_banbenxinxi:
                fg = true;
                MyThreadPoolManager.getInstance().execute(runnable);
                break;
            case R.id.button_shaohou:
                popupWindow.dismiss();
                break;
            case R.id.button_once:
                if (!StringUtils.isEmpty(net_path)){
                    popupWindow.dismiss();
                    intent.setAction("android.intent.action.VIEW");
                    Uri content_url = Uri.parse(net_path);
                    intent.setData(content_url);
                    startActivity(intent);
                }else {
                    popupWindow.dismiss();
                    Toast.makeText(getApplication(), "暂无新版本", Toast.LENGTH_SHORT).show();
                }
//                Intent intent = new Intent();
//                intent.setClass(context1, VersionService.class);
//                context1.startService(intent);
//                dismiss();
                break;
        }
    }
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    versionOL = VersionCode.versioncontent(getApplicationContext());
                    statese = new Statese();
                    statese = msg.getData().getParcelable("version");
                    if (statese!= null){
                        if (statese.getState().equals(String.valueOf(Numbers.ONE))){
                            fb = true;
                            PopupEdition(fb,statese.getData());
                        }
                    }
                    break;
            }
        }
    };
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void PopupEdition(boolean fg,String path){
        popupWindow = new PopupWindow();
        view = LayoutInflater.from(this).inflate(R.layout.popupeditionlayout,null);
        popupWindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(WindowManager.LayoutParams.MATCH_PARENT);
        popupWindow.setContentView(view);

        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setClippingEnabled(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        textView = (TextView) view.findViewById(R.id.textview_edition_title);
        textView_ol = (TextView) view.findViewById(R.id.textview_edition_content);
        button1 = (Button) view.findViewById(R.id.button_shaohou);
        button2 = (Button) view.findViewById(R.id.button_once);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        try {
            PackageManager manager = getApplication().getPackageManager();
            PackageInfo info = null;
            info = manager.getPackageInfo(getApplication().getPackageName(), 0);
            JSONObject jsonObject = new JSONObject(path);
            if (versionCode<jsonObject.getInt("Version")){
                textView.setText("新版本");
                net_path = jsonObject.getString("UpdateFile");
                textView_ol.setText(jsonObject.getString("UpdateFile"));
                button2.setVisibility(View.VISIBLE);
            }else {
                textView.setText("暂无新版本");
                textView_ol.setText("当前版本" + jsonObject.getString("Remark"));
                button2.setVisibility(View.GONE);
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
            popupWindow.showAsDropDown(view, 0, 0, Gravity.CENTER);
        }
    }
}
