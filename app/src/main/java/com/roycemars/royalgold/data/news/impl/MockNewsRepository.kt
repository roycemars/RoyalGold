package com.roycemars.royalgold.data.news.impl

import com.roycemars.royalgold.data.news.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext

/**
 * Implementation of NewsRepository that returns a hardcoded list of
 * news with resources after some delay in a background thread.
 */
class MockNewsRepository: NewsRepository {

}