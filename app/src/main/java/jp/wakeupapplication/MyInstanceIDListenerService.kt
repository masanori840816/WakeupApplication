package jp.wakeupapplication

import android.content.Intent
import com.google.android.gms.iid.InstanceIDListenerService

/**
 * Created by masanori on 2016/04/16.
 */
class MyInstanceIDListenerService: InstanceIDListenerService(){
    override fun onTokenRefresh() {
        var intent = Intent(this, RegistrationIntentService::class.java)
        startService(intent);
    }
}