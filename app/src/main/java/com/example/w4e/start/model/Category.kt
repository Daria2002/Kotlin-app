package com.example.w4e.start.model

import android.content.res.Resources

enum class Category {
    PHOTOGRAPHY {
        override fun icon() = "" // TODO: path to icon
        override fun title() = "Photography"
        override var id: Int
            get() = 1
            set(value) {}
    },

    PROGRAMMING {
        override fun icon() = "" // TODO: path to icon
        override fun title() = "Programming"
        override var id: Int
            get() = 2
            set(value) {}
    };

    abstract fun icon(): String
    abstract fun title(): String
    abstract var id : Int
}