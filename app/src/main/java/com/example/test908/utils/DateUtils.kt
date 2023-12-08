package com.example.test908.utils

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

fun LocalDateTime.format(pattern: String): String? {
    val formatter = DateTimeFormatter.ofPattern(pattern)
    return format(formatter)
}

fun LocalDateTime.toEpochMillis() =
    ZonedDateTime.of(this, ZoneId.systemDefault()).toInstant().toEpochMilli()

object DateUtils {
    const val CALENDAR_UI_FORMAT = "yyyy/MM/dd"
    const val CALENDAR_UI_ITEM_FORMAT = "yyyy/MM/dd   mm:ss:mm"
    fun parseLocalDateTime(date: String?) = try {
        LocalDateTime.parse(date, DateTimeFormatter.ISO_DATE_TIME)
    } catch (e: Exception) {
        e
        null
    }

    fun parseLocalDateTime(date: Long) = try {
        LocalDateTime.ofInstant(Instant.ofEpochMilli(date), ZoneId.systemDefault())
    } catch (e: Exception) {
        e
        null
    }

    fun getCalendarUiDate(firstDate: LocalDateTime?, secondDate: LocalDateTime?): String {
        return if (firstDate == null || secondDate == null) {
            return ""
        } else {
            firstDate.format(CALENDAR_UI_FORMAT).orEmpty() + " - " +
                    secondDate.format(CALENDAR_UI_FORMAT).orEmpty()
        }
    }
}