package com.roycemars.royalgold.app.di

import android.content.Context

/**
 * Dependency Injection container at the application level.
 */
interface AppContainer {
//    val newsRepository: NewsRepository
}

/**
 * Implementation for the Dependency Injection container at the application level.
 *
 * Variables are initialized lazily and the same instance is shared across the whole app.
 */
class AppContainerImpl(private val applicationContext: Context) : AppContainer {

//    override val newsRepository: NewsRepository by lazy {
//        MockNewsRepository()
//    }
}