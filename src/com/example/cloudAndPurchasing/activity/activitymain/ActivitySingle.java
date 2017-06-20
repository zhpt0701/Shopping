package com.example.cloudAndPurchasing.activity.activitymain;

import android.content.Intent;
import android.os.*;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.example.cloudAndPurchasing.BaseFragmentActivity;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.activity.activitysurpired.ActivityShareDetail;
import com.example.cloudAndPurchasing.adapter.adaptermain.AdapterGoodsShare;
import com.example.cloudAndPurchasing.customcontrol.listviewupdown.PullToRefreshLayout;
import com.example.cloudAndPurchasing.http.HttpTransfeData;
import com.example.cloudAndPurchasing.http.Numbers;
import com.example.cloudAndPurchasing.http.SaveShared;
import com.example.cloudAndPurchasing.imageloder.Imageloderinit;
import com.example.cloudAndPurchasing.kind.Publish;
import com.example.cloudAndPurchasing.manager.MyThreadPoolManager;
import com.example.cloudAndPurchasing.tiltle.TeaskBar;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/3/24 0024.
 */
public class ActivitySingle extends BaseFragmentActivity implements View.OnClickListener,PullToRefreshLayout.OnRefreshListener {
    private Button imageButton;
    private ListView listView;
    private AdapterGoodsShare adapterGoodsShare;
    private Handler mhandler;
    private HandlerThread handlerThread;
    private PullToRefreshLayout pullToRefreshLayout;
    private LinearLayout linearLayout;
    private Imageloderinit imageloderinit;
    private ArrayList<Publish> arrayList,publishs;
    private int index = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitysinglelayout);

        TeaskBar.onSystemoutcolor(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        initData();
        initview();
    }
    public void initData(){
        index = 1;
        imageloderinit = new Imageloderinit(this);
        handlerThread = new HandlerThread("MyHandlerThread");
        handlerThread.start();
        mhandler = new Handler(handlerThread.getLooper());
        MyThreadPoolManager.getInstance().execute(runnable);
        publishs = new ArrayList<Publish>();
    }

    /**
     * 获取所有晒单信息
     */
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            String token = SaveShared.tokenget(getApplication());
            arrayList = HttpTransfeData.allgoodspublishhttp(getApplication(),token,index);
            if (arrayList != null){
                publishs.addAll(arrayList);
            }
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("content",publishs);
            message.setData(bundle);
            message.what = Numbers.ONE;
            handler1.sendMessage(message);
        }
    };
    public void initview(){
        linearLayout = (LinearLayout)findViewById(R.id.linearlayout_publish_all);
        linearLayout.setVisibility(View.VISIBLE);
        pullToRefreshLayout = (PullToRefreshLayout)findViewById(R.id.pulllayout_single);
        imageButton = (Button)findViewById(R.id.iamgebutton_return);
        listView = (ListView)findViewById(R.id.listview_goodsshare);
        listView.setOnScrollListener(new PauseOnScrollListener(imageloderinit.imageLoader,true,false));
        imageButton.setOnClickListener(this);
        pullToRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iamgebutton_return:
                finish();
                break;
        }

    }
    private Handler handler1 = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    linearLayout.setVisibility(View.GONE);
                    arrayList = new ArrayList<Publish>();
                    arrayList = msg.getData().getParcelableArrayList("content");
                    if (arrayList!=null){
                        Log.i(getApplication()+"","230329782"+arrayList.size());
                        adapterGoodsShare = new AdapterGoodsShare(imageloderinit.imageLoader,imageloderinit.options,getApplication(),arrayList);
                        listView.setAdapter(adapterGoodsShare);
                        listView.setSelected(true);
                        listView.setSelection(adapterGoodsShare.getCount()-10);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent();
                                intent.putExtra("commid",arrayList.get(position).getQID());
                                intent.putExtra("content_ol",arrayList.get(position).getContent());
                                intent.putExtra("time",arrayList.get(position).getTime());
                                intent.putExtra("name",arrayList.get(position).getNickname());
                                intent.putExtra("photopath",arrayList.get(position).getImagepath());
                                intent.putExtra("this",String.valueOf(Numbers.THREE));
                                intent.putExtra("title",arrayList.get(position).getTitle());
                                intent.putExtra("praise",arrayList.get(position).getFavorCount());
                                intent.putExtra("publish",arrayList.get(position).getCommendnumber());
                                intent.setClass(getApplication(),ActivityShareDetail.class);
                                startActivity(intent);

                            }
                        });
                    }
                    break;
            }
        }
    };
    @Override
    protected void onDestroy() {
        //mhandler.removeCallbacks(runnable);
        if (arrayList != null){
            arrayList.clear();
        }
        if (publishs != null){
            publishs.clear();
        }
        super.onDestroy();
    }

    @Override
    public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
        new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                publishs = new ArrayList<Publish>();
                index = 1;
                MyThreadPoolManager.getInstance().execute(runnable);
                pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
            }
        }.sendEmptyMessageDelayed(0, 1500);
    }

    @Override
    public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
        new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                index++;
                MyThreadPoolManager.getInstance().execute(runnable);
                pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
            }
        }.sendEmptyMessageDelayed(0, 1500);
    }
}
