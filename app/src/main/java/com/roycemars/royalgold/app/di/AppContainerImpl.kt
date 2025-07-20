package com.roycemars.royalgold.app.di

import android.content.Context
import com.roycemars.royalgold.feature.market.data.NewsRepository
import com.roycemars.royalgold.feature.market.data.impl.MockNewsRepository

/**
 * Dependency Injection container at the application level.
 */
interface AppContainer {
    val newsRepository: NewsRepository
}

/**
 * Implementation for the Dependency Injection container at the application level.
 *
 * Variables are initialized lazily and the same instance is shared across the whole app.
 */
class AppContainerImpl(private val applicationContext: Context) : AppContainer {

    override val newsRepository: NewsRepository by lazy {
        MockNewsRepository()
    }
}