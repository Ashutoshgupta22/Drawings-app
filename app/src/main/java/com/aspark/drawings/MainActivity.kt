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
import com.aspark.drawings.room.DrawingDao

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: AddDrawingViewModel by viewModels()
    private lateinit var drawingDao: DrawingDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvDrawing.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,false)

        checkStoragePermission()

        drawingDao = getDatabase(applicationContext).drawingDao()
        viewModel.getAllDrawings(drawingDao)

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

        //OnDismissListener is implemented because the observer becomes inactive after
        //bottom sheet is shown, so value can not be updated from viewModel
        // and no lifecycle methods of mainActivity are triggered

        val bottomSheetDialog = AddDrawingBottomSheet {

            registerDrawingListObserver()
            viewModel.getAllDrawings(drawingDao)
        }
        bottomSheetDialog.show(supportFragmentManager,"AddDrawingBottomSheet")
    }

   private fun registerDrawingListObserver() {

       Log.d("MainActivity", "registerDrawingListObserver: called")

       viewModel.drawingList.observe(this) {

           it?.let {

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
   }

    override fun onResume() {
        super.onResume()

        Log.d("MainActivity", "onResume: called")
        registerDrawingListObserver()
        viewModel.getAllDrawings(drawingDao)
    }

}