package com.example.base.model

import androidx.annotation.DrawableRes
import com.example.base.R

/**
 * The available avatars with their corresponding drawable resource ids.
 */
enum class Avatar(@param:DrawableRes val drawableId: Int) {
    ONE(R.drawable.avatar1),
    TWO(R.drawable.avatar2);
    val nameForAccessibility get() = "Avatar ${ordinal + 1}"
}