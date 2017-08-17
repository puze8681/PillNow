package hoosasack.pillnow.Util.TimePicker

import android.app.TimePickerDialog
import android.content.Context
import android.widget.TimePicker
import android.widget.Toast
import hoosasack.pillnow.AddAlramActivity

/**
 * Created by parktaejun on 2017. 8. 18..
 */
class TimeSettings : TimePickerDialog.OnTimeSetListener {
    var context: Context

    constructor(context: Context) {
        this.context = context
    }

    override fun onTimeSet(view: TimePicker?, hour: Int, min: Int) {
        var AM_PM: String
        when (hour < 12) {
            true -> AM_PM = "AM"
            false -> AM_PM = "PM"
        }
        var time : String = AM_PM+" "+hour+" "+min
        Toast.makeText(context, "Selected Time is "+time, 0).show()
    }
}