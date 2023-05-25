package com.aspark.drawings.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.aspark.drawings.model.Marker

@Dao
interface MarkerDao {

    @Insert
    fun insert(marker: Marker)

    @Query("SELECT * FROM marker WHERE drawing_id = :drawingId")
    fun getMarkersForDrawing(drawingId: Int): List<Marker>
}