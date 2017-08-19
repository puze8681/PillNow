package hoosasack.pillnow

import android.content.Intent
import android.graphics.PixelFormat
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import hoosasack.pillnow.Util.Font.FontActivity
import kotlinx.android.synthetic.main.activity_splash.*
import java.lang.Thread.sleep
import com.google.android.gms.internal.d
import android.Manifest.permission.MODIFY_PHONE_STATE
import android.Manifest.permission.RECORD_AUDIO
import android.Manifest.permission.INTERNET
import android.Manifest.permission.READ_CONTACTS
import android.Manifest.permission.READ_PHONE_STATE
import android.os.Build
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat
import android.Manifest.permission.CALL_PHONE
import android.util.Log


class SplashActivity : FontActivity() {

    lateinit var anim : Animation
    lateinit var intentRegister : Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //전체화면 만들기
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash)

        //퍼미션
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED||
                    ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    Log.d("puze", "PERMISSION")
                    requestPermissions(arrayOf<String>(android.Manifest.permission.INTERNET, android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.CAMERA), 200)
                    Log.d("puze", "initApp0")
                    initApp()
                }
            } else {
                Log.d("puze", "initApp1")
                initApp()
            }
            initApp()
        } else {
            Log.d("puze", "initApp2")
            initApp()
        }
    }

    fun initApp() : Unit{
        startAnimations()
        intentRegister = Intent(this, RegisterActivity::class.java)
        btn_register.setOnClickListener{
            view->startActivity(intentRegister)
            finish()
        }
    }
    override fun onAttachedToWindow(): Unit{
        super.onAttachedToWindow()
        val window : Window = window
        window.setFormat(PixelFormat.RGBA_8888)
    }

    fun startAnimations(): Unit{

        val animlayout : LinearLayout = findViewById(R.id.login_layout) as LinearLayout
        val icon : ImageView = findViewById(R.id.icon) as ImageView

        anim = AnimationUtils.loadAnimation(this, R.anim.alpha)
        anim.reset()
        animlayout.clearAnimation()
        icon.clearAnimation()
        animlayout.startAnimation(anim)
        icon.startAnimation(anim)

        val thread : Thread = Thread {
            try {
                var term = 0
                while (term < 3500) {
                    sleep(100)
                    term += 100
                }
//                val loginIntent = Intent(this, LoginActivity::class.java)
//                loginIntent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
//                startActivity(loginIntent)
//                finish()
            } catch (e : InterruptedException) {
            } finally {
//                this.finish()
            }
        }
        thread.start()
    }
}
