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
class DialogHandler : DialogFragment(){

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        var calender : Calendar = Calendar.getInstance()
        var hour : Int = calender.get(Calendar.HOUR_OF_DAY)
        var min : Int = calender.get(Calendar.MINUTE)
        var dialog : TimePickerDialog
        var timeSettings : TimeSettings = TimeSettings(activity)
        dialog = TimePickerDialog(activity, timeSettings, hour, min, DateFormat.is24HourFormat(activity))
        return dialog
    }
}