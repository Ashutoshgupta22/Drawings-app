package com.aspark.drawings

import android.content.Intent
import android.os.Bundle
import android.text.format.DateUtils
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.aspark.drawings.databinding.ActivityDrawingProfileBinding
import com.aspark.drawings.model.Drawing
import com.bumptech.glide.Glide

class DrawingProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDrawingProfileBinding
    private val viewModel: DrawingProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDrawingProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val drawing = intent.getParcelableExtra<Drawing>("drawing")

        Glide
            .with(this)
            .load(drawing?.imagePath?.toUri())
            .into(binding.imgDrawing)

        binding.tvDrawingName.text = drawing?.name
        binding.tvMarkers.text = drawing?.markers.toString()
        binding.tvTimeAdded.text = formatTime(drawing?.timeAdded!!)

        binding.imgDrawing.setOnClickListener {

            val intent = Intent(this,FullImageActivity::class.java)
            intent.putExtra("imagePath",drawing.imagePath)
            intent.putExtra("drawingId", drawing.id)
            intent.putExtra("markerCount",drawing.markers)
            startActivity(intent)

        }

    }

    private fun formatTime(millis: Long): String {

        val now = System.currentTimeMillis()

        val formattedTime = DateUtils.getRelativeTimeSpanString(millis, now,
            DateUtils.MINUTE_IN_MILLIS)

        return formattedTime.toString()
    }
}