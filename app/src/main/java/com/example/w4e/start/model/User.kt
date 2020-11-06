package com.example.w4e.start.model

import android.provider.ContactsContract

// model class
data class User(val id: Int = -1, val name: String, val email: String, val password: String, val cv: ByteArray) {
    override fun toString() = name
    fun valid() = !(name.isNullOrEmpty() || email.isNullOrEmpty())
}