package com.samsaz.githubsearch.dagger

import com.samsaz.githubsearch.data.GithubRepository
import dagger.Component

/**
 * Copyright 2018
 * Created and maintained by Hamid Moazzami
 */

@Component(modules = [GithubApiModule::class])
interface CoreComponent {

    fun getGithubRepository(): GithubRepository

}