package com.aspark.drawings

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aspark.drawings.model.Drawing
import com.aspark.drawings.repo.DrawingRepository
import com.aspark.drawings.room.DrawingDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class DrawingProfileViewModel: ViewModel() {

    private var _drawing = MutableLiveData<Drawing>()
    val drawing: LiveData<Drawing> = _drawing

     fun getDrawingById(drawingId: Int, drawingDao: DrawingDao) {

         //used async await just to try it
       val deferred = viewModelScope.async(Dispatchers.IO) {

           DrawingRepository(drawingDao).getDrawingById(drawingId)
        }

         viewModelScope.launch(Dispatchers.Default) {

            Log.i("DrawingProfileViewModel", "getDrawingById: success")
             _drawing.postValue(deferred.await())

         }
     }
}