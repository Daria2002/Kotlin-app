package com.example.w4e.start.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.w4e.start.R
import com.example.w4e.start.adapter.CategoryAdapter
import com.example.w4e.start.helper.UserDatabaseHelper
import com.example.w4e.start.model.Category
import kotlinx.android.synthetic.main.item_category_recycler.*

class CategorySelectionActivity : AppCompatActivity(), View.OnClickListener {
    private val activity = this@CategorySelectionActivity
    private lateinit var userName: AppCompatTextView
    private lateinit var myProfileButton: AppCompatButton
    private lateinit var recyclerViewCategories: RecyclerView
    private lateinit var listCategories: MutableList<Category>
    private lateinit var categoriesRecyclerAdapter: CategoryAdapter
    private lateinit var userDatabaseHelper: UserDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
        supportActionBar!!.hide()
        initViews()
        initListeners()
        initObjects()
    }

    private fun initListeners() {
        myProfileButton!!.setOnClickListener(this)
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
        myProfileButton = findViewById(R.id.myProfile) as AppCompatButton
        myProfileButton.setTransformationMethod(null)
        recyclerViewCategories = findViewById(R.id.recyclerViewUsers) as RecyclerView
    }

    private fun openProfile(userName: String) {
        val intent = Intent(applicationContext, UserProfileActivity::class.java)
        intent.putExtra("USER_NAME", userName)
        startActivity(intent)
    }

    private fun showPostsForCategory(categoryIndex: Int) {
        val intent = Intent(applicationContext, PostActivity::class.java)
        intent.putExtra("CATEGORY", Category.values()[categoryIndex].title())
        intent.putExtra("USER_NAME", userName.text)
        startActivity(intent)
    }

    override fun onClick(v: View?) {
        if (v != null) {
            if(v.id == R.id.myProfile) {
                openProfile(userName = userName.text.toString())
            } else {
                var categoryIndex = v.id
                showPostsForCategory(categoryIndex)
            }
        }
    }
}