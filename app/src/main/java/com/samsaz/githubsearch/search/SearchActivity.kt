package com.samsaz.githubsearch.search

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.samsaz.githubsearch.R
import com.samsaz.githubsearch.ui.LoadingView
import com.samsaz.githubsearch.util.checkAllMatched
import kotlinx.android.synthetic.main.activity_search.*


/**
 * Copyright 2018
 * Created and maintained by Hamid Moazzami
 */

class SearchActivity: AppCompatActivity() {

    private val repositoryAdapter = GithubRepoAdapter()
    private lateinit var viewModel: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        viewModel = ViewModelProviders.of(this)[SearchViewModel::class.java]
        viewModel.stateLiveData.observe(this, Observer{
            updateState(it)
        })
    }

    private fun updateState(state: SearchViewState) {
        when(state) {
            is SearchViewState.Success -> {
                repositoryAdapter.items = state.repoList
                repositoryAdapter.notifyDataSetChanged()
                rvList.visibility = View.VISIBLE
                initialNoticeView.visibility = View.GONE
                loadingView.setState(LoadingView.State.Success)
            }
            is SearchViewState.Initial -> {
                rvList.visibility = View.GONE
                initialNoticeView.visibility = View.VISIBLE
                loadingView.setState(LoadingView.State.Success)
            }
            is SearchViewState.Empty -> {
                rvList.visibility = View.GONE
                initialNoticeView.visibility = View.GONE
                loadingView.setState(LoadingView.State.Notice(getString(R.string.noResults)))
            }
            is SearchViewState.Error -> {
                rvList.visibility = View.GONE
                initialNoticeView.visibility = View.GONE
                loadingView.setState(LoadingView.State.Error(message = state.message))
            }
            is SearchViewState.Progress -> {
                rvList.visibility = View.GONE
                initialNoticeView.visibility = View.GONE
                loadingView.setState(LoadingView.State.Progress())
            }
        }.checkAllMatched
    }
}