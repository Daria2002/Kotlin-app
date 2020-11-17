package com.example.w4e.start.model

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

data class Review @RequiresApi(Build.VERSION_CODES.O) constructor(val id: Int = -1,
                                                                  var reviewer: String,
                                                                  var text: String,
                                                                  var reviewed_user: String,
                                                                  var time: String = LocalDateTime.now().format(formatter))