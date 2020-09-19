package com.example.base.helper

import android.content.Context
import android.service.media.MediaBrowserService
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.base.persistence.Work4ExperienceDatabaseHelper

const val TAG = "Work4Experience"

fun Context.database() = Work4ExperienceDatabaseHelper.getInstance(this)

fun Context.inflate(resource: Int, root: ViewGroup?, attachToRoot: Boolean) : View =
    LayoutInflater.from(this).inflate(resource, root, attachToRoot)