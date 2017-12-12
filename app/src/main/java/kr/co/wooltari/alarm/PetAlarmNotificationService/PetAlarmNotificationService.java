package kr.co.wooltari.alarm.PetAlarmNotificationService;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

/**
 * Created by Kyung on 2017-12-12.
 */

public class PetAlarmNotificationService extends NotificationListenerService {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        Log.e("NotificationListener", "onNotificationPosted() - " + sbn.toString());
        Log.e("NotificationListener", "PackageName:" + sbn.getPackageName());
        Log.e("NotificationListener", "PostTime:" + sbn.getPostTime());

        Notification notification = sbn.getNotification();
        Bundle extras = notification.extras;
        Log.e("notification", "title:" + extras.getString(Notification.EXTRA_TITLE));
        Log.e("notification", "text:" + extras.getString(Notification.EXTRA_TEXT));
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        Log.e("NotificationListener", "onNotificationRemoved() - " + sbn.toString());
    }
}
