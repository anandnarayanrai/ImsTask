package com.ims.imstask.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ims.imstask.custom.SingleLiveEvent
import com.ims.imstask.retrofit.BackEndApi
import com.ims.imstask.retrofit.BookingSlotsResponseItem
import com.ims.imstask.retrofit.WebServiceClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel(), Callback<List<BookingSlotsResponseItem?>?> {

    private val _responseText = MutableLiveData<String>().apply {
        value = "_responseText"
    }
    val responseText: LiveData<String> = _responseText

    var emptyErrorMessage: SingleLiveEvent<String>? = null

    init {
        WebServiceClient.client.create(BackEndApi::class.java).getBookingSlots().enqueue(this)
    }

    val getMasterResponse = MutableLiveData<List<BookingSlotsResponseItem?>?>()

    override fun onResponse(
        call: Call<List<BookingSlotsResponseItem?>?>,
        response: Response<List<BookingSlotsResponseItem?>?>
    ) {
        if (response.isSuccessful) {
            getMasterResponse.postValue(response.body())
        }
    }
    override fun onFailure(call: Call<List<BookingSlotsResponseItem?>?>, t: Throwable) {
        //progressBar?.value=8
        Log.e("response====", "onFailure")
    }
}