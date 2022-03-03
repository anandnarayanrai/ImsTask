package com.ims.imstask.ui.main

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    fun getDateFromDateTime(dateTime: String): String {
        //2021-12-15 08:30:00+00:00
        val sdf = SimpleDateFormat("yyyy-MM-dd hh:mm:ss+00:00", Locale.getDefault())
        var date: Date? = null
        try {
            date = sdf.parse(dateTime)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        val timeFormatter = SimpleDateFormat("hh:mm a", Locale.getDefault())
        return timeFormatter.format(date)
    }

    fun getTimeType(dateTime: String): Int {
        val sdf = SimpleDateFormat("yyyy-MM-dd hh:mm:ss+00:00", Locale.getDefault())
        var date: Date? = null
        try {
            date = sdf.parse(dateTime)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        val timeFormatter = SimpleDateFormat("hh", Locale.getDefault())
        return Integer.parseInt(timeFormatter.format(date))
    }
}