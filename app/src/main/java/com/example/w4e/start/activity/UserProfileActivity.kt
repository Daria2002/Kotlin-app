package com.example.w4e.start.activity

import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.os.FileUtils
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.downloader.OnDownloadListener
import com.downloader.PRDownloader
import com.example.w4e.start.R
import com.example.w4e.start.adapter.UserProfileAdapter
import com.example.w4e.start.helper.PostDatabaseHelper
import com.example.w4e.start.helper.UserDatabaseHelper
import com.example.w4e.start.model.User
import com.github.barteksc.pdfviewer.PDFView
import com.google.android.material.tabs.TabLayout
import java.io.File

class UserProfileActivity: AppCompatActivity(), View.OnClickListener {
    private lateinit var userName: String
    private lateinit var cvUrl: String
    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userName = intent.getStringExtra("USER_NAME").toString()
        cvUrl = intent.getStringExtra("CV_URL").toString()
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
        tabLayout.addTab(tabLayout.newTab().setText("Posts"))
        tabLayout.addTab(tabLayout.newTab().setText("CV and Experience"))
        tabLayout.addTab(tabLayout.newTab().setText("Review"))
        tabLayout.addTab(tabLayout.newTab().setText("In Progress"))
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL
        val adapter = UserProfileAdapter(this, supportFragmentManager,
            tabLayout.tabCount, cvUrl, userName)
        viewPager.adapter = adapter
    }

    // Extension function to replace fragment
    fun AppCompatActivity.replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        val bundle = Bundle()
        bundle.putString("CV_URL", cvUrl)
        fragment.arguments = bundle
        transaction.replace(R.id.userProfile, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun initViews() {
        title = "$userName's Profile"
        tabLayout = findViewById(R.id.tabLayout)
        viewPager = findViewById(R.id.viewPager)
    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }
}