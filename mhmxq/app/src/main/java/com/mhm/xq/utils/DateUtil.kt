package com.mhm.xq.utils

import java.text.SimpleDateFormat
import java.util.*


class DateUtil {
    companion object {

        var hours: String = "HH"
        var months: String = "MM:dd"

        fun timeStampformatDate(time: Long) {
            var format = SimpleDateFormat()
            format.parse(format.format(time))
            var date = Date()
            date.toString()
        }
    }
}