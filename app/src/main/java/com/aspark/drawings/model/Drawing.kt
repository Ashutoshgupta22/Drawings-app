package com.aspark.drawings.model

import android.os.Parcel
import android.os.Parcelable
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
    ): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readLong()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(imagePath)
        parcel.writeInt(markers)
        parcel.writeLong(timeAdded)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Drawing> {
        override fun createFromParcel(parcel: Parcel): Drawing {
            return Drawing(parcel)
        }

        override fun newArray(size: Int): Array<Drawing?> {
            return arrayOfNulls(size)
        }
    }
}