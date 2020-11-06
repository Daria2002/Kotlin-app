package com.example.w4e.start.model

enum class Category {
    GRAPHICS {
        // override fun activity() = LoginActivity::class.java
        override fun icon() = "" // TODO: path to icon
        override fun title() = "Graphics"
    },

    TRANSLATION {
        // override fun activity() = LoginActivity::class.java
        override fun icon() = "" // TODO: path to icon
        override fun title() = "Translation"
    },

    PROGRAMMING {
        // override fun activity() = LoginActivity::class.java
        override fun icon() = "" // TODO: path to icon
        override fun title() = "Programming"
    },

    INFLUENCERS {
        // override fun activity() = LoginActivity::class.java
        override fun icon() = "" // TODO: path to icon
        override fun title() = "Influencers"
    };

    // abstract fun activity(): Class<*>
    abstract fun icon(): String
    abstract fun title(): String
}