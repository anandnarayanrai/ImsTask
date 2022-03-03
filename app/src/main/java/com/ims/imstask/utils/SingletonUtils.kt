package com.ims.imstask.utils

import androidx.lifecycle.MutableLiveData

class SingletonUtils {

    companion object {

        val actionBarTitle by lazy {
            return@lazy MutableLiveData<String>()
        }

    }
}