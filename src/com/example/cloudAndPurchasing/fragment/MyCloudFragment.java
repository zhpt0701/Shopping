package com.example.cloudAndPurchasing.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.cloudAndPurchasing.MyActivity;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.activity.activitycloud.ActivityCloudRecord;
import com.example.cloudAndPurchasing.activity.activitycloud.ActivityPublish;
import com.example.cloudAndPurchasing.activity.activitycloud.ActivityRecharge;
import com.example.cloudAndPurchasing.activity.activitycloud.ActivityWinningRecord;
import com.example.cloudAndPurchasing.activity.activitycloud.activityfriend.ActivityFriend;
import com.example.cloudAndPurchasing.activity.activitycloud.activityhelp.ActivityHelp;
import com.example.cloudAndPurchasing.activity.activitycloud.activityinstall.ActivitySetting;
import com.example.cloudAndPurchasing.activity.activitycloud.activitynew.ActivityNews;
import com.example.cloudAndPurchasing.activity.activitycloud.activitythis.ActivityAddress;
import com.example.cloudAndPurchasing.activity.activitycloud.activitythis.ActivityThisInformation;
import com.example.cloudAndPurchasing.activity.activityloadsingleup.ActivityLoding;
import com.example.cloudAndPurchasing.activity.activityloadsingleup.ActivitySigleUp;
import com.example.cloudAndPurchasing.http.HttpApi;
import com.example.cloudAndPurchasing.http.HttpTransfeData;
import com.example.cloudAndPurchasing.http.Numbers;
import com.example.cloudAndPurchasing.http.SaveShared;
import com.example.cloudAndPurchasing.imageloder.Myimageloder;
import com.example.cloudAndPurchasing.kind.*;
import com.example.cloudAndPurchasing.manager.MyThreadPoolManager;
import com.example.cloudAndPurchasing.zhi.LogUtil;
import com.umeng.socialize.UMShareAPI;

import java.util.ArrayList;

/**
 * Created by zhangpengtao on 2016/3/21 0021.
 */
public class MyCloudFragment extends Fragment implements View.OnClickListener {
    private TextView textView_name,textView_age,textView_number,textView_jifenumber,textview_xiaoxi;
    private Button button_chonzhi,button_zhuce,button_denglu,button_address,
            button_yubgoujilu,button_zhongjiangjilu,button_myshaisdan,button_haoyou
            ,button_bangzhu,button_kefu,imagebutton_fenxiang,imagebutton_shezhi;
    private ImageButton imagebutton_thisinfo;
    private ImageView imageView,imageView_ol;
    private LinearLayout linearLayout,linearLayout_ol,linearLayout_ll,linearlayout_xiaoxi;
    private boolean falg = false;
    private View view;
    private String token = null;
    private Statese statese;
    private int please = 0,friend = 0;
    private ArrayList<Single> arrayList;
    private  ArrayList<Single> this_ol ;
    private Myimageloder myimageloder;
    private String name = null,leve = null;
    private SharedPreferences dh;
    private int num = 0;
    private int[] leve_ol = {R.drawable.dengji_one,R.drawable.dengji_two,R.drawable.dengji_three,
            R.drawable.dengji_four,R.drawable.dengji_five,R.drawable.dengji_six,
            R.drawable.dengji_seven,R.drawable.dengji_eight,R.drawable.dengji_nine,R.drawable.dengji_ten,};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
         view = inflater.inflate(R.layout.fragmentmycloud_layout,null);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        inittextview(view);
        initData(view);
        initview(view);
    }

    /**
     * 初始化数据
     * @param view
     */
    public void initData(View view){
        LogUtil.i("FragmentMyCloud initData start.","");
        imageView_ol = (ImageView)view.findViewById(R.id.imageview_this_photo);
        myimageloder = new Myimageloder(getActivity());
        linearLayout = (LinearLayout)view.findViewById(R.id.linearlayout_denglu);
        linearLayout_ol = (LinearLayout)view.findViewById(R.id.linearlayout_weidenglu);
        dh = getActivity().getSharedPreferences("data", getActivity().getApplicationContext().MODE_PRIVATE);
        token = dh.getString("token","");
        if (!token.equals("")){
            linearLayout.setVisibility(View.VISIBLE);
            linearLayout_ol.setVisibility(View.GONE);
            textView_name.setText(dh.getString("nickname", ""));
            if (!StringUtils.isEmpty(dh.getString("yue",""))){
                textView_number.setText(dh.getString("yue",""));
            }else {
                textView_number.setText("0");
            }
            imageView.setImageResource(leve_ol[Integer.parseInt(dh.getString("level", ""))-1]);
            String uid = SaveShared.uid(getActivity());
            myimageloder.imageLoader.displayImage(HttpApi.yu+"/usermember/getpicture?mid="+uid, imageView_ol, myimageloder.options);
            try {
                MyThreadPoolManager.getInstance().execute(runnable);
                if (StringUtils.isEmpty(SharePreferencesUlits.getString(getActivity(),Constants.CAR_NUMBER,Constants.NUll_VALUE))){
                    MyThreadPoolManager.getInstance().execute(runnableCarNumber);
                }else {
                    MyActivity.textView.setText(SharePreferencesUlits.getString(getActivity(),Constants.CAR_NUMBER,Constants.NUll_VALUE));
                }
            }catch (Exception e){
                LogUtil.e("FragmentMyCloud initData error:",e.toString());
            }finally {
                LogUtil.i("FragmentMyCloud initData end.","");
            }
        }else {
            MyActivity.textView.setText("0");
            imageView_ol.setImageResource(R.drawable.head16);
            linearLayout_ol.setVisibility(View.VISIBLE);
            linearLayout.setVisibility(View.GONE);
        }

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                arrayList = new ArrayList<Single>();
//                 arrayList = SqLiteAddDelete.findall(getActivity());
//                if (arrayList.size()>0){
//                    Log.i("fjslkfks", "jfslfksfajuio" + arrayList.get(0).getLevelid());
//                    Message message = new Message();
//                    Bundle bundle = new Bundle();
//                    bundle.putParcelableArrayList("cusor", arrayList);
//                    message.what = Number.THREE;
//                    message.setData(bundle);
//                    handler.handleMessage(message);
//                }else {
//                    linearLayout_ol.setVisibility(View.VISIBLE);
//                    linearLayout.setVisibility(View.GONE);
//                }
//            }
//        }).start();
    }
    //http获取消息数量
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
           statese = new Statese();
            String token_ol = SaveShared.tokenget(getActivity());
            statese = HttpTransfeData.httpmessageallnumber(getActivity(),token_ol);
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putParcelable("con",statese);
            message.what = Numbers.ONE;
            message.setData(bundle);
            handler.sendMessage(message);
        }
    };

    public void setCurrentFragment (int index){
        ((MyActivity)getActivity()).setCurrentFargmentIndex(index);
    }
    /**
     * 实例化控件
     * @param v
     */
    public void initview(View v){
        linearLayout_ll = (LinearLayout)v.findViewById(R.id.linearlayout_this);
        initbutton(v);
        linearLayout_ll.setOnClickListener(this);
        imagebutton_fenxiang = (Button)v.findViewById(R.id.imagebutton_fenxiang);
        imagebutton_shezhi = (Button)v.findViewById(R.id.imagebutton_setting);
        imagebutton_fenxiang.setOnClickListener(this);
        imagebutton_shezhi.setOnClickListener(this);
        imageView_ol.setOnClickListener(this);
    }

    /**
     * 实例化textview控件
     * @param view
     */
    private void inittextview(View view) {
        textView_name = (TextView)view.findViewById(R.id.textview_name);
        textView_number = (TextView)view.findViewById(R.id.textview_shuliang);
        textview_xiaoxi = (TextView)view.findViewById(R.id.textview_xiaoxi);
        imageView = (ImageView)view.findViewById(R.id.imageview_levelthis_ol);
    }

    /**
     * 实例化button控件并设置（onClick）监听事件
     * @param view
     */
    public void initbutton(View view){
        button_chonzhi = (Button)view.findViewById(R.id.button_chongzhi);
        button_zhuce = (Button)view.findViewById(R.id.button_single);
        button_denglu = (Button)view.findViewById(R.id.button_loading);
        button_address = (Button)view.findViewById(R.id.button_addresses);
        button_yubgoujilu = (Button)view.findViewById(R.id.button_cloudjilu);
        button_zhongjiangjilu = (Button)view.findViewById(R.id.button_zhongjiangjilu);
        button_myshaisdan = (Button)view.findViewById(R.id.button_myshaidan);
        button_haoyou = (Button)view.findViewById(R.id.button_myfriend);
        button_bangzhu = (Button)view.findViewById(R.id.button_help);
        button_kefu = (Button)view.findViewById(R.id.button_kefu);
        linearlayout_xiaoxi = (LinearLayout)view.findViewById(R.id.button_my_word_ol);
        linearlayout_xiaoxi.setOnClickListener(this);
        button_chonzhi.setOnClickListener(this);
        button_zhuce.setOnClickListener(this);
        button_denglu.setOnClickListener(this);
        button_address.setOnClickListener(this);
        button_yubgoujilu.setOnClickListener(this);
        button_zhongjiangjilu.setOnClickListener(this);
        button_myshaisdan.setOnClickListener(this);
        button_haoyou.setOnClickListener(this);
        button_bangzhu.setOnClickListener(this);
        button_kefu.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.imagebutton_fenxiang:
//                falg = true;
//                SharePopupwindows sharePopupwindows = new SharePopupwindows(getActivity(),view);
//                sharePopupwindows.show();
                break;
            case R.id.imagebutton_setting:
                intent.setClass(getActivity(), ActivitySetting.class);
                startActivity(intent);
                break;
            case R.id.imageview_this_photo:
                if (!token.equals("")){
                    intent.setClass(getActivity(), ActivityThisInformation.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(getActivity(),"请登录",Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.linearlayout_this:
                if (!token.equals("")){
                    intent.setClass(getActivity(), ActivityThisInformation.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(getActivity(),"请登录",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.button_chongzhi:
                intent.putExtra("back", String.valueOf(Numbers.ONE));
                intent.setClass(getActivity(), ActivityRecharge.class);
                startActivity(intent);
                break;
            case R.id.button_single:
                intent.setClass(getActivity(), ActivitySigleUp.class);
                startActivity(intent);
                break;
            case R.id.button_loading:
                falg = true;
                intent.setClass(getActivity(), ActivityLoding.class);
                startActivity(intent);
                break;
            case R.id.button_addresses:
                if (!token.equals("")){
                    intent.setClass(getActivity(), ActivityAddress.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(getActivity(),"请登录",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.button_my_word_ol:
                if (!token.equals("")){
                    intent.setClass(getActivity(), ActivityNews.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(getActivity(),"请登录",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.button_cloudjilu:
                if (!token.equals("")){
                    intent.setClass(getActivity(), ActivityCloudRecord.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(getActivity(),"请登录",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.button_zhongjiangjilu:
                if (!token.equals("")){
                    intent.putExtra("update", "2");
                    intent.setClass(getActivity(), ActivityWinningRecord.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(getActivity(),"请登录",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.button_myshaidan:
                if (!token.equals("")){
                    intent.putExtra("shaidan", "我的晒单");
                    intent.setClass(getActivity(), ActivityPublish.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(getActivity(),"请登录",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.button_myfriend:
                if (!token.equals("")){
                    intent.putExtra("back", String.valueOf(Numbers.ONE));
                    intent.setClass(getActivity(), ActivityFriend.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(getActivity(),"请登录",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.button_help:
                intent.setClass(getActivity(), ActivityHelp.class);
                startActivity(intent);
                break;
            case R.id.button_kefu:

                break;
        }
    }

    /**
     * 主界面刷新
     */
    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    statese = new Statese();
                    statese = msg.getData().getParcelable("con");
                        if (statese!= null){
                            if (statese.getState().equals(String.valueOf(Numbers.ONE))){
                                friend = statese.getAddFriendCount();
                                please = statese.getInviteCount();
                                if (friend+please>0){
                                    textview_xiaoxi.setText("有新消息("+(friend+please)+")");
                                }else {
                                    textview_xiaoxi.setText("");
                                }
                            }else {
                                if(!StringUtils.isEmpty(statese.getMsg())){
                                    if (statese.getMsg().equals("token失效")){
                                        String phone = dh.getString("phone_ol","");
                                        if (!phone.equals("")){
                                            MyThreadPoolManager.getInstance().execute(runnableSingle);
                                        }
                                    }
                                }
                            }

                        }
                    break;
                case 2:
                    statese = new Statese();
                    statese = msg.getData().getParcelable("this");
                    if (statese!=null){
                        if (!StringUtils.isEmpty(statese.getState())){
                            if (statese.getState().equals(String.valueOf(Numbers.ONE))){
                                MyThreadPoolManager.getInstance().execute(runnable);
                                MyThreadPoolManager.getInstance().execute(runnableCarNumber);
                            }
                        }
                    }
                    break;
                case 3:
                statese = new Statese();
                statese = msg.getData().getParcelable("content");
                if (statese!=null){
                    if (!StringUtils.isEmpty(statese.getState())){
                        if (statese.getState().equals(String.valueOf(Numbers.ONE))){
                            if (!StringUtils.isEmpty(statese.getData())){
                                MyActivity.textView.setText(statese.getData());
                                SharePreferencesUlits.saveString(getActivity(), Constants.CAR_NUMBER,statese.getData());
                            }else {
                                num++;
                                if (num <3){
                                    MyThreadPoolManager.getInstance().execute(runnableCarNumber);
                                }
                            }
                        }else {
                            if(!StringUtils.isEmpty(statese.getMsg())){
                                if (statese.getMsg().equals("token失效")){
                                    String phone = dh.getString("phone_ol","");
                                    if (!phone.equals("")){
                                        MyThreadPoolManager.getInstance().execute(runnableSingle);
                                    }
                                }else {
                                    num++;
                                    if (num<3){
                                        MyThreadPoolManager.getInstance().execute(runnableCarNumber);
                                    }
                                }
                            }
                        }
                    }
                }
                break;
            }
        }
    };
    private Runnable runnableSingle = new Runnable() {
        @Override
        public void run() {
            String phone = dh.getString("phone_ol","");
            String pass = dh.getString("pass_ol","");
            statese = new Statese();
            statese = HttpTransfeData.httppostloding(getActivity(),
                    phone,pass);
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putParcelable("this", statese);
            message.setData(bundle);
            message.what = Numbers.TWO;
            handler.sendMessage(message);
        }
    };
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /** attention to this below ,must add this**/
        UMShareAPI.get(getActivity()).onActivityResult(requestCode, resultCode, data);
        Log.d("result","onActivityResult");
    }
    /**
     * 获取商品件数
     */
    private Runnable runnableCarNumber = new Runnable() {
        @Override
        public void run() {
            statese = new Statese();
            String token = SaveShared.tokenget(getActivity());
            if (!TextUtils.isEmpty(token)){
                statese = HttpTransfeData.getcarnumberhttp(statese,getActivity(),token);
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putParcelable("content",statese);
                message.what = Numbers.THREE;
                message.setData(bundle);
                handler.sendMessage(message);
            }
        }
    };
}
