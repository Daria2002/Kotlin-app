package com.example.base.helper

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.IntentSender
import android.net.wifi.hotspot2.pps.Credential
import android.util.Log
import androidx.annotation.VisibleForTesting
import com.example.base.model.Avatar
import com.example.base.model.Worker
import com.google.android.gms.auth.api.credentials.Credentials
import com.google.android.gms.auth.api.credentials.CredentialsOptions
import com.google.android.gms.common.api.ResolvableApiException
import java.lang.Exception

/**
 * Help with signing in and out via SmartLock for Passwords Api
 */

const val REQUEST_SAVE = 54;
const val REQUEST_LOGIN = 704

const val WORKER_PREFERENCE = "workerPreferences"
const val PREFERENCE_FIRST_NAME = "$WORKER_PREFERENCE.firstName"
const val PREFERENCE_LAST_NAME = "$WORKER_PREFERENCE.lastName"
const val PREFERENCE_AVATAR = "$WORKER_PREFERENCE.avatar"
const val PREFERENCE_EMAIL = "$WORKER_PREFERENCE.email"

/**
 * Request login info from Google SmartLock Api
 */
fun Activity.requestLogin(success: (Worker) -> Unit) {
    if(isLoggedIn()) {
        success(getWorker()!!)
        return
    }
}

@VisibleForTesting
fun Context.getWorker(): Worker? {
    return with(getWorkerPreferences()) {
        val worker = Worker(getString(PREFERENCE_FIRST_NAME, null),
            getString(PREFERENCE_LAST_NAME, null),
            getString(PREFERENCE_AVATAR, null)?.let { Avatar.valueOf(it) },
            getString(PREFERENCE_EMAIL, ""))
        if(worker.valid()) worker
        else null
    }
}

fun Context.isLoggedIn(): Boolean {
    return with(getWorkerPreferences()) {
        contains(PREFERENCE_FIRST_NAME)
                && contains(PREFERENCE_LAST_NAME)
                && contains(PREFERENCE_AVATAR)
    }
}

private fun Context.getWorkerPreferences() =
    getSharedPreferences(WORKER_PREFERENCE, Context.MODE_PRIVATE)


interface Login {
    fun loginWorker(activity: Activity, onSucess: (Worker) -> Unit)
    fun saveWorker(activity: Activity, worker: Worker, onSucess: () -> Unit)
}

/**
 * Save login info using Google SmartLock Api
 */
fun Activity.saveLogin(worker: Worker, success: () -> Unit) {
    storeWorkerLocally(worker)
    credentialsApiClient()
        .save(worker.toCredential())
        .addOnCompleteListener {
            if(it.isSuccessful) success()
            else resolveException(it.exception, REQUEST_SAVE)
        }
}

private fun Context.credentialsApiClient() = Credentials.getClient(this, CredentialsOptions.Builder().forceEnableSaveDialog().build())

private fun Activity.resolveException(exception: Exception?, requestCode: Int) {
    if (exception is ResolvableApiException) {
        Log.d(ContentValues.TAG, "Resolving: $exception")
        try {
            exception.startResolutionForResult(this, requestCode)
        } catch (e: IntentSender.SendIntentException) {
            Log.e(ContentValues.TAG, "STATUS: Failed to send resolution.", e)
        }
    }
}

@SuppressLint
private fun Context.editWorker() = getWorkerPreferences().edit()

@VisibleForTesting
fun Context.storeWorkerLocally(worker: Worker) {
    with(worker) {
        if(valid())
            editWorker()
                .putString(PREFERENCE_FIRST_NAME, firstName)
                .putString(PREFERENCE_LAST_NAME, lastName)
                .putString(PREFERENCE_AVATAR, avatar?.name)
                .putString(PREFERENCE_EMAIL, email)
                .apply()
    }
}

/**
 * The default means of obtaining user credentials
 */
object DefaultLogin : Login {
    override fun loginWorker(activity: Activity, onSucess: (Worker) -> Unit) {
        activity.requestLogin(onSucess)
    }

    override fun saveWorker(activity: Activity, worker: Worker, onSucess: () -> Unit) {
        activity.saveLogin(worker, onSucess)
    }
}

/**
 * The login implementation used for this app
 */
var login: Login = DefaultLogin