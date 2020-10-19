package com.example.w4e.start.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.w4e.start.R
import com.example.w4e.start.R.id
import com.example.w4e.start.adapter.PostAdapter
import com.example.w4e.start.helper.PostDatabaseHelper
import com.example.w4e.start.model.Post
import androidx.recyclerview.widget.RecyclerView as RecyclerView

/**
 * This activity is used for displaying posts of selected category
 */
class PostActivity: AppCompatActivity(), View.OnClickListener {
    private val activity = this@PostActivity
    private lateinit var categoryName: AppCompatTextView
    private lateinit var recyclerViewPosts: RecyclerView
    private lateinit var postsRecyclerAdapter: PostAdapter
    private lateinit var listPosts: MutableList<Post>
    private lateinit var category: String
    private lateinit var user_name: String
    private lateinit var postDatabaseHelper: PostDatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.category_posts)
        supportActionBar!!.hide()
        category = intent.getStringExtra("CATEGORY").toString()
        user_name = intent.getStringExtra("USER_NAME").toString()
        initViews()
        initObjects()
    }

    private fun initObjects() {
        postDatabaseHelper = PostDatabaseHelper(activity)
        categoryName.text = category.toUpperCase()
        // the next 4 lines are hardcoded for testing db post helper, TODO: implement like it's done for users
        var prvi = Post(text = "ovo je prvi tekst", user_name = user_name, category = PostDatabaseHelper.titleToCategory(category))
        var drugi = Post(text = "ovo je drugi tekst", user_name = user_name, category = PostDatabaseHelper.titleToCategory(category))
        postDatabaseHelper.addPost(prvi)
        postDatabaseHelper.addPost(drugi)
        listPosts = mutableListOf<Post>()
        listPosts = postDatabaseHelper.getPostsForCategory(category) as MutableList<Post>
        postsRecyclerAdapter = PostAdapter(listPosts, this)
        val mLayoutManager = LinearLayoutManager(applicationContext)
        recyclerViewPosts.layoutManager = mLayoutManager
        recyclerViewPosts.itemAnimator = DefaultItemAnimator()
        recyclerViewPosts.setHasFixedSize(true)
        recyclerViewPosts.adapter = postsRecyclerAdapter
    }

    private fun initViews() {
        categoryName = findViewById(id.textCategoryName) as AppCompatTextView
        recyclerViewPosts = findViewById(id.recyclerViewPosts) as RecyclerView
    }

    /**
     * Open post for more info.
     */
    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }
}