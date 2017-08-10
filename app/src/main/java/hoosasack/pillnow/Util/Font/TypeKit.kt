package hoosasack.pillnow.Util.Font

import android.app.Application
import com.tsengvn.typekit.Typekit

/**
 * Created by parktaejun on 2017. 8. 9..
 */
open class TypeKit : Application() {

    override fun onCreate() {
        super.onCreate()

        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "NanumBarunGothic.ttf"))
                .addBold(Typekit.createFromAsset(this, "NanumBarunGothicBold.ttf"))
                .addCustom1(Typekit.createFromAsset(this, "NanumBarunGothicLight.ttf"))
    }
}