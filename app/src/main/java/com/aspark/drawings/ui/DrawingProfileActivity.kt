package com.aspark.drawings.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.aspark.drawings.databinding.ActivityDrawingProfileBinding
import com.aspark.drawings.model.Drawing
import com.aspark.drawings.room.AppDatabase.Companion.getDatabase
import com.aspark.drawings.utility.Utility
import com.bumptech.glide.Glide

class DrawingProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDrawingProfileBinding
    private val viewModel: DrawingProfileViewModel by viewModels()
    private var drawingId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDrawingProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        drawingId = intent.getIntExtra("drawingId",-1)

    }

    override fun onResume() {
        super.onResume()

        if (drawingId != -1) {

            registerObserver()
            val drawingDao = getDatabase(applicationContext).drawingDao()
            viewModel.getDrawingById(drawingId, drawingDao)

        }
        else
            Log.e("DrawingProfileActivity", "onResume: drawingId = -1" )
    }

    private fun registerObserver() {

        viewModel.drawing.observe(this) {

            it?.let {

                loadImage(it)
                setTexts(it)
                setClickListeners(it)
            }
        }

    }

    private fun setClickListeners(drawing: Drawing) {

        binding.imgDrawing.setOnClickListener {

            val intent = Intent(this, FullImageActivity::class.java)
            intent.putExtra("imagePath", drawing.imagePath)
            intent.putExtra("drawingId", drawing.id)
            intent.putExtra("markerCount", drawing.markers)
            startActivity(intent)
        }
    }

    private fun setTexts(it: Drawing) {

        binding.tvDrawingName.text = it.name
        binding.tvMarkers.text = it.markers.toString()
        binding.tvTimeAdded.text = Utility().formatTime(it.timeAdded)

    }

    private fun loadImage(it: Drawing) {

        Glide
            .with(this)
            .load(it.imagePath.toUri())
            .into(binding.imgDrawing)
    }
}