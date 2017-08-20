package hoosasack.pillnow

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import hoosasack.pillnow.Adapter.AlramAdapter
import hoosasack.pillnow.Data.AlramData
import hoosasack.pillnow.Util.Font.FontActivity
import kotlinx.android.synthetic.main.activity_alram.*
import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;
import hoosasack.pillnow.Util.BlueTooth.BluetoothService


class AlramActivity : FontActivity() {

    var context : Context = applicationContext
    var items: ArrayList<AlramData> = ArrayList()
    lateinit var adapter: AlramAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alram)

        adapter = AlramAdapter(context, items)

        recyclerview_alram?.adapter = adapter

        btn_check.setOnClickListener{
            kill()
        }
    }

    fun kill(){
        finish()
    }
}
