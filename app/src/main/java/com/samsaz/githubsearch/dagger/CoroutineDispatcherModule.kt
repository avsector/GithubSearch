package com.samsaz.githubsearch.dagger

import com.samsaz.githubsearch.data.CoroutineDispatcherProvider
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers

/**
 * Copyright 2018
 * Created and maintained by Hamid Moazzami
 */
@Module
class CoroutineDispatcherModule {

    @Provides
    fun provideCoroutineDispatcherProvider(): CoroutineDispatcherProvider {
        return CoroutineDispatcherProvider(
            main = Dispatchers.Main,
            io = Dispatchers.IO
        )
    }

}