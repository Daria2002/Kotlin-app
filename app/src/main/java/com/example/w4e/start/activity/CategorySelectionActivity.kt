package com.example.w4e.start.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.w4e.start.R

class CategorySelectionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
        supportActionBar!!.hide()
    }
}