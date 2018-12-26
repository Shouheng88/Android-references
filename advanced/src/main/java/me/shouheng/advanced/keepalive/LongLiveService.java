package me.shouheng.advanced.keepalive;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import java.util.concurrent.TimeUnit;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import io.reactivex.Observable;
import me.shouheng.advanced.MainActivity;
import me.shouheng.advanced.R;
import me.shouheng.commons.tools.LogUtils;

/**
 * 通过设置为前台服务的形式进行保活
 * 另外还有在 onStartCommand() 中返回 START_STICKY
 *
 * Created on 2018/12/26.
 */
public class LongLiveService extends Service {

    @Override
    public void onCreate() {
        /* 启动前台服务保活 */
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationManager notificationManager = (NotificationManager)
                    getSystemService(Context.NOTIFICATION_SERVICE);

            CharSequence name = "FOREGROUND";
            String description = "";
            NotificationChannel channel;
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            channel = new NotificationChannel("ChannelId", name, importance);
            channel.setDescription(description);
            notificationManager.createNotificationChannel(channel);
        }

        Notification notification = new NotificationCompat.Builder(getApplicationContext(), "ChannelId")
                .setContentTitle("Content Title 2")
                .setContentText("Content Text 2")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .build();
        Intent intent = new Intent(this, MainActivity.class);
        notification.contentIntent = PendingIntent.getActivity(this, 0, intent, 0);
        startForeground(2, notification);

        /* 每隔 1 秒的时间输出一个日志 */
        Observable.interval(1, TimeUnit.SECONDS).subscribe(aLong -> LogUtils.d("LongLive " + aLong)).isDisposed();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        /* 返回 START_STICKY，可以做到在“正在运行的服务”中停止的时候自启 */
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        // do nothing
    }

    @Override
    public void onDestroy() {
        Intent intent = new Intent(this, LongLiveService.class);
        startService(intent);
    }
}
