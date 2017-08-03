package hoosasack.pillnow

import android.animation.PropertyValuesHolder
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.getActionBar().setCustomView(R.id.custom_actionbar_base)
        setContentView(R.layout.activity_register)

        val spinner : Spinner = findViewById(R.id.spinner) as Spinner
        var str : Array<String> = resources.getStringArray(R.array.age)
        val spinnerAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,str)
        spinner.adapter(spinnerAdapter)

    }
}
