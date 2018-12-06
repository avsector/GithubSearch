package com.samsaz.githubsearch.search

/**
 * Copyright 2018
 * Created and maintained by Hamid Moazzami
 */
sealed class SearchViewEvent

data class TermUpdated(val term: String): SearchViewEvent()