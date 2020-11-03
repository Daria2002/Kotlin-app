package com.example.w4e.start.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.viewpager.widget.ViewPager
import com.example.w4e.start.R
import com.example.w4e.start.adapter.UserProfileAdapter
import com.google.android.material.tabs.TabLayout

class UserProfileActivity: AppCompatActivity(), View.OnClickListener {
    private lateinit var userName: String
    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userName = intent.getStringExtra("USER_NAME").toString()
        setContentView(R.layout.activity_user_profile)
        initViews()
        initListeners()
        initObjects()
    }

    private fun initListeners() {
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    private fun initObjects() {
        tabLayout.addTab(tabLayout.newTab().setText("Review"))
        tabLayout.addTab(tabLayout.newTab().setText("CV and Experience"))
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL
        val adapter = UserProfileAdapter(this, supportFragmentManager,
            tabLayout.tabCount)
        viewPager.adapter = adapter
    }

    private fun initViews() {
        title = userName + "'s Profile"
        tabLayout = findViewById(R.id.tabLayout)
        viewPager = findViewById(R.id.viewPager)
    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }
}