package com.aube.mysize.presentation.ui.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
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
}
