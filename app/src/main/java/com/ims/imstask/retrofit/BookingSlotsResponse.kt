package com.ims.imstask.retrofit

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class BookingSlotsResponseItem(

    @field:SerializedName("start_time")
    val startTime: String? = null,

    @field:SerializedName("end_time")
    val endTime: String? = null,

    @field:SerializedName("is_booked")
    val isBooked: Boolean? = null
) : Parcelable

data class BookingSlotsParentItem(
    val header: String,
    val slotsAvailable: Int,
    val childItem: List<BookingSlotsChildItem?>
)

data class BookingSlotsChildItem(
    val timeSlots: String,
    val isBooked: Boolean
)