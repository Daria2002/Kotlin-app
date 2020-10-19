package com.example.w4e.start.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.w4e.start.R
import com.example.w4e.start.adapter.CategoryAdapter
import com.example.w4e.start.helper.UserDatabaseHelper
import com.example.w4e.start.model.Category

class CategorySelectionActivity : AppCompatActivity(), View.OnClickListener {
    private val activity = this@CategorySelectionActivity
    private lateinit var userName: AppCompatTextView
    private lateinit var recyclerViewCategories: RecyclerView
    private lateinit var listCategories: MutableList<Category>
    private lateinit var categoriesRecyclerAdapter: CategoryAdapter
    private lateinit var userDatabaseHelper: UserDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
        supportActionBar!!.hide()
        initViews()
        initObjects()
    }

    private fun initObjects() {
        listCategories = ArrayList()
        userDatabaseHelper = UserDatabaseHelper(activity)
        listCategories = Category.values().toMutableList()
        categoriesRecyclerAdapter = CategoryAdapter(listCategories, this)
        val mLayoutManager = LinearLayoutManager(applicationContext)
        recyclerViewCategories.layoutManager = mLayoutManager
        recyclerViewCategories.itemAnimator = DefaultItemAnimator()
        recyclerViewCategories.setHasFixedSize(true)
        recyclerViewCategories.adapter = categoriesRecyclerAdapter
        val emailFromIntent = intent.getStringExtra("EMAIL")
        userName.text = userDatabaseHelper.getUser(emailFromIntent)
    }

    private fun initViews() {
        userName = findViewById(R.id.userName) as AppCompatTextView
        recyclerViewCategories = findViewById(R.id.recyclerViewUsers) as RecyclerView
    }

    override fun onClick(v: View?) {
        if (v != null) {
            val intent = Intent(applicationContext, PostActivity::class.java)
            intent.putExtra("CATEGORY", Category.values()[v.id].title())
            intent.putExtra("USER_NAME", userName.text)
            startActivity(intent)
        }
    }
}