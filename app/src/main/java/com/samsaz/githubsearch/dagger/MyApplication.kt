package com.samsaz.githubsearch.dagger

import android.app.Application
import android.content.Context
import com.samsaz.githubsearch.BuildConfig

/**
 * Copyright 2018
 * Created and maintained by Hamid Moazzami
 */

class MyApplication: Application() {
    private val coreComponent: CoreComponent by lazy {
        DaggerCoreComponent.builder()
            .githubApiModule(GithubApiModule())
            .retrofitModule(RetrofitModule(BuildConfig.ENDPOINT))
            .build()
    }

    companion object {
        fun coreComponent(context: Context): CoreComponent {
            return (context.applicationContext as MyApplication).coreComponent
        }
    }
}