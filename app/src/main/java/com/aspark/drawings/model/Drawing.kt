package com.aspark.drawings.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Drawing(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    @ColumnInfo(name = "image_path") val imagePath: String,
    @ColumnInfo(name = "no_of_markers") val markers: Int,
    @ColumnInfo(name = "time_added") val timeAdded: Long
    )