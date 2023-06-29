package com.example.weatherforecastmvvm.internal

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import androidx.lifecycle.lifecycleScope


fun <T> lazyDeferred(block: suspend CoroutineScope.() -> T): Lazy<Deferred<T>> {
    return lazy {
        GlobalScope.async(start = CoroutineStart.LAZY) {
            block.invoke(this)
        }
    }
}
//fun <T> lazy(block: () -> T): Lazy<T> {
//    return lazy {
//        block.invoke()
//    }
//}





