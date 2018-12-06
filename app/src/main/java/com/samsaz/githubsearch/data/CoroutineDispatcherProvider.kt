package com.samsaz.githubsearch.data

import kotlinx.coroutines.CoroutineDispatcher

/**
 * Copyright 2018
 * Created and maintained by Hamid Moazzami
 */

data class CoroutineDispatcherProvider(val main: CoroutineDispatcher,
                                       val io: CoroutineDispatcher)