package com.example.test908.presentation.reviews

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.example.test908.R
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ReviewUiMapper @Inject constructor(@ApplicationContext val context: Context) {
    fun convertTimer(time: Long): String? {
        val min = TimeUnit.SECONDS.toMinutes(time)
        val sec = time % 60
        val hour = TimeUnit.SECONDS.toHours(time) % 24
        val day = TimeUnit.DAYS.toDays(time) / time
        return if (min.toInt() == 0 && hour.toInt() == 0) {
            context.resources?.getQuantityString(R.plurals.timer_sec, sec.toInt(), sec)
        } else if (hour.toInt() != 0 && min.toInt() == 0 && sec.toInt() != 0) {
            context.resources?.getQuantityString(R.plurals.timer_hours, hour.toInt(), hour)
        } else if (day.toInt() != 0 && hour.toInt() != 0) {
            "${context.resources?.getQuantityString(R.plurals.timer_days,day.toInt(),day)} ${
                context.resources?.getQuantityString(
                R.plurals.timer_hours,
                hour.toInt(),
                hour
            )}"
        } else {
            "${context.resources?.getQuantityString(R.plurals.timer_minutes,min.toInt(),min)} ${
                context.resources?.getQuantityString(
                R.plurals.timer_sec,
                sec.toInt(),
                sec
            )}"
        } }

    fun checkFlagForAdapter(isFavorite: Boolean): Drawable? {
       return if (isFavorite) {
           ContextCompat.getDrawable(context, R.drawable.baseline_favorite_24)
       } else {
           ContextCompat.getDrawable(context, R.drawable.baseline_favorite_border_24)
       }
    }
}


