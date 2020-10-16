package com.example.w4e.start.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import com.example.w4e.start.R

/**
 * This activity is used for displaying posts of selected category
 */
class CategoryActivity: AppCompatActivity(), View.OnClickListener {
    private lateinit var appTextName: AppCompatTextView
    override fun onCreate(savedInstanceState: Bundle?) {
        // val category = intent.getStringExtra("CATEGORY")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.category_posts)
        supportActionBar!!.hide()
        initViews()
        initObjects()
    }

    private fun initObjects() {
        appTextName.text = "posts"
    }

    private fun initViews() {
        appTextName = findViewById(R.id.appName) as AppCompatTextView
    }

    /**
     * Open post for more info.
     */
    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }
}