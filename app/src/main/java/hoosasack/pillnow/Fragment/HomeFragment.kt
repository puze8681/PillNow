package hoosasack.pillnow.Fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import hoosasack.pillnow.Adapter.*
import hoosasack.pillnow.AddAlramActivity
import hoosasack.pillnow.Data.HomeAlramData
import hoosasack.pillnow.Data.HomeDetailAlramData
import hoosasack.pillnow.Data.HomeDetailDetailContentData
import hoosasack.pillnow.Data.InformProhibitedAllergyData
import hoosasack.pillnow.R
import kotlinx.android.synthetic.main.actionbar_home_detail.*
import kotlinx.android.synthetic.main.actionbar_home_detail_detail.*
import kotlinx.android.synthetic.main.layout_home.*
import kotlinx.android.synthetic.main.layout_home_detail.*
import kotlinx.android.synthetic.main.layout_home_detail_detail.*

class HomeFragment : Fragment() {

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

        adapting()

        adapter.itemClick = object : HomeAlramAdapter.ItemClick {
            override fun onItemClick(view: View?, position: Int) {

                layout_fragment_home.visibility = View.GONE
                layout_fragment_home_detail.visibility = View.VISIBLE
                layout_fragment_home_detail_detail.visibility = View.GONE

                title_detail.text = items.get(position).name
                content_detail.text = items.get(position).content
            }

        }

        btn_back_detail.setOnClickListener{
            layout_fragment_home.visibility = View.VISIBLE
            layout_fragment_home_detail.visibility = View.GONE
            layout_fragment_home_detail_detail.visibility = View.GONE
        }

        main_content_detail.setOnClickListener{
            layout_fragment_home.visibility = View.GONE
            layout_fragment_home_detail.visibility = View.GONE
            layout_fragment_home_detail_detail.visibility = View.VISIBLE

            title_detail_detail.text = title_detail.text
            title_detail_detail_sub.text = title_detail.text
            content_detail_detail.text = content_detail.text
        }

        btn_back_detail_detail.setOnClickListener{
            layout_fragment_home.visibility = View.GONE
            layout_fragment_home_detail.visibility = View.VISIBLE
            layout_fragment_home_detail_detail.visibility = View.GONE
        }

        btn_add_alram_detail.setOnClickListener{
            val intent = Intent(context, AddAlramActivity::class.java)
            startActivity(intent)
        }

        return view
    }

    private fun adapting(){

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

    companion object {
        fun newInstance(): HomeFragment {
            val fragment: HomeFragment = HomeFragment()
            return fragment
        }
    }
}