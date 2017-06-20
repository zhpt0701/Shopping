package com.example.cloudAndPurchasing.service;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import com.example.cloudAndPurchasing.kind.VersionOL;

/**
 * Created by Administrator on 2016/7/9 0009.
 */
public class VersionCode {
    public static VersionOL versioncontent(Context context){
        VersionOL version = new VersionOL();
        PackageManager manager = context.getPackageManager();
        try { PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            version.setViersionname(info.versionName); //版本名
            version.setViersioncode(info.versionCode);//版本号
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return version;
    }

}
