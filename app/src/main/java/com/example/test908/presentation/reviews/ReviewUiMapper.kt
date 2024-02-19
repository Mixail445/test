package com.example.test908.presentation.reviews

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.example.test908.R
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ReviewUiMapper
    @Inject
    constructor(
        @ApplicationContext val context: Context,
    ) {
        private val drawableFavorite by lazy {
            ContextCompat.getDrawable(context, R.drawable.baseline_favorite_24)
        }
        private val drawableNotFavorite by lazy {
            ContextCompat.getDrawable(context, R.drawable.baseline_favorite_border_24)
        }

        fun convertTimer(time: Long): String? {
            val min = TimeUnit.SECONDS.toMinutes(time) % 60
            val sec = time % 60
            val hour = TimeUnit.SECONDS.toHours(time) % 24
            val day = TimeUnit.SECONDS.toDays(time)
            return if (min.toInt() == 0 && hour.toInt() == 0 && day.toInt() == 0) {
                context.resources?.getQuantityString(R.plurals.timer_sec, sec.toInt(), sec)
            } else if (hour.toInt() != 0 && day.toInt() == 0) {
                " ${
                    context.resources?.getQuantityString(
                        R.plurals.timer_hours,
                        hour.toInt(),
                        hour,
                    )
                } ${
                    context.resources?.getQuantityString(
                        R.plurals.timer_minutes,
                        min.toInt(),
                        min,
                    )
                }"
            } else if (day.toInt() != 0) {
                "${context.resources?.getQuantityString(R.plurals.timer_days, day.toInt(), day)} ${
                    context.resources?.getQuantityString(
                        R.plurals.timer_hours,
                        hour.toInt(),
                        hour,
                    )
                }"
            } else {
                "${context.resources?.getQuantityString(R.plurals.timer_minutes, min.toInt(), min)} ${
                    context.resources?.getQuantityString(
                        R.plurals.timer_sec,
                        sec.toInt(),
                        sec,
                    )
                }"
            }
        }

        fun getDrawable(isFavorite: Boolean): Drawable? {
            return if (isFavorite) {
                drawableFavorite
            } else {
                drawableNotFavorite
            }
        }
    }
