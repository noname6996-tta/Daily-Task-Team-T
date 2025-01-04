package com.tta.core_utils.uitls
import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
object TimeUtils {

    fun currentTimeString(format: String = "HH_mm"): String {
        val sdf = SimpleDateFormat(format, Locale.getDefault())
        return sdf.format(Date())
    }

    fun millSecToString(millSec: Long, format: String): String {
        val sdf = SimpleDateFormat(format, Locale.getDefault())
        return sdf.format(millSec)
    }

    @SuppressLint("DefaultLocale")
    fun millSecFormatTime(time: Long?, isTakeHour: Boolean = false): String {
        if (time == null) return ""

        val totalSec = time / 1000
        val hour = totalSec / 3600
        val min = (totalSec % 3600) / 60
        val sec = totalSec % 60

        return if (isTakeHour) {
            String.format("%02d:%02d:%02d", hour, min, sec)
        } else {
            if (hour > 0) {
                String.format("%02d:%02d:%02d", hour, min, sec)
            } else {
                String.format("%02d:%02d", min, sec)
            }
        }
    }
}