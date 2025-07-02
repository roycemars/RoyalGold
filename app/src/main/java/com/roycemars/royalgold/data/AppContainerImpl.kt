package com.roycemars.royalgold.data

import android.content.Context
import com.roycemars.royalgold.data.news.NewsRepository
import com.roycemars.royalgold.data.news.impl.MockNewsRepository

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