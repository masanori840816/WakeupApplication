package jp.wakeupapplication

import android.content.Intent
import com.google.android.gms.iid.InstanceIDListenerService

/**
 * Created by masanori on 2016/04/16.
 */
class MyInstanceIDListenerService: InstanceIDListenerService(){
    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. This call is initiated by the
     * InstanceID provider.
     */
    // [START refresh_token]
    override fun onTokenRefresh() {
        // Fetch updated Instance ID token and notify our app's server of any changes (if applicable).
        var intent = Intent(this, RegistrationIntentService::class.java)
        startService(intent);
    }
    // [END refresh_token]
}