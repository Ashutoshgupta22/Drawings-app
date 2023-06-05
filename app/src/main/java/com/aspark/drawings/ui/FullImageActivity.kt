package com.aspark.drawings.ui

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Matrix
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.GestureDetector
import android.view.Gravity
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.aspark.drawings.R
import com.aspark.drawings.bottom_sheet.AddMarkerBottomSheet
import com.aspark.drawings.bottom_sheet.AddMarkerViewModel
import com.aspark.drawings.databinding.ActivityFullImageBinding
import com.aspark.drawings.model.Marker
import com.aspark.drawings.room.AppDatabase.Companion.getDatabase
import com.bumptech.glide.Glide

class FullImageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFullImageBinding
    private val viewModel: AddMarkerViewModel by viewModels()
    private var scaleFactor = 1f
    private var matrix: Matrix = Matrix()

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFullImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.i("FullImageActivity", "onCreate: called")

        val imagePath = intent.getStringExtra("imagePath")
        val drawingId = intent.getIntExtra("drawingId",-1)
        val markerCount = intent.getIntExtra("markerCount",-1)

        val markerDao = getDatabase(applicationContext).markerDao()
        viewModel.getAllMarkers(drawingId, markerDao)

        registerObservers()
        viewModel.setMarkerCount(markerCount)

        Glide
            .with(this)
            .load(imagePath?.toUri())
            .into(binding.ivFullImage)


        val scaleGestureDetector = ScaleGestureDetector(this,
            object : ScaleGestureDetector.SimpleOnScaleGestureListener() {

                override fun onScale(detector: ScaleGestureDetector): Boolean {

                    Log.i("FullImageActivity", "onScale: zooming in")

                    scaleFactor *= detector.scaleFactor
                    scaleFactor = scaleFactor.coerceIn(1f,5f)

                    val focusX = detector.focusX
                    val focusY = detector.focusY

                    matrix.setScale(scaleFactor, scaleFactor, focusX, focusY)
                    binding.ivFullImage.imageMatrix = matrix

                    return true
                }

        })

        val gestureDetector = GestureDetector(this, object :
            GestureDetector.SimpleOnGestureListener() {

            override fun onDoubleTap(e: MotionEvent): Boolean {

                scaleFactor = 1f
                matrix.reset()

                binding.ivFullImage.imageMatrix = matrix

                return true
            }

        })



          val DOUBLE_TAP_DELAY = 200
          var doubleTapDetected = false

        binding.ivFullImage.setOnTouchListener { _, event ->

            scaleGestureDetector.onTouchEvent(event)
            gestureDetector.onTouchEvent(event)


            Log.d("FullImageActivity", "onCreate: fullImage touch")



            when (event.action) {
                MotionEvent.ACTION_DOWN -> {

                    if (doubleTapDetected) {

                        Log.d("FullImageActivity", "onCreate: doubleTap detected")

                        if (drawingId != -1 && viewModel.markerCount.value != -1) {

                            val addMarkerBottomSheet = AddMarkerBottomSheet( drawingId,
                                event.x, event.y, null) {

                                registerObservers()
                                viewModel.getAllMarkers(drawingId, markerDao)
                            }

                            addMarkerBottomSheet.show(supportFragmentManager,
                                "AddMarkerBottomSheet")
                        }
                        doubleTapDetected = false
                        true

                    } else {

                        // Wait for a short delay to check for a second tap

                        doubleTapDetected = true
                        Handler(Looper.myLooper()!!).postDelayed({
                            doubleTapDetected = false
                        }, DOUBLE_TAP_DELAY.toLong())
                        false

                    }
                }

                else -> false
            }

        }
    }

    private fun createMarkerView(markerNumber: Int, marker: Marker): View {

        val markerTextView = TextView(this)
        markerTextView.text = (markerNumber).toString()
        markerTextView.setTextColor(Color.WHITE)
        markerTextView.setBackgroundResource(R.drawable.bg_marker)
        markerTextView.setPadding(16, 8, 16, 8)
        markerTextView.gravity = Gravity.CENTER

        markerTextView.setOnClickListener {

            val markerBottomSheet = AddMarkerBottomSheet(marker.drawingId,
                marker.doubleTapX, marker.doubleTapY, marker) {

            }
            markerBottomSheet.show(supportFragmentManager,"EditMarkerBottomSheet")
        }

        return markerTextView
    }

    private fun registerObservers() {

        Log.d("FullImageActivity", "registerObservers: called ")

        viewModel.markersList.observe(this) {

            if ( ! it.isNullOrEmpty()) {

                binding.markerOverlay.removeAllViews()

                for ((count, marker) in viewModel.markersList.value!!.withIndex()) {

                    val markerView = createMarkerView(count + 1, marker)
                    val layoutParams = FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT)

                    layoutParams.leftMargin = marker.doubleTapX.toInt()
                    layoutParams.topMargin = marker.doubleTapY.toInt()
                    binding.markerOverlay.addView(markerView, layoutParams)
                }
            }
        }

    }



}