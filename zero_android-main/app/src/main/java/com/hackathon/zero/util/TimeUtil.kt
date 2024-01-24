package com.hackathon.zero.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

object TimeUtil {

    fun isToday(date: Date) : Boolean {
        val now = Calendar.getInstance().time
        val dateFormat = "yyyyMMdd"
        val simpleDateFormat = SimpleDateFormat(dateFormat)
        return simpleDateFormat.format(now) == simpleDateFormat.format(date)
    }
}