package com.samsaz.githubsearch.model

import com.samsaz.githubsearch.model.GithubUser

/**
 * Copyright 2018
 * Created and maintained by Hamid Moazzami
 */
data class Repo(val id: Long, val name: String, val description: String, val owner: GithubUser,
                val htmlUrl: String, val forks: Int, val watchers: Int, val openIssues: Int)