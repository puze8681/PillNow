package hoosasack.pillnow.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import hoosasack.pillnow.Data.HomeDetailDetailContentData
import hoosasack.pillnow.Data.InformPillData
import hoosasack.pillnow.R
import kotlinx.android.synthetic.main.item_home_detail_detail_content.view.*

/**
 * Created by parktaejun on 2017. 8. 12..
 */
class HomeDetailDetailContentAdapter(private val context : Context, items: List<HomeDetailDetailContentData>) : BaseAdapter() {
    private var items = listOf<HomeDetailDetailContentData>()

    init {
        this.items = items
    }

    override fun getCount(): Int {
        return this.items.size
    }

    override fun getItem(i: Int): Any {
        return i
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    @SuppressLint("InflateParams", "ViewHolder")
    override fun getView(i: Int, convertView: View?, viewGroup: ViewGroup): View {
        var view: View? = convertView as? View
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        view = inflater.inflate(R.layout.item_home_detail_detail_content, null)
        view.content.text = items.get(i).content
        return view
    }
}