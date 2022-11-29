package com.andreotti.network.state

sealed class UiAction<out T>

object LoadAction : UiAction<Nothing>()
data class ErrorAction(val data: Throwable) : UiAction<Nothing>()
data class SuccessAction<T>(val data: T) : UiAction<T>()