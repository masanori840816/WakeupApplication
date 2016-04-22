package jp.wakeupapplication

import android.app.IntentService
import android.content.Intent
import android.util.Log
import com.google.android.gms.gcm.GoogleCloudMessaging
import com.google.android.gms.iid.InstanceID

/**
 * Created by masanori on 2016/04/16.
 * get token to send messages from server.
 */
class RegistrationIntentService: IntentService("Registration Service") {
    override fun onHandleIntent(intent: Intent){
        try {
            var instanceID = InstanceID.getInstance(this);
            var token: String = instanceID.getToken(getString(R.string.google_app_id), GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            // 取得したTokenをServer側に渡し、それを元にMessageを送信する.
            Log.i("WUA", "GCM Registration Token: " + token);
        } catch (e:Exception) {
            Log.d("WUA", "Failed to get token", e);
        }
    }
}