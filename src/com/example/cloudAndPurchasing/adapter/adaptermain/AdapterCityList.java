package com.example.cloudAndPurchasing.adapter.adaptermain;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.kind.City;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/4/18 0018.
 */
public class AdapterCityList extends BaseAdapter implements SectionIndexer{
    private Context mContext;
    private ArrayList<City> mAllCityList;
    public AdapterCityList(Context activityCity, ArrayList<City> arrayList) {
        mContext = activityCity;
        mAllCityList = arrayList;
    }
    public void updateListView(ArrayList<City> list) {
        mAllCityList = list;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return mAllCityList.size();
    }

    @Override
    public Object getItem(int position) {
        return mAllCityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        int viewType = getItemViewType(position);
//        if (viewType == 0) {//view类型为0，也就是：当前定位城市的布局
//            convertView = View.inflate(mContext, R.layout.item_location_city,
//                    null);
//            tvLocate=(TextView) convertView.findViewById(R.id.tv_locate);
//            tvCurrentLocateCity=(TextView) convertView.findViewById(R.id.tv_current_locate_city);
//            pbLocate = (ProgressBar) convertView.findViewById(R.id.pb_loacte);
//
//            if(!isNeedRefresh){
//                tvLocate.setText(当前定位城市);
//                tvCurrentLocateCity.setVisibility(View.VISIBLE);
//                tvCurrentLocateCity.setText(currentCity);
//                pbLocate.setVisibility(View.GONE);
//            }else{
//                myLocationClient.start();
//            }
//
//            tvCurrentLocateCity.setOnClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    pbLocate.setVisibility(View.VISIBLE);
//                    tvLocate.setText(正在定位);
//                    tvCurrentLocateCity.setVisibility(View.GONE);
//                    myLocationClient.start();
//                }
//            });
//
//        } else if (viewType == 1) {//最近访问城市
//            convertView = View.inflate(mContext,R.layout.item_recent_visit_city, null);
//            TextView tvRecentVisitCity=(TextView) convertView.findViewById(R.id.tv_recent_visit_city);
//            tvRecentVisitCity.setText(最近访问城市);
//            MyGridView gvRecentVisitCity = (MyGridView) convertView.findViewById(R.id.gv_recent_visit_city);
//            gvRecentVisitCity.setAdapter(new RecentVisitCityAdapter(mContext,mRecentCityList));
//
//        } else if (viewType == 2) {//热门城市
//            convertView = View.inflate(mContext,R.layout.item_recent_visit_city, null);
//            TextView tvRecentVisitCity=(TextView) convertView.findViewById(R.id.tv_recent_visit_city);
//            tvRecentVisitCity.setText(热门城市);
//            MyGridView gvRecentVisitCity = (MyGridView) convertView.findViewById(R.id.gv_recent_visit_city);
//            gvRecentVisitCity.setAdapter(new HotCityAdapter(mContext,mHotCityList));
//        } else if (viewType == 3) {//全部城市，仅展示“全部城市这四个字”
//            convertView = View.inflate(mContext,R.layout.item_all_city_textview, null);
//        } else {//数据库中所有的城市的名字展示
//            if (convertView == null){
//                viewHolder = new ViewHolder();
//                convertView = LayoutInflater.from(mContext).inflate(R.layout.adaptercityitem,null);
//                viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.title);
//                viewHolder.tvLetter = (TextView) convertView.findViewById(R.id.catalog);
//                convertView.setTag(viewHolder);
//            }else {
//                viewHolder = (ViewHolder)convertView.getTag();
//            }
//            int section = getSectionForPosition(position);
//            City city = mAllCityList.get(position);
//            if (position == getPositionForSection(section)) {
//                viewHolder.tvLetter.setVisibility(View.VISIBLE);
//                viewHolder.tvLetter.setText(city.getCitypinyin());
//            } else {
//                viewHolder.tvLetter.setVisibility(View.GONE);
//            }
//
//            viewHolder.tvTitle.setText(city.getCityname());
//
//        }
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adaptercityitem,null);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.title);
            viewHolder.tvLetter = (TextView) convertView.findViewById(R.id.catalog);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        int section = getSectionForPosition(position);
        City city = mAllCityList.get(position);
        if (position == getPositionForSection(section)) {
            viewHolder.tvLetter.setVisibility(View.VISIBLE);
            viewHolder.tvLetter.setText(city.getCitypinyin());
        } else {
            viewHolder.tvLetter.setVisibility(View.GONE);
        }

        viewHolder.tvTitle.setText(city.getCityname());
        return convertView;
    }
    class ViewHolder {
        TextView tvTitle;
        TextView tvLetter;
        LinearLayout llMain;
    }
    public int getSectionForPosition(int position) {
        return mAllCityList.get(position).getCitypinyin().charAt(0);
    }

    /**
     * ���ݷ��������ĸ��Char asciiֵ��ȡ���һ�γ��ָ�����ĸ��λ��
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = mAllCityList.get(i).getCitypinyin();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }

        return -1;
    }

    /**
     *
     *
     * @param str
     * @return
     */
//    private String getAlpha(String str) {
//        String sortStr = str.trim().substring(0, 1).toUpperCase();
//        // ������ʽ���ж�����ĸ�Ƿ���Ӣ����ĸ
//        if (sortStr.matches("[A-Z]")) {
//            return sortStr;
//        } else {
//            return "#";
//        }
//    }
    // 获得汉语拼音首字母
    private String getAlpha(String str) {
        String sortStr = str.trim().substring(0, 1).toUpperCase();
        if (str == null) {
            return "#";
        }
        if (str.trim().length() == 0) {
            return "#";
        }
        char c = str.trim().substring(0, 1).charAt(0);
        // 正则表达式，判断首字母是否是英文字母
        Pattern pattern = Pattern.compile("^[A-Za-z]+$");
        if (pattern.matcher(c+"+").matches()) {
            return (c+"+").toUpperCase();
        } else if (str.equals(0)) {
            return "定位";
        } else if (str.equals(1)) {
            return "最近";
        } else if (str.equals(2)) {
            return "热门";
        } else if (str.equals(3)) {
            return "全部";
        } else {
            return "#";
        }
    }
    @Override
    public Object[] getSections() {
        return null;
    }
}
