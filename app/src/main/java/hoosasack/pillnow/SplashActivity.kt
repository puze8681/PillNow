package hoosasack.pillnow

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.PixelFormat
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.annotation.ColorInt
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import java.io.IOException
import java.lang.Thread.sleep

class SplashActivity : AppCompatActivity() {

    lateinit var anim : Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //전체화면 만들기
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash)

        startAnimations()

    }

    override fun onAttachedToWindow(): Unit{
        super.onAttachedToWindow()
        val window : Window = window
        window.setFormat(PixelFormat.RGBA_8888)
    }

    fun startAnimations(): Unit{

        val linlayout : LinearLayout = findViewById(R.id.activity_splash) as LinearLayout
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
