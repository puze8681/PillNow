package hoosasack.pillnow.Fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hoosasack.pillnow.Data.HomeAlramData
import hoosasack.pillnow.Adapter.HomeAlramAdapter
import hoosasack.pillnow.R
import kotlinx.android.synthetic.main.actionbar_home.*
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    var items: ArrayList<HomeAlramData> = ArrayList()
    lateinit var adapter: HomeAlramAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_home, container, false)

        adapter = HomeAlramAdapter(context.applicationContext, items)
        recyclerView?.layoutManager = LinearLayoutManager(context.applicationContext, LinearLayoutManager.VERTICAL, false)
        recyclerView?.adapter = adapter

        adapter.itemClick = object : HomeAlramAdapter.ItemClick {
            override fun onItemClick(view: View?, position: Int) {
                val intent: Intent = Intent(context, HomeDetailFragment::class.java)
                intent.putExtra("position", position)
                intent.putExtra("image", items.get(position).image)
                intent.putExtra("name", items.get(position).name)
                intent.putExtra("content", items.get(position).content)
                intent.putExtra("switch", items.get(position).switch)
                startActivityForResult(intent, MY_SET_CODE)
            }

        }
        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (data == null)
            return
        when (requestCode) {
            MY_ADD_CODE -> {
                val image: Int = data.getIntExtra("image",R.drawable.ic_splash_logo)
                val name: String = data.getStringExtra("content")
                val content: String = data.getStringExtra("content")
                val switch: Boolean = data.getBooleanExtra("switch",false)
                items.add(0, HomeAlramData(image, name, content, switch))
                adapter.notifyItemInserted(0)
            }
            MY_SET_CODE -> {
                val position: Int = data.getIntExtra("position", -1)
                val image: Int = data.getIntExtra("image",R.drawable.ic_splash_logo)
                val name: String = data.getStringExtra("content")
                val content: String = data.getStringExtra("content")
                val switch: Boolean = data.getBooleanExtra("switch",false)
                if (position == -1)
                    return
                items.set(position, HomeAlramData(image, name, content, switch))
                adapter.notifyItemChanged(position)
            }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }


    companion object {

        val MY_ADD_CODE: Int = 1000
        val MY_SET_CODE: Int = 2000

        fun newInstance(): HomeFragment {
            val fragment: HomeFragment = HomeFragment()
            return fragment
        }
    }
}