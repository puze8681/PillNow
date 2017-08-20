package hoosasack.pillnow.Adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import hoosasack.pillnow.Data.InformPillData
import hoosasack.pillnow.R
import kotlinx.android.synthetic.main.item_inform_content_double.view.*
/**
 * Created by parktaejun on 2017. 8. 12..
 */
class InformPillAdapter(private val context : Context, items: List<InformPillData>) : BaseAdapter() {
    private var items = listOf<InformPillData>()

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
        view = inflater.inflate(R.layout.item_inform_content_double, null)
        view.image.setImageDrawable(ContextCompat.getDrawable(context, items.get(i).image))
        view.name.text = items.get(i).name
        view.content.text = items.get(i).content
        return view
    }
}