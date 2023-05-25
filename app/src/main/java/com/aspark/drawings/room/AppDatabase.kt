package com.aspark.drawings.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aspark.drawings.model.Drawing
import com.aspark.drawings.model.Marker

@Database(entities = [Drawing::class, Marker::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun drawingDao() : DrawingDao
    abstract fun markerDao() : MarkerDao

}