package com.example.w4e.start.model

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime

data class Post constructor(val id: Int = -1, var text: String, var user_name: String, var category: Category, var time: LocalDateTime = LocalDateTime.now()) {
}