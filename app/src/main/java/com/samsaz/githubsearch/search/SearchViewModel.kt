package com.samsaz.githubsearch.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.samsaz.githubsearch.data.CoroutineDispatcherProvider
import com.samsaz.githubsearch.data.GithubRepository
import com.samsaz.githubsearch.util.Result
import com.samsaz.githubsearch.util.TlsProviderInstaller
import com.samsaz.githubsearch.util.checkAllMatched
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.net.ssl.SSLHandshakeException

/**
 * Copyright 2018
 * Created and maintained by Hamid Moazzami
 */

class SearchViewModel @Inject constructor(
    private val githubRepository: GithubRepository,
    private val dispatcherProvider: CoroutineDispatcherProvider,
    private val tlsProviderInstaller: TlsProviderInstaller? = null
) : ViewModel() {

    private val parentJob = Job()
    private val scope = CoroutineScope(dispatcherProvider.main + parentJob)
    private var lastSearchJob: Job? = null

    val stateLiveData = MutableLiveData<SearchViewState>()

    fun onEvent(event: SearchViewEvent) {
        when (event) {
            is TermUpdated -> {
                termUpdated(event.term)
            }
        }.checkAllMatched
    }

    private fun termUpdated(term: String) {
        lastSearchJob?.cancel()
        if (term.length < 2) {
            stateLiveData.value = SearchViewState.Initial
            return
        }

        lastSearchJob = scope.launch {
            //Introducing a delay here to debounce intermediate search terms
            delay(SEARCH_DEBOUNCE_DURATION)
            stateLiveData.value = SearchViewState.Progress
            val result = githubRepository.search(term)
            when (result) {
                is Result.Success -> {
                    val items = result.data.items
                    if (items.isEmpty())
                        stateLiveData.value = SearchViewState.Empty
                    else
                        stateLiveData.value =
                                SearchViewState.Success(result.data.items.toList())
                }
                is Result.Error -> {
                    if (result.exception is SSLHandshakeException) {
                        val handled = withContext(dispatcherProvider.io) {
                            tlsProviderInstaller?.install()
                        }
                        if (handled != null && handled) {
                            termUpdated(term)
                            return@launch
                        }
                    }
                    val message = result.exception.message
                    if (message != null && !message.contains("cancelled"))
                        stateLiveData.value =
                                SearchViewState.Error("Something went wrong...!")
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }

    companion object {
        const val SEARCH_DEBOUNCE_DURATION = 400L
    }
}