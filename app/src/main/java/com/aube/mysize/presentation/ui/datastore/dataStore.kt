package com.aube.mysize.presentation.ui.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.aube.mysize.presentation.model.AgeGroup
import com.aube.mysize.presentation.model.Gender
import com.aube.mysize.presentation.model.PriceRange
import com.aube.mysize.presentation.model.Style
import com.aube.mysize.presentation.model.UserPreference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "settings")

object SettingsDataStore {

    private val LANGUAGE_KEY = stringPreferencesKey("language")

    suspend fun saveLanguage(context: Context, language: String) {
        context.dataStore.edit { settings ->
            settings[LANGUAGE_KEY] = language
        }
    }

    fun getLanguage(context: Context): Flow<String> {
        return context.dataStore.data
            .map { preferences ->
                preferences[LANGUAGE_KEY] ?: "ko"
            }
    }

    private val BODY_FIELDS_KEY = stringPreferencesKey("body_fields")

    suspend fun saveBodyFields(context: Context, bodyFields: Set<String>) {
        context.dataStore.edit { settings ->
            settings[BODY_FIELDS_KEY] = bodyFields.joinToString(",")
        }
    }

    fun getBodyFields(context: Context): Flow<Set<String>> {
        return context.dataStore.data
            .map { preferences ->
                preferences[BODY_FIELDS_KEY]?.split(",")?.toSet() ?: emptySet()
            }
    }

    private val IS_BODY_SIZE_CARD_STICKY_KEY = booleanPreferencesKey("is_body_size_card_sticky")

    suspend fun saveIsBodySizeCardSticky(context: Context, value: Boolean) {
        context.dataStore.edit { settings ->
            settings[IS_BODY_SIZE_CARD_STICKY_KEY] = value
        }
    }

    fun getIsBodySizeCardSticky(context: Context): Flow<Boolean> {
        return context.dataStore.data
            .map { preferences ->
                preferences[IS_BODY_SIZE_CARD_STICKY_KEY] ?: false
            }
    }

    private val IS_BODY_SIZE_CARD_EXPANDED_KEY = booleanPreferencesKey("is_body_size_card_expanded")

    suspend fun saveIsBodySizeCardExpanded(context: Context, value: Boolean) {
        context.dataStore.edit { settings ->
            settings[IS_BODY_SIZE_CARD_EXPANDED_KEY] = value
        }
    }

    fun getIsBodySizeCardExpanded(context: Context): Flow<Boolean> {
        return context.dataStore.data
            .map { preferences ->
                preferences[IS_BODY_SIZE_CARD_EXPANDED_KEY] ?: true
            }
    }

    private val IS_BODY_SIZE_REVEALED_KEY = booleanPreferencesKey("is_body_size_revealed")

    suspend fun saveIsBodySizeRevealed(context: Context, value: Boolean) {
        context.dataStore.edit { settings ->
            settings[IS_BODY_SIZE_REVEALED_KEY] = value
        }
    }

    fun getIsBodySizeRevealed(context: Context): Flow<Boolean> {
        return context.dataStore.data
            .map { preferences ->
                preferences[IS_BODY_SIZE_REVEALED_KEY] ?: false
            }
    }

    object PreferenceKeys {
        val GENDER = stringPreferencesKey("gender")
        val STYLE = stringPreferencesKey("style")
        val AGE = stringPreferencesKey("age")
        val PRICE = stringPreferencesKey("price")
    }

    suspend fun saveUserPreference(context: Context, value: UserPreference) {
        context.dataStore.edit { prefs ->
            prefs[PreferenceKeys.GENDER] = value.gender.name
            prefs[PreferenceKeys.STYLE] = value.styles.joinToString(",")
            prefs[PreferenceKeys.AGE] = value.ageGroups.joinToString(",")
            prefs[PreferenceKeys.PRICE] = value.priceRanges.joinToString(",")
        }
    }

    suspend fun clearUserPreference(context: Context) {
        context.dataStore.edit { prefs ->
            prefs.remove(PreferenceKeys.GENDER)
            prefs.remove(PreferenceKeys.STYLE)
            prefs.remove(PreferenceKeys.AGE)
            prefs.remove(PreferenceKeys.PRICE)
        }
    }

    fun getUserPreference(context: Context): Flow<UserPreference?> {
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
}
