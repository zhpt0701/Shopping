package com.example.cloudAndPurchasing.activity.activitycloud.photo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.*;
import android.provider.MediaStore;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.*;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.activity.activitycloud.ActivityWinningRecord;
import com.example.cloudAndPurchasing.customcontrol.photocontol.*;
import com.example.cloudAndPurchasing.http.HttpTransfeData;
import com.example.cloudAndPurchasing.http.Numbers;
import com.example.cloudAndPurchasing.http.SaveShared;
import com.example.cloudAndPurchasing.kind.Image;
import com.example.cloudAndPurchasing.kind.Statese;
import com.example.cloudAndPurchasing.tiltle.TeaskBar;
import com.example.cloudAndPurchasing.times.CToast;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/4/6 0006.
 */
public class ActivitySurce extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private Button imageButton;
    private Button button;
    private GridView gridView;
    private EditText editText,editText_ol;
    private GridomAdapter gridAdpter;
    private ArrayList<Image> arrayList;
    public static Bitmap bitmap;
    private View parentView,view_ol;
    private static final int TAKE_PICTURE = 0x000001;
    private int [] location=new int[2];
    private PopupWindow popupWindow;
    private boolean flag = false;
    private Statese statese;
    private HandlerThread handlerThread;
    private Handler mhandler;
    private LinearLayout linearLayout_ol;
//    private AdapterGridviewPhoto adapterGridviewPhoto;
    private CToast cToast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Res.init(this);
        bitmap = BitmapFactory.decodeResource(
                getResources(),
                R.drawable.camera_ol);
        PublicWay.activityList.add(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        parentView = getLayoutInflater().inflate(R.layout.activitysurcelayout, null);
        setContentView(parentView);
        //改变状态栏颜色
        TeaskBar.onSystemoutcolor(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        //初始化数据
        initData();
        //初始化控件
        initView();
    }
    public void initData(){
        handlerThread = new HandlerThread("MyHandlerThread");
        handlerThread.start();
        mhandler = new Handler(handlerThread.getLooper());
        arrayList = new ArrayList<Image>();
        gridAdpter = new GridomAdapter(this);
    }
    public void initView(){
        editText = (EditText)findViewById(R.id.edittext_shaidan);
        editText_ol = (EditText)findViewById(R.id.editext_title);
        linearLayout_ol = (LinearLayout)findViewById(R.id.linearlayout_shangchaun);
        imageButton = (Button)findViewById(R.id.imagebutton_xiedan_back);
        button = (Button)findViewById(R.id.button_issue);
        gridView = (GridView)findViewById(R.id.gridview_photo);
        gridView.setAdapter(gridAdpter);

        gridView.setOnItemClickListener(this);
        imageButton.setOnClickListener(this);
        button.setOnClickListener(this);
        view_ol=findViewById(R.id.linearlayout_view_photo);
    }

    /**
     * pop弹出框
     * @param flag
     */
    private void initpopup(Boolean flag) {
        popupWindow = new PopupWindow();
        View view1 = LayoutInflater.from(this).inflate(R.layout.photopoplayout,null);
        popupWindow.setContentView(view1);
        popupWindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(WindowManager.LayoutParams.MATCH_PARENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        Button button1 = (Button)view1.findViewById(R.id.button_carmara);
        Button button2 = (Button)view1.findViewById(R.id.button_album);
        Button button3 = (Button)view1.findViewById(R.id.button_cancel_pop);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        if (flag){
            popupWindow.showAsDropDown(view1);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.imagebutton_xiedan_back:
                Bimp.tempSelectBitmap.clear();
                finish();
                break;
            case R.id.button_issue:
                if (editText_ol.getText().toString().length()>0){
                    if (!containsEmoji(editText_ol.getText().toString())){
                        if (editText.getText().toString().length()>0){
                            if (!containsEmoji(editText.getText().toString())){
                                if ( Bimp.tempSelectBitmap.size()>0){
                                    linearLayout_ol.setVisibility(View.VISIBLE);
                                    InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                                    inputMethodManager.hideSoftInputFromWindow(ActivitySurce.this.getCurrentFocus().getWindowToken(),
                                            InputMethodManager.HIDE_NOT_ALWAYS);
                                    mhandler.post(runnable);
                                }else {
                                    cToast = CToast.makeText(getApplication(),"请选择至少一张图片~",600);
                                    cToast.show();
                                }
                            }else {
                                cToast = CToast.makeText(getApplication(),"暂不支持表情包~",600);
                                cToast.show();
                            }

                        }else {
                            cToast = CToast.makeText(getApplication(),"请输入标题~",600);
                            cToast.show();
                        }
                    }else {
                        cToast = CToast.makeText(getApplication(),"暂不支持表情包~",600);
                        cToast.show();
                    }
                }else {
                    cToast = CToast.makeText(getApplication(),"请输入标题~",600);
                    cToast.show();
                }

                break;
            case R.id.button_photo_obtain:

                break;
            case R.id.button_carmara:
                Intent intent_Ol=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent_Ol, TAKE_PICTURE);
                popupWindow.dismiss();
                view_ol.clearAnimation();
                break;
            case R.id.button_album:
                Intent intent1 = new Intent(ActivitySurce.this,
                        AlbumActivity.class);
                startActivity(intent1);
                overridePendingTransition(R.anim.activity_translate_in, R.anim.activity_translate_out);
                popupWindow.dismiss();
                view_ol.clearAnimation();
                break;
            case R.id.button_cancel_pop:
                popupWindow.dismiss();
                view_ol.clearAnimation();
                break;
        }
    }

    /**
     * 发送评论
     */
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            statese = new Statese();
            SharedPreferences preferences = getSharedPreferences("data",MODE_PRIVATE);
            String pid = preferences.getString("pid","");
            String gid = preferences.getString("gid","");
            String token = SaveShared.tokenget(getApplication());
            String uid = SaveShared.uid(getApplication());
            statese = HttpTransfeData.shaidanhttp(pid,gid,getApplication(),token,uid,editText.getText().toString(),editText_ol.getText().toString());
            Message message = new Message();
            Bundle bundle = new Bundle();
            message.what = Numbers.ONE;
            bundle.putParcelable("content",statese);
            message.setData(bundle);
            handler1.sendMessage(message);
        }
    };
    private Handler handler1 = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    statese = msg.getData().getParcelable("content");
                    if (statese.getState() == "1"){
                        Bimp.tempSelectBitmap.clear();
                        finish();
                    }
                    linearLayout_ol.setVisibility(View.GONE);
                    Toast.makeText(getApplication(),statese.getMsg(),Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == Bimp.tempSelectBitmap.size()) {
            Log.i("ddddddd", "----------");
            view.startAnimation(AnimationUtils.loadAnimation(ActivitySurce.this, R.anim.activity_translate_in));
            view.getLocationOnScreen(location);
            InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(ActivitySurce.this.getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
            flag = true;
            initpopup(flag);
//            popupWindow.showAtLocation(view, Gravity.BOTTOM,location[0],location[1]);
        } else {
            Intent intent = new Intent(ActivitySurce.this,
                    GalleryActivity.class);
            intent.putExtra("position", "1");
            intent.putExtra("ID", position);
            startActivity(intent);
        }
    }
    /**
     * @param （try：String）进行数据提交处理
     * @param （Try：map）传输参数
     * @param （try：path）传输路径
     * @return
     */
    public String getString(String s) {
        String path = null;
        if (s == null)
            return "";
        for (int i = s.length() - 1; i > 0; i++) {
            s.charAt(i);
        }
        return path;
    }
    protected void onRestart() {
        gridAdpter.update();
        super.onRestart();
    }
    //获取图片路径 响应startActivityForResult
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PICTURE:
                if (Bimp.tempSelectBitmap.size() < 3 && resultCode == RESULT_OK) {
                    String fileName = String.valueOf(System.currentTimeMillis());
                    Bitmap bm = (Bitmap) data.getExtras().get("data");
                    Log.i(this+"","r3898eio"+FileUtils.SDPATH+fileName);
                    FileUtils.saveBitmap(bm, fileName);
                    ImageItem takePhoto = new ImageItem();
                    takePhoto.setImagePath(FileUtils.SDPATH+fileName+".JPEG");
                    takePhoto.setBitmap(bm);
                    Bimp.tempSelectBitmap.add(takePhoto);
                }
                break;
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            for(int i=0;i<PublicWay.activityList.size();i++){
                if (null != PublicWay.activityList.get(i)) {
                    PublicWay.activityList.get(i).finish();
                }
            }
            System.exit(0);
        }
        return true;
    }

    /**
     * 内部适配器
     */
    public class GridomAdapter extends BaseAdapter {
        private int selectedPosition = -1;
        private boolean shape;
        private LayoutInflater inflate;
        public boolean isShape() {
            return shape;
        }

        public void setShape(boolean shape) {
            this.shape = shape;
        }

        public GridomAdapter(Context context) {
            inflate = LayoutInflater.from(context);
        }

        public void update() {
            loading();
        }

        public int getCount() {
            if(Bimp.tempSelectBitmap.size() == 3){
                return 3;
            }
            return (Bimp.tempSelectBitmap.size() + 1);
        }

        public Object getItem(int arg0) {
            return null;
        }

        public long getItemId(int arg0) {
            return 0;
        }

        public void setSelectedPosition(int position) {
            selectedPosition = position;
        }

        public int getSelectedPosition() {
            return selectedPosition;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = inflate.inflate(R.layout.item_published_grida,
                        parent, false);
                holder = new ViewHolder();
                holder.image = (ImageView) convertView
                        .findViewById(R.id.item_grida_image);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            if (position == Bimp.tempSelectBitmap.size()) {
                holder.image.setImageBitmap(BitmapFactory.decodeResource(
                        getResources(), R.drawable.camera_ol));
                if (position == 3) {
                    holder.image.setVisibility(View.GONE);
                }
            } else {
                holder.image.setImageBitmap(Bimp.tempSelectBitmap.get(position).getBitmap());
            }

            return convertView;
        }

        public void updata() {
            loading();
        }

        public class ViewHolder {
            public ImageView image;
        }

        Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        gridAdpter.notifyDataSetChanged();
                        break;
                }
                super.handleMessage(msg);
            }
        };

        public void loading() {
            new Thread(new Runnable() {
                public void run() {
                    while (true) {
                        if (Bimp.max == Bimp.tempSelectBitmap.size()) {
                            Message message = new Message();
                            message.what = 1;
                            handler.sendMessage(message);
                            break;
                        } else {
                            Bimp.max += 1;
                            Message message = new Message();
                            message.what = 1;
                            handler.sendMessage(message);
                        }
                    }
                }
            }).start();
        }
    }

    /**
     * 判断字符串否有emoji表情
     * @param source
     * @return
     */
    public boolean containsEmoji(String source) {
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (!isEmojiCharacter(codePoint)) { //如果不能匹配,则该字符是Emoji表情
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否是Emoji
     *
     * @param codePoint 比较的单个字符
     * @return
     */
    private boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) ||
                (codePoint == 0xD) || ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) || ((codePoint >= 0x10000)
                && (codePoint <= 0x10FFFF));
    }
}
