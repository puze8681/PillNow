package hoosasack.pillnow.Util.TimePicker

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.v4.app.DialogFragment
import android.widget.TimePicker
import android.text.format.DateFormat;
import java.util.*

/**
 * Created by parktaejun on 2017. 8. 18..
 */
class TimePickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener{

    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    @NonNull
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        var calender = Calendar.getInstance()
        var hour = calender.get(Calendar.HOUR_OF_DAY)
        var minute = calender.get(Calendar.MINUTE)
        var timePickerDialog = TimePickerDialog(context, this, hour, minute, DateFormat.is24HourFormat(context))
        return timePickerDialog

    }

}