package hoosasack.pillnow.Fragment

import android.content.Context
import android.content.Context.WINDOW_SERVICE
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.text.Layout
import android.util.DisplayMetrics
import android.view.*
import android.widget.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import hoosasack.pillnow.Adapter.HomeAlramAdapter
import hoosasack.pillnow.Adapter.HomeDetailAlramAdapter
import hoosasack.pillnow.Adapter.MapListAdapter
import hoosasack.pillnow.Data.HomeAlramData
import hoosasack.pillnow.Data.MapListData
import hoosasack.pillnow.R
import kotlinx.android.synthetic.main.actionbar_map.*
import kotlinx.android.synthetic.main.actionbar_map_inform.*
import kotlinx.android.synthetic.main.actionbar_map_list.*
import kotlinx.android.synthetic.main.layout_home_detail.*
import kotlinx.android.synthetic.main.layout_map.*
import kotlinx.android.synthetic.main.layout_map_inform.*
import kotlinx.android.synthetic.main.layout_map_list.*
import kotlin.properties.Delegates

class MapFragment : Fragment(), OnMapReadyCallback {

    private var map by Delegates.notNull<GoogleMap>()
    var items: ArrayList<MapListData> = ArrayList()
    lateinit var adapter: MapListAdapter

    lateinit var current : LatLng

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_map, container, false)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        adapting()

        btn_current_location.setOnClickListener{
            Toast.makeText(context, "LOCATION", 0)
        }
        btn_zoom_in.setOnClickListener{
            map.animateCamera(CameraUpdateFactory.zoomIn())
        }

        btn_zoom_out.setOnClickListener{
            map.animateCamera(CameraUpdateFactory.zoomOut())
        }
        btn_pill_list.setOnClickListener{
            layout_fragment_map.visibility = View.GONE
            layout_fragment_map_list.visibility = View.VISIBLE
            layout_fragment_map_inform.visibility = View.GONE
        }
        btn_back_inform.setOnClickListener{
            layout_fragment_map.visibility = View.VISIBLE
            layout_fragment_map_list.visibility = View.GONE
            layout_fragment_map_inform.visibility = View.GONE
        }
        btn_search.setOnClickListener{
            layout_fragment_map.visibility = View.GONE
            layout_fragment_map_list.visibility = View.GONE
            layout_fragment_map_inform.visibility = View.VISIBLE
        }
        btn_back_list.setOnClickListener{
            layout_fragment_map.visibility = View.VISIBLE
            layout_fragment_map_list.visibility = View.GONE
            layout_fragment_map_inform.visibility = View.GONE
        }



        return view
    }

    override fun onMapReady(googleMap: GoogleMap) {
        var mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    private fun updateCamera(lat: Double, log: Double) {
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(lat, log), 13.0f))
    }

    private fun zoomCamera(zoom: Float) {
        map.animateCamera(CameraUpdateFactory.zoomTo(zoom))
    }

    private fun addMarker(title: String, location: String, lat: Double, log: Double, isMarkerRemove: Boolean) {
        val tparam = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
        val mparam = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
        val cparam = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
        val lparam = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
        val view = FrameLayout(context)
        val textTitle = TextView(context)
        val textLocation = TextView(context)
        val informLayout = LayoutInflater.from(context)

        lparam.topMargin = 11

        tparam.topMargin = 12
        textTitle.text = title
        textTitle.layoutParams = tparam
        textTitle.gravity = Gravity.CENTER_HORIZONTAL
        textTitle.textSize = 15.0f
        textTitle.setTextColor(ContextCompat.getColor(context, R.color.orange))

        cparam.topMargin = 10
        textLocation.text= location
        textLocation.layoutParams = cparam
        textLocation.gravity = Gravity.CENTER_HORIZONTAL
        textLocation.textSize = 13.0f
        textLocation.setTextColor(ContextCompat.getColor(context, R.color.textBlack))

        val imageView = ImageView(context)
        imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_map_marker))
        imageView.layoutParams = mparam

        view.addView(imageView)
        view.addView(textTitle)
        view.addView(textLocation)

        map.addMarker(MarkerOptions().position(LatLng(lat, log)).icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(context, view))))

    }

    val Context.windowManager : WindowManager
        get() = getSystemService(Context.WINDOW_SERVICE) as WindowManager

    private fun createDrawableFromView(context: Context, view: View): Bitmap {

        val displayMetrics = DisplayMetrics()
        context.windowManager.defaultDisplay.getMetrics(displayMetrics)
        view.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels)
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels)
        view.buildDrawingCache()
        val bitmap = Bitmap.createBitmap(view.measuredWidth, view.measuredHeight, Bitmap.Config.ARGB_8888)

        val canvas = Canvas(bitmap)
        view.draw(canvas)

        return bitmap
    }

    private fun adapting(){
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
