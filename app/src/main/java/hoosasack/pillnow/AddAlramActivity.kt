package hoosasack.pillnow

import android.os.Bundle
import android.widget.Toast
import hoosasack.pillnow.Util.Font.FontActivity
import kotlinx.android.synthetic.main.actionbar_add_alram.*
import kotlinx.android.synthetic.main.activity_add_alram.*
import java.util.*
import android.app.TimePickerDialog


class AddAlramActivity : FontActivity() {

    lateinit var time: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_alram)

        btn_back.setOnClickListener {
            finish()
        }

        btn_edit_alram_time.setOnClickListener {
            timePick()
        }

        btn_add.setOnClickListener {
            Toast.makeText(this, "ADD", 0)
        }

        btn_edit_alram_time.setOnClickListener {
            Toast.makeText(this, "EDIT", 0)
        }
    }

    private fun timePick() {
        var currentTime: Calendar = Calendar.getInstance()
        val hour = currentTime.get(Calendar.HOUR_OF_DAY)
        val minute = currentTime.get(Calendar.MINUTE)
        val mTimePicker: TimePickerDialog
        mTimePicker = TimePickerDialog(this@AddAlramActivity, TimePickerDialog.OnTimeSetListener { timePicker, selectedHour, selectedMinute -> time = (selectedHour.toString() + ":" + selectedMinute) }, hour, minute, true)
        mTimePicker.actionBar.hide()
        mTimePicker.show()
        text_alram_time.text = time
    }
}