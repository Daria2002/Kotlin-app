package com.example.w4e.start.activity

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.w4e.start.R
import com.example.w4e.start.R.id
import com.example.w4e.start.adapter.PostAdapter
import com.example.w4e.start.helper.PostDatabaseHelper
import com.example.w4e.start.model.Post
import kotlinx.android.synthetic.main.item_post_recycler.view.*


/**
 * This activity is used for displaying posts of selected category
 */
class PostActivity: AppCompatActivity(), View.OnClickListener {
    private val activity = this@PostActivity
    private lateinit var categoryName: AppCompatTextView
    private lateinit var addPostButton: AppCompatButton
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
        initListeners()
        initObjects()
    }

    private fun updatePostsInGui() {
        postsRecyclerAdapter = PostAdapter(listPosts, this)
        recyclerViewPosts.adapter = postsRecyclerAdapter
    }

    private fun initObjects() {
        postDatabaseHelper = PostDatabaseHelper(activity)
        categoryName.text = category.toUpperCase()
        val mLayoutManager = LinearLayoutManager(applicationContext)
        recyclerViewPosts.layoutManager = mLayoutManager
        recyclerViewPosts.itemAnimator = DefaultItemAnimator()
        recyclerViewPosts.setHasFixedSize(true)
        listPosts = mutableListOf<Post>()
        listPosts = postDatabaseHelper.getPostsForCategory(category) as MutableList<Post>
        updatePostsInGui()
    }

    private fun initListeners() {
        addPostButton!!.setOnClickListener(this)
    }

    private fun initViews() {
        categoryName = findViewById(id.textCategoryName) as AppCompatTextView
        recyclerViewPosts = findViewById(id.recyclerViewPosts) as RecyclerView
        addPostButton = findViewById(id.addPost) as AppCompatButton
    }

    /**
     * Open post for more info.
     */
    override fun onClick(v: View) {
        when(v.id) {
            R.id.addPost -> addNewPost(activity)
            R.id.postText -> showPostDetails(activity, v.tag as Post)
        }
    }

    private fun showPostDetails(c: Context, post: Post) {
        val postEditText = EditText(c)
        var postDetails = post.text + "\n" + postDatabaseHelper.getPostCreator(post)
        postEditText.setTextColor(Color.WHITE)
        val dialog = AlertDialog.Builder(c, R.style.Work4Experience_AddPostDialog)
            .setTitle("Post details")
            .setMessage(postDetails)
            .setPositiveButton("Bid", null)
            .setNegativeButton("Cancel", null)
            .create()
        dialog.show()
    }

    private fun addNewPost(c: Context) {
        val postEditText = EditText(c)
        postEditText.setTextColor(Color.WHITE)
        val dialog = AlertDialog.Builder(c, R.style.Work4Experience_AddPostDialog)
            .setTitle("Add a new post")
            .setMessage("What do you want to post?")
            .setView(postEditText)
            .setPositiveButton("Add"
            ) { dialog, which -> updatePostsInDb(postEditText.text.toString()) }
            .setNegativeButton("Cancel", null)
            .create()
        dialog.show()
    }

    private fun updatePostsInDb(text: String) {
        var newPost = Post(text = text,
            user_name = user_name, category = PostDatabaseHelper.titleToCategory(category))
        postDatabaseHelper.addPost(newPost)
        listPosts.add(newPost)
        updatePostsInGui()
    }
}