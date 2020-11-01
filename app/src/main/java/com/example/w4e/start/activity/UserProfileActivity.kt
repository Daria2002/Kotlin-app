package com.example.w4e.start.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import com.example.w4e.start.R

class UserProfileActivity: AppCompatActivity(), View.OnClickListener {
    private lateinit var userName: String
    private lateinit var userNameText: AppCompatTextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userName = intent.getStringExtra("USER_NAME").toString()
        setContentView(R.layout.activity_user_profile)
        supportActionBar!!.hide()
        initViews()
        initObjects()
    }

    private fun initObjects() {
        userNameText.text = userName
    }

    private fun initViews() {
        userNameText = findViewById(R.id.userName) as AppCompatTextView
    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }
}