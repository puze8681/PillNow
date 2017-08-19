package hoosasack.pillnow

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import hoosasack.pillnow.Adapter.AlramAdapter
import hoosasack.pillnow.Data.AlramData
import kotlinx.android.synthetic.main.activity_alram.*

class AlramActivity : AppCompatActivity() {

    var context : Context = applicationContext
    var items: ArrayList<AlramData> = ArrayList()
    lateinit var adapter: AlramAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alram)

        adapter = AlramAdapter(context, items)

        recyclerview_alram?.adapter = adapter
    }
}
