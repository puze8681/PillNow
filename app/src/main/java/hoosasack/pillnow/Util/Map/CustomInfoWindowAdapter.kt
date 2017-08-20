package hoosasack.pillnow.Util.Map

import android.content.Context
import android.view.View
import android.widget.TextView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import hoosasack.pillnow.R

/**
 * Created by parktaejun on 2017. 8. 20..
 */
class CustomInfoWindowAdapter(context: Context) : GoogleMap.InfoWindowAdapter {

    private var infoWindow: View

    init {
        infoWindow = View.inflate(context, R.layout.item_marker, null)
    }

    override fun getInfoWindow(marker: Marker?): View {

        var title: String = marker!!.title
        var location: String = marker.snippet
        var titleText: TextView = infoWindow.findViewById(R.id.title)
        var locationText: TextView = infoWindow.findViewById(R.id.location)
        titleText.text = title
        locationText.text = location

        return infoWindow
    }

    override fun getInfoContents(marker: Marker?): View? {
        return null
    }

}