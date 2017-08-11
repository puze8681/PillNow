package hoosasack.pillnow

import android.content.Intent
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
        setContentView(R.layout.activity_register)

        val afterIntent = Intent(this, MainActivity::class.java)
        val beforeIntent = Intent(this, SplashActivity::class.java)

        btn_after.setOnClickListener(){
            view->startActivity(afterIntent)
        }
        btn_before.setOnClickListener(){
            view->startActivity(beforeIntent)
        }
    }
}
