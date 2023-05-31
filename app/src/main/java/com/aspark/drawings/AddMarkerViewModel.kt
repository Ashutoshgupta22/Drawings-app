package com.aspark.drawings

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aspark.drawings.model.Marker
import com.aspark.drawings.repo.DrawingRepository
import com.aspark.drawings.repo.MarkerRepository
import com.aspark.drawings.room.DrawingDao
import com.aspark.drawings.room.MarkerDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddMarkerViewModel: ViewModel() {

    private var mMarkersList = MutableLiveData<List<Marker>>()
    val markersList: LiveData<List<Marker>> = mMarkersList


    fun saveMarker(
        drawingId: Int, title: String, details: String,
        doubleTapX: Float, doubleTapY: Float, count: Int,
        markerDao: MarkerDao,
        drawingDao: DrawingDao) {

        Log.d("FullImageViewModel", "onImageDoubleTapped: $doubleTapX $doubleTapY")

        val marker = Marker(title = title, details = details, drawingId = drawingId,
        doubleTapX = doubleTapX, doubleTapY = doubleTapY)

        val job = viewModelScope.launch(Dispatchers.IO) {

            MarkerRepository(markerDao).insertMarker(marker)
        }

        job.invokeOnCompletion {

            if (job.isCompleted) {

                Log.d(Thread.currentThread().name, "saveMarker: job completed")
                updateMarkerCount(drawingId,count,drawingDao)
                getAllMarkers(drawingId,markerDao)
            }
        }
    }

    private fun updateMarkerCount(drawingId: Int, count: Int, drawingDao: DrawingDao) {

        Log.d("AddMarkerViewModel", "updateMarkerCount: called")
        viewModelScope.launch(Dispatchers.IO) {
            DrawingRepository(drawingDao).updateMarkerCount(drawingId,count)
        }

    }

    fun getAllMarkers(drawingId: Int, markerDao: MarkerDao) {

        Log.d("AddMarkerViewModel", "getAllMarkers: called")

        viewModelScope.launch(Dispatchers.Default) {
            val list =  MarkerRepository(markerDao).getAllMarkers(drawingId)

            withContext(Dispatchers.Main) {
                mMarkersList.postValue(list)
            }
        }

    }


}