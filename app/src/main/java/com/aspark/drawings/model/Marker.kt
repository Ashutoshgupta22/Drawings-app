package com.aspark.drawings.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Marker(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val details: String,
    @ColumnInfo(name = "drawing_id") val drawingId: Int
    )