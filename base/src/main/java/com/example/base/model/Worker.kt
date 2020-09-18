package com.example.base.model

import android.os.Parcelable
import com.example.base.helper.TAG

/**
 * Stores values to identify the subject that is currently attempting to post/search
 */
data class Worker (
    var firstName: String?,
    var lastName: String?,
    /**
     * Only used for SmartLock purposes
     */
    val email: String? = TAG
) : Parcelable {

}