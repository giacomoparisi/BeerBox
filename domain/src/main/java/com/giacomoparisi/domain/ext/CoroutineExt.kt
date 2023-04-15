package com.giacomoparisi.domain.ext

import kotlinx.coroutines.*

fun CoroutineScope.launchSafe(block: suspend () -> Unit): Job =
    launch(CoroutineExceptionHandler { _, _ -> }) {

        block()

    }

fun CoroutineScope.launchCatch(
    block: suspend () -> Unit,
    onError: suspend (Throwable) -> Unit
): Job =
    launch(CoroutineExceptionHandler { _, _ -> }) {

        catchSuspend({ block() }, { onError(it) })

    }

fun CoroutineScope.launchAction(action: Action): Job =
    launch(CoroutineExceptionHandler { _, _ -> }) {

        catchSuspend({ action.block() }, { action.onError(it) })

    }

data class Action(val block: suspend () -> Unit, val onError: suspend (Throwable) -> Unit)

fun action(
    block: suspend () -> Unit,
    onError: suspend (Throwable) -> Unit
): Action =
    Action(block, onError)

fun Throwable.isCoroutineException() =
    this is CancellationException || this is TimeoutCancellationException