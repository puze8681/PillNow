package hoosasack.pillnow

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import hoosasack.pillnow.Util.TimePicker.TimePickerFragment
import kotlinx.android.synthetic.main.actionbar_add_alram.*
import kotlinx.android.synthetic.main.activity_add_alram.*

class AddAlramActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_alram)

        val timePickerFragment: TimePickerFragment

        btn_back.setOnClickListener{
            finish()
        }

        btn_add.setOnClickListener{
            Toast.makeText(this, "ADD", 0)
        }

        btn_edit_alram_time.setOnClickListener{
            Toast.makeText(this, "EDIT", 0)
        }
    }
}
