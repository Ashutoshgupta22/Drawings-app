package com.aspark.drawings.bottom_sheet

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aspark.drawings.model.Drawing
import com.aspark.drawings.repo.DrawingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddDrawingViewModel @Inject constructor(
    private val drawingRepo: DrawingRepository) : ViewModel() {

    private var mDrawingList = MutableLiveData<List<Drawing>>()
    val drawingList: LiveData<List<Drawing>> = mDrawingList

    fun addDrawing(title: String, imagePath: String, timeAdded: Long) {

        val isVerified = verifyInput(title, imagePath, timeAdded)

       if (isVerified){

           val job = viewModelScope.launch(Dispatchers.IO){

               val drawing = Drawing(name = title, imagePath = imagePath,
                   markers = 0, timeAdded = timeAdded)
               drawingRepo.insertDrawing(drawing)
           }

           job.invokeOnCompletion {
               if (job.isCompleted)
                   getAllDrawings()
           }

       }
    }

    fun getAllDrawings() {

        Log.d("AddDrawingViewModel", "getAllDrawings: called")

        viewModelScope.launch(Dispatchers.Default) {

            val list = drawingRepo.getAllDrawings()

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