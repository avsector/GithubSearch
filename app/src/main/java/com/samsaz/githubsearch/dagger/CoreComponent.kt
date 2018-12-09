package com.samsaz.githubsearch.dagger

import android.app.Application
import android.content.Context
import com.samsaz.githubsearch.data.CoroutineDispatcherProvider
import com.samsaz.githubsearch.data.GithubRepository
import dagger.BindsInstance
import dagger.Component


/**
 * Copyright 2018
 * Created and maintained by Hamid Moazzami
 */

@Component(modules = [GithubApiModule::class, AppModule::class, CoroutineDispatcherModule::class])
interface CoreComponent {

    fun getGithubRepository(): GithubRepository
    fun getAppContext(): Context
    fun getCoroutineDispatcherProvider(): CoroutineDispatcherProvider

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder
        fun retrofitModule(module: RetrofitModule): Builder
        fun build(): CoreComponent
    }

}