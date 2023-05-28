package com.aspark.drawings.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.aspark.drawings.model.Drawing

@Dao
interface DrawingDao {

    @Insert
    fun insert(drawing: Drawing)

    @Query("SELECT * from drawing")
    fun getAllDrawings() : List<Drawing>

    @Query("UPDATE drawing SET no_of_markers = :count WHERE id = :drawingId ")
    fun updateMarkerCount(drawingId: Int, count: Int)
}