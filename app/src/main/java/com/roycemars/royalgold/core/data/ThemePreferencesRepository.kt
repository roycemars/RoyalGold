package com.roycemars.royalgold.core.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.roycemars.royalgold.core.ui.theme.AppThemeIdentifier // Use the new enum
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.themeIdentifierDataStore: DataStore<Preferences> by preferencesDataStore(name = "app_theme_settings")

class ThemePreferencesRepository @Inject constructor(@ApplicationContext private val context: Context) {

    private object PreferencesKeys {
        val SELECTED_THEME_IDENTIFIER = stringPreferencesKey("selected_theme_identifier")
        val USE_DYNAMIC_THEME = booleanPreferencesKey("use_dynamic_theme")
    }

    val selectedThemeIdentifier: Flow<AppThemeIdentifier> = context.themeIdentifierDataStore.data
        .map { preferences ->
            val themeName = preferences[PreferencesKeys.SELECTED_THEME_IDENTIFIER] ?: AppThemeIdentifier.DEFAULT.name
            try {
                AppThemeIdentifier.valueOf(themeName)
            } catch (e: IllegalArgumentException) {
                AppThemeIdentifier.DEFAULT // Fallback
            }
        }

    suspend fun setSelectedThemeIdentifier(identifier: AppThemeIdentifier) {
        context.themeIdentifierDataStore.edit { preferences ->
            preferences[PreferencesKeys.SELECTED_THEME_IDENTIFIER] = identifier.name
        }
    }
}