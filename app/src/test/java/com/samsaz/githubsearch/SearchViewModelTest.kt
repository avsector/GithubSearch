package com.samsaz.githubsearch

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.samsaz.githubsearch.data.GithubRepository
import com.samsaz.githubsearch.model.GithubUser
import com.samsaz.githubsearch.model.Items
import com.samsaz.githubsearch.model.Repo
import com.samsaz.githubsearch.search.SearchViewModel
import com.samsaz.githubsearch.search.SearchViewState
import com.samsaz.githubsearch.search.TermUpdated
import com.samsaz.githubsearch.shared.LiveDataTestUtil
import com.samsaz.githubsearch.shared.provideFakeCoroutineDispatcherProvider
import com.samsaz.githubsearch.shared.provideNoDelayCoroutineDispatcherProvider
import com.samsaz.githubsearch.util.Result
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import java.io.IOException

/**
 * Copyright 2018
 * Created and maintained by Hamid Moazzami
 */

class SearchViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    val repos = Array<Repo>(10) {
        val user = GithubUser(it.toLong(), "User $it", null)
        Repo(
            it.toLong(), "Repo $it", "This is Repo $it", user,
            "", it * 10, it * 100, it
        )
    }

    private fun createRepository(repos: Array<Repo>?): GithubRepository {
        return mock<GithubRepository> {
            on { runBlocking { search("term") } }.doReturn(
                if (repos != null) {
                    Result.Success(Items(repos))
                } else {
                    Result.Error(IOException("Error!"))
                }
            )
        }
    }

    private fun getViewModel(repository: GithubRepository, withDelay: Boolean): SearchViewModel {
        val dispatcherProvider = if (withDelay) {
            provideFakeCoroutineDispatcherProvider()
        } else {
            provideNoDelayCoroutineDispatcherProvider()
        }
        return SearchViewModel(repository, dispatcherProvider)
    }

    private fun getState(viewModel: SearchViewModel): SearchViewState? {
        return LiveDataTestUtil.getValue(viewModel.stateLiveData)
    }

    private fun simulateSearch(repos: Array<Repo>?, withDelay: Boolean, term: String = "term"):
            SearchViewState? {
        val repository = createRepository(repos)
        val viewModel = getViewModel(repository, withDelay)

        viewModel.onEvent(TermUpdated(term))
        return getState(viewModel)
    }

    @Test
    fun emptyResultTest() {
        val state = simulateSearch(arrayOf(), false)
        assertEquals(state, SearchViewState.Empty)
    }

    @Test
    fun shortTermWontTriggerSearchTest() {
        val state = simulateSearch(repos, false, "t")
        assertEquals(state, SearchViewState.Initial)
    }

    @Test
    fun legitSearchHasResultsTest() {
        val state = simulateSearch(repos, false)
        assertTrue(state is SearchViewState.Success)
        assertEquals((state as SearchViewState.Success).repoList.size, repos.size)
    }

    @Test
    fun searchErrorTest() {
        val state = simulateSearch(null, false)
        assertTrue(state is SearchViewState.Error)
    }

    @Test
    fun searchDelayTest() {
        val repository = createRepository(repos)
        val viewModel = getViewModel(repository, true)
        viewModel.onEvent(TermUpdated("term"))
        Thread.sleep(SearchViewModel.SEARCH_DEBOUNCE_DURATION)
        var state = getState(viewModel)
        assertTrue(state is SearchViewState.Progress)
        Thread.sleep(10)
        state = getState(viewModel)
        assertTrue(state is SearchViewState.Success)
    }

    @Test
    fun searchDebounceTest() {
        var firstTermCalled = false
        var secondTermCalled = false
        val repository = mock<GithubRepository> {
            on { runBlocking { search("term") } }.doAnswer {
                firstTermCalled = true
                Result.Success(Items(repos))
            }
            on { runBlocking { search("otherTerm") } }.doAnswer {
                secondTermCalled = true
                Result.Success(Items(repos.slice(1..5).toTypedArray()))
            }
        }
        val viewModel = getViewModel(repository, true)

        viewModel.onEvent(TermUpdated("term"))
        Thread.sleep(10)
        viewModel.onEvent(TermUpdated("otherTerm"))
        Thread.sleep(SearchViewModel.SEARCH_DEBOUNCE_DURATION + 50)
        val state = getState(viewModel)

        assertFalse(firstTermCalled)
        assertTrue(secondTermCalled)
        assertEquals((state as SearchViewState.Success).repoList.size, 5)
    }


}