package com.aspark.drawings.bottom_sheet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aspark.drawings.model.Drawing
import com.aspark.drawings.repo.DrawingRepository
import com.aspark.drawings.room.DrawingDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
          // getAllDrawings(drawingDao)
       }
    }

    fun getAllDrawings(drawingDao: DrawingDao) {

        val drawingRepo = DrawingRepository(drawingDao)

        viewModelScope.launch(Dispatchers.IO) {

            val list = drawingRepo.getAllDrawings()

            withContext(Dispatchers.Main){
                mDrawingList.value = list
            }
        }
    }

    private fun verifyInput(title: String, imagePath: String, timeAdded: Long): Boolean {

        return true
    }


}