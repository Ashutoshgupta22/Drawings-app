package com.aspark.drawings.repo

import android.util.Log
import com.aspark.drawings.model.Drawing
import com.aspark.drawings.room.DrawingDao
import javax.inject.Inject

class DrawingRepository @Inject constructor(private val drawingDao: DrawingDao) {

    fun insertDrawing(drawing: Drawing) {
        drawingDao.insert(drawing)
    }

    fun getDrawingById(id: Int): Drawing {

        return drawingDao.getDrawingById(id)
    }

    fun getAllDrawings(): List<Drawing> {

       return drawingDao.getAllDrawings()
    }

    fun updateMarkerCount(drawingId: Int, count: Int) {

        Log.i("DrawingRepo", "updateMarkerCount: drawingId= $drawingId, " +
                "count= $count")
        drawingDao.updateMarkerCount(drawingId,count)
    }
}