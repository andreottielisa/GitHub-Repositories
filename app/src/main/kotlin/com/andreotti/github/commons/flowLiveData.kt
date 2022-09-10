package com.andreotti.github.commons

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

private const val DEFAULT_TIMEOUT = 3000L
internal typealias LiveDataUiAction<T> = LiveData<UiAction<T>>

@JvmOverloads
internal fun <T> Flow<T>.asLiveData(
    context: CoroutineContext = EmptyCoroutineContext,
    timeoutInMs: Long = DEFAULT_TIMEOUT
): LiveDataUiAction<T> = liveData(context, timeoutInMs) {
    onStart { emit(LoadAction) }

    collect { emit(SuccessAction(it)) }

    catch { emit(ErrorAction(it)) }
}