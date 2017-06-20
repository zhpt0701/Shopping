package com.example.cloudAndPurchasing.activity.activitycloud.activitythis;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;
import com.example.cloudAndPurchasing.BaseFragmentActivity;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.activity.activitycloud.ActivityIntegral;
import com.example.cloudAndPurchasing.adapter.adaptercloud.AdapterLevel;
import com.example.cloudAndPurchasing.adapter.adaptersurpired.AdapterPhoto;
import com.example.cloudAndPurchasing.customcontrol.ThisGridView;
import com.example.cloudAndPurchasing.customcontrol.ThisListView;
import com.example.cloudAndPurchasing.http.HttpApi;
import com.example.cloudAndPurchasing.http.SaveShared;
import com.example.cloudAndPurchasing.imageloder.Imageloderinit;
import com.example.cloudAndPurchasing.imageloder.Myimageloder;
import com.example.cloudAndPurchasing.kind.Level;
import com.example.cloudAndPurchasing.kind.Share;
import com.example.cloudAndPurchasing.tiltle.TeaskBar;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/28 0028.
 */
public class ActivityLevel extends BaseFragmentActivity implements View.OnClickListener {
    private ImageView imageView,imageView_ol;
    private Button imageButton;
    private TextView textView,textView_ol,textView_level;
    private ThisListView listView;
    private AdapterLevel adapterLevel;
    private ArrayList<Level> arrayList;
    private Bitmap bitmap1;
    private Myimageloder myimageloder;
    String[] level={"梦想少校","梦想中校","梦想上校","梦想大校","梦想少将","梦想中将","梦想上将","梦想大将","大土豪","超级大土豪"};
    String[] jinyan = {"0","1000","10000","50000","200000","800000","1500000","3000000","5000000","10000000"};
    int[] tupian = {R.drawable.dengji_one,R.drawable.dengji_two,R.drawable.dengji_three
            ,R.drawable.dengji_four,R.drawable.dengji_five,R.drawable.dengji_six
            ,R.drawable.dengji_seven,R.drawable.dengji_eight,R.drawable.dengji_nine,R.drawable.dengji_ten};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitylevellayout);
        TeaskBar.onSystemoutcolor(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        initData();
        initView();
    }

    private void initData() {
        myimageloder = new Myimageloder(getApplication());
        arrayList = new ArrayList<Level>();
        for (int c = 0 ; c < level.length;c++){
            Level level1 = new Level();
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),tupian[c]);
            level1.setLevelname(level[c]);
            level1.setJiangyan(jinyan[c]);
            level1.setLevelphoto(bitmap);
            arrayList.add(level1);
        }
        adapterLevel = new AdapterLevel(this,arrayList);
    }
    public void initView(){
        imageButton = (Button)findViewById(R.id.imagebuton_levelback);
        imageView = (ImageView)findViewById(R.id.imageview_levelphoto);
        textView = (TextView)findViewById(R.id.textview_levelid);
        imageView_ol = (ImageView)findViewById(R.id.imageview_level);
        textView_ol = (TextView)findViewById(R.id.textview_levelone);
        textView_level = (TextView)findViewById(R.id.textview_experience);
        listView = (ThisListView)findViewById(R.id.listview_level);
        listView.setAdapter(adapterLevel);
        SharedPreferences sharedPreferences = getSharedPreferences("data",MODE_PRIVATE);
        textView.setText(sharedPreferences.getString("phone",""));
        textView_level.setText("经验值: 500/1000");
        textView_ol.setText(level[Integer.parseInt(sharedPreferences.getString("level",""))-1]);
        imageView_ol.setImageResource(tupian[Integer.parseInt(sharedPreferences.getString("level",""))-1]);
        String uid = SaveShared.uid(getApplication());
        myimageloder.imageLoader.displayImage(HttpApi.mytu+uid,imageView,myimageloder.options);
        imageButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.imagebuton_levelback:
                finish();
                break;
        }
    }
}
