package com.example.cloudAndPurchasing.popupwindows;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.util.Log;
import android.view.*;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.Toast;
import com.example.cloudAndPurchasing.R;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

/**
 * Created by Administrator on 2016/4/7 0007.
 */
public class SharePopupwindows extends PopupWindow implements View.OnClickListener {
    private Button button_weixin,button_phone,
            button_friend,button_kongjian,button_weibo,
            button_liangjie;
    private ImageButton imageButton;
    private View view;
    private Context context1;
    UMImage image = new UMImage(context1, "http://www.umeng.com/images/pic/social/integrated_3.png");
    String url = "http://www.umeng.com";
    public SharePopupwindows(final Context context,View view1){
        context1 = context;

        view = LayoutInflater.from(context).inflate(R.layout.popuplayout,null);
        setHeight(WindowManager.LayoutParams.MATCH_PARENT);
        setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        setContentView(view);
        setFocusable(true);
        setOutsideTouchable(true);
        setBackgroundDrawable(new BitmapDrawable());
        imageButton = (ImageButton)view.findViewById(R.id.imagebutton_cancel);
        button_weixin = (Button)view.findViewById(R.id.button_weixin);
        button_friend = (Button)view.findViewById(R.id.button_friend);
        button_phone = (Button)view.findViewById(R.id.button_phone_qq);
        button_kongjian = (Button)view.findViewById(R.id.button_qqkongjian);
        button_weibo = (Button)view.findViewById(R.id.button_xinlang);
        button_liangjie = (Button)view.findViewById(R.id.button_copy);
        imageButton.setOnClickListener(this);
        button_weibo.setOnClickListener(this);
        button_liangjie.setOnClickListener(this);
        button_weixin.setOnClickListener(this);
        button_phone.setOnClickListener(this);
        button_friend.setOnClickListener(this);
        button_kongjian.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.button_weixin:
                new ShareAction((Activity) context1).setPlatform(SHARE_MEDIA.WEIXIN).setCallback(umShareListener)
                        .withText("hello umeng")
                        .withMedia(image)
                                //.withMedia(new UMEmoji(ShareActivity.this,"http://img.newyx.net/news_img/201306/20/1371714170_1812223777.gif"))
//                        .withText("hello umeng")
                        .withTargetUrl(url)
                        .share();
                dismiss();
                break;
            case R.id.button_friend:
                new ShareAction((Activity) context1).setPlatform(SHARE_MEDIA.WEIXIN_FAVORITE).setCallback(umShareListener)
                        .withMedia(image)
                        .withText("This is my favorite")
                        .withTargetUrl(url)
                        .share();
                dismiss();
                break;
            case R.id.button_phone_qq:
                new ShareAction((Activity) context1).setPlatform(SHARE_MEDIA.QQ).setCallback(umShareListener)
                        .withTitle("this is title")
                        .withText("hello umeng")
                        .withMedia(image)
                                //.withMedia(music)
                                //.withTargetUrl(url)
                                //.withTitle("qqshare")
                        .share();
                dismiss();
                break;
            case R.id.button_qqkongjian:
                new ShareAction((Activity) context1).setPlatform(SHARE_MEDIA.QZONE).setCallback(umShareListener)
                        .withText("空间")
                        .withTitle("分享到空间")
                        .withMedia(image)
                                //.withMedia(video)
                        .share();
                dismiss();
                break;
            case R.id.button_xinlang:
                /** shareaction need setplatform , callbacklistener,and content(text,image).then share it **/
                new ShareAction((Activity) context1).setPlatform(SHARE_MEDIA.SINA).setCallback(umShareListener)
                        .withText("Umeng Share")
                        .withTitle("this is title")
                        .withMedia(image)
//                       .withExtra(new UMImage(ShareActivity.this,R.drawable.ic_launcher))
                                //.withTargetUrl(url)
                        .share();
                dismiss();
                break;
            case R.id.button_copy:
                Toast.makeText(v.getContext(),"修改中！！",Toast.LENGTH_SHORT).show();
                dismiss();
                break;
            case R.id.imagebutton_cancel:
                dismiss();
                break;

        }
    }
    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.d("plat", "platform" + platform);
            if(platform.name().equals("WEIXIN_FAVORITE")){
                Toast.makeText(context1,platform + " 收藏成功啦",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(context1, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(context1,platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(context1,platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void shasrepopupwindows(View v) {
            showAsDropDown(v,0,0,Gravity.CENTER);
        }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void show() {
        showAsDropDown(view,0,0,Gravity.CENTER);
    }

}
