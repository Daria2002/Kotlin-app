package com.example.w4e.start.model

import android.content.res.Resources

enum class Category {
    PHOTOGRAPHY {
        override fun icon() = "" // TODO: path to icon
        override fun title() = "Photography"
    },

    PROGRAMMING {
        override fun icon() = "" // TODO: path to icon
        override fun title() = "Programming"
    };

    abstract fun icon(): String
    abstract fun title(): String
}