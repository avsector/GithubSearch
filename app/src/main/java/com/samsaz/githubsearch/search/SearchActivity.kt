package com.samsaz.githubsearch.search

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.samsaz.githubsearch.R
import com.samsaz.githubsearch.dagger.inject
import com.samsaz.githubsearch.ui.LoadingView
import com.samsaz.githubsearch.util.checkAllMatched
import kotlinx.android.synthetic.main.activity_search.*
import javax.inject.Inject


/**
 * Copyright 2018
 * Created and maintained by Hamid Moazzami
 */

class SearchActivity: AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: SearchViewModelFactory
    private val repositoryAdapter = GithubRepoAdapter()
    private lateinit var viewModel: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        inject(this)

        etInput.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s != null) {
                    textChanged(s.toString())
                }
            }
        })

        rvList.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvList.adapter = repositoryAdapter
        rvList.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState != RecyclerView.SCROLL_STATE_IDLE) {
                    hideKeyboard()
                }
            }
        })

        viewModel = ViewModelProviders.of(this, viewModelFactory)[SearchViewModel::class.java]
        viewModel.getStateLiveData().observe(this, Observer{
            updateState(it)
        })

        loadingView.actionHandler = {
            textChanged(etInput.text.toString())
        }
    }

    fun textChanged(text: String) {
        viewModel.onEvent(TermUpdated(text))
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
                loadingView.setState(LoadingView.State.Notice(getString(R.string.noResults))
                )
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

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(etInput.windowToken, 0)
    }
}