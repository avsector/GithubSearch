package com.samsaz.githubsearch.ui

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.samsaz.githubsearch.R
import com.samsaz.githubsearch.util.checkAllMatched
import kotlinx.android.synthetic.main.view_loading.view.*

/**
 * Copyright 2018
 * Created and maintained by Hamid Moazzami
 */

class LoadingView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    var actionHandler: (() -> Unit)? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.view_loading, this, true)
        gravity = Gravity.CENTER
        orientation = LinearLayout.VERTICAL
        btnAction.setOnClickListener {
            actionHandler?.invoke()
        }
        setState(State.Success)
    }

    fun setState(state: State) {
        when(state) {
            is State.Progress -> {
                if (state.notice != null) {
                    tvNotice.text = state.notice
                    tvNotice.visibility = View.VISIBLE
                } else {
                    tvNotice.visibility = View.GONE
                }
                pbProgress.visibility = View.VISIBLE
                btnAction.visibility = View.GONE
                visibility = View.VISIBLE
            }
            is State.Error -> {
                tvNotice.text = state.message
                tvNotice.visibility = View.VISIBLE
                pbProgress.visibility = View.GONE
                if (state.action != null) {
                    btnAction.text = state.action
                } else {
                    btnAction.text = resources.getString(R.string.tryAgain)
                }
                btnAction.visibility = View.VISIBLE
                visibility = View.VISIBLE
            }
            is State.Notice -> {
                tvNotice.text = state.notice
                tvNotice.visibility = View.VISIBLE
                pbProgress.visibility = View.GONE
                btnAction.visibility = View.GONE
                visibility = View.VISIBLE
            }
            is State.Success -> {
                visibility = View.GONE
            }
        }.checkAllMatched
    }

    sealed class State {
        data class Progress(val notice: String? = null): State()
        object Success: State()
        data class Error(val message: String, val action: String? = null): State()
        data class Notice(val notice: String): State()
    }
}