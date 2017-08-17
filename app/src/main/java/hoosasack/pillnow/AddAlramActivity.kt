package hoosasack.pillnow

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import hoosasack.pillnow.Util.TimePicker.DialogHandler
import kotlinx.android.synthetic.main.actionbar_add_alram.*
import kotlinx.android.synthetic.main.activity_add_alram.*
import kotlinx.android.synthetic.main.layout_home.*

class AddAlramActivity : AppCompatActivity() {

    lateinit var time : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_alram)


        val timePickerFragment: DialogHandler

        btn_back.setOnClickListener{
            finish()
        }

        btn_edit_alram_time.setOnClickListener{
            showDialog(View(applicationContext))
        }

        btn_add.setOnClickListener{
            Toast.makeText(this, "ADD", 0)
        }

        btn_edit_alram_time.setOnClickListener{
            Toast.makeText(this, "EDIT", 0)
        }
    }

    private fun showDialog(view: View){
        var dialogHandler : DialogHandler = DialogHandler()
        dialogHandler.show(supportFragmentManager, "Time Picker")
    }

    public fun setTimes(mTime : String){
        time = mTime
    }

    private fun setTimeText(mTime : String){
        text_alram_time.text = mTime
    }
}
