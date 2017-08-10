package hoosasack.pillnow

import android.os.Bundle
import android.support.v7.app.ActionBar
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import hoosasack.pillnow.Util.Font.FontActivity
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : FontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.getActionBar().setCustomView(R.id.custom_actionbar_base)
        setContentView(R.layout.activity_register)

        val actionBar = supportActionBar
        actionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        actionBar.setCustomView(R.layout.custom_actionbar_base)

        var customActionBarView : View
        customActionBarView = actionBar.customView

    }
}
