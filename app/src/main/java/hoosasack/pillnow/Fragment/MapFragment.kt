package hoosasack.pillnow.Fragment

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import hoosasack.pillnow.Adapter.MapListAdapter
import hoosasack.pillnow.Data.MapListData
import hoosasack.pillnow.Data.MarkerItemData
import hoosasack.pillnow.MainActivity
import hoosasack.pillnow.R
import hoosasack.pillnow.Util.Map.CustomInfoWindowAdapter
import hoosasack.pillnow.Util.Map.GpsInfo
import hoosasack.pillnow.Util.Server.Data.Location
import hoosasack.pillnow.Util.Server.Data.Login
import hoosasack.pillnow.Util.Server.NetWork.RetrofitService
import kotlinx.android.synthetic.main.actionbar_map.*
import kotlinx.android.synthetic.main.actionbar_map_inform.*
import kotlinx.android.synthetic.main.actionbar_map_list.*
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.android.synthetic.main.layout_map.*
import kotlinx.android.synthetic.main.layout_map_inform.*
import kotlinx.android.synthetic.main.layout_map_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.properties.Delegates

class MapFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener {

    var intent : Intent = Intent()
    var token = intent.getStringExtra("token")

    lateinit var retrofitService: RetrofitService
    lateinit var progressDialog: ProgressDialog
    var markerCheck : Boolean = false
    private var map by Delegates.notNull<GoogleMap>()
    var items: ArrayList<MapListData> = ArrayList()
    lateinit var adapter: MapListAdapter
    lateinit var gpsInfo: GpsInfo
    lateinit var marker: Marker
    var selectedMarker : Marker? = null

    val Context.windowManager : WindowManager
        get() = getSystemService(Context.WINDOW_SERVICE) as WindowManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_map, container, false)

        markerCheck = false

        var mapFragment : SupportMapFragment? = null
        mapFragment = fragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        gpsInfo = GpsInfo(this@MapFragment.context)
        adapting()

        btn_current_location?.setOnClickListener{
            setLocation()
        }

        btn_pill_list?.setOnClickListener {
            layout_fragment_map.visibility = View.GONE
            layout_fragment_map_list.visibility = View.VISIBLE
            layout_fragment_map_inform.visibility = View.GONE
        }
        btn_back_inform?.setOnClickListener {
            layout_fragment_map.visibility = View.VISIBLE
            layout_fragment_map_list.visibility = View.GONE
            layout_fragment_map_inform.visibility = View.GONE
        }
        btn_search?.setOnClickListener {
            layout_fragment_map.visibility = View.GONE
            layout_fragment_map_list.visibility = View.GONE
            layout_fragment_map_inform.visibility = View.VISIBLE
        }
        btn_back_list?.setOnClickListener {
            layout_fragment_map.visibility = View.VISIBLE
            layout_fragment_map_list.visibility = View.GONE
            layout_fragment_map_inform.visibility = View.GONE
        }

        return view
    }

    override fun onMapReady(googleMap: GoogleMap) {

        var mMap = googleMap

        setLocation()
        mMap.setMinZoomPreference(10F)
        mMap.setInfoWindowAdapter(CustomInfoWindowAdapter(context))

        map.setOnMapClickListener(this)
        map.setOnMarkerClickListener(this)

        btn_zoom_in?.setOnClickListener {
            map.animateCamera(CameraUpdateFactory.zoomIn())
        }

        btn_zoom_out?.setOnClickListener {
            map.animateCamera(CameraUpdateFactory.zoomOut())
        }
    }

    override fun onMarkerClick(clickedMarker: Marker?): Boolean {
        var center : CameraUpdate = CameraUpdateFactory.newLatLng(marker.position)
        map.animateCamera(center)
        markerCheck = true
        selectedMarker = clickedMarker
        changeSelectedMarker(clickedMarker)
        return true
    }

    override fun onMapClick(p0: LatLng?) {
        markerCheck = false
        selectedMarker = null
        changeSelectedMarker(null)
    }

    fun changeSelectedMarker(changedMarker : Marker?){
        if(markerCheck != false){
            //마커 -> 마커 클릭
            if(changedMarker != null){
                selectedMarker?.hideInfoWindow()
                changedMarker.showInfoWindow()
                selectedMarker = changedMarker
                markerCheck = true
            }
            //마커 -> 바탕 클릭
            else{
                selectedMarker?.hideInfoWindow()
                selectedMarker = null
                markerCheck = true
            }
        }else{
            //바탕 -> 마커 클릭
            if(changedMarker != null){
                marker.showInfoWindow()
                selectedMarker = changedMarker
                markerCheck = true
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

    private fun setLocation() {
        var isSuccess: Boolean = false
        var currentIcon: BitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.ic_map_current_position)
        if (gpsInfo.isGetLocation) {
            if (gpsInfo.latitude == 0.toDouble() && gpsInfo.longitude == 0.toDouble()) {
                isSuccess = true
            }
        }
        if (isSuccess) {
            marker = map.addMarker(MarkerOptions().icon(currentIcon).position(LatLng(gpsInfo.lat as Double, gpsInfo.lon as Double)))
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(gpsInfo.lat as Double, gpsInfo.lon as Double), 17.0f))
            getStore(gpsInfo.lat as String, gpsInfo.lon as String)
        } else {
            Toast.makeText(context, "자신의 위치 불러오기 실패", 0)
        }
    }

    private fun getStore(lat : String, lon : String) {
        progressDialogSetting()
        var markerList: ArrayList<MarkerItemData> = ArrayList()
        var call: Call<Location> = retrofitService.location(lat, lon)
        call.enqueue(object : Callback<Location>{
            override fun onResponse(call: Call<Location>?, response: Response<Location>?) {
                if (response?.code() === 200) {
                    progressDialog.dismiss()
                    val user = response?.body()
                    if (user != null) {
                        Toast.makeText(this@MapFragment.context, "약국 위치 불러오기 성공 . . .", Toast.LENGTH_SHORT).show()
                    }
                } else if (response?.code() === 404) {
                    progressDialog.dismiss()
                    Toast.makeText(this@MapFragment.context, "약국 위치 불러오기 실패... ", Toast.LENGTH_SHORT).show()
                } else {
                    progressDialog.dismiss()
                    Toast.makeText(this@MapFragment.context, "UNKNOWN ERR ... ", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Location>?, t: Throwable?) {
                progressDialog.dismiss();
                Toast.makeText(this@MapFragment.context, "요청 불가 ... ", Toast.LENGTH_SHORT).show();
            }
        })

        //List this ... add marker
        //markerList.add(MarkerItemData(12.0, 13.1, "check", "check"))
        for (i in markerList) {
            addMarker(i)
        }
    }

    fun retrofitSetting() {
        var retrofit: Retrofit = Retrofit.Builder()
                .baseUrl("https://soylatte.kr:3000")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        retrofitService = retrofit.create(RetrofitService::class.java)
    }

    fun progressDialogSetting(){
        progressDialog = ProgressDialog(this@MapFragment.context)
        progressDialog.setMessage("로그인 하는 중입니다")
        progressDialog.show()
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