package com.roycemars.royalgold.core.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.roycemars.royalgold.core.data.ThemePreferencesRepository
import com.roycemars.royalgold.core.ui.theme.AppThemeIdentifier
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    application: Application,
    private val themePreferencesRepository: ThemePreferencesRepository
): AndroidViewModel(application) {
    val currentThemeIdentifier: StateFlow<AppThemeIdentifier> = themePreferencesRepository.selectedThemeIdentifier
        .stateIn(scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = AppThemeIdentifier.DEFAULT)
    fun updateThemeIdentifier(identifier: AppThemeIdentifier) {
        viewModelScope.launch {
            themePreferencesRepository.setSelectedThemeIdentifier(identifier)
        }
    }
}