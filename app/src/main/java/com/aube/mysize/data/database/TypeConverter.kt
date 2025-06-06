package com.aube.mysize.data.database

import androidx.room.TypeConverter
import com.aube.mysize.domain.model.size.BodySize
import com.aube.mysize.presentation.model.MemoVisibility
import com.aube.mysize.presentation.model.Visibility
import com.google.gson.Gson
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

    @TypeConverter
    fun fromVisibility(visibility: Visibility): String = visibility.name

    @TypeConverter
    fun toVisibility(name: String): Visibility = Visibility.valueOf(name)

    @TypeConverter
    fun fromMemoVisibility(memoVisibility: MemoVisibility): String = memoVisibility.name

    @TypeConverter
    fun toMemoVisibility(name: String): MemoVisibility = MemoVisibility.valueOf(name)

    private val gson = Gson()

    @TypeConverter
    fun fromBodySize(bodySize: BodySize?): String? {
        return bodySize?.let { gson.toJson(it) }
    }

    @TypeConverter
    fun toBodySize(data: String?): BodySize? {
        return data?.let { gson.fromJson(it, BodySize::class.java) }
    }

}