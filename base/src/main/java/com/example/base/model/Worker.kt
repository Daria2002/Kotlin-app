package com.example.base.model

import com.google.android.gms.auth.api.credentials.Credential
import android.os.Parcel
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
    /**
     * Create a Worker from SmartLock API
     */
    constructor(credential: Credential) :
            this(firstName = credential.name?.substringBefore(" "),
                lastName = credential.name?.substringAfterLast(" ")?.get(0).toString(),
                email = credential.id ?: TAG)

    override fun writeToParcel(dest: Parcel, flags: Int) {
        with(dest) {
            writeString(firstName)
            writeString(lastName)
            writeString(email)
        }
    }

    override fun describeContents() = 0

    override fun toString() = "$firstName $lastName"

    fun valid() = !(firstName.isNullOrEmpty() || lastName.isNullOrEmpty())

    /**
     * Create a [Credential] containing information of this [Worker]
     */
    fun toCredential(): Credential = Credential.Builder(email)
        .setName(toString())
        .build()

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<Worker> = object : Parcelable.Creator<Worker> {
            override fun createFromParcel(parcel: Parcel) = with(parcel) {
                Worker(firstName = readString(),
                        lastName = readString(),
                        email = readString())
            }

            override fun newArray(size: Int): Array<Worker?> = arrayOfNulls(size)
        }
    }
}