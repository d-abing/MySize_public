package com.aube.mysize.data.database

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object TypeConverter {
    @TypeConverter
    fun fromMap(map: Map<String, Int>): String =
        map.entries.joinToString(";") { "${it.key}:${it.value}" }

    @TypeConverter
    fun toMap(data: String): Map<String, Int> =
        if (data.isBlank()) emptyMap()
        else data.split(";").associate {
            val (key, value) = it.split(":")
            key to value.toInt()
        }

    @TypeConverter
    fun fromString(value: String?): Set<String> {
        return value?.split(",")?.filter { it.isNotBlank() }?.toSet() ?: emptySet()
    }

    @TypeConverter
    fun toString(set: Set<String>?): String {
        return set?.joinToString(",") ?: ""
    }

    @TypeConverter
    fun fromLocalDateTime(dateTime: LocalDateTime?): String? =
        dateTime?.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)

    @TypeConverter
    fun toLocalDateTime(dateTimeStr: String?): LocalDateTime? =
        dateTimeStr?.let { LocalDateTime.parse(it, DateTimeFormatter.ISO_LOCAL_DATE_TIME) }

    @TypeConverter
    fun fromTimestamp(value: String?): LocalDate? {
        return value?.let { LocalDate.parse(it) }
    }

    @TypeConverter
    fun toTimestamp(date: LocalDate?): String? {
        return date?.toString()
    }
}