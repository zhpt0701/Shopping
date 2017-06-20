package com.example.cloudAndPurchasing.adapter.adaptershopping;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.LocalBroadcastManager;
import android.text.*;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.customcontrol.listviewupdown.PullableListView;
import com.example.cloudAndPurchasing.kind.Goods;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2016/4/7 0007.
 */
public class AdapterIndent extends BaseAdapter{
    private Context context;
    private ArrayList<Goods> arrayList1;
    public Shoppingonclick addonclick;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;
    public static String num_ol = null,pid_ol = null;
    private EachedittextOnclicklistener eachedittextOnclicklistener;
    private HashMap<Integer,Boolean> map;
    private int c_ol = 0,num = 0;
    public HashMap<Integer, Boolean> getMap() {
        return map;
    }

    public void setMap(HashMap<Integer, Boolean> map) {
        this.map = map;
    }
    private LocalBroadcastManager localBroadcastManager;
    public AdapterIndent(LocalBroadcastManager localBroadcastManager1,Context activity, ArrayList<Goods> arrayList,Shoppingonclick addonclick01,ImageLoader imageLoader1,DisplayImageOptions options1) {
        context = activity;
        arrayList1 = arrayList;
        addonclick = addonclick01;
        imageLoader = imageLoader1;
        options = options1;
        localBroadcastManager = localBroadcastManager1;
    }

    @Override
    public int getCount() {
        if (arrayList1 != null){
            return arrayList1.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (arrayList1 != null){
            return arrayList1.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {

        return position;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Goods goods = arrayList1.get(position);
        final ViewHandler viewHandler;
        if (convertView == null){
            viewHandler = new ViewHandler();
            convertView = LayoutInflater.from(context).inflate(R.layout.adapteridentitem,null);
            viewHandler.textView = (TextView)convertView.findViewById(R.id.textview_shoppingname_ol);
            viewHandler.textView_ol = (TextView)convertView.findViewById(R.id.textview_number);
            viewHandler.editText = (TextView)convertView.findViewById(R.id.editext_shoppingnumber_ol);
            viewHandler.button = (Button)convertView.findViewById(R.id.button_all);
            viewHandler.imageView_ol = (ImageView)convertView.findViewById(R.id.imagebutton_xiangou_car);
            viewHandler.imageView_ool = (ImageView)convertView.findViewById(R.id.imagebutton_shiyuan_ol_car);
            viewHandler.imageView = (ImageView)convertView.findViewById(R.id.imageview_shoppingcat);
            viewHandler.imageButton = (Button)convertView.findViewById(R.id.imagebutton_jian);
            viewHandler.imageButton_ol = (Button)convertView.findViewById(R.id.imagebutton_add);
            viewHandler.imageButton_delete = (ImageButton)convertView.findViewById(R.id.imagebutton_delete);
            viewHandler.textView_surpelpe = (TextView)convertView.findViewById(R.id.textview_surpeplenumber);
            viewHandler.checkBox = (CheckBox)convertView.findViewById(R.id.checkbox);
            convertView.setTag(viewHandler);
        }else {
            viewHandler = (ViewHandler)convertView.getTag();
        }

        viewHandler.imageView_ol = (ImageView)convertView.findViewById(R.id.imagebutton_xiangou_car);
        viewHandler.textview_limitcount = (TextView)convertView.findViewById(R.id.textview_limitcount);
        viewHandler.textView_surpelpe = (TextView)convertView.findViewById(R.id.textview_surpeplenumber);
        viewHandler.textView_ol = (TextView)convertView.findViewById(R.id.textview_number);
        viewHandler.textView = (TextView)convertView.findViewById(R.id.textview_shoppingname_ol);
        Button button_all = (Button)convertView.findViewById(R.id.button_all);
        Button button_jian = (Button)convertView.findViewById(R.id.imagebutton_jian);
        Button button_add = (Button)convertView.findViewById(R.id.imagebutton_add);
        final TextView editText = (TextView)convertView.findViewById(R.id.editext_shoppingnumber_ol);
        viewHandler.textView.setText("(第"+goods.getGoodspnumber()+"期)"+goods.getGoodsname());
        viewHandler.editText = editText;
        viewHandler.imageButton = button_jian;
        viewHandler.imageButton_ol = button_add;
        viewHandler.button = button_all;
        if (!goods.getGoodsxian().equals("null")){
            if (Integer.parseInt(goods.getGoodsxian())!=0){
                viewHandler.imageView_ol.setVisibility(View.VISIBLE);
                viewHandler.textview_limitcount.setVisibility(View.VISIBLE);
                SpannableString stayle_ol = new SpannableString("限购:"+goods.getGoodsxian()+"人次");
                stayle_ol.setSpan(new ForegroundColorSpan (context.getResources().getColor(R.color.tiltle)), 3, 3+goods.getGoodsxian().length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //设置指定位置文字的颜色
                viewHandler.textview_limitcount.setText(stayle_ol);
            }else {
                viewHandler.textview_limitcount.setVisibility(View.GONE);
                viewHandler.imageView_ol.setVisibility(View.GONE);
            }
        }else {
            viewHandler.textview_limitcount.setVisibility(View.GONE);
            viewHandler.imageView_ol.setVisibility(View.GONE);
        }
        CheckBox checkBox1 = (CheckBox)convertView.findViewById(R.id.checkbox);
        viewHandler.checkBox = checkBox1;
        viewHandler.checkBox.setChecked(goods.getXuanzhong());
        viewHandler.imageButton.setOnClickListener(addonclick);
        viewHandler.imageButton_ol.setOnClickListener(addonclick);
        viewHandler.imageButton.setTag(position);
        viewHandler.imageButton_ol.setTag(position);
        viewHandler.imageButton_delete.setOnClickListener(addonclick);
        viewHandler.button.setOnClickListener(addonclick);
        viewHandler.imageButton_delete.setTag(position);
        viewHandler.button.setTag(position);
        viewHandler.checkBox.setOnClickListener(addonclick);
        viewHandler.checkBox.setTag(position);
        viewHandler.editText.setOnClickListener(addonclick);
        viewHandler.editText.setTag(position);
        SpannableString style_OL = new SpannableString("总需:"+
                goods.getGoodsallpople()+"人次");
        SpannableString style_l = new SpannableString("剩余:"+goods.getGoods_surplus()+"人次");
        style_OL.setSpan(new ForegroundColorSpan (Color.RED), 3, 3+goods.getGoodsallpople().length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //设置指定位置文字的颜色
        style_l.setSpan(new ForegroundColorSpan(Color.RED), 3, 3 + goods.getGoods_surplus().length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        viewHandler.textView_ol.setText(style_OL);
        viewHandler.textView_surpelpe.setText(style_l);
        viewHandler.editText.setText(goods.getCount());
//        InputMethodManager inputMethodManager = (InputMethodManager)AdapterIndent.ViewHandler.editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//        inputMethodManager.toggleSoftInput(0,InputMethodManager.SHOW_FORCED);

//        for (int c = 0;c<arrayList1.size();c++){
//            c_ol+=Integer.parseInt(arrayList1.get(c).getCount());
//        }
//        viewHandler.editText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (viewHandler.editText.getText().toString()!=null){
//                    if (!viewHandler.editText.getText().toString().equals("")){
//                        num_ol = viewHandler.editText.getText().toString();
//                        pid_ol = goods.getID();
//                        num=c_ol-Integer.parseInt(arrayList1.get(position).getCount())+Integer.parseInt(viewHandler.editText.getText().toString());
//                        FragmentShoppingCar.textView.setText(String.valueOf(num));
//                        arrayList1.get(position).setCount(viewHandler.editText.getText().toString());
//                        Intent intent = new Intent("com.example.cloudAndPurchasing.zhol");
//                        localBroadcastManager.sendBroadcast(intent);
//                    }
//                }
//
//            }
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
        imageLoader.displayImage(goods.getImagepath(), viewHandler.imageView, options);
        return convertView;
    }
    public static class ViewHandler{
      public static TextView textView,textView_ol,textView_surpelpe,textview_limitcount;
      public static TextView editText;
      public static Button button,imageButton,imageButton_ol;
      public static ImageButton imageButton_delete;
        ImageView imageView,imageView_ol,imageView_ool;
       public static CheckBox checkBox;
    }
    public static abstract class Shoppingonclick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            addonclick((Integer)v.getTag(),v);
        }
        protected abstract void addonclick(Integer tag, View v);
    }
    public static abstract class EachedittextOnclicklistener implements TextWatcher{


    }
    /**
     * 单个item刷新
     * @param itemIndex
     */
    public void updateView_fg(int itemIndex,View view2,int c){
            if (view2 != null){
                ViewHandler viewHandler1 = (ViewHandler)view2.getTag();
                viewHandler1.editText = (TextView)view2.findViewById(R.id.editext_shoppingnumber_ol);
                viewHandler1.editText.setText(c+"");
                arrayList1.get(itemIndex).setCount(String.valueOf(c));
            }else {
                return;
            }
    }

    /**
     * cheackbox
     * @param listView
     * @param tag
     * @param b
     */
    public void updateview_th(ListView listView, Integer tag, boolean b) {
        int vb = listView.getFirstVisiblePosition();
        if (tag - vb >= 0){
            View view2 = listView.getChildAt(tag-vb);
            ViewHandler viewHandler1 = (ViewHandler)view2.getTag();
            viewHandler1.checkBox = (CheckBox)view2.findViewById(R.id.checkbox);
            viewHandler1.checkBox.setChecked(b);
            arrayList1.get(tag).setXuanzhong(b);
        }
    }
}
