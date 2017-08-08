package hoosasack.pillnow

import android.content.Intent
import android.graphics.PixelFormat
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
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
        setContentView(R.layout.activity_splash)

        //전체화면 만들기
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        startAnimations()

    }

    override fun onAttachedToWindow(): Unit{
        super.onAttachedToWindow()
        val window : Window = window
        window.setFormat(PixelFormat.RGBA_8888)
    }

    fun startAnimations(): Unit{

        val linlayout : LinearLayout = findViewById(R.id.activity_splash) as LinearLayout
        val animlayout : LinearLayout = findViewById(R.id.anim_layout) as LinearLayout

        anim = AnimationUtils.loadAnimation(this, R.anim.alpha)
        anim.reset()
        linlayout.clearAnimation()
        linlayout.startAnimation(anim)

        anim = AnimationUtils.loadAnimation(this, R.anim.translate)
        anim.reset()

        animlayout.clearAnimation()
        animlayout.startAnimation(anim)

        val thread : Thread = Thread {
            try {
                var term = 0
                while (term < 3500) {
                    sleep(100)
                    term += 100
                }
                val loginIntent = Intent(this, LoginActivity::class.java)
                loginIntent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
                startActivity(loginIntent)
                finish()
            } catch (e : InterruptedException) {
            } finally {
                this.finish()
            }
        }
        thread.start()

//        val hd : Handler = Handler()
//        // in Java -> Handler hd = new Handler();
//
//
//        hd.postDelayed({
//            //Intent loginIntent = new Intent(SplashActivity.this, LoginActivity.class);
//            val loginIntent = Intent(this, LoginActivity::class.java)
//            startActivity(loginIntent)
//            finish()
//        }, 3000)

    }
}
