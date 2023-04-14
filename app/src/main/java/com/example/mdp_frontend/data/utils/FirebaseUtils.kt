package com.example.mdp_frontend.data.utils

import com.google.android.gms.tasks.Task
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resumeWithException

suspend fun <T> Task<T>.await(): T {
    return suspendCancellableCoroutine { cont ->
        addOnCompleteListener {task ->
            if (task.isSuccessful) {
                val result = task.result
                if (result != null) {
                    cont.resume(result, null)
                } else {
                    cont.resumeWithException(NullPointerException("Task result is null"))
                }
            } else {
                cont.resumeWithException(task.exception ?: Exception("Unknown exception"))
            }
        }
    }
}