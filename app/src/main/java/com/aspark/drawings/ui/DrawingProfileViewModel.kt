package com.aspark.drawings.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aspark.drawings.model.Drawing
import com.aspark.drawings.repo.DrawingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DrawingProfileViewModel @Inject constructor(
    private val drawingRepo: DrawingRepository): ViewModel() {

    private var _drawing = MutableLiveData<Drawing>()
    val drawing: LiveData<Drawing> = _drawing

     fun getDrawingById(drawingId: Int) {

         //used async await just to try it
       val deferred = viewModelScope.async(Dispatchers.IO) {

           drawingRepo.getDrawingById(drawingId)
        }

         viewModelScope.launch(Dispatchers.Default) {

            Log.i("DrawingProfileViewModel", "getDrawingById: success")
             _drawing.postValue(deferred.await())

         }
     }
}