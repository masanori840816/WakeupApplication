package jp.wakeupapplication

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Bundle
import android.support.v7.app.NotificationCompat
import android.util.Log
import com.google.android.gms.gcm.GcmListenerService

/**
 * Created by masanori on 2016/04/16.
 * this class for receiving GCM and build notification.
 */
class MessageListenerService : GcmListenerService(){
   override fun onMessageReceived(from: String, data: Bundle?) {
        var message = data?.getString("message");
        Log.d("WUA", "From: " + from);
        Log.d("WUA", "Message: " + message);

        sendNotification(message)
    }
    private fun sendNotification(message: String?) {
        // 受け取ったメッセージからNotificationを作成.
        var intent = Intent(this, MainActivity::class.java)
        // どのActivityを開いていてもMainActivity上にNotificationが表示されるようにする.
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        var pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        // Notification用の音のUri取得.
        var defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        var notificationBuilder = NotificationCompat.Builder(this)
                .setContentTitle("GCM Message")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)

        var notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(0, notificationBuilder.build());
    }
}