package com.samsaz.githubsearch.shared

import com.samsaz.githubsearch.data.CoroutineDispatcherProvider
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.resume

/**
 * Copyright 2018
 * Created and maintained by Hamid Moazzami
 */


@UseExperimental(ExperimentalCoroutinesApi::class)
fun provideFakeCoroutineDispatcherProvider(): CoroutineDispatcherProvider {
    return CoroutineDispatcherProvider(
        main = Dispatchers.Unconfined,
        io = Dispatchers.Unconfined
    )
}

fun provideNoDelayCoroutineDispatcherProvider(): CoroutineDispatcherProvider {
    return CoroutineDispatcherProvider(
        main = NoDelayTestDispatcher(),
        io = NoDelayTestDispatcher()
    )
}


@UseExperimental(InternalCoroutinesApi::class)
class NoDelayTestDispatcher: CoroutineDispatcher(), Delay {
    override fun dispatch(context: CoroutineContext, block: Runnable) {
        block.run()
    }

    override fun scheduleResumeAfterDelay(
        timeMillis: Long,
        continuation: CancellableContinuation<Unit>
    ) {
        continuation.resume(Unit)
    }

}