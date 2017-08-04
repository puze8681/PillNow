package hoosasack.pillnow

import android.animation.PropertyValuesHolder
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.getActionBar().setCustomView(R.id.custom_actionbar_base)
        setContentView(R.layout.activity_register)

        var agePosition : Int

        var str = (resources.getStringArray(R.array.age))
        spinner.adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,str)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                agePosition = position
            }
        }
    }
}
