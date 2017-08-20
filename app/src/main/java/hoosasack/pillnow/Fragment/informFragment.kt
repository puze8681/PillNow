package hoosasack.pillnow.Fragment

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.gson.JsonObject
import com.squareup.picasso.Picasso
import hoosasack.pillnow.Adapter.HomeAlramAdapter
import hoosasack.pillnow.Adapter.InformPillAdapter
import hoosasack.pillnow.Adapter.InformProhibitedAllergyAdapter
import hoosasack.pillnow.Data.HomeAlramData
import hoosasack.pillnow.Data.InformPillData
import hoosasack.pillnow.Data.InformProhibitedAllergyData
import hoosasack.pillnow.R
import hoosasack.pillnow.Util.Server.Data.Medicine
import hoosasack.pillnow.Util.Server.Data.MedicineUserList
import hoosasack.pillnow.Util.Server.NetWork.RetrofitService
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_inform.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class InformFragment : Fragment() {

    var intent : Intent = Intent()
    var token = intent.getStringExtra("token")

    lateinit var retrofitService: RetrofitService
    lateinit var progressDialog: ProgressDialog

    var itemsChronic: ArrayList<InformPillData> = ArrayList()
    var itemsCurrent: ArrayList<InformPillData> = ArrayList()
    var itemsProhibited: ArrayList<InformProhibitedAllergyData> = ArrayList()
    var itemsAllergy: ArrayList<InformProhibitedAllergyData> = ArrayList()
    lateinit var adapterChronic: InformPillAdapter
    lateinit var adapterCurrent: InformPillAdapter
    lateinit var adapterProhibited: InformProhibitedAllergyAdapter
    lateinit var adapterAllergy: InformProhibitedAllergyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_inform, container, false)

        retrofitSetting()
        progressDialogSetting()

        var call: Call<List<MedicineUserList>> = retrofitService.medicineUserList(token)
        call.enqueue(object : Callback<List<MedicineUserList>> {
            override fun onResponse(call: Call<List<MedicineUserList>>?, response: Response<List<MedicineUserList>>?) {
                if (response?.code() === 200) {
                    progressDialog.dismiss()
                    val user = response?.body()
                    var len = user?.size!!.toInt()
                    for(i in 0..len){
                        var jsons : JsonObject = user[i].userList
                        itemsCurrent[i].image = jsons.get("image").toString()
                        itemsCurrent[i].name = jsons.get("name").toString()
                        itemsCurrent[i].content = jsons.get("content").toString()
                    }

                    Toast.makeText(this@InformFragment.context, "성공적으로 불러왔습니다 . . .", Toast.LENGTH_SHORT).show()
                } else if (response?.code() === 404) {
                    progressDialog.dismiss()
                    Toast.makeText(this@InformFragment.context, "불러오기에 실패하였습니다 ... ", Toast.LENGTH_SHORT).show()
                } else {
                    progressDialog.dismiss()
                    Toast.makeText(this@InformFragment.context, "UNKNOWN ERR ... ", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<MedicineUserList>>?, t: Throwable?) {
                progressDialog.dismiss();
                Toast.makeText(this@InformFragment.context, "요청 불가 ... ", Toast.LENGTH_SHORT).show();
            }
        })

        adapterChronic = InformPillAdapter(context.applicationContext, itemsChronic)
        adapterCurrent = InformPillAdapter(context.applicationContext, itemsCurrent)
        adapterProhibited = InformProhibitedAllergyAdapter(context.applicationContext, itemsProhibited)
        adapterAllergy = InformProhibitedAllergyAdapter(context.applicationContext, itemsAllergy)

        chronic_pill_list?.adapter = adapterChronic
        current_pill_list?.adapter = adapterCurrent
        prohibited_list?.adapter = adapterProhibited
        allergy_list?.adapter = adapterAllergy

        return view
    }

    fun retrofitSetting() {
        var retrofit: Retrofit = Retrofit.Builder()
                .baseUrl("http://soylatte.kr:3000")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        retrofitService = retrofit.create(RetrofitService::class.java)
    }

    fun progressDialogSetting(){
        progressDialog = ProgressDialog(this@InformFragment.context)
        progressDialog.setMessage("로그인 하는 중입니다")
        progressDialog.show()
    }

    companion object {
        fun newInstance(): InformFragment {
            val fragment: InformFragment = InformFragment()
            return fragment
        }
    }
}
