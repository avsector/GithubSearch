package com.samsaz.githubsearch.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Copyright 2018
 * Created and maintained by Hamid Moazzami
 */

class SearchViewModel: ViewModel() {

    val stateLiveData = MutableLiveData<SearchViewState>()

}