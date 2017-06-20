package com.example.cloudAndPurchasing.activity.activitysurpired;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.*;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import com.example.cloudAndPurchasing.BaseFragmentActivity;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.activity.ActivityShoppingdetail;
import com.example.cloudAndPurchasing.activity.activityshopping.ActivityGoodsDateil;
import com.example.cloudAndPurchasing.http.HttpTransfeData;
import com.example.cloudAndPurchasing.http.Numbers;
import com.example.cloudAndPurchasing.imageloder.Imageloderinit;
import com.example.cloudAndPurchasing.manager.MyThreadPoolManager;
import com.example.cloudAndPurchasing.tiltle.Background;
import com.example.cloudAndPurchasing.tiltle.TeaskBar;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.BreakIterator;

/**
 * Created by Administrator on 2016/4/12 0012.
 */
public class ActivityInframeText extends BaseFragmentActivity implements View.OnClickListener {
    private Button imageView;
    private WebView webView;
    private Imageloderinit imageloderinit;
    private HandlerThread handlerThread;
    private Handler mhandler;
    private String fh = null;
    private LinearLayout linearLayout,linearLayout_ol;
//    private NetworkImageGetter imageGetter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityinframetextlayout);

        TeaskBar.onSystemoutcolor(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        initData();
        initView();
    }
    public void initData(){
        handlerThread = new HandlerThread("MyHandlerThread");
        handlerThread.start();
        mhandler = new Handler(handlerThread.getLooper());
        MyThreadPoolManager.getInstance().execute(runnable);
    }
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            String id = getIntent().getStringExtra("goodsid");
            String url = HttpTransfeData.imagetexthttp(getApplication(),id);
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putString("data",url);
            message.what = Numbers.ONE;
            message.setData(bundle);
            handler.sendMessage(message);
        }
    };
    public void initView(){
        linearLayout = (LinearLayout)findViewById(R.id.linearlayout_information);
        linearLayout_ol = (LinearLayout)findViewById(R.id.linearlayout_information_lose);
        linearLayout.setVisibility(View.VISIBLE);
        imageView = (Button)findViewById(R.id.imagebutton_inframetext_back);
        webView = (WebView)findViewById(R.id.textveiw_tuwen);
        imageView.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imagebutton_inframetext_back:
                finish();
                break;
                /*if (getIntent().getStringExtra("back").equals(String.valueOf(Numbers.ONE))){
                    intent.setClass(this, ActivityGoodsDateil.class);
                    startActivity(intent);
                }else if (getIntent().getStringExtra("back").equals(String.valueOf(Numbers.TWO))){
                    intent.setClass(this, ActivityShoppingdetail.class);
                    startActivity(intent);
                }else{
                    intent.setClass(this,ActivityShoppingTimeDetail.class);
                    startActivity(intent);
                }
                finish();
                break;*/
        }
    }
    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    String con = msg.getData().getString("data");
                    linearLayout.setVisibility(View.GONE);
                    if (con!=null){
                        linearLayout_ol.setVisibility(View.GONE);
                        getNewContent(con);
                        webView.loadDataWithBaseURL(null, getNewContent(con), "text/html", "utf-8", null);
//                        fh = con;
//                        webView_ol(con);
//                        mhandler.post(runnable1);
                    }else {
                        linearLayout_ol.setVisibility(View.VISIBLE);
                    }
                    break;
                case 2:


//                    textview.setMovementMethod(ScrollingMovementMethod.getInstance());//滚动
//                    textview.setText((CharSequence) msg.obj);
                    break;
            }
        }
    };
//    private Runnable runnable1 = new Runnable() {
//        @Override
//        public void run() {
//            /**
//             * 要实现图片的显示需要使用Html.fromHtml的一个重构方法：public static Spanned
//             * fromHtml (String source, Html.ImageGetterimageGetter,
//             * Html.TagHandler
//             * tagHandler)其中Html.ImageGetter是一个接口，我们要实现此接口，在它的getDrawable
//             * (String source)方法中返回图片的Drawable对象才可以。
//             */
//            WindowManager wm = (WindowManager) getApplication()
//                    .getSystemService(Context.WINDOW_SERVICE);
//
//            final int width = wm.getDefaultDisplay().getWidth();
//            final int height = wm.getDefaultDisplay().getHeight()/2;
//            Html.ImageGetter imageGetter = new Html.ImageGetter() {
//                @Override
//                public Drawable getDrawable(String source) {
//                    // TODO Auto-generated method stub
//                    URL url;
//                    Drawable drawable = null;
//                    try {
//                        url = new URL(HttpApi.tu_ol+source);
//                        Log.i("this","903r82"+HttpApi.tu_ol+source);
//                        drawable = Drawable.createFromStream(
//                                url.openStream(), null);
////                        drawable.setBounds(0, 0,
////                                drawable.getIntrinsicHeight(),
////                                drawable.getIntrinsicHeight());
//                        drawable.setBounds(0, 0,
//                                width,
//                                height);
//                    } catch (MalformedURLException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    }
//                    return drawable;
//                }
//            };
//            CharSequence test = Html.fromHtml(fh, imageGetter, null);
//            Message message = new Message();
//            message.what = Numbers.TWO;
//            message.obj = test;
//            handler.sendMessage(message);
//        }
//    };
    private void webView_ol(String mDescription) {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);//启用js功能
        settings.setLoadWithOverviewMode(true);//适应屏幕
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setUseWideViewPort(true);//关键点

        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setDisplayZoomControls(false);
        settings.setJavaScriptEnabled(true); // 设置支持javascript脚本
        settings.setBuiltInZoomControls(true); // 设置显示缩放按钮
        settings.setSupportZoom(true); // 支持缩放
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int mDensity = metrics.densityDpi;
        Log.d("maomao", "densityDpi = " + mDensity);
        if (mDensity == 240) {
            settings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        } else if (mDensity == 160) {
            settings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        } else if(mDensity == 120) {
            settings.setDefaultZoom(WebSettings.ZoomDensity.CLOSE);
        }else if(mDensity == DisplayMetrics.DENSITY_XHIGH){
            settings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        }else if (mDensity == DisplayMetrics.DENSITY_TV){
            settings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        }else{
            settings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        }
//        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
//        settings.setUseWideViewPort(true);
        //开始加载网页
        linearLayout.setVisibility(View.GONE);
        webView.loadDataWithBaseURL(null, mDescription, "text/html", "utf-8", null);
//        webview.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient() {
            //页面开始加载
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                System.out.println("开始加载....");
            }

            //跳转链接
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                System.out.println("跳转链接...." + url);
                //所有跳转链接强制在当前webview加载,不跳浏览器
//                webview.loadUrl(url);

                return true;
            }

            //加载结束
            @Override
            public void onPageFinished(WebView view, String url) {
                System.out.println("加载结束....");
                super.onPageFinished(view, url);
            }

        });

    }

    /**
     * 处理图片
     * @param htmltext
     * @return
     */
    private String getNewContent(String htmltext){

        Document doc= Jsoup.parse(htmltext);
        Elements elements=doc.getElementsByTag("img");
        for (Element element : elements) {
            element.attr("width","100%").attr("height","auto");
        }
        return doc.toString();
    }
}
