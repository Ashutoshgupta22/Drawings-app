package com.aspark.drawings.repo

import com.aspark.drawings.model.Marker
import com.aspark.drawings.room.MarkerDao
import javax.inject.Inject

class MarkerRepository @Inject constructor(private val markerDao: MarkerDao) {

    fun insertMarker(marker: Marker) {

        markerDao.insert(marker)
    }

    fun getAllMarkers(drawingId: Int): List<Marker> {

        return markerDao.getMarkersForDrawing(drawingId)
    }
}