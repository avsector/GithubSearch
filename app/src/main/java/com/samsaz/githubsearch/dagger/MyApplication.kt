package com.samsaz.githubsearch.dagger

import android.app.Application
import android.content.Context
import com.samsaz.githubsearch.BuildConfig
import javax.inject.Inject

/**
 * Copyright 2018
 * Created and maintained by Hamid Moazzami
 */

class MyApplication: Application() {
    private val coreComponent: CoreComponent by lazy {
        DaggerCoreComponent.builder()
            .retrofitModule(RetrofitModule(BuildConfig.ENDPOINT))
            .application(this)
            .build()
    }

    companion object {
        fun coreComponent(context: Context): CoreComponent {
            return (context.applicationContext as MyApplication).coreComponent
        }
    }
}