package com.example.w4e.start.model

import android.content.Intent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import com.example.w4e.start.activity.CategorySelectionActivity
import com.example.w4e.start.activity.LoginActivity
import com.example.w4e.start.activity.RegisterActivity

enum class Category(value: Int) {
    GRAPHICS(0) {
        override fun activity() = LoginActivity::class.java
        override fun icon() = "" // TODO: path to icon
        override fun title() = "Graphics"
    },

    TRANSLATION(1) {
        override fun activity() = LoginActivity::class.java
        override fun icon() = "" // TODO: path to icon
        override fun title() = "Translation"
    },

    PROGRAMMING(2) {
        override fun activity() = LoginActivity::class.java
        override fun icon() = "" // TODO: path to icon
        override fun title() = "Programming"
    },

    INFLUENCERS(3) {
        override fun activity() = LoginActivity::class.java
        override fun icon() = "" // TODO: path to icon
        override fun title() = "Influencers"
    };

    abstract fun activity(): Class<*>
    abstract fun icon(): String
    abstract fun title(): String
}