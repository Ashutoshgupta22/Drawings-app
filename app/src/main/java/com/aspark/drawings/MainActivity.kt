package com.aspark.drawings

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.aspark.drawings.bottom_sheet.AddDrawingBottomSheet
import com.aspark.drawings.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fabAddDrawing.setOnClickListener {

            showAddDrawingBottomSheet()

        }

    }

    private fun showAddDrawingBottomSheet() {

        val bottomSheetDialog = AddDrawingBottomSheet()
        bottomSheetDialog.show(supportFragmentManager,"AddDrawingBottomSheet")

    }
}