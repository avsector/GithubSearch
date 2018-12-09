package com.samsaz.githubsearch.search

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.samsaz.githubsearch.R
import com.samsaz.githubsearch.model.Repo
import com.samsaz.githubsearch.util.GlideApp
import kotlinx.android.synthetic.main.item_repo.view.*

/**
 * Copyright 2018
 * Created and maintained by Hamid Moazzami
 */

class GithubRepoViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(repo: Repo) = with(itemView) {
        name.text = repo.name
        description.text = repo.description

        val transition = DrawableTransitionOptions.withCrossFade(DrawableCrossFadeFactory.Builder()
            .setCrossFadeEnabled(true))
        val transform = MultiTransformation(FitCenter(),
            RoundedCorners(itemView.resources.getDimensionPixelSize(R.dimen.cardItemCornerRadius)))

        GlideApp.with(itemView)
            .load(repo.owner.avatarUrl)
            .placeholder(R.drawable.ic_github_list)
            .transform(transform)
            .transition(transition)
            .into(itemView.userImage)

        score.text = resources.getString(R.string.score, calculateScore(repo))
    }


    private fun calculateScore(repo: Repo): Int = with(repo) {
        (watchers + forks * 2) * 10 / (openIssues + 1)
    }
}