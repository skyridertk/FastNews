package com.tanaka.fastnews.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user-preferences")

class UserPreferences(private val context: Context) {

    val uiFlow: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[DARK_KEY] ?: false
        }

    suspend fun saveDarkMode(boolean: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[DARK_KEY] = boolean
        }
    }

    companion object {

        val DARK_KEY = booleanPreferencesKey("dark_key")
    }
}