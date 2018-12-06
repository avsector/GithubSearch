package com.samsaz.githubsearch.model

import com.google.gson.annotations.SerializedName

/**
 * Copyright 2018
 * Created and maintained by Hamid Moazzami
 */

data class GithubUser(
        val id: Long,
        @SerializedName("login") val name: String,
        val avatarUrl: String?)