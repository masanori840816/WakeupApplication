package jp.wakeupapplication

import android.content.*
import android.preference.PreferenceManager
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import android.widget.ProgressBar

import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability

class MainActivity : AppCompatActivity() {

    private var registrationBroadcastReceiver: BroadcastReceiver? = null
    //private ProgressBar mRegistrationProgressBar;
    //private TextView mInformationTextView;
    private var isReceiverRegistered: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // mRegistrationProgressBar = (ProgressBar) findViewById(R.id.registrationProgressBar);
        registrationBroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                //        mRegistrationProgressBar.setVisibility(ProgressBar.GONE);
                val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
                val sentToken = sharedPreferences.getBoolean("sentTokenToServer", false)
                if (sentToken) {
                    //     mInformationTextView.setText(getString(R.string.gcm_send_message));
                } else {
                    //    mInformationTextView.setText(getString(R.string.token_error_message));
                }
            }
        }
        //  mInformationTextView = (TextView) findViewById(R.id.informationTextView);

        // Registering BroadcastReceiver
        registerReceiver()

        if (checkPlayServices()) {
            // IntentServiceを開始して、Tokenを取得する.
            val intent = Intent(this, RegistrationIntentService::class.java)
            startService(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        registerReceiver()
    }

    override fun onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(registrationBroadcastReceiver)
        isReceiverRegistered = false
        super.onPause()
    }

    private fun registerReceiver() {
        if (!isReceiverRegistered) {
            LocalBroadcastManager.getInstance(this).registerReceiver(registrationBroadcastReceiver,
                    IntentFilter("registrationComplete"))
            isReceiverRegistered = true
        }
    }
    /**
     * GooglePlayServices APKが使用可能かをチェック
     * */
    private fun checkPlayServices(): Boolean {
        val apiAvailability = GoogleApiAvailability.getInstance()
        val resultCode = apiAvailability.isGooglePlayServicesAvailable(this)
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                // ユーザーの操作によってエラーが発生した場合はDialogでエラーコード表示.
                apiAvailability.getErrorDialog(this, resultCode, 9000).show()
            } else {
                // その他GooglePlayServices APKが使用不可能ならDialog表示.
                val alert = AlertDialog.Builder(this)
                alert.setTitle("Not Supported")
                alert.setMessage("This device is not supported.")

                alert.setPositiveButton(getString(android.R.string.ok), null)
                alert.show()

            }
            return false
        }
        return true
    }
}
