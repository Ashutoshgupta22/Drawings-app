package com.aspark.drawings

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.aspark.drawings.databinding.ActivityFullImageBinding
import com.bumptech.glide.Glide

class FullImageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFullImageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFullImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imagePath = intent.getStringExtra("imagePath")

        Glide
            .with(this)
            .load(imagePath?.toUri())
            .into(binding.ivFullImage)


    }
}