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
import android.support.v4.content.ContextCompat
import android.widget.Toast
import hoosasack.pillnow.Data.HomeDetailAlramData
import hoosasack.pillnow.R
import kotlinx.android.synthetic.main.item_home_detail_alram.view.*


/**
 * Created by parktaejun on 2017. 8. 11..
 */
class HomeDetailAlramAdapter(private val context : Context, private var items : ArrayList<HomeDetailAlramData>) : RecyclerView.Adapter<HomeDetailAlramAdapter.ViewHolder>(){

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder
            = ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_home_detail_alram, null), context)

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder!!.bind(items.get(position))
        holder.itemView.setOnClickListener {
            itemClick!!.onItemClick(holder.itemView, position)
        }

        holder.itemView.btn_edit.setOnClickListener{
            Toast.makeText(context, "EDIT", 0)
        }
        holder.itemView.btn_delete.setOnClickListener{
            Toast.makeText(context, "DELETE", 0)
        }
    }

    class ViewHolder(itemView: View, private val context : Context) : RecyclerView.ViewHolder(itemView) {
        init {
            var params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            itemView.layoutParams = params
        }
        fun bind(item: HomeDetailAlramData) {
            itemView.title.text = item.title
            itemView.content.text = item.content
            when(item.switch){
                true-> itemView.btn_switch.setChecked(true)
                false-> itemView.btn_switch.setChecked(false)
            }
        }
    }
    var itemClick: ItemClick? = null

    interface ItemClick {
        fun onItemClick(view: View?, position: Int)
    }
}
