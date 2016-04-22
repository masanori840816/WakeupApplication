package jp.wakeupapplication

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.WindowManager
import android.widget.CompoundButton
import android.widget.ToggleButton

import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var toggleScreenOn = findViewById(R.id.toggle_screenon) as ToggleButton
        toggleScreenOn.setOnCheckedChangeListener{
            button:CompoundButton, isChecked: Boolean ->
                if(isChecked){
                    // togglebuttonがOnの場合はScreenをOnにし続ける.
                    window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
                }
                else{
                    window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
                }
        }

        if (checkPlayServices()) {
            // IntentServiceを開始して、Tokenを取得する.
            val intent = Intent(this, RegistrationIntentService::class.java)
            startService(intent)
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
