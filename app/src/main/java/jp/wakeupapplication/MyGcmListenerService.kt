package jp.wakeupapplication

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.NotificationCompat
import android.util.Log
import com.google.android.gms.gcm.GcmListenerService

/**
 * Created by masanori on 2016/04/16.
 */
class MyGcmListenerService: GcmListenerService(){
   override fun onMessageReceived(from: String, data: Bundle?) {
        var message = data?.getString("message");
        Log.d("GcmListenerService", "From: " + from);
        Log.d("GcmListenerService", "Message: " + message);

        sendNotification(message)
    }
    private fun sendNotification(message: String?) {
        var intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        var pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent, PendingIntent.FLAG_ONE_SHOT)

        var defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        var notificationBuilder = NotificationCompat.Builder(this)
                .setContentTitle("GCM Message")
                .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        var notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}