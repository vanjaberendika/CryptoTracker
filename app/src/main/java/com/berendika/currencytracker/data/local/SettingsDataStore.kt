package com.berendika.currencytracker.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "settings")

class SettingsDataStore(private val context: Context) {

    private val KEY_DEFAULT_FIAT = stringPreferencesKey("default_fiat")

    val defaultFiat: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[KEY_DEFAULT_FIAT] ?: "USD"
    }

    suspend fun setDefaultFiat(value: String) {
        context.dataStore.edit { prefs ->
            prefs[KEY_DEFAULT_FIAT] = value
        }
    }
}