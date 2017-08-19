package hoosasack.pillnow.Adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import hoosasack.pillnow.Data.AlramData
import kotlinx.android.synthetic.main.item_alram.view.*
import hoosasack.pillnow.R

/**
 * Created by parktaejun on 2017. 8. 12..
 */
class AlramAdapter(private val context : Context, private var items : ArrayList<AlramData>) : RecyclerView.Adapter<AlramAdapter.ViewHolder>(){

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder
            = ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_alram, null), context)

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
        fun bind(item: AlramData) {
            itemView.image.setImageDrawable(ContextCompat.getDrawable(context, item.image))
            itemView.name.text = item.name
            itemView.content.text = item.content
        }
    }
    var itemClick: ItemClick? = null

    interface ItemClick {
        fun onItemClick(view: View?, position: Int)
    }
}
