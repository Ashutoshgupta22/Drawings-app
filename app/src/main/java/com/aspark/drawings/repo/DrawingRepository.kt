package com.aspark.drawings.repo

import com.aspark.drawings.model.Drawing
import com.aspark.drawings.room.DrawingDao

class DrawingRepository(private val drawingDao: DrawingDao) {

    fun insertDrawing(drawing: Drawing) {
        drawingDao.insert(drawing)

    }

    fun getAllDrawings(): List<Drawing> {

       return drawingDao.getAllDrawings()
    }
}