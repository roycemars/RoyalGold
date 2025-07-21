package com.roycemars.royalgold.core.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import java.util.prefs.Preferences

data class UserPreferences(val showCompleted: Boolean)

private const val USER_PREFERENCES_NAME = "user_preferences"

private val Context.dataStore by preferencesDataStore(
    name = USER_PREFERENCES_NAME
)

class UserPreferencesRepository(
    private val dataStore: DataStore<Preferences>,
    context: Context
) {  }