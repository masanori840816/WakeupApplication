package jp.wakeupapplication

import android.app.IntentService
import android.content.Intent
import android.preference.PreferenceManager
import android.support.v4.content.LocalBroadcastManager
import android.util.Log
import com.google.android.gms.gcm.GoogleCloudMessaging
import com.google.android.gms.iid.InstanceID

/**
 * Created by masanori on 2016/04/16.
 */
class RegistrationIntentService: IntentService("Registration Service") {
    override fun onHandleIntent(intent: Intent){
        var sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        try {

            var instanceID = InstanceID.getInstance(this);
            var token: String = instanceID.getToken(getString(R.string.google_app_id), GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            // [END get_token]
            Log.i("RegistrationIntent", "GCM Registration Token: " + token);

            // You should store a boolean that indicates whether the generated token has been
            // sent to your server. If the boolean is false, send the token to your server,
            // otherwise your server should have already received the token.
            sharedPreferences.edit().putBoolean("sentTokenToServer", true).apply();
            // [END register_for_gcm]
        } catch (e:Exception) {
            Log.d("RegistrationIntent", "Failed to complete token refresh", e);
            // If an exception happens while fetching the new token or updating our registration data
            // on a third-party server, this ensures that we'll attempt the update at a later time.
            sharedPreferences.edit().putBoolean("sentTokenToServer", false).apply();
        }
        // Notify UI that registration has completed, so the progress indicator can be hidden.
        var registrationComplete = Intent("registrationComplete");
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }
}