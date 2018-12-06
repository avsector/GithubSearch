package com.samsaz.githubsearch.search

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.samsaz.githubsearch.R
import com.samsaz.githubsearch.model.Repo
import com.samsaz.githubsearch.search.GithubRepoViewHolder

/**
 * Copyright 2018
 * Created and maintained by Hamid Moazzami
 */

class GithubRepoAdapter: RecyclerView.Adapter<GithubRepoViewHolder>() {

    var items: List<Repo> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GithubRepoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_repo, parent, false)
        val holder = GithubRepoViewHolder(view)
        holder.itemView.setOnClickListener {
            val position = holder.adapterPosition
            if (position == RecyclerView.NO_POSITION)
                return@setOnClickListener
            val repo = items[position]
            val context = it.context
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(repo.htmlUrl))
            if (context.packageManager.resolveActivity(intent, 0) != null)
                context.startActivity(intent)
        }
        return holder
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: GithubRepoViewHolder, position: Int) {
        val repo = items[position]
        holder.bind(repo)
    }
}