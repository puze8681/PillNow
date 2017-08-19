package hoosasack.pillnow.Util.Alram

import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService
import okhttp3.FormBody
import okhttp3.RequestBody
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException


/**
 * Created by parktaejun on 2017. 8. 19..
 */
class MyFirebaseInstanceIDService : FirebaseInstanceIdService() {

    private val TAG : String = "MyFirebaseIIDService"

    override fun onTokenRefresh() {
        var refreshedToken : String? = FirebaseInstanceId.getInstance().getToken()
        Log.d(TAG, "Refreshed token: " + refreshedToken)

        sendRegistrationToServer(refreshedToken);
    }

    private fun sendRegistrationToServer(token : String?){

        val client = OkHttpClient()
        val body = FormBody.Builder()
                .add("Token", token)
                .build()

        val request = Request.Builder()
                .url("url")
                .post(body)
                .build()

        try {
            client.newCall(request).execute()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }
}