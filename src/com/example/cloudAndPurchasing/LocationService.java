package com.example.cloudAndPurchasing;

import android.annotation.TargetApi;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
import com.example.cloudAndPurchasing.sqlliste.DBManger;

/**
 * Created by Administrator on 2016/3/24 0024.
 */
public class LocationService extends Service {
    private DBManger dbManger;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @TargetApi(Build.VERSION_CODES.ECLAIR)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        dbManger = new DBManger(this.getApplicationContext());
        dbManger.openSqlite();
        dbManger.downSqlite();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
