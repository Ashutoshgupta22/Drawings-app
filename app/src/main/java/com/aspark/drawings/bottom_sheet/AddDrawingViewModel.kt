package com.aspark.drawings.bottom_sheet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aspark.drawings.model.Drawing
import com.aspark.drawings.repo.DrawingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddDrawingViewModel: ViewModel() {

    fun addDrawing(title: String, imagePath: String, timeAdded: Long,
        drawingRepo: DrawingRepository) {

        val isVerified = verifyInput(title,imagePath,timeAdded)

       if (isVerified){

           viewModelScope.launch(Dispatchers.IO){

               val drawing = Drawing(name = title, imagePath = imagePath,
                   timeAdded = timeAdded)
               drawingRepo.insertDrawing(drawing)
           }


       }

    }

    private fun verifyInput(title: String, imagePath: String, timeAdded: Long): Boolean {

        return true
    }


}