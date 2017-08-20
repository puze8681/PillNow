package hoosasack.pillnow.Fragment

import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hoosasack.pillnow.R
import android.graphics.BitmapFactory
import android.util.DisplayMetrics
import android.graphics.Bitmap
import com.google.android.gms.vision.text.TextBlock
import com.google.android.gms.vision.text.TextRecognizer
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.ContentValues
import android.content.Intent
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import com.google.android.gms.vision.Frame
import hoosasack.pillnow.Adapter.SearchCustomAdapter
import hoosasack.pillnow.Data.SearchCustomData
import hoosasack.pillnow.MainActivity
import hoosasack.pillnow.Util.Server.Data.Login
import hoosasack.pillnow.Util.Server.Data.Medicine
import hoosasack.pillnow.Util.Server.NetWork.RetrofitService
import kotlinx.android.synthetic.main.actionbar_custom_input.*
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.android.synthetic.main.fragment_scan.*
import kotlinx.android.synthetic.main.layout_custom_input.*
import kotlinx.android.synthetic.main.layout_scan.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream
import java.util.*


class ScanFragment : Fragment() {

    val detectedText = StringBuilder()

    var intent : Intent = Intent()
    var token = intent.getStringExtra("token")

    lateinit var retrofitService: RetrofitService
    lateinit var progressDialog: ProgressDialog

    private var REQUEST_GALLERY: Int = 0
    private var REQUEST_CAMERA: Int = 1

    private var TAG: String = ScanFragment::class.java.simpleName
    lateinit var imageUri: Uri

    var items: ArrayList<SearchCustomData> = ArrayList()
    lateinit var adapter: SearchCustomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_scan, container, false)

        retrofitSetting()

        btn_camera?.setOnClickListener {
            var filename : String = System.currentTimeMillis().toString()+".jpg"
            var values : ContentValues = ContentValues()
            values.put(MediaStore.Images.Media.TITLE, filename)
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            imageUri = this@ScanFragment.context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
            startActivityForResult(intent, REQUEST_CAMERA)
        }
        btn_check?.setOnClickListener{
            progressDialogSetting()
            var call: Call<Medicine> = retrofitService.medicine(detectedText.toString(), token)
            call.enqueue(object : Callback<Medicine> {
                override fun onResponse(call: Call<Medicine>?, response: Response<Medicine>?) {
                    if (response?.code() === 200) {
                        progressDialog.dismiss()
                        val user = response?.body()
                        Toast.makeText(this@ScanFragment.context, "성공적으로 등록하였습니다 . . .", Toast.LENGTH_SHORT).show()
                    } else if (response?.code() === 404) {
                        progressDialog.dismiss()
                        Toast.makeText(this@ScanFragment.context, "등록에 실패하였습니다 ... ", Toast.LENGTH_SHORT).show()
                    } else {
                        progressDialog.dismiss()
                        Toast.makeText(this@ScanFragment.context, "UNKNOWN ERR ... ", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Medicine>?, t: Throwable?) {
                    progressDialog.dismiss();
                    Toast.makeText(this@ScanFragment.context, "요청 불가 ... ", Toast.LENGTH_SHORT).show();
                }
            })
        }
        btn_custom_input?.setOnClickListener{
            layout_scan.visibility = View.GONE
            layout_scan_custom_input.visibility = View.VISIBLE
        }

        btn_back?.setOnClickListener{
            layout_scan.visibility = View.VISIBLE
            layout_scan_custom_input.visibility = View.GONE
        }

        adapter = SearchCustomAdapter(context.applicationContext, items)
        search_pill_list?.adapter = adapter

        adapter.itemClick = object : SearchCustomAdapter.ItemClick{
            override fun onItemClick(view: View?, position: Int) {
                progressDialogSetting()
                var call: Call<Medicine> = retrofitService.medicine(items.get(position).content, token)
                call.enqueue(object : Callback<Medicine> {
                    override fun onResponse(call: Call<Medicine>?, response: Response<Medicine>?) {
                        if (response?.code() === 200) {
                            progressDialog.dismiss()
                            val user = response?.body()
                            Toast.makeText(this@ScanFragment.context, "성공적으로 등록하였습니다 . . .", Toast.LENGTH_SHORT).show()
                        } else if (response?.code() === 404) {
                            progressDialog.dismiss()
                            Toast.makeText(this@ScanFragment.context, "등록에 실패하였습니다 ... ", Toast.LENGTH_SHORT).show()
                        } else {
                            progressDialog.dismiss()
                            Toast.makeText(this@ScanFragment.context, "UNKNOWN ERR ... ", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<Medicine>?, t: Throwable?) {
                        progressDialog.dismiss();
                        Toast.makeText(this@ScanFragment.context, "요청 불가 ... ", Toast.LENGTH_SHORT).show();
                    }
                })
            }
        }

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_GALLERY -> if (resultCode == RESULT_OK) {
                inspect(data!!.data)
            }
            REQUEST_CAMERA -> if (resultCode == RESULT_OK) {
                if (imageUri != null) {
                    inspect(imageUri)
                }
            }
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun inspectFromBitmap(bitmap: Bitmap) {
        val textRecognizer = TextRecognizer.Builder(this@ScanFragment.context).build()
        try {
            if (!textRecognizer.isOperational) {
                AlertDialog.Builder(this@ScanFragment.context).setMessage("기기에서 구글 비전을 사용할 수 없습니다").show()
                return
            }

            val frame = Frame.Builder().setBitmap(bitmap).build()
            val origTextBlocks = textRecognizer.detect(frame)
            var textBlocks: List<TextBlock> = ArrayList()
            for (i in 0..origTextBlocks.size() - 1) {
                val textBlock = origTextBlocks.valueAt(i)
                textBlocks.plus(textBlock)
            }
            Collections.sort(textBlocks, object : Comparator<TextBlock> {
                override fun compare(o1: TextBlock, o2: TextBlock): Int {
                    val diffOfTops = o1.boundingBox.top - o2.boundingBox.top
                    val diffOfLefts = o1.boundingBox.left - o2.boundingBox.left
                    if (diffOfTops != 0) {
                        return diffOfTops
                    }
                    return diffOfLefts
                }
            })

            for (textBlock in textBlocks) {
                if (textBlock != null && textBlock!!.getValue() != null) {
                    detectedText.append(textBlock!!.getValue())
                    detectedText.append("\n")
                }
            }

            resultTv.setText(detectedText)
        } finally {
            textRecognizer.release()
        }
    }

    private fun inspect(uri: Uri) {
        var iss: InputStream? = null
        var bitmap: Bitmap? = null
        try {
            iss = this@ScanFragment.context.getContentResolver().openInputStream(uri)
            val options = BitmapFactory.Options()
            options.inPreferredConfig = Bitmap.Config.ARGB_8888
            options.inSampleSize = 2
            options.inScreenDensity = DisplayMetrics.DENSITY_LOW
            bitmap = BitmapFactory.decodeStream(iss, null, options)
            inspectFromBitmap(bitmap)
        } catch (e: FileNotFoundException) {
            Log.w(TAG, "Failed to find the file: " + uri, e)
        } finally {
            if (bitmap != null) {
                bitmap.recycle()
            }
            if (iss != null) {
                try {
                    iss!!.close()
                } catch (e: IOException) {
                    Log.w(TAG, "Failed to close InputStream", e)
                }

            }
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
        progressDialog = ProgressDialog(this@ScanFragment.context)
        progressDialog.setMessage("로그인 하는 중입니다")
        progressDialog.show()
    }

    companion object {
        fun newInstance(): ScanFragment {
            val fragment: ScanFragment = ScanFragment()
            return fragment
        }
    }
}
