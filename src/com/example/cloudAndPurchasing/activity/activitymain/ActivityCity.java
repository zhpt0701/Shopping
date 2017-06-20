package com.example.cloudAndPurchasing.activity.activitymain;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.*;
import android.widget.*;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.cloudAndPurchasing.BaseFragmentActivity;
import com.example.cloudAndPurchasing.MyActivity;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.kind.City;
import com.example.cloudAndPurchasing.mothendsol.DBHelper;
import com.example.cloudAndPurchasing.mothendsol.DatabaseHelper;
import com.example.cloudAndPurchasing.mothendsol.MyLetterListView;
import com.example.cloudAndPurchasing.mothendsol.PingYinUtil;
import com.example.cloudAndPurchasing.tiltle.TeaskBar;
import com.example.cloudAndPurchasing.times.CToast;

import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/4/15 0015.
 */
public class ActivityCity extends BaseFragmentActivity implements AbsListView.OnScrollListener, View.OnClickListener {
    private BaseAdapter adapter;
    private ResultListAdapter resultListAdapter;
    private ListView personList;
    private ListView resultList;
    private TextView overlay;
    private MyLetterListView letterListView; // A-Z listview
    private HashMap<String, Integer> alphaIndexer;
    private String[] sections;
    private Handler handler;
    private OverlayThread overlayThread;
    private ArrayList<City> allCity_lists;
    private ArrayList<City> city_lists;
    private ArrayList<City> city_hot;
    private ArrayList<City> city_result;
    private ArrayList<String> city_history;
    private EditText sh;
    private TextView tv_noresult;
    private Button button_ol;
    private LocationClient mLocationClient;
    private MyLocationListener mMyLocationListener;
    private String currentCity;
    private int locateProcess = 1;
    private boolean isNeedFresh;
    private DatabaseHelper helper;
    private CToast cToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitycitylayout);

        TeaskBar.onSystemoutcolor(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        //初始化数据
        initData();
        //初始化控件
        initView();
        helper = new DatabaseHelper(this);
        sh.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.toString() == null || "".equals(s.toString())) {
                    letterListView.setVisibility(View.VISIBLE);
                    personList.setVisibility(View.VISIBLE);
                    resultList.setVisibility(View.GONE);
                    tv_noresult.setVisibility(View.GONE);
                } else {
                    city_result.clear();
                    letterListView.setVisibility(View.GONE);
                    personList.setVisibility(View.GONE);
                    getResultCityList(s.toString());
                    if (city_result.size() <= 0) {
                        tv_noresult.setVisibility(View.VISIBLE);
                        resultList.setVisibility(View.GONE);
                    } else {
                        tv_noresult.setVisibility(View.GONE);
                        resultList.setVisibility(View.VISIBLE);
                        resultListAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        letterListView = (MyLetterListView) findViewById(R.id.MyLetterListView);
        letterListView
                .setOnTouchingLetterChangedListener(new LetterListViewListener());
        alphaIndexer = new HashMap<String, Integer>();
        handler = new Handler();
        overlayThread = new OverlayThread();
        isNeedFresh = true;
        personList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (position >= 4) {
                    SharedPreferences.Editor editor = getSharedPreferences("data", Context.MODE_PRIVATE).edit();
                    editor.putString("address_ol",allCity_lists.get(position).getCityname()+"市");
                    editor.putString("zone_ol","");
                    editor.putString("zone_name","");
                    editor.commit();
                    Intent intent = new Intent();
                    intent.putExtra("city", allCity_lists.get(position).getCityname()+"市");
                    intent.setClass(getApplication(), MyActivity.class);
                    setResult(1, intent);
                    finish();
                }
            }
        });
        locateProcess = 1;
        personList.setAdapter(adapter);
        personList.setOnScrollListener(this);
        resultListAdapter = new ResultListAdapter(this, city_result);
        resultList.setAdapter(resultListAdapter);
        resultList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                SharedPreferences.Editor editor = getSharedPreferences("data", Context.MODE_PRIVATE).edit();
                editor.putString("address_ol",city_result.get(position).getCityname()+"市");
                editor.putString("zone_ol","");
                editor.putString("zone_name","");
                editor.commit();
                Intent intent = new Intent();
                intent.putExtra("city", city_result.get(position).getCityname()+"市");
                intent.setClass(getApplication(), MyActivity.class);
                setResult(1, intent);
                finish();
            }
        });
        initOverlay();
        cityInit();
        hotCityInit();
        hisCityInit();
        setAdapter(allCity_lists, city_hot, city_history);

        mLocationClient = new LocationClient(this.getApplicationContext());
        mMyLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(mMyLocationListener);
        InitLocation();
        mLocationClient.start();
    }
    public void initData(){
        personList = (ListView) findViewById(R.id.list_view);
        allCity_lists = new ArrayList<City>();
        city_hot = new ArrayList<City>();
        city_result = new ArrayList<City>();
        city_history = new ArrayList<String>();
        city_history.add("西安");
        city_history.add("汉中");
        city_history.add("天水");
    }
    public void initView(){
        button_ol = (Button)findViewById(R.id.button_citythis);
        resultList = (ListView) findViewById(R.id.search_result);
        sh = (EditText) findViewById(R.id.sh);
        tv_noresult = (TextView) findViewById(R.id.tv_noresult);

        button_ol.setOnClickListener(this);
    }
    public void InsertCity(String name) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from recentcity where name = '"
                + name + "'", null);
        if (cursor.getCount() > 0) { //
            db.delete("recentcity", "name = ?", new String[] { name });
        }
        db.execSQL("insert into recentcity(name, date) values('" + name + "', "
                + System.currentTimeMillis() + ")");
        db.close();
    }
    private void InitLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setCoorType("bd09ll");
        option.setScanSpan(10000);
        option.setAddrType("all");
//        option.setPoiExtraInfo(true);
        option.setProdName("ͨ��GPS��λ�ҵ�ǰ��λ��");
        option.disableCache(true);
//        option.setPoiNumber(3);
        option.setPriority(LocationClientOption.GpsFirst);
        mLocationClient.setLocOption(option);
    }

    private void cityInit() {
        City city = new City("定位", "0");
        allCity_lists.add(city);
        city = new City("最近", "1");
        allCity_lists.add(city);
        city = new City("热门", "2");
        allCity_lists.add(city);
        city = new City("全部", "3");
        allCity_lists.add(city);
        city_lists = getCityList();
        allCity_lists.addAll(city_lists);
    }

    /**
     * ���ų���
     */
    public void hotCityInit() {
        City city = new City("西安", "2");
        city_hot.add(city);
        city = new City("北京", "2");
        city_hot.add(city);
        city = new City("上海", "2");
        city_hot.add(city);
        city = new City("酒泉", "2");
        city_hot.add(city);
        city = new City("银川", "2");
        city_hot.add(city);
        city = new City("天水", "2");
        city_hot.add(city);
        city = new City("深圳", "2");
        city_hot.add(city);
        city = new City("汉中", "2");
        city_hot.add(city);
        city = new City("兰州", "2");
        city_hot.add(city);
        city = new City("石家庄", "2");
        city_hot.add(city);
        city = new City("苏州", "2");
        city_hot.add(city);
    }

    private void hisCityInit() {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "select * from recentcity order by date desc limit 0, 3", null);
        while (cursor.moveToNext()) {
            city_history.add(cursor.getString(1));
        }
        cursor.close();
        db.close();
    }

    @SuppressWarnings("unchecked")
    private ArrayList<City> getCityList() {
        DBHelper dbHelper = new DBHelper(this);
        ArrayList<City> list = new ArrayList<City>();
        try {
            dbHelper.createDataBase();
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Cursor cursor = db.rawQuery("select * from city", null);
            City city;
            while (cursor.moveToNext()) {
                city = new City(cursor.getString(1), cursor.getString(2));
                list.add(city);
            }
            cursor.close();
            db.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Collections.sort(list, comparator);
        return list;
    }

    @SuppressWarnings("unchecked")
    private void getResultCityList(String keyword) {
        DBHelper dbHelper = new DBHelper(this);
        try {
            dbHelper.createDataBase();
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Cursor cursor = db.rawQuery(
                    "select * from city where name like \"%" + keyword
                            + "%\" or pinyin like \"%" + keyword + "%\"", null);
            City city;
            Log.e("info", "length = " + cursor.getCount());
            while (cursor.moveToNext()) {
                city = new City(cursor.getString(1), cursor.getString(2));
                city_result.add(city);
            }
            cursor.close();
            db.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Collections.sort(city_result, comparator);
    }

    /**
     * a-z����
     */
    @SuppressWarnings("rawtypes")
    Comparator comparator = new Comparator<City>() {
        @Override
        public int compare(City lhs, City rhs) {
            String a = lhs.getCitypinyin().substring(0, 1);
            String b = rhs.getCitypinyin().substring(0, 1);
            int flag = a.compareTo(b);
            if (flag == 0) {
                return a.compareTo(b);
            } else {
                return flag;
            }
        }
    };

    private void setAdapter(List<City> list, List<City> hotList,
                            List<String> hisCity) {
        adapter = new ListAdapter(this, list, hotList, hisCity);
        personList.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_citythis:
                handler.removeCallbacks(overlayThread);
                Intent intent = new Intent();
                intent.putExtra("city", "");
                intent.setClass(this, MyActivity.class);
                setResult(1,intent);
                finish();
                break;
        }
    }
    /**
     * 设置返回控制
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            Intent intent = new Intent();
            intent.putExtra("city", "");
            intent.setClass(this, MyActivity.class);
            setResult(1,intent);
            finish();
            return true;
        }else {
            return  super.onKeyDown(keyCode, event);
        }

    }
    /**
     * ����
     */
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation arg0) {
            Log.e("info", "city = " + arg0.getCity());
            if (!isNeedFresh) {
                return;
            }
            isNeedFresh = false;
            if (arg0.getCity() == null) {
                locateProcess = 3; // ��λʧ��
                personList.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return;
            }
            currentCity = arg0.getCity().substring(0,
                    arg0.getCity().length() - 1);
            locateProcess = 2; // ��λ�ɹ�
            personList.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }


        public void onReceivePoi(BDLocation arg0) {

        }
    }

    private class ResultListAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private ArrayList<City> results = new ArrayList<City>();

        public ResultListAdapter(Context context, ArrayList<City> results) {
            inflater = LayoutInflater.from(context);
            this.results = results;
        }

        @Override
        public int getCount() {
            return results.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.list_item, null);
                viewHolder = new ViewHolder();
                viewHolder.name = (TextView) convertView
                        .findViewById(R.id.name);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.name.setText(results.get(position).getCityname());
            return convertView;
        }

        class ViewHolder {
            TextView name;
        }
    }

    public class ListAdapter extends BaseAdapter {
        private Context context;
        private LayoutInflater inflater;
        private List<City> list;
        private List<City> hotList;
        private List<String> hisCity;
        final int VIEW_TYPE = 5;

        public ListAdapter(Context context, List<City> list,
                           List<City> hotList, List<String> hisCity) {
            this.inflater = LayoutInflater.from(context);
            this.list = list;
            this.context = context;
            this.hotList = hotList;
            this.hisCity = hisCity;
            alphaIndexer = new HashMap<String, Integer>();
            sections = new String[list.size()];
            for (int i = 0; i < list.size(); i++) {
                String currentStr = getAlpha(list.get(i).getCitypinyin());
                // ��һ������ƴ������ĸ�����������Ϊ" "
                String previewStr = (i - 1) >= 0 ? getAlpha(list.get(i - 1)
                        .getCitypinyin()) : " ";
                if (!previewStr.equals(currentStr)) {
                    String name = getAlpha(list.get(i).getCitypinyin());
                    alphaIndexer.put(name, i);
                    sections[i] = name;
                }
            }
        }

        @Override
        public int getViewTypeCount() {
            return VIEW_TYPE;
        }

        @Override
        public int getItemViewType(int position) {
            return position < 4 ? position : 4;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        ViewHolder holder;

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final TextView city;
            int viewType = getItemViewType(position);
            if (viewType == 0) {
                convertView = inflater.inflate(R.layout.frist_list_item, null);
                TextView locateHint = (TextView) convertView
                        .findViewById(R.id.locateHint);
                city = (TextView) convertView.findViewById(R.id.lng_city);
                city.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (locateProcess == 2) {
                            SharedPreferences.Editor editor = getSharedPreferences("data", Context.MODE_PRIVATE).edit();
                            editor.putString("address_ol",city.getText().toString()+"市");
                            editor.putString("zone_ol","");
                            editor.putString("zone_name","");
                            editor.commit();
                            Intent intent = new Intent();
                            intent.putExtra("city", city.getText().toString() + "市");
                            intent.setClass(getApplication(), MyActivity.class);
                            setResult(1, intent);
                            finish();
                        } else if (locateProcess == 3) {
                            locateProcess = 1;
                            personList.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            mLocationClient.stop();
                            isNeedFresh = true;
                            InitLocation();
                            currentCity = "";
                            mLocationClient.start();
                        }
                    }
                });
                ProgressBar pbLocate = (ProgressBar) convertView
                        .findViewById(R.id.pbLocate);
                if (locateProcess == 1) { // ���ڶ�λ
                    locateHint.setText("当前定位城市");
                    city.setVisibility(View.GONE);
                    pbLocate.setVisibility(View.VISIBLE);
                } else if (locateProcess == 2) { // ��λ�ɹ�
                    locateHint.setText("当前定位城市");
                    city.setVisibility(View.VISIBLE);
                    city.setText(currentCity);
                    mLocationClient.stop();
                    pbLocate.setVisibility(View.GONE);
                } else if (locateProcess == 3) {
                    locateHint.setText("当前定位城市");
                    city.setVisibility(View.VISIBLE);
                    city.setText("定位");
                    pbLocate.setVisibility(View.GONE);
                    cToast = CToast.makeText(getApplication(), "无网络链接~", 600);
                    cToast.show();
                }
            } else if (viewType == 1) { // ������ʳ���
                convertView = inflater.inflate(R.layout.recent_city, null);
                GridView rencentCity = (GridView) convertView
                        .findViewById(R.id.recent_city);
                rencentCity
                        .setAdapter(new HitCityAdapter(context, city_history));
                rencentCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        SharedPreferences.Editor editor = getSharedPreferences("data", Context.MODE_PRIVATE).edit();
                        editor.putString("address_ol",city_history.get(position)+"市");
                        editor.putString("zone_ol","");
                        editor.putString("zone_name","");
                        editor.commit();
                        Intent intent = new Intent();
                        intent.putExtra("city", city_history.get(position)+"市");
                        intent.setClass(getApplication(), MyActivity.class);
                        setResult(1, intent);
                        finish();
                    }
                });
                TextView recentHint = (TextView) convertView
                        .findViewById(R.id.recentHint);
                recentHint.setText("最近访问城市");
            } else if (viewType == 2) {
                convertView = inflater.inflate(R.layout.recent_city, null);
                GridView hotCity = (GridView) convertView
                        .findViewById(R.id.recent_city);
                hotCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        SharedPreferences.Editor editor = getSharedPreferences("data", Context.MODE_PRIVATE).edit();
                        editor.putString("address_ol",city_hot.get(position).getCityname()+"市");
                        editor.putString("zone_ol","");
                        editor.putString("zone_name","");
                        editor.commit();
                        Intent intent = new Intent();
                        intent.putExtra("city", city_hot.get(position).getCityname()+"市");
                        intent.setClass(getApplication(), MyActivity.class);
                        setResult(1,intent);
                        finish();
                    }
                });
                hotCity.setAdapter(new HotCityAdapter(context, this.hotList));
                TextView hotHint = (TextView) convertView
                        .findViewById(R.id.recentHint);
                hotHint.setText("热门城市");
            } else if (viewType == 3) {
                convertView = inflater.inflate(R.layout.total_item, null);
            } else {
                if (convertView == null) {
                    convertView = inflater.inflate(R.layout.list_item, null);
                    holder = new ViewHolder();
                    holder.alpha = (TextView) convertView
                            .findViewById(R.id.alpha);
                    holder.name = (TextView) convertView
                            .findViewById(R.id.name);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }
                if (position >= 1) {
                    holder.name.setText(list.get(position).getCityname());
                    String currentStr = getAlpha(list.get(position).getCitypinyin());
                    String previewStr = (position - 1) >= 0 ? getAlpha(list
                            .get(position - 1).getCitypinyin()) : " ";
                    if (!previewStr.equals(currentStr)) {
                        holder.alpha.setVisibility(View.VISIBLE);
                        holder.alpha.setText(currentStr);
                    } else {
                        holder.alpha.setVisibility(View.GONE);
                    }
                }
            }
            return convertView;
        }

        private class ViewHolder {
            TextView alpha; // ����ĸ����
            TextView name; // ��������
        }
    }

    @Override
    protected void onStop() {
        mLocationClient.stop();
        super.onStop();
    }

    class HotCityAdapter extends BaseAdapter {
        private Context context;
        private LayoutInflater inflater;
        private List<City> hotCitys;

        public HotCityAdapter(Context context, List<City> hotCitys) {
            this.context = context;
            inflater = LayoutInflater.from(this.context);
            this.hotCitys = hotCitys;
        }

        @Override
        public int getCount() {
            return hotCitys.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = inflater.inflate(R.layout.item_city, null);
            TextView city = (TextView) convertView.findViewById(R.id.city);
            city.setText(hotCitys.get(position).getCityname());
            return convertView;
        }
    }

    class HitCityAdapter extends BaseAdapter {
        private Context context;
        private LayoutInflater inflater;
        private List<String> hotCitys;

        public HitCityAdapter(Context context, List<String> hotCitys) {
            this.context = context;
            inflater = LayoutInflater.from(this.context);
            this.hotCitys = hotCitys;
        }

        @Override
        public int getCount() {
            return hotCitys.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = inflater.inflate(R.layout.item_city, null);
            TextView city = (TextView) convertView.findViewById(R.id.city);
            city.setText(hotCitys.get(position));
            return convertView;
        }
    }

    private boolean mReady;

    private void initOverlay() {
        mReady = true;
        LayoutInflater inflater = LayoutInflater.from(this);
        overlay = (TextView) inflater.inflate(R.layout.overlay, null);
        overlay.setVisibility(View.INVISIBLE);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                PixelFormat.TRANSLUCENT);
        WindowManager windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        windowManager.addView(overlay, lp);
    }

    private boolean isScroll = false;

    public class LetterListViewListener implements
            MyLetterListView.OnTouchingLetterChangedListener {

        @Override
        public void onTouchingLetterChanged(final String s) {
            isScroll = false;
            if (alphaIndexer.get(s) != null) {
                int position = alphaIndexer.get(s);
                personList.setSelection(position);
                overlay.setText(s);
                overlay.setVisibility(View.VISIBLE);
                handler.removeCallbacks(overlayThread);
                handler.postDelayed(overlayThread, 1000);
            }
        }
    }
    private class OverlayThread implements Runnable {
        @Override
        public void run() {
            overlay.setVisibility(View.GONE);
        }
    }
    private String getAlpha(String str) {
        if (str == null) {
            return "#";
        }
        if (str.trim().length() == 0) {
            return "#";
        }
        char c = str.trim().substring(0, 1).charAt(0);
        // ������ʽ���ж�����ĸ�Ƿ���Ӣ����ĸ
        Pattern pattern = Pattern.compile("^[A-Za-z]+$");
        if (pattern.matcher(c + "").matches()) {
            return (c + "").toUpperCase();
        } else if (str.equals("0")) {
            return "定位";
        } else if (str.equals("1")) {
            return "最近";
        } else if (str.equals("2")) {
            return "热门";
        } else if (str.equals("3")) {
            return "全部";
        } else {
            return "#";
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == SCROLL_STATE_TOUCH_SCROLL
                || scrollState == SCROLL_STATE_FLING) {
            isScroll = true;
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        if (!isScroll) {
            return;
        }

        if (mReady) {
            String text;
            String name = allCity_lists.get(firstVisibleItem).getCityname();
            String pinyin = allCity_lists.get(firstVisibleItem).getCityname();
            if (firstVisibleItem < 4) {
                text = name;
            } else {
                text = PingYinUtil.converterToFirstSpell(pinyin)
                        .substring(0, 1).toUpperCase();
            }
            overlay.setText(text);
            overlay.setVisibility(View.VISIBLE);
            handler.removeCallbacks(overlayThread);
            handler.postDelayed(overlayThread, 1000);
        }
    }
}
