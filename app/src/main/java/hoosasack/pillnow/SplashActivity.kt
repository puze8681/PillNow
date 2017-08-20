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
import android.app.ProgressDialog
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import app.akexorcist.bluetotohspp.library.BluetoothSPP
import hoosasack.pillnow.Util.BlueTooth.BluetoothService
import hoosasack.pillnow.Util.Server.Data.Login
import hoosasack.pillnow.Util.Server.NetWork.RetrofitService
import hoosasack.pillnow.Util.Server.Schema.UserSchema
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class SplashActivity : FontActivity() {

    lateinit var retrofitService: RetrofitService
    lateinit var progressDialog: ProgressDialog
    lateinit var anim: Animation
    lateinit var intentRegister: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //전체화면 만들기
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash)

        //퍼미션
        permisionCheck()
    }

    override fun onAttachedToWindow(): Unit {
        super.onAttachedToWindow()
        val window: Window = window
        window.setFormat(PixelFormat.RGBA_8888)
    }

    fun permisionCheck() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_ADMIN) != PackageManager.PERMISSION_GRANTED) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    Log.d("puze", "PERMISSION")
                    requestPermissions(arrayOf<String>(android.Manifest.permission.INTERNET, android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.CAMERA, android.Manifest.permission.BLUETOOTH, android.Manifest.permission.BLUETOOTH_ADMIN), 200)
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

    fun initApp(): Unit {
        retrofitSetting()
        startService()
        startAnimations()
        intentRegister = Intent(this, RegisterActivity::class.java)
        btn_register.setOnClickListener {
            view ->
            startActivity(intentRegister)
            finish()
        }

        btn_login.setOnClickListener {
            progressDialogSetting()

            var call: Call<Login> = retrofitService.login(id.toString().trim(), password.toString().trim())
            call.enqueue(object : Callback<Login> {
                override fun onResponse(call: Call<Login>?, response: Response<Login>?) {
                    if (response?.code() === 200) {
                        progressDialog.dismiss()
                        val user = response?.body()
                        var id = user?.id
                        var password = user?.password
                        var token = user?.token.toString()

                        if (user != null) {
                            val loginIntent = Intent(this@SplashActivity, MainActivity::class.java)
                            loginIntent.putExtra(id, "id")
                            loginIntent.putExtra(password, "password")
                            loginIntent.putExtra(token, "token")
                            startActivity(loginIntent)
                            finish()
                            Toast.makeText(this@SplashActivity, "로그인 성공 . . .", Toast.LENGTH_SHORT).show()
                        }
                    } else if (response?.code() === 404) {
                        progressDialog.dismiss()
                        Toast.makeText(this@SplashActivity, "아이디 혹은 비밀번호가 옳지 않습니다 ... ", Toast.LENGTH_SHORT).show()
                    } else {
                        progressDialog.dismiss()
                        Toast.makeText(this@SplashActivity, "UNKNOWN ERR ... ", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Login>?, t: Throwable?) {
                    progressDialog.dismiss();
                    Toast.makeText(this@SplashActivity, "요청 불가 ... ", Toast.LENGTH_SHORT).show();
                }
            })
        }
    }

    fun retrofitSetting() {
        var retrofit: Retrofit = Retrofit.Builder()
                .baseUrl("https://soylatte.kr:3000")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        retrofitService = retrofit.create(RetrofitService::class.java)
    }

    fun progressDialogSetting(){
        progressDialog = ProgressDialog(this@SplashActivity)
        progressDialog.setMessage("로그인 하는 중입니다")
        progressDialog.show()
    }

    fun startService() {
        var servie: Intent = Intent(this, BluetoothService::class.java)
        startService(servie)
    }

    fun startAnimations(): Unit {

        val animlayout: LinearLayout = findViewById(R.id.login_layout) as LinearLayout
        val icon: ImageView = findViewById(R.id.icon) as ImageView

        anim = AnimationUtils.loadAnimation(this, R.anim.alpha)
        anim.reset()
        animlayout.clearAnimation()
        icon.clearAnimation()
        animlayout.startAnimation(anim)
        icon.startAnimation(anim)

        val thread: Thread = Thread {
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
            } catch (e: InterruptedException) {
            } finally {
//                this.finish()
            }
        }
        thread.start()
    }
}
