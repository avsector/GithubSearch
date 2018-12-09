package com.samsaz.githubsearch.dagger

import com.samsaz.githubsearch.dagger.CoroutineDispatcherModule
import com.samsaz.githubsearch.dagger.GithubApiModule
import com.samsaz.githubsearch.search.SearchActivity
import dagger.Component

/**
 * Copyright 2018
 * Created and maintained by Hamid Moazzami
 */
@Component(dependencies = [CoreComponent::class])
interface SearchComponent {

    fun inject(activity: SearchActivity)

}