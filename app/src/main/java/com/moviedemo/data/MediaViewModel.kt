package com.moviedemo.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MediaViewModel @Inject constructor(val mediaRepository: MediaRepository) : ViewModel() {

    public val currentQuery = MutableLiveData("")

    val result = currentQuery.switchMap { queryString ->
        mediaRepository.getSearchResults(queryString).cachedIn(viewModelScope)
    }

    fun searchResults(query: String) {
        currentQuery.value = query
    }

}