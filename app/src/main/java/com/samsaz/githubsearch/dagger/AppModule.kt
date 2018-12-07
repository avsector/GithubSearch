package com.samsaz.githubsearch.dagger

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module

/**
 * Copyright 2018
 * Created and maintained by Hamid Moazzami
 */

@Module
abstract class AppModule {

    @Binds
    abstract fun providesApplicationContext(application: Application): Context

}