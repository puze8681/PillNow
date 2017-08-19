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
import com.google.firebase.messaging.RemoteMessage
import hoosasack.pillnow.AlramActivity
import hoosasack.pillnow.R
import java.lang.Exception


/**
 * Created by parktaejun on 2017. 8. 20..
 */
class MyFirebaseMessagingService : FirebaseMessagingService() {

    // [START receive_message]
    override fun onMessageReceived(remoteMessage: RemoteMessage?) {

        showNotification(remoteMessage!!.data["message"])
    }

    override fun onSendError(str: String?, error: Exception?) {
        Log.i("TEST", "error services")
    }

    private fun showNotification(message: String?) {

        val i = Intent(this, MainActivity::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT)

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setContentTitle(" Pill Now !!! ")
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_splash_logo)
                .setContentIntent(pendingIntent)
                .setSound(defaultSoundUri)

        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        manager.notify(0, builder.build())
        showAlramActivity(message)
    }

    private fun showAlramActivity(message: String?){
        var intent : Intent = Intent(this, AlramActivity::class.java)
        intent.putExtra(message, "message")
        startActivity(intent)
    }
}