package com.aube.mysize.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.aube.mysize.presentation.model.recommend.AgeGroup
import com.aube.mysize.presentation.model.recommend.Gender
import com.aube.mysize.presentation.model.recommend.PriceRange
import com.aube.mysize.presentation.model.recommend.Style
import com.aube.mysize.presentation.model.recommend.UserPreference
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@Singleton
class SettingsDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val LANGUAGE_KEY = stringPreferencesKey("language")

    suspend fun saveLanguage(language: String) {
        context.dataStore.edit { settings ->
            settings[LANGUAGE_KEY] = language
        }
    }

    fun getLanguage(): Flow<String> {
        return context.dataStore.data
            .map { preferences ->
                preferences[LANGUAGE_KEY] ?: "ko"
            }
    }

    private val BODY_FIELDS_KEY = stringPreferencesKey("body_fields")

    suspend fun saveBodyFields(bodyFields: Set<String>) {
        context.dataStore.edit { settings ->
            settings[BODY_FIELDS_KEY] = bodyFields.joinToString(",")
        }
    }

    fun getBodyFields(): Flow<Set<String>> {
        return context.dataStore.data
            .map { preferences ->
                preferences[BODY_FIELDS_KEY]?.split(",")?.toSet() ?: emptySet()
            }
    }

    private val IS_BODY_SIZE_CARD_STICKY_KEY = booleanPreferencesKey("is_body_size_card_sticky")

    suspend fun saveIsBodySizeCardSticky(value: Boolean) {
        context.dataStore.edit { settings ->
            settings[IS_BODY_SIZE_CARD_STICKY_KEY] = value
        }
    }

    fun getIsBodySizeCardSticky(): Flow<Boolean> {
        return context.dataStore.data
            .map { preferences ->
                preferences[IS_BODY_SIZE_CARD_STICKY_KEY] ?: false
            }
    }

    private val IS_BODY_SIZE_CARD_EXPANDED_KEY = booleanPreferencesKey("is_body_size_card_expanded")

    suspend fun saveIsBodySizeCardExpanded(value: Boolean) {
        context.dataStore.edit { settings ->
            settings[IS_BODY_SIZE_CARD_EXPANDED_KEY] = value
        }
    }

    fun getIsBodySizeCardExpanded(): Flow<Boolean> {
        return context.dataStore.data
            .map { preferences ->
                preferences[IS_BODY_SIZE_CARD_EXPANDED_KEY] ?: true
            }
    }

    private val IS_BODY_SIZE_REVEALED_KEY = booleanPreferencesKey("is_body_size_revealed")

    suspend fun saveIsBodySizeRevealed(value: Boolean) {
        context.dataStore.edit { settings ->
            settings[IS_BODY_SIZE_REVEALED_KEY] = value
        }
    }

    fun getIsBodySizeRevealed(): Flow<Boolean> {
        return context.dataStore.data
            .map { preferences ->
                preferences[IS_BODY_SIZE_REVEALED_KEY] ?: true
            }
    }

    object PreferenceKeys {
        val GENDER = stringPreferencesKey("gender")
        val STYLE = stringPreferencesKey("style")
        val AGE = stringPreferencesKey("age")
        val PRICE = stringPreferencesKey("price")
    }

    suspend fun saveUserPreference(value: UserPreference) {
        context.dataStore.edit { prefs ->
            prefs[PreferenceKeys.GENDER] = value.gender.name
            prefs[PreferenceKeys.STYLE] = value.styles.joinToString(",")
            prefs[PreferenceKeys.AGE] = value.ageGroups.joinToString(",")
            prefs[PreferenceKeys.PRICE] = value.priceRanges.joinToString(",")
        }
    }

    fun getUserPreference(): Flow<UserPreference?> {
        return context.dataStore.data.map { prefs ->
            val genderString = prefs[PreferenceKeys.GENDER]
            val styleString = prefs[PreferenceKeys.STYLE]
            val ageString = prefs[PreferenceKeys.AGE]
            val priceString = prefs[PreferenceKeys.PRICE]

            val gender = Gender.entries.find { it.name == genderString }

            val styles = styleString
                ?.split(",")
                ?.mapNotNull { raw -> Style.entries.find { it.name == raw } }

            val ageGroup = ageString
                ?.split(",")
                ?.mapNotNull { raw -> AgeGroup.entries.find { it.name == raw } }
            val priceRange = priceString
                ?.split(",")
                ?.mapNotNull { raw -> PriceRange.entries.find { it.name == raw } }

            if (gender != null && !styles.isNullOrEmpty() && !ageGroup.isNullOrEmpty() && !priceRange.isNullOrEmpty()) {
                UserPreference(gender, styles, ageGroup, priceRange)
            } else {
                null
            }
        }
    }

    private val LAST_SHOP_SYNC_TIME_KEY = stringPreferencesKey("last_shop_sync_time")

    suspend fun saveLastShopSyncTime(timestamp: Long = System.currentTimeMillis()) {
        context.dataStore.edit { prefs ->
            prefs[LAST_SHOP_SYNC_TIME_KEY] = timestamp.toString()
        }
    }

    fun getLastShopSyncTime(): Flow<Long> {
        return context.dataStore.data.map { prefs ->
            prefs[LAST_SHOP_SYNC_TIME_KEY]?.toLongOrNull() ?: 0L
        }
    }

    suspend fun shouldRefreshRecommendedShops(): Boolean {
        val lastTime = getLastShopSyncTime().first()
        val lastDate = Instant.ofEpochMilli(lastTime).atZone(ZoneId.systemDefault()).toLocalDate()
        val now = LocalDate.now()

        return now.year != lastDate.year || now.month != lastDate.month
    }
}
