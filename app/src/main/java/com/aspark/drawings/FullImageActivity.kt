package com.aspark.drawings

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MotionEvent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.aspark.drawings.bottom_sheet.AddMarkerBottomSheet
import com.aspark.drawings.databinding.ActivityFullImageBinding
import com.aspark.drawings.room.AppDatabase.Companion.getDatabase
import com.bumptech.glide.Glide

class FullImageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFullImageBinding
    val viewModel: AddMarkerViewModel by viewModels()

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFullImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imagePath = intent.getStringExtra("imagePath")
        val drawingId = intent.getIntExtra("drawingId",-1)
        val markerCount = intent.getIntExtra("markerCount",-1)

        val markerDao = getDatabase(applicationContext).markerDao()
        viewModel.getMarkers(drawingId, markerDao)

        viewModel.markersList.observe(this) {

//            if ( ! it.isNullOrEmpty()) {
//
//                for ( marker in viewModel.markersList) {
//
//                    val markerView = createMarkerView(marker.id)
//                    val layoutParams = RelativeLayout.LayoutParams(
//                        ViewGroup.LayoutParams.WRAP_CONTENT,
//                        ViewGroup.LayoutParams.WRAP_CONTENT
//                    )
//                    layoutParams.leftMargin = marker.x.toInt()
//                    layoutParams.topMargin = marker.y.toInt()
//                    imageViewDrawing.addView(markerView, layoutParams)
//                }
//            }
        }

        Glide
            .with(this)
            .load(imagePath?.toUri())
            .into(binding.ivFullImage)

          val DOUBLE_TAP_DELAY = 200
          var doubleTapDetected = false

        binding.ivFullImage.setOnTouchListener { v, event ->

            Log.d("FullImageActivity", "onCreate: fullImage touch")
           val doubleTapX = v.x + v.width / 2
           val doubleTapY = v.y + v.height / 2

            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (doubleTapDetected) {
                        // Double-tap detected, handle the event here
                        Log.d("FullImageActivity", "onCreate: doubleTap detected")
                        val addMarkerBottomSheet = AddMarkerBottomSheet(drawingId, markerCount,
                            doubleTapX, doubleTapY)

                        addMarkerBottomSheet.show(supportFragmentManager,
                            "AddMarkerBottomSheet")

                        doubleTapDetected = false
                        true

                    } else {
                        // Wait for a short delay to check for a second tap
                        doubleTapDetected = true
                        Handler().postDelayed({
                            doubleTapDetected = false
                        }, DOUBLE_TAP_DELAY.toLong())
                        false

                    }
                }

                else -> false
            }

//            val gestureDetector = GestureDetector(this,
//                object : GestureDetector.SimpleOnGestureListener() {
//
//                    override fun onDoubleTap(e: MotionEvent): Boolean {
//
//                        Log.i("FullImageActivity", "onDoubleTap: FullImage Double tap")
//
//                        if (drawingId != -1) {
//
//                            val addMarkerBottomSheet = AddMarkerBottomSheet(drawingId,
//                                doubleTapX, doubleTapY)
//                            addMarkerBottomSheet.show(supportFragmentManager,
//                                "AddMarkerBottomSheet")
//                        }
//                        return true
//                    }
//
//                    override fun onDoubleTapEvent(e: MotionEvent): Boolean {
//                        return true
//                    }
//                })
//            gestureDetector.onTouchEvent(event)
        }
    }

//    private fun createMarkerView(markerId: Int): View {
//
//        val markerTextView = TextView(this)
//        markerTextView.text = (markerId + 1).toString()
//        markerTextView.setTextColor(Color.WHITE)
//        markerTextView.setBackgroundResource(R.drawable.marker_background)
//        markerTextView.setPadding(16, 8, 16, 8)
//        markerTextView.gravity = Gravity.CENTER
//
//        return markerTextView
//    }

}