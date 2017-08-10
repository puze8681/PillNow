package hoosasack.pillnow.Util.Font

import android.content.Context
import android.support.v7.app.AppCompatActivity
import com.tsengvn.typekit.TypekitContextWrapper

/**
 * Created by parktaejun on 2017. 8. 9..
 */
open class FontActivity : AppCompatActivity() {
    override fun attachBaseContext(newBase : Context) : Unit {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase))
    }
}