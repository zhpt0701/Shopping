package com.example.cloudAndPurchasing.activity.activitycloud.activitythis;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.*;
import android.provider.MediaStore;
import android.util.Log;
import android.view.*;
import android.widget.*;
import com.example.cloudAndPurchasing.BaseFragmentActivity;
import com.example.cloudAndPurchasing.MyActivity;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.activity.activitycloud.ActivityIntegral;
import com.example.cloudAndPurchasing.activity.activitycloud.ActivityRecharge;
import com.example.cloudAndPurchasing.activity.activitycloud.activitythis.ActivityAddress;
import com.example.cloudAndPurchasing.http.HttpApi;
import com.example.cloudAndPurchasing.http.HttpTransfeData;
import com.example.cloudAndPurchasing.http.Numbers;
import com.example.cloudAndPurchasing.http.SaveShared;
import com.example.cloudAndPurchasing.imageloder.Myimageloder;
import com.example.cloudAndPurchasing.imageloder.PhotoLevel;
import com.example.cloudAndPurchasing.kind.*;
import com.example.cloudAndPurchasing.manager.MyThreadPoolManager;
import com.example.cloudAndPurchasing.mothendsol.DeviceUtil;
import com.example.cloudAndPurchasing.mothendsol.Tailoring;
import com.example.cloudAndPurchasing.popupwindows.SpinnerPopup;
import com.example.cloudAndPurchasing.sqllite.MySqLite;
import com.example.cloudAndPurchasing.sqllite.SqLiteAddDelete;
import com.example.cloudAndPurchasing.tiltle.TeaskBar;
import com.example.cloudAndPurchasing.zhi.LogUtil;
import com.example.cloudAndPurchasing.zhi.ValuePrice;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.io.IOException;

/**
 * Created by Administrator on 2016/3/31 0031.
 */
public class ActivityThisInformation extends BaseFragmentActivity implements View.OnClickListener {
    private Button button_sex,button_indiana,button_ingral;
    private Button button_name,button_age;
    private Button button,button_ol,btn_ol;
    private LinearLayout button_level;
    private TextView textView;
    private Button imageButton;
    private ImageView imageView,imageView_ol,imageview_ool;
    private boolean flag = false,flags = false;
    private PopupWindow popupWindow,popupWindow1;
    private View view;
    private int TAKE_PICTURE = Numbers.ONE;
    private int PHOTO = Numbers.TWO;
    private int state = Numbers.ZERO;
    private long chicktim = Numbers.ZERO;
    private Integraton integraton;
    private SharedPreferences sharedPreferences;
    private Statese statese;
    private Bitmap bitmap;
    private Myimageloder myimageloder;
    private String path ;
    private String number ;
    private LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = LayoutInflater.from(this).inflate(R.layout.activitythisinformationlayout,null);
        setContentView(view);

        //改变状态栏颜色
        TeaskBar.onSystemoutcolor(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        //初始化数据
        initData();
        //初始化控件
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        try{
            if (!StringUtils.isEmpty(SharePreferencesUlits.getString(getApplication(),Constants.INTEGRATION,Constants.NUll_VALUE))){
                button_ingral.setText(SharePreferencesUlits.getString(getApplication(),Constants.INTEGRATION,Constants.NUll_VALUE));
            }else {
                MyThreadPoolManager.getInstance().execute(runnableIntegral);
            }
        }catch (Exception e){
            LogUtil.i("ActivityThisINform initdata error:",e.toString());
        }finally {
            LogUtil.i("ActivityThisInform initData end.","");
        }
        if (!sharedPreferences.getString("yue","").equals("null")){
            if (!sharedPreferences.getString("yue","").equals("")){
                button_indiana.setText(sharedPreferences.getString("yue",""));
            }else {
                button_indiana.setText("0");
            }
        }else {
            button_indiana.setText("0");
        }
        button_name.setText(sharedPreferences.getString("nickname",""));
        if (!sharedPreferences.getString("level","").equals("")){
            textView.setText(PhotoLevel.level[Integer.parseInt(sharedPreferences.getString("level",""))-1]);
            imageView_ol.setImageResource(PhotoLevel.tupian[Integer.parseInt(sharedPreferences.getString("level",""))-1]);
        }
    }

    public void initData(){
        LogUtil.i("ActivityThisInform initData start.", "");
        myimageloder = new Myimageloder(this);
        sharedPreferences = getSharedPreferences("data",MODE_PRIVATE);
        imageView = (ImageView)findViewById(R.id.imagebutton_My_photool);
        String uid = SaveShared.uid(this);
        imageView.setImageBitmap(null);
        path = HttpApi.yu+"/usermember/getpicture?mid="+uid;
        myimageloder.imageLoader.displayImage(path, imageView, myimageloder.options);
    }

    /**
     * 网络请求
     */
    private Runnable runnableIntegral = new Runnable() {
        @Override
        public void run() {
            integraton = new Integraton();
            String uid = SaveShared.uid(getApplication());
            String token = SaveShared.tokenget(getApplication());
            integraton = HttpTransfeData.integralhttp(getApplication(),token,uid);
            Message message = new Message();
            Bundle bundle = new Bundle();
            message.what = Numbers.ONE;
            bundle.putParcelable("state",integraton);
            message.setData(bundle);
            handler.sendMessage(message);
        }
    };
    public void initView(){
        linearLayout = (LinearLayout)findViewById(R.id.linearlayout_photo_up);
        textView = (TextView)findViewById(R.id.textview_levelname);
        imageView_ol = (ImageView)findViewById(R.id.imageview_tupian);
        button_age = (Button)findViewById(R.id.button_age);
        button_name = (Button)findViewById(R.id.button_nackmame);
        button_sex = (Button)findViewById(R.id.button_sex);
        button_indiana = (Button)findViewById(R.id.button_duobao);
        button_ingral = (Button)findViewById(R.id.button_this_integral);
        button_level = (LinearLayout)findViewById(R.id.button_level);
        button_ol = (Button)findViewById(R.id.button_amend_photo);
        btn_ol = (Button)findViewById(R.id.button_quit);
        imageButton = (Button)findViewById(R.id.imagebutton_informationback);
        imageview_ool = (ImageView)findViewById(R.id.imageview_thisrieht);
        button_name.setText(sharedPreferences.getString("nickname",""));
        if (!sharedPreferences.getString("level","").equals("")){
            textView.setText(PhotoLevel.level[Integer.parseInt(sharedPreferences.getString("level",""))-1]);
            imageView_ol.setImageResource(PhotoLevel.tupian[Integer.parseInt(sharedPreferences.getString("level",""))-1]);
        }
        button_sex.setText("男");
        //设置监听事件
        button_sex.setOnClickListener(this);
        button_level.setOnClickListener(this);
        button_indiana.setOnClickListener(this);
        button_ingral.setOnClickListener(this);
        button_ol.setOnClickListener(this);
        btn_ol.setOnClickListener(this);
        button_age.setOnClickListener(this);
        button_name.setOnClickListener(this);
        imageButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.button_amend_photo:
                flags = true;
                initphotoPopup(flags);
                break;
            case R.id.button_quit:
                try {
                    SqLiteAddDelete.deletetable(this);
                    SharePreferencesUlits.saveString(this, Constants.TOKEN, Constants.NUll_VALUE);
                    SharePreferencesUlits.saveString(this,Constants.PHONE,Constants.NUll_VALUE);
                    SharePreferencesUlits.saveString(this,Constants.COUNTY_ID,Constants.NUll_VALUE);
                    SharePreferencesUlits.saveString(this,Constants.COUNTY_DISTRICT,Constants.NUll_VALUE);
                    SharePreferencesUlits.saveString(this,Constants.CAR_NUMBER,Constants.NUll_VALUE);
                    SharePreferencesUlits.saveString(this,Constants.BALANCE,Constants.NUll_VALUE);
                    SharePreferencesUlits.saveString(this,Constants.ADDRESS,Constants.NUll_VALUE);
                    SharePreferencesUlits.saveString(this,Constants.INTEGRATION,Constants.NUll_VALUE);
                    finish();
                }catch (Exception e){
                    LogUtil.e("ActivityThisInformation button_quit",e.toString());
                }
                break;
            case R.id.button_duobao:
//                try {
//                    intent.putExtra("back",String.valueOf(Numbers.TWO));
//                    intent.setClass(this, ActivityRecharge.class);
//                    startActivity(intent);
//                    finish();
//                }catch (Exception e){
//                    LogUtil.e("ActivityThisInformation button_duobao",e.toString());
//                }

                break;
            case R.id.button_this_integral:
                try {
                    intent.putExtra("num",number);
                    intent.setClass(this, ActivityIntegral.class);
                    startActivity(intent);
                }catch (Exception e){
                    LogUtil.e("ActivityThisInformation button_this_integral",e.toString());
                }

                break;
            case R.id.button_sex:
                flag = true;
                initpopup(flag,v);
                break;
            case R.id.button_level:
                try {
                    intent.setClass(this,ActivityLevel.class);
                    startActivity(intent);
                }catch (Exception e){
                    LogUtil.e("ActivityThisInformation button_level",e.toString());
                }

                break;
            case R.id.button_man:
                button_sex.setText("男");
                flag = false;
                popupWindow.dismiss();
                break;
            case R.id.button_woman:
                button_sex.setText("女");
                flag = false;
                popupWindow.dismiss();
                break;
            case R.id.imagebutton_informationback:
                finish();
                break;
            case R.id.button_carmara:
                state = Numbers.ONE;
                startActivityForResult(new Intent("android.media.action.IMAGE_CAPTURE"), TAKE_PICTURE);
                popupWindow1.dismiss();
                break;
            case R.id.button_album:
                state = Numbers.TWO;
                Intent intent1 = new Intent(Intent.ACTION_PICK);
                intent1.setType("image/*");//相片类型
                startActivityForResult(intent1, PHOTO);
                popupWindow1.dismiss();
                break;
            case R.id.button_cancel_pop:
                popupWindow1.dismiss();
                break;
            case R.id.button_age:
//                try {
//                    intent.putExtra("up", "修改年龄");
//                    intent.putExtra("name","10");
//                    intent.putExtra("da",String.valueOf(Numbers.ONE));
//                    intent.setClass(this,ActivityUpdate.class);
//                    startActivity(intent);
//                    finish();
//                }catch (Exception e){
//                    LogUtil.e("ActivityThisInformation button_age",e.toString());
//                }
                break;
            case R.id.button_nackmame:
                try {
                    intent.putExtra("up", "修改昵称");
                    intent.putExtra("name",sharedPreferences.getString("nickname", ""));
                    intent.putExtra("da",String.valueOf(Numbers.TWO));
                    intent.setClass(this,ActivityUpdate.class);
                    startActivity(intent);
                }catch (Exception e){
                    LogUtil.e("ActivityThisInformation button_nackmame",e.toString());
                }
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (data != null){
                myimageloder.imageLoader.clearDiskCache();
                myimageloder.imageLoader.clearMemoryCache();
                if(bitmap!=null&&!bitmap.isRecycled()){
                    bitmap.recycle() ;
                    bitmap=null;
                    System.gc();
                }
                if (requestCode == TAKE_PICTURE){
                    if (data.getExtras()!=null){
                        Bitmap bm = (Bitmap) data.getExtras().get("data");
                        bitmap = Tailoring.phototailoring(bm);
                        imageView.setImageBitmap(bitmap);
                        linearLayout.setVisibility(View.VISIBLE);
                        MyThreadPoolManager.getInstance().execute(runnablePhoto);
                    }
                }else if (requestCode == PHOTO){
                    if (data.getData()!=null){
                        Uri uri = data.getData();
                        Log.i(getApplication()+"","74362984"+data.getData());
                        if(uri==null){
                            //use bundle to get data
                            Bundle bundle = data.getExtras();
                            Log.i(getApplication()+"","74362984"+bundle);
                            if (bundle!=null) {
                                Bitmap  photo = (Bitmap) bundle.get(uri.toString()); //get bitmap
                                Log.i(getApplication()+"","74362984"+bundle.get("data"));
                                //spath :生成图片取个名字和路径包含类型
//                    Bitmap bitmap1 = Tailoring.ziphone(photo);
//                    String path_ol = Tailoring.photo(bitmap1);
//                    bitmap = Tailoring.phototailoring_ol(photo, getApplication());
                                bitmap = Tailoring.phototailoring(photo);
                                imageView.setImageBitmap(bitmap);
                                linearLayout.setVisibility(View.VISIBLE);
                                MyThreadPoolManager.getInstance().execute(runnablePhoto);
                            } else {
                                Toast.makeText(getApplicationContext(), "err****", Toast.LENGTH_LONG).show();
                                return;
                            }
                        }else{
                            ContentResolver resolver = getContentResolver();
                            Bitmap  bm = null;
                            try {
                                bm = MediaStore.Images.Media.getBitmap(resolver, uri);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            //在相册中做压缩处理在本地做存储sd卡根目录
//                bitmap = Tailoring.phototailoring_ol(bm, getApplication());
                            //在上传时做压缩处理不在本地做存储
                            bitmap = Tailoring.phototailoring(bm);
                            imageView.setImageBitmap(bitmap);
                            linearLayout.setVisibility(View.VISIBLE);
                            try {
                                MyThreadPoolManager.getInstance().execute(runnablePhoto);
                            }catch (Exception e){
                                LogUtil.e("ActivityThisInformation runable1", e.toString());
                            }

                        }
                    }
                }
            }
        }catch (Exception e){
            LogUtil.e("ActivityThisInformation onActivityResult",e.toString());
        }
    }

    /**
     * 图片上传
     */
    private Runnable runnablePhoto = new Runnable() {
        @Override
        public void run() {
            statese = new Statese();
            String token = SaveShared.tokenget(getApplication());
            String uid = SaveShared.uid(getApplication());
            statese = HttpTransfeData.thisphotohttp(getApplication(),uid,token,bitmap,state);
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putParcelable("con_ol",statese);
            message.what = Numbers.TWO;
            message.setData(bundle);
            handler.sendMessage(message);
        }
    };
    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    private void initphotoPopup(boolean flags) {
        popupWindow1 = new PopupWindow();
        View view1 = LayoutInflater.from(this).inflate(R.layout.photopoplayout,null);
        popupWindow1.setContentView(view1);
        popupWindow1.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        popupWindow1.setHeight(WindowManager.LayoutParams.MATCH_PARENT);
        popupWindow1.setFocusable(true);
        popupWindow1.setOutsideTouchable(true);
        popupWindow1.setTouchable(true);
        popupWindow1.setBackgroundDrawable(new BitmapDrawable());
        Button button1 = (Button)view1.findViewById(R.id.button_carmara);
        Button button2 = (Button)view1.findViewById(R.id.button_album);
        Button button3 = (Button)view1.findViewById(R.id.button_cancel_pop);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        if (flags){
            popupWindow1.showAsDropDown(view1);
        }
    }

    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    private void initpopup(boolean flag, View v) {
        popupWindow = new PopupWindow();
        View view = LayoutInflater.from(this).inflate(R.layout.sexlayout,null);
        popupWindow.setContentView(view);
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        Button button = (Button)view.findViewById(R.id.button_woman);
        Button button1 = (Button)view.findViewById(R.id.button_man);
        button.setOnClickListener(this);
        button1.setOnClickListener(this);
        if(flag){
            popupWindow.showAsDropDown(imageview_ool,-150,0);
        }
    }

    /**
     * 界面刷新
     */
    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    integraton = new Integraton();
                    integraton = msg.getData().getParcelable("state");
                    if (integraton!= null){
                        if (!integraton.equals("null")){
                            number = String.valueOf(integraton.getAvailablePoints());
                            button_ingral.setText(String.valueOf(integraton.getAvailablePoints()));
                            SharePreferencesUlits.saveString(getApplication(),Constants.INTEGRATION,String.valueOf(integraton.getAvailablePoints()));
                        }else {
                            button_ingral.setText("0");
                        }
                    }
                    break;
                case 2:
                    statese = new Statese();
                    statese = msg.getData().getParcelable("con_ol");
                    if (statese!=null){
                        if (statese.getMsg().equals("token失效")){
                            String phone = SaveShared.loadong_name(getApplication());
                            if (!phone.equals("")){
                                try {
                                    MyThreadPoolManager.getInstance().execute(runnableSingle);
                                }catch (Exception e){
                                   LogUtil.e("ActivityThisInformation runable3",e.toString());
                                }
                            }
                        }else {
                            Toast.makeText(getApplication(), statese.getMsg(),Toast.LENGTH_SHORT).show();
                        }
                    }
                    linearLayout.setVisibility(View.GONE);
                    break;
                case 3:
                    statese = new Statese();
                    statese = msg.getData().getParcelable("this");
                    if (statese != null){
                        if(statese.getState().equals(String.valueOf(Numbers.ONE))){
                            try {
                                MyThreadPoolManager.getInstance().execute(runnablePhoto);
                            }catch (Exception e){
                                LogUtil.e("ActivityThisInformation runable1_ol", e.toString());
                            }
                        }
                    }
                    break;
            }
        }
    };
    //重新登陆
    private Runnable runnableSingle = new Runnable() {
        @Override
        public void run() {
            String phone = SaveShared.loadong_name(getApplication());
            String pass = SaveShared.loading_pass(getApplication());
            statese = new Statese();
            statese = HttpTransfeData.httppostloding(getApplication(),
                    phone,pass);
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putParcelable("this", statese);
            message.setData(bundle);
            message.what = Numbers.THREE;
            handler.sendMessage(message);
        }
    };
}
