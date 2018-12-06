package com.samsaz.githubsearch.search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.samsaz.githubsearch.util.checkAllMatched

/**
 * Copyright 2018
 * Created and maintained by Hamid Moazzami
 */

class SearchViewModel: ViewModel() {

    val stateLiveData = MutableLiveData<SearchViewState>()

    fun onEvent(event: SearchViewEvent) {
        when (event) {
            is TermUpdated -> {
                termUpdated(event.term)
            }
        }.checkAllMatched
    }

    private fun termUpdated(term: String) {
        //Hit the network
        Log.d("SearchViewModel", "Term updated: $term")
    }
}