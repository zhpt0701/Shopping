package com.example.cloudAndPurchasing.activity.activitycloud.activityhelp;

import android.app.Activity;
import android.content.Intent;
import android.os.*;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import com.example.cloudAndPurchasing.BaseFragmentActivity;
import com.example.cloudAndPurchasing.MyActivity;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.activity.activitycloud.activityhelp.ActivityHelp;
import com.example.cloudAndPurchasing.http.HttpTransfeData;
import com.example.cloudAndPurchasing.http.Numbers;
import com.example.cloudAndPurchasing.http.SaveShared;
import com.example.cloudAndPurchasing.kind.Statese;
import com.example.cloudAndPurchasing.tiltle.TeaskBar;
import com.example.cloudAndPurchasing.zhi.ValuePrice;
import org.apache.http.client.HttpClient;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/5 0005.
 */
public class ActivityIdea extends BaseFragmentActivity implements View.OnClickListener {
    private Button imageButton;
    private Button button;
    private Handler mhamder;
    private Statese statese;
    private HandlerThread handlerThread;
    private EditText editText,editText_ol;
    private String content = null,opinion = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityidealayout);
        TeaskBar.onSystemoutcolor(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        initData();
        initview();
    }
    public void initData(){
        handlerThread = new HandlerThread("MyHandlerTheard");
        handlerThread.start();
        mhamder = new Handler(handlerThread.getLooper());
    }
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            statese = new Statese();
            String token = SaveShared.tokenget(getApplication());
            String uid = SaveShared.uid(getApplication());
            statese = HttpTransfeData.addfeedbackhttp(getApplication(),content,opinion,token,uid);
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putParcelable("content",statese);
            message.setData(bundle);
            message.what  = Numbers.ONE;
            handler.sendMessage(message);
        }
    };
    /**
     * 登陆
     */
    private Runnable runnable1 = new Runnable() {
        @Override
        public void run() {
            statese = new Statese();
            String user_ol = SaveShared.loadong_name(getApplication());
            String pass_ol = SaveShared.loading_pass(getApplication());
            statese = HttpTransfeData.httppostloding(getApplication(),user_ol,pass_ol);
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putParcelable("this", statese);
            message.setData(bundle);
            message.what = Numbers.TWO;
            handler.sendMessage(message);
        }
    };
    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    statese = msg.getData().getParcelable("content");
                    if (statese != null){
                        if (statese.getState().equals(String.valueOf(Numbers.ONE))){
                            if (statese.getMsg().equals("token失效")){
                                mhamder.post(runnable1);
                            }else {
                                Intent intent = new Intent();
                                intent.setClass(getApplication(),ActivityHelp.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                        Toast.makeText(getApplication(), statese.getMsg(),Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 2:
                    statese = new Statese();
                    statese = msg.getData().getParcelable("this");
                    if (statese != null){
                        if (statese.getState().equals(String.valueOf(Numbers.ONE))){
                            mhamder.post(runnable);
                        }
                        Toast.makeText(getApplication(),statese.getMsg(),Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };
    public void initview(){
        imageButton = (Button)findViewById(R.id.imagebutton_ideaback);
        button = (Button)findViewById(R.id.button_ticking_submit);
        editText = (EditText)findViewById(R.id.edittext_contact_ol);
        editText_ol = (EditText)findViewById(R.id.edittext_opinion);
        //字体设置方法
//        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/kaiti.ttf");
//        editText.setTypeface(typeface);
        button.setOnClickListener(this);
        imageButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imagebutton_ideaback:
                finish();
                break;
            case R.id.button_ticking_submit:
                content = editText.getText().toString();
                opinion = editText_ol.getText().toString();
                if (content.length()>0){
                    if (opinion.length()>0){
                        mhamder.post(runnable);
                    }else {
                        Toast.makeText(this,"请输入您的个人意见",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(this,"请输入您的联系方式",Toast.LENGTH_SHORT).show();
                }


                break;
        }
    }
}
