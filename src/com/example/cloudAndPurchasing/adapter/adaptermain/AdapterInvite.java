package com.example.cloudAndPurchasing.adapter.adaptermain;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.activity.activitymain.ActivityInvite;
import com.example.cloudAndPurchasing.adapter.adaptershopping.AdapterIndent;
import com.example.cloudAndPurchasing.http.HttpApi;
import com.example.cloudAndPurchasing.kind.Friend;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/4/13 0013.
 */
public class AdapterInvite extends BaseAdapter{
    private Context context1;
    private ArrayList<Friend> arrayList1;
    private HashMap<Integer,Boolean> isselect;
    private InviteOnCheckedChangeListener inviteOnCheckedChangeListener;
    private ImageLoader imageLoader1;
    private DisplayImageOptions options1;
    public HashMap<Integer, Boolean> getIsselect() {
        return isselect;
    }

    public void setIsselect(HashMap<Integer, Boolean> isselect) {
        this.isselect = isselect;
    }

    public AdapterInvite(ImageLoader imageLoader,DisplayImageOptions options,Context activityInvite, ArrayList<Friend> arrayList,InviteOnCheckedChangeListener inviteOnClickListener1) {
        context1 = activityInvite;
        arrayList1 = arrayList;
        inviteOnCheckedChangeListener = inviteOnClickListener1;
        imageLoader1 = imageLoader;
        options1 = options;
        isselect = new HashMap<Integer, Boolean>();
        initdata();
    }

    private void initdata() {
        for (int c = 0;c<arrayList1.size();c++){
            getIsselect().put(c,false);
        }
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHandler viewHandler;
        if (convertView == null){
            convertView = LayoutInflater.from(context1).inflate(R.layout.adapterinviteitem,null);
            viewHandler = new ViewHandler();
            viewHandler.imageview = (ImageView)convertView.findViewById(R.id.imageview_invite_photos);
            viewHandler.textview = (TextView)convertView.findViewById(R.id.textview_invitenickname);
            viewHandler.textview_ol = (TextView)convertView.findViewById(R.id.textview_invitethisphonenumber);
            viewHandler.textview_ool = (TextView)convertView.findViewById(R.id.textview_invitezhuzhi);
            viewHandler.checkBox = (CheckBox)convertView.findViewById(R.id.button_xuangzhong);
            convertView.setTag(viewHandler);
        }else {
            viewHandler = (ViewHandler)convertView.getTag();
        }
        final CheckBox checkBox_ol = (CheckBox)convertView.findViewById(R.id.button_xuangzhong);
        viewHandler.checkBox = checkBox_ol;
        Friend friend = arrayList1.get(position);
        if (isselect.get(position)){
            viewHandler.checkBox.isChecked();
        }else {
            viewHandler.checkBox.setChecked(false);
        }
        viewHandler.checkBox.setOnCheckedChangeListener(inviteOnCheckedChangeListener);
        viewHandler.checkBox.setTag(position);
        viewHandler.textview.setText(friend.getNickname());
        viewHandler.textview_ol.setText("手机号: " + friend.getPhonenumber());
        viewHandler.textview_ool.setText("ID: "+friend.getID());
        imageLoader1.displayImage(HttpApi.yu+"/usermember/getpicture?mid="+friend.getID(),viewHandler.imageview,options1);
        return convertView;
    }

    public static class ViewHandler{
        TextView textview,textview_ol,textview_ool;
        ImageView imageview;
       public static CheckBox checkBox;
    }
    public static abstract class InviteOnCheckedChangeListener implements CompoundButton.OnCheckedChangeListener{
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            inviteOnCheckedChangeListener((Integer)buttonView.getTag(),isChecked);
        }

        protected abstract void inviteOnCheckedChangeListener(Integer tag, boolean isChecked);

    }

    /**
     * 界面刷新
     * @param listView
     * @param tag
     * @param b
     */
    public void updateviewcontent(ListView listView, Integer tag, boolean b) {
        int first = listView.getFirstVisiblePosition();
        if (tag-first>=0){
            View view = listView.getChildAt(tag-first);
            ViewHandler viewHandler1 = (ViewHandler)view.getTag();
            viewHandler1.checkBox = (CheckBox)view.findViewById(R.id.button_xuangzhong);
            viewHandler1.checkBox.setChecked(b);
            isselect.put(tag,b);
        }
    }
}
