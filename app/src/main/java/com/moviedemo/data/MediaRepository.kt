package com.moviedemo.data

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

val defaultPagingConfig = PagingConfig(
    pageSize = 3,
    enablePlaceholders = false
)

@Singleton
class MediaRepository @Inject constructor(    @ApplicationContext private val context: Context){

    fun getSearchResults(query: String) =
        Pager(
            config = defaultPagingConfig,
            pagingSourceFactory = { PopularMoviesPagingSource(context!!, query) }
        ).liveData

}