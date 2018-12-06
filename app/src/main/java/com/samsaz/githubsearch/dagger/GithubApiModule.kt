package com.samsaz.githubsearch.dagger

import com.samsaz.githubsearch.data.GithubApi
import com.samsaz.githubsearch.data.GithubRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

/**
 * Copyright 2018
 * Created and maintained by Hamid Moazzami
 */

@Module(includes = [RetrofitModule::class])
class GithubApiModule {

    @Provides
    fun provideGithubRepository(api: GithubApi): GithubRepository {
        return GithubRepository(api)
    }

    @Provides
    fun provideGithubApi(retrofit: Retrofit): GithubApi {
        return retrofit.create(GithubApi::class.java)
    }
}