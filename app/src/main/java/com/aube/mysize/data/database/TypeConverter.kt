package com.aube.mysize.data.database

import androidx.annotation.Keep
import androidx.room.TypeConverter
import com.aube.mysize.domain.model.clothes.BodyField
import com.aube.mysize.domain.model.clothes.LinkedSizeGroup
import com.aube.mysize.domain.model.clothes.MemoVisibility
import com.aube.mysize.domain.model.clothes.Visibility
import com.aube.mysize.domain.model.size.BodySize
import com.aube.mysize.domain.model.size.SizeEntryType
import com.aube.mysize.presentation.model.recommend.Gender
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Keep
object TypeConverter {
    // region String <-> Set<String>

    @TypeConverter
    fun fromStringSet(set: Set<String>?): String {
        // ex) setOf("a", "b") -> "a,b"
        return set?.joinToString(",") ?: ""
    }

    @TypeConverter
    fun toStringSet(value: String?): Set<String> {
        // ex) "a,b" -> setOf("a", "b")
        return value?.split(",")?.filter { it.isNotBlank() }?.toSet() ?: emptySet()
    }

    // endregion

    // region LocalDateTime <-> String

    @TypeConverter
    fun fromLocalDateTime(dateTime: LocalDateTime?): String? =
        dateTime?.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    // ex) LocalDateTime.of(2024, 6, 1, 12, 0) -> "2024-06-01T12:00:00"

    @TypeConverter
    fun toLocalDateTime(dateTimeStr: String?): LocalDateTime? =
        dateTimeStr?.let { LocalDateTime.parse(it, DateTimeFormatter.ISO_LOCAL_DATE_TIME) }
    // ex) "2024-06-01T12:00:00" -> LocalDateTime

    // endregion

    // region Enum <-> String

    @TypeConverter
    fun fromVisibility(visibility: Visibility): String = visibility.name

    @TypeConverter
    fun toVisibility(name: String): Visibility = Visibility.valueOf(name)

    @TypeConverter
    fun fromMemoVisibility(memoVisibility: MemoVisibility): String = memoVisibility.name

    @TypeConverter
    fun toMemoVisibility(name: String): MemoVisibility = MemoVisibility.valueOf(name)

    @TypeConverter
    fun fromSizeEntryType(value: SizeEntryType?): String? = value?.name

    @TypeConverter
    fun toSizeEntryType(value: String?): SizeEntryType? =
        value?.let { SizeEntryType.valueOf(it) }

    // endregion

    // region BodySize <-> JSON

    @TypeConverter
    fun fromBodySize(bodySize: BodySize?): String? = bodySize?.let { gson.toJson(it) }

    @TypeConverter
    fun toBodySize(data: String?): BodySize? = data?.let { gson.fromJson(it, BodySize::class.java) }

    // endregion

    // region LinkedSizeGroup List <-> JSON

    @TypeConverter
    fun fromLinkedSizeGroupList(value: List<LinkedSizeGroup>?): String? =
        gson.toJson(value)
    // ex) listOf(LinkedSizeGroup(...)) -> JSON string

    @TypeConverter
    fun toLinkedSizeGroupList(value: String?): List<LinkedSizeGroup>? {
        val type = object : TypeToken<List<LinkedSizeGroup>>() {}.type
        return gson.fromJson(value, type)
    }

    // endregion

    // region Gson 초기화

    val gson: Gson = GsonBuilder()
        .registerTypeAdapter(LocalDateTime::class.java, object : JsonDeserializer<LocalDateTime> {
            override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): LocalDateTime {
                return json?.asString?.let { LocalDateTime.parse(it) } ?: LocalDateTime.MIN
            }
        })
        .registerTypeAdapter(LocalDateTime::class.java, object : JsonSerializer<LocalDateTime> {
            override fun serialize(src: LocalDateTime?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
                return JsonPrimitive(src?.toString()) // ISO-8601 형식
            }
        })
        .create()

    // endregion

    // region List -> String

    @TypeConverter
    fun fromStringList(list: List<String>): String = list.joinToString(",")

    @TypeConverter
    fun toStringList(data: String): List<String> =
        if (data.isEmpty()) emptyList() else data.split(",")

    // endregion

    // region Gender -> String

    @TypeConverter
    fun fromGender(gender: Gender): String = gender.name

    @TypeConverter
    fun toGender(value: String): Gender = Gender.valueOf(value)

    // endregion

    // region Set<BodyField> -> String

    @TypeConverter
    fun fromBodyFieldSet(set: Set<BodyField>?): String {
        return set?.joinToString(separator = ",") { it.name } ?: ""
    }

    @TypeConverter
    fun toBodyFieldSet(value: String): Set<BodyField> {
        if (value.isBlank()) return emptySet()
        return value.split(",")
            .mapNotNull { runCatching { BodyField.valueOf(it) }.getOrNull() }
            .toSet()
    }

    // endregion
}
