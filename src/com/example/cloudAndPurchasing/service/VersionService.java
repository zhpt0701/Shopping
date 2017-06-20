package com.example.cloudAndPurchasing.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.RemoteViews;
import com.example.cloudAndPurchasing.MyActivity;
import com.example.cloudAndPurchasing.R;
import com.example.cloudAndPurchasing.http.HttpApi;
import com.example.cloudAndPurchasing.http.Numbers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2016/7/8 0008.
 */
public class VersionService extends Service {
    private Notification updateNotification;
    private NotificationManager updateNotificationMgr;
    private File updateFile;
    private Intent updateIntent;
    private int notificationId = Numbers.ONE;
    private final int DOWNLOAD_COMPLETE = 1, DOWNLOAD_FALL = 2, DOWNLOAD_SUCCESS = 3;
    private PendingIntent updatePendingIntent;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        String sdPath = FileHelper.getSDCardPath();
        if (sdPath != null) {
            updateFile = new File(sdPath + HttpApi.downloading + "艾购梦想.apk");
            // 初始化通知管理器
            updateNotificationMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            updateNotification = new Notification();
            updateNotification.icon = R.drawable.ic_launcher;
            updateIntent = new Intent(this, MyActivity.class);
            updatePendingIntent = PendingIntent.getActivity(this, 0, updateIntent, 0);
            //通知自定义视图
            updateNotification.contentView = new RemoteViews(getPackageName(), R.layout.mynotification_progressbar);
            updateNotification.contentView.setProgressBar(R.id.pb_notifi, 100, 0, false);
            updateNotification.contentIntent = updatePendingIntent;//这个pengdingIntent很重要，必须要设置

            // 发出通知
            updateNotificationMgr.notify(notificationId, updateNotification);

            // 开启线程进行下载
            new Thread(new updateThread()).start();
        }
        super.onStart(intent, startId);
    }

    class updateThread implements Runnable {
        Message msg = handler.obtainMessage();

        @Override
        public void run() {
            try {
                if (!updateFile.exists()) {
                    updateFile.createNewFile();
                }
                long downSize = downloadFile(HttpApi.downloading + "艾购梦想.apk", updateFile);
                if (downSize > 0) {
                    //下载成功！
                    msg.what = DOWNLOAD_SUCCESS;
                    handler.sendMessage(msg);
                }
            } catch (Exception ex) {
                ex.printStackTrace();//下载失败
                msg.what = DOWNLOAD_FALL;
                handler.sendMessage(msg);
            }
        }
    }

    /**
     * 下载
     *
     * @param downloadUrl
     * @param saveFile
     * @return
     * @throws Exception
     */
    public long downloadFile(String downloadUrl, File saveFile) throws Exception {
        int downloadCount = 0;
        int currentSize = 0;
        long totalSize = 0;
        int updateTotalSize = 0;
        HttpURLConnection httpConnection = null;
        InputStream is = null;
        FileOutputStream fos = null;
        try {
            URL url = new URL(downloadUrl);
            httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setRequestProperty("User-Agent", "PacificHttpClient");
            if (currentSize > 0) {
                httpConnection.setRequestProperty("RANGE", "bytes=" + currentSize + "-");
            }
            httpConnection.setConnectTimeout(10000);
            httpConnection.setReadTimeout(20000);
            updateTotalSize = httpConnection.getContentLength();//总大小
            if (httpConnection.getResponseCode() == 404) {
                throw new Exception("conection net 404！");
            }
            is = httpConnection.getInputStream();
            fos = new FileOutputStream(saveFile);
            byte[] buf = new byte[1024];
            int readSize = -1;

            while ((readSize = is.read(buf)) != -1) {
                fos.write(buf, 0, readSize);
                //通知更新进度
                totalSize += readSize;
                int tmp = (int) (totalSize * 100 / updateTotalSize);
                //为了防止频繁的通知导致应用吃紧，百分比增加10才通知一次
                if (downloadCount == 0 || tmp - 10 > downloadCount) {
                    downloadCount += 10;
                    Message msg = handler.obtainMessage();
                    msg.what = DOWNLOAD_COMPLETE;
                    msg.arg1 = downloadCount;
                    handler.sendMessage(msg);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (httpConnection != null) {
                httpConnection.disconnect();
            }
            if (is != null) {
                is.close();
            }
            if (fos != null) {
                fos.close();
            }
        }
        return totalSize;
    }

    /**
     * 刷新进度条
     */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case DOWNLOAD_SUCCESS:
                    //下载完成点击通知进入安装
                    Uri uri = Uri.fromFile(updateFile);
                    Intent installIntent = new Intent(Intent.ACTION_VIEW);
                    installIntent.setDataAndType(uri, "application/vnd.android.package-archive");
                    updatePendingIntent = PendingIntent.getActivity(VersionService.this, 0, installIntent, 0);
                    updateNotification.defaults = Notification.DEFAULT_SOUND;//设置铃声
                    updateNotification.contentIntent = updatePendingIntent;
                    //更新通知视图值
                    updateNotification.contentView.setTextViewText(R.id.tv_downInfo, "下载成功,点击安装。");
                    updateNotification.contentView.setProgressBar(R.id.pb_notifi, 100, 100, false);
                    updateNotificationMgr.notify(notificationId, updateNotification);
                    stopService(updateIntent);//停止service
                    break;
                case DOWNLOAD_COMPLETE://下载中状态
                    System.out.println(msg.arg1);
                    updateNotification.contentView.setProgressBar(R.id.pb_notifi, 100, msg.arg1, false);
                    updateNotification.contentView.setTextViewText(R.id.tv_downInfo, "下载中" + msg.arg1 + "%");
                    updateNotificationMgr.notify(notificationId, updateNotification);
                    break;
                case DOWNLOAD_FALL://失败状态
                    //updateNotification.setLatestEventInfo(UpgradeService.this, "下载失败", "", updatePendingIntent);]
                    updateNotification.contentView.setTextViewText(R.id.tv_downInfo, "下载失败");
                    updateNotificationMgr.notify(notificationId, updateNotification);
                    stopService(updateIntent);//停止service
                    break;
                default:
                    stopService(updateIntent);

            }
        }
    };

}
