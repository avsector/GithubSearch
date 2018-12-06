package com.samsaz.githubsearch.search

import com.samsaz.githubsearch.model.Repo

/**
 * Copyright 2018
 * Created and maintained by Hamid Moazzami
 */

sealed class SearchViewState {
    object Initial : SearchViewState()
    object Progress : SearchViewState()
    object Empty : SearchViewState()
    data class Success(val repoList: List<Repo>) : SearchViewState()
    data class Error(val message: String) : SearchViewState()
}