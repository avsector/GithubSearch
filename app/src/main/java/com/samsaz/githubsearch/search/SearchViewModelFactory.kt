package com.samsaz.githubsearch.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

/**
 * Copyright 2018
 * Created and maintained by Hamid Moazzami
 */

class SearchViewModelFactory @Inject constructor() : ViewModelProvider.Factory {

    @Inject lateinit var searchViewModel: SearchViewModel

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            searchViewModel as T
        } else {
            throw IllegalArgumentException(
                "Class ${modelClass.name} is not supported in this factory."
            )
        }
    }
}