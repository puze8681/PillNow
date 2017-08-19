package hoosasack.pillnow.Util.Alram

import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService


/**
 * Created by parktaejun on 2017. 8. 19..
 */
class MyFirebaseInstanceIDService : FirebaseInstanceIdService() {
    private final var TAG : String = "MyFirebaseIIDService"

    override fun onTokenRefresh() {
        var refreshedToken : String? = FirebaseInstanceId.getInstance().getToken()
        Log.d(TAG, "Refreshed token: " + refreshedToken)

        sendRegistrationToServer(refreshedToken);
    }

    private fun sendRegistrationToServer(token : String?){

    }
}