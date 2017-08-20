package hoosasack.pillnow

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.ActionBar
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import hoosasack.pillnow.Util.Font.FontActivity
import hoosasack.pillnow.Util.Server.Data.Login
import hoosasack.pillnow.Util.Server.Data.SignUp
import hoosasack.pillnow.Util.Server.NetWork.RetrofitService
import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RegisterActivity : FontActivity() {

    lateinit var retrofitService: RetrofitService
    lateinit var progressDialog: ProgressDialog
    var sex: String = "sex"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        retrofitSetting()
        val afterIntent = Intent(this, MainActivity::class.java)
        val beforeIntent = Intent(this, SplashActivity::class.java)

        btn_male.setOnClickListener {
            btn_female.background = getDrawable(R.drawable.btn_female)
            btn_female.setTextColor(R.color.orange)
            btn_male.background = getDrawable(R.drawable.btn_male_checked)
            btn_male.setTextColor(R.color.white)
            sex = "male"
        }
        btn_female.setOnClickListener {
            btn_male.background = getDrawable(R.drawable.btn_male)
            btn_male.setTextColor(R.color.orange)
            btn_female.background = getDrawable(R.drawable.btn_female_checked)
            btn_female.setTextColor(R.color.white)
            sex = "female"
        }
        btn_before.setOnClickListener {
            startActivity(afterIntent)
            finish()
        }
        btn_after.setOnClickListener {
            progressDialogSetting()
            var call: Call<SignUp> = retrofitService.signup(id.toString().trim(), pw.toString().trim(), age.toString().trim(), sex, name.toString().trim())
            call.enqueue(object : Callback<SignUp> {
                override fun onResponse(call: Call<SignUp>?, response: Response<SignUp>?) {
                    if (response?.code() === 200) {
                        progressDialog.dismiss()
                        val user = response?.body()
                        var token = user?.token.toString()

                        if (user != null) {
                            val signupIntent = Intent(this@RegisterActivity, MainActivity::class.java)
                            signupIntent.putExtra(id.toString().trim(), "id")
                            signupIntent.putExtra(pw.toString().trim(), "password")
                            signupIntent.putExtra(token, "token")
                            startActivity(signupIntent)
                            finish()
                            Toast.makeText(this@RegisterActivity, "로그인 성공 . . .", Toast.LENGTH_SHORT).show()
                        }
                    } else if (response?.code() === 401) {
                        progressDialog.dismiss()
                        Toast.makeText(this@RegisterActivity, "사용할 수 없는 아이디 입니다 ... ", Toast.LENGTH_SHORT).show()
                    } else {
                        progressDialog.dismiss()
                        Toast.makeText(this@RegisterActivity, "UNKNOWN ERR ... ", Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<SignUp>?, t: Throwable?) {
                    progressDialog.dismiss();
                    Toast.makeText(this@RegisterActivity, "요청 불가 ... ", Toast.LENGTH_SHORT).show();
                }
            })
        }
    }

    fun retrofitSetting() {
        var retrofit: Retrofit = Retrofit.Builder()
                .baseUrl("http://soylatte.kr:3000")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        retrofitService = retrofit.create(RetrofitService::class.java)
    }

    fun progressDialogSetting() {
        progressDialog = ProgressDialog(this@RegisterActivity)
        progressDialog.setMessage("로그인 하는 중입니다")
        progressDialog.show()
    }
}
