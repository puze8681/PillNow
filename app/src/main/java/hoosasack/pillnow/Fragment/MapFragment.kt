package hoosasack.pillnow.Fragment

import android.content.Context
import android.content.Context.WINDOW_SERVICE
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.text.Layout
import android.util.DisplayMetrics
import android.view.*
import android.widget.*
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import hoosasack.pillnow.Adapter.HomeAlramAdapter
import hoosasack.pillnow.Adapter.HomeDetailAlramAdapter
import hoosasack.pillnow.Adapter.MapListAdapter
import hoosasack.pillnow.Data.HomeAlramData
import hoosasack.pillnow.Data.MapListData
import hoosasack.pillnow.Data.MarkerItemData
import hoosasack.pillnow.R
import hoosasack.pillnow.Util.Map.GpsInfo
import kotlinx.android.synthetic.main.actionbar_map.*
import kotlinx.android.synthetic.main.actionbar_map_inform.*
import kotlinx.android.synthetic.main.actionbar_map_list.*
import kotlinx.android.synthetic.main.layout_home_detail.*
import kotlinx.android.synthetic.main.layout_map.*
import kotlinx.android.synthetic.main.layout_map_inform.*
import kotlinx.android.synthetic.main.layout_map_list.*
import kotlin.properties.Delegates

class MapFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener {

    lateinit var selectedMarker : Marker
    private var map by Delegates.notNull<GoogleMap>()
    var items: ArrayList<MapListData> = ArrayList()
    lateinit var adapter: MapListAdapter
    var gpsInfo: GpsInfo = GpsInfo(context)
    lateinit var marker: Marker

    lateinit var current: LatLng

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_map, container, false)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        adapting()

        btn_pill_list.setOnClickListener {
            layout_fragment_map.visibility = View.GONE
            layout_fragment_map_list.visibility = View.VISIBLE
            layout_fragment_map_inform.visibility = View.GONE
        }
        btn_back_inform.setOnClickListener {
            layout_fragment_map.visibility = View.VISIBLE
            layout_fragment_map_list.visibility = View.GONE
            layout_fragment_map_inform.visibility = View.GONE
        }
        btn_search.setOnClickListener {
            layout_fragment_map.visibility = View.GONE
            layout_fragment_map_list.visibility = View.GONE
            layout_fragment_map_inform.visibility = View.VISIBLE
        }
        btn_back_list.setOnClickListener {
            layout_fragment_map.visibility = View.VISIBLE
            layout_fragment_map_list.visibility = View.GONE
            layout_fragment_map_inform.visibility = View.GONE
        }

        return view
    }

    override fun onMapReady(googleMap: GoogleMap) {
        var mMap = googleMap

        // Add a marker in Sydney and move the camera
        mMap.setMinZoomPreference(10F);
        mMap.setInfoWindowAdapter(CustomInfoWindowAdapter(context))
        map.setOnMarkerClickListener(this)
        map.setOnMapClickListener(this)
        getSampleMarkerItems()
        setLocation()

        btn_current_location.setOnClickListener{
            setLocation()
        }

        btn_zoom_in.setOnClickListener {
            map.animateCamera(CameraUpdateFactory.zoomIn())
        }

        btn_zoom_out.setOnClickListener {
            map.animateCamera(CameraUpdateFactory.zoomOut())
        }
    }



    override fun onMarkerClick(p0: Marker?): Boolean {
        var center : CameraUpdate = CameraUpdateFactory.newLatLng(marker.position)
        map.animateCamera(center)
        changeSelectedMarker(marker)
        return true
    }

    override fun onMapClick(p0: LatLng?) {
        changeSelectedMarker(null)
    }

    fun changeSelectedMarker(marker : Marker?){
        if(selectedMarker != null){
            if(marker != null){
                selectedMarker.hideInfoWindow()
                marker.showInfoWindow()
                selectedMarker = marker
            }else{
                selectedMarker.hideInfoWindow()
                selectedMarker = null!!
            }
        }else{
            if(marker != null){
                marker.showInfoWindow()
                selectedMarker = marker
            }else{
            }
        }

    }

    private fun addMarker(markerItem: MarkerItemData) {
        var position: LatLng = LatLng(markerItem.lat, markerItem.lon)
        var markerOptions: MarkerOptions
        var markerIcon: BitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.ic_map_marker)
        markerOptions = MarkerOptions().position(position).icon(markerIcon).title(markerItem.title).snippet(markerItem.location)
        map.addMarker(markerOptions)
    }


    val Context.windowManager: WindowManager
        get() = getSystemService(Context.WINDOW_SERVICE) as WindowManager


    private fun setLocation() {
        var isSuccess: Boolean = false
        var currentIcon: BitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.ic_map_current_position)
        if (gpsInfo.isGetLocation) {
            if (gpsInfo.latitude == 0.toDouble() && gpsInfo.longitude == 0.toDouble()) {
                isSuccess = true
            }
        }
        if (isSuccess) {
            marker = map.addMarker(MarkerOptions().icon(currentIcon).position(LatLng(gpsInfo.lat, gpsInfo.lon)))
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(gpsInfo.lat, gpsInfo.lon), 17.0f))
        } else {
            Toast.makeText(context, "자신의 위치 불러오기 실패", 0)
        }
    }

    private fun getSampleMarkerItems() {
        var markerList: ArrayList<MarkerItemData> = ArrayList()

        //List this ... add marker
        //markerList.add(MarkerItemData(12.0, 13.1, "check", "check"))

        for (i in markerList) {
            addMarker(i)
        }
    }

    private class CustomInfoWindowAdapter(private var context: Context) : GoogleMap.InfoWindowAdapter {

        private lateinit final var infoWindow: View

        init {
            infoWindow = View.inflate(context, R.layout.item_marker, null)
        }

        override fun getInfoWindow(marker: Marker?): View {

            var title: String = marker!!.title
            var location: String = marker!!.snippet
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

    private fun adapting() {
        adapter = MapListAdapter(context.applicationContext, items)
        recyclerView?.layoutManager = LinearLayoutManager(context.applicationContext, LinearLayoutManager.VERTICAL, false)
        recyclerView?.adapter = adapter
    }

    companion object {
        fun newInstance(): MapFragment {
            val fragment: MapFragment = MapFragment()
            return fragment
        }
    }
}

//    private fun updateCamera(lat: Double, log: Double) {
//        map.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(lat, log), 13.0f))
//    }
//    private fun zoomCamera(zoom: Float) {
//        map.animateCamera(CameraUpdateFactory.zoomTo(zoom))
//    }
//    private fun createDrawableFromView(context: Context, view: View): Bitmap {
//
//        val displayMetrics = DisplayMetrics()
//        context.windowManager.defaultDisplay.getMetrics(displayMetrics)
//        view.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
//        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels)
//        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels)
//        view.buildDrawingCache()
//        val bitmap = Bitmap.createBitmap(view.measuredWidth, view.measuredHeight, Bitmap.Config.ARGB_8888)
//
//        val canvas = Canvas(bitmap)
//        view.draw(canvas)
//
//        return bitmap
//    }