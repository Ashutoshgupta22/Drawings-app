package com.aspark.drawings

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.aspark.drawings.bottom_sheet.AddDrawingBottomSheet
import com.aspark.drawings.bottom_sheet.AddDrawingViewModel
import com.aspark.drawings.databinding.ActivityMainBinding
import com.aspark.drawings.room.AppDatabase.Companion.getDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: AddDrawingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvDrawing.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,false)

        checkStoragePermission()

        val drawingDao = getDatabase(applicationContext).drawingDao()
        viewModel.getAllDrawings(drawingDao)

        viewModel.drawingList.observe(this) {

            it.let {

                if (it.isNotEmpty()){

                    Log.d("MainActivity", "onCreate: list is not empty ")
                    binding.rvDrawing.adapter = DrawingAdapter(it)
                    binding.tvNoDrawings.visibility = View.INVISIBLE
                }
                else{
                    binding.tvNoDrawings.visibility = View.VISIBLE
                }

            }
        }

        binding.fabAddDrawing.setOnClickListener {

            showAddDrawingBottomSheet()
        }

    }

    private fun checkStoragePermission() {

        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.READ_MEDIA_IMAGES) ==
            PackageManager.PERMISSION_GRANTED) {
            Log.d("MainActivity", "checkStoragePermission: granted ")
        }

        else{

            requestStoragePermission()
        }

    }

    private fun requestStoragePermission() {

        val request = registerForActivityResult(
            ActivityResultContracts.RequestPermission()) { isGranted ->

            if (isGranted) {
                Log.d("MainActivity", "requestStoragePermission: permission granted")
            }
            else
                Log.d("MainActivity", "requestStoragePermission: permission denied")

        }

        request.launch(android.Manifest.permission.READ_MEDIA_IMAGES)

    }

    private fun showAddDrawingBottomSheet() {

        val bottomSheetDialog = AddDrawingBottomSheet()
        bottomSheetDialog.show(supportFragmentManager,"AddDrawingBottomSheet")

    }
}