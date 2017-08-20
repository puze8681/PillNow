package hoosasack.pillnow

import android.app.ProgressDialog
import android.os.Bundle
import android.widget.Toast
import hoosasack.pillnow.Util.Font.FontActivity
import kotlinx.android.synthetic.main.actionbar_add_alram.*
import kotlinx.android.synthetic.main.activity_add_alram.*
import java.util.*
import android.app.TimePickerDialog
import android.content.Intent
import hoosasack.pillnow.Util.Server.Data.Alarm
import hoosasack.pillnow.Util.Server.Data.Login
import hoosasack.pillnow.Util.Server.NetWork.RetrofitService
import kotlinx.android.synthetic.main.activity_splash.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class AddAlramActivity : FontActivity() {

    lateinit var retrofitService: RetrofitService
    lateinit var progressDialog: ProgressDialog

    lateinit var time: String
    lateinit var serverTime: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_alram)

        retrofitSetting()
        var intent: Intent = Intent()
        var token = intent.getStringExtra("token")
        var name = intent.getStringExtra("name")

        btn_back.setOnClickListener {
            finish()
        }

        btn_edit_alram_time.setOnClickListener {
            timePick()
        }

        btn_add.setOnClickListener {
            progressDialogSetting()

            var call: Call<Alarm> = retrofitService.alarm(token, name, serverTime)
            call.enqueue(object : Callback<Alarm> {
                override fun onResponse(call: Call<Alarm>?, response: Response<Alarm>?) {
                    if (response?.code() === 200) {
                        progressDialog.dismiss()

                        Toast.makeText(this@AddAlramActivity, "알람이 추가되었습니다 . . .", Toast.LENGTH_SHORT).show()
                    } else if (response?.code() === 409) {
                        progressDialog.dismiss()
                        Toast.makeText(this@AddAlramActivity, response?.message(), Toast.LENGTH_SHORT).show()
                    } else if (response?.code() === 403) {
                        progressDialog.dismiss()
                        Toast.makeText(this@AddAlramActivity, response?.message(), Toast.LENGTH_SHORT).show()
                    } else {
                        progressDialog.dismiss()
                        Toast.makeText(this@AddAlramActivity, "UNKNOWN ERR ... ", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Alarm>?, t: Throwable?) {
                    progressDialog.dismiss();
                    Toast.makeText(this@AddAlramActivity, "요청 불가 ... ", Toast.LENGTH_SHORT).show();
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

    fun progressDialogSetting() {
        progressDialog = ProgressDialog(this@AddAlramActivity)
        progressDialog.setMessage("로그인 하는 중입니다")
        progressDialog.show()
    }

    private fun timePick() {
        var currentTime: Calendar = Calendar.getInstance()
        val hour = currentTime.get(Calendar.HOUR_OF_DAY)
        val minute = currentTime.get(Calendar.MINUTE)
        val mTimePicker: TimePickerDialog
        mTimePicker = TimePickerDialog(this@AddAlramActivity, TimePickerDialog.OnTimeSetListener { timePicker, selectedHour, selectedMinute -> serverTime = (selectedHour.toString());time = (selectedHour.toString() + ":" + selectedMinute) }, hour, minute, true)
        mTimePicker.actionBar.hide()
        mTimePicker.show()
        text_alram_time.text = time
    }
}