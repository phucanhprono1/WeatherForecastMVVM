package com.example.weatherforecastmvvm.internal

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.CoroutineStart
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope

fun <T> lazyDeferred(lifecycleOwner: LifecycleOwner, block: suspend CoroutineScope.() -> T): Lazy<Deferred<T>> {
    return lazy {
        lifecycleOwner.lifecycleScope.async(start = CoroutineStart.LAZY) {
            block.invoke(this)
        }
    }
}



