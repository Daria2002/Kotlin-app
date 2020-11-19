package com.example.w4e.start.model

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@RequiresApi(Build.VERSION_CODES.O)
val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)


data class Post @RequiresApi(Build.VERSION_CODES.O) constructor(val id: Int, var text: String,
                                                                var user_name: String,
                                                                var category: Category,
                                                                var time: String = LocalDateTime.now().format(formatter),
                                                                var worker_name: String = "")