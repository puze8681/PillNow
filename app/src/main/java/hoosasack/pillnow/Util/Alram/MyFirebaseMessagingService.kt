package hoosasack.pillnow.Util.Alram

import com.google.firebase.messaging.FirebaseMessagingService
import android.content.Context.NOTIFICATION_SERVICE
import android.app.NotificationManager
import android.media.RingtoneManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import hoosasack.pillnow.MainActivity
import com.google.android.gms.internal.d
import android.support.design.widget.CoordinatorLayout.Behavior.setTag
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.firebase.jobdispatcher.FirebaseJobDispatcher
import com.firebase.jobdispatcher.GooglePlayDriver
import com.firebase.jobdispatcher.Job
import com.google.firebase.messaging.RemoteMessage
import hoosasack.pillnow.R




/**
 * Created by parktaejun on 2017. 8. 20..
 */
class MyFirebaseMessagingService : FirebaseMessagingService() {

    // [START receive_message]
    override fun onMessageReceived(remoteMessage: RemoteMessage?) {

        showNotification(remoteMessage!!.data["message"])
    }

    private fun showNotification(message: String?) {

        val i = Intent(this, MainActivity::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setContentTitle("FCM Test")
                .setContentText(message)
                .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
                .setContentIntent(pendingIntent)

        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        manager.notify(0, builder.build())
    }
}