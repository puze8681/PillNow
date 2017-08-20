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
import hoosasack.pillnow.Adapter.*
import hoosasack.pillnow.AddAlramActivity
import hoosasack.pillnow.Data.HomeAlramData
import hoosasack.pillnow.Data.HomeDetailAlramData
import hoosasack.pillnow.Data.HomeDetailDetailContentData
import hoosasack.pillnow.R
import hoosasack.pillnow.Util.Server.Data.MedicineUserList
import hoosasack.pillnow.Util.Server.NetWork.RetrofitService
import kotlinx.android.synthetic.main.actionbar_home_detail.*
import kotlinx.android.synthetic.main.actionbar_home_detail_detail.*
import kotlinx.android.synthetic.main.layout_home.*
import kotlinx.android.synthetic.main.layout_home_detail.*
import kotlinx.android.synthetic.main.layout_home_detail_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeFragment : Fragment() {

    var intent: Intent = Intent()
    var token = intent.getStringExtra("token")

    lateinit var retrofitService: RetrofitService
    lateinit var progressDialog: ProgressDialog

    var items: ArrayList<HomeAlramData> = ArrayList()
    lateinit var adapter: HomeAlramAdapter

    var itemsHomeDAlram: ArrayList<HomeDetailAlramData> = ArrayList()
    lateinit var adapterHomeDAlram: HomeDetailAlramAdapter

    var itemsHomeDdWarnOne: ArrayList<HomeDetailDetailContentData> = ArrayList()
    lateinit var adapterHomeDdWarnOne: HomeDetailDetailContentAdapter

    var itemsHomeDdWarnTwo: ArrayList<HomeDetailDetailContentData> = ArrayList()
    lateinit var adapterHomeDdWarnTwo: HomeDetailDetailContentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_home, container, false)

        retrofitSetting()
        progressDialogSetting()

        var call: Call<List<MedicineUserList>> = retrofitService.medicineUserList(token)
        call.enqueue(object : Callback<List<MedicineUserList>> {
            override fun onResponse(call: Call<List<MedicineUserList>>?, response: Response<List<MedicineUserList>>?) {
                if (response?.code() === 200) {
                    progressDialog.dismiss()
                    val user = response?.body()
                    var len = user?.size!!.toInt()
                    for (i in 0..len) {
                        var jsons: JsonObject = user[i].userList
                        items[i].image = jsons.get("image").toString()
                        items[i].name = jsons.get("name").toString()
                        items[i].content = jsons.get("content").toString()
                        if (i % 2 == 0) {
                            items[i].switch = false
                        } else {
                            items[i].switch = true
                        }
                    }

                    Toast.makeText(this@HomeFragment.context, "성공적으로 불러왔습니다 . . .", Toast.LENGTH_SHORT).show()
                } else if (response?.code() === 404) {
                    progressDialog.dismiss()
                    Toast.makeText(this@HomeFragment.context, "불러오기에 실패하였습니다 ... ", Toast.LENGTH_SHORT).show()
                } else {
                    progressDialog.dismiss()
                    Toast.makeText(this@HomeFragment.context, "UNKNOWN ERR ... ", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<MedicineUserList>>?, t: Throwable?) {
                progressDialog.dismiss();
                Toast.makeText(this@HomeFragment.context, "요청 불가 ... ", Toast.LENGTH_SHORT).show();
            }
        })

        adapting()



        adapter?.itemClick = object : HomeAlramAdapter.ItemClick {
            override fun onItemClick(view: View?, position: Int) {

                layout_fragment_home.visibility = View.GONE
                layout_fragment_home_detail.visibility = View.VISIBLE
                layout_fragment_home_detail_detail.visibility = View.GONE

                title_detail.text = items.get(position).name
                content_detail.text = items.get(position).content
            }

        }

        btn_back_detail?.setOnClickListener {
            layout_fragment_home.visibility = View.VISIBLE
            layout_fragment_home_detail.visibility = View.GONE
            layout_fragment_home_detail_detail.visibility = View.GONE
        }

        main_content_detail?.setOnClickListener {
            layout_fragment_home.visibility = View.GONE
            layout_fragment_home_detail.visibility = View.GONE
            layout_fragment_home_detail_detail.visibility = View.VISIBLE

            title_detail_detail.text = title_detail.text
            title_detail_detail_sub.text = title_detail.text
            content_detail_detail.text = content_detail.text
        }

        btn_back_detail_detail?.setOnClickListener {
            layout_fragment_home.visibility = View.GONE
            layout_fragment_home_detail.visibility = View.VISIBLE
            layout_fragment_home_detail_detail.visibility = View.GONE
        }

        btn_add_alram_detail?.setOnClickListener {
            val intent = Intent(context, AddAlramActivity::class.java)
            intent.putExtra(title_detail.text.toString().trim(), "text")
            startActivity(intent)
        }



        return view
    }

    private fun adapting() {

        adapter = HomeAlramAdapter(context.applicationContext, items)
        recyclerView?.layoutManager = LinearLayoutManager(context.applicationContext, LinearLayoutManager.VERTICAL, false)
        recyclerView?.adapter = adapter

        adapterHomeDAlram = HomeDetailAlramAdapter(context.applicationContext, itemsHomeDAlram)
        recyclerView_detail_alram?.layoutManager = LinearLayoutManager(context.applicationContext, LinearLayoutManager.VERTICAL, false)
        recyclerView_detail_alram?.adapter = adapterHomeDAlram

        adapterHomeDdWarnOne = HomeDetailDetailContentAdapter(context.applicationContext, itemsHomeDdWarnOne)
        list_warn_one_detail_detail?.adapter = adapterHomeDdWarnOne

        adapterHomeDdWarnTwo = HomeDetailDetailContentAdapter(context.applicationContext, itemsHomeDdWarnTwo)
        list_warn_two_detail_detail?.adapter = adapterHomeDdWarnTwo
    }

    fun retrofitSetting() {
        var retrofit: Retrofit = Retrofit.Builder()
                .baseUrl("https://soylatte.kr:3000")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        retrofitService = retrofit.create(RetrofitService::class.java)
    }

    fun progressDialogSetting() {
        progressDialog = ProgressDialog(this@HomeFragment.context)
        progressDialog.setMessage("로그인 하는 중입니다")
        progressDialog.show()
    }


    companion object {
        fun newInstance(): HomeFragment {
            val fragment: HomeFragment = HomeFragment()
            return fragment
        }
    }
}