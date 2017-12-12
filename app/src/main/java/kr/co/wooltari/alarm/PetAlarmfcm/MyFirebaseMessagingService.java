package kr.co.wooltari.alarm.PetAlarmfcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import kr.co.wooltari.R;
import kr.co.wooltari.constant.Const;
import kr.co.wooltari.user.SignInActivity;

/**
 * Created by Kyung on 2017-12-11.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    // 내 앱이 화면에 현재 떠있으면 noti가 전송되었을 때 이 함수가 호출
    // remoteMessage에 데이터가 담겨서 넘어온다.
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        if (remoteMessage.getNotification() != null) {
            sendNotification(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody()
                    ,remoteMessage.getNotification().getSound(), remoteMessage.getNotification().getTag());
        }
    }

    // 노티피케이션 생성
    private void sendNotification(String title, String content, String sound, String tag) {
        Intent intent = new Intent(this, SignInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, Const.PET_ALARM_NOTIFICATION, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri uri = null;
        switch (sound){
            case "dog":
                uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.dog);
                break;
            default:
                uri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                break;
        }
        int tagId = Integer.parseInt(tag);

        String channelId =  "DEFAULT CHANNEL";
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setColor(ContextCompat.getColor(this,R.color.gold))
                        .setSmallIcon(R.drawable.pet_profile)
                        .setContentTitle(title)
                        .setContentText(content)
                        .setAutoCancel(true)
                        .setSound(uri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(tagId, notificationBuilder.build());
    }
}
