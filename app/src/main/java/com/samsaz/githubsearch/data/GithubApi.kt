package com.samsaz.githubsearch.data

import com.samsaz.githubsearch.model.Items
import com.samsaz.githubsearch.model.Repo
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Copyright 2018
 * Created and maintained by Hamid Moazzami
 */
interface GithubApi {
    @GET("/search/repositories")
    fun searchRepos(@Query("q") query: String,
                    @Query("per_page") pageSize: Int = 100): Deferred<Response<Items<Repo>>>
}