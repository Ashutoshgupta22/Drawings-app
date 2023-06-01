package com.aspark.drawings.utility

import android.text.format.DateUtils

class Utility {

     fun formatTime(millis: Long): String {

        val now = System.currentTimeMillis()

        val formattedTime = DateUtils.getRelativeTimeSpanString(millis, now,
            DateUtils.MINUTE_IN_MILLIS)

        return formattedTime.toString()
    }
}