package com.aspark.drawings.bottom_sheet

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aspark.drawings.model.Drawing
import com.aspark.drawings.repo.DrawingRepository
import com.aspark.drawings.room.DrawingDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddDrawingViewModel: ViewModel() {

    private var mDrawingList = MutableLiveData<List<Drawing>>()
    val drawingList: LiveData<List<Drawing>> = mDrawingList

    fun addDrawing(title: String, imagePath: String, timeAdded: Long,
        drawingDao: DrawingDao) {

        val drawingRepo = DrawingRepository(drawingDao)
        val isVerified = verifyInput(title, imagePath, timeAdded)

       if (isVerified){

           viewModelScope.launch(Dispatchers.IO){

               val drawing = Drawing(name = title, imagePath = imagePath,
                   markers = 0, timeAdded = timeAdded)
               drawingRepo.insertDrawing(drawing)
           }

       }
    }

    fun getAllDrawings(drawingDao: DrawingDao) {

        Log.d("AddDrawingViewModel", "getAllDrawings: called")

        viewModelScope.launch(Dispatchers.Default) {

            val list = DrawingRepository(drawingDao).getAllDrawings()

            //postValue updates livedata in main thread
            mDrawingList.postValue(list)

            Log.d("AddDrawingViewModel", "mDrawingList has observer: " +
                    "${mDrawingList.hasActiveObservers()}")


        }

    }

    private fun verifyInput(title: String, imagePath: String, timeAdded: Long): Boolean {

        return true
    }


}