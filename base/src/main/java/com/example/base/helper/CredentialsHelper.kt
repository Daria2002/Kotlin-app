package com.example.base.helper

import android.content.Context

/**
 * Help with signing in and out via SmartLock for Passwords Api
 */

const val WORKER_PREFERENCE = "workerPreferences"
const val PREFERENCE_FIRST_NAME = "$WORKER_PREFERENCE.firstName"
const val PREFERENCE_LAST_NAME = "$WORKER_PREFERENCE.lastInitial"

fun Context.isLoggedIn(): Boolean {
    return with(getWorkerPreferences()) {
        contains(PREFERENCE_FIRST_NAME)
                && contains(PREFERENCE_LAST_NAME)
    }
}

private fun Context.getWorkerPreferences() =
    getSharedPreferences(WORKER_PREFERENCE, Context.MODE_PRIVATE)
