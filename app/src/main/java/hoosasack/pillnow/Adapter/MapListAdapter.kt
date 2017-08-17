package hoosasack.pillnow.Adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.LinearLayout
import hoosasack.pillnow.Data.HomeAlramData
import kotlinx.android.synthetic.main.item_home_alram.*
import kotlinx.android.synthetic.main.item_home_alram.view.*
import android.support.v4.content.ContextCompat
import hoosasack.pillnow.Data.MapListData
import hoosasack.pillnow.R
import kotlinx.android.synthetic.main.item_map_list.view.*


/**
 * Created by parktaejun on 2017. 8. 11..
 */
class MapListAdapter(private val context : Context, private var items : ArrayList<MapListData>) : RecyclerView.Adapter<MapListAdapter.ViewHolder>(){

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder
            = ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_map_list, null), context)

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder!!.bind(items.get(position))
        holder.itemView.setOnClickListener {
            itemClick!!.onItemClick(holder.itemView, position)
        }
    }

    class ViewHolder(itemView: View, private val context : Context) : RecyclerView.ViewHolder(itemView) {
        init {
            var params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            itemView.layoutParams = params
        }
        fun bind(item: MapListData) {
            itemView.title.text = item.title
            itemView.location.text = item.location
            itemView.call.text = item.call
        }
    }
    var itemClick: ItemClick? = null

    interface ItemClick {
        fun onItemClick(view: View?, position: Int)
    }
}
