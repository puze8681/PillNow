///*
//package hoosasack.pillnow.Util.Camera
//
//import android.view.View
//import android.text.method.TextKeyListener.clear
//import com.google.android.gms.internal.add
//import android.graphics.Bitmap
//import android.support.v4.view.ViewCompat.setAlpha
//import android.graphics.Paint.Align
//import android.graphics.Paint.LINEAR_TEXT_FLAG
//import android.opengl.ETC1.getHeight
//import android.opengl.ETC1.getWidth
//import android.annotation.SuppressLint
//import android.content.Context
//import android.graphics.Paint
//import android.hardware.camera2.CameraManager
//import android.graphics.Paint.ANTI_ALIAS_FLAG
//import android.util.AttributeSet
//import hoosasack.pillnow.R
//
//
//*/
///**
// * Created by parktaejun on 2017. 8. 19..
// *//*
//
//class ViewFinderView constructor(context: Context, attrs: AttributeSet): View{
//    private val SCANNER_ALPHA = intArrayOf(0, 64, 128, 192, 255, 192, 128, 64)
//    private val ANIMATION_DELAY = 80L
//    private val CURRENT_POINT_OPACITY = 0xA0
//    private val MAX_RESULT_POINTS = 20
//    private val POINT_SIZE = 6
//
//    private var cameraManager: CameraManager? = null
//    private var paint: Paint
//    private var whitePaint: Paint
//    private var resultBitmap: Bitmap? = null
//    private var maskColor: Int
//    private var resultColor: Int
//    private var laserColor: Int
//    private var resultPointColor: Int
//    private var scannerAlpha: Int = 0
//    private var possibleResultPoints: MutableList<ResultPoint>? = null
//    private var lastPossibleResultPoints: List<ResultPoint>? = null
//
//    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
//
//    // This constructor is used when the class is built from an XML resource.
//    fun ViewfinderView(context: Context, attrs: AttributeSet): super(context, attrs){
//
//        // Initialize these once for performance rather than calling them every time in onDraw().
//        paint = Paint(Paint.ANTI_ALIAS_FLAG)
//        whitePaint = Paint(Paint.ANTI_ALIAS_FLAG)
//        val resources = resources
//        maskColor = resources.getColor(R.color.viewfinder_mask)
//        resultColor = resources.getColor(R.color.result_view)
//        laserColor = resources.getColor(R.color.viewfinder_laser)
//        resultPointColor = resources.getColor(R.color.possible_result_points)
//        scannerAlpha = 0
//        possibleResultPoints = ArrayList(5)
//        lastPossibleResultPoints = null
//    }
//
//    fun setCameraManager(cameraManager: CameraManager) {
//        this.cameraManager = cameraManager
//    }
//
//    @SuppressLint("DrawAllocation")
//    fun onDraw(canvas: Canvas) {
//        if (cameraManager == null) {
//            return  // not ready yet, early draw before done configuring
//        }
//        val frame = cameraManager!!.getFramingRect()
//        val previewFrame = cameraManager!!.getFramingRectInPreview()
//        if (frame == null || previewFrame == null) {
//            return
//        }
//        val width = canvas.getWidth()
//        val height = canvas.getHeight()
//
//        // Draw the exterior (i.e. outside the framing rect) darkened
//        val text = Paint(Paint.LINEAR_TEXT_FLAG)
//        text.setAntiAlias(true)
//        text.setTextAlign(Paint.Align.CENTER)
//        text.setTextSize(resources.getDimensionPixelSize(R.dimen.cameraCanvasTextSize))
//        text.setColor(Color.WHITE)
//        paint.setColor(if (resultBitmap != null) resultColor else maskColor)
//        whitePaint.setColor(Color.WHITE)
//        whitePaint.setStrokeWidth(10)
//        whitePaint.setStyle(Paint.Style.STROKE)
//        canvas.drawRect(0, 0, width, frame!!.top, paint)
//        canvas.drawRect(0, frame!!.top, frame!!.left, frame!!.bottom + 1, paint)
//        canvas.drawRect(frame!!.right + 1, frame!!.top, width, frame!!.bottom + 1, paint)
//        canvas.drawRect(0, frame!!.bottom + 1, width, height, paint)
//
//        canvas.drawRect(frame!!.left, frame!!.top, frame!!.right, frame!!.bottom, whitePaint)
//        //        canvas.drawRoundRect(frame.left, frame.top, frame.right, frame.bottom,10, 10, whitePaint);
//        canvas.drawText("바코드가 사각형 안에 들어오도록 촬영해 주세요.", width / 2, frame!!.bottom + 100, text)
//
//        if (resultBitmap != null) {
//            // Draw the opaque result bitmap over the scanning rectangle
//            paint.setAlpha(CURRENT_POINT_OPACITY)
//            canvas.drawBitmap(resultBitmap, null, frame, paint)
//        } else {
//
//            // Draw a red "laser scanner" line through the middle to show decoding is active
//            scannerAlpha = (scannerAlpha + 1) % SCANNER_ALPHA.size
//
//            val currentPossible = possibleResultPoints
//            val currentLast = lastPossibleResultPoints
//            val frameLeft = frame!!.left
//            val frameTop = frame!!.top
//            if (currentPossible!!.isEmpty()) {
//                lastPossibleResultPoints = null
//            } else {
//                possibleResultPoints = ArrayList(5)
//                lastPossibleResultPoints = currentPossible
//            }
//        }
//
//    }
//
//
//    fun drawViewfinder() {
//        val resultBitmap = this.resultBitmap
//        this.resultBitmap = null
//        resultBitmap?.recycle()
//        invalidate()
//    }
//
//    */
///**
//     * Draw a bitmap with the result points highlighted instead of the live scanning display.
//
//     * @param barcode An image of the decoded barcode.
//     *//*
//
//    fun drawResultBitmap(barcode: Bitmap) {
//        resultBitmap = barcode
//        invalidate()
//    }
//
//    fun addPossibleResultPoint(point: ResultPoint) {
//        val points = possibleResultPoints
//        synchronized(points) {
//            points!!.add(point)
//            val size = points.size
//            if (size > MAX_RESULT_POINTS) {
//                // trim it
//                points.subList(0, size - MAX_RESULT_POINTS / 2).clear()
//            }
//        }
//    }
//
//}*/
