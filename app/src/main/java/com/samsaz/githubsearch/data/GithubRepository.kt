package com.samsaz.githubsearch.data


import com.samsaz.githubsearch.data.GithubApi
import com.samsaz.githubsearch.model.Items
import com.samsaz.githubsearch.model.Repo
import com.samsaz.githubsearch.util.Result
import com.samsaz.githubsearch.util.safeCallDeferred
import javax.inject.Inject

/**
 * Copyright 2018
 * Created and maintained by Hamid Moazzami
 */

class GithubRepository @Inject constructor(private val githubApi: GithubApi) {

    suspend fun search(query: String): Result<Items<Repo>> =
        safeCallDeferred(githubApi.searchRepos(query))

}