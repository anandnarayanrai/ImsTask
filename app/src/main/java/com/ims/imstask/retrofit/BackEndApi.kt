package com.ims.imstask.retrofit

import retrofit2.Call

import retrofit2.http.GET

interface BackEndApi {
    @GET("v2/booking/slots/all/")
    fun getBookingSlots(
    ): Call<List<BookingSlotsResponseItem?>?>
}