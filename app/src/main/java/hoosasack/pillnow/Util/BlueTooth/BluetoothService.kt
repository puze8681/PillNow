package hoosasack.pillnow.Util.BlueTooth

import android.app.Service
import android.content.Intent
import android.os.IBinder
import app.akexorcist.bluetotohspp.library.BluetoothSPP
import hoosasack.pillnow.MainActivity
import android.widget.Toast
import app.akexorcist.bluetotohspp.library.BluetoothState




/**
 * Created by parktaejun on 2017. 8. 20..
 */
class BluetoothService : Service() {

    lateinit var bt: BluetoothSPP

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        bt = BluetoothSPP(applicationContext)

        if (!bt.isBluetoothAvailable) {
            Toast.makeText(applicationContext, "블루투스를 켜주세요", Toast.LENGTH_SHORT).show()
        }

        bt.setBluetoothConnectionListener(object : BluetoothSPP.BluetoothConnectionListener {
            override fun onDeviceConnected(name: String, address: String) {
                Toast.makeText(applicationContext, "연결되었습니다", Toast.LENGTH_SHORT).show()
            }

            override fun onDeviceDisconnected() {
                Toast.makeText(applicationContext, "연결이끊겼습니다", Toast.LENGTH_SHORT).show()
            }

            override fun onDeviceConnectionFailed() {
                Toast.makeText(applicationContext, "연결에 실패하였습니다", Toast.LENGTH_SHORT).show()
            }
        })

        bt.setAutoConnectionListener(object : BluetoothSPP.AutoConnectionListener {
            override fun onNewConnection(name: String, address: String) {

                //처음 연결될 떄 "C", 알람이 울릴 때 "R" 을 보낼거임
                bt.send("C", false)
            }
            override fun onAutoConnectionStarted() {}
        })

        bt.setOnDataReceivedListener { data, message ->
            val mainIntent = Intent(applicationContext, MainActivity::class.java)
            mainIntent.putExtra("message", message)
            startActivity(mainIntent)

            //처음 연결될 때 "C", 알약통을 열었을 때 "O" 를 받을거임
        }

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(p0: Intent?): IBinder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy()
    }

    fun setup(){
        bt.autoConnect("pillnow")
    }

    fun onStart(){
        if (!bt.isBluetoothEnabled()) {
            bt.enable()
        } else {
            if (!bt.isServiceAvailable()) {
                bt.setupService()
                bt.startService(BluetoothState.DEVICE_OTHER)
                setup()
            }
        }
    }
}